package es.caib.rolsac2.service.model;

import java.util.Objects;

public class ProcesoLogBaseDTO extends ModelApi implements Comparable<ProcesoLogBaseDTO>{
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;

    /** Codigo. ***/
    private Long codigo;


    /**
     * Constructor.
     */
    public ProcesoLogBaseDTO() {
        super();
    }

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }


    @Override
    public int compareTo(final ProcesoLogBaseDTO o) {
        return this.getCodigo().compareTo(o.getCodigo());
    }

    /**
     * Equals.
     */
    @Override
    public boolean equals(final Object objeto) {
        boolean retorno;
        if (objeto == null) {
            retorno = false;
        } else if (!(objeto instanceof ProcesoLogBaseDTO)) {
            retorno = false;
        } else {
            final ProcesoLogBaseDTO apunte = (ProcesoLogBaseDTO) objeto;
            if (apunte.getCodigo() == null || this.getCodigo() == null) {
                retorno = false;
            } else {
                retorno = apunte.getCodigo().compareTo(this.getCodigo()) == 0;
            }
        }
        return retorno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo());
    }
}
