package relaciones.entity.proyecto.dominio.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "valoracion")
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_valoracion")
    @SequenceGenerator(
        name = "sec_valoracion",
        sequenceName = "sec_valoracion",
        allocationSize = 1
    )
    @Column(name = "id_valoracion")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion;

    @Column(name = "calif_usuario")
    private Integer califUsuario;

    @Column(name = "calif_publicacion")
    private Integer califPublicacion;

    @Column(name = "comentario", length = 255)
    private String comentario;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @PrePersist
    protected void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Integer getCalifUsuario() {
        return califUsuario;
    }

    public void setCalifUsuario(Integer califUsuario) {
        this.califUsuario = califUsuario;
    }

    public Integer getCalifPublicacion() {
        return califPublicacion;
    }

    public void setCalifPublicacion(Integer califPublicacion) {
        this.califPublicacion = califPublicacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
