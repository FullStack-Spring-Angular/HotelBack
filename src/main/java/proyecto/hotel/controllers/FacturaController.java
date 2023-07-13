package proyecto.hotel.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.hotel.dtos.ResponseMessageDto;
import proyecto.hotel.dtos.DtoReservasRegistro.DtoListaReservas;
import proyecto.hotel.interfaces.ReservaInfoInterface;
import proyecto.hotel.models.Factura;
import proyecto.hotel.models.ListaReservas;
import proyecto.hotel.models.Usuarios;
import proyecto.hotel.services.FacturaService;
import proyecto.hotel.services.ListaReservasService;
import proyecto.hotel.services.UsuariosService;

@RestController
@RequestMapping("/api/factura/")
public class FacturaController {

	@Autowired
	private FacturaService facturaService;
	@Autowired
	private ListaReservasService listaReservasService;
	@Autowired
	private UsuariosService usuariosService;
	
	@PostMapping("register/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> saveFactura(@PathVariable(value = "id") Long userId){
		
		Usuarios usuario = usuariosService.findByIdUsuario(userId);
		
		List<Integer> numeros = new ArrayList<>();
		List<ReservaInfoInterface> listaReservas = listaReservasService.getByUsuarioId(userId);
		for (ReservaInfoInterface reserva : listaReservas) {
		 	numeros.add(facturaService.LongToInt(reserva.getprecioHabitacion()));
		}
		
		int totalFactura = facturaService.sumTotal(numeros);
		Factura factura = new Factura();
		factura.setTotalFactura(facturaService.IntToLong(totalFactura));
		factura.setCodigoFactura(facturaService.codigoFactura());
		factura.setFechaEmision(LocalDate.now());
		factura.setUsuario(usuario);
		Factura newFactura = facturaService.createFactura(factura);
		return new ResponseEntity(new ResponseMessageDto("Factura almacenada correctamente", HttpStatus.OK.value()), HttpStatus.OK);
	}
}
