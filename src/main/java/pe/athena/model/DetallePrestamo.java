package pe.athena.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_detalle_prestamo")
public class DetallePrestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle")
	private Integer idDetalle;

	@Column(name = "id_prestamo")
	private Integer idPrestamo;

	@Column(name = "id_ejemplar")
	private Integer idEjemplar;

	@Column(name = "observacion")
	private String observacion;

	@ManyToOne
	@JoinColumn(name = "id_prestamo", insertable = false, updatable = false)
	private Prestamo objPrestamo;

	@ManyToOne
	@JoinColumn(name = "id_ejemplar", insertable = false, updatable = false)
	private Ejemplar objEjemplar;

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Integer getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(Integer idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public Integer getIdEjemplar() {
		return idEjemplar;
	}

	public void setIdEjemplar(Integer idEjemplar) {
		this.idEjemplar = idEjemplar;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Prestamo getObjPrestamo() {
		return objPrestamo;
	}

	public void setObjPrestamo(Prestamo objPrestamo) {
		this.objPrestamo = objPrestamo;
	}

	public Ejemplar getObjEjemplar() {
		return objEjemplar;
	}

	public void setObjEjemplar(Ejemplar objEjemplar) {
		this.objEjemplar = objEjemplar;
	}
}