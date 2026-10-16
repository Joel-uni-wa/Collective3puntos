package relaciones.entity.proyecto.cu.consultarcategoria;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarcategoria.response.ResponseCategoria;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class ControllerConsultarCategoria {

    private final ServiceConsultarCategoria serviceConsultarCategoria;

    public ControllerConsultarCategoria(ServiceConsultarCategoria serviceConsultarCategoria) {
        this.serviceConsultarCategoria = serviceConsultarCategoria;
    }

    @GetMapping("/todos")
    public List<ResponseCategoria> listarTodos() {
        return serviceConsultarCategoria.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseCategoria consultarCategoria(@PathVariable int id) {
        return serviceConsultarCategoria.consultarCategoria(id);
    }
}
