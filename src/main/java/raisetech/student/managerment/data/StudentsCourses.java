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
  private LocalDateTime courseStartAt;
  private LocalDateTime courseEndAt;
  private String studentId;

}
