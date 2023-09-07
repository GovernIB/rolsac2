package es.caib.rolsac2.service.model.migracion;

/**
 * Para cuando se migra tener los datos.
 */
public class FicheroInfo {

    /**
     * Codigo fichero rolsac1
     **/
    Long codigoFicheroRolsac1;

    /**
     * Codigo que tiene el ficherorolsac1, que será un codigo doc normativa trad o codigo doc proc trad
     **/
    Long codigoDocumentoTraduccion;

    /**
     * Codigo del padre que será procedimiento o normativa
     **/
    Long codigoPadre;

    public Long getCodigoFicheroRolsac1() {
        return codigoFicheroRolsac1;
    }

    public void setCodigoFicheroRolsac1(Long codigoFicheroRolsac1) {
        this.codigoFicheroRolsac1 = codigoFicheroRolsac1;
    }

    public Long getCodigoDocumentoTraduccion() {
        return codigoDocumentoTraduccion;
    }

    public void setCodigoDocumentoTraduccion(Long codigoDocumentoTraduccion) {
        this.codigoDocumentoTraduccion = codigoDocumentoTraduccion;
    }

    public Long getCodigoPadre() {
        return codigoPadre;
    }

    public void setCodigoPadre(Long codigoPadre) {
        this.codigoPadre = codigoPadre;
    }
}
