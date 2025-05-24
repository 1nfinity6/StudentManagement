package raisetech.student.management.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.entity.Status;
import raisetech.student.management.entity.Student;
import raisetech.student.management.entity.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細リストが作成できること() {
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setCourseName("Javaベーシック");
    studentCourse.setStartAt(LocalDateTime.now());
    studentCourse.setEndAt(LocalDateTime.now().plusYears(1));
    studentCourse.setStudentId("1");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
  }


  @Test
  void 受講生リストと受講生コース情報リストを渡した時に紐づかない受講生コース情報は除外されること() {
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setCourseName("Javaベーシック");
    studentCourse.setStartAt(LocalDateTime.now());
    studentCourse.setEndAt(LocalDateTime.now().plusYears(1));
    studentCourse.setStudentId("2");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEmpty();
  }

  @Test
  void 空リストを渡した場合は空の受講生詳細リストが返ること() {
    List<StudentDetail> actual = sut.convertStudentDetails(Collections.emptyList(),
        Collections.emptyList());
    assertThat(actual).isEmpty();
  }

  @Test
  void 複数受講生と複数コースが正しく紐づくこと() {
    Student student1 = createStudentWithId("1");
    Student student2 = createStudentWithId("2");

    StudentCourse course1 = createCourse("A", "Java", "1");
    StudentCourse course2 = createCourse("B", "Python", "2");

    List<StudentDetail> actual = sut.convertStudentDetails(
        List.of(student1, student2),
        List.of(course1, course2)
    );

    assertThat(actual).hasSize(2);
    assertThat(actual.get(0).getStudentCourseList()).contains(course1);
    assertThat(actual.get(1).getStudentCourseList()).contains(course2);
  }

  @Test
  void nullが渡された場合は空の受講生詳細リストが返ること() {
    List<StudentDetail> actual = sut.convertStudentDetails(null, null);
    assertThat(actual).isEmpty();
  }

  private static Student createStudent() {
    return createStudentWithId("1");
  }

  // 任意のIDでStudent作成
  private static Student createStudentWithId(String id) {
    Student student = new Student();
    student.setId(id);
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("こうじ");
    student.setRegion("奈良県奈良市");
    student.setGender("男");
    student.setAge(36);
    student.setEmail("koji@example.com");
    student.setRemark("");
    student.setDeleted(false);
    return student;
  }

  // コース生成の共通化
  private static StudentCourse createCourse(String id, String name, String studentId) {
    return new StudentCourse(
        id,
        name,
        LocalDateTime.now(),
        LocalDateTime.now().plusYears(1),
        studentId,
        Status.TEMPORARY
    );
  }
}
