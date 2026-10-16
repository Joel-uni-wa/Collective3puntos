package relaciones.entity.proyecto.cu.consultarproducto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseProducto(
    int idProducto,
    String nombre,
    Integer cantidad,
    BigDecimal precio,
    String categoria,
    String coleccion,
    List<String> imagenes
) {
}
