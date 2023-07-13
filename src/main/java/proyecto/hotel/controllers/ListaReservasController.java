package proyecto.hotel.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import proyecto.hotel.dtos.DtoHabitacionesRegistro;
import proyecto.hotel.dtos.DtoReservasRegistro.DtoListaReservas;
import proyecto.hotel.dtos.ResponseMessageDto;
import proyecto.hotel.interfaces.ReservaInfoInterface;
import proyecto.hotel.models.Habitaciones;
import proyecto.hotel.models.ListaReservas;
import proyecto.hotel.models.Reservas;
import proyecto.hotel.models.Usuarios;
import proyecto.hotel.services.ListaReservasService;
import proyecto.hotel.services.ReservasService;
import proyecto.hotel.services.UsuariosService;
import proyecto.hotel.services.HabitacionesService;
import proyecto.hotel.services.HabitacionesService.ResourceNotFoundException;

@RestController
@Validated
@RequestMapping("/api/lista/reservas")
public class ListaReservasController {
	
	@Autowired
	private ListaReservasService listaReservasService;
	@Autowired
	private HabitacionesService habitacionesService;
	@Autowired
	private ReservasService reservasService;
	@Autowired
	private UsuariosService usuariosService;

	@GetMapping("/lista")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<List<Object[]>> findListasReservasGroupedByReserva() {

		List<Object[]> listaReservas = listaReservasService.obtenerListasReservasAgrupadas();
	    return new ResponseEntity<>(listaReservas, HttpStatus.OK);
	}
	
	@GetMapping("/lista/all")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<List<ListaReservas>> obtenerListasReservas() {

		List<ListaReservas> listaReservas = listaReservasService.obtenerListasReservas();
	    return new ResponseEntity<>(listaReservas, HttpStatus.OK);
	}
	
	@GetMapping("/lista/all/user/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ReservaInfoInterface>> getByUsuarioId(@PathVariable(value = "id") Long usuarioId) {
        List<ReservaInfoInterface> listaReservas = listaReservasService.getByUsuarioId(usuarioId);
        return new ResponseEntity<>(listaReservas, HttpStatus.OK);
    }
	
	@GetMapping("/lista/all/reservas/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ReservaInfoInterface>> getByReservaId(@PathVariable(value = "id") Long reservaId) {
        List<ReservaInfoInterface> listaReservas = listaReservasService.getByReservaId(reservaId);
        return new ResponseEntity<>(listaReservas, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") Long id){
		listaReservasService.deleteListaReserva(id);
		return new ResponseEntity(new ResponseMessageDto("Listado reserva eliminado correctamente", HttpStatus.OK.value()), HttpStatus.OK);
	}
	
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> updateListadoReserva(@PathVariable(value = "id") Long id, @Valid @RequestBody DtoListaReservas dtoRegistro)
			throws ResourceNotFoundException {
				
		Optional<ListaReservas> listaReservasOptional = listaReservasService.findByIdListaReserva(id);
		
		if (listaReservasOptional == null) {
			return new ResponseEntity(new ResponseMessageDto("Lista reserva no encontrada", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		
		ListaReservas listaReservas = listaReservasOptional.get();
		//Obtengo el usuario
		Reservas reservas = reservasService.getById(listaReservas.getIdReserva());
		
		//Obtengo la habitacion con el viejo id y le seteo y actualizo el estado como disponible
		Optional<Habitaciones> optionalOldHabitacion = habitacionesService.findById(listaReservas.getIdHabitacion());
		//Obtengo  la habitacion con el nuevo id y le seteo y actualizo el estado como no disponible
		Optional<Habitaciones> optionalNewHabitacion = habitacionesService.findById(dtoRegistro.getHabitacionId());
		
		Habitaciones oldHabitacion = optionalOldHabitacion.get();
		Habitaciones newHabitacion = optionalNewHabitacion.get();
		
		//Seteo a disponible la habitacion antigua
		oldHabitacion.setDisponibilidad(true);
		oldHabitacion.setUser(null);
		habitacionesService.save(oldHabitacion);
		///Seteo a  no disponible la habitacion nueva
		newHabitacion.setDisponibilidad(false);
		newHabitacion.setUser(reservas.getUser());
		habitacionesService.save(newHabitacion);
		
		listaReservasService.updateListaReservas(dtoRegistro.getFechaFin(), dtoRegistro.getFechaInicio(), dtoRegistro.getHabitacionId(), id);
        return new ResponseEntity(new ResponseMessageDto("Listado reserva actualizada correctamente", HttpStatus.OK.value()), HttpStatus.OK);
		
	}
}

