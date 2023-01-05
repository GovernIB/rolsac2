package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J ficha.
 */
@Entity
@SequenceGenerator(name = "ficha-sequence", sequenceName = "RS2_FICHA_SEQ", allocationSize = 1)
@Table(name = "RS2_FICHA",
        indexes = {
                @Index(name = "RS2_FICHA_PK_I", columnList = "FCHA_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JFicha.FIND_BY_ID,
                query = "select p from JFicha p where p.codigo = :id")
})
public class JFicha {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-sequence")
    @Column(name = "FCHA_CODIGO", nullable = false)
    private Long codigo;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Ficha.FIND_BY_ID";

    /**
     * Responsable
     **/
    @Column(name = "FCHA_RESPNOM")
    private String responsable;

    /**
     * Notas
     **/
    @Column(name = "FCHA_NOTAS", length = 4000)
    private String notas;

    /**
     * Seccion de la unidad administrativa
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCME_TIPOMED", nullable = false)
    private JUnidadAdministrativaSeccion seccionUA;


    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene responsable.
     *
     * @return  responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * Establece responsable.
     *
     * @param fchaRespnom  fcha respnom
     */
    public void setResponsable(String fchaRespnom) {
        this.responsable = fchaRespnom;
    }

    /**
     * Obtiene notas.
     *
     * @return  notas
     */
    public String getNotas() {
        return notas;
    }

    /**
     * Establece notas.
     *
     * @param fchaNotas  fcha notas
     */
    public void setNotas(String fchaNotas) {
        this.notas = fchaNotas;
    }

    /**
     * Obtiene seccion ua.
     *
     * @return  seccion ua
     */
    public JUnidadAdministrativaSeccion getSeccionUA() {
        return seccionUA;
    }

    /**
     * Establece seccion ua.
     *
     * @param seccionUA  seccion ua
     */
    public void setSeccionUA(JUnidadAdministrativaSeccion seccionUA) {
        this.seccionUA = seccionUA;
    }
}