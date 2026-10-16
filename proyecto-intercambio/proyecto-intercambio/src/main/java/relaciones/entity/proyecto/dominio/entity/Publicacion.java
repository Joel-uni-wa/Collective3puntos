package relaciones.entity.proyecto.dominio.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacion")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_publicacion")
    @SequenceGenerator(
        name = "sec_publicacion",
        sequenceName = "sec_publicacion",
        allocationSize = 1
    )
    @Column(name = "id_publicacion")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublicacionIntercambio> intercambios = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }

    public void agregarIntercambio(String nombreSolicitado, String descripcion, Integer cantidad) {
        PublicacionIntercambio item = new PublicacionIntercambio();
        item.setPublicacion(this);
        item.setNombreSolicitado(nombreSolicitado);
        item.setDescripcion(descripcion);
        item.setCantidad(cantidad);
        intercambios.add(item);
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<PublicacionIntercambio> getIntercambios() {
        return intercambios;
    }

    public void setIntercambios(List<PublicacionIntercambio> intercambios) {
        this.intercambios = intercambios;
    }
}
