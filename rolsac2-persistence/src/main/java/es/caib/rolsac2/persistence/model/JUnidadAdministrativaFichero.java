package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J unidad administrativa fichero.
 */
@Entity
@SequenceGenerator(name = "ua-fic-sequence", sequenceName = "RS2_UNAMED_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAMED",
        indexes = {
                @Index(name = "RS2_UNAMED_PK_I", columnList = "UAME_CODIGO")
        }
)
public class JUnidadAdministrativaFichero {
    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-fic-sequence")
    @Column(name = "UAME_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Unidad administrativa
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAME_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Fichero
     */
    @Column(name = "UAME_FICHERO")
    private Integer fichero;

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
     * @param uameCodua  uame codua
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa uameCodua) {
        this.unidadAdministrativa = uameCodua;
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
     * @param uameFichero  uame fichero
     */
    public void setFichero(Integer uameFichero) {
        this.fichero = uameFichero;
    }

}