#!/usr/bin/python3
# -*-:coding:utf-8-*-

import pymysql

db = pymysql.connect("127.0.0.1", "test", "fanqiliang123456", "exercise")

cur = db.cursor()

