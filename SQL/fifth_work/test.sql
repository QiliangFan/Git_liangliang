create temporary table if not exists result(
        id varchar(8) not null
     );
drop function if exists fun;
delimiter $$ create function fun(c_id varchar(8)) returns integer deterministic reads sql data
begin
    declare i integer;

    insert into result
    values ((select distinct prereq_id
        from prereq
        where course_id=c_id
        ) );
    select count(*) into i
    from result;
    return i;
end$$
delimiter ;


select * from result;
drop temporary table IF EXISTS result;

