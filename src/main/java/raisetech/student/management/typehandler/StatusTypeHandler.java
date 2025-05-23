package raisetech.student.management.typehandler;

import java.util.Arrays;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import raisetech.student.management.entity.Status;

import java.sql.*;

@MappedTypes(Status.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StatusTypeHandler extends BaseTypeHandler<Status> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Status status, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, status.getLabel());
  }

  @Override
  public Status getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return getStatusByLabel(rs.getString(columnName));
  }

  @Override
  public Status getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return getStatusByLabel(rs.getString(columnIndex));
  }

  @Override
  public Status getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return getStatusByLabel(cs.getString(columnIndex));
  }

  private Status getStatusByLabel(String label) {
    if (label == null) {
      return null;
    }
    return Arrays.stream(Status.values())
        .filter(s -> s.getLabel().equals(label))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown status label: " + label));
  }
}
