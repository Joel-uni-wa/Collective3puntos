package relaciones.entity.proyecto.cu.registrarusuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarusuario.ServiceConsultarUsuario;
import relaciones.entity.proyecto.cu.consultarusuario.response.ResponseUsuario;
import relaciones.entity.proyecto.cu.registrarusuario.request.RequestUsuario;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarUsuario {

    private final RepoUsuario repoUsuario;
    private final ServiceConsultarUsuario serviceConsultarUsuario;

    public ServiceRegistrarUsuario(
            RepoUsuario repoUsuario,
            ServiceConsultarUsuario serviceConsultarUsuario) {
        this.repoUsuario = repoUsuario;
        this.serviceConsultarUsuario = serviceConsultarUsuario;
    }

    @Transactional
    public ResponseUsuario registrarUsuario(RequestUsuario request) {
        Validar.cuerpo(request);

        String nombre = Validar.texto(request.nombre(), "nombre", 100);
        String correo = Validar.texto(request.correo(), "correo", 100).toLowerCase();
        if (!correo.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new SolicitudInvalidaException("El correo no tiene un formato valido");
        }
        String telefono = Validar.textoOpcional(request.telefono(), "telefono", 15);
        if (telefono != null && !telefono.matches("\\+?[0-9]{6,15}")) {
            throw new SolicitudInvalidaException(
                    "El telefono solo puede tener numeros (y un + al inicio), de 6 a 15 digitos");
        }
        if (repoUsuario.existsByCorreo(correo)) {
            throw new ConflictoException("Ya existe un usuario con el correo " + correo);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setTipo(request.tipo() != null ? request.tipo() : false);
        Usuario guardado = repoUsuario.save(usuario);

        return serviceConsultarUsuario.consultarUsuario(guardado.getId());
    }
}
