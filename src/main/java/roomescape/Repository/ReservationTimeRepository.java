package roomescape.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservationtime.ReservationTime;

@Repository
public class ReservationTimeRepository implements BaseRepository<ReservationTime> {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public synchronized ReservationTime insert(final ReservationTime reservationTime) {
        final String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.startAt().toString());
            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(id);
    }

    public boolean existsBy(LocalTime startAt) {
        final String sql = "SELECT COUNT(*) FROM reservation_time WHERE start_at = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startAt);
        return count != null && count > 0;
    }

    @Override
    public synchronized List<ReservationTime> findAll() {
        final String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> mapRow(resultSet));
    }

    @Override
    public ReservationTime findById(final Long id) {
        final String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> mapRow(resultSet), id
        );
    }

    @Override
    public synchronized int delete(final Long id) {
        final String sql = "DELETE FROM reservation_time where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public ReservationTime mapRow(ResultSet resultSet) throws SQLException {
        return new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
    }
}
