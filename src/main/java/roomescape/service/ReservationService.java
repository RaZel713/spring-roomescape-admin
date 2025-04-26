package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import roomescape.Repository.ReservationTimeRepository;
import roomescape.domain.reservation.CreateReservationRequest;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;

@Service
public class ReservationService {

    private static final int DELETE_NO_ROWS_AFFECTED = 0;

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation createReservation(final CreateReservationRequest request) {
        final ReservationTime time = reservationTimeRepository.findById(request.timeId());
        final Reservation reservation = new Reservation(
                0L,
                request.name(),
                request.date(),
                time
        );
        return reservationRepository.insert(reservation);
    }

    public List<Reservation> readAllReservations() {
        return reservationRepository.findAllReservations();
    }

    public void deleteReservation(final Long id) {
        if (reservationRepository.delete(id) == DELETE_NO_ROWS_AFFECTED) {
            throw new NoSuchElementException("[ERROR] 해당 ID의 예약을 찾을 수 없습니다. id:" + id);
        }
    }
}

