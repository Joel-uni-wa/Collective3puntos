package relaciones.entity.proyecto.cu.registrarpublicacion.request;

import java.math.BigDecimal;
import java.util.List;

public record RequestPublicacion(
    Integer idUsuario,
    Integer idProducto,
    String titulo,
    String descripcion,
    String tipo,
    List<RequestIntercambio> intercambios
) {

    public record RequestIntercambio(
        String nombreSolicitado,
        String descripcion,
        Integer cantidad
    ) {
    }
}
