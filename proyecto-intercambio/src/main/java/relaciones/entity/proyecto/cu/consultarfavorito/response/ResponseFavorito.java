package relaciones.entity.proyecto.cu.consultarfavorito.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseFavorito(
    int idFavorito,
    int idUsuario,
    String nombreUsuario,
    int idPublicacion,
    String tituloPublicacion,
    LocalDateTime fecha
) {
}
