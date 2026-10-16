package relaciones.entity.proyecto.cu.consultarusuario.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseUsuario(
    int idUsuario,
    String nombre,
    String correo,
    String telefono,
    Boolean tipo
) {
}
