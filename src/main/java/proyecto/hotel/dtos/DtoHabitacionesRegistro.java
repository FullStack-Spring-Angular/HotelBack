package proyecto.hotel.dtos;

import lombok.Data;

@Data
public class DtoHabitacionesRegistro {
	private String nombre;
	private String descripcion;
	private long numCamas;
	private long numBanios;
	private long precio;
	private boolean disponibilidad;
}
