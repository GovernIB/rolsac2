package es.caib.rolsac2.service.model.types;

/**
 * Tipos de fichero externo
 */
public enum TypeFicheroExterno {
    /**
     * Entidad (RS2_ENTIDA).
     */
    ENTIDAD("/entidad/", "ENTIDA"),
    /**
     * Ficha documentos (RS2_FCHDOC).
     */
    FICHA_DOCUMENTOS("/ficha/", "FICDOC"),
    /**
     * Ficha multimedia (RS2_FCHMED).
     */
    FICHA_MULTIMEDIA("/fichamulti/", "FCHMED"),
    /**
     * Edificio multimedia (RS2_EDIMED).
     */
    EDIFICIO_MULTIMEDIA("/edificio/", "EDIMED"),
    /**
     * UA multimedia (RS2_UNAMED).
     */
    UNIADMIN_MULTIMEDIA("/unidad/", "UNAMED"),
    /**
     * Procedimientos documento (RS2_TRADOPR).
     */
    PROCEDIMIENTO_DOCUMENTOS("/proced/", "TRADOPR");

    private String ruta;
    private String tipo;

    TypeFicheroExterno(String ruta, String tipo) {
        this.ruta = ruta;
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getRuta() {
        return this.ruta;
    }

    public TypeFicheroExterno fromString(String tipo) {
        TypeFicheroExterno type = null;
        if (tipo != null && !tipo.isEmpty()) {
            for (TypeFicheroExterno typeFic : TypeFicheroExterno.values()) {
                if (typeFic.getTipo().equals(tipo)) {
                    type = typeFic;
                    break;
                }
            }
        }
        return type;
    }

}
