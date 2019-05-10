drop table if exists instructor3;
drop table if exists mylog3;
drop trigger if exists insert_trigger_instructor3;
drop trigger if exists update_trigger_instructor3;
drop trigger if exists delete_trigger_instructor3;

create table instructor3 like instructor;
create table mylog3 (
    `ID` int auto_increment,
    instruct_id varchar(5),
    tbname varchar(20),
    colname varchar(20),
    event varchar(20),
    oldvalue varchar(20) ,
    newvalue varchar(20) ,
    date   time,
     primary key (ID)
);

create trigger insert_trigger_instructor3
    after insert on instructor3
    FOR EACH ROW
    begin
        insert into mylog3(instruct_id,tbname,colname,event,newvalue,date)
            values ( NEW.ID,'instructor3','name','insert', NEW.name,curtime());
        insert into mylog3(instruct_id, tbname, colname, event, newvalue,date)
            values (NEW.ID,'instructor3','dept_name','insert',NEW.dept_name,curtime());
        insert into mylog3(instruct_id, tbname, colname, event, newvalue,date)
            values (NEW.ID,'instructor3','salary','insert',NEW.salary,curtime());
    end;

create trigger update_trigger_instructor3
    after update on instructor3
    for each row
    begin
        if old.name != new .name then
        insert into mylog3(instruct_id,tbname,colname,event,oldvalue,newvalue,date)
            values ( NEW.ID,'instructor3','name','update',OLD.name, NEW.name,curtime());
        end if ;
        if old.dept_name != new.dept_name then
        insert into mylog3(instruct_id, tbname, colname, event, oldvalue,newvalue,date)
            values (NEW.ID,'instructor3','dept_name','update',OLD.dept_name,NEW.dept_name,curtime());
        end if;
        if old.salary != new.dept_name then
        insert into mylog3(instruct_id, tbname, colname, event, oldvalue,newvalue,date)
            values (NEW.ID,'instructor3','salary','update',old.salary,NEW.salary,curtime());
        end if;
    end;

create trigger delete_trigger_instructor3
    after delete on instructor3
    for each row
    begin
        insert into mylog3(instruct_id,tbname,colname,event,oldvalue,date)
            values ( NEW.ID,'instructor3','name','delete',OLD.name, curtime());
        insert into mylog3(instruct_id, tbname, colname, event, oldvalue,date)
            values (NEW.ID,'instructor3','dept_name','delete',OLD.dept_name,curtime());
        insert into mylog3(instruct_id, tbname, colname, event, oldvalue,date)
            values (NEW.ID,'instructor3','salary','delete',old.salary,curtime());
    end;

insert into instructor3
select *
from instructor
where instructor.ID>=concat(30000,'')
limit 5;

update instructor3
set salary=salary *2;


delete  from instructor3 ;
select * from mylog3;