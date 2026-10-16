package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.Favorito;

public interface RepoFavorito extends JpaRepository<Favorito, Integer> {
    boolean existsByUsuario_IdAndPublicacion_Id(Integer idUsuario, Integer idPublicacion);
}
