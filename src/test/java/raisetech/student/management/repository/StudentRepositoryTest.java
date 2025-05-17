package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.repository.MyBatis.StudentRepository;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual).hasSize(6);

    Student expected = new Student();
    expected.setName("佐藤智美");
    expected.setKanaName("サトウトモミ");
    expected.setNickname("ともみ");
    expected.setRegion("埼玉県川口市");
    expected.setGender("女");
    expected.setAge(40);
    expected.setEmail("tomomi@example.com");
    expected.setRemark(null);
    expected.setDeleted(false);

    Student actualStudent = sut.search().get(0);
    assertThat(actualStudent).isEqualTo(expected);
  }

  @Test
  void IDで受講生を検索できる() {
    Student student = registerTestStudent("test@example.com");
    Student result = sut.searchStudent(student.getId());

    assertThat(result.getName()).isEqualTo("テスト");
    assertThat(result.getKanaName()).isEqualTo("テスト");
    assertThat(result.getNickname()).isEqualTo("テスト");
    assertThat(result.getRegion()).isEqualTo("奈良県奈良市");
    assertThat(result.getGender()).isEqualTo("男");
    assertThat(result.getAge()).isEqualTo(30);
    assertThat(result.getEmail()).isEqualTo("test@example.com");
    assertThat(result.getRemark()).isEqualTo("");
    assertThat(result.isDeleted()).isFalse();
  }

  @Test
  void 受講生コースの全件検索ができる() {
    List<StudentCourse> courses = sut.searchStudentCourseList();

    assertThat(courses).isNotNull();
    assertThat(courses.size()).isGreaterThan(0);

    StudentCourse first = courses.get(0);
    assertThat(first.getCourseName()).isEqualTo("Javaベーシック");
    assertThat(first.getStudentId()).isNotNull();
    assertThat(first.getCourseStartAt()).isBefore(first.getCourseEndAt());
  }

  @Test
  void 学生IDで受講生コース情報を検索できる() {
    Student student = registerTestStudent("test@example.com");

    LocalDateTime fixedStart = LocalDateTime.of(2022, 3, 2, 0, 0);
    LocalDateTime fixedEnd = LocalDateTime.of(2022, 7, 1, 0, 0);
    String status = "本申込";

    StudentCourse expected = new StudentCourse(
        UUID.randomUUID().toString(),
        "AWSアドバンス",
        fixedEnd,
        fixedStart,
        student.getId(),
        status
    );
    sut.registerStudentCourse(expected);

    List<StudentCourse> results = sut.searchStudentCourse(student.getId());

    assertThat(results).isNotEmpty();
    StudentCourse result = results.get(0);

    assertThat(result.getCourseStartAt()).isEqualTo(fixedStart);
    assertThat(result.getCourseEndAt()).isEqualTo(fixedEnd);
    assertThat(result.getCourseName()).isEqualTo("AWSアドバンス");
  }

  @Test
  void 受講生IDで検索して受講生コース情報とステータスを取得できる() {
    String email = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(email);
    StudentCourse course = new StudentCourse(
        UUID.randomUUID().toString(),
        "Javaベーシック",
        LocalDateTime.of(2022, 3, 1, 0, 0),
        LocalDateTime.of(2022, 7, 1, 0, 0),
        student.getId(),
        "仮申込"
    );

    sut.registerStudentCourse(course);

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("studentCourseId", course.getId());
    paramMap.put("status", "仮申込");
    sut.registerApplicationStatus(paramMap);

    List<StudentCourse> courseList = sut.searchStudentCourse(student.getId());

    assertFalse(courseList.isEmpty());
    assertEquals("仮申込", courseList.get(0).getStatus());
    System.out.println("ステータス：" + courseList.get(0).getStatus());
  }

  @Test
  void 受講生を登録できる() {
    String uniqueEmail = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(uniqueEmail);
    assertThat(student.getId()).isNotNull();

    Student result = sut.searchStudent(student.getId());
    assertThat(result.getEmail()).isEqualTo(uniqueEmail);
  }

  @Test
  void 受講生コース情報を登録できる() {
    String uniqueEmail = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(uniqueEmail);

    String uniqueCourseName = "Pythonベーシック" + UUID.randomUUID();
    StudentCourse course = registerTestCourse(student.getId(), uniqueCourseName);

    List<StudentCourse> results = sut.searchStudentCourse(student.getId());
    assertThat(results).extracting("courseName").contains(uniqueCourseName);
  }

  @Test
  void 受講生を更新できる() {
    String email = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(email);

    student.setNickname("こーじ");
    sut.updateStudent(student);

    Student updated = sut.searchStudent(student.getId());
    assertThat(updated.getNickname()).isEqualTo("こーじ");
  }

  @Test
  void 受講生コース情報を更新できる() {
    String email = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(email);

    String initialCourseName = "旧Javaベーシック" + UUID.randomUUID();
    StudentCourse course = registerTestCourse(student.getId(), initialCourseName);

    course.setCourseName("新Javaベーシック");
    sut.updateStudentCourse(course);

    List<StudentCourse> results = sut.searchStudentCourse(student.getId());
    assertThat(results).extracting("courseName").contains("新Javaベーシック");
  }

  // テストデータ登録用ヘルパーメソッド
  private Student registerTestStudent(String email) {
    Student student = new Student(
        UUID.randomUUID().toString(),
        "テスト",
        "テスト",
        "テスト",
        "奈良県奈良市",
        "男",
        30,
        email,
        "",
        false
    );
    sut.registerStudent(student);
    return student;
  }

  private StudentCourse registerTestCourse(String studentId, String courseName) {
    LocalDateTime fixedStart = LocalDateTime.of(2022, 3, 2, 0, 0);
    LocalDateTime fixedEnd = LocalDateTime.of(2022, 7, 1, 0, 0);
    String status = "本申込";

    StudentCourse course = new StudentCourse(
        UUID.randomUUID().toString(),
        courseName,
        fixedStart,
        fixedEnd,
        studentId,
        status
    );
    sut.registerStudentCourse(course);
    return course;
  }
}
