package relaciones.entity.proyecto.cu.consultarcategoria.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseCategoria(
    int idCategoria,
    String nombre,
    String descripcion
) {
}
