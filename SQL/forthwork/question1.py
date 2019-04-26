#!/usr/bin/python
# -*-coding:utf-8-*-

import pymysql


def main():
    db = pymysql.connect('127.0.0.1', 'root', 'fanqiliang123456', 'exercise', 3306)
    cursor = db.cursor()
    cursor.execute('''
        drop view if exists departbasicview;
        ''')
    cursor.execute('''
        delete from department
        where dept_name = 'Soft'
        ''')
    cursor.execute('''
       create view departbasicview(name,build)
       as(
       select dept_name,building
       from department
       )
       ''')
    s = '''
    select *
    from departbasicview
    where name like 's%'
    '''
    cursor.execute(s)
    data1 = cursor.fetchall()

    cursor.execute('''
    insert into departbasicview(name)
    values ('Soft');
    ''')
    cursor.execute('''
    select *
    from departbasicview''')
    data2 = cursor.fetchall()

    cursor.execute('''
    insert into departbasicview(name)
    values ('Comp')''')
    cursor.execute('''
    select *
    from departbasicview''')
    data3 = cursor.fetchall()

    for i in range(0, len(data1)):
        print(data1[i])
    print(' ')
    for i in range(0,len(data2)):
        print(data2[i])
    print(' ')
    for i in range(0, len(data3)):
        print(data3[i])


if __name__ == '__main__':
    main()
