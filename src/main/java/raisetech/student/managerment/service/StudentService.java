package raisetech.student.managerment.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.managerment.data.Student;
import raisetech.student.managerment.data.StudentsCourses;
import raisetech.student.managerment.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  /**
   * 30代の受講生リストを取得
   */
  public List<Student> searchStudentList() {
    List<Student> allStudents = repository.search();
    return allStudents.stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() < 40)
        .collect(Collectors.toList());
  }

  /**
   * Javaコースを受講している学生情報を取得
   */
  public List<StudentsCourses> searchStudentsCoursesList() {
    List<StudentsCourses> allCourses = repository.searchStudentsCourses();
    return allCourses.stream()
        .filter(course -> "Javaスタンダード".equals(course.getCourseName()))
        .collect(Collectors.toList());
  }
}