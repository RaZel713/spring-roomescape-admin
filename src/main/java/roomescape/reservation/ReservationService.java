package roomescape.reservation;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void createReservation(final ReservationRequest request) {
        final ReservationTime time = reservationTimeRepository.findById(request.timeId());
        final Reservation reservation = new Reservation(
                0L,
                request.name(),
                request.date(),
                time
        );
        reservationRepository.insert(reservation);
    }

    public List<Reservation> readAllReservations() {
        return reservationRepository.findAllReservations();
    }

    public void deleteReservation(final Long id) {
        if (reservationRepository.delete(id) == 0) {
            throw new NoSuchElementException("[ERROR] 해당 ID의 예약을 찾을 수 없습니다. id:" + id);
        }
    }
}
