package relaciones.entity.proyecto.cu.registrarreporte.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestReporte(
    Integer idUsuario,
    Integer idPublicacion,
    String motivo
) {
}
