package pe.athena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

	// validar acceso (mismo patron del profesor)
	Usuario findByCorreoAndClave(String correo, String clave);
}