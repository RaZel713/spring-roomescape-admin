package roomescape.time;

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
}
