package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationTimeRepository;
import roomescape.domain.reservationtime.ReservationTime;

@Service
public class ReservationTimeService {

    private static final String ERROR_SIGN = "[ERROR] ";
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(ReservationTime reservationTime) {
        if (isReservationTimeExist(reservationTime)) {
            throw new IllegalArgumentException(ERROR_SIGN + " 이미 존재하는 예약 시간이 있습니다.");
        }

        Long id = reservationTimeRepository.insert(reservationTime);

        return reservationTimeRepository.findBy(id);
    }

    private boolean isReservationTimeExist(ReservationTime reservationTime) {
        return reservationTimeRepository.existsBy(reservationTime.startAt());
    }

    public List<ReservationTime> readAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteBy(final Long id) {
        if (!reservationTimeRepository.deleteBy(id)) {
            throw new NoSuchElementException(ERROR_SIGN + " 해당 ID의 시간을 찾을 수 없습니다. id:" + id);
        }
    }
}
