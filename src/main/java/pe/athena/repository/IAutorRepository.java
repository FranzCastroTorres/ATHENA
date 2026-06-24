package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Autor;

@Repository
public interface IAutorRepository extends JpaRepository<Autor, Integer> {

	// listar solo activos
	List<Autor> findByEstado(Integer estado);

	// buscar por nombre exacto para validar duplicados / reactivar
	Autor findByNombre(String nombre);
}