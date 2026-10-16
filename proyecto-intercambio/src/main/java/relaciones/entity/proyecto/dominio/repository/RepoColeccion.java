package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.Coleccion;

public interface RepoColeccion extends JpaRepository<Coleccion, Integer> {
}
