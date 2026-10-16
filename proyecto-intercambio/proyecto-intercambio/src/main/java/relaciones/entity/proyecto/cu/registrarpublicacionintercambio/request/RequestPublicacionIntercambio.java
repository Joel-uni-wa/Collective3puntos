package relaciones.entity.proyecto.cu.registrarpublicacionintercambio.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestPublicacionIntercambio(
    Integer idPublicacion,
    String nombreSolicitado,
    String descripcion,
    Integer cantidad
) {
}
