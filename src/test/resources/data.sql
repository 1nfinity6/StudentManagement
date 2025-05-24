SET REFERENTIAL_INTEGRITY FALSE;
DELETE FROM application_statuses;
DELETE FROM students_courses;
DELETE FROM students;
SET REFERENTIAL_INTEGRITY TRUE;


-- students テーブル
INSERT INTO students (id, name, kanaName, nickname, region, gender, age, email, remark, deleted) VALUES
('1', '佐藤智美', 'サトウトモミ', 'ともみ', '埼玉県川口市', '女', 40, 'Tomomi@example.com', '', 0),
('19', '江並公史', 'エナミコウジ', 'こーじ', '奈良県奈良市', 'その他', 36, 'kouji@exaple.com', '', 1),
('2', '我妻敬佑', 'アズマケイスケ', 'あず', '山梨県甲府', '男', 25, 'keisuke@example.com', NULL, 0),
('21', '江並太郎', 'エナミタロウ', 'こーじ', '奈良県奈良市', '男', 36, 'kouji@exaple.com', '', 0),
('22', '江並康介', 'エナミタロウ', 'こーじ', '奈良県奈良市', '男', 36, 'kouji@exaple.com', '', 0),
('4', '布施亮平', 'フセリョウヘイ', 'りょうへい', '石川県金沢市', '男', 29, 'ryouhei@example.com', NULL, 0),
('5', '水間なみ', 'ミズマナミ', 'なみ', '北海道札幌市', '女', 33, 'nami@example.com', '', 0),
('6', '深水玄喜', 'フカミゲンキ', 'げんちゃん', '沖縄県那覇市', '男', 34, 'genki@example.com', '', 0),
('7', '戸倉優希', 'トクラユキ', 'とくら', '神奈川県横浜市', 'その他', 37, 'tokura@example.com', '', 1),
('stu001', 'テスト', 'テスト', 'テスト', '東京', '男', 25, 'test@example.com', '', 0);

-- students_courses テーブル
INSERT INTO students_courses (id, course_name, course_start_at, course_end_at, student_id, status_id, status) VALUES
('10', 'AWSアドバンス', '2025-04-30 14:26:40', '2026-04-30 14:26:40', '22', 1, '仮申込'),
('2', 'Javaスタンダード', '2021-07-08 00:00:00', '2021-10-30 00:00:00', '2', 1, '仮申込'),
('4', 'Javaベーシック', '2025-04-10 00:00:00', '2025-09-18 00:00:00', '4', 1, '仮申込'),
('5', 'Pythonスタンダード', '2023-10-26 00:00:00', '2023-12-01 00:00:00', '5', 1, '仮申込'),
('6', 'Pythonベーシック', '2019-10-20 00:00:00', '2019-12-01 00:00:00', '6', 1, '仮申込'),
('7', 'AWSアドバンス', '2022-03-02 00:00:00', '2022-07-01 00:00:00', '7', 1, '仮申込'),
('8', 'Javaスタンダード', '2025-04-16 14:43:04', '2026-04-16 14:43:04', '19', 1, '仮申込'),
('9', 'AWSアドバンス', '2025-04-30 14:21:49', '2026-04-30 14:21:49', '21', 1, '仮申込'),
('course001', 'Javaベーシック', '2024-04-01 00:00:00', '2024-08-01 00:00:00', 'stu001', 1, '仮申込');
