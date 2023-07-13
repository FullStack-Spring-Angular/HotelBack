package proyecto.hotel.interfaces;

import java.util.List;

import proyecto.hotel.models.ListaReservas;

public interface ListaReservasInterface {

	ListaReservas saveListaReservas(ListaReservas listaReservas);
	
	public List<Object[]> obtenerListasReservasAgrupadas();

}
