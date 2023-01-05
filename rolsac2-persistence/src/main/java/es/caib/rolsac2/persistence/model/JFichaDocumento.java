package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J ficha documento.
 */
@Entity
@SequenceGenerator(name = "ficha-doc-sequence", sequenceName = "RS2_FCHDOC_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHDOC",
        indexes = {
                @Index(name = "RS2_FCHDOC_PK_I", columnList = "FCDO_CODIGO")
        })
public class JFichaDocumento {

    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-doc-sequence")
    @Column(name = "FCDO_CODIGO", nullable = false)
    private Integer codigo;

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


    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    /**
     * Obtiene fichero.
     *
     * @return  fichero
     */
    public Integer getFichero() {
        return fichero;
    }

    /**
     * Establece fichero.
     *
     * @param fcdoFichero  fcdo fichero
     */
    public void setFichero(Integer fcdoFichero) {
        this.fichero = fcdoFichero;
    }

    /**
     * Obtiene ficha.
     *
     * @return  ficha
     */
    public JFicha getFicha() {
        return ficha;
    }

    /**
     * Establece ficha.
     *
     * @param ficha  ficha
     */
    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }
}