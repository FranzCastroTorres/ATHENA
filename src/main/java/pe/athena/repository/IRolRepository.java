package pe.athena.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {

}

