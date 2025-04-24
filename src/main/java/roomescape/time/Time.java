package roomescape.time;

import java.time.LocalTime;

public record Time(
        long id, LocalTime startAt
) {
    public Time {
        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 시간이 비어 있을 수 없습니다.");
        }
    }
}
