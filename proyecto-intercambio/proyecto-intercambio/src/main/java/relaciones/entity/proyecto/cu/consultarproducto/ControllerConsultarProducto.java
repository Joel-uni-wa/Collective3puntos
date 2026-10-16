package relaciones.entity.proyecto.cu.consultarproducto;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarproducto.response.ResponseProducto;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ControllerConsultarProducto {

    private final ServiceConsultarProducto serviceConsultarProducto;

    public ControllerConsultarProducto(ServiceConsultarProducto serviceConsultarProducto) {
        this.serviceConsultarProducto = serviceConsultarProducto;
    }

    @GetMapping("/todos")
    public List<ResponseProducto> listarTodos() {
        return serviceConsultarProducto.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseProducto consultarProducto(@PathVariable int id) {
        return serviceConsultarProducto.consultarProducto(id);
    }
}
