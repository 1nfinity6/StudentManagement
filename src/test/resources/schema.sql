CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100),
    kanaName VARCHAR(100),
    nickname VARCHAR(100),
    region VARCHAR(100),
    gender VARCHAR(10),
    age INT NOT NULL,
    email VARCHAR(100),
    remark VARCHAR(255),
    deleted BOOLEAN
);

CREATE TABLE IF NOT EXISTS students_courses (
    id VARCHAR(36) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_start_at TIMESTAMP,
    course_end_at TIMESTAMP,
    student_id VARCHAR(36) NOT NULL,
    status VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

CREATE TABLE IF NOT EXISTS application_statuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_course_id VARCHAR(36) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (student_course_id) REFERENCES students_courses(id)
);
