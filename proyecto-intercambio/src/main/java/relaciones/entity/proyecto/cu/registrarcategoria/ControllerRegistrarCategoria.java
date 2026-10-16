package relaciones.entity.proyecto.cu.registrarcategoria;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarcategoria.response.ResponseCategoria;
import relaciones.entity.proyecto.cu.registrarcategoria.request.RequestCategoria;

@RestController
@RequestMapping("/categoria")
public class ControllerRegistrarCategoria {

    private final ServiceRegistrarCategoria serviceRegistrarCategoria;

    public ControllerRegistrarCategoria(ServiceRegistrarCategoria serviceRegistrarCategoria) {
        this.serviceRegistrarCategoria = serviceRegistrarCategoria;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCategoria registrarCategoria(@RequestBody RequestCategoria request) {
        return serviceRegistrarCategoria.registrarCategoria(request);
    }
}
