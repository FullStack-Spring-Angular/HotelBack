package proyecto.hotel.models;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lista_reservas")
public class ListaReservas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista_reserva")
    private Long idListaReserva;
	
	@Column(name = "habitacion_id")
	private Long idHabitacion;
	
	@Column(name = "reserva_id")
	private Long idReserva;
	
    @ManyToOne
    @JoinColumn(name = "habitacion_id",insertable = false, updatable = false)
    @NotAudited
    private Habitaciones habitacion;

    private LocalDate startDate;

    private LocalDate endDate;
    
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "reserva_id",insertable = false, updatable = false)
    @NotAudited
    private Reservas reserva;
}
