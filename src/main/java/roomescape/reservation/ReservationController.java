package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeRepository;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody final ReservationRequest request
    ) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId());
        Reservation reservation = new Reservation(
                0L,
                request.name(),
                request.date(),
                time
        );
        reservationRepository.insert(reservation);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservation() {
        return ResponseEntity.ok(reservationRepository.findAllReservations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(
            @PathVariable("id") final long id
    ) {
        if (reservationRepository.delete(id) == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}

