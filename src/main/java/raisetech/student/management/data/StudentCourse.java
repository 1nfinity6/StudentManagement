package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentCourse {

  private String id;

  @NotNull(message = "コース名は必須です。")
  private String courseName;

  @NotNull(message = "修了予定日は必須です。")
  private LocalDateTime courseEndAt;

  @NotNull(message = "受講開始日は必須です。")
  private LocalDateTime courseStartAt;

  @NotNull(message = "受講生IDは必須です。")
  private String studentId;
}
