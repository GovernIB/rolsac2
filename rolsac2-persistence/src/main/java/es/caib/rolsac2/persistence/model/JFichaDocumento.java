package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-doc-sequence", sequenceName = "RS2_FCHDOC_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHDOC",
        indexes = {
                @Index(name = "RS2_FCHDOC_PK_I", columnList = "FCDO_CODIGO")
        })
public class JFichaDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-doc-sequence")
    @Column(name = "FCDO_CODIGO", nullable = false)
    private Integer id;

    /**
     * Fichero
     **/
    @Column(name = "FCDO_FICHERO")
    private Integer fichero;

    /**
     * Ficha
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCDO_CODFCH", nullable = false)
    private JFicha ficha;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFichero() {
        return fichero;
    }

    public void setFichero(Integer fcdoFichero) {
        this.fichero = fcdoFichero;
    }

    public JFicha getFicha() {
        return ficha;
    }

    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }
}