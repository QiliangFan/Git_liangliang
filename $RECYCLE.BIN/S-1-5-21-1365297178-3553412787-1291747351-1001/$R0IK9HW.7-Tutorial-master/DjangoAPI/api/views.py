from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets

from api.models import Student
from api.serializers import StudentSerializers


class StudentViewSet(viewsets.ModelViewSet):
    # 指定结果集并设置排序
    queryset = Student.objects.all().order_by('-pk')
    # 指定序列化的类
    serializer_class = StudentSerializers
