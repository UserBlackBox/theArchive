from django.urls import path
from . import views
import json
from .tasks import retrieve, purge
from django.db import connection

app_name = 'dashboard'
urlpatterns = [
    path('', views.IndexView.as_view(), name='index'),
    path('sources/', views.SourcesView.as_view(), name='sources'),
    path('sources/edit/<slug:source>', views.SourcesEditView.as_view(), name='source_edit'),
    path('sources/post/<slug:source>', views.SourceEditPOST.as_view(), name='source_post'),
    path('sources/post/', views.SourceEditPOST.as_view(), name='source_post'),
    path('sources/delete/<slug:source>', views.SourceDelete.as_view(), name='source_delete'),
    path('sources/new/', views.SourceAddView.as_view(), name='source_new'),
    path('cards/', views.CardsView.as_view(), name='cards'),
    path('cards/up/<int:id>', views.CardsUp.as_view(), name='card_up'),
    path('cards/down/<int:id>', views.CardsDown.as_view(), name='card_down'),
    path('cards/edit/<int:id>', views.CardsEdit.as_view(), name='card_edit'),
    path('cards/post/<int:id>', views.CardsEditPOST.as_view(), name='card_post'),
    path('cards/new/', views.CardsNew.as_view(), name='card_new'),
    path('cards/delete/<int:id>', views.CardsDelete.as_view(), name='card_delete'),
]

with connection.cursor() as cursor:
    cursor.execute("DELETE FROM background_task;")
with open('conf/sources.json') as f:
    data = json.load(f)
for i in data.keys():
    retrieve(i, repeat=data[i]['frequency'], repeat_until=None)
purge(repeat=60, repeat_until=None)