package roomescape.domain.reservationtime;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalTime;

public class ReservationTime {

    private static final String ERROR_SIGN = "[ERROR] ";

    private final Long id;
    private final LocalTime startAt;

    @JsonCreator
    public ReservationTime(Long id, LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this(null, startAt);
    }

    private void validate(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException(ERROR_SIGN + "시간이 비어 있을 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
