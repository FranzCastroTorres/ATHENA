package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Prestamo;

@Repository
public interface IPrestamoRepository extends JpaRepository<Prestamo, Integer> {

	// consulta de rubrica: prestamos por usuario (para sprint posterior)
	List<Prestamo> findByIdUsuario(Integer idUsuario);
}