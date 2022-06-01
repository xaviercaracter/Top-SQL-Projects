/* Put your final project reporting queries here */
USE cs_hu_310_final_project;
-- 1. Calculate the GPA for students given a student_id (use student_id=1)
SELECT  
 students.first_name,
 students.last_name,
 count(class_registrations.student_id) AS number_of_classes,
 sum(convert_to_grade_point(grades.letter_grade)) AS total_grade_points_earned,
 sum(convert_to_grade_point(grades.letter_grade))/count(convert_to_grade_point(grades.letter_grade)) AS GPA
FROM class_registrations
INNER JOIN students ON students.student_id = class_registrations.student_id
INNER JOIN grades ON grades.grade_id  = class_registrations.grade_id
WHERE students.student_id = 1
GROUP BY students.student_id;

-- 2. Calculate the GPA for each student (across all classes and all terms
SELECT  
 students.first_name,
 students.last_name,
 count(class_registrations.student_id) AS number_of_classes,
 sum(convert_to_grade_point(grades.letter_grade)) AS total_grade_points_earned,
 sum(convert_to_grade_point(grades.letter_grade))/count(convert_to_grade_point(grades.letter_grade)) AS GPA
FROM class_registrations
INNER JOIN students ON students.student_id = class_registrations.student_id
INNER JOIN grades ON grades.grade_id  = class_registrations.grade_id
GROUP BY students.student_id;

-- 3. Calculate the avg GPA for each class
SELECT  
 classes.code,
 classes.name,
 count(class_registrations.class_section_id) AS number_of_grades,
 sum(convert_to_grade_point(grades.letter_grade)) AS total_grade_points,
 sum(convert_to_grade_point(grades.letter_grade))/count(convert_to_grade_point(grades.letter_grade)) AS AVG_GPA
FROM class_sections
INNER JOIN classes ON classes.class_id = class_sections.class_id
INNER JOIN class_registrations ON class_registrations.class_section_id = class_sections.class_section_id
INNER JOIN grades ON grades.grade_id  = class_registrations.grade_id
GROUP BY classes.class_id;

-- 4. Calculate the average GPA for each class and term
SELECT  
 classes.code AS code,
 classes.name AS name,
 terms.name AS term,
 count(class_registrations.class_section_id) AS number_of_grades,
 sum(convert_to_grade_point(grades.letter_grade)) AS total_grade_points,
 sum(convert_to_grade_point(grades.letter_grade))/count(convert_to_grade_point(grades.letter_grade)) AS AVG_GPA
FROM class_sections
INNER JOIN classes ON classes.class_id = class_sections.class_id
LEFT JOIN class_registrations ON class_registrations.class_section_id = class_sections.class_section_id
LEFT JOIN grades ON grades.grade_id  = class_registrations.grade_id
INNER JOIN terms on terms.term_id = class_sections.term_id
GROUP BY classes.class_id ,class_sections.term_id
ORDER BY class_sections.term_id, classes.name;

-- 5. List all the classes being taught by an instructor (use instructor_id=1)
SELECT 
 instructors.first_name,
 instructors.last_name,
 academic_titles.title,
 classes.code,
 classes.name,
 terms.name AS term
FROM class_sections 
INNER JOIN terms ON class_sections.term_id = terms.term_id
INNER JOIN classes ON classes.class_id = class_sections.class_id
INNER JOIN instructors ON instructors.instructor_id = class_sections.instructor_id
INNER JOIN academic_titles ON instructors.academic_title_id = academic_titles.academic_title_id
WHERE instructors.instructor_id = 1;

-- 6. List all classes with terms & instructor
SELECT 
 classes.code,
 classes.name,
 terms.name AS term,
 instructors.first_name,
 instructors.last_name
FROM class_sections 
INNER JOIN terms ON class_sections.term_id = terms.term_id
INNER JOIN classes ON classes.class_id = class_sections.class_id
INNER JOIN instructors ON instructors.instructor_id = class_sections.instructor_id;

-- 7. Calculate the remaining space left in a class
SELECT  
 classes.code AS code,
 classes.name AS name,
 terms.name AS term,
 count(class_registrations.class_section_id) AS enrolled_students,
 classes.maximum_students - count(class_registrations.class_section_id) AS space_remaining
FROM class_sections
INNER JOIN classes ON classes.class_id = class_sections.class_id
INNER JOIN class_registrations ON class_registrations.class_section_id = class_sections.class_section_id
INNER JOIN terms on terms.term_id = class_sections.term_id
GROUP BY classes.class_id ,class_sections.term_id
ORDER BY class_sections.term_id, classes.class_id;