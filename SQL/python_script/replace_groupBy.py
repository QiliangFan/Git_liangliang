import pymysql

db = pymysql.connect("localhost", "test", "fanqiliang123456", "exercise")

cur = db.cursor()

cur.execute('''SELECT dept_name,`AVG(salary)` AS avg_salary FROM  "
    ( 
        WITH temp_table AS (  
            SELCECT DISTINCT T.dept_name AS dept_name,B.AVG(salary) AS `AVG(salary)` 
            FROM instructor AS T,instructor AS B  
            WHERE B.dept_name=T.dept_name  
        ) 
    )  
''') 

data.fetch()


cur.executeAll()

