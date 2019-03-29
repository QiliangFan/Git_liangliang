from django.urls import path
from form_app import views

urlpatterns = [
    path('login/', views.login, name='login'),
    path('regist/', views.regist, name='regist'),
    path('', views.index, name='index1'),
    path('index/', views.index, name='index'),
    path('logout/', views.logout, name='logout')
]
