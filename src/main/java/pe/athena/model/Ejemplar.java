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
@Table(name = "tb_ejemplar")
public class Ejemplar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ejemplar")
	private Integer idEjemplar;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "id_libro")
	private Integer idLibro;

	@Column(name = "estado")
	private Integer estado = 1;

	@Column(name = "observacion")
	private String observacion;

	@ManyToOne
	@JoinColumn(name = "id_libro", insertable = false, updatable = false)
	private Libro objLibro;

	public Integer getIdEjemplar() {
		return idEjemplar;
	}

	public void setIdEjemplar(Integer idEjemplar) {
		this.idEjemplar = idEjemplar;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(Integer idLibro) {
		this.idLibro = idLibro;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Libro getObjLibro() {
		return objLibro;
	}

	public void setObjLibro(Libro objLibro) {
		this.objLibro = objLibro;
	}
}