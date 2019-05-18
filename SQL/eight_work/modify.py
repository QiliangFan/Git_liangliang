import pymysql
import os
import shutil

def exe(db,cursor,root):
    i = 0
    rootDir = 'H:\\sqlite-autoconf-3230100'
    dirs = os.listdir(rootDir)
    cursor.execute(
        "insert into file(groupId, name, sub_groupId, size) values(%s,\'%s\',%s,%s);" % (i, rootDir, i + 1, len(dirs)))

    i += 1
    for (dirName, dirs, files) in os.walk(rootDir):
        for fileName in files:
            filePath = os.path.join(dirName, fileName)
            fileSize = os.path.getsize(filePath)
            cursor.execute('''
                    insert into file values
                    (%s,\'%s\',%s,%s)''' % (i, fileName, i + 1, fileSize))
        for dirsName in dirs:
            dirPath = os.path.join(dirName, dirsName)
            sub_dirs = os.listdir(dirPath)
            cursor.execute('''
                    insert into file values
                    (%s,\'%s\',%s,%s)''' % (i, dirsName, i + 1, len(sub_dirs)))
        i += 1
    db.commit()
    cursor.execute("select * from file;")
    db.commit()
    data = cursor.fetchall()
    for d in data:
        for i in range(0, d[0]):
            print("", end="             ")
        print(str(d[1]) + ":" + str(d[3]))
    db.close()


def main():
    db = pymysql.connect("127.0.0.1", "test", "fanqiliang123456", "exercise")
    cursor = db.cursor()
    os.rename('H:\\sqlite-autoconf-3230100\\tea\\win\\makefile.vc','H:\\sqlite-autoconf-3230100\\tea\\win\\mymakefile.vc')
    cursor.execute('''update file
    set name='mymakefile.vc'
    where groupId=3 and name='makefile.vc';''')
    shutil.move("H:\\sqlite-autoconf-3230100\\tea\\win","H:\\sqlite-autoconf-3230100\\tea\\doc")
    exe(db,cursor,)
    db.commit()
    db.close()


if __name__ == "__main__":
    main()
