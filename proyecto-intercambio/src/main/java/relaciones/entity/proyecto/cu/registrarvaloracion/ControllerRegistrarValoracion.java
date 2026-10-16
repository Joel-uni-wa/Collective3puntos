package relaciones.entity.proyecto.cu.registrarvaloracion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarvaloracion.response.ResponseValoracion;
import relaciones.entity.proyecto.cu.registrarvaloracion.request.RequestValoracion;

@RestController
@RequestMapping("/valoracion")
public class ControllerRegistrarValoracion {

    private final ServiceRegistrarValoracion serviceRegistrarValoracion;

    public ControllerRegistrarValoracion(ServiceRegistrarValoracion serviceRegistrarValoracion) {
        this.serviceRegistrarValoracion = serviceRegistrarValoracion;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseValoracion registrarValoracion(@RequestBody RequestValoracion request) {
        return serviceRegistrarValoracion.registrarValoracion(request);
    }
}
