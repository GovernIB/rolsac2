package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoTramitacionTraduccion;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un tipo de tramitación. A nivel de clase, definimos la
 * secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-tramitacion-sequence", sequenceName = "RS2_TRMPRE_SEQ", allocationSize = 1)
@Table(name = "RS2_TRMPRE", indexes = {@Index(name = "RS2_TRMPRE_PK_I", columnList = "PRES_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoTramitacion.FIND_BY_ID, query = "select p from JTipoTramitacion p where p.codigo = :id")})
public class JTipoTramitacion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La constante FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "JTipoTramitacion.FIND_BY_ID";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-tramitacion-sequence")
    @Column(name = "PRES_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "tipoTramitacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoTramitacionTraduccion> traducciones;
    /**
     * Tramitación presencial
     */
    @Column(name = "PRES_TRPRES", nullable = false, precision = 1, scale = 0)
    private boolean tramitPresencial;

    /**
     * Tramitación electrónica
     */
    @Column(name = "PRES_TRELEC", nullable = false, precision = 1, scale = 0)
    private boolean tramitElectronica;

    /**
     * Tramitacion telefonica
     */
    @Column(name = "PRES_TRTEL", nullable = false, precision = 1, scale = 0)
    private boolean tramitTelefonica;


    /**
     * Código plataforma tramitación
     */
    @ManyToOne
    @JoinColumn(name = "PRES_INTPTR")
    private JPlatTramitElectronica codPlatTramitacion;

    /**
     * Trámite Id
     */
    @Column(name = "PRES_INTTID", nullable = false, length = 50)
    private String tramiteId;

    /**
     * Fase Procedimiento
     */
    @Column(name = "PRES_FASEPROC")
    private Integer faseProc;

    /**
     * Trámite Versión
     */
    @Column(name = "PRES_INTTVE", nullable = false, precision = 3, scale = 0)
    private Integer tramiteVersion;

    /**
     * Trámite parámetros
     */
    @Column(name = "PRES_INTTPA", nullable = false, length = 500)
    private String tramiteParametros;

    /**
     * Tramitación electrónica
     */
    @Column(name = "PRES_PLANTI", nullable = false, precision = 1, scale = 0)
    private boolean plantilla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRES_CODENTI")
    private JEntidad entidad;

    public static JTipoTramitacion clonar(JTipoTramitacion otro) {
        JTipoTramitacion tipoTramitacion = null;
        if (otro != null) {
            tipoTramitacion = new JTipoTramitacion();
            //tipoTramitacion.setCodigo(otro.getCodigo());
            tipoTramitacion.setTramitElectronica(otro.isTramitElectronica());
            tipoTramitacion.setCodPlatTramitacion(otro.getCodPlatTramitacion());
            tipoTramitacion.setFaseProc(otro.getFaseProc());
            tipoTramitacion.setTramiteId(otro.getTramiteId());
            tipoTramitacion.setTramiteVersion(otro.getTramiteVersion());
            tipoTramitacion.setTramiteParametros(otro.getTramiteParametros());
            tipoTramitacion.setTramitPresencial(otro.isTramitPresencial());
            tipoTramitacion.setTramitElectronica(otro.isTramitElectronica());
            tipoTramitacion.setPlantilla(otro.isPlantilla());
            tipoTramitacion.setEntidad(otro.getEntidad());
            tipoTramitacion.setTraducciones(JTipoTramitacionTraduccion.clonar(otro.getTraducciones(), tipoTramitacion));
        }
        return tipoTramitacion;
    }


    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Is tramit presencial boolean.
     *
     * @return boolean
     */
    public boolean isTramitPresencial() {
        return tramitPresencial;
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
     * Establece tramit presencial.
     *
     * @param tramitPresencial tramit presencial
     */
    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    /**
     * Is tramit electronica boolean.
     *
     * @return boolean
     */
    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    /**
     * Establece tramit electronica.
     *
     * @param tramitElectronica tramit electronica
     */
    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    /**
     * Obtiene cod plat tramitacion.
     *
     * @return cod plat tramitacion
     */
    public JPlatTramitElectronica getCodPlatTramitacion() {
        return codPlatTramitacion;
    }

    /**
     * Establece cod plat tramitacion.
     *
     * @param codPlatTramitacion cod plat tramitacion
     */
    public void setCodPlatTramitacion(JPlatTramitElectronica codPlatTramitacion) {
        this.codPlatTramitacion = codPlatTramitacion;
    }

    /**
     * Obtiene tramite id.
     *
     * @return tramite id
     */
    public String getTramiteId() {
        return tramiteId;
    }

    /**
     * Is tramit telefonica boolean.
     *
     * @return boolean
     */
    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    /**
     * Establece tramit telefonica.
     *
     * @param tramitTelefonica tramit telefonica
     */
    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    /**
     * Establece tramite id.
     *
     * @param tramiteId tramite id
     */
    public void setTramiteId(String tramiteId) {
        this.tramiteId = tramiteId;
    }

    /**
     * Obtiene tramite version.
     *
     * @return tramite version
     */
    public Integer getTramiteVersion() {
        return tramiteVersion;
    }

    /**
     * Establece tramite version.
     *
     * @param tramiteVersion tramite version
     */
    public void setTramiteVersion(Integer tramiteVersion) {
        this.tramiteVersion = tramiteVersion;
    }

    /**
     * Obtiene tramite parametros.
     *
     * @return tramite parametros
     */
    public String getTramiteParametros() {
        return tramiteParametros;
    }

    /**
     * Establece tramite parametros.
     *
     * @param tramiteParametros tramite parametros
     */
    public void setTramiteParametros(String tramiteParametros) {
        this.tramiteParametros = tramiteParametros;
    }

    /**
     * Is plantilla boolean.
     *
     * @return boolean
     */
    public boolean isPlantilla() {
        return plantilla;
    }

    /**
     * Establece plantilla.
     *
     * @param plantilla plantilla
     */
    public void setPlantilla(boolean plantilla) {
        this.plantilla = plantilla;
    }

    /**
     * Obtiene entidad.
     *
     * @return entidad
     */
    public JEntidad getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad entidad
     */
    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene fase proc.
     *
     * @return fase proc
     */
    public Integer getFaseProc() {
        return faseProc;
    }

    /**
     * Establece fase proc.
     *
     * @param faseProc fase proc
     */
    public void setFaseProc(Integer faseProc) {
        this.faseProc = faseProc;
    }

    /**
     * Obtiene traducciones.
     *
     * @return traducciones
     */
    public List<JTipoTramitacionTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece traducciones.
     *
     * @param traducciones traducciones
     */
    public void setTraducciones(List<JTipoTramitacionTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JTipoTramitacion)) return false;
        JTipoTramitacion that = (JTipoTramitacion) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoTramitacion{" + "id=" + codigo + '}';
       /* return "JTipoTramitacion{" + "id=" + codigo + ", tramitPresencial="
                + tramitPresencial + ", plantilla=" + plantilla + ", tramitElectronica=" + tramitElectronica
                + '\'' + ", codPlatTramitacion=" + codPlatTramitacion
                + ", tramiteId='" + tramiteId + '\'' + ", tramiteVersion=" + tramiteVersion + ", tramiteParametros='"
                + tramiteParametros + '\'' + '}'; **/
    }

    /**
     * Merge.
     *
     * @param tipoTramitacion tipo tramitacion
     */
    public void merge(TipoTramitacionDTO tipoTramitacion) {
        this.setTramitElectronica(tipoTramitacion.isTramitElectronica());
        //this.setCodPlatTramitacion(tipoTramitacion.getCodPlatTramitacion());
        this.setFaseProc(tipoTramitacion.getFaseProc());
        this.setTramiteId(tipoTramitacion.getTramiteId());
        this.setTramiteVersion(tipoTramitacion.getTramiteVersion());
        this.setTramiteParametros(tipoTramitacion.getTramiteParametros());
        this.setTramitPresencial(tipoTramitacion.isTramitPresencial());
        this.setTramitElectronica(tipoTramitacion.isTramitElectronica());

        //Vamos a crear primero las traducciones que antes no existian
        if (tipoTramitacion.getUrl() != null || tipoTramitacion.getDescripcion() != null) {
            List<String> idiomas = new ArrayList<>();
            if (tipoTramitacion.getUrl() != null) {
                idiomas.addAll(tipoTramitacion.getUrl().getIdiomas());
            }
            if (tipoTramitacion.getDescripcion() != null) {
                List<String> idiomasDescripcion = tipoTramitacion.getDescripcion().getIdiomas();
                for (String idioma : idiomasDescripcion) {
                    if (!idiomas.contains(idioma)) {
                        idiomas.add(idioma);
                    }
                }
            }
            for (String idioma : idiomas) {
                boolean encontrado = false;
                for (JTipoTramitacionTraduccion traduccion : this.getTraducciones()) {
                    if (traduccion.getIdioma().equals(idioma)) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    JTipoTramitacionTraduccion traduccion = new JTipoTramitacionTraduccion();
                    traduccion.setIdioma(idioma);
                    traduccion.setTipoTramitacion(this);
                    this.getTraducciones().add(traduccion);
                }
            }
        }

        if (this.getTraducciones() != null && tipoTramitacion.getUrl() != null) {
            for (JTipoTramitacionTraduccion traduccion : this.getTraducciones()) {
                if (tipoTramitacion.getUrl() != null) {
                    traduccion.setUrl(tipoTramitacion.getUrl().getTraduccion(traduccion.getIdioma()));
                }
                if (tipoTramitacion.getDescripcion() != null) {
                    traduccion.setDescripcion(tipoTramitacion.getDescripcion().getTraduccion(traduccion.getIdioma()));
                }

            }
        }
    }

    /**
     * Obtiene traduccion.
     *
     * @param idioma idioma
     * @return traduccion
     */
    public JTipoTramitacionTraduccion getTraduccion(String idioma) {
        JTipoTramitacionTraduccion retorno = null;
        if (this.traducciones != null && idioma != null) {
            for (JTipoTramitacionTraduccion trad : this.getTraducciones()) {
                if (idioma.equals(trad.getIdioma())) {
                    retorno = trad;
                    break;
                }
            }
        }
        return retorno;
    }
}
