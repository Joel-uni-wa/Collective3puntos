package relaciones.entity.proyecto.cu.registrarproducto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarproducto.response.ResponseProducto;
import relaciones.entity.proyecto.cu.registrarproducto.request.RequestProducto;

@RestController
@RequestMapping("/producto")
public class ControllerRegistrarProducto {

    private final ServiceRegistrarProducto serviceRegistrarProducto;

    public ControllerRegistrarProducto(ServiceRegistrarProducto serviceRegistrarProducto) {
        this.serviceRegistrarProducto = serviceRegistrarProducto;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseProducto registrarProducto(@RequestBody RequestProducto request) {
        return serviceRegistrarProducto.registrarProducto(request);
    }
}
