package relaciones.entity.proyecto.excepcion;

/**
 * Se lanza cuando el dato ya existe o choca con otro (responde 409).
 */
public class ConflictoException extends RuntimeException {

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
