package relaciones.entity.proyecto.dominio.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "imagen_producto")
public class ImagenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_imagen_producto")
    @SequenceGenerator(
        name = "sec_imagen_producto",
        sequenceName = "sec_imagen_producto",
        allocationSize = 1
    )
    @Column(name = "id_imagen")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "orden")
    private Integer orden;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
