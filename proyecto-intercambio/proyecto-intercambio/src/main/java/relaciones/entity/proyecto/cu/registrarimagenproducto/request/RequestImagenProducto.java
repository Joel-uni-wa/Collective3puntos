package relaciones.entity.proyecto.cu.registrarimagenproducto.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestImagenProducto(
    Integer idProducto,
    String url,
    Integer orden
) {
}
