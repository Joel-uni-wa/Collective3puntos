package relaciones.entity.proyecto.cu.consultarpublicacionintercambio;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarpublicacionintercambio.response.ResponsePublicacionIntercambio;

import java.util.List;

@RestController
@RequestMapping("/publicacion-intercambio")
public class ControllerConsultarPublicacionIntercambio {

    private final ServiceConsultarPublicacionIntercambio serviceConsultarPublicacionIntercambio;

    public ControllerConsultarPublicacionIntercambio(ServiceConsultarPublicacionIntercambio serviceConsultarPublicacionIntercambio) {
        this.serviceConsultarPublicacionIntercambio = serviceConsultarPublicacionIntercambio;
    }

    @GetMapping("/todos")
    public List<ResponsePublicacionIntercambio> listarTodos() {
        return serviceConsultarPublicacionIntercambio.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponsePublicacionIntercambio consultarPublicacionIntercambio(@PathVariable int id) {
        return serviceConsultarPublicacionIntercambio.consultarPublicacionIntercambio(id);
    }
}
