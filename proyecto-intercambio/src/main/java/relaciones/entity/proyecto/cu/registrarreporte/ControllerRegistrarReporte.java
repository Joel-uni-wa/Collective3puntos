package relaciones.entity.proyecto.cu.registrarreporte;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarreporte.response.ResponseReporte;
import relaciones.entity.proyecto.cu.registrarreporte.request.RequestReporte;

@RestController
@RequestMapping("/reporte")
public class ControllerRegistrarReporte {

    private final ServiceRegistrarReporte serviceRegistrarReporte;

    public ControllerRegistrarReporte(ServiceRegistrarReporte serviceRegistrarReporte) {
        this.serviceRegistrarReporte = serviceRegistrarReporte;
    }

    @PostMapping("/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseReporte registrarReporte(@RequestBody RequestReporte request) {
        return serviceRegistrarReporte.registrarReporte(request);
    }
}
