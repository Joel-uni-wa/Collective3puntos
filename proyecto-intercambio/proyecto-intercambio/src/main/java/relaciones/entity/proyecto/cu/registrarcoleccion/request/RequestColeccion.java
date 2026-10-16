package relaciones.entity.proyecto.cu.registrarcoleccion.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestColeccion(
    Integer idCategoria,
    String nombre,
    String descripcion
) {
}
