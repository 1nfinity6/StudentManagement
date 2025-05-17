package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.data.Student;

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
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "999";
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                    "student": {
                      "id": "1",
                      "name" : "江並公史",
                      "kanaName": "エナミコウジ",
                      "nickname": "こーじ",
                      "region": "奈良県奈良市",
                      "gender": "その他",
                      "email": "kouji@example.com",
                      "age": 25,
                      "remark" : "",
                      "deleted": false
                    },
                    "studentCourseList" : [
                      {
                         "courseName" : "Javaスタンダード",
                         "endDate":  "2024-09-01T00:00:00",
                         "startDate":  "2024-04-01T00:00:00",
                         "studentId": "1"
                      }
                    ]
                 }
                """
        ))
        .andExpect(status().isOk());
    verify(service, times(1)).registerStudent(any());
  }


  @Test
  void 受講生詳細の更新が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                 {
                     "student": {
                       "id": "1",
                       "name" : "江並公史",
                       "kanaName": "エナミコウジ",
                       "nickname": "こーじ",
                       "region": "奈良県奈良市",
                       "gender": "その他",
                       "email": "kouji@example.com",
                       "age": 25,
                       "remark" : "",
                       "deleted": false
                     },
                     "studentCourseList" : [
                       {
                          "id" : "15",
                          "courseName" : "Javaスタンダード",
                          "endDate":  "2024-09-01T00:00:00",
                          "startDate":  "2024-04-01T00:00:00",
                          "studentId": "1"
                       }
                     ]
                  }
                """
        ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時にチェックに異常が発生しない事() {
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
}