package raisetech.student.managerment.data;

import lombok.Getter;
import lombok.Setter;

@Getter//lombok
@Setter//lombok

public class Student {

  private String id;
  private String name;
  private String furigana;
  private String nickname;
  private String region;
  private String gender;
  private int age;
  private String email;
}
