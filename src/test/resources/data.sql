SET REFERENTIAL_INTEGRITY FALSE;
DELETE FROM application_statuses;
DELETE FROM students_courses;
DELETE FROM students;
SET REFERENTIAL_INTEGRITY TRUE;

-- students テーブル--
INSERT INTO students (id, name, kanaName, nickname, region, gender, age, email) VALUES
('uuid-001', '佐藤智美', 'サトウトモミ', 'ともみ', '埼玉県川口市', '女', 40, 'tomomi@example.com'),
('uuid-002', '我妻敬佑', 'アズマケイスケ', 'あず', '山梨県甲府', '男', 25, 'keisuke@example.com'),
('uuid-003', '布施亮平', 'フセリョウヘイ', 'りょうへい', '石川県金沢市', '男', 29, 'ryouhei@example.com'),
('uuid-004', '水間なみ', 'ミズマナミ', 'なみ', '北海道札幌市', '女', 33, 'nami@example.com'),
('uuid-005', '深水玄喜', 'フカミゲンキ', 'げんちゃん', '沖縄県那覇市', '男', 34, 'genki@example.com'),
('uuid-006', '戸倉優希', 'トクラユキ', 'とくら', '神奈川県横浜市', 'その他', 37, 'tokura@example.com');

--students_courses テーブル--
INSERT INTO students_courses (id, course_name, course_start_at, course_end_at, student_id) VALUES
('sc-001', 'Javaベーシック', '2020-04-01 00:00:00', '2020-08-30 00:00:00', 'uuid-001'),
('sc-002', 'Javaスタンダード', '2021-07-08 00:00:00', '2021-10-30 00:00:00', 'uuid-002'),
('sc-003', 'Pythonベーシック', '2025-04-10 00:00:00', '2025-09-18 00:00:00', 'uuid-004'),
('sc-004', 'Pythonスタンダード', '2023-10-26 00:00:00', '2023-12-01 00:00:00', 'uuid-005'),
('sc-005', 'Pythonベーシック', '2019-10-20 00:00:00', '2019-12-01 00:00:00', 'uuid-006'),
('sc-006', 'AWSアドバンス', '2022-03-02 00:00:00', '2022-07-01 00:00:00', 'uuid-006');

-- application_statuses に合わせた ID--
INSERT INTO application_statuses (student_course_id, status) VALUES
('sc-001', '仮申込'),
('sc-002', '本申込'),
('sc-003', '受講中'),
('sc-004', '受講修了'),
('sc-005', '仮申込'),
('sc-006', '本申込');
