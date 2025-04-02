package raisetech.student.managerment;

import  lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class StudentsCourses {

  private String id;
  private String courseName;
  private String startDate;
  private String endDate;
  private String studentId;

}
