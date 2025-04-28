package roomescape.domain.reservationtime;

import java.time.LocalTime;

public record ReservationTime(
        Long id,
        LocalTime startAt
) {

    private static final String ERROR_SIGN = "[ERROR] ";

    public ReservationTime {
        if (startAt == null) {
            throw new IllegalArgumentException(ERROR_SIGN + "시간이 비어 있을 수 없습니다.");
        }
    }
}
