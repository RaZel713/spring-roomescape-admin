package roomescape.dto;

import java.time.LocalTime;

public record ReservationTimeCreation(
        LocalTime startAt
) {
    public static ReservationTimeCreation from(CreateReservationTimeRequest request) {
        return new ReservationTimeCreation(request.startAt());
    }
}
