package roomescape.time;

import java.util.List;
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

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(
            @RequestBody final ReservationTime reservationTime
    ) {
        reservationTimeRepository.insert(reservationTime);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readAllTie() {
        return ResponseEntity.ok(reservationTimeRepository.findAllReservationTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(
            @PathVariable("id") final long id
    ) {
        if (reservationTimeRepository.delete(id) == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
