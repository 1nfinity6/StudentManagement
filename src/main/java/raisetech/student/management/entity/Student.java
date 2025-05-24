package raisetech.student.management.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {
    "id", "name", "kanaName", "nickname", "region", "gender", "age", "email", "remark", "deleted"
})

public class Student {


  @NotNull
  @Pattern(regexp = "^\\d+$", message = "数字のみ入力するようにしてください。")
  private String id;

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

  @Min(1)
  private int age;

  @NotBlank
  @Email
  private String email;

  private String remark;
  private boolean deleted;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Student)) {
      return false;
    }
    Student student = (Student) o;
    return age == student.age &&
        deleted == student.deleted &&
        Objects.equals(name, student.name) &&
        Objects.equals(kanaName, student.kanaName) &&
        Objects.equals(nickname, student.nickname) &&
        Objects.equals(region, student.region) &&
        Objects.equals(gender, student.gender) &&
        Objects.equals(email, student.email) &&
        Objects.equals(remark, student.remark);
  }
  private Boolean deleted = false;

  @Override
  public int hashCode() {
    return Objects.hash(name, kanaName, nickname, region, gender, age, email, remark, deleted);
  }
}
