package relaciones.entity.proyecto.cu.consultarusuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarusuario.response.ResponseUsuario;
import relaciones.entity.proyecto.dominio.entity.Usuario;
import relaciones.entity.proyecto.dominio.repository.RepoUsuario;
import relaciones.entity.proyecto.excepcion.RecursoNoEncontradoException;

import java.util.List;

@Service
public class ServiceConsultarUsuario {

    private final RepoUsuario repoUsuario;

    public ServiceConsultarUsuario(RepoUsuario repoUsuario) {
        this.repoUsuario = repoUsuario;
    }

    @Transactional(readOnly = true)
    public ResponseUsuario consultarUsuario(int id) {
        Usuario usuario = repoUsuario.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario con id " + id + " no existe"));
        return toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public List<ResponseUsuario> listarTodos() {
        return repoUsuario.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseUsuario toResponse(Usuario usuario) {
        return new ResponseUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getTipo()
        );
    }
}
