package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Reservations {
    private final JdbcTemplate jdbcTemplate;

    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    private final AtomicLong nextId = new AtomicLong(1);
//    private final List<Reservation> reservations = new ArrayList<>();

//    public synchronized Reservation save(final Reservation reservation) {
//        final Reservation reservationWithId = reservation.writeId(nextId.getAndIncrement());
//        reservations.add(reservationWithId);
//
//        return reservationWithId;
//    }
//
//    public synchronized void removeReservation(final long id) {
//        final Reservation target = findBy(id);
//
//        reservations.remove(target);
//    }
//
//    private Reservation findBy(long id) {
//        return reservations.stream()
//                .filter(reservation -> Objects.equals(reservation.id(), id))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("[ERROR] 해당 ID의 예약을 찾을 수 없습니다. id:" + id));
//    }

    public synchronized List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";

        // 저장된 모든 Reservations를 list형태로 반환
        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                    );
                    return reservation;
                });
    }
}
