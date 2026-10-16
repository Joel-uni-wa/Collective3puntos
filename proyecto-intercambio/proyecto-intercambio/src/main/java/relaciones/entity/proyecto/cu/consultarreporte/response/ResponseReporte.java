package relaciones.entity.proyecto.cu.consultarreporte.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseReporte(
    int idReporte,
    int idUsuario,
    String nombreUsuario,
    int idPublicacion,
    String tituloPublicacion,
    String motivo,
    String estado,
    LocalDateTime fecha
) {
}
