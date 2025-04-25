package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void createReservationTime(ReservationTime reservationTime) {
        reservationTimeRepository.insert(reservationTime);
    }

    public List<ReservationTime> getAllReservationTime() {
        return reservationTimeRepository.findAllReservationTime();
    }

    public void deleteReservationTime(Long id) {
        if (reservationTimeRepository.delete(id) == 0) {
            throw new NoSuchElementException("[ERROR] 해당 ID의 시간을 찾을 수 없습니다. id:" + id);
        }
    }
}
