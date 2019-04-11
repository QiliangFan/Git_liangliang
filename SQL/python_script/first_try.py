import pymysql
import time

db = pymysql.connect("localhost", "test", "fanqiliang123456", "exercise")


def runTime(s):
    start_time=time.time()
    cursor = db.cursor()
    cursor.execute(s)
    end_time=time.time()
    print("run-time is:%f"%(end_time-start_time))

s1='''
SELECT DISTINCT course_id
FROM section
WHERE semester="Fall" AND year=2018 AND 
      Course_id IN (SELECT course_id FROM section WHERE semester= "Spring" AND year=2019);
'''
s2='''
SELECT DISTINCT T1.course_id course_id
FROM section AS T1,section AS T2
WHERE T1.semester="Fall" AND T1.year=2018 AND T2.semester="Spring" AND T2.year=2019 AND T1.course_id=T2.course_id;
'''

s3='''
SELECT DISTINCT T1.course_id course_id
FROM section AS T1,section AS T2
WHERE T1.semester="Fall" AND T1.year=2018 AND T2.semester="Spring" AND T2.year=2019 AND T1.course_id=T2.course_id;
)
'''

runTime(s1)
runTime(s2)
db.close()
