package pe.athena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

	// listar solo los activos (borrado logico)
	List<Categoria> findByEstado(Integer estado);
}