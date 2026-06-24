package pe.athena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.athena.model.DetallePrestamo;

@Repository
public interface IDetallePrestamoRepository extends JpaRepository<DetallePrestamo, Integer> {

}