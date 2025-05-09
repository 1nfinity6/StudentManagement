package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
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

  @Schema(description = "コース名", example = "Javaベーシック")
  private String courseName;

  @Schema(description = "コース開始日", example = "2024-04-01T00:00:00")
  private LocalDateTime endDate;

  @Schema(description = "コース終了日", example = "2025-04-01T00:00:00")
  private LocalDateTime startDate;

  private String studentId;
}
