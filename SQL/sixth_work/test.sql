drop procedure if exists course_search;

#
create procedure course_search(in deptname varchar(20))
    deterministic
    reads sql data
begin
        with recursive pre_table(pre_id) as (
        select  prereq_id
        from prereq join course on(course.course_id=prereq.course_id)
        where dept_name=deptname
#         select course_id
#         from course
#         where deptname=dept_name
        union
        select prereq_id
        from prereq join pre_table on(prereq.course_id=pre_id)

        )select * from pre_table;

end;
