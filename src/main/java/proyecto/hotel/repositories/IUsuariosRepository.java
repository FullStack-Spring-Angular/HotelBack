package proyecto.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import proyecto.hotel.models.Usuarios;

import java.util.Optional;

public interface IUsuariosRepository extends JpaRepository<Usuarios, Long> {
    //Método para poder buscar un usuario mediante su nombre
    Optional<Usuarios> findByUsername(String username);

    //Método para poder verificar si un usuario existe en nuestra base de datos
    Boolean existsByUsername(String username);
    
    @Query("SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario")
    Usuarios findByIdUsuario(Long idUsuario);
}
