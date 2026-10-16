package relaciones.entity.proyecto.cu.registrarimagenproducto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarimagenproducto.response.ResponseImagenProducto;
import relaciones.entity.proyecto.cu.registrarimagenproducto.request.RequestImagenProducto;

@RestController
@RequestMapping("/imagen-producto")
public class ControllerRegistrarImagenProducto {

    private final ServiceRegistrarImagenProducto serviceRegistrarImagenProducto;

    public ControllerRegistrarImagenProducto(ServiceRegistrarImagenProducto serviceRegistrarImagenProducto) {
        this.serviceRegistrarImagenProducto = serviceRegistrarImagenProducto;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseImagenProducto registrarImagenProducto(@RequestBody RequestImagenProducto request) {
        return serviceRegistrarImagenProducto.registrarImagenProducto(request);
    }
}
