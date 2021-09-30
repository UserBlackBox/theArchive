import json
from django.utils import timezone
from django.views import generic
from django.db import models
from django.http import Http404
from django.shortcuts import render, redirect
from .models import RetreivedData
from django.db import connection
from .tasks import retrieve


class IndexView(generic.TemplateView):
    template_name = 'dashboard/index.html'

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        with open('conf/display.json') as f:
            displayConf = json.load(f)
        context['cards'] = []
        for card in displayConf['cards']:
            context['cards'].append({})
            context['cards'][-1]['name'] = card['name']
            context['cards'][-1]['icon'] = card['icon']
            context['cards'][-1]['data'] = []

            for data in card['data']:
                entry = [data['name']]
                value = RetreivedData.objects.filter(sourceID=data['id']).order_by('time').last()
                if not value:
                    value = ""
                else:
                    if 'recent' in data and data['recent'] and (timezone.now() - value.time).total_seconds() > 300:
                        value = ""
                    else:
                        value = value.value
                        if len(value) != 0:
                            if 'round' in data.keys():
                                value = str(round(float(value), data['round']))
                            if 'unit' in data.keys():
                                value += " " + data['unit']
                entry.append(value)
                context['cards'][-1]['data'].append(entry)
        return context


class SourcesView(generic.TemplateView):
    template_name = "dashboard/sources.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        with open('conf/sources.json') as f:
            sourcesConf = json.load(f)
        context['sources'] = []
        for source in sourcesConf.keys():
            context['sources'].append(sourcesConf[source])
            context['sources'][-1]['id'] = source
        return context


class SourcesEditView(generic.TemplateView):
    template_name = "dashboard/sourceEdit.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        with open('conf/sources.json') as f:
            sourcesConf = json.load(f)
        try:
            context['sourceDetail'] = sourcesConf[context['source']]
        except KeyError:
            raise Http404("Source does not exist")
        context['sourceDetail']['data'] = "{\n\t" + json.dumps(context['sourceDetail']['data'])[1:-1].replace(", ", ",\n\t") + "\n}"
        try:
            context['sourceDetail']['header'] = "{\n\t" + json.dumps(context['sourceDetail']['header'])[1:-1].replace(", ", ",\n\t") + "\n}"
        except KeyError:
            pass
        return context


class SourceEditPOST(generic.View):
    def post(self, request, **kwargs):
        formData = request.POST
        with open('conf/sources.json') as f:
            sourcesConf = json.load(f)
        source = formData.get('sourceID')
        if len(formData.get('sourceID')) == 0:
            return redirect('/sources/new/')
        if source not in sourcesConf.keys():
            sourcesConf[source] = {}
        sourcesConf[source]['url'] = formData.get('sourceURL')
        sourcesConf[source]['frequency'] = int(formData.get('sourceFrequency'))
        jsonParse = json.JSONDecoder()
        if len(formData.get('sourceHeader')) > 1:
            sourcesConf[source]['header'] = jsonParse.decode(formData.get('sourceHeader'))
        if len(formData.get('sourceData')) > 1:
            sourcesConf[source]['data'] = jsonParse.decode(formData.get('sourceData'))
        with open('conf/sources.json', 'w') as f:
            json.dump(sourcesConf, f, indent=4)

        with connection.cursor() as cursor:
            cursor.execute("DELETE FROM background_task;")
        for i in sourcesConf.keys():
            retrieve(i, repeat=sourcesConf[i]['frequency'], repeat_until=None)
        return redirect('/sources/')


class SourceDelete(generic.View):
    def get(self, request, **kwargs):
        source = request.path.split('/')[-1]

        with open('conf/sources.json') as f:
            sourcesConf = json.load(f)

        try:
            del sourcesConf[source]
        except KeyError:
            raise Http404("Source does not exist")

        with open('conf/sources.json', 'w') as f:
            json.dump(sourcesConf, f, indent=4)

        with connection.cursor() as cursor:
            cursor.execute("DELETE FROM background_task;")
        for i in sourcesConf.keys():
            retrieve(i, repeat=sourcesConf[i]['frequency'], repeat_until=None)

        return redirect('/sources/')


class SourceAddView(generic.TemplateView):
    template_name = "dashboard/sourceNew.html"


class CardsView(generic.TemplateView):
    template_name = "dashboard/cards.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        with open('conf/display.json') as f:
            displayConf = json.load(f)
        context['cards'] = displayConf['cards']
        context['last'] = len(context['cards'])-1
        for card in range(len(context['cards'])):
            context['cards'][card]['id'] = card
        return context


class CardsUp(generic.View):
    def get(self, request, **kwargs):
        id = int(request.path.split('/')[-1])

        with open('conf/display.json') as f:
            displayConf = json.load(f)

        if id == 0:
            return redirect('/cards/')

        displayConf['cards'][id], displayConf['cards'][id-1] = displayConf['cards'][id-1], displayConf['cards'][id]

        with open('conf/display.json', 'w') as f:
            json.dump(displayConf, f, indent=4)

        return redirect('/cards/')


class CardsDown(generic.View):
    def get(self, request, **kwargs):
        id = int(request.path.split('/')[-1])

        with open('conf/display.json') as f:
            displayConf = json.load(f)

        if id == len(displayConf['cards'])-1:
            return redirect('/cards/')

        displayConf['cards'][id], displayConf['cards'][id+1] = displayConf['cards'][id+1], displayConf['cards'][id]

        with open('conf/display.json', 'w') as f:
            json.dump(displayConf, f, indent=4)

        return redirect('/cards/')


class CardsEdit(generic.TemplateView):
    template_name = "dashboard/cardEdit.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        id = context['id']
        with open('conf/display.json') as f:
            displayConf = json.load(f)
        context['cardDetail'] = displayConf['cards'][id]
        context['cardDetail']['data'] = json.dumps(context['cardDetail']['data'])
        return context


class CardsEditPOST(generic.View):
    def post(self, request, **kwargs):
        id = int(request.path.split('/')[-1])
        formData = request.POST
        jsonParse = json.JSONDecoder()
        with open('conf/display.json') as f:
            displayConf = json.load(f)
        displayConf['cards'][id]['name'] = formData.get('cardName')
        displayConf['cards'][id]['icon'] = formData.get('cardIcon')
        displayConf['cards'][id]['data'] = jsonParse.decode(formData.get('cardData'))
        with open('conf/display.json', 'w') as f:
            json.dump(displayConf, f, indent=4)
        return redirect('/cards/')


class CardsNew(generic.View):
    def get(self, request, **kwargs):
        with open('conf/display.json') as f:
            displayConf = json.load(f)

        displayConf['cards'].append(
            {
                'name': 'New Card',
                'icon': 'card-heading',
                'data': []
            }
        )

        with open('conf/display.json', 'w') as f:
            json.dump(displayConf, f, indent=4)

        return redirect('/cards/')


class CardsDelete(generic.View):
    def get(self, request, **kwargs):
        id = int(request.path.split('/')[-1])

        with open('conf/display.json') as f:
            displayConf = json.load(f)

        del displayConf['cards'][id]

        with open('conf/display.json', 'w') as f:
            json.dump(displayConf, f, indent=4)

        return redirect('/cards/')