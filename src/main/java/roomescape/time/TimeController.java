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
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping
    public ResponseEntity<Time> createTime(
            @RequestBody final Time time
    ) {
        timeRepository.insert(time);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Time>> readAllTie() {
        return ResponseEntity.ok(timeRepository.findAllReservationTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(
            @PathVariable("id") final long id
    ) {
        if (timeRepository.delete(id) == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
