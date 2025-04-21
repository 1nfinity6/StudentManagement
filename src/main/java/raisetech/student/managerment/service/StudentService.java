package raisetech.student.managerment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCourses();
  }

  @Transactional
  public void registerStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.insertStudent(student);

    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setCourseStartAt(LocalDateTime.now());
      studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourses(studentsCourses);
    }
  }

  public void save(Student student) {
    repository.insertStudent(student);
  }

  public void updateStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    if ("MALE".equals(student.getGender())) {
      student.setGender("男");
    } else if ("FEMALE".equals(student.getGender())) {
      student.setGender("女");
    } else {
      student.setGender("その他");
    }

    repository.updateStudent(student);
  }

  public Student findById(Long id) {
    return repository.findById(id);
  }
}
