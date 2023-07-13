package proyecto.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.hotel.models.Factura;

public interface IFacturaRepository extends JpaRepository<Factura, Long>{

}
