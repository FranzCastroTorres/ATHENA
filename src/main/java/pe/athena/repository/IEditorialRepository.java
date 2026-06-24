package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Editorial;

@Repository
public interface IEditorialRepository extends JpaRepository<Editorial, Integer> {

	// listar solo activos
	List<Editorial> findByEstado(Integer estado);

	// buscar por nombre exacto para validar duplicados / reactivar
	Editorial findByNombre(String nombre);
}