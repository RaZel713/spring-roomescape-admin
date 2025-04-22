package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final Reservations reservations;

    public ReservationController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservation() {
        return ResponseEntity.ok(reservations.findAllReservations());
    }

//    @PostMapping
//    public ResponseEntity<Reservation> createReservation(
//            @RequestBody final Reservation reservation
//    ) {
//        final Reservation savedReservation = reservations.save(reservation);
//        return ResponseEntity.ok(savedReservation);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteReservationById(
//            @PathVariable("id") final long id
//    ) {
//        try {
//            reservations.removeReservation(id);
//            return ResponseEntity.ok().build();
//        } catch (final NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
