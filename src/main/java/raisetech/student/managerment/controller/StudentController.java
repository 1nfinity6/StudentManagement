package raisetech.student.managerment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.managerment.controller.converter.StudentConverter;
import raisetech.student.managerment.data.Student;
import raisetech.student.managerment.data.StudentsCourses;
import raisetech.student.managerment.domain.StudentDetail;
import raisetech.student.managerment.service.StudentService;

@Controller
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;


  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.searchStudentsCoursesList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    service.registerStudentDetail(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "editStudent";
    }

    service.updateStudentDetail(studentDetail);
    return "redirect:/studentList";
  }
}
