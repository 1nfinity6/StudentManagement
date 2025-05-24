package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import raisetech.student.management.entity.Status;
import raisetech.student.management.entity.Student;
import raisetech.student.management.entity.StudentCourse;
import raisetech.student.management.repository.mybatis.StudentRepository;

@MybatisTest
@MapperScan("raisetech.student.management.repository.mybatis")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.execute("DELETE FROM application_statuses");
    jdbcTemplate.execute("DELETE FROM students_courses");
    jdbcTemplate.execute("DELETE FROM students");
  }

  @Test
  void 受講生の全件検索が行えること() {
    Student testStudent = registerTestStudent("example@example.com");
    List<Student> actual = sut.search();
    assertThat(actual).extracting("id").contains(testStudent.getId());
  }

  @Test
  void IDで受講生を検索できること() {
    Student student = registerTestStudent("test@example.com");
    Student result = sut.searchStudent(student.getId());

    assertThat(result).isEqualTo(student);
  }

  @Test
  void 存在しないIDで受講生を検索したらnullが返ること() {
    Student result = sut.searchStudent("nonexistent-id");
    assertThat(result).isNull();
  }

  @Test
  void 受講生コースの全件検索ができること() {
    Student student = registerTestStudent("coursecheck@example.com");
    registerTestCourse(student.getId(), "Javaベーシック", Status.TEMPORARY);

    List<StudentCourse> courses = sut.searchStudentCourseList();
    assertThat(courses).isNotNull();
    assertThat(courses.size()).isGreaterThan(0);

    StudentCourse first = courses.get(0);
    assertThat(first.getStartAt()).isBefore(first.getEndAt());
  }

  @Test
  void 存在しないIDで受講生コース情報を検索すると空リストが返ること() {
    List<StudentCourse> result = sut.searchStudentCourse("nonexistent-id");
    assertThat(result).isEmpty();
  }

  @Test
  void 受講生IDで受講生コース情報を検索できること() {
    Student student = registerTestStudent("test@example.com");

    LocalDateTime fixedStart = LocalDateTime.of(2022, 3, 2, 0, 0);
    LocalDateTime fixedEnd = LocalDateTime.of(2022, 7, 1, 0, 0);
    Status status = Status.TEMPORARY;

    StudentCourse expected = new StudentCourse(
        UUID.randomUUID().toString(),
        "AWSアドバンス",
        fixedStart,
        fixedEnd,
        student.getId(),
        status
    );
    sut.registerStudentCourse(expected);

    List<StudentCourse> results = sut.searchStudentCourse(student.getId());

    assertThat(results).isNotEmpty();
    StudentCourse result = results.get(0);
    
    assertThat(result).isEqualTo(expected);
  }


  @Test
  void 各受講ステータスで登録と検索が行えること() {
    Student student = registerTestStudent("statuscheck@example.com");

    for (Status status : Status.values()) {
      String uniqueCourse = "コース_" + status.name() + "_" + UUID.randomUUID();
      registerTestCourse(student.getId(), uniqueCourse, status);
    }

    List<StudentCourse> results = sut.searchStudentCourse(student.getId());

    assertThat(results).hasSize(Status.values().length);
    assertThat(results).extracting("status")
        .containsExactlyInAnyOrder((Object[]) Status.values());
  }

  @Test
  void 受講生を登録できること() {
    Student student = registerTestStudent("test@example.com");
    Student result = sut.searchStudent(student.getId());

    assertThat(result).isEqualTo(student);
  }


  @Test
  void 受講生コース情報を登録できること() {
    String uniqueEmail = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(uniqueEmail);

    String uniqueCourseName = "Pythonベーシック" + UUID.randomUUID();
    StudentCourse course = registerTestCourse(student.getId(), uniqueCourseName, Status.TEMPORARY);

    List<StudentCourse> results = sut.searchStudentCourse(student.getId());
    assertThat(results).extracting("courseName").contains(uniqueCourseName);
  }

  @Test
  void 受講生を更新できること() {
    String email = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(email);

    student.setNickname("こーじ");
    sut.updateStudent(student);

    Student updated = sut.searchStudent(student.getId());
    assertThat(updated.getNickname()).isEqualTo("こーじ");
  }

  @Test
  void 受講生コース情報を更新できること() {
    String email = "test+" + UUID.randomUUID() + "@example.com";
    Student student = registerTestStudent(email);

    String initialCourseName = "旧Javaベーシック" + UUID.randomUUID();
    StudentCourse course = registerTestCourse(student.getId(), initialCourseName, Status.TEMPORARY);

    course.setCourseName("新Javaベーシック");
    sut.updateStudentCourse(course);

    List<StudentCourse> results = sut.searchStudentCourse(student.getId());
    assertThat(results).extracting("courseName").contains("新Javaベーシック");
  }

  @Test
  void 受講生コースのステータスを更新できること() {
    Student student = registerTestStudent("statusupdate@example.com");
    StudentCourse course = registerTestCourse(student.getId(), "AWS", Status.TEMPORARY);

    course.setStatus(Status.COMPLETED);
    sut.updateStudentCourse(course);

    List<StudentCourse> updated = sut.searchStudentCourse(student.getId());
    assertThat(updated.get(0).getStatus()).isEqualTo(Status.COMPLETED);
  }

  // テスト用の受講生データを登録
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

  // テスト用のコースデータを登録
  private StudentCourse registerTestCourse(String studentId, String courseName, Status status) {
    LocalDateTime fixedStart = LocalDateTime.of(2022, 3, 2, 0, 0);
    LocalDateTime fixedEnd = LocalDateTime.of(2022, 7, 1, 0, 0);

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