package proyecto.hotel.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.hotel.interfaces.UsuariosInterface;
import proyecto.hotel.models.Usuarios;
import proyecto.hotel.repositories.IUsuariosRepository;

@Service
public class UsuariosService implements UsuariosInterface {
	
	@Autowired
	IUsuariosRepository usuariosRepository;

	@Override
	public Optional<Usuarios> findByUsername(String username) {
		// TODO Auto-generated method stub
		return usuariosRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		return usuariosRepository.existsByUsername(username);
	}
	
	public Optional<Usuarios> getUserById(Long id){
		return usuariosRepository.findById(id);
	}
	
	public void save(Usuarios usuarios) {
		// TODO Auto-generated method stub
		usuariosRepository.save(usuarios);
	}
	
	public Usuarios findByIdUsuario(Long id) {
		return usuariosRepository.findByIdUsuario(id);
	}

}
