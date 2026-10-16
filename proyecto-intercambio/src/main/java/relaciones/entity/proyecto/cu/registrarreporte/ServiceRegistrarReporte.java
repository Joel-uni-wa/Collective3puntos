package relaciones.entity.proyecto.cu.registrarreporte;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarreporte.ServiceConsultarReporte;
import relaciones.entity.proyecto.cu.consultarreporte.response.ResponseReporte;
import relaciones.entity.proyecto.cu.registrarreporte.request.RequestReporte;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarReporte {

    private final RepoUsuario repoUsuario;
    private final RepoPublicacion repoPublicacion;
    private final RepoReporte repoReporte;
    private final ServiceConsultarReporte serviceConsultarReporte;

    public ServiceRegistrarReporte(
            RepoUsuario repoUsuario,
            RepoPublicacion repoPublicacion,
            RepoReporte repoReporte,
            ServiceConsultarReporte serviceConsultarReporte) {
        this.repoUsuario = repoUsuario;
        this.repoPublicacion = repoPublicacion;
        this.repoReporte = repoReporte;
        this.serviceConsultarReporte = serviceConsultarReporte;
    }

    @Transactional
    public ResponseReporte registrarReporte(RequestReporte request) {
        Validar.cuerpo(request);

        int idUsuario = Validar.idObligatorio(request.idUsuario(), "idUsuario");
        int idPublicacion = Validar.idObligatorio(request.idPublicacion(), "idPublicacion");
        String motivo = Validar.texto(request.motivo(), "motivo", 255);
        Usuario usuario = repoUsuario.findById(idUsuario)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El usuario con id " + idUsuario + " no existe"));
        Publicacion publicacion = repoPublicacion.findById(idPublicacion)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "La publicacion con id " + idPublicacion + " no existe"));
        if (publicacion.getUsuario().getId().equals(usuario.getId())) {
            throw new SolicitudInvalidaException("No puedes reportar tu propia publicacion");
        }

        Reporte reporte = new Reporte();
        reporte.setUsuario(usuario);
        reporte.setPublicacion(publicacion);
        reporte.setMotivo(motivo);
        reporte.setEstado("PENDIENTE");
        Reporte guardado = repoReporte.save(reporte);

        return serviceConsultarReporte.consultarReporte(guardado.getId());
    }
}
