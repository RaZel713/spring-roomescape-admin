package roomescape.domain.reservation;

import java.time.LocalDate;

public record CreateReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
}
