package relaciones.entity.proyecto.cu.registrarimagenproducto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarimagenproducto.ServiceConsultarImagenProducto;
import relaciones.entity.proyecto.cu.consultarimagenproducto.response.ResponseImagenProducto;
import relaciones.entity.proyecto.cu.registrarimagenproducto.request.RequestImagenProducto;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarImagenProducto {

    private final RepoProducto repoProducto;
    private final RepoImagenProducto repoImagenProducto;
    private final ServiceConsultarImagenProducto serviceConsultarImagenProducto;

    public ServiceRegistrarImagenProducto(
            RepoProducto repoProducto,
            RepoImagenProducto repoImagenProducto,
            ServiceConsultarImagenProducto serviceConsultarImagenProducto) {
        this.repoProducto = repoProducto;
        this.repoImagenProducto = repoImagenProducto;
        this.serviceConsultarImagenProducto = serviceConsultarImagenProducto;
    }

    @Transactional
    public ResponseImagenProducto registrarImagenProducto(RequestImagenProducto request) {
        Validar.cuerpo(request);

        int idProducto = Validar.idObligatorio(request.idProducto(), "idProducto");
        String url = Validar.texto(request.url(), "url", 255);
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new SolicitudInvalidaException("La url debe empezar con http:// o https://");
        }
        int orden = request.orden() == null ? 1 : Validar.minimo(request.orden(), "orden", 1);
        Producto producto = repoProducto.findById(idProducto)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El producto con id " + idProducto + " no existe"));

        ImagenProducto imagen = new ImagenProducto();
        imagen.setProducto(producto);
        imagen.setUrl(url);
        imagen.setOrden(orden);
        ImagenProducto guardado = repoImagenProducto.save(imagen);

        return serviceConsultarImagenProducto.consultarImagenProducto(guardado.getId());
    }
}
