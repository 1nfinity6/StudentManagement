package raisetech.student.management.repository.mybatis;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.student.management.entity.Student;
import raisetech.student.management.entity.StudentCourse;
import raisetech.student.management.dto.StudentSearchRequest;
import raisetech.student.management.entity.Status;


/**
 * 受講生テーブルと受講生コース情報テーブルに関するMyBatisマッパー。
 */
@Mapper
public interface StudentRepository {


  // 全データ削除（テスト用など）
  void deleteAllStudentCourses();

  void deleteAllStudents();

  // 全件取得
  List<Student> search();

  Student searchStudent(@Param("id") String id);

  List<StudentCourse> searchStudentCourseList();

  List<StudentCourse> searchStudentCourse(@Param("studentId") String studentId);

  // IDで受講生コースを検索
  Optional<StudentCourse> findById(@Param("studentCourseId") String studentCourseId);

  // 条件検索（動的検索）
  List<Student> searchByConditions(@Param("request") StudentSearchRequest request);

  // 複数学生IDに対する受講コース情報の取得
  List<StudentCourse> searchStudentCourseListForStudents(
      @Param("studentIds") List<String> studentIds);

  // 登録処理
  void registerStudent(Student student);

  void registerStudentCourse(StudentCourse studentCourse);

  // 更新処理
  void updateStudent(Student student);

  void updateStudentCourse(StudentCourse studentCourse);

  // ステータス更新処理（追加）
  void updateStatus(@Param("studentCourseId") String studentCourseId,
      @Param("status") Status status);
}
