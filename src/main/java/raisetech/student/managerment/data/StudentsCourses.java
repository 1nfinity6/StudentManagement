package raisetech.student.managerment.data;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {

  private String id;
  private String courseName;
  private LocalDateTime endDate;
  private LocalDateTime startDate;
  private String studentId;

  public void setCourseEndAt(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public void setCourseStartAt(LocalDateTime startDate) {
    this.startDate = startDate;
  }
}