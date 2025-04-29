package roomescape.dto;

import java.time.LocalDate;

public record ReservationCreation(
        String name,
        LocalDate date,
        Long timeId
) {
    public static ReservationCreation from(CreateReservationRequest request) {
        return new ReservationCreation(request.name(), request.date(), request.timeId());
    }
}
