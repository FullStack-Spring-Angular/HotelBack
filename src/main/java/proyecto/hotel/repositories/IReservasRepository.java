package proyecto.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.hotel.models.Reservas;

public interface IReservasRepository extends JpaRepository<Reservas, Long>{
	
}
