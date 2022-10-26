package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.Objects;

/**
 * Dades d'un ProcedimientoTramite.
 *
 */
@Schema(name = "ProcedimientoTramite")
public class ProcedimientoTramiteDTO extends ModelApi {

    private Long codigo;
    private UnidadAdministrativaDTO unidadAdministrativa;
    private ProcedimientoWorkflowDTO procedimiento;
    private TipoTramitacionDTO tipoTramitacion;
    private ListaDocumentosDto listaDocumentos;
    private ListaDocumentosDto listaModelos;

    private Boolean tasaAsociada = false;
    private Literal requisitos;
    private Literal nombre;
    private Literal documentacion;
    private Literal observacion;
    private Literal terminoMaximo;

    private Date fechaPublicacion;
    private Date fechaInicio;
    private Date fechaCierre;

    public static ProcedimientoTramiteDTO createInstance() {
        ProcedimientoTramiteDTO procedimientoTramite = new ProcedimientoTramiteDTO();
        procedimientoTramite.setRequisitos(Literal.createInstance());
        procedimientoTramite.setNombre(Literal.createInstance());
        procedimientoTramite.setDocumentacion(Literal.createInstance());
        procedimientoTramite.setObservacion(Literal.createInstance());
        procedimientoTramite.setTerminoMaximo(Literal.createInstance());
        return procedimientoTramite;
    }

    public ProcedimientoTramiteDTO() {}

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

    public ListaDocumentosDto getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(ListaDocumentosDto listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public ListaDocumentosDto getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(ListaDocumentosDto listaModelos) {
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
