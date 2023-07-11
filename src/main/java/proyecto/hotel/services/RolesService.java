package proyecto.hotel.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.hotel.interfaces.RolesInterface;
import proyecto.hotel.models.Roles;
import proyecto.hotel.repositories.IRolesRepository;

@Service
public class RolesService implements RolesInterface {
	
	@Autowired
	IRolesRepository rolesRepository;

	@Override
	public Optional<Roles> findByName(String name) {
		// TODO Auto-generated method stub
		return rolesRepository.findByName(name);
	}

}
