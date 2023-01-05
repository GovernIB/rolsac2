package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JFichaTemaPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * La clase J ficha tema.
 */
@Entity
@Table(name = "RS2_FCHTEM")
public class JFichaTema {
    @EmbeddedId
    private JFichaTemaPK codigo;

    /**
     * Gets codigo.
     *
     * @return  codigo
     */
    public JFichaTemaPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(JFichaTemaPK id) {
        this.codigo = id;
    }

//TODO [JPA Buddy] generate columns from DB
}