package raisetech.student.managerment.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {

  private String id;
  private String courseName;
  private String startDate;
  private String endDate;
  private String studentId;

}
