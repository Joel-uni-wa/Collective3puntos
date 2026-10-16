package relaciones.entity.proyecto.cu.consultarcoleccion.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseColeccion(
    int idColeccion,
    int idCategoria,
    String nombreCategoria,
    String nombre,
    String descripcion
) {
}
