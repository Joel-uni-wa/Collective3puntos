package relaciones.entity.proyecto.cu.consultarvaloracion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarvaloracion.response.ResponseValoracion;
import relaciones.entity.proyecto.dominio.entity.Valoracion;
import relaciones.entity.proyecto.dominio.repository.RepoValoracion;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarValoracion {

    private final RepoValoracion repoValoracion;

    public ServiceConsultarValoracion(RepoValoracion repoValoracion) {
        this.repoValoracion = repoValoracion;
    }

    @Transactional(readOnly = true)
    public ResponseValoracion consultarValoracion(int id) {
        Valoracion valoracion = repoValoracion.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Valoracion con id " + id + " no existe"));
        return toResponse(valoracion);
    }

    @Transactional(readOnly = true)
    public List<ResponseValoracion> listarTodos() {
        return repoValoracion.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseValoracion toResponse(Valoracion valoracion) {
        return new ResponseValoracion(
                valoracion.getId(),
                valoracion.getUsuario().getId(),
                valoracion.getUsuario().getNombre(),
                valoracion.getPublicacion().getId(),
                valoracion.getPublicacion().getTitulo(),
                valoracion.getCalifUsuario(),
                valoracion.getCalifPublicacion(),
                valoracion.getComentario(),
                valoracion.getFecha()
        );
    }
}
