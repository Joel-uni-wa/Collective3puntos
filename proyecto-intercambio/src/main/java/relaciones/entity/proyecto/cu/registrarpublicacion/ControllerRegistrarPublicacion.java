package relaciones.entity.proyecto.cu.registrarpublicacion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarpublicacion.response.ResponsePublicacion;
import relaciones.entity.proyecto.cu.registrarpublicacion.request.RequestPublicacion;

@RestController
@RequestMapping("/publicacion")
public class ControllerRegistrarPublicacion {

    private final ServiceRegistrarPublicacion serviceRegistrarPublicacion;

    public ControllerRegistrarPublicacion(ServiceRegistrarPublicacion serviceRegistrarPublicacion) {
        this.serviceRegistrarPublicacion = serviceRegistrarPublicacion;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePublicacion registrarPublicacion(@RequestBody RequestPublicacion request) {
        return serviceRegistrarPublicacion.registrarPublicacion(request);
    }

    /** Solo para demostrar la condicion de carrera en los tests (no usar en la pagina real). */
    @PostMapping("/nuevo/sinlock")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePublicacion registrarPublicacionSinLock(@RequestBody RequestPublicacion request) {
        return serviceRegistrarPublicacion.registrarPublicacionSinLock(request);
    }
}
