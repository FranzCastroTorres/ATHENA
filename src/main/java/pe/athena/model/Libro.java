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
@Table(name = "tb_libro")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_libro")
	private Integer idLibro;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "anio")
	private Integer anio;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "imagen")
	private String imagen;

	@Column(name = "ubicacion")
	private String ubicacion;

	@Column(name = "id_autor")
	private Integer idAutor;

	@Column(name = "id_editorial")
	private Integer idEditorial;

	@Column(name = "id_categoria")
	private Integer idCategoria;

	@Column(name = "estado")
	private Integer estado = 1;

	@ManyToOne
	@JoinColumn(name = "id_autor", insertable = false, updatable = false)
	private Autor objAutor;

	@ManyToOne
	@JoinColumn(name = "id_editorial", insertable = false, updatable = false)
	private Editorial objEditorial;

	@ManyToOne
	@JoinColumn(name = "id_categoria", insertable = false, updatable = false)
	private Categoria objCategoria;

	public Integer getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(Integer idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}

	public Integer getIdEditorial() {
		return idEditorial;
	}

	public void setIdEditorial(Integer idEditorial) {
		this.idEditorial = idEditorial;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Autor getObjAutor() {
		return objAutor;
	}

	public void setObjAutor(Autor objAutor) {
		this.objAutor = objAutor;
	}

	public Editorial getObjEditorial() {
		return objEditorial;
	}

	public void setObjEditorial(Editorial objEditorial) {
		this.objEditorial = objEditorial;
	}

	public Categoria getObjCategoria() {
		return objCategoria;
	}

	public void setObjCategoria(Categoria objCategoria) {
		this.objCategoria = objCategoria;
	}
}