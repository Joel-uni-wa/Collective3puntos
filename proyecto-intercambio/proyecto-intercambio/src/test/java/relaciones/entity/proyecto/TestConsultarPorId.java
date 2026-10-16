package relaciones.entity.proyecto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import relaciones.entity.proyecto.dominio.entity.Categoria;
import relaciones.entity.proyecto.dominio.entity.Coleccion;
import relaciones.entity.proyecto.dominio.entity.Favorito;
import relaciones.entity.proyecto.dominio.entity.ImagenProducto;
import relaciones.entity.proyecto.dominio.entity.Producto;
import relaciones.entity.proyecto.dominio.entity.Publicacion;
import relaciones.entity.proyecto.dominio.entity.PublicacionIntercambio;
import relaciones.entity.proyecto.dominio.entity.Reporte;
import relaciones.entity.proyecto.dominio.entity.Usuario;
import relaciones.entity.proyecto.dominio.entity.Valoracion;
import relaciones.entity.proyecto.dominio.repository.RepoCategoria;
import relaciones.entity.proyecto.dominio.repository.RepoColeccion;
import relaciones.entity.proyecto.dominio.repository.RepoFavorito;
import relaciones.entity.proyecto.dominio.repository.RepoImagenProducto;
import relaciones.entity.proyecto.dominio.repository.RepoProducto;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacion;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacionIntercambio;
import relaciones.entity.proyecto.dominio.repository.RepoReporte;
import relaciones.entity.proyecto.dominio.repository.RepoUsuario;
import relaciones.entity.proyecto.dominio.repository.RepoValoracion;

/**
 * Consulta por id de cada una de las 10 entidades del modelo.
 * Cada test toma un registro que YA existe en la base (los datos de prueba),
 * llama a GET /{entidad}/{id} y compara lo que responde el servicio con lo que hay guardado.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TestConsultarPorId {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepoUsuario repoUsuario;
    @Autowired
    private RepoCategoria repoCategoria;
    @Autowired
    private RepoColeccion repoColeccion;
    @Autowired
    private RepoProducto repoProducto;
    @Autowired
    private RepoImagenProducto repoImagenProducto;
    @Autowired
    private RepoPublicacion repoPublicacion;
    @Autowired
    private RepoPublicacionIntercambio repoPublicacionIntercambio;
    @Autowired
    private RepoValoracion repoValoracion;
    @Autowired
    private RepoFavorito repoFavorito;
    @Autowired
    private RepoReporte repoReporte;

    @Test
    @DisplayName("Consulta por id: usuario")
    void testConsultarUsuario() throws Exception {
        Usuario usuario = repoUsuario.findAll().get(0);
        mockMvc.perform(get("/usuario/{id}", usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(usuario.getId()))
                .andExpect(jsonPath("$.nombre").value(usuario.getNombre()))
                .andExpect(jsonPath("$.correo").value(usuario.getCorreo()));
    }

    @Test
    @DisplayName("Consulta por id: categoria")
    void testConsultarCategoria() throws Exception {
        Categoria categoria = repoCategoria.findAll().get(0);
        mockMvc.perform(get("/categoria/{id}", categoria.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCategoria").value(categoria.getId()))
                .andExpect(jsonPath("$.nombre").value(categoria.getNombre()));
    }

    @Test
    @DisplayName("Consulta por id: coleccion")
    void testConsultarColeccion() throws Exception {
        Coleccion coleccion = repoColeccion.findAll().get(0);
        mockMvc.perform(get("/coleccion/{id}", coleccion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idColeccion").value(coleccion.getId()))
                .andExpect(jsonPath("$.nombre").value(coleccion.getNombre()))
                .andExpect(jsonPath("$.nombreCategoria").exists());
    }

    @Test
    @DisplayName("Consulta por id: producto")
    void testConsultarProducto() throws Exception {
        Producto producto = repoProducto.findAll().get(0);
        mockMvc.perform(get("/producto/{id}", producto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(producto.getId()))
                .andExpect(jsonPath("$.nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$.categoria").exists());
    }

    @Test
    @DisplayName("Consulta por id: imagen de producto")
    void testConsultarImagenProducto() throws Exception {
        ImagenProducto imagen = repoImagenProducto.findAll().get(0);
        mockMvc.perform(get("/imagen-producto/{id}", imagen.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idImagen").value(imagen.getId()))
                .andExpect(jsonPath("$.url").value(imagen.getUrl()));
    }

    @Test
    @DisplayName("Consulta por id: publicacion (incluye sus intercambios)")
    void testConsultarPublicacion() throws Exception {
        Publicacion publicacion = repoPublicacion.findAll().get(0);
        mockMvc.perform(get("/publicacion/{id}", publicacion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPublicacion").value(publicacion.getId()))
                .andExpect(jsonPath("$.titulo").value(publicacion.getTitulo()))
                .andExpect(jsonPath("$.nombreUsuario").exists())
                .andExpect(jsonPath("$.intercambios").isArray());
    }

    @Test
    @DisplayName("Consulta por id: publicacion_intercambio")
    void testConsultarPublicacionIntercambio() throws Exception {
        PublicacionIntercambio intercambio = repoPublicacionIntercambio.findAll().get(0);
        mockMvc.perform(get("/publicacion-intercambio/{id}", intercambio.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idIntercambio").value(intercambio.getId()))
                .andExpect(jsonPath("$.nombreSolicitado").value(intercambio.getNombreSolicitado()));
    }

    @Test
    @DisplayName("Consulta por id: valoracion")
    void testConsultarValoracion() throws Exception {
        Valoracion valoracion = repoValoracion.findAll().get(0);
        mockMvc.perform(get("/valoracion/{id}", valoracion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idValoracion").value(valoracion.getId()))
                .andExpect(jsonPath("$.califUsuario").value(valoracion.getCalifUsuario()))
                .andExpect(jsonPath("$.califPublicacion").value(valoracion.getCalifPublicacion()));
    }

    @Test
    @DisplayName("Consulta por id: favorito")
    void testConsultarFavorito() throws Exception {
        Favorito favorito = repoFavorito.findAll().get(0);
        mockMvc.perform(get("/favorito/{id}", favorito.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFavorito").value(favorito.getId()))
                .andExpect(jsonPath("$.nombreUsuario").exists())
                .andExpect(jsonPath("$.tituloPublicacion").exists());
    }

    @Test
    @DisplayName("Consulta por id: reporte")
    void testConsultarReporte() throws Exception {
        Reporte reporte = repoReporte.findAll().get(0);
        mockMvc.perform(get("/reporte/{id}", reporte.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReporte").value(reporte.getId()))
                .andExpect(jsonPath("$.motivo").value(reporte.getMotivo()))
                .andExpect(jsonPath("$.estado").value(reporte.getEstado()));
    }

    @Test
    @DisplayName("Consulta por id inexistente: responde 404 con el JSON de error")
    void testConsultarIdInexistente() throws Exception {
        mockMvc.perform(get("/usuario/{id}", 999999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.mensaje").value("Usuario con id 999999 no existe"));
    }
}
