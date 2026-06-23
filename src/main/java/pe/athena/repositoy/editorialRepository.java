package pe.athena.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Editorial;

@Repository
public interface editorialRepository extends JpaRepository<Editorial, Integer> {
}
