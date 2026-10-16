package relaciones.entity.proyecto.config;

import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Carga datos de prueba la primera vez que se ejecuta la aplicacion
 * (solo si la tabla usuario esta vacia). Sirve para tener algo que consultar
 * en la presentacion. Esta clase se puede borrar sin afectar el resto.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final RepoUsuario repoUsuario;
    private final RepoCategoria repoCategoria;
    private final RepoColeccion repoColeccion;
    private final RepoProducto repoProducto;
    private final RepoImagenProducto repoImagenProducto;
    private final RepoPublicacion repoPublicacion;
    private final RepoPublicacionIntercambio repoPublicacionIntercambio;
    private final RepoValoracion repoValoracion;
    private final RepoFavorito repoFavorito;
    private final RepoReporte repoReporte;

    public DatosIniciales(RepoUsuario repoUsuario,
                            RepoCategoria repoCategoria,
                            RepoColeccion repoColeccion,
                            RepoProducto repoProducto,
                            RepoImagenProducto repoImagenProducto,
                            RepoPublicacion repoPublicacion,
                            RepoPublicacionIntercambio repoPublicacionIntercambio,
                            RepoValoracion repoValoracion,
                            RepoFavorito repoFavorito,
                            RepoReporte repoReporte) {
        this.repoUsuario = repoUsuario;
        this.repoCategoria = repoCategoria;
        this.repoColeccion = repoColeccion;
        this.repoProducto = repoProducto;
        this.repoImagenProducto = repoImagenProducto;
        this.repoPublicacion = repoPublicacion;
        this.repoPublicacionIntercambio = repoPublicacionIntercambio;
        this.repoValoracion = repoValoracion;
        this.repoFavorito = repoFavorito;
        this.repoReporte = repoReporte;
    }

    @Override
    public void run(String... args) {
        if (repoUsuario.count() > 0) {
            return;
        }

        Categoria pokemon = categoria("Cartas Pokemon", "Cartas del juego Pokemon TCG");
        Categoria yugioh = categoria("Cartas Yu-Gi-Oh", "Cartas del juego Yu-Gi-Oh TCG");

        Coleccion baseSet = coleccion(pokemon, "Base Set", "Primera edicion de cartas Pokemon");
        Coleccion legendBlue = coleccion(yugioh, "Legend of Blue Eyes White Dragon", "Set clasico de Yu-Gi-Oh");

        Usuario ana = usuario("Ana Torres", "ana@correo.com", "987654321", true);
        Usuario luis = usuario("Luis Quispe", "luis@correo.com", "912345678", false);

        Producto charizard = producto(pokemon, baseSet, "Charizard Base Set", 1, "450.00");
        Producto blueEyes = producto(yugioh, legendBlue, "Blue-Eyes White Dragon", 1, "300.00");
        Producto pikachu = producto(pokemon, baseSet, "Pikachu", 2, "25.00");

        imagen(charizard, "https://ejemplo.com/img/charizard-frente.jpg", 1);
        imagen(charizard, "https://ejemplo.com/img/charizard-atras.jpg", 2);
        imagen(blueEyes, "https://ejemplo.com/img/blue-eyes-frente.jpg", 1);
        imagen(pikachu, "https://ejemplo.com/img/pikachu-frente.jpg", 1);

        Publicacion pub1 = publicacion(ana, charizard, "Vendo Charizard Base Set",
                "Carta en buen estado, sin dobleces", "VENTA");
        Publicacion pub2 = publicacion(luis, blueEyes, "Cambio Blue-Eyes por carta Pokemon",
                "Acepto cambio por las cartas indicadas", "INTERCAMBIO");
        Publicacion pub3 = publicacion(ana, pikachu, "Pikachu en venta o cambio",
                "Vendo o cambio por una carta Yu-Gi-Oh", "AMBOS");

        intercambio(pub2, "Charizard Base Set", "En buen estado", 1);
        intercambio(pub2, "Mewtwo holo", "Cualquier edicion", 1);
        intercambio(pub3, "Dark Magician", "Edicion original", 1);

        valoracion(luis, pub1, 5, 5, "Excelente carta, tal como en las fotos");
        favorito(luis, pub1);
        reporte(luis, pub3, "La foto no coincide con la descripcion", "PENDIENTE");
    }

    private Categoria categoria(String nombre, String descripcion) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        return repoCategoria.save(c);
    }

    private Coleccion coleccion(Categoria categoria, String nombre, String descripcion) {
        Coleccion c = new Coleccion();
        c.setCategoria(categoria);
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        return repoColeccion.save(c);
    }

    private Usuario usuario(String nombre, String correo, String telefono, boolean tipo) {
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setCorreo(correo);
        u.setTelefono(telefono);
        u.setTipo(tipo);
        return repoUsuario.save(u);
    }

    private Producto producto(Categoria categoria, Coleccion coleccion, String nombre, int cantidad, String precio) {
        Producto p = new Producto();
        p.setCategoria(categoria);
        p.setColeccion(coleccion);
        p.setNombre(nombre);
        p.setCantidad(cantidad);
        p.setPrecio(new BigDecimal(precio));
        return repoProducto.save(p);
    }

    private ImagenProducto imagen(Producto producto, String url, int orden) {
        ImagenProducto i = new ImagenProducto();
        i.setProducto(producto);
        i.setUrl(url);
        i.setOrden(orden);
        return repoImagenProducto.save(i);
    }

    private Publicacion publicacion(Usuario usuario, Producto producto, String titulo,
                                    String descripcion, String tipo) {
        Publicacion p = new Publicacion();
        p.setUsuario(usuario);
        p.setProducto(producto);
        p.setTitulo(titulo);
        p.setDescripcion(descripcion);
        p.setTipo(tipo);
        p.setEstado("ACTIVA");
        return repoPublicacion.save(p);
    }

    private PublicacionIntercambio intercambio(Publicacion publicacion, String nombreSolicitado,
                                               String descripcion, int cantidad) {
        PublicacionIntercambio i = new PublicacionIntercambio();
        i.setPublicacion(publicacion);
        i.setNombreSolicitado(nombreSolicitado);
        i.setDescripcion(descripcion);
        i.setCantidad(cantidad);
        return repoPublicacionIntercambio.save(i);
    }

    private Valoracion valoracion(Usuario usuario, Publicacion publicacion,
                                  int califUsuario, int califPublicacion, String comentario) {
        Valoracion v = new Valoracion();
        v.setUsuario(usuario);
        v.setPublicacion(publicacion);
        v.setCalifUsuario(califUsuario);
        v.setCalifPublicacion(califPublicacion);
        v.setComentario(comentario);
        return repoValoracion.save(v);
    }

    private Favorito favorito(Usuario usuario, Publicacion publicacion) {
        Favorito f = new Favorito();
        f.setUsuario(usuario);
        f.setPublicacion(publicacion);
        return repoFavorito.save(f);
    }

    private Reporte reporte(Usuario usuario, Publicacion publicacion, String motivo, String estado) {
        Reporte r = new Reporte();
        r.setUsuario(usuario);
        r.setPublicacion(publicacion);
        r.setMotivo(motivo);
        r.setEstado(estado);
        return repoReporte.save(r);
    }
}
