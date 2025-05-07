package raisetech.student.management.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
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
  void 受講生の詳細検索_指定IDで受講生と受講生コース情報が取得できること() {
    Student student = new Student();
    student.setId("1");
    student.setName("田中");

    StudentCourse course = new StudentCourse();
    course.setCourseName("Javaベーシック");

    when(repository.searchStudent("1")).thenReturn(student);
    when(repository.searchStudentCourse("1")).thenReturn(List.of(course));

    StudentDetail result = sut.searchStudent("1");

    assertEquals("田中", result.getStudent().getName());
    assertEquals(1, result.getStudentCourseList().size());
    assertEquals("Javaベーシック", result.getStudentCourseList().get(0).getCourseName());
  }


  @Test
  void 受講生情報と受講正コース情報を登録_受講生とその受講コースの登録処理が正しく行われているか() {
    Student student = new Student();
    student.setId("1");
    student.setName("佐藤");

    StudentCourse course = new StudentCourse();
    course.setCourseName("Javaベーシック");

    StudentDetail detail = new StudentDetail(student, List.of(course));

    doNothing().when(repository).registerStudent(any());
    doNothing().when(repository).registerStudentCourse(any());

    StudentDetail result = sut.registerStudent(detail);

    assertEquals("佐藤", result.getStudent().getName());
    verify(repository).registerStudent(student);
    verify(repository).registerStudentCourse(any());
  }

  @Test
  void 受講生情報と受講生コース情報の更新_性別がMALEのとき日本語に変換されて保存されること() {
    Student student = new Student();
    student.setId("1");
    student.setGender("MALE");

    StudentCourse course = new StudentCourse();
    course.setCourseName("Javaベーシック");

    StudentDetail detail = new StudentDetail(student, List.of(course));

    doNothing().when(repository).updateStudent(any());
    doNothing().when(repository).updateStudentCourse(any());

    sut.updateStudent(detail);

    assertEquals("男", student.getGender());

    verify(repository).updateStudent(student);
    verify(repository).updateStudentCourse(course);
  }
}
