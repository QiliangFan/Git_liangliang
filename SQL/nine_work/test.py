import pymysql


db = pymysql.connect("127.0.0.1","test","fanqiliang123456","exercise",charset="utf8")
cur = db.cursor()


db.commit()
db.close()
