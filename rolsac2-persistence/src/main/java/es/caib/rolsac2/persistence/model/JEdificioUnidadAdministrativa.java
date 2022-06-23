package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JEdificioUnidadAdministrativaPK;

import javax.persistence.*;

@Entity
@Table(name = "RS2_UNAEDI")
public class JEdificioUnidadAdministrativa {
    @EmbeddedId
    private JEdificioUnidadAdministrativaPK id;

    @MapsId("edificio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAED_CODEDI", nullable = false)
    private JEdificio edificio;

    @MapsId("unidadAdministrativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAED_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    public JEdificioUnidadAdministrativaPK getId() {
        return id;
    }

    public void setId(JEdificioUnidadAdministrativaPK id) {
        this.id = id;
    }

    public JEdificio getEdificio() {
        return edificio;
    }

    public void setEdificio(JEdificio uaedCodedi) {
        this.edificio = uaedCodedi;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa uaedCodua) {
        this.unidadAdministrativa = uaedCodua;
    }

}