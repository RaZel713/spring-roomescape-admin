package roomescape.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;

@Repository
public class ReservationRepository implements BaseRepository<Reservation> {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation insert(final Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setString(2, String.valueOf(reservation.date()));
            ps.setLong(3, reservation.time().id());
            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;

        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> mapRow(resultSet));
    }

    @Override
    public Reservation findById(final Long id) {
        final String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                INNER JOIN reservation_time as t
                ON r.time_id = t.id
                WHERE r.id = ?
                """;

        return jdbcTemplate.queryForObject(
                sql, (resultSet, rowNum) -> mapRow(resultSet), id);
    }

    @Override
    public int delete(final Long id) {
        final String sql = "DELETE FROM reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Reservation mapRow(ResultSet resultSet) throws SQLException {
        ReservationTime time = new ReservationTime(
                resultSet.getLong("time_id"),
                LocalTime.parse(resultSet.getString("time_value"))
        );
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                time
        );
    }
}
