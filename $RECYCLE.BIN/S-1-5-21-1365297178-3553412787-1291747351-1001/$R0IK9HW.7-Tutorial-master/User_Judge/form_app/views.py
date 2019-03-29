from django.shortcuts import render, render_to_response
from django import forms
from django.http import HttpResponse, HttpResponseRedirect
from django.template import RequestContext
from form_app.models import User


# Create your views here.
class UserForm(forms.Form):
    username = forms.CharField(label='用户名', max_length=100)
    password = forms.CharField(label='密__码', widget=forms.PasswordInput())


def regist(req):
    if req.method == 'POST':
        uf = UserForm(req.POST)
        if uf.is_valid():
            password = uf.cleaned_data['password']
            username = uf.cleaned_data['username']  # 添加到数据库
            registAdd = User.objects.get_or_create(username=username, password=password)[1]
            if registAdd == False:
                return render_to_response('share.html', {'registAdd': registAdd, 'username': username})
            else:
                return render_to_response('share.html', {'registAdd': registAdd})
    else:
        uf = UserForm()
        return render_to_response('regist.html', {'uf': uf})


def login(req):
    if req.method == 'POST':
        uf = UserForm(req.POST)
        if uf.is_valid():
            username = uf.cleaned_data['username']
            password = uf.cleaned_data['password']
            # 对比提交的数据与数据库中的数据
            user = User.objects.filter(username__exact=username, password__exact=password)
            if user:
                response = HttpResponseRedirect('/index/')
                # 将username写入浏览器cookie，失效时间为3600
                response.set_cookie('username', username, 3600)
                return response
            else:
                return HttpResponseRedirect('/login/')#重定向到登录界面
    else:
        uf = UserForm()
    # 否则为空
    return render_to_response('login.html', {'uf': uf})

    # return render(request,'login.html',context=csrf(request))


# 登录成功
def index(req):
    username = req.COOKIES.get('username', '')
    return render_to_response('index.html', {'username': username})


# 退出登录

# 登出
def logout(req):
    response = HttpResponse('logout!!!')
    # 清除cookie里保存的username
    response.delete_cookie('username')
    return response


def share(req):
    if req.method == 'POST':
        uf = UserForm(req.POST)
        if uf.is_valid():
            username = uf.cleaned_data['username']
            password = uf.cleaned_data['password']
        return render_to_response('share.html', {'username': username})
    else:
        uf = UserForm()
    return render_to_response('share.html', {'uf': uf})
