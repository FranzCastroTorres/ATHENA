package pe.athena.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_rol")
public class Rol {

    @Id
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "descripcion", length = 30)
    private String descripcion;

    public Rol() {}

    // getters y setters
}