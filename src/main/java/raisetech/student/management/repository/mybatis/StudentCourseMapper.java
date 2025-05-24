package raisetech.student.management.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.student.management.entity.StudentCourse;

@Mapper
public interface StudentCourseMapper {

  void registerStudentCourse(StudentCourse course);

  void updateStudentCourse(StudentCourse course);

  void updateStatus(@Param("studentCourseId") String studentCourseId,
      @Param("status") String status);
}
