package roomescape.reservation;

import java.time.LocalDate;
import roomescape.time.ReservationTime;

public record Reservation(
        long id,
        String name,
        LocalDate date,
        ReservationTime time
) {

    public Reservation {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름이 비어 있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 날짜가 비어 있을 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간이 비어 있을 수 없습니다.");
        }
    }
}
