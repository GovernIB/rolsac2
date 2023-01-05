package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Dades d'un ProcedimientoTramite.
 */
@Schema(name = "ProcedimientoTramite")
public class ProcedimientoTramiteDTO extends ModelApi implements Cloneable {

    /**
     * LOGGER
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoTramiteDTO.class);

    private Long codigo;

    private Integer fase;

    private String codigoString;
    private UnidadAdministrativaDTO unidadAdministrativa;
    private ProcedimientoWorkflowDTO procedimiento;
    private TipoTramitacionDTO tipoTramitacion;
    private TipoTramitacionDTO plantillaSel;
    private List<ProcedimientoDocumentoDTO> listaDocumentos;
    private List<ProcedimientoDocumentoDTO> listaModelos;

    private Boolean tasaAsociada = false;
    private Literal requisitos;
    private Literal nombre;
    private Literal documentacion;
    private Literal observacion;
    private Literal terminoMaximo;

    private Date fechaPublicacion;
    private Date fechaInicio;
    private Date fechaCierre;

    /**
     * Tipos de presentacion: telematica, presencial o telefonica.
     **/
    private boolean tramitPresencial;
    private boolean tramitElectronica;
    private boolean tramitTelefonica;

    /**
     * Crea instancia de procedimiento tramite dto.
     *
     * @param idiomas  idiomas
     * @return  procedimiento tramite dto
     */
    public static ProcedimientoTramiteDTO createInstance(List<String> idiomas) {
        ProcedimientoTramiteDTO procedimientoTramite = new ProcedimientoTramiteDTO();
        procedimientoTramite.setCodigoString(String.valueOf(Calendar.getInstance().getTime().getTime()));
        procedimientoTramite.setTramitPresencial(false);
        procedimientoTramite.setTramitElectronica(false);
        procedimientoTramite.setTramitTelefonica(false);
        if (idiomas == null || idiomas.isEmpty()) {
            procedimientoTramite.setRequisitos(Literal.createInstance());
            procedimientoTramite.setNombre(Literal.createInstance());
            procedimientoTramite.setDocumentacion(Literal.createInstance());
            procedimientoTramite.setObservacion(Literal.createInstance());
            procedimientoTramite.setTerminoMaximo(Literal.createInstance());
        } else {
            procedimientoTramite.setRequisitos(Literal.createInstance(idiomas));
            procedimientoTramite.setNombre(Literal.createInstance(idiomas));
            procedimientoTramite.setDocumentacion(Literal.createInstance(idiomas));
            procedimientoTramite.setObservacion(Literal.createInstance(idiomas));
            procedimientoTramite.setTerminoMaximo(Literal.createInstance(idiomas));
        }
        return procedimientoTramite;
    }

    /**
     * Instancia un nuevo Procedimiento tramite dto.
     */
    public ProcedimientoTramiteDTO() {
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return  codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Agregar documento.
     *
     * @param doc  doc
     */
    public void agregarDocumento(ProcedimientoDocumentoDTO doc) {
        if (getListaDocumentos() == null) {
            this.setListaDocumentos(new ArrayList<>());
        }
        boolean encontrado = false;
        for (int i = 0; i < this.getListaDocumentos().size(); i++) {
            ProcedimientoDocumentoDTO documento = this.getListaDocumentos().get(i);
            if (doc.getCodigo() == null && documento.getCodigo() == null && doc.getCodigoString() != null && doc.getCodigoString().equalsIgnoreCase(documento.getCodigoString())) {
                encontrado = true;
                this.getListaDocumentos().set(i, doc);
                break;
            } else if (doc.getCodigo() != null && documento.getCodigo() != null && doc.getCodigo().compareTo(documento.getCodigo()) == 0) {
                encontrado = true;
                this.getListaDocumentos().set(i, doc);
                break;
            }
        }

        if (!encontrado) {
            this.getListaDocumentos().add(doc);
        }
    }


    /**
     * Agregar modelo.
     *
     * @param doc  doc
     */
    public void agregarModelo(ProcedimientoDocumentoDTO doc) {
        if (getListaModelos() == null) {
            this.setListaModelos(new ArrayList<>());
        }
        boolean encontrado = false;
        for (int i = 0; i < this.getListaModelos().size(); i++) {
            ProcedimientoDocumentoDTO documento = this.getListaModelos().get(i);
            if (doc.getCodigo() == null && documento.getCodigo() == null && doc.getCodigoString() != null && doc.getCodigoString().equalsIgnoreCase(documento.getCodigoString())) {
                encontrado = true;
                this.getListaModelos().set(i, doc);
                break;
            } else if (doc.getCodigo() != null && documento.getCodigo() != null && doc.getCodigo().compareTo(documento.getCodigo()) == 0) {
                encontrado = true;
                this.getListaModelos().set(i, doc);
                break;
            }
        }

        if (!encontrado) {
            this.getListaModelos().add(doc);
        }
    }

    /**
     * Instantiates a new Procedimiento tramite dto.
     *
     * @param id  id
     */
    public ProcedimientoTramiteDTO(Long id) {
        this.codigo = id;
    }

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
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return  unidad administrativa
     */
    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param unidadAdministrativa  unidad administrativa
     */
    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return  procedimiento
     */
    public ProcedimientoWorkflowDTO getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param procedimiento  procedimiento
     */
    public void setProcedimiento(ProcedimientoWorkflowDTO procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * Obtiene tipo tramitacion.
     *
     * @return  tipo tramitacion
     */
    public TipoTramitacionDTO getTipoTramitacion() {
        return tipoTramitacion;
    }

    /**
     * Establece tipo tramitacion.
     *
     * @param tipoTramitacion  tipo tramitacion
     */
    public void setTipoTramitacion(TipoTramitacionDTO tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    /**
     * Obtiene lista documentos.
     *
     * @return  lista documentos
     */
    public List<ProcedimientoDocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * Establece lista documentos.
     *
     * @param listaDocumentos  lista documentos
     */
    public void setListaDocumentos(List<ProcedimientoDocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    /**
     * Obtiene lista modelos.
     *
     * @return  lista modelos
     */
    public List<ProcedimientoDocumentoDTO> getListaModelos() {
        return listaModelos;
    }

    /**
     * Establece lista modelos.
     *
     * @param listaModelos  lista modelos
     */
    public void setListaModelos(List<ProcedimientoDocumentoDTO> listaModelos) {
        this.listaModelos = listaModelos;
    }

    /**
     * Obtiene tasa asociada.
     *
     * @return  tasa asociada
     */
    public Boolean getTasaAsociada() {
        return tasaAsociada;
    }

    /**
     * Establece tasa asociada.
     *
     * @param tasaAsociada  tasa asociada
     */
    public void setTasaAsociada(Boolean tasaAsociada) {
        this.tasaAsociada = tasaAsociada;
    }

    /**
     * Obtiene requisitos.
     *
     * @return  requisitos
     */
    public Literal getRequisitos() {
        return requisitos;
    }

    /**
     * Establece requisitos.
     *
     * @param requisitos  requisitos
     */
    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene documentacion.
     *
     * @return  documentacion
     */
    public Literal getDocumentacion() {
        return documentacion;
    }

    /**
     * Establece documentacion.
     *
     * @param documentacion  documentacion
     */
    public void setDocumentacion(Literal documentacion) {
        this.documentacion = documentacion;
    }

    /**
     * Obtiene observacion.
     *
     * @return  observacion
     */
    public Literal getObservacion() {
        return observacion;
    }

    /**
     * Establece observacion.
     *
     * @param observacion  observacion
     */
    public void setObservacion(Literal observacion) {
        this.observacion = observacion;
    }

    /**
     * Obtiene termino maximo.
     *
     * @return  termino maximo
     */
    public Literal getTerminoMaximo() {
        return terminoMaximo;
    }

    /**
     * Establece termino maximo.
     *
     * @param terminoMaximo  termino maximo
     */
    public void setTerminoMaximo(Literal terminoMaximo) {
        this.terminoMaximo = terminoMaximo;
    }

    /**
     * Obtiene fecha publicacion.
     *
     * @return  fecha publicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece fecha publicacion.
     *
     * @param fechaPublicacion  fecha publicacion
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene fecha inicio.
     *
     * @return  fecha inicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece fecha inicio.
     *
     * @param fechaInicio  fecha inicio
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene fecha cierre.
     *
     * @return  fecha cierre
     */
    public Date getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Establece fecha cierre.
     *
     * @param fechaCierre  fecha cierre
     */
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /**
     * Obtiene codigo string.
     *
     * @return  codigo string
     */
    public String getCodigoString() {
        if (codigo == null) {
            return codigoString;
        } else {
            return codigo.toString();
        }
    }

    /**
     * Establece codigo string.
     *
     * @param codigoString  codigo string
     */
    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }

    /**
     * Obtiene fase.
     *
     * @return  fase
     */
    public Integer getFase() {
        return fase;
    }

    /**
     * Establece fase.
     *
     * @param fase  fase
     */
    public void setFase(Integer fase) {
        this.fase = fase;
    }

    /**
     * Obtiene plantilla sel.
     *
     * @return  plantilla sel
     */
    public TipoTramitacionDTO getPlantillaSel() {
        return plantillaSel;
    }

    /**
     * Establece plantilla sel.
     *
     * @param plantillaSel  plantilla sel
     */
    public void setPlantillaSel(TipoTramitacionDTO plantillaSel) {
        this.plantillaSel = plantillaSel;
    }

    /**
     * Is tramit presencial boolean.
     *
     * @return  boolean
     */
    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    /**
     * Establece tramit presencial.
     *
     * @param tramitPresencial  tramit presencial
     */
    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    /**
     * Is tramit electronica boolean.
     *
     * @return  boolean
     */
    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    /**
     * Establece tramit electronica.
     *
     * @param tramitElectronica  tramit electronica
     */
    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    /**
     * Is tramit telefonica boolean.
     *
     * @return  boolean
     */
    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    /**
     * Establece tramit telefonica.
     *
     * @param tramitTelefonica  tramit telefonica
     */
    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }


    public Object clone() {
        ProcedimientoTramiteDTO obj = null;
        try {
            obj = (ProcedimientoTramiteDTO) super.clone();
            if (obj.tipoTramitacion != null) {
                obj.tipoTramitacion = (TipoTramitacionDTO) this.tipoTramitacion.clone();
            }
            if (obj.nombre != null) {
                obj.nombre = (Literal) this.nombre.clone();
            }
            if (obj.documentacion != null) {
                obj.documentacion = (Literal) this.documentacion.clone();
            }
            if (obj.requisitos != null) {
                obj.requisitos = (Literal) this.requisitos.clone();
            }
            if (obj.observacion != null) {
                obj.observacion = (Literal) this.observacion.clone();
            }
            if (obj.terminoMaximo != null) {
                obj.terminoMaximo = (Literal) this.terminoMaximo.clone();
            }
            if (listaDocumentos != null) {
                List<ProcedimientoDocumentoDTO> lista = new ArrayList<>();
                for (ProcedimientoDocumentoDTO doc : listaDocumentos) {
                    lista.add((ProcedimientoDocumentoDTO) doc.clone());
                }
                obj.setListaDocumentos(lista);
            }
            if (listaModelos != null) {
                List<ProcedimientoDocumentoDTO> lista = new ArrayList<>();
                for (ProcedimientoDocumentoDTO doc : listaModelos) {
                    lista.add((ProcedimientoDocumentoDTO) doc.clone());
                }
                obj.setListaModelos(lista);
            }
        } catch (CloneNotSupportedException ex) {
            LOG.error(" no se puede duplicar", ex);
        }
        return obj;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProcedimientoTramiteDTO that = (ProcedimientoTramiteDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }


    @Override
    public String toString() {
        return "ProcedimientoTramiteDTO{" + "codigo=" + codigo + ", unidadAdministrativa=" + unidadAdministrativa
                + ", procedimiento=" + procedimiento + ", tipoTramitacion=" + tipoTramitacion
                + ", listaDocumentos=" + listaDocumentos + ", listaModelos=" + listaModelos + ", tasaAsociada="
                + tasaAsociada + ", requisitos=" + requisitos + ", nombre=" + nombre + ", documentacion="
                + documentacion + ", observacion=" + observacion + ", terminoMaximo=" + terminoMaximo + '}';
    }
}
