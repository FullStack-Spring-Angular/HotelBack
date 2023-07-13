package proyecto.hotel.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.hotel.interfaces.ReservasInterface;
import proyecto.hotel.models.Reservas;
import proyecto.hotel.repositories.IReservasRepository;

@Service
public class ReservasService implements ReservasInterface{

	@Autowired
    private IReservasRepository reservasRepository;


	@Override
	public Reservas reservarHabitacion(Reservas reserva) {
		Reservas reservaResponse = reservasRepository.save(reserva);
		return reservaResponse;
	}
	
	public Reservas getById(Long id) {
		return reservasRepository.getById(id);
	}
	
	public List<Reservas> getAllReservas(){
		return reservasRepository.findAll();
	}
	
	public void deleteReserva(Long id) {
		reservasRepository.deleteById(id);
	}
}
