package proyecto.hotel.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.hotel.interfaces.HabitacionesInterface;
import proyecto.hotel.models.Habitaciones;
import proyecto.hotel.repositories.IHabitacionesRepository;

@Service
public class HabitacionesService implements HabitacionesInterface {

	@Autowired
	private IHabitacionesRepository habitacionesRepository;
	
	public void save(Habitaciones habitaciones) {
		habitacionesRepository.save(habitaciones);
	}

	@Override
	public Boolean existsByName(String name) {
		return habitacionesRepository.existsByNombre(name);
	}
	
	public List<Habitaciones> getAll() {
		return habitacionesRepository.findAll();
	}
	
	public Optional<Habitaciones> findById(Long id) {
		return habitacionesRepository.findById(id);
	}
	
	public static class ResourceNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ResourceNotFoundException(String message) {
	        super(message);
	    }
	}
	
	public void delete(Long id) {
		habitacionesRepository.deleteById(id);
	}

	@Override
	public List<Habitaciones> getByDisponibilidad() {
		return habitacionesRepository.getByDisponibilidad();
	}
}
