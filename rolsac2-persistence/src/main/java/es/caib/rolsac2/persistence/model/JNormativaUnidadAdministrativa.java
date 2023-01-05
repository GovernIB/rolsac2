package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JNormativaUnidadAdministrativaPK;

import javax.persistence.*;

/**
 * La clase J normativa unidad administrativa.
 */
@Entity
@Table(name = "RS2_UADNOR")
public class JNormativaUnidadAdministrativa {
    /**
     * Codigo
     **/
    @EmbeddedId
    private JNormativaUnidadAdministrativaPK codigo;

    /**
     * Unidad administrativa
     **/
    @MapsId("unidadAdministrativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UANO_CODUNA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Normativa
     **/
    @MapsId("normativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UANO_CODNORM", nullable = false)
    private JNormativa normativa;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public JNormativaUnidadAdministrativaPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(JNormativaUnidadAdministrativaPK id) {
        this.codigo = id;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return  unidad administrativa
     */
    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param uanoCoduna  uano coduna
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa uanoCoduna) {
        this.unidadAdministrativa = uanoCoduna;
    }

    /**
     * Obtiene normativa.
     *
     * @return  normativa
     */
    public JNormativa getNormativa() {
        return normativa;
    }

    /**
     * Establece normativa.
     *
     * @param uanoCodnorm  uano codnorm
     */
    public void setNormativa(JNormativa uanoCodnorm) {
        this.normativa = uanoCodnorm;
    }

}