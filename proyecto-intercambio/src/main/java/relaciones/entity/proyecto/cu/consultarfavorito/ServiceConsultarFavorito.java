package relaciones.entity.proyecto.cu.consultarfavorito;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarfavorito.response.ResponseFavorito;
import relaciones.entity.proyecto.dominio.entity.Favorito;
import relaciones.entity.proyecto.dominio.repository.RepoFavorito;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarFavorito {

    private final RepoFavorito repoFavorito;

    public ServiceConsultarFavorito(RepoFavorito repoFavorito) {
        this.repoFavorito = repoFavorito;
    }

    @Transactional(readOnly = true)
    public ResponseFavorito consultarFavorito(int id) {
        Favorito favorito = repoFavorito.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Favorito con id " + id + " no existe"));
        return toResponse(favorito);
    }

    @Transactional(readOnly = true)
    public List<ResponseFavorito> listarTodos() {
        return repoFavorito.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseFavorito toResponse(Favorito favorito) {
        return new ResponseFavorito(
                favorito.getId(),
                favorito.getUsuario().getId(),
                favorito.getUsuario().getNombre(),
                favorito.getPublicacion().getId(),
                favorito.getPublicacion().getTitulo(),
                favorito.getFecha()
        );
    }
}
