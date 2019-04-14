#-*-coding:utf-8-*-
import pymysql
import io
import sys
sys.stdout = io.TextIOWrapper(sys.stdout.buffer,encoding='utf8')  
db = pymysql.connect("127.0.0.1", "test", "fanqiliang123456", "exercise",charset='utf8')
cursor = db.cursor()


def first_problem():
    first_problem = '''
    select dept_name,count(*)
    from student
    group by dept_name
    order by dept_name DESC
    '''
    first_problem=first_problem.replace('\n', '')
    cursor.execute(first_problem)
    first_problem_data = cursor.fetchall()
    for i in range(0, len(first_problem_data)):
        print(first_problem_data[i][0], first_problem_data[i][1])


def second_problem():
    second_problem = '''
    select takes.course_id,course.title,semester,year
    from course,takes
    where(
        course.course_id=takes.course_id
        and
        takes.course_id=course.course_id
    )
    group by takes.course_id,year,semester
    having count(distinct takes.ID)>=310
    '''
    second_problem=second_problem.replace('\n', '')
    cursor.execute(second_problem)
    second_problem_data = cursor.fetchall()
    for i in range(0, len(second_problem_data)):
        print(second_problem_data[i][0], second_problem_data[i][1]
              [0:5], second_problem_data[i][2], second_problem_data[i][3])


def third_problem():
    third_problem = '''
    select distinct ID,name
    from (
        select distinct student.ID,student.name
        from student,takes
        where (
        student.ID=takes.ID
        and
        takes.course_id in ( 
                select distinct course_id 
                from teaches 
                where (
                    teaches.ID = (
                    select distinct instructor.ID
                    from instructor
                    where instructor.name='Dale'
                    )
                    and
                    takes.year=teaches.year
                    and
                    takes.semester=teaches.semester
                )
            )
        )
    ) t;
    '''
    third_problem=third_problem.replace('\n', ' ')
    cursor.execute(third_problem)
    third_problem_data=cursor.fetchall()
    for i in range (0,len(third_problem_data)):
        print(third_problem_data[i][0],third_problem_data[i][1].encode('utf-8').decode('utf-8'))

def forth_problem():
    forth_problem='''
    select distinct ID,course_id,grade
    from takes
    where (
        ID not in(
            select t.ID
            from takes t
            where (
                t.grade not like 'A%'
            )
        )
    )
    '''
    forth_problem=forth_problem.replace('\n',' ')
    cursor.execute(forth_problem)
    forth_problem_data=cursor.fetchall()
    for i in range(0,len(forth_problem_data)):
        print(forth_problem_data[i])

#def fifth_problem():


def fifth_problem():
    fifth_problem='''
        select avg(budget) avg_budget
        from department
    '''
    fifth_problem=fifth_problem.replace('\n',' ')
    cursor.execute(fifth_problem)
    fifth_problem_data=cursor.fetchall()
    average_number=fifth_problem_data[0][0]
    fifth_problem_1='''
        select budget
        from department
        order by budget ASC
    '''
    fifth_problem_1=fifth_problem_1.replace('\n',' ')
    cursor.execute(fifth_problem_1)
    budget=cursor.fetchall()
    size=len(budget)
    mid_number=(budget[(size-1)//2][0]+budget[(size)//2][0])/2
    print("average: ",average_number)
    print("median: ",mid_number)


print("FIRST\n*************************************\n")
first_problem()
print("SECOND\n************************************\n")
second_problem()
print("THIRD\n************************************\n")
third_problem()
print("FORTH\n************************************\n")
forth_problem()
print("FIFTH\n************************************\n")
fifth_problem()






