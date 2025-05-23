package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.converter.StudentConverter;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.entity.Student;
import raisetech.student.management.entity.StudentCourse;
import raisetech.student.management.repository.mybatis.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;
  private Clock fixedClock;

  @BeforeEach
  void before() {
    fixedClock = Clock.fixed(Instant.parse("2024-01-01T10:00:00Z"), ZoneId.systemDefault());
    sut = new StudentService(repository, converter, fixedClock);
  }

  @Test
  void 受講生詳細の検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細の検索_空リストが返るときでもエラーなく動作すること() {
    when(repository.search()).thenReturn(new ArrayList<>());
    when(repository.searchStudentCourseList()).thenReturn(new ArrayList<>());
    when(converter.convertStudentDetails(new ArrayList<>(), new ArrayList<>()))
        .thenReturn(new ArrayList<>());

    List<StudentDetail> result = sut.searchStudentList();

    assertEquals(0, result.size());
  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること() {
    String id = "999";
    Student student = new Student();
    student.setId(id);
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(new ArrayList<>());

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);
    Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }

  @Test
  void 受講生詳細の登録_とリポジトリの処理が適切に呼び出されていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>(List.of(studentCourse));
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
  }

  @Test
  void 受講生詳細の登録_初期化処理が行われていること() {
    String id = "999";
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentsCourse(studentCourse, student.getId());

    LocalDateTime expectedStart = LocalDateTime.now(fixedClock);
    LocalDateTime expectedEnd = expectedStart.plusYears(1);

    assertEquals(id, studentCourse.getStudentId());
    assertEquals(expectedStart.getYear(), studentCourse.getStartAt().getYear());
    assertEquals(expectedEnd.getYear(), studentCourse.getEndAt().getYear());
  }

  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出されていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>(List.of(studentCourse));
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(studentDetail.getStudent());
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
  void 受講生詳細の登録_引数がnullなら例外が発生すること() {
    Assertions.assertThrows(NullPointerException.class, () -> sut.registerStudent(null));
  }
}
