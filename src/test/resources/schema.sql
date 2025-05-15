CREATE TABLE IF NOT EXISTS students
(
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   kanaName VARCHAR(100) NOT NULL,
   nickname VARCHAR(100),
   region VARCHAR(100),
   gender VARCHAR(10),
   age INT NOT NULL,
   email VARCHAR(100) NOT NULL,
   remark varchar(255),
   deleted boolean
);

CREATE TABLE IF NOT EXISTS students_courses
(
   id INT AUTO_INCREMENT PRIMARY KEY,
   course_name VARCHAR(100) NOT NULL,
   course_start_at TIMESTAMP,
   course_end_at TIMESTAMP,
   student_id INT NOT NULL
);