package raisetech.student.management.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import raisetech.student.management.entity.Status;

@Getter
@Setter
public class StudentCourseDto {

  private String id;
  private String courseName;
  private LocalDateTime courseStartAt;
  private LocalDateTime courseEndAt;
  private String studentId;

  private Status status;
}

