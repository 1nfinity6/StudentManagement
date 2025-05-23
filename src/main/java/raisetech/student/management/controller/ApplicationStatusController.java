package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.entity.Status;
import raisetech.student.management.service.StudentCourseService;

/**
 * 受講ステータスの登録・更新用のControllerです。
 */
@Validated
@RestController
@RequestMapping("/status")
public class ApplicationStatusController {

  private final StudentCourseService studentCourseService;

  @Autowired
  public ApplicationStatusController(StudentCourseService studentCourseService) {
    this.studentCourseService = studentCourseService;
  }

  /**
   * 受講ステータスを新規登録します。
   *
   * @param studentCourseId 対象の受講生コースID
   * @param status          登録するステータス
   * @return 登録成功メッセージ
   */
  @Operation(summary = "ステータス登録", description = "受講生のコースにステータスを新規登録します。")
  @PostMapping("/{studentCourseId}")
  public ResponseEntity<String> registerStatus(
      @PathVariable @NotNull String studentCourseId,
      @RequestBody @NotNull Status status) {

    studentCourseService.registerStatus(studentCourseId, status);
    return ResponseEntity.ok("ステータスの登録が完了しました。");
  }

  /**
   * 受講ステータスを更新します。
   *
   * @param studentCourseId 対象の受講生コースID
   * @param status          更新するステータス
   * @return 更新成功メッセージ
   */
  @Operation(summary = "ステータス更新", description = "受講生のコースに紐づくステータスを更新します。")
  @PutMapping("/{studentCourseId}")
  public ResponseEntity<String> updateStatus(
      @PathVariable @NotNull String studentCourseId,
      @RequestBody @NotNull Status status) {

    studentCourseService.updateStatus(studentCourseId, status);
    return ResponseEntity.ok("ステータスの更新が完了しました。");
  }
}

