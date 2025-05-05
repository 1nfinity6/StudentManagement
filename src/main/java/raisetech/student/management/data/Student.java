package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生詳細")
@Getter
@Setter

public class Student {

  @Schema(description = "受講生ID", example = "1001")
  @NotNull(message = "ID は必須です")
  @Pattern(regexp = "^\\d+$")
  private String id;

  @Schema(description = "名前", example = "山田 太郎")
  @NotBlank
  private String name;

  @Schema(description = "フリガナ", example = "ヤマダ タロウ")
  @NotBlank
  private String kanaName;

  @Schema(description = "ニックネーム", example = "たろちゃん")
  @NotBlank
  private String nickname;

  @Schema(description = "地域", example = "東京都千代田区")
  @NotBlank
  private String region;

  @Schema(description = "性別（男／女／その他）", example = "男")
  @NotBlank
  private String gender;

  @Schema(description = "年齢", example = "25")
  private int age;

  @Schema(description = "メールアドレス", example = "taro@example.com")
  @NotBlank
  @Email
  private String email;


  private String remark;
  private boolean isDeleted;
}
