package relaciones.entity.proyecto.cu.registrarpublicacion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarpublicacion.ServiceConsultarPublicacion;
import relaciones.entity.proyecto.cu.consultarpublicacion.response.ResponsePublicacion;
import relaciones.entity.proyecto.cu.registrarpublicacion.request.RequestPublicacion;
import relaciones.entity.proyecto.dominio.entity.Producto;
import relaciones.entity.proyecto.dominio.entity.Publicacion;
import relaciones.entity.proyecto.dominio.entity.Usuario;
import relaciones.entity.proyecto.dominio.repository.RepoProducto;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacion;
import relaciones.entity.proyecto.dominio.repository.RepoUsuario;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.util.List;

/**
 * Registra una publicacion (cabecera) junto con los productos que se piden a
 * cambio (detalle: publicacion_intercambio).
 *
 * Regla de negocio: un producto solo puede tener UNA publicacion ACTIVA.
 * Para que la regla se cumpla aunque dos usuarios publiquen el mismo producto
 * en el mismo instante, se bloquea la fila del producto (SELECT ... FOR UPDATE).
 */
@Service
public class ServiceRegistrarPublicacion {

    private static final String ESTADO_ACTIVA = "ACTIVA";

    private final RepoUsuario repoUsuario;
    private final RepoProducto repoProducto;
    private final RepoPublicacion repoPublicacion;
    private final ServiceConsultarPublicacion serviceConsultarPublicacion;
    private final long pausaMs;

    public ServiceRegistrarPublicacion(
            RepoUsuario repoUsuario,
            RepoProducto repoProducto,
            RepoPublicacion repoPublicacion,
            ServiceConsultarPublicacion serviceConsultarPublicacion,
            @Value("${app.demo.pausa-ms:0}") long pausaMs) {
        this.repoUsuario = repoUsuario;
        this.repoProducto = repoProducto;
        this.repoPublicacion = repoPublicacion;
        this.serviceConsultarPublicacion = serviceConsultarPublicacion;
        this.pausaMs = pausaMs;
    }

    /** Version correcta: bloquea el producto mientras se revisa y se guarda. */
    @Transactional
    public ResponsePublicacion registrarPublicacion(RequestPublicacion request) {
        return registrar(request, true);
    }

    /** Version SIN bloqueo. Existe solo para demostrar la condicion de carrera en los tests. */
    @Transactional
    public ResponsePublicacion registrarPublicacionSinLock(RequestPublicacion request) {
        return registrar(request, false);
    }

    private ResponsePublicacion registrar(RequestPublicacion request, boolean conBloqueo) {
        // 1) Validar y armar la publicacion en memoria (todavia sin tocar la base de datos)
        Validar.cuerpo(request);

        int idUsuario = Validar.idObligatorio(request.idUsuario(), "idUsuario");
        int idProducto = Validar.idObligatorio(request.idProducto(), "idProducto");
        String titulo = Validar.texto(request.titulo(), "titulo", 100);
        String descripcion = Validar.textoOpcional(request.descripcion(), "descripcion", 255);
        String tipo = Validar.texto(request.tipo(), "tipo", 20).toUpperCase();
        if (!tipo.equals("VENTA") && !tipo.equals("INTERCAMBIO") && !tipo.equals("AMBOS")) {
            throw new SolicitudInvalidaException("El tipo debe ser VENTA, INTERCAMBIO o AMBOS");
        }

        List<RequestPublicacion.RequestIntercambio> intercambios =
                request.intercambios() == null ? List.of() : request.intercambios();
        if (tipo.equals("VENTA") && !intercambios.isEmpty()) {
            throw new SolicitudInvalidaException(
                    "Una publicacion de tipo VENTA no puede tener productos a cambio");
        }
        if (!tipo.equals("VENTA") && intercambios.isEmpty()) {
            throw new SolicitudInvalidaException(
                    "Una publicacion de tipo " + tipo + " debe indicar al menos un producto a cambio");
        }

        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(titulo);
        publicacion.setDescripcion(descripcion);
        publicacion.setTipo(tipo);
        publicacion.setEstado(ESTADO_ACTIVA);

        for (RequestPublicacion.RequestIntercambio item : intercambios) {
            Validar.cuerpo(item);
            String nombreSolicitado = Validar.texto(item.nombreSolicitado(), "nombreSolicitado", 100);
            String descItem = Validar.textoOpcional(item.descripcion(), "descripcion (producto a cambio)", 255);
            int cantidadItem = item.cantidad() == null ? 1 : Validar.minimo(item.cantidad(), "cantidad", 1);
            publicacion.agregarIntercambio(nombreSolicitado, descItem, cantidadItem);
        }

        // 2) Buscar usuario y producto. Con bloqueo, quien llega segundo espera aqui.
        Usuario usuario = repoUsuario.findById(idUsuario)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El usuario con id " + idUsuario + " no existe"));
        Producto producto = (conBloqueo
                ? repoProducto.findByIdForUpdate(idProducto)
                : repoProducto.findById(idProducto))
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El producto con id " + idProducto + " no existe"));

        // 3) Regla de negocio: un producto no puede tener dos publicaciones activas
        if (repoPublicacion.existsByProducto_IdAndEstado(idProducto, ESTADO_ACTIVA)) {
            throw new ConflictoException("El producto " + idProducto + " ya tiene una publicacion activa");
        }

        pausar();

        // 4) Guardar cabecera y detalle (el cascade guarda los intercambios)
        publicacion.setUsuario(usuario);
        publicacion.setProducto(producto);
        Publicacion guardado = repoPublicacion.save(publicacion);

        return serviceConsultarPublicacion.consultarPublicacion(guardado.getId());
    }

    private void pausar() {
        if (pausaMs > 0) {
            try {
                Thread.sleep(pausaMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
