package proyecto.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import proyecto.hotel.models.Habitaciones;

public interface IHabitacionesRepository extends JpaRepository<Habitaciones,Long >{
	
	 //Método para poder verificar si una habitacion existe en nuestra base de datos
    Boolean existsByNombre(String nombre);
    
    @Query("SELECT h FROM Habitaciones h where h.disponibilidad=true")
    List<Habitaciones> getByDisponibilidad();

}
