package proyecto.hotel.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DtoReservasRegistro {

	private String usuarioNombre;
	private List<DtoListaReservas> reservas;
	
	@Data
	public static class DtoListaReservas {

	    private Long habitacionId;
	    private LocalDate fechaInicio;
	    private LocalDate fechaFin;
	}
}


