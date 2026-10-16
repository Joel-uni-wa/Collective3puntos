package relaciones.entity.proyecto.excepcion;

import java.time.LocalDateTime;

/** Formato JSON en el que se responden todos los errores. */
public record ErrorResponse(
    int status,
    String error,
    String mensaje,
    LocalDateTime fecha
) {
}
