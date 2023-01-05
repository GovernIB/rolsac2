package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * La clase J unidad administrativa evolucion.
 */
@Entity
@SequenceGenerator(name = "ua-evol-sequence", sequenceName = "RS2_UNAEVO_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAEVO",
        indexes = {
                @Index(name = "RS2_UNAEVO_PK_I", columnList = "UAEV_CODIGO")
        }
)
public class JUnidadAdministrativaEvolucion {
    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-evol-sequence")
    @Column(name = "UAEV_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Tipo
     */
    @Column(name = "UAEV_TIPO", nullable = false, length = 1)
    private String tipo;

    /**
     * Fecha
     */
    @Column(name = "UAEV_FECHA", nullable = false)
    private LocalDate fecha;

    /**
     * Unidad administrativa
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UAEV_UADEP")
    private JUnidadAdministrativa unidadAdministrativa;

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
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param uaevTipo  uaev tipo
     */
    public void setTipo(String uaevTipo) {
        this.tipo = uaevTipo;
    }

    /**
     * Obtiene fecha.
     *
     * @return  fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece fecha.
     *
     * @param uaevFecha  uaev fecha
     */
    public void setFecha(LocalDate uaevFecha) {
        this.fecha = uaevFecha;
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
     * @param uaevUadep  uaev uadep
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa uaevUadep) {
        this.unidadAdministrativa = uaevUadep;
    }

}