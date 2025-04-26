package roomescape.domain.reservationtime;

import java.time.LocalTime;

public record ReservationTime(
        Long id,
        LocalTime startAt
) {
    public ReservationTime {
        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 시간이 비어 있을 수 없습니다.");
        }
    }
}
