package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J unidad administrativa seccion.
 */
@Entity
@SequenceGenerator(name = "ua-seccion-sequence", sequenceName = "RS2_UNASEC_SEQ", allocationSize = 1)
@Table(name = "RS2_UNASEC",
        indexes = {
                @Index(name = "RS2_UNASEC_PK_I", columnList = "UASE_CODIGO")
        }
)
public class JUnidadAdministrativaSeccion {
    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-seccion-sequence")
    @Column(name = "UASE_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Unidad administrativa
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UASE_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Seccion
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UASE_CODSEC", nullable = false)
    private JSeccion seccion;

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
     * @param uaseCodua  uase codua
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa uaseCodua) {
        this.unidadAdministrativa = uaseCodua;
    }

    /**
     * Obtiene seccion.
     *
     * @return  seccion
     */
    public JSeccion getSeccion() {
        return seccion;
    }

    /**
     * Establece seccion.
     *
     * @param seccion  seccion
     */
    public void setSeccion(JSeccion seccion) {
        this.seccion = seccion;
    }
}