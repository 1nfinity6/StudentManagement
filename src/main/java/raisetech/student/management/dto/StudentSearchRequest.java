package raisetech.student.management.dto;

import lombok.Data;

@Data
public class StudentSearchRequest {

  private String name;
  private String region;
  private Integer minAge;
  private Integer maxAge;
  private String gender;
  private String courseName;
  private String status;
}

