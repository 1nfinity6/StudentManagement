CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(15),
    kanaName VARCHAR(25),
    nickname VARCHAR(15),
    region VARCHAR(35),
    gender ENUM('男', '女', 'その他'),
    age INT NOT NULL,
    email VARCHAR(30),
    remark VARCHAR(255),
    deleted TINYINT(1)
);

CREATE TABLE IF NOT EXISTS students_courses (
    id VARCHAR(36) PRIMARY KEY,
    course_name VARCHAR(20),
    course_start_at DATETIME,
    course_end_at DATETIME,
    student_id VARCHAR(36) NOT NULL,
    status_id INT NOT NULL DEFAULT 1,
    status ENUM('仮申込', '本申込', '受講中', '受講修了') NOT NULL DEFAULT '仮申込',
    FOREIGN KEY (student_id) REFERENCES students(id)
);
