package roomescape.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseRepository<T> {

    T insert(T t);

    Iterable<T> findAll();

    T findBy(Long id);

    int deleteBy(Long id);

    T mapRow(ResultSet resultSet) throws SQLException;
}
