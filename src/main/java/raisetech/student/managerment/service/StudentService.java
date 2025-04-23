package raisetech.student.managerment.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.managerment.data.Student;
import raisetech.student.managerment.data.StudentsCourses;
import raisetech.student.managerment.domain.StudentDetail;
import raisetech.student.managerment.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();  // repository.search() メソッドを呼び出す
  }

  public StudentDetail searchStudent(String id) {
    Student student = repository.findStudentById(Long.parseLong(id));  // findById を使用
    if (student != null) {
      List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      studentDetail.setStudentsCourses(studentsCourses);
      return studentDetail;
    } else {
      return null;
    }
  }

  public List<StudentsCourses> findCoursesByStudentId(Long studentId) {
    return repository.searchStudentsCourses(studentId);
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCoursesList();
  }

  public void registerStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.insertStudent(student);
  }

  public void updateStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.updateStudent(student);
    List<StudentsCourses> courses = studentDetail.getStudentsCourses();
    for (StudentsCourses course : courses) {
      if (course.getId() != null && !course.getId().isEmpty()) {
        System.out.println("Updating course with ID: " + course.getId() + " to course name: "
            + course.getCourseName() + " and studentId: " + course.getStudentId());
        repository.updateStudentCourse(course);
      }
    }
  }

  public void save(Student student) {
    if (student.getId() == null) {
      repository.insertStudent(student);
    } else {
      repository.updateStudent(student);
    }
  }

  public Student findById(Long id) {
    return repository.findStudentById(id);
  }
}
