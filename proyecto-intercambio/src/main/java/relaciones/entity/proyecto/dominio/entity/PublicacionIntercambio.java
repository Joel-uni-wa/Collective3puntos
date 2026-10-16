package relaciones.entity.proyecto.dominio.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacion_intercambio")
public class PublicacionIntercambio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_publicacion_intercambio")
    @SequenceGenerator(
        name = "sec_publicacion_intercambio",
        sequenceName = "sec_publicacion_intercambio",
        allocationSize = 1
    )
    @Column(name = "id_intercambio")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion;

    @Column(name = "nombre_solicitado", nullable = false, length = 100)
    private String nombreSolicitado;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "cantidad")
    private Integer cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public String getNombreSolicitado() {
        return nombreSolicitado;
    }

    public void setNombreSolicitado(String nombreSolicitado) {
        this.nombreSolicitado = nombreSolicitado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
