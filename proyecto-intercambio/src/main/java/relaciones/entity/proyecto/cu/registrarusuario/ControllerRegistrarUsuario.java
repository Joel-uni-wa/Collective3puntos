package relaciones.entity.proyecto.cu.registrarusuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarusuario.response.ResponseUsuario;
import relaciones.entity.proyecto.cu.registrarusuario.request.RequestUsuario;

@RestController
@RequestMapping("/usuario")
public class ControllerRegistrarUsuario {

    private final ServiceRegistrarUsuario serviceRegistrarUsuario;

    public ControllerRegistrarUsuario(ServiceRegistrarUsuario serviceRegistrarUsuario) {
        this.serviceRegistrarUsuario = serviceRegistrarUsuario;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUsuario registrarUsuario(@RequestBody RequestUsuario request) {
        return serviceRegistrarUsuario.registrarUsuario(request);
    }
}
