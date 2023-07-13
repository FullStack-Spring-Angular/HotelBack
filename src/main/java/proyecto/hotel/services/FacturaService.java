package proyecto.hotel.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.hotel.models.Factura;
import proyecto.hotel.repositories.IFacturaRepository;

@Service
@Transactional
public class FacturaService {

	@Autowired
	private IFacturaRepository facturaRepository;
	
	public Factura createFactura(Factura factura) {
		Factura facturaResponse = facturaRepository.save(factura);
		return facturaResponse;
	}
	
	public Integer sumTotal(List<Integer> precios) {
		int suma = precios.stream().mapToInt(Integer::intValue).sum();
		return suma;
	}
	
	public String codigoFactura() {
		UUID uuid = UUID.randomUUID();
        String codigo = uuid.toString().replace("-", "").substring(0, 32); // Obtén los primeros 8 caracteres del UUID
        return codigo;
	}
	
	public Integer LongToInt(Long value) {
		long longValue = value;
	 	int intValue = (int) longValue;
	 	return intValue;
	}
	
	public Long IntToLong(int value) {
		int intValue = (int) value;
	 	long longValue = intValue;
	 	return longValue;
	}
}
