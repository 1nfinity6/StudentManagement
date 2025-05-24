package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.StudentSearchRequest;
import raisetech.student.management.exception.StudentNotFoundException;
import raisetech.student.management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行わないものとします。
   *
   * @return 受講生詳細一覧（全件）
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です。 IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 　検索対象の受講生のID
   * @return 対象の受講生情報（単一）
   */
  @Operation(summary = "受講生検索", description = "受講生詳細を検索します。")
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @NotNull String id) {
    StudentDetail studentDetail = service.searchStudent(id);
    if (studentDetail == null) {
      throw new StudentNotFoundException("指定されたIDの受講生が見つかりません");
    }
    return studentDetail;
  }

  @ExceptionHandler(StudentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public String handleStudentNotFound(StudentNotFoundException ex) {
    return ex.getMessage();
  }


  /**
   * 受講生詳細のを登録します。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }


  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新も行います。(論理削除)
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生詳細を更新します。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  @Operation(summary = "条件検索", description = "指定した条件で受講生を検索します。")
  @PostMapping("/searchStudents")
  public List<StudentDetail> searchStudents(
      @RequestBody StudentSearchRequest request) {
    return service.searchStudents(request);
  }
}
