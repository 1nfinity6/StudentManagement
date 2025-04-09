package raisetech.student.managerment.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.managerment.data.Student;
import raisetech.student.managerment.data.StudentsCourses;

@Getter
@Setter

public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
  private String remark;
  private boolean isDeleted;

  public void setIsDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
