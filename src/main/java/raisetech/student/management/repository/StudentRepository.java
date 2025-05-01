package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */

@Mapper
public interface StudentRepository {

  /**
   * 受験生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */

  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id 　受講生ID
   * @return 受講生
   */

  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報（全件）
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourse(Long studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  @Insert(
      "INSERT INTO students (name, kanaName, nickname, region, gender, age, email, remark, deleted) "
          +
          "VALUES (#{name}, #{kanaName}, #{nickname}, #{region}, #{gender}, #{age}, #{email}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  @Insert(
      "INSERT INTO students_courses (course_name, student_id, course_start_at, course_end_at) " +
          "VALUES (#{courseName}, #{studentId}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  @Update("""
      UPDATE students SET name = #{name},kanaName = #{kanaName},nickname = #{nickname},region = #{region},
      gender = #{gender},age = #{age},email = #{email},remark = #{remark},deleted = #{deleted} WHERE id = #{id}
      """)
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourse(StudentCourse studentCourse);
}
