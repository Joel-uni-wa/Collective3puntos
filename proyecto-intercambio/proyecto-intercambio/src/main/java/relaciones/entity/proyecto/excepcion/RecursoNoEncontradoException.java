package relaciones.entity.proyecto.excepcion;

/**
 * Se lanza cuando un id consultado no existe (responde 404).
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
