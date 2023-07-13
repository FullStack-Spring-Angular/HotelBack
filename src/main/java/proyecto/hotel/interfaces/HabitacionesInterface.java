package proyecto.hotel.interfaces;

import java.util.List;

import proyecto.hotel.models.Habitaciones;

public interface HabitacionesInterface {

	//Método para poder verificar si una habitacion existe en nuestra base de datos
    Boolean existsByName(String name);
    
    List<Habitaciones> getByDisponibilidad();
}
