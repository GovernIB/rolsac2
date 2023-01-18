package es.caib.rolsac2.service.model.auditoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Cambio registro auditoría.
 *
 * @author Indra
 */
public class AuditoriaCambio {

    /**
     * Campo.
     */
    private String idCampo;

    /**
     * Valores modificados campo.
     */
    private List<AuditoriaValorCampo> valoresModificados = new ArrayList<>();


    /**
     * Obtiene idCampo.
     *
     * @return idCampo
     */
    public String getIdCampo() {
        return idCampo;
    }

    /**
     * Establece idCampo.
     *
     * @param idCampo idCampo a establecer
     */
    public void setIdCampo(final String idCampo) {
        this.idCampo = idCampo;
    }

    /**
     * Obtiene valoresModificados.
     *
     * @return valoresModificados
     */
    public List<AuditoriaValorCampo> getValoresModificados() {
        return valoresModificados;
    }

    /**
     * Establece valoresModificados.
     *
     * @param valoresModificados valoresModificados a establecer
     */
    public void setValoresModificados(final List<AuditoriaValorCampo> valoresModificados) {
        this.valoresModificados = valoresModificados;
    }


}
