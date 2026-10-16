package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.ImagenProducto;

public interface RepoImagenProducto extends JpaRepository<ImagenProducto, Integer> {
}
