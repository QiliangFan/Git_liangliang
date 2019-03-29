from rest_framework import serializers

from api.models import Student


class StudentSerializers(serializers.ModelSerializer):
    class Meta:
        model = Student  # 指定的模型类
        fields = ('pk', 'name', 'sex', 'sid',)  # 需要序列化的属性
