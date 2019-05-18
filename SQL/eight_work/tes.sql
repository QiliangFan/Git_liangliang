drop table if exists file;
create table file(
    groupId int,
    name varchar(100),
    sub_groupId int,
    size int,
    parentDir varchar(100),
    TYPE int,
    primary key (groupId,name)
);