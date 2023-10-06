package es.caib.rolsac2.service.exception;

import java.util.Locale;

/**
 * Ja existeix una procediment amb el mateix codiSia.
 *
 * @author Indra
 */
public class DatoDuplicadoException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private final Long id;

    public DatoDuplicadoException(Long id) {
        this.id = id;
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        return translate(locale, "error.procedimentDuplicat", id);
    }
}
