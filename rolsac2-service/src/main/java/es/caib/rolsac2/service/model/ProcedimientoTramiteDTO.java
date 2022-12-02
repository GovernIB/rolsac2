package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.*;

/**
 * Dades d'un ProcedimientoTramite.
 */
@Schema(name = "ProcedimientoTramite")
public class ProcedimientoTramiteDTO extends ModelApi {

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

    public ProcedimientoTramiteDTO() {
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

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

    public ProcedimientoTramiteDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public ProcedimientoWorkflowDTO getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(ProcedimientoWorkflowDTO procedimiento) {
        this.procedimiento = procedimiento;
    }

    public TipoTramitacionDTO getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(TipoTramitacionDTO tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    public List<ProcedimientoDocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<ProcedimientoDocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<ProcedimientoDocumentoDTO> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<ProcedimientoDocumentoDTO> listaModelos) {
        this.listaModelos = listaModelos;
    }

    public Boolean getTasaAsociada() {
        return tasaAsociada;
    }

    public void setTasaAsociada(Boolean tasaAsociada) {
        this.tasaAsociada = tasaAsociada;
    }

    public Literal getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    public Literal getNombre() {
        return nombre;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    public Literal getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(Literal documentacion) {
        this.documentacion = documentacion;
    }

    public Literal getObservacion() {
        return observacion;
    }

    public void setObservacion(Literal observacion) {
        this.observacion = observacion;
    }

    public Literal getTerminoMaximo() {
        return terminoMaximo;
    }

    public void setTerminoMaximo(Literal terminoMaximo) {
        this.terminoMaximo = terminoMaximo;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getCodigoString() {
        if (codigo == null) {
            return codigoString;
        } else {
            return codigo.toString();
        }
    }

    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }

    public Integer getFase() {
        return fase;
    }

    public void setFase(Integer fase) {
        this.fase = fase;
    }

    public TipoTramitacionDTO getPlantillaSel() {
        return plantillaSel;
    }

    public void setPlantillaSel(TipoTramitacionDTO plantillaSel) {
        this.plantillaSel = plantillaSel;
    }

    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
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
