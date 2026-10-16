package relaciones.entity.proyecto.cu.consultarpublicacion.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponsePublicacion(
    int idPublicacion,
    String titulo,
    String descripcion,
    String tipo,
    String estado,
    LocalDateTime fecha,
    int idUsuario,
    String nombreUsuario,
    int idProducto,
    String nombreProducto,
    BigDecimal precio,
    List<ResponseIntercambio> intercambios
) {

    public record ResponseIntercambio(
        String nombreSolicitado,
        String descripcion,
        Integer cantidad
    ) {
    }
}
