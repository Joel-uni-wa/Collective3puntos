package relaciones.entity.proyecto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.jsonpath.JsonPath;

import tools.jackson.databind.ObjectMapper;

import relaciones.entity.proyecto.cu.registrarpublicacion.request.RequestPublicacion;
import relaciones.entity.proyecto.cu.registrarpublicacion.request.RequestPublicacion.RequestIntercambio;
import relaciones.entity.proyecto.dominio.entity.Producto;
import relaciones.entity.proyecto.dominio.entity.Usuario;
import relaciones.entity.proyecto.dominio.repository.RepoCategoria;
import relaciones.entity.proyecto.dominio.repository.RepoProducto;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacion;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacionIntercambio;
import relaciones.entity.proyecto.dominio.repository.RepoUsuario;

/**
 * Cabecera-detalle 1: Publicacion (cabecera) + PublicacionIntercambio (items).
 *
 * El test es @Transactional: todo lo que guarda se deshace al terminar (rollback),
 * asi la base de datos de Neon no se llena de datos de prueba.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TestRegistrarPublicacion {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoCategoria repoCategoria;

    @Autowired
    private RepoProducto repoProducto;

    @Autowired
    private RepoPublicacion repoPublicacion;

    @Autowired
    private RepoPublicacionIntercambio repoPublicacionIntercambio;

    @Test
    @DisplayName("Debe guardar una publicacion (cabecera) con sus productos a cambio (items)")
    void testRegistrarPublicacionConItems() throws Exception {
        Usuario vendedor = repoUsuario.findAll().get(0);

        // Producto propio del test: los productos de los datos de prueba ya tienen publicacion activa
        Producto producto = new Producto();
        producto.setCategoria(repoCategoria.findAll().get(0));
        producto.setNombre("Carta de prueba para publicar");
        producto.setCantidad(1);
        producto.setPrecio(new BigDecimal("50.00"));
        producto = repoProducto.save(producto);

        long publicacionesAntes = repoPublicacion.count();
        long itemsAntes = repoPublicacionIntercambio.count();

        List<RequestIntercambio> items = List.of(
                new RequestIntercambio("Dark Magician", "Edicion original", 1),
                new RequestIntercambio("Mew holo", null, 2));
        RequestPublicacion request = new RequestPublicacion(
                vendedor.getId(), producto.getId(),
                "Cambio carta de prueba", "Estado casi nuevo", "INTERCAMBIO", items);

        String respuesta = mockMvc.perform(post("/publicacion/nuevo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPublicacion").exists())
                .andExpect(jsonPath("$.estado").value("ACTIVA"))
                .andExpect(jsonPath("$.intercambios.length()").value(2))
                .andReturn().getResponse().getContentAsString();

        // Se guardo 1 cabecera y 2 items en la base de datos
        int idPublicacion = JsonPath.read(respuesta, "$.idPublicacion");
        assertThat(repoPublicacion.findById(idPublicacion)).isPresent();
        assertThat(repoPublicacion.count()).isEqualTo(publicacionesAntes + 1);
        assertThat(repoPublicacionIntercambio.count()).isEqualTo(itemsAntes + 2);
    }
}
