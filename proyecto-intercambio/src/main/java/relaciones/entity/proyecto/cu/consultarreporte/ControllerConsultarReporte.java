package relaciones.entity.proyecto.cu.consultarreporte;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarreporte.response.ResponseReporte;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ControllerConsultarReporte {

    private final ServiceConsultarReporte serviceConsultarReporte;

    public ControllerConsultarReporte(ServiceConsultarReporte serviceConsultarReporte) {
        this.serviceConsultarReporte = serviceConsultarReporte;
    }

    @GetMapping("/todos")
    public List<ResponseReporte> listarTodos() {
        return serviceConsultarReporte.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseReporte consultarReporte(@PathVariable int id) {
        return serviceConsultarReporte.consultarReporte(id);
    }
}
