package roomescape.reservation;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody final CreateReservationRequest request
    ) {
        Reservation insertedReservation = reservationService.createReservation(request);
        return ResponseEntity.ok(insertedReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservation() {
        final List<Reservation> reservations = reservationService.readAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(
            @PathVariable("id") final long id
    ) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
