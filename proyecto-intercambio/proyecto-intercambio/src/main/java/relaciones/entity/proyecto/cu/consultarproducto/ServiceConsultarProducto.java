package relaciones.entity.proyecto.cu.consultarproducto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarproducto.response.ResponseProducto;
import relaciones.entity.proyecto.dominio.entity.Producto;
import relaciones.entity.proyecto.dominio.repository.RepoProducto;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarProducto {

    private final RepoProducto repoProducto;

    public ServiceConsultarProducto(RepoProducto repoProducto) {
        this.repoProducto = repoProducto;
    }

    @Transactional(readOnly = true)
    public ResponseProducto consultarProducto(int id) {
        Producto producto = repoProducto.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + id + " no existe"));
        return toResponse(producto);
    }

    @Transactional(readOnly = true)
    public List<ResponseProducto> listarTodos() {
        return repoProducto.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseProducto toResponse(Producto producto) {
        return new ResponseProducto(
                producto.getId(),
                producto.getNombre(),
                producto.getCantidad(),
                producto.getPrecio(),
                producto.getCategoria().getNombre(),
                producto.getColeccion() != null ? producto.getColeccion().getNombre() : null,
                producto.getImagenes().stream().map(i -> i.getUrl()).toList()
        );
    }
}
