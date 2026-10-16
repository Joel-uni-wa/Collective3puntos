package relaciones.entity.proyecto.cu.consultarimagenproducto;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarimagenproducto.response.ResponseImagenProducto;

import java.util.List;

@RestController
@RequestMapping("/imagen-producto")
public class ControllerConsultarImagenProducto {

    private final ServiceConsultarImagenProducto serviceConsultarImagenProducto;

    public ControllerConsultarImagenProducto(ServiceConsultarImagenProducto serviceConsultarImagenProducto) {
        this.serviceConsultarImagenProducto = serviceConsultarImagenProducto;
    }

    @GetMapping("/todos")
    public List<ResponseImagenProducto> listarTodos() {
        return serviceConsultarImagenProducto.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseImagenProducto consultarImagenProducto(@PathVariable int id) {
        return serviceConsultarImagenProducto.consultarImagenProducto(id);
    }
}
