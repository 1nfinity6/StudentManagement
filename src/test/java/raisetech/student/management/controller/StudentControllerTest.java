package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;
  @MockBean
  private StudentRepository repository;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の取得が実行できて空のStudentDetailが返ってくること() throws Exception {
    Student student = new Student();
    student.setId("1");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("こーじ");
    student.setRegion("奈良県奈良市");
    student.setGender("その他");
    student.setEmail("kouji@exaple.com");
    student.setAge(25);
    student.setDeleted(false);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    when(service.searchStudent("1")).thenReturn(detail);

    mockMvc.perform(get("/student/1"))
        .andExpect(status().isOk());

    verify(service).searchStudent("1");
  }

  @Test
  void 受講生詳細の登録が実行できて200で返ってくること() throws Exception {
    Student student = new Student();
    student.setId("1");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("こーじ");
    student.setRegion("奈良県奈良市");
    student.setGender("その他");
    student.setEmail("kouji@example.com");
    student.setAge(25);
    student.setDeleted(false);

    StudentDetail input = new StudentDetail();
    input.setStudent(student);

    when(service.registerStudent(any(StudentDetail.class))).thenReturn(input);

    String json = """
        {
          "student": {
            "id": "1",
            "name": "江並公史",
            "kanaName": "エナミコウジ",
            "nickname": "こーじ",
            "region": "奈良県奈良市",
            "gender": "その他",
            "email": "kouji@example.com",
            "age": 25,
            "deleted": false
          },
          "studentCourseList": []
        }
        """;

    mockMvc.perform(post("/registerStudent")
            .contentType("application/json")
            .content(json))
        .andExpect(status().isOk());

    verify(service).registerStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生を更新できること() throws Exception {
    doNothing().when(service).updateStudent(any(StudentDetail.class));

    String json = """
        {
         "student": {
            "id": "1",
            "name": "更新後の名前"
          },
          "studentCourseList": []
        }
        """;

    mockMvc.perform(put("/updateStudent")
            .contentType("application/json")
            .content(json))
        .andExpect(status().isOk());

    verify(service).updateStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力チェックに異常が発生しない事() {
    Student student = new Student();
    student.setId("1");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("こーじ");
    student.setRegion("奈良県奈良市");
    student.setGender("その他");
    student.setEmail("kouji@exaple.com");
    student.setAge(25);
    student.setDeleted(false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations).isEmpty();
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かる事() {
    Student student = new Student();
    student.setId("テストです。");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("こーじ");
    student.setRegion("奈良県奈良市");
    student.setGender("その他");
    student.setEmail("kouji@exaple.com");
    student.setAge(25);
    student.setDeleted(false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");
  }

  @Test
  void Studentのバリデーションが正常に通ること() {
    Student student = new Student();
    student.setId("1");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("こーじ");
    student.setRegion("奈良県奈良市");
    student.setGender("その他");
    student.setEmail("kouji@example.com");
    student.setAge(25);
    student.setDeleted(false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations).isEmpty();
  }

  @Test
  void StudentCourseの必須項目が空の場合バリデーションエラーが発生されること() {
    StudentCourse course = new StudentCourse();
    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(course);
    assertThat(violations).isNotEmpty();
    assertThat(violations).extracting("message")
        .contains("コース名は必須です。", "受講開始日は必須です。", "修了予定日は必須です。",
            "受講生IDは必須です。");
  }
}