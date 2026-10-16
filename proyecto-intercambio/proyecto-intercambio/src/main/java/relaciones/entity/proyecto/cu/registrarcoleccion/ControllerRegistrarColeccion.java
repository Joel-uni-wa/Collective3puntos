package relaciones.entity.proyecto.cu.registrarcoleccion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarcoleccion.response.ResponseColeccion;
import relaciones.entity.proyecto.cu.registrarcoleccion.request.RequestColeccion;

@RestController
@RequestMapping("/coleccion")
public class ControllerRegistrarColeccion {

    private final ServiceRegistrarColeccion serviceRegistrarColeccion;

    public ControllerRegistrarColeccion(ServiceRegistrarColeccion serviceRegistrarColeccion) {
        this.serviceRegistrarColeccion = serviceRegistrarColeccion;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseColeccion registrarColeccion(@RequestBody RequestColeccion request) {
        return serviceRegistrarColeccion.registrarColeccion(request);
    }
}
