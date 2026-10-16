package relaciones.entity.proyecto.cu.consultarimagenproducto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseImagenProducto(
    int idImagen,
    int idProducto,
    String nombreProducto,
    String url,
    Integer orden
) {
}
