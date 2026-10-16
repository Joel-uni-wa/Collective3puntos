package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.Publicacion;

public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {
    boolean existsByProducto_IdAndEstado(Integer idProducto, String estado);

    java.util.List<Publicacion> findByProducto_Id(Integer idProducto);
}
