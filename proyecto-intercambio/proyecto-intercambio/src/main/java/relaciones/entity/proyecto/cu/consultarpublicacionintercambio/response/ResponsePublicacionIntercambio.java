package relaciones.entity.proyecto.cu.consultarpublicacionintercambio.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponsePublicacionIntercambio(
    int idIntercambio,
    int idPublicacion,
    String tituloPublicacion,
    String nombreSolicitado,
    String descripcion,
    Integer cantidad
) {
}
