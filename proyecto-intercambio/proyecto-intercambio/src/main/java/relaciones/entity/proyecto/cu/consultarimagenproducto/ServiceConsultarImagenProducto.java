package relaciones.entity.proyecto.cu.consultarimagenproducto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarimagenproducto.response.ResponseImagenProducto;
import relaciones.entity.proyecto.dominio.entity.ImagenProducto;
import relaciones.entity.proyecto.dominio.repository.RepoImagenProducto;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarImagenProducto {

    private final RepoImagenProducto repoImagenProducto;

    public ServiceConsultarImagenProducto(RepoImagenProducto repoImagenProducto) {
        this.repoImagenProducto = repoImagenProducto;
    }

    @Transactional(readOnly = true)
    public ResponseImagenProducto consultarImagenProducto(int id) {
        ImagenProducto imagenProducto = repoImagenProducto.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Imagen de producto con id " + id + " no existe"));
        return toResponse(imagenProducto);
    }

    @Transactional(readOnly = true)
    public List<ResponseImagenProducto> listarTodos() {
        return repoImagenProducto.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseImagenProducto toResponse(ImagenProducto imagenProducto) {
        return new ResponseImagenProducto(
                imagenProducto.getId(),
                imagenProducto.getProducto().getId(),
                imagenProducto.getProducto().getNombre(),
                imagenProducto.getUrl(),
                imagenProducto.getOrden()
        );
    }
}
