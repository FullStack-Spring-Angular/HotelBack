package proyecto.hotel.dtos;

import lombok.Data;
import proyecto.hotel.models.Usuarios;

//Esta clase va a ser la que nos devolverá la información con el token y el tipo que tenga este
@Data
public class DtoAuthRespuesta {
    private String accessToken;
    private String tokenType = "Bearer ";
    private Usuarios usuarios;

    public DtoAuthRespuesta(String accessToken, Usuarios usuarios) {
        this.accessToken = accessToken;
        this.usuarios = usuarios;
    }
}
