package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Ejemplar;

@Repository
public interface IEjemplarRepository extends JpaRepository<Ejemplar, Integer> {

	// ejemplares disponibles (estado=1) de un libro - lo usara el Prestamo despues
	List<Ejemplar> findByIdLibroAndEstado(Integer idLibro, Integer estado);
}