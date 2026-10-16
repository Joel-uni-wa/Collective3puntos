package relaciones.entity.proyecto.cu.registrarpublicacionintercambio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarpublicacionintercambio.ServiceConsultarPublicacionIntercambio;
import relaciones.entity.proyecto.cu.consultarpublicacionintercambio.response.ResponsePublicacionIntercambio;
import relaciones.entity.proyecto.cu.registrarpublicacionintercambio.request.RequestPublicacionIntercambio;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarPublicacionIntercambio {

    private final RepoPublicacion repoPublicacion;
    private final RepoPublicacionIntercambio repoPublicacionIntercambio;
    private final ServiceConsultarPublicacionIntercambio serviceConsultarPublicacionIntercambio;

    public ServiceRegistrarPublicacionIntercambio(
            RepoPublicacion repoPublicacion,
            RepoPublicacionIntercambio repoPublicacionIntercambio,
            ServiceConsultarPublicacionIntercambio serviceConsultarPublicacionIntercambio) {
        this.repoPublicacion = repoPublicacion;
        this.repoPublicacionIntercambio = repoPublicacionIntercambio;
        this.serviceConsultarPublicacionIntercambio = serviceConsultarPublicacionIntercambio;
    }

    @Transactional
    public ResponsePublicacionIntercambio registrarPublicacionIntercambio(RequestPublicacionIntercambio request) {
        Validar.cuerpo(request);

        int idPublicacion = Validar.idObligatorio(request.idPublicacion(), "idPublicacion");
        String nombreSolicitado = Validar.texto(request.nombreSolicitado(), "nombreSolicitado", 100);
        String descripcion = Validar.textoOpcional(request.descripcion(), "descripcion", 255);
        int cantidad = request.cantidad() == null ? 1 : Validar.minimo(request.cantidad(), "cantidad", 1);
        Publicacion publicacion = repoPublicacion.findById(idPublicacion)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "La publicacion con id " + idPublicacion + " no existe"));
        if (publicacion.getTipo().equals("VENTA")) {
            throw new SolicitudInvalidaException("La publicacion " + idPublicacion
                    + " es solo de venta, no acepta productos a cambio");
        }

        PublicacionIntercambio intercambio = new PublicacionIntercambio();
        intercambio.setPublicacion(publicacion);
        intercambio.setNombreSolicitado(nombreSolicitado);
        intercambio.setDescripcion(descripcion);
        intercambio.setCantidad(cantidad);
        PublicacionIntercambio guardado = repoPublicacionIntercambio.save(intercambio);

        return serviceConsultarPublicacionIntercambio.consultarPublicacionIntercambio(guardado.getId());
    }
}
