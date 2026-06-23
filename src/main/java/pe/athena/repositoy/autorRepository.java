package pe.athena.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Autor;

@Repository
public interface autorRepository extends JpaRepository<Autor, Integer> {
}
