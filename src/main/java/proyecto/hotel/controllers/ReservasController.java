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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.hotel.dtos.DtoReservasRegistro;
import proyecto.hotel.dtos.DtoReservasRegistro.DtoListaReservas;
import proyecto.hotel.dtos.ResponseMessageDto;
import proyecto.hotel.models.Habitaciones;
import proyecto.hotel.models.ListaReservas;
import proyecto.hotel.models.Reservas;
import proyecto.hotel.models.Usuarios;
import proyecto.hotel.services.HabitacionesService;
import proyecto.hotel.services.ListaReservasService;
import proyecto.hotel.services.ReservasService;
import proyecto.hotel.services.UsuariosService;

@RestController
@Validated
@RequestMapping("/api/reservas/")
public class ReservasController {
	
	@Autowired
	private ReservasService reservasService;
	@Autowired
	private UsuariosService usuariosService;
	@Autowired
	private HabitacionesService habitacionesService;
	@Autowired
	private ListaReservasService listaReservasService;

	@PostMapping("register")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> registrar(@RequestBody DtoReservasRegistro dtoReservasRegistro) {

		Optional<Usuarios> usuarioOptional = usuariosService.findByUsername(dtoReservasRegistro.getUsuarioNombre());
		
		if (!usuarioOptional.isEmpty()) {
			Usuarios usuario = usuarioOptional.get();
	        Reservas newReserva = new Reservas();
	        newReserva.setIdUsuario(usuario.getIdUsuario());
	        Reservas reservaResponse = reservasService.reservarHabitacion(newReserva);
		
	        List<DtoListaReservas> reservas = dtoReservasRegistro.getReservas();
        
	        for (DtoListaReservas reserva : reservas) {
	        	
	        	Optional<Habitaciones> habitacionOptional = habitacionesService.findById(reserva.getHabitacionId());
	         
	        	if (!habitacionOptional.isEmpty()) {
		          	Habitaciones habitacion = habitacionOptional.get();
		          	ListaReservas listaReservas = new ListaReservas();
		          	
		          	listaReservas.setStartDate(reserva.getFechaInicio());
		          	listaReservas.setEndDate(reserva.getFechaFin());
		          	listaReservas.setIdHabitacion(habitacion.getIdHabitacion());
		          	listaReservas.setIdReserva(reservaResponse.getIdReserva());
		          	listaReservas = listaReservasService.saveListaReservas(listaReservas);
		          	if (listaReservas != null) {
		          		habitacion.setUser(usuario);
		          		habitacion.setDisponibilidad(false);
		          		habitacionesService.save(habitacion);
		          		newReserva.setEstadoReserva(true);
		          		reservasService.reservarHabitacion(newReserva);
		          	} 
	        	}
	        }
        }
        return new ResponseEntity(new ResponseMessageDto("Reserva registrada correctamente", HttpStatus.OK.value()), HttpStatus.OK);
    }
	
	@GetMapping("reservas")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<List<Reservas>> getAllReservas(){
		List<Reservas> reservas = reservasService.getAllReservas();
		
		return new ResponseEntity<>(reservas, HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> deleteListReserva(@PathVariable(value = "id") Long id){
		reservasService.deleteReserva(id);
		return new ResponseEntity(new ResponseMessageDto("Eliminado correctamente", HttpStatus.OK.value()), HttpStatus.OK);
	}
	
}
