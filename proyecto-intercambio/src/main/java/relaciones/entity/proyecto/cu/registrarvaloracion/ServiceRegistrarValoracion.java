package relaciones.entity.proyecto.cu.registrarvaloracion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarvaloracion.ServiceConsultarValoracion;
import relaciones.entity.proyecto.cu.consultarvaloracion.response.ResponseValoracion;
import relaciones.entity.proyecto.cu.registrarvaloracion.request.RequestValoracion;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarValoracion {

    private final RepoUsuario repoUsuario;
    private final RepoPublicacion repoPublicacion;
    private final RepoValoracion repoValoracion;
    private final ServiceConsultarValoracion serviceConsultarValoracion;

    public ServiceRegistrarValoracion(
            RepoUsuario repoUsuario,
            RepoPublicacion repoPublicacion,
            RepoValoracion repoValoracion,
            ServiceConsultarValoracion serviceConsultarValoracion) {
        this.repoUsuario = repoUsuario;
        this.repoPublicacion = repoPublicacion;
        this.repoValoracion = repoValoracion;
        this.serviceConsultarValoracion = serviceConsultarValoracion;
    }

    @Transactional
    public ResponseValoracion registrarValoracion(RequestValoracion request) {
        Validar.cuerpo(request);

        int idUsuario = Validar.idObligatorio(request.idUsuario(), "idUsuario");
        int idPublicacion = Validar.idObligatorio(request.idPublicacion(), "idPublicacion");
        int califUsuario = Validar.rango(request.califUsuario(), "califUsuario", 1, 5);
        int califPublicacion = Validar.rango(request.califPublicacion(), "califPublicacion", 1, 5);
        String comentario = Validar.textoOpcional(request.comentario(), "comentario", 255);
        Usuario usuario = repoUsuario.findById(idUsuario)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El usuario con id " + idUsuario + " no existe"));
        Publicacion publicacion = repoPublicacion.findById(idPublicacion)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "La publicacion con id " + idPublicacion + " no existe"));
        if (publicacion.getUsuario().getId().equals(usuario.getId())) {
            throw new SolicitudInvalidaException("No puedes valorar tu propia publicacion");
        }
        if (repoValoracion.existsByUsuario_IdAndPublicacion_Id(idUsuario, idPublicacion)) {
            throw new ConflictoException("El usuario " + idUsuario
                    + " ya valoro la publicacion " + idPublicacion);
        }

        Valoracion valoracion = new Valoracion();
        valoracion.setUsuario(usuario);
        valoracion.setPublicacion(publicacion);
        valoracion.setCalifUsuario(califUsuario);
        valoracion.setCalifPublicacion(califPublicacion);
        valoracion.setComentario(comentario);
        Valoracion guardado = repoValoracion.save(valoracion);

        return serviceConsultarValoracion.consultarValoracion(guardado.getId());
    }
}
