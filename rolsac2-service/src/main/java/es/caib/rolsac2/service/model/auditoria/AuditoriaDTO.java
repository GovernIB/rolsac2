package es.caib.rolsac2.service.model.auditoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Registro auditoría.
 *
 * @author Indra
 */
public class AuditoriaDTO {

    /**
     * Cambios.
     */
    private List<AuditoriaCambio> cambios = new ArrayList<>();

    /**
     * Obtiene cambios.
     *
     * @return cambios
     */
    public List<AuditoriaCambio> getCambios() {
        return cambios;
    }

    /**
     * Establece cambios.
     *
     * @param cambios cambios a establecer
     */
    public void setCambios(final List<AuditoriaCambio> cambios) {
        this.cambios = cambios;
    }

}
