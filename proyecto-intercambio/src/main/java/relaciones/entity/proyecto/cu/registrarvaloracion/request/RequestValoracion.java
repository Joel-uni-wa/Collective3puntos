package relaciones.entity.proyecto.cu.registrarvaloracion.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestValoracion(
    Integer idUsuario,
    Integer idPublicacion,
    Integer califUsuario,
    Integer califPublicacion,
    String comentario
) {
}
