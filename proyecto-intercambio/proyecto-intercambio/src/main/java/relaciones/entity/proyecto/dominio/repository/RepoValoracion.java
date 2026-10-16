package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.Valoracion;

public interface RepoValoracion extends JpaRepository<Valoracion, Integer> {
    boolean existsByUsuario_IdAndPublicacion_Id(Integer idUsuario, Integer idPublicacion);
}
