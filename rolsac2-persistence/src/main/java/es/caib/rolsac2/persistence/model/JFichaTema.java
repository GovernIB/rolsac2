package es.caib.rolsac2.persistence.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RS2_FCHTEM")
public class JFichaTema {
    @EmbeddedId
    private JFichaTemaPK id;

    public JFichaTemaPK getId() {
        return id;
    }

    public void setId(JFichaTemaPK id) {
        this.id = id;
    }

//TODO [JPA Buddy] generate columns from DB
}