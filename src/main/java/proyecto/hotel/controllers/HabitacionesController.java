package proyecto.hotel.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import proyecto.hotel.dtos.DtoHabitacionesRegistro;
import proyecto.hotel.dtos.ResponseMessageDto;
import proyecto.hotel.models.Habitaciones;
import proyecto.hotel.services.HabitacionesService;
import proyecto.hotel.services.HabitacionesService.ResourceNotFoundException;

@RestController
@RequestMapping("/api/habitaciones/")
public class HabitacionesController {

	@Autowired
	private HabitacionesService habitacionesService;
	
	@PostMapping("register")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> registrar(@RequestBody DtoHabitacionesRegistro dtoRegistro) {
        if (habitacionesService.existsByName(dtoRegistro.getNombre())) {
            return new ResponseEntity(new ResponseMessageDto("Ya existe habitación con ese nombre", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Habitaciones habitacion= new Habitaciones();
        
        habitacion.setNombre(dtoRegistro.getNombre());
        habitacion.setDescripcion(dtoRegistro.getDescripcion());
        habitacion.setNumCamas(dtoRegistro.getNumCamas());
        habitacion.setNumBanios(dtoRegistro.getNumBanios());
        habitacion.setPrecio(dtoRegistro.getPrecio());
        habitacion.setDisponibilidad(true);
        
        habitacionesService.save(habitacion);
        return new ResponseEntity(new ResponseMessageDto("Habitación registrada correctamente", HttpStatus.OK.value()), HttpStatus.OK);
    }
	
	@GetMapping("todos")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<List<Habitaciones>> getAllHabitaciones() {

	    // Get all rooms from the database.
	    List<Habitaciones> habitaciones = habitacionesService.getAll();

	    // Return the rooms.
	    return new ResponseEntity<>(habitaciones, HttpStatus.OK);
	}
	
	@GetMapping("disponibles")
	public ResponseEntity<List<Habitaciones>> getByDisponibilidad() {

	    // Get all rooms from the database.
	    List<Habitaciones> habitaciones = habitacionesService.getByDisponibilidad();

	    // Return the rooms.
	    return new ResponseEntity<>(habitaciones, HttpStatus.OK);
	}
	
	@GetMapping("habitacionById/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Habitaciones> findById(@PathVariable(value = "id") Long id) {

		Optional<Habitaciones> optionalHabitacion = habitacionesService.findById(id);
		
		if (optionalHabitacion.isEmpty()) {
			return new ResponseEntity(new ResponseMessageDto("Habitación no registrada", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		
		Habitaciones habitacion = optionalHabitacion.get();
	    // Return the rooms.
	    return new ResponseEntity<>(habitacion, HttpStatus.OK);
	}
	
	@PutMapping("habitacion/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	  public ResponseEntity<Habitaciones> updateHabitacion(
	      @PathVariable(value = "id") Long id, @Valid @RequestBody DtoHabitacionesRegistro dtoRegistro)
	      throws ResourceNotFoundException {
		
		Optional<Habitaciones> optionalHabitacion = habitacionesService.findById(id);
			
		if (optionalHabitacion.isEmpty()) {
			return new ResponseEntity(new ResponseMessageDto("Habitación no encontrada", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		
		Habitaciones habitacion= optionalHabitacion.get();
	        
        habitacion.setNombre(dtoRegistro.getNombre());
        habitacion.setDescripcion(dtoRegistro.getDescripcion());
        habitacion.setNumCamas(dtoRegistro.getNumCamas());
        habitacion.setNumBanios(dtoRegistro.getNumBanios());
        habitacion.setPrecio(dtoRegistro.getPrecio());
        habitacion.setDisponibilidad(true);
        habitacionesService.save(habitacion);
        return new ResponseEntity(new ResponseMessageDto("Habitación actualizada correctamente", HttpStatus.OK.value()), HttpStatus.OK);
	  }
	
	@DeleteMapping("habitacion/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	  public ResponseEntity<Habitaciones> deleteHabitacion(
	      @PathVariable(value = "id") Long id, @Valid @RequestBody DtoHabitacionesRegistro dtoRegistro)
	      throws ResourceNotFoundException {
		
		Optional<Habitaciones> optionalHabitacion = habitacionesService.findById(id);
			
		if (optionalHabitacion.isEmpty()) {
			return new ResponseEntity(new ResponseMessageDto("Habitación no encontrada", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		
		habitacionesService.delete(id);
      return new ResponseEntity(new ResponseMessageDto("Habitación eliminada correctamente", HttpStatus.OK.value()), HttpStatus.OK);
	  }
	
}
