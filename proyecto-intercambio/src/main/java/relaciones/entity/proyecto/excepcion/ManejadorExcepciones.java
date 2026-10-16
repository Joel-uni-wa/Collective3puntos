package relaciones.entity.proyecto.excepcion;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

/**
 * Atrapa las excepciones de todos los controllers y las convierte en una
 * respuesta JSON con el codigo HTTP correcto.
 */
@RestControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return new ErrorResponse(404, "No encontrado", ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse manejarSolicitudInvalida(SolicitudInvalidaException ex) {
        return new ErrorResponse(400, "Solicitud invalida", ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ConflictoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse manejarConflicto(ConflictoException ex) {
        return new ErrorResponse(409, "Conflicto", ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return new ErrorResponse(400, "Solicitud invalida",
                "El cuerpo de la solicitud no es un JSON valido o algun campo tiene un tipo incorrecto",
                LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse manejarTipoIncorrecto(MethodArgumentTypeMismatchException ex) {
        return new ErrorResponse(400, "Solicitud invalida",
                "El valor '" + ex.getValue() + "' no es valido para '" + ex.getName()
                        + "' (se esperaba un numero entero)",
                LocalDateTime.now());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse manejarIntegridad(DataIntegrityViolationException ex) {
        return new ErrorResponse(409, "Conflicto",
                "La operacion viola una restriccion de la base de datos (dato duplicado o relacion invalida)",
                LocalDateTime.now());
    }
}
