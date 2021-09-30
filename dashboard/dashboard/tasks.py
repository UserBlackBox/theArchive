from background_task import background
from django.db import models
from django.utils import timezone
import datetime
import json
import requests
from .models import RetreivedData

@background(schedule=60)
def retrieve(source):
    with open('conf/sources.json', 'r') as f:
        data = json.load(f)
    if 'header' in data[source].keys():
        request = requests.get(data[source]['url'], headers=data[source]['header'])
    else:
        request = requests.get(data[source]['url'])
    if request.status_code != 200:
        return
    for i in data[source]['data'].keys():
        entry = RetreivedData(sourceID=i, value="")
        path = data[source]['data'][i].split('/')
        value = request.json()
        for j in path:
            if isinstance(value, list):
                value = value[int(j)]
            else:
                value = value[j]
        if value == None:
            value = ""
        entry.value = str(value)
        entry.save()

@background(schedule=60)
def purge():
    toDelete = RetreivedData.objects.filter(time__lt=datetime.datetime.now()-datetime.timedelta(weeks=1))
    toDelete.delete()
