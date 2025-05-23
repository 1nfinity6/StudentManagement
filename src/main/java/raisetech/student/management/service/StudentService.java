package raisetech.student.management.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.converter.StudentConverter;
import raisetech.student.management.entity.Student;
import raisetech.student.management.entity.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.StudentSearchRequest;
import raisetech.student.management.repository.mybatis.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;
  private final Clock clock;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter, Clock clock) {
    this.repository = repository;
    this.converter = converter;
    this.clock = clock;
  }

  public StudentService(StudentRepository repository, StudentConverter converter) {
    this(repository, converter, Clock.systemDefaultZone());
  }

  /**
   * 受講生コース情報に初期設定（受講生ID、開始日、終了日）を行います。 Clockを使うことでテストでの時間制御が可能。
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 条件に基づいて受講生詳細を検索します。
   *
   * @param request 検索条件
   * @return 条件に合致する受講生詳細一覧。該当がなければ空リスト。
   */
  public List<StudentDetail> searchStudents(StudentSearchRequest request) {
    List<Student> students = repository.searchByConditions(request);

    if (students == null || students.isEmpty()) {
      return List.of();
    }

    List<String> studentIds = students.stream()
        .map(Student::getId)
        .toList();

    List<StudentCourse> courses = repository.searchStudentCourseListForStudents(studentIds);
    return converter.convertStudentDetails(students, courses);
  }

  /**
   * 指定されたIDに紐づく受講生詳細を取得します。
   *
   * @param id 受講生ID
   * @return 受講生詳細（該当がなければ例外、または null）
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    if (student == null) {
      return null;
    }

    List<StudentCourse> studentsCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentsCourse);
  }

  /**
   * 受講生詳細を新規登録します。
   *
   * @param studentDetail 登録対象の受講生詳細
   * @return 登録された受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);

    studentDetail.getStudentCourseList().forEach(course -> {
      initStudentsCourse(course, student.getId());
      repository.registerStudentCourse(course);
    });

    return studentDetail;
  }

  /**
   * 受講生コース情報に初期設定（受講生ID、開始日、終了日）を行います。 Clockを使うことでテストでの時間制御が可能。
   */
  void initStudentsCourse(StudentCourse studentCourse, String studentId) {
    LocalDateTime now = LocalDateTime.now(clock); // ★Clockを使った時間取得
    studentCourse.setStudentId(studentId);
    studentCourse.setStartAt(now);
    studentCourse.setEndAt(now.plusYears(1));
  }

  /**
   * 受講生詳細の情報を更新します。
   *
   * @param studentDetail 更新対象の受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());

    studentDetail.getStudentCourseList().forEach(
        course -> repository.updateStudentCourse(course)
    );
  }
}

