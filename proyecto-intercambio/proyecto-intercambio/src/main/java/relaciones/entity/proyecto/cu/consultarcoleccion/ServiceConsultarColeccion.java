package relaciones.entity.proyecto.cu.consultarcoleccion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarcoleccion.response.ResponseColeccion;
import relaciones.entity.proyecto.dominio.entity.Coleccion;
import relaciones.entity.proyecto.dominio.repository.RepoColeccion;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarColeccion {

    private final RepoColeccion repoColeccion;

    public ServiceConsultarColeccion(RepoColeccion repoColeccion) {
        this.repoColeccion = repoColeccion;
    }

    @Transactional(readOnly = true)
    public ResponseColeccion consultarColeccion(int id) {
        Coleccion coleccion = repoColeccion.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Coleccion con id " + id + " no existe"));
        return toResponse(coleccion);
    }

    @Transactional(readOnly = true)
    public List<ResponseColeccion> listarTodos() {
        return repoColeccion.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseColeccion toResponse(Coleccion coleccion) {
        return new ResponseColeccion(
                coleccion.getId(),
                coleccion.getCategoria().getId(),
                coleccion.getCategoria().getNombre(),
                coleccion.getNombre(),
                coleccion.getDescripcion()
        );
    }
}
