package proyecto.hotel.interfaces;

import java.time.LocalDate;

public interface ReservaInfoInterface {

	Long getIdReserva();
    boolean isEstadoReserva();
    Long getIdListaReserva();
    Long getIdUsuario();
    LocalDate getEndDate();
    LocalDate getStartDate();
    Long getIdHabitacion();
    String getNombreHabitacion();
    Long getprecioHabitacion();
}

