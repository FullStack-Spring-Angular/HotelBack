package proyecto.hotel.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.hotel.interfaces.ListaReservasInterface;
import proyecto.hotel.interfaces.ReservaInfoInterface;
import proyecto.hotel.models.ListaReservas;
import proyecto.hotel.repositories.IListaReservasRepository;

@Service
@Transactional
public class ListaReservasService implements ListaReservasInterface {

	@Autowired
	private IListaReservasRepository listaReservasRepository;
	
	@Override
	public ListaReservas saveListaReservas(ListaReservas listaReservas) {
		ListaReservas reservaResponse = listaReservasRepository.save(listaReservas);
		return reservaResponse;
	}

	@Override
	public List<Object[]> obtenerListasReservasAgrupadas() {
        return listaReservasRepository.findListasReservasGroupedByReserva();
    }
	
	public List<ListaReservas> obtenerListasReservas() {
        return listaReservasRepository.findAll();
    }
	
	public List<ReservaInfoInterface> getByUsuarioId(Long usuarioId) {
        return listaReservasRepository.getByUsuarioId(usuarioId);
    }
	
	public List<ReservaInfoInterface> getByReservaId(Long reservasId){
		return listaReservasRepository.getByReservaId(reservasId);
	}
	
	public void deleteListaReserva(Long id) {
		listaReservasRepository.deleteById(id);
	}
	
	public Optional<ListaReservas> findByIdListaReserva(Long id){
		return listaReservasRepository.findByIdListaReserva(id);
	}
	
	public void updateListaReservas(LocalDate endDate, LocalDate startDate, Long idHabitacion, Long idListaReserva) {
		listaReservasRepository.updateListaReservas(endDate,startDate,idHabitacion,idListaReserva);
	}
}
