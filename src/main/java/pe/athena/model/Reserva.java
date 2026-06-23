package pe.athena.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_reserva")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reserva")
	private Integer idReserva;

	@Column(name = "id_usuario")
	private Integer idUsuario;

	@Column(name = "id_libro")
	private Integer idLibro;

	@Column(name = "fecha_reserva")
	private LocalDate fechaReserva;

	@Column(name = "fecha_vencimiento")
	private LocalDate fechaVencimiento;

	@Column(name = "estado")
	private Integer estado = 1;

	@ManyToOne
	@JoinColumn(name = "id_usuario", insertable = false, updatable = false)
	private Usuario objUsuario;

	@ManyToOne
	@JoinColumn(name = "id_libro", insertable = false, updatable = false)
	private Libro objLibro;

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(Integer idLibro) {
		this.idLibro = idLibro;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Usuario getObjUsuario() {
		return objUsuario;
	}

	public void setObjUsuario(Usuario objUsuario) {
		this.objUsuario = objUsuario;
	}

	public Libro getObjLibro() {
		return objLibro;
	}

	public void setObjLibro(Libro objLibro) {
		this.objLibro = objLibro;
	}
}