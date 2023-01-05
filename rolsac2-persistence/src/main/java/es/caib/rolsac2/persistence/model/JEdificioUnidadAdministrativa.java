package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JEdificioUnidadAdministrativaPK;

import javax.persistence.*;

/**
 * La clase J edificio unidad administrativa.
 */
@Entity
@Table(name = "RS2_UNAEDI")
public class JEdificioUnidadAdministrativa {
    @EmbeddedId
    private JEdificioUnidadAdministrativaPK codigo;

    /**
     * Edificio
     **/
    @MapsId("edificio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAED_CODEDI", nullable = false)
    private JEdificio edificio;

    /**
     * Unidad Administrativa
     **/
    @MapsId("unidadAdministrativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAED_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public JEdificioUnidadAdministrativaPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(JEdificioUnidadAdministrativaPK id) {
        this.codigo = id;
    }

    /**
     * Obtiene edificio.
     *
     * @return  edificio
     */
    public JEdificio getEdificio() {
        return edificio;
    }

    /**
     * Establece edificio.
     *
     * @param uaedCodedi  uaed codedi
     */
    public void setEdificio(JEdificio uaedCodedi) {
        this.edificio = uaedCodedi;
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
     * @param uaedCodua  uaed codua
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa uaedCodua) {
        this.unidadAdministrativa = uaedCodua;
    }

}