package proyecto.hotel.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reservas implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;
    private boolean estadoReserva;
    @Column(name = "usuario_id")
	private Long idUsuario;
    
    
	@ManyToOne
	@JoinColumn(name = "usuario_id",insertable = false, updatable = false)
    private Usuarios user;
	
	@OneToMany(mappedBy = "reserva", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
    private List<ListaReservas> listaReservas = new ArrayList<>();
	
	
}
