package pe.athena.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_editorial")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    private Integer idEditorial;

    @Column(name = "nombre", length = 60)
    private String nombre;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "direccion", length = 120)
    private String direccion;

    @Column(name = "sitio_web", length = 120)
    private String sitioWeb;

    @Column(name = "estado")
    private Integer estado = 1;

    public Editorial() {}

    // getters y setters
}