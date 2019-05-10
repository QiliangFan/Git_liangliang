import pymysql


def main():
    db = pymysql.connect("127.0.0.1", "test", "fanqiliang123456", "exercise")
    cur = db.cursor()
    cur.execute('''
select * from mylog3;''')
    data = cur.fetchall()
    for d in data:
        print(d)
    db.close()


if __name__ == "__main__":
    main()
