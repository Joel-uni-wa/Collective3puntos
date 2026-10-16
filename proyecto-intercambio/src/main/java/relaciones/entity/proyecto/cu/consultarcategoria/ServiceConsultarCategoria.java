package relaciones.entity.proyecto.cu.consultarcategoria;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarcategoria.response.ResponseCategoria;
import relaciones.entity.proyecto.dominio.entity.Categoria;
import relaciones.entity.proyecto.dominio.repository.RepoCategoria;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarCategoria {

    private final RepoCategoria repoCategoria;

    public ServiceConsultarCategoria(RepoCategoria repoCategoria) {
        this.repoCategoria = repoCategoria;
    }

    @Transactional(readOnly = true)
    public ResponseCategoria consultarCategoria(int id) {
        Categoria categoria = repoCategoria.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria con id " + id + " no existe"));
        return toResponse(categoria);
    }

    @Transactional(readOnly = true)
    public List<ResponseCategoria> listarTodos() {
        return repoCategoria.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseCategoria toResponse(Categoria categoria) {
        return new ResponseCategoria(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
