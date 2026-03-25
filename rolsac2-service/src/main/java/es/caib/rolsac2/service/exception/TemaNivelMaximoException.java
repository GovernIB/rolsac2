package es.caib.rolsac2.service.exception;

import java.util.Locale;

public class TemaNivelMaximoException extends ServiceException {

    private static final long serialVersionUID = 1L;
    private final int nivelMaximo;

    public TemaNivelMaximoException(int nivelMaximo) {
        this.nivelMaximo = nivelMaximo;
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        return translate(locale, "error.tema.nivelMaximo", nivelMaximo);
    }
}