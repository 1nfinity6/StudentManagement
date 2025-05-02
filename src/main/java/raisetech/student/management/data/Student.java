package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter//lombok
@Setter//lombok

public class Student {

  @NotBlank
  @Pattern(regexp = "^\\d+$")
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String kanaName;

  @NotBlank
  private String nickname;

  @NotBlank
  private String region;

  @NotBlank
  private String gender;

  @NotBlank
  private int age;

  @NotBlank
  @Email
  private String email;

  private String remark;
  private boolean isDeleted;
}
