package pe.athena.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Categoria;

@Repository
public interface categoriaRepository extends JpaRepository<Categoria, Integer> {
}
