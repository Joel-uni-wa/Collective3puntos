package relaciones.entity.proyecto.cu.registrarfavorito.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestFavorito(
    Integer idUsuario,
    Integer idPublicacion
) {
}
