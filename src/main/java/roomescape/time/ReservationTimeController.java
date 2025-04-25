package roomescape.time;

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
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(
            @RequestBody final ReservationTime reservationTime
    ) {
        reservationTimeService.createReservationTime(reservationTime);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readAllTie() {
        List<ReservationTime> reservationTimes = reservationTimeService.getAllReservationTime();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(
            @PathVariable("id") final long id
    ) {
        try {
            reservationTimeService.deleteReservationTime(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
