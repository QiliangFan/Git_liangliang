import pymysql

db = pymysql.connect('localhost', 'test', 'fanqiliang123456', 'exercise', 3306);
cur = db.cursor()

cur.execute('''drop procedure pre_search;''')
cur.execute('''
create procedure pre_search(in course varchar(8))
    deterministic
    reads sql data
begin
    with recursive pre_table(pre_id) as (
        select prereq_id
        from prereq
        where course_id=course
        union
        select prereq_id
        from prereq,pre_table
        where prereq.course_id=pre_id
    )
    select *
    from pre_table;
end;

''')

cur.execute('call pre_search(362);')

data = cur.fetchall()

for d in data:
    print(d)
