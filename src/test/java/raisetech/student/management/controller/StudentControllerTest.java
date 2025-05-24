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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.data.Student;

import raisetech.student.management.repository.StudentRepository;
import raisetech.student.management.converter.StudentConverter;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.entity.Status;
import raisetech.student.management.entity.Student;
import raisetech.student.management.entity.StudentCourse;
import raisetech.student.management.exception.StudentNotFoundException;
import raisetech.student.management.repository.mybatis.StudentCourseMapper;
import raisetech.student.management.repository.mybatis.StudentRepository;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService studentService;
  @MockBean
  private StudentRepository studentRepository;
  @MockBean
  private StudentConverter studentConverter;
  @MockBean
  private StudentCourseMapper studentCourseMapper;


  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    when(studentService.searchStudentList()).thenReturn(Collections.emptyList());
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(studentService, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "999";

    Student student = new Student(
        "999",
        "テスト 太郎",
        "テスト タロウ",
        "テスト",
        "東京都",
        "男",
        30,
        "test@example.com",
        null,
        false
    );

    StudentCourse course = new StudentCourse(
        "1",
        "Javaスタンダード",
        LocalDateTime.of(2024, 4, 1, 0, 0),
        LocalDateTime.of(2024, 9, 1, 0, 0),
        "999",
        Status.TEMPORARY
    );

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(List.of(course));

    when(studentService.searchStudent(id)).thenReturn(new StudentDetail());
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().json("{}"));

    verify(studentService, times(1)).searchStudent(id);
  }

  @Test
  void 存在しないIDで検索した場合に404が返ること() throws Exception {
    String notFoundId = "404";

    when(studentService.searchStudent(notFoundId))
        .thenThrow(new StudentNotFoundException("Student not found"));

    mockMvc.perform(get("/student/{id}", notFoundId))
        .andExpect(status().isNotFound());
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {

    StudentDetail returnedDetail = new StudentDetail();
    returnedDetail.setStudent(new Student());
    returnedDetail.setStudentCourseList(List.of());

    when(studentService.registerStudent(any())).thenReturn(new StudentDetail());
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
                         "startAt": "2024-04-01T00:00:00",
                         "endAt": "2024-09-01T00:00:00",
                         "studentId": "1",
                         "status": "仮申込"
                      }
                    ]
                 }
                """
        ))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student").value(Matchers.nullValue()))
        .andExpect(jsonPath("$.student.id").doesNotExist())
        .andExpect(jsonPath("$.studentCourseList").isArray())
        .andExpect(jsonPath("$.studentCourseList").isEmpty());
    verify(studentService, times(1)).registerStudent(any());
  }

  @Test
  void 無効なJSONで登録した場合400が返ること() throws Exception {
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void 受講生詳細の更新が実行できて空のレスポンスで200OKが返ってくる事() throws Exception {
    doNothing().when(studentService).updateStudent(any());

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
                          "startAt": "2024-04-01T00:00:00",
                          "endAt": "2024-09-01T00:00:00",
                          "studentId": "1"
                       }
                     ]
                  }
                """
        ))
        .andExpect(status().isOk());

    verify(studentService, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時にチェックに異常が発生しない事() {
    Student student = new Student(
        "1",
        "江並公史",
        "エナミコウジ",
        "こーじ",
        "奈良県奈良市",
        "その他",
        25,
        "kouji@exaple.com",
        "",
        false
    );

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations).isEmpty();
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かる事() {
    Student student = new Student(
        "テストです。",
        "江並公史",
        "エナミコウジ",
        "こーじ",
        "奈良県奈良市",
        "その他",
        25,
        "kouji@exaple.com",
        "",
        false
    );

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");
  }
}