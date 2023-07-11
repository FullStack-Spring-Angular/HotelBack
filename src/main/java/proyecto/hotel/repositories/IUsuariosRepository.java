package proyecto.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.hotel.models.Usuarios;

import java.util.Optional;

@Repository
public interface IUsuariosRepository extends JpaRepository<Usuarios, Long> {
    //Método para poder buscar un usuario mediante su nombre
    Optional<Usuarios> findByUsername(String username);

    //Método para poder verificar si un usuario existe en nuestra base de datos
    Boolean existsByUsername(String username);
}
