package relaciones.entity.proyecto.cu.consultarvaloracion.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseValoracion(
    int idValoracion,
    int idUsuario,
    String nombreUsuario,
    int idPublicacion,
    String tituloPublicacion,
    Integer califUsuario,
    Integer califPublicacion,
    String comentario,
    LocalDateTime fecha
) {
}
