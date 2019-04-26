select t.ID,course.title,grade
from 
(
    select *
    from takes
    where takes.grade like 'A%'
) t,student,course
where (
        course.course_id=t.course_id
)