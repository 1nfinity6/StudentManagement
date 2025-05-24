package raisetech.student.management.entity;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum Status {
  TEMPORARY("仮申込"),
  FORMAL("本申込"),
  IN_PROGRESS("受講中"),
  COMPLETED("受講修了");

  private final String label;

  Status(String label) {
    this.label = label;
  }

  @JsonValue
  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static Status fromLabel(String label) {
    return Arrays.stream(values())
        .filter(s -> s.label.equals(label))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown label: " + label));
  }
}
