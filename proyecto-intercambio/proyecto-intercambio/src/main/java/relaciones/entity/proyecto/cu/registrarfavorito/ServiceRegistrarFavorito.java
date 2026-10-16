package relaciones.entity.proyecto.cu.registrarfavorito;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarfavorito.ServiceConsultarFavorito;
import relaciones.entity.proyecto.cu.consultarfavorito.response.ResponseFavorito;
import relaciones.entity.proyecto.cu.registrarfavorito.request.RequestFavorito;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarFavorito {

    private final RepoUsuario repoUsuario;
    private final RepoPublicacion repoPublicacion;
    private final RepoFavorito repoFavorito;
    private final ServiceConsultarFavorito serviceConsultarFavorito;

    public ServiceRegistrarFavorito(
            RepoUsuario repoUsuario,
            RepoPublicacion repoPublicacion,
            RepoFavorito repoFavorito,
            ServiceConsultarFavorito serviceConsultarFavorito) {
        this.repoUsuario = repoUsuario;
        this.repoPublicacion = repoPublicacion;
        this.repoFavorito = repoFavorito;
        this.serviceConsultarFavorito = serviceConsultarFavorito;
    }

    @Transactional
    public ResponseFavorito registrarFavorito(RequestFavorito request) {
        Validar.cuerpo(request);

        int idUsuario = Validar.idObligatorio(request.idUsuario(), "idUsuario");
        int idPublicacion = Validar.idObligatorio(request.idPublicacion(), "idPublicacion");
        Usuario usuario = repoUsuario.findById(idUsuario)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El usuario con id " + idUsuario + " no existe"));
        Publicacion publicacion = repoPublicacion.findById(idPublicacion)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "La publicacion con id " + idPublicacion + " no existe"));
        if (repoFavorito.existsByUsuario_IdAndPublicacion_Id(idUsuario, idPublicacion)) {
            throw new ConflictoException("La publicacion " + idPublicacion
                    + " ya esta en los favoritos del usuario " + idUsuario);
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setPublicacion(publicacion);
        Favorito guardado = repoFavorito.save(favorito);

        return serviceConsultarFavorito.consultarFavorito(guardado.getId());
    }
}
