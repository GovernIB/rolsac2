package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JFichaTemaPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RS2_FCHTEM")
public class JFichaTema {
    @EmbeddedId
    private JFichaTemaPK codigo;

    public JFichaTemaPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JFichaTemaPK id) {
        this.codigo = id;
    }

//TODO [JPA Buddy] generate columns from DB
}