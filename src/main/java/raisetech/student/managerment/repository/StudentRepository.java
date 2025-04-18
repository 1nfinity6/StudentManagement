package raisetech.student.managerment.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.managerment.data.Student;
import raisetech.student.managerment.data.StudentsCourses;


@Mapper
public interface StudentRepository {


  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findById(Long id);

  @Insert(
      "INSERT INTO students (name, kanaName, nickname, region, gender, age, email, remark, deleted) "
          + "VALUES (#{name}, #{kanaName}, #{nickname}, #{region}, #{gender}, #{age}, #{email}, #{remark}, #{isDeleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
    //自動生成したい項目↑
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses (course_name, course_start_at, course_end_at, student_id) "
      + "VALUES(#{courseName}, #{courseStartAt}, #{courseEndAt}, #{studentId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourses(StudentsCourses studentscourses);

  @Update("""
      UPDATE students SET name = #{name},kanaName = #{kanaName},nickname = #{nickname},region = #{region},
      gender = #{gender},age = #{age},email = #{email},remark = #{remark},deleted = #{deleted} WHERE id = #{id}
      """)
  void updateStudent(Student student);
}
