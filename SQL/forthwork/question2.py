#!/usr/bin/python
# -*-coding:utf-8-*-
import pymysql

db = pymysql.connect('127.0.0.1', 'root', 'fanqiliang123456', 'exercise', 3306)
cursor = db.cursor()
order = 1
count = 1

cursor.execute('''
select name,salary
from instructor
order by salary;
'''.replace('\n', ' '))
data = cursor.fetchall()
data = list(data)
for i in range(0, len(data)):
    data[i] = list(data[i])
    if i == 0:
        data[i].append(count)
        order += 1
    else:
        if data[i][len(data[i])-1] > data[i - 1][len(data[i-1])-1]:
            count += 1
        data[i].append(count)
        order += 1
    data[i] = tuple(data[i])
data = tuple(data)
for i in range(0, len(data)):
    print(data[i])

print(len(data))
