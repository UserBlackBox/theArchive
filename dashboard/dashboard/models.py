from django.db import models
from django.utils import timezone
import datetime

class RetreivedData(models.Model):
    sourceID = models.CharField(max_length=100)
    value = models.CharField(max_length=500)
    time = models.DateTimeField(auto_now_add=True)

