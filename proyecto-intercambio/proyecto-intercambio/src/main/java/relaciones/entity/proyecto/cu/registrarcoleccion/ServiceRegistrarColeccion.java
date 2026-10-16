package relaciones.entity.proyecto.cu.registrarcoleccion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarcoleccion.ServiceConsultarColeccion;
import relaciones.entity.proyecto.cu.consultarcoleccion.response.ResponseColeccion;
import relaciones.entity.proyecto.cu.registrarcoleccion.request.RequestColeccion;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarColeccion {

    private final RepoCategoria repoCategoria;
    private final RepoColeccion repoColeccion;
    private final ServiceConsultarColeccion serviceConsultarColeccion;

    public ServiceRegistrarColeccion(
            RepoCategoria repoCategoria,
            RepoColeccion repoColeccion,
            ServiceConsultarColeccion serviceConsultarColeccion) {
        this.repoCategoria = repoCategoria;
        this.repoColeccion = repoColeccion;
        this.serviceConsultarColeccion = serviceConsultarColeccion;
    }

    @Transactional
    public ResponseColeccion registrarColeccion(RequestColeccion request) {
        Validar.cuerpo(request);

        int idCategoria = Validar.idObligatorio(request.idCategoria(), "idCategoria");
        String nombre = Validar.texto(request.nombre(), "nombre", 100);
        String descripcion = Validar.textoOpcional(request.descripcion(), "descripcion", 255);
        Categoria categoria = repoCategoria.findById(idCategoria)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "La categoria con id " + idCategoria + " no existe"));

        Coleccion coleccion = new Coleccion();
        coleccion.setCategoria(categoria);
        coleccion.setNombre(nombre);
        coleccion.setDescripcion(descripcion);
        Coleccion guardado = repoColeccion.save(coleccion);

        return serviceConsultarColeccion.consultarColeccion(guardado.getId());
    }
}
