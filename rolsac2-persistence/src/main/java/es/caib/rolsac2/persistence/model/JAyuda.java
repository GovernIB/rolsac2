package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JAyudaTraduccion;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * La clase JAyuda
 */
@Entity
@SequenceGenerator(name = "ayuda-sequence", sequenceName = "RS2_AYUDA_SEQ", allocationSize = 1)
@Table(name = "RS2_AYUDA", indexes = {@Index(name = "RS2_AYUDA_PK_I", columnList = "AYU_CODIGO")})
@NamedQueries({@NamedQuery(name = JAyuda.FIND_BY_ID, query = "select p from JAyuda p where p.codigo = :id"), @NamedQuery(name = JAyuda.COUNT_BY_IDENTIFICADOR, query = "select count(p) from JAyuda p where p.identificador = :identificador")})
public class JAyuda extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Ayuda.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "Ayuda.COUNT_BY_IDENTIFICADOR";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR_ENTIDAD = "Ayuda.COUNT_BY_IDENTIFICADOR_ENTIDAD";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ayuda-sequence")
    @Column(name = "AYU_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Identificador
     */
    @Column(name = "AYU_IDENTIFICADOR", nullable = false, length = 100)
    private String identificador;

    /**
     * Perfil
     */
    @Column(name = "AYU_PERFIL", nullable = false, length = 10)
    private String perfil;

    /**
     * Fecha Creacion
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AYU_FECCREA")
    private Date fechaCreacion;

    /**
     * Fecha ini
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AYU_FECACT")
    private Date fechaActualizacion;

    @OneToMany(mappedBy = "ayuda", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JAyudaTraduccion> traducciones;

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public List<JAyudaTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setTraducciones(List<JAyudaTraduccion> descripcion) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = descripcion;
        } else {
            this.traducciones.addAll(descripcion);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JAyuda jayuda = (JAyuda) o;
        return codigo.equals(jayuda.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JAyuda{" + "codigo=" + codigo + ", identificador='" + identificador + '\'' + ", descripcion=" + traducciones + '}';
    }
}