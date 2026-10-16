package relaciones.entity.proyecto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import relaciones.entity.proyecto.cu.registrarpublicacion.request.RequestPublicacion;
import relaciones.entity.proyecto.cu.registrarpublicacion.request.RequestPublicacion.RequestIntercambio;
import relaciones.entity.proyecto.dominio.entity.Producto;
import relaciones.entity.proyecto.dominio.entity.Usuario;
import relaciones.entity.proyecto.dominio.repository.RepoCategoria;
import relaciones.entity.proyecto.dominio.repository.RepoProducto;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacion;
import relaciones.entity.proyecto.dominio.repository.RepoUsuario;

/**
 * Concurrencia: dos usuarios publican el MISMO producto en el mismo instante.
 * Regla: un producto solo puede tener una publicacion activa.
 *
 * - Con lock (SELECT ... FOR UPDATE sobre el producto): solo se guarda una, la otra recibe 409.
 * - Sin lock: las dos pasan la revision a la vez y se guardan las dos (condicion de carrera).
 *
 * Este test NO puede ser @Transactional (los dos hilos necesitan transacciones reales y
 * separadas), asi que crea su propio producto y lo borra al terminar.
 * app.demo.pausa-ms alarga la ventana entre "revisar" y "guardar" para que la carrera se vea siempre.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "app.demo.pausa-ms=1500")
@AutoConfigureTestRestTemplate
class TestPublicacionConcurrente {

    private static final int HILOS = 2;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoCategoria repoCategoria;

    @Autowired
    private RepoProducto repoProducto;

    @Autowired
    private RepoPublicacion repoPublicacion;

    private Producto producto;

    @BeforeEach
    void crearProducto() {
        producto = new Producto();
        producto.setCategoria(repoCategoria.findAll().get(0));
        producto.setNombre("Producto para prueba concurrente");
        producto.setCantidad(1);
        producto.setPrecio(new BigDecimal("100.00"));
        producto = repoProducto.save(producto);
        System.out.println("Producto creado con ID " + producto.getId());
    }

    @AfterEach
    void limpiar() {
        repoPublicacion.deleteAll(repoPublicacion.findByProducto_Id(producto.getId()));
        repoProducto.deleteById(producto.getId());
    }

    @Test
    @DisplayName("Con lock: de dos publicaciones simultaneas del mismo producto solo se guarda una")
    void testPublicarMismoProductoConLock() throws Exception {
        List<Integer> codigos = publicarEnParalelo("/publicacion/nuevo");

        assertThat(codigos).containsExactlyInAnyOrder(201, 409);
        assertThat(repoPublicacion.findByProducto_Id(producto.getId())).hasSize(1);
    }

    @Test
    @DisplayName("Test ficticio: sin lock se guardan las dos publicaciones (condicion de carrera)")
    void testPublicarMismoProductoSinLock() throws Exception {
        List<Integer> codigos = publicarEnParalelo("/publicacion/nuevo/sinlock");

        assertThat(codigos).containsExactly(201, 201);
        assertThat(repoPublicacion.findByProducto_Id(producto.getId())).hasSize(2);
    }

    /** Lanza HILOS peticiones POST al mismo tiempo y devuelve el codigo HTTP de cada una. */
    private List<Integer> publicarEnParalelo(String ruta) throws Exception {
        Usuario vendedor = repoUsuario.findAll().get(0);
        RequestPublicacion request = new RequestPublicacion(
                vendedor.getId(), producto.getId(), "Cambio carta concurrente", "Prueba", "INTERCAMBIO",
                List.of(new RequestIntercambio("Dark Magician", "Edicion original", 1)));

        CountDownLatch salida = new CountDownLatch(1);
        ExecutorService hilos = Executors.newFixedThreadPool(HILOS);
        List<Future<Integer>> futuros = new ArrayList<>();
        for (int i = 0; i < HILOS; i++) {
            futuros.add(hilos.submit(() -> {
                salida.await();   // todos esperan la misma senal de salida
                ResponseEntity<String> respuesta = restTemplate.postForEntity(ruta, request, String.class);
                System.out.println(Thread.currentThread().getName() + " -> "
                        + respuesta.getStatusCode().value() + " " + respuesta.getBody());
                return respuesta.getStatusCode().value();
            }));
        }
        salida.countDown();   // disparar al mismo tiempo

        List<Integer> codigos = new ArrayList<>();
        for (Future<Integer> futuro : futuros) {
            codigos.add(futuro.get(60, TimeUnit.SECONDS));
        }
        hilos.shutdown();
        return codigos;
    }
}
