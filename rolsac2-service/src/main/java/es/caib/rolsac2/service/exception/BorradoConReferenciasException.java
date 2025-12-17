package es.caib.rolsac2.service.exception;

import java.util.Locale;

/**
 * Excepció per indicar que el recurs sol·licitat en una operació no s'ha trobat.
 *
 * @author Indra
 */
public class BorradoConReferenciasException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public BorradoConReferenciasException() {
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        return translate(locale, "error.deleteNoPermes");
    }
}

