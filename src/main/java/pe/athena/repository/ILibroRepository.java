package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Libro;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Integer> {

	// listar solo los activos (borrado logico)
	List<Libro> findByEstado(Integer estado);

	// consulta de la rubrica: libros por categoria (Query Method, sin @Query)
	List<Libro> findByIdCategoria(Integer idCategoria);
}