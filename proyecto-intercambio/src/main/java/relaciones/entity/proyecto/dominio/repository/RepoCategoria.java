package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.Categoria;

public interface RepoCategoria extends JpaRepository<Categoria, Integer> {
}
