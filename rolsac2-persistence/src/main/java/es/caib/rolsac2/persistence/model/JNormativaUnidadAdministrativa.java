package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JNormativaUnidadAdministrativaPK;

import javax.persistence.*;

@Entity
@Table(name = "RS2_UADNOR")
public class JNormativaUnidadAdministrativa {
    @EmbeddedId
    private JNormativaUnidadAdministrativaPK codigo;

    @MapsId("unidadAdministrativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UANO_CODUNA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    @MapsId("normativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UANO_CODNORM", nullable = false)
    private JNormativa normativa;

    public JNormativaUnidadAdministrativaPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JNormativaUnidadAdministrativaPK id) {
        this.codigo = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa uanoCoduna) {
        this.unidadAdministrativa = uanoCoduna;
    }

    public JNormativa getNormativa() {
        return normativa;
    }

    public void setNormativa(JNormativa uanoCodnorm) {
        this.normativa = uanoCodnorm;
    }

}