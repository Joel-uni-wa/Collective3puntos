package relaciones.entity.proyecto.cu.registrarpublicacionintercambio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarpublicacionintercambio.response.ResponsePublicacionIntercambio;
import relaciones.entity.proyecto.cu.registrarpublicacionintercambio.request.RequestPublicacionIntercambio;

@RestController
@RequestMapping("/publicacion-intercambio")
public class ControllerRegistrarPublicacionIntercambio {

    private final ServiceRegistrarPublicacionIntercambio serviceRegistrarPublicacionIntercambio;

    public ControllerRegistrarPublicacionIntercambio(ServiceRegistrarPublicacionIntercambio serviceRegistrarPublicacionIntercambio) {
        this.serviceRegistrarPublicacionIntercambio = serviceRegistrarPublicacionIntercambio;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePublicacionIntercambio registrarPublicacionIntercambio(@RequestBody RequestPublicacionIntercambio request) {
        return serviceRegistrarPublicacionIntercambio.registrarPublicacionIntercambio(request);
    }
}
