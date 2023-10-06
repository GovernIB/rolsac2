package es.caib.rolsac2.service.exception;

import java.util.Locale;

/**
 * La unitat té procediments associats i per tat no es pot esborrar
 *
 * @author Indra
 */
public class UnitatTeProcedimentsException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private final Long idUnitat;

    public UnitatTeProcedimentsException(Long idUnitat) {
        this.idUnitat = idUnitat;
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        return translate(locale, "error.unitatTeProcediments", idUnitat);
    }
}
