package relaciones.entity.proyecto.util;

import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;

/** Validaciones simples que se usan en los POST. Si algo esta mal, lanzan un 400. */
public final class Validar {

    private Validar() {
    }

    public static void cuerpo(Object request) {
        if (request == null) {
            throw new SolicitudInvalidaException("Debe enviar los datos en el cuerpo de la solicitud");
        }
    }

    /** Texto obligatorio, sin espacios sobrantes y con un maximo de caracteres. */
    public static String texto(String valor, String campo, int max) {
        if (valor == null || valor.isBlank()) {
            throw new SolicitudInvalidaException("El campo '" + campo + "' es obligatorio");
        }
        String limpio = valor.trim();
        if (limpio.length() > max) {
            throw new SolicitudInvalidaException(
                    "El campo '" + campo + "' no puede superar los " + max + " caracteres");
        }
        return limpio;
    }

    /** Texto opcional: si viene vacio devuelve null. */
    public static String textoOpcional(String valor, String campo, int max) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return texto(valor, campo, max);
    }

    /** Id obligatorio (mayor que 0). */
    public static int idObligatorio(Integer valor, String campo) {
        if (valor == null || valor <= 0) {
            throw new SolicitudInvalidaException(
                    "El campo '" + campo + "' es obligatorio y debe ser un id valido (mayor que 0)");
        }
        return valor;
    }

    /** Numero obligatorio mayor o igual a un minimo. */
    public static int minimo(Integer valor, String campo, int min) {
        if (valor == null || valor < min) {
            throw new SolicitudInvalidaException(
                    "El campo '" + campo + "' es obligatorio y debe ser mayor o igual a " + min);
        }
        return valor;
    }

    /** Numero obligatorio dentro de un rango. */
    public static int rango(Integer valor, String campo, int min, int max) {
        if (valor == null || valor < min || valor > max) {
            throw new SolicitudInvalidaException(
                    "El campo '" + campo + "' es obligatorio y debe estar entre " + min + " y " + max);
        }
        return valor;
    }
}
