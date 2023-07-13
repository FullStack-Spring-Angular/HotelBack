package proyecto.hotel.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DtoUsuario {

	private Long idUsuario;
   	private String username;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
}
