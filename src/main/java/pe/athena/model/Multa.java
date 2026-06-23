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
@Table(name = "tb_multa")
public class Multa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_multa")
	private Integer idMulta;

	@Column(name = "id_prestamo")
	private Integer idPrestamo;

	@Column(name = "monto")
	private Double monto;

	@Column(name = "motivo")
	private String motivo;

	@Column(name = "fecha_multa")
	private LocalDate fechaMulta;

	@Column(name = "fecha_pago")
	private LocalDate fechaPago;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "estado")
	private Integer estado = 1;

	@ManyToOne
	@JoinColumn(name = "id_prestamo", insertable = false, updatable = false)
	private Prestamo objPrestamo;

	public Integer getIdMulta() {
		return idMulta;
	}

	public void setIdMulta(Integer idMulta) {
		this.idMulta = idMulta;
	}

	public Integer getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(Integer idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public LocalDate getFechaMulta() {
		return fechaMulta;
	}

	public void setFechaMulta(LocalDate fechaMulta) {
		this.fechaMulta = fechaMulta;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Prestamo getObjPrestamo() {
		return objPrestamo;
	}

	public void setObjPrestamo(Prestamo objPrestamo) {
		this.objPrestamo = objPrestamo;
	}
}