package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.Usuario;

public interface RepoUsuario extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
}
