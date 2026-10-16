package relaciones.entity.proyecto.cu.consultarpublicacionintercambio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarpublicacionintercambio.response.ResponsePublicacionIntercambio;
import relaciones.entity.proyecto.dominio.entity.PublicacionIntercambio;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacionIntercambio;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarPublicacionIntercambio {

    private final RepoPublicacionIntercambio repoPublicacionIntercambio;

    public ServiceConsultarPublicacionIntercambio(RepoPublicacionIntercambio repoPublicacionIntercambio) {
        this.repoPublicacionIntercambio = repoPublicacionIntercambio;
    }

    @Transactional(readOnly = true)
    public ResponsePublicacionIntercambio consultarPublicacionIntercambio(int id) {
        PublicacionIntercambio publicacionIntercambio = repoPublicacionIntercambio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Intercambio de publicacion con id " + id + " no existe"));
        return toResponse(publicacionIntercambio);
    }

    @Transactional(readOnly = true)
    public List<ResponsePublicacionIntercambio> listarTodos() {
        return repoPublicacionIntercambio.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponsePublicacionIntercambio toResponse(PublicacionIntercambio publicacionIntercambio) {
        return new ResponsePublicacionIntercambio(
                publicacionIntercambio.getId(),
                publicacionIntercambio.getPublicacion().getId(),
                publicacionIntercambio.getPublicacion().getTitulo(),
                publicacionIntercambio.getNombreSolicitado(),
                publicacionIntercambio.getDescripcion(),
                publicacionIntercambio.getCantidad()
        );
    }
}
