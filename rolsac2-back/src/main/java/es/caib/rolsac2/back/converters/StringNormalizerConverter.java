package es.caib.rolsac2.back.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.Normalizer;

@FacesConverter("stringNormalizerConverter")
public class StringNormalizerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value != null ? Normalizer.normalize(value, Normalizer.Form.NFC) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value != null ? value.toString() : "";
    }
}
