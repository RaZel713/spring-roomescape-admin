package roomescape.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseRepository<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
