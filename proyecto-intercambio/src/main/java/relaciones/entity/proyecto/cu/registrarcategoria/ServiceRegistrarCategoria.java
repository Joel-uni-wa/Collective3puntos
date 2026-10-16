package relaciones.entity.proyecto.cu.registrarcategoria;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarcategoria.ServiceConsultarCategoria;
import relaciones.entity.proyecto.cu.consultarcategoria.response.ResponseCategoria;
import relaciones.entity.proyecto.cu.registrarcategoria.request.RequestCategoria;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarCategoria {

    private final RepoCategoria repoCategoria;
    private final ServiceConsultarCategoria serviceConsultarCategoria;

    public ServiceRegistrarCategoria(
            RepoCategoria repoCategoria,
            ServiceConsultarCategoria serviceConsultarCategoria) {
        this.repoCategoria = repoCategoria;
        this.serviceConsultarCategoria = serviceConsultarCategoria;
    }

    @Transactional
    public ResponseCategoria registrarCategoria(RequestCategoria request) {
        Validar.cuerpo(request);

        String nombre = Validar.texto(request.nombre(), "nombre", 50);
        String descripcion = Validar.textoOpcional(request.descripcion(), "descripcion", 255);

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        Categoria guardado = repoCategoria.save(categoria);

        return serviceConsultarCategoria.consultarCategoria(guardado.getId());
    }
}
