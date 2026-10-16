package relaciones.entity.proyecto.cu.registrarusuario.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestUsuario(
    String nombre,
    String correo,
    String telefono,
    Boolean tipo
) {
}
