package raisetech.student.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSearchDto {

  private String name;
  private String region;
  private Integer minAge;
  private Integer maxAge;
  private String gender;
  private String courseName;
  private String status;
}
