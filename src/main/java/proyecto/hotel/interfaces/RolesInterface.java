package proyecto.hotel.interfaces;

import java.util.Optional;

import proyecto.hotel.models.Roles;

public interface RolesInterface {

	//Método para buscar un role por su nombre en nuestra base de datos
    Optional<Roles> findByName(String name);
}
