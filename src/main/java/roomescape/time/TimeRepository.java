package roomescape.time;

import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public synchronized void insert(final Time time) {
        // 시간을 데이터 베이스에 저장하기
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        jdbcTemplate.update(
                sql,
                time.startAt());
    }

    public synchronized List<Time> findAllReservationTime() {
        String sql = "SELECT id, start_at FROM reservation_time";

        // 저장된 모든 시갇들을 list 형태로 반환
        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("start_at"))
                    );
                    return time;
                });
    }
}
