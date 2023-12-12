package es.caib.rolsac2.service.model.types;

public enum TypeExportarFormato {

    TXT, CSV, XLS, PDF;

    public static TypeExportarFormato fromString(String valor) {
        if (valor != null) {
            for (TypeExportarFormato type : TypeExportarFormato.values()) {
                if (valor.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }
        }
        return null;
    }
}
