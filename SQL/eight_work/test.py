import pymysql
import os
import shutil
db = pymysql.connect("127.0.0.1", "test", "fanqiliang123456", "exercise")
cursor = db.cursor()

def modify():
    # os.rename("H:\\sqlite-autoconf-3230100\\tea\\win\\makefile.vc",
    #           "H:\\sqlite-autoconf-3230100\\tea\\win\\mymakefile.vc")
    # shutil.move("H:\\sqlite-autoconf-3230100\\tea\\win",
    #             "H:\\sqlite-autoconf-3230100\\tea\\doc")
    cursor.execute("select * from file where TYPE=1 and size>=2")
    db.commit()
    data=cursor.fetchall()
    for d in data:
        print(d)
    for (dirName,dirs,files) in os.walk("H:\\sqlite-autoconf-3230100\\tea"):
        for f in files:
            os.remove(os.path.join(dirName,f))
    shutil.rmtree("H:\\sqlite-autoconf-3230100\\tea")
    exe("H:\\sqlite-autoconf-3230100", 0)


def Print(data, order, last):
    if data is None:
        return
    for d in data:
        if d[0] != order:
            continue
        for i in range(0, d[0]-1):
            print("", end="        ")
        if d[0] != 0:
            print("", end="└-------")
        # print("", end="--------")
        print(str(d[1]) + ":" + str(d[3]))

        if d[5] == 1:
            cursor.execute("select * from file where parentDir=\'%s\'" % (d[1],))
            _data = cursor.fetchall()
            if _data is not None:
                Print(_data, order + 1, order)


def exe(rootdir, i):
    rootDir = rootdir
    dirs = os.listdir(rootDir)
    cursor.execute(
        "insert into file(groupId, name, sub_groupId, size,parentDir,TYPE) values(%s,\'%s\',%s,%s,\'%s\',%d);" % (
            i, os.path.basename(rootDir), i + 1, len(dirs),os.path.basename(rootDir) , 1))
    i += 1
    sign=i-1
    for (dirName, dirs, files) in os.walk(rootDir):
        if i==sign+1:
            pass
        else:
            i=sign+1
        if len(dirs) == 0 :
            pass
        else:
            sign+=1
        for fileName in files:
            filePath = os.path.join(dirName, fileName)
            fileSize = os.path.getsize(filePath)
            cursor.execute('''
                    insert into file values
                    (%s,\'%s\',%s,%s,\'%s\',%d)''' % (i, fileName, i + 1, fileSize, os.path.basename(dirName), 0))
        for dirsName in dirs:
            dirPath = os.path.join(dirName, dirsName)
            sub_dirs = os.listdir(dirPath)
            cursor.execute('''
                    insert into file values
                    (%s,\'%s\',%s,%s,\'%s\',%d)''' % (i, dirsName, i + 1, len(sub_dirs), os.path.basename(dirName), 1))
    db.commit()
    cursor.execute("select * from file;")
    data = cursor.fetchall()
    last = 0
    Print(data, 0, 0)



def main():
    exe('H:\\sqlite-autoconf-3230100', 0)
    cursor.execute("delete from file")
    db.commit()
    modify()


if __name__ == "__main__":
    main()
