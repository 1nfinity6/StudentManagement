package raisetech.student.managerment.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.managerment.data.Student;
import raisetech.student.managerment.data.StudentsCourses;
import raisetech.student.managerment.service.StudentService;

@RestController

public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  //READ読み出し　学生情報を取得
  @GetMapping("/student")
  public List<Student> getStudentList() {
    //リクエストの加工処理・入力チェックなど
    return service.searchStudentList();
  }

  //READ処理　受講生コース情報
  @GetMapping("/studentscourse/java")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.searchStudentsCoursesList();
  }

}
