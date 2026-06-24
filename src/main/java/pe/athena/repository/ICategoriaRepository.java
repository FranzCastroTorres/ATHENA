package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

	// listar solo activos
	List<Categoria> findByEstado(Integer estado);

	// buscar por descripcion exacta para validar duplicados / reactivar
	Categoria findByDescripcion(String descripcion);
}