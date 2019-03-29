from django.db import models


# Create your models here.
class Student(models.Model):
    name = models.CharField(u'姓名', max_length=100, default='no_name')
    sex = models.CharField(u'性别', max_length=50, default='male')
    sid = models.CharField(u'学号', max_length=100, default='0')

    def __unicode__(self):
        return '%d: %s' % (self.pk, self.name)
