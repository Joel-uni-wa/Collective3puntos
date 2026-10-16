package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.PublicacionIntercambio;

public interface RepoPublicacionIntercambio extends JpaRepository<PublicacionIntercambio, Integer> {
}
