drop function if exists findAllPrereqs;
DELIMITER $$
create function findAllPrereqs(cid varchar(8))
returns integer
deterministic
reads sql data
begin
drop temporary table if exists new_;
drop temporary table if exists temp;
create temporary table new_(course_id varchar(8));#包含上一次迭代中发现的新课程
create temporary table temp(course_id varchar(8));#用于存储中间结果

insert into new_
	select prereq_id
    from prereq
    where course_id=cid;#找出cid的直接先导课程
    
repeat
	insert into c_prereq
		select course_id
		from new_;#把开始或者上一次迭代中发现的新课程加入表中
	insert into temp
		(select prereq.prereq_id
        from new_,prereq
        where new_.course_id=prereq.course_id
        and prereq.prereq_id not in(
        select course_id
        from c_prereq
        ));#找出new_表中课程的先导课程
		delete from new_;#清空new_
        insert into new_
			select*
            from temp;#把temp中的值赋值到new_中
		delete from temp;#清空temp
        until not exists(select*from new_)
end repeat;
return 0;
end$$
DELIMITER ;

drop table if exists c_prereq;
create table c_prereq(course_id varchar(8));
select findAllPrereqs(362);