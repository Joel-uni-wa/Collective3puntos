package relaciones.entity.proyecto.excepcion;

/**
 * Se lanza cuando faltan datos o son incorrectos (responde 400).
 */
public class SolicitudInvalidaException extends RuntimeException {

    public SolicitudInvalidaException(String mensaje) {
        super(mensaje);
    }
}
