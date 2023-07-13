package proyecto.hotel.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import proyecto.hotel.interfaces.ReservaInfoInterface;
import proyecto.hotel.models.ListaReservas;

public interface IListaReservasRepository extends JpaRepository<ListaReservas, Long>{


	@Query("SELECT l FROM ListaReservas l GROUP BY l.reserva.idReserva, l.idListaReserva")
	List<Object[]> findListasReservasGroupedByReserva();
	
	//Obtener el listado de reservas por el id de usuario
	
	@Query("SELECT r.idReserva AS idReserva, r.estadoReserva AS estadoReserva, lr.idListaReserva AS idListaReserva, " +
            "lr.endDate AS endDate, lr.startDate AS startDate, h.idHabitacion AS idHabitacion, h.precio AS precioHabitacion ,h.nombre AS nombreHabitacion, u.idUsuario AS idUsuario " +
            "FROM ListaReservas lr " +
            "JOIN lr.reserva r " +
            "JOIN lr.habitacion h " +
            "JOIN r.user u " +
            "JOIN Reservas re ON r.idReserva = re.idReserva " +
            "JOIN Habitaciones ha ON ha.idHabitacion = h.idHabitacion " +
            "WHERE re.idUsuario = :usuarioId")
    List<ReservaInfoInterface> getByUsuarioId(Long usuarioId);
	
	//Obtener listado de reservas por el idReserva
	
	@Query("SELECT r.idReserva AS idReserva, r.estadoReserva AS estadoReserva, lr.idListaReserva AS idListaReserva, " +
            "lr.endDate AS endDate, lr.startDate AS startDate, h.idHabitacion AS idHabitacion, h.nombre AS nombreHabitacion, u.idUsuario AS idUsuario " +
            "FROM ListaReservas lr " +
            "JOIN lr.reserva r " +
            "JOIN lr.habitacion h " +
            "JOIN r.user u " +
            "JOIN Reservas re ON r.idReserva = re.idReserva " +
            "JOIN Habitaciones ha ON ha.idHabitacion = h.idHabitacion " +
            "WHERE re.idReserva = :idReserva")
    List<ReservaInfoInterface> getByReservaId(Long idReserva);
	
	@Query("SELECT lr FROM ListaReservas lr WHERE lr.idListaReserva = :idListaReserva")
    Optional<ListaReservas> findByIdListaReserva(@Param("idListaReserva") Long idListaReserva);
	
	@Modifying
    @Query("UPDATE ListaReservas lr SET lr.endDate = :endDate, lr.startDate = :startDate, lr.habitacion.idHabitacion = :habitacionId WHERE lr.idListaReserva = :idListaReserva")
    void updateListaReservas(@Param("endDate") LocalDate endDate, @Param("startDate") LocalDate startDate, @Param("habitacionId") Long habitacionId, @Param("idListaReserva") Long idListaReserva);
}
