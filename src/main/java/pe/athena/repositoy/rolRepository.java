package pe.athena.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Rol;

@Repository
public interface rolRepository extends JpaRepository<Rol, Integer> {
}
