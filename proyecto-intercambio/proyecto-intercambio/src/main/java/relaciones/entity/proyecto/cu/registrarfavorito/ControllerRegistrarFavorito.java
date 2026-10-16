package relaciones.entity.proyecto.cu.registrarfavorito;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarfavorito.response.ResponseFavorito;
import relaciones.entity.proyecto.cu.registrarfavorito.request.RequestFavorito;

@RestController
@RequestMapping("/favorito")
public class ControllerRegistrarFavorito {

    private final ServiceRegistrarFavorito serviceRegistrarFavorito;

    public ControllerRegistrarFavorito(ServiceRegistrarFavorito serviceRegistrarFavorito) {
        this.serviceRegistrarFavorito = serviceRegistrarFavorito;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseFavorito registrarFavorito(@RequestBody RequestFavorito request) {
        return serviceRegistrarFavorito.registrarFavorito(request);
    }
}
