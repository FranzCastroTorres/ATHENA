package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.SolicitudPrestamo;

@Repository
public interface ISolicitudPrestamoRepository extends JpaRepository<SolicitudPrestamo, Integer> {

	// solicitudes de un usuario especifico (para que vea solo las suyas)
	List<SolicitudPrestamo> findByIdUsuario(Integer idUsuario);
	
	// 7B: solicitudes por estado (pendientes = 1)
	List<SolicitudPrestamo> findByEstado(Integer estado);
}