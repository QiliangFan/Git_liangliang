import pymysql

db = pymysql.connect('localhost', 'test', 'fanqiliang123456', 'exercise', 3306);
cur = db.cursor()

cur.execute('''drop procedure if exists course_search;''')
cur.execute('''
create procedure course_search(in deptname varchar(20))
    deterministic
    reads sql data
begin
        with recursive pre_table(pre_id) as (
        select  prereq_id
        from prereq join course on(course.course_id=prereq.course_id)
        where dept_name=deptname
#         select course_id
#         from course
#         where deptname=dept_name
        union
        select prereq_id
        from prereq join pre_table on(prereq.course_id=pre_id)

        )select * from pre_table;

end;
''')

cur.execute('call course_search("Mech. Eng.");')

data = cur.fetchall()

for d in data:
    print(d)
