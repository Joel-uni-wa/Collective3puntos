package relaciones.entity.proyecto.cu.consultarreporte;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarreporte.response.ResponseReporte;
import relaciones.entity.proyecto.dominio.entity.Reporte;
import relaciones.entity.proyecto.dominio.repository.RepoReporte;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarReporte {

    private final RepoReporte repoReporte;

    public ServiceConsultarReporte(RepoReporte repoReporte) {
        this.repoReporte = repoReporte;
    }

    @Transactional(readOnly = true)
    public ResponseReporte consultarReporte(int id) {
        Reporte reporte = repoReporte.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte con id " + id + " no existe"));
        return toResponse(reporte);
    }

    @Transactional(readOnly = true)
    public List<ResponseReporte> listarTodos() {
        return repoReporte.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseReporte toResponse(Reporte reporte) {
        return new ResponseReporte(
                reporte.getId(),
                reporte.getUsuario().getId(),
                reporte.getUsuario().getNombre(),
                reporte.getPublicacion().getId(),
                reporte.getPublicacion().getTitulo(),
                reporte.getMotivo(),
                reporte.getEstado(),
                reporte.getFecha()
        );
    }
}
