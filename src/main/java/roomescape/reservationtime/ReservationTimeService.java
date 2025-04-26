package roomescape.reservationtime;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        if (isReservationTimeExist(reservationTime)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 예약 시간이 있습니다.");
        }

        return reservationTimeRepository.insert(reservationTime);
    }

    private boolean isReservationTimeExist(ReservationTime reservationTime) {
        return reservationTimeRepository.existsBy(reservationTime.startAt());
    }

    public List<ReservationTime> getAllReservationTime() {
        return reservationTimeRepository.findAllReservationTime();
    }

    public void deleteReservationTime(final Long id) {
        if (reservationTimeRepository.delete(id) == 0) {
            throw new NoSuchElementException("[ERROR] 해당 ID의 시간을 찾을 수 없습니다. id:" + id);
        }
    }
}
