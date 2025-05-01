package raisetech.student.managerment.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourse {

  private String id;
  private String courseName;
  private LocalDateTime endDate;
  private LocalDateTime startDate;
  private Long studentId;

  public void setCourseEndAt(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public void setCourseStartAt(LocalDateTime startDate) {
    this.startDate = startDate;
  }
}