package es.caib.rolsac2.back.exception;

import java.util.Locale;

public class PerfilException extends AbstractBackException {

    private static final long serialVersionUID = 1L;

    public PerfilException() {
    }

    public PerfilException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        return translate(locale, "error.perfilException");
    }
}
