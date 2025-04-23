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

  @Select("SELECT * FROM students WHERE deleted = false")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(Long id);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentsCourses(Long studentId);

  @Insert("""
        INSERT INTO students (name, kanaName, nickname, region, gender, age, email, remark, deleted) 
        VALUES (#{name}, #{kanaName}, #{nickname}, #{region}, #{gender}, #{age}, #{email}, #{remark}, #{isDeleted})
      """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Update("""
        UPDATE students 
        SET name = #{name}, kanaName = #{kanaName}, nickname = #{nickname}, region = #{region},
        gender = #{gender}, age = #{age}, email = #{email}, remark = #{remark}, deleted = #{deleted} 
        WHERE id = #{id}
      """)
  void updateStudent(Student student);

  @Insert("INSERT INTO students_courses (course_name, student_id) VALUES (#{courseName}, #{studentId})")
  void insertStudentCourse(StudentsCourses course);

  @Update("""
        UPDATE students_courses
        SET course_name = #{courseName}
        WHERE id = #{id} AND student_id = #{studentId}
      """)
  void updateStudentCourse(StudentsCourses course);
}
