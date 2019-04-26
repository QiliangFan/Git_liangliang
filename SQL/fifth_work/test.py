#!/usr/bin/python3
# -*-:coding:utf-8-*-

import pymysql

db = pymysql.connect("127.0.0.1", "test", "fanqiliang123456", "exercise")

cur = db.cursor()
cur.execute("drop function if exists fun;")
s = '''
create function  fun(c_id varchar(8)) returns integer 
    deterministic reads sql data 
begin 
    declare i integer default 0; 
    
    if exists(select * from prereq t where t.course_id=c_id) then 
    insert into result 
    select distinct t0.prereq_id 
        from prereq t0
        where t0.course_id=c_id ;
    end if;
    return i; 
end
'''.replace('\n', ' ')

cur.execute("create temporary table if not exists result(id varchar(8) );")
cur.execute(s)


def exec_fun(str_id):
    cur.execute("select fun(%s);", (str_id,))


def query(str_id):
    cur.execute("select prereq_id from prereq where course_id=%s;", (str_id,))
    data = cur.fetchall()
    return data


def getresult(str_id):
    if str_id is not None:
        exec_fun(str_id)
    data = query(str_id)
    for d in data:
        getresult(d[0])


s = input()
getresult(s)
cur.execute("select * from result;")
data = cur.fetchall()
print(data)
# for d in data:
#     print(d[0])
cur.execute("drop temporary table if exists result;")
cur.execute("drop function if exists fun;")
cur.close()
