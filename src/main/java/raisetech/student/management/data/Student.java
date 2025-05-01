package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter//lombok
@Setter//lombok

public class Student {

  private Long id;
  @NotBlank(message = "名前は必須です")
  private String name;
  private String kanaName;
  private String nickname;
  private String region;
  private String gender;
  private int age;
  @Email(message = "正しいメールアドレスを入力してください")
  private String email;
  private String remark;
  private boolean isDeleted;
}
