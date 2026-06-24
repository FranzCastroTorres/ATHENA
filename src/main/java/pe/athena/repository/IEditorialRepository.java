package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Editorial;

@Repository
public interface IEditorialRepository extends JpaRepository<Editorial, Integer> {

	// listar solo los activos (borrado logico)
	List<Editorial> findByEstado(Integer estado);
}