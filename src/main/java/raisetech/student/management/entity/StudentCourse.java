package raisetech.student.management.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Schema(description = "受講生コース情報")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students_courses")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class StudentCourse {

  @Id
  @EqualsAndHashCode.Include
  @Column(name = "id", columnDefinition = "VARCHAR(36)")
  private String id;

  @NotNull(message = "コース名は必須です。")
  @Column(name = "course_name")
  private String courseName;

  @NotNull(message = "受講開始日は必須です。")
  @Column(name = "course_start_at")
  private LocalDateTime StartAt;

  @NotNull(message = "修了予定日は必須です。")
  @Column(name = "course_end_at")
  private LocalDateTime EndAt;

  @NotNull(message = "受講生IDは必須です。")
  @Column(name = "student_id")
  private String studentId;

  @NotNull(message = "ステータスは必須です。")
  @Enumerated(EnumType.STRING)
  @Column(name = "status", columnDefinition = "ENUM('仮申込','本申込','受講中','受講修了')")
  private Status status = Status.TEMPORARY;

  @PrePersist
  public void generateId() {
    if (this.id == null || this.id.isBlank()) {
      this.id = UUID.randomUUID().toString();
    }
  }
}