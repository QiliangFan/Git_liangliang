
drop table if exists pre_table;
drop procedure if exists pre_search;

create procedure pre_search(in course varchar(8))
    deterministic
    reads sql data
begin
    with recursive pre_table(pre_id) as (
        select prereq_id
        from prereq
        where course_id=course
        union
        select prereq_id
        from prereq,pre_table
        where prereq.course_id=pre_id
    )
    select *
    from pre_table;
end;


call pre_search(362);