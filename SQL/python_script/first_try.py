import pymysql

db = pymysql.connect("localhost", "test", "fanqiliang123456", "exercise")

cursor = db.cursor()

#cursor.execute("SELECT VERSION();SELECT DATABASE();")
cursor.execute("SELECT DATABASE();")

data = cursor.fetchone()

cursor.execute("SELECT * FROM advisor;")

data1 = cursor.fetchall()

print("Database version: %s " % data)

for i in range(0,2000,1):
    print("What we get: %10s %10s" % (data1[i][0], data1[i][1]))

db.close()
