package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.entity.Status;
import raisetech.student.management.entity.StudentCourse;
import raisetech.student.management.repository.mybatis.StudentRepository;

/**
 * StudentCourseService は、受講生のコース情報を管理するサービスクラスです。
 */
@Service
public class StudentCourseService {

  private final StudentRepository studentRepository;

  /**
   * コンストラクタ。StudentCourseRepositoryとStudentRepositoryを注入します。
   *
   * @param studentRepository StudentCourseの永続化操作用リポジトリ
   * @param studentRepository Studentの永続化操作用リポジトリ
   */
  @Autowired
  public StudentCourseService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }


  /**
   * コース情報の初期化を行います。
   *
   * @param course    コース情報
   * @param studentId 学生ID
   */
  public void initializeCourse(StudentCourse course, String studentId) {
    LocalDateTime now = LocalDateTime.now();
    course.setStudentId(studentId);
    course.setStartAt(now);
    course.setEndAt(now.plusYears(1));
  }

  /**
   * 複数の受講生コース情報を登録します。
   *
   * @param studentId 学生ID
   * @param courses   受講生コース情報リスト
   */
  @Transactional
  public void registerCourses(String studentId, List<StudentCourse> courses) {
    for (StudentCourse course : courses) {
      initializeCourse(course, studentId);
      studentRepository.registerStudentCourse(course);
    }
  }

  /**
   * 受講生コース情報を更新します。
   *
   * @param courses 更新する受講生コースリスト
   */
  @Transactional
  public void updateCourses(List<StudentCourse> courses) {
    for (StudentCourse course : courses) {
      studentRepository.updateStudentCourse(course);
    }
  }

  /**
   * 指定された受講生コースIDのステータスを更新します。 該当する受講生コースが存在しない場合は IllegalArgumentException をスローします。
   *
   * @param studentCourseId 更新対象の受講生コースID
   * @param status          新しいステータス情報
   * @throws IllegalArgumentException 指定した ID に該当するコースが存在しない場合
   */
  @Transactional
  public void updateStatus(String studentCourseId, Status status) {
    StudentCourse course = studentRepository.findById(studentCourseId)
        .orElseThrow(
            () -> new IllegalArgumentException("StudentCourse not found: " + studentCourseId));
    course.setStatus(status);
    studentRepository.updateStudentCourse(course);
  }

  /**
   * ステータスを登録（または更新）します。 該当する受講生コースが存在しない場合は IllegalArgumentException をスローします。
   *
   * @param studentCourseId 対象の受講生コースID
   * @param status          登録（更新）するステータス
   * @throws IllegalArgumentException 指定した ID に該当するコースが存在しない場合
   */
  @Transactional
  public void registerStatus(String studentCourseId, Status status) {
    StudentCourse course = studentRepository.findById(studentCourseId)
        .orElseThrow(
            () -> new IllegalArgumentException("StudentCourse not found: " + studentCourseId));
    course.setStatus(status);
    studentRepository.registerStudentCourse(course);
  }
}

