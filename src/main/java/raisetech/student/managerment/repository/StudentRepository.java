package raisetech.student.managerment.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.managerment.data.Student;
import raisetech.student.managerment.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ 全件検索や単一検索、コース情報の検索が行えるクラスです。
 */
@Mapper//これをつけるとMyBatisが処理すると認識する.
public interface StudentRepository {

  /**
   * 全検索します。
   *
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();
}
