package relaciones.entity.proyecto.cu.registrarproducto.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestProducto(
    Integer idCategoria,
    Integer idColeccion,
    String nombre,
    Integer cantidad,
    BigDecimal precio,
    List<RequestImagen> imagenes
) {

    public record RequestImagen(
        String url,
        Integer orden
    ) {
    }
}
