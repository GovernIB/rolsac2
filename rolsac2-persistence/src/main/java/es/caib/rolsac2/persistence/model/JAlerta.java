package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JAlertaTraduccion;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * La clase JAlerta
 */
@Entity
@SequenceGenerator(name = "alerta-sequence", sequenceName = "RS2_ALERTAS_SEQ", allocationSize = 1)
@Table(name = "RS2_ALERTAS", indexes = {@Index(name = "RS2_ALERTAS_PK_I", columnList = "ALER_CODIGO")})
@NamedQueries({@NamedQuery(name = JAlerta.FIND_BY_ID, query = "select p from JAlerta p where p.codigo = :id"), @NamedQuery(name = JAlerta.COUNT_BY_IDENTIFICADOR, query = "select count(p) from JAlerta p where p.tipo = :tipo"), @NamedQuery(name = JAlerta.COUNT_BY_IDENTIFICADOR_ENTIDAD, query = "select count(p) from JAlerta p where p.tipo = :tipo and p.entidad.codigo = :entidad")})
public class JAlerta extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Alerta.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "Alerta.COUNT_BY_IDENTIFICADOR";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR_ENTIDAD = "Alerta.COUNT_BY_IDENTIFICADOR_ENTIDAD";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta-sequence")
    @Column(name = "ALER_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ALER_CODENT", nullable = false)
    private JEntidad entidad;

    /**
     * UA cuando ambito es UA.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ALER_CODUNA", nullable = true)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Identificador
     */
    @Column(name = "ALER_TIPO", nullable = false, length = 3)
    private String tipo;

    /**
     * Identificador
     */
    @Column(name = "ALER_AMBITO", nullable = false, length = 3)
    private String ambito;

    /**
     * Fecha ini
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ALER_FECINI")
    private Date fechaIni;

    /**
     * Fecha ini
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ALER_FECFIN")
    private Date fechaFin;

    /**
     * Descendientes UA cuando ambito es UA.
     */
    @Column(name = "ALER_DESCEN")
    private boolean descendientes;

    /**
     * Perfil cuando ambito es de tipo perfil
     */
    @Column(name = "ALER_PERFIL")
    private String perfil;

    @OneToMany(mappedBy = "alerta", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JAlertaTraduccion> traducciones;


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

    /**
     * Obtiene entidad.
     *
     * @return entidad
     */
    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<JAlertaTraduccion> getTraducciones() {
        return traducciones;
    }

    public boolean isDescendientes() {
        return descendientes;
    }

    public void setDescendientes(boolean descendientes) {
        this.descendientes = descendientes;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setTraducciones(List<JAlertaTraduccion> descripcion) {
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
        JAlerta jalerta = (JAlerta) o;
        return codigo.equals(jalerta.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JAlerta{" + "codigo=" + codigo + ", entidad=" + entidad + ", identificador='" + tipo + '\'' + ", descripcion=" + traducciones + '}';
    }
}