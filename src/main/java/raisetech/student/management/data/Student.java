package raisetech.student.management.data;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter//lombok
@Setter//lombok

public class Student {

  private Long id;
  private String name;
  private String kanaName;
  private String nickname;
  private String region;
  private String gender;
  private int age;
  private String email;
  private String remark;
  private boolean isDeleted;
}
