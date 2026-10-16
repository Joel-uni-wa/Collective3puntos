package relaciones.entity.proyecto.cu.registrarproducto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarproducto.ServiceConsultarProducto;
import relaciones.entity.proyecto.cu.consultarproducto.response.ResponseProducto;
import relaciones.entity.proyecto.cu.registrarproducto.request.RequestProducto;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarProducto {

    private final RepoCategoria repoCategoria;
    private final RepoColeccion repoColeccion;
    private final RepoProducto repoProducto;
    private final ServiceConsultarProducto serviceConsultarProducto;

    public ServiceRegistrarProducto(
            RepoCategoria repoCategoria,
            RepoColeccion repoColeccion,
            RepoProducto repoProducto,
            ServiceConsultarProducto serviceConsultarProducto) {
        this.repoCategoria = repoCategoria;
        this.repoColeccion = repoColeccion;
        this.repoProducto = repoProducto;
        this.serviceConsultarProducto = serviceConsultarProducto;
    }

    @Transactional
    public ResponseProducto registrarProducto(RequestProducto request) {
        Validar.cuerpo(request);

        int idCategoria = Validar.idObligatorio(request.idCategoria(), "idCategoria");
        String nombre = Validar.texto(request.nombre(), "nombre", 100);
        int cantidad = Validar.minimo(request.cantidad(), "cantidad", 0);
        if (request.precio() == null
                || request.precio().compareTo(BigDecimal.ZERO) < 0
                || request.precio().compareTo(new BigDecimal("99999999.99")) > 0) {
            throw new SolicitudInvalidaException(
                    "El campo 'precio' es obligatorio y debe estar entre 0 y 99999999.99");
        }
        BigDecimal precio = request.precio().setScale(2, RoundingMode.HALF_UP);

        Categoria categoria = repoCategoria.findById(idCategoria)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "La categoria con id " + idCategoria + " no existe"));

        Coleccion coleccion = null;
        if (request.idColeccion() != null) {
            int idColeccion = Validar.idObligatorio(request.idColeccion(), "idColeccion");
            coleccion = repoColeccion.findById(idColeccion)
                    .orElseThrow(() -> new SolicitudInvalidaException(
                            "La coleccion con id " + idColeccion + " no existe"));
            if (!coleccion.getCategoria().getId().equals(categoria.getId())) {
                throw new SolicitudInvalidaException("La coleccion '" + coleccion.getNombre()
                        + "' no pertenece a la categoria '" + categoria.getNombre() + "'");
            }
        }

        Producto producto = new Producto();
        producto.setCategoria(categoria);
        producto.setColeccion(coleccion);
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setPrecio(precio);

        List<RequestProducto.RequestImagen> imagenes =
                request.imagenes() == null ? List.of() : request.imagenes();
        for (RequestProducto.RequestImagen item : imagenes) {
            Validar.cuerpo(item);
            String url = Validar.texto(item.url(), "url (imagen)", 255);
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                throw new SolicitudInvalidaException("La url de la imagen debe empezar con http:// o https://");
            }
            int orden = item.orden() == null
                    ? producto.getImagenes().size() + 1
                    : Validar.minimo(item.orden(), "orden (imagen)", 1);
            producto.agregarImagen(url, orden);
        }

        Producto guardado = repoProducto.save(producto);

        return serviceConsultarProducto.consultarProducto(guardado.getId());
    }
}
