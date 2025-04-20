package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final AtomicLong nextId = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    public synchronized Reservation save(final Reservation reservation) {
        final Reservation reservationWithId = reservation.writeId(nextId.getAndIncrement());
        reservations.add(reservationWithId);

        return reservationWithId;
    }

    public synchronized void removeReservation(final long id) {
        final Reservation target = findBy(id);

        reservations.remove(target);
    }

    private Reservation findBy(long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 해당 ID의 예약을 찾을 수 없습니다. id:" + id));
    }

    public synchronized List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
}
