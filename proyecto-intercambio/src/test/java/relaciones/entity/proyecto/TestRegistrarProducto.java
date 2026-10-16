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

import relaciones.entity.proyecto.cu.registrarproducto.request.RequestProducto;
import relaciones.entity.proyecto.cu.registrarproducto.request.RequestProducto.RequestImagen;
import relaciones.entity.proyecto.dominio.entity.Categoria;
import relaciones.entity.proyecto.dominio.repository.RepoCategoria;
import relaciones.entity.proyecto.dominio.repository.RepoImagenProducto;
import relaciones.entity.proyecto.dominio.repository.RepoProducto;

/**
 * Cabecera-detalle 2: Producto (cabecera) + ImagenProducto (items).
 * Tambien es @Transactional, asi que se deshace al terminar.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TestRegistrarProducto {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepoCategoria repoCategoria;

    @Autowired
    private RepoProducto repoProducto;

    @Autowired
    private RepoImagenProducto repoImagenProducto;

    @Test
    @DisplayName("Debe guardar un producto (cabecera) con sus imagenes (items)")
    void testRegistrarProductoConImagenes() throws Exception {
        Categoria categoria = repoCategoria.findAll().get(0);
        long productosAntes = repoProducto.count();
        long imagenesAntes = repoImagenProducto.count();

        List<RequestImagen> imagenes = List.of(
                new RequestImagen("https://ejemplo.com/img/frente.jpg", 1),
                new RequestImagen("https://ejemplo.com/img/reverso.jpg", 2));
        RequestProducto request = new RequestProducto(
                categoria.getId(), null, "Producto de prueba con fotos", 3, new BigDecimal("75.50"), imagenes);

        String respuesta = mockMvc.perform(post("/producto/nuevo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").exists())
                .andExpect(jsonPath("$.imagenes.length()").value(2))
                .andExpect(jsonPath("$.imagenes[0]").value("https://ejemplo.com/img/frente.jpg"))
                .andReturn().getResponse().getContentAsString();

        // Se guardo 1 cabecera y 2 items en la base de datos
        int idProducto = JsonPath.read(respuesta, "$.idProducto");
        assertThat(repoProducto.findById(idProducto)).isPresent();
        assertThat(repoProducto.count()).isEqualTo(productosAntes + 1);
        assertThat(repoImagenProducto.count()).isEqualTo(imagenesAntes + 2);
    }
}
