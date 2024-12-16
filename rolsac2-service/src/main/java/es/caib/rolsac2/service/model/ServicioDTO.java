package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "ServicioDTO")
public class ServicioDTO extends ProcedimientoBaseDTO {

    private String tasa;
    private TipoTramitacionDTO tipoTramitacion;

    private TipoTramitacionDTO plantillaSel;

    private boolean tramitPresencial;
    private boolean tramitElectronica;
    private boolean tramitTelefonica;
    private boolean activoLOPD;

    public static ServicioDTO createInstance(List<String> idiomas) {
        ServicioDTO serv = new ServicioDTO();
        serv.setActivoLOPD(true);
        serv.setWorkflow(TypeProcedimientoWorkflow.MODIFICACION);
        serv.setEstado(TypeProcedimientoEstado.MODIFICACION);
        serv.setNombreProcedimientoWorkFlow(Literal.createInstance(idiomas));
        serv.setLopdFinalidad(Literal.createInstance(idiomas));
        serv.setLopdInfoAdicional(Literal.createInstance(idiomas));
        serv.setLopdDerechos(Literal.createInstance(idiomas));
        serv.setRequisitos(Literal.createInstance(idiomas));
        serv.setObjeto(Literal.createInstance(idiomas));
        serv.setDestinatarios(Literal.createInstance(idiomas));
        serv.setTerminoResolucion(Literal.createInstance(idiomas));
        serv.setObservaciones(Literal.createInstance(idiomas));
        serv.setTipoTramitacion(TipoTramitacionDTO.createInstance(idiomas));
        serv.setKeywords(Literal.createInstance(idiomas));
        serv.setTramitElectronica(true);
        return serv;
    }

    public String getTasa() {
        return tasa;
    }

    public void setTasa(String tasa) {
        this.tasa = tasa;
    }

    public TipoTramitacionDTO getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(TipoTramitacionDTO tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
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

    public boolean isActivoLOPD() {
        return activoLOPD;
    }

    public void setActivoLOPD(boolean activoLOPD) {
        this.activoLOPD = activoLOPD;
    }

    @Override
    public String toString() {
        return "ServicioDTO{" + "codigo=" + getCodigo() + ", codigoWF='" + getCodigoWF() + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicioDTO that = (ServicioDTO) o;
        return getCodigo().equals(that.getCodigo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }


    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        ServicioDTO srvClonado = new ServicioDTO();
        srvClonado.setCodigo(this.getCodigo());
        srvClonado.setCodigoWF(this.getCodigoWF());
        srvClonado.setWorkflow(this.getWorkflow());
        srvClonado.setEstado(this.getEstado());
        srvClonado.setCodigoSIA(this.getCodigoSIA());
        srvClonado.setEstadoSIA(this.getEstadoSIA());
        srvClonado.setTipo(this.getTipo());
        srvClonado.setPublicado(this.isPublicado());
        srvClonado.setFechaCaducidad(this.getFechaCaducidad());
        srvClonado.setFechaPublicacion(this.getFechaPublicacion());
        srvClonado.setFechaActualizacion(this.getFechaActualizacion());
        srvClonado.setTramitElectronica(this.isTramitElectronica());
        srvClonado.setTramitPresencial(this.isTramitPresencial());
        srvClonado.setTramitTelefonica(this.isTramitTelefonica());

        srvClonado.setResponsable(this.getResponsable());
        srvClonado.setFechaSIA(this.getFechaSIA());
        srvClonado.setComun(this.getComun());
        srvClonado.setLopdResponsable(this.getLopdResponsable());
        srvClonado.setInterno(this.isInterno());
        srvClonado.setMensajes(this.getMensajes());
        srvClonado.setHabilitadoApoderado(this.isHabilitadoApoderado());
        srvClonado.setHabilitadoFuncionario(this.getHabilitadoFuncionario());
        srvClonado.setUsuarioAuditoria(this.getUsuarioAuditoria());

        //Tipos
        if (this.getSilencio() != null) {
            srvClonado.setSilencio((TipoSilencioAdministrativoDTO) this.getSilencio().clone());
        }
        if (this.getDatosPersonalesLegitimacion() != null) {
            srvClonado.setDatosPersonalesLegitimacion((TipoLegitimacionDTO) this.getDatosPersonalesLegitimacion().clone());
        }
        if (this.getIniciacion() != null) {
            srvClonado.setIniciacion((TipoFormaInicioDTO) this.getIniciacion().clone());
        }
        if (this.getUaResponsable() != null) {
            srvClonado.setUaResponsable((UnidadAdministrativaDTO) this.getUaResponsable().clone());
        }
        if (this.getUaInstructor() != null) {
            srvClonado.setUaInstructor((UnidadAdministrativaDTO) this.getUaInstructor().clone());
        }
        if (this.getTipoProcedimiento() != null) {
            srvClonado.setTipoProcedimiento((TipoProcedimientoDTO) this.getTipoProcedimiento().clone());
        }
        if (this.getTipoVia() != null) {
            srvClonado.setTipoVia((TipoViaDTO) this.getTipoVia().clone());
        }

        //Literal
        if (this.getNombreProcedimientoWorkFlow() != null) {
            srvClonado.setNombreProcedimientoWorkFlow((Literal) this.getNombreProcedimientoWorkFlow().clone());
        }
        if (this.getLopdFinalidad() != null) {
            srvClonado.setLopdFinalidad((Literal) this.getLopdFinalidad().clone());
        }
        if (this.getLopdInfoAdicional() != null) {
            srvClonado.setLopdInfoAdicional((Literal) this.getLopdInfoAdicional().clone());
        }
        if (this.getObjeto() != null) {
            srvClonado.setObjeto((Literal) this.getObjeto().clone());
        }
        if (this.getDestinatarios() != null) {
            srvClonado.setDestinatarios((Literal) this.getDestinatarios().clone());
        }
        if (this.getTerminoResolucion() != null) {
            srvClonado.setTerminoResolucion((Literal) this.getTerminoResolucion().clone());
        }
        if (this.getObservaciones() != null) {
            srvClonado.setObservaciones((Literal) this.getObservaciones().clone());
        }
        if (this.getRequisitos() != null) {
            srvClonado.setRequisitos((Literal) this.getRequisitos().clone());
        }

        //Relaciones
        if (getPublicosObjetivo() != null) {
            List<TipoPublicoObjetivoEntidadGridDTO> publicos = new ArrayList<>();
            for (TipoPublicoObjetivoEntidadGridDTO publico : getPublicosObjetivo()) {
                publicos.add((TipoPublicoObjetivoEntidadGridDTO) publico.clone());
            }
            srvClonado.setPublicosObjetivo(publicos);

        }
        if (getDocumentos() != null) {
            List<ProcedimientoDocumentoDTO> docs = new ArrayList<>();
            for (ProcedimientoDocumentoDTO documento : getDocumentos()) {
                docs.add((ProcedimientoDocumentoDTO) documento.clone());
            }
            srvClonado.setDocumentos(docs);

        }
        if (getDocumentosLOPD() != null) {
            List<ProcedimientoDocumentoDTO> docs = new ArrayList<>();
            for (ProcedimientoDocumentoDTO documento : getDocumentosLOPD()) {
                docs.add((ProcedimientoDocumentoDTO) documento.clone());
            }
            srvClonado.setDocumentosLOPD(docs);

        }
        if (getNormativas() != null) {
            List<NormativaGridDTO> norms = new ArrayList<>();
            for (NormativaGridDTO normativa : getNormativas()) {
                norms.add((NormativaGridDTO) normativa.clone());
            }
            srvClonado.setNormativas(norms);

        }
        if (getTemas() != null) {
            List<TemaGridDTO> temas = new ArrayList<>();
            for (TemaGridDTO tema : getTemas()) {
                temas.add((TemaGridDTO) tema.clone());
            }
            srvClonado.setTemas(temas);
        }

        //Exclusivo Procedimiento DTO
        /*
        if (tramites != null) {
            List<ProcedimientoTramiteDTO> tramits = new ArrayList<>();
            for (ProcedimientoTramiteDTO tramite : tramites) {
                tramits.add((ProcedimientoTramiteDTO) tramite.clone());
            }
            procClonado.setTramites(tramits);

        }*/
        return srvClonado;
    }


    public int compareTo(ServicioDTO dataOriginal) {

        if (dataOriginal == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), dataOriginal.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), dataOriginal.getCodigo());
        }

        if (UtilComparador.compareTo(this.getCodigoSIA(), dataOriginal.getCodigoSIA()) != 0) {
            return UtilComparador.compareTo(this.getCodigoSIA(), dataOriginal.getCodigoSIA());
        }

        if (UtilComparador.compareTo(this.getWorkflow(), dataOriginal.getWorkflow()) != 0) {
            return UtilComparador.compareTo(this.getWorkflow(), dataOriginal.getWorkflow());
        }

        if (UtilComparador.compareTo(this.getEstado(), dataOriginal.getEstado()) != 0) {
            return UtilComparador.compareTo(this.getEstado(), dataOriginal.getEstado());
        }
        if (UtilComparador.compareTo(this.getCodigoSIA(), dataOriginal.getCodigoSIA()) != 0) {
            return UtilComparador.compareTo(this.getCodigoSIA(), dataOriginal.getCodigoSIA());
        }
        if (UtilComparador.compareTo(this.getEstadoSIA(), dataOriginal.getEstadoSIA()) != 0) {
            return UtilComparador.compareTo(this.getEstadoSIA(), dataOriginal.getEstadoSIA());
        }
        if (UtilComparador.compareTo(this.getFechaSIA(), dataOriginal.getFechaSIA()) != 0) {
            return UtilComparador.compareTo(this.getFechaSIA(), dataOriginal.getFechaSIA());
        }
        if (UtilComparador.compareTo(this.getTipo(), dataOriginal.getTipo()) != 0) {
            return UtilComparador.compareTo(this.getTipo(), dataOriginal.getTipo());
        }
        if (UtilComparador.compareTo(this.isPublicado(), dataOriginal.isPublicado()) != 0) {
            return UtilComparador.compareTo(this.isPublicado(), dataOriginal.isPublicado());
        }
        if (UtilComparador.compareTo(this.getFechaCaducidad(), dataOriginal.getFechaCaducidad()) != 0) {
            return UtilComparador.compareTo(this.getFechaCaducidad(), dataOriginal.getFechaCaducidad());
        }
        if (UtilComparador.compareTo(this.getFechaPublicacion(), dataOriginal.getFechaPublicacion()) != 0) {
            return UtilComparador.compareTo(this.getFechaPublicacion(), dataOriginal.getFechaPublicacion());
        }
        if (UtilComparador.compareTo(this.getFechaActualizacion(), dataOriginal.getFechaActualizacion()) != 0) {
            return UtilComparador.compareTo(this.getFechaActualizacion(), dataOriginal.getFechaActualizacion());
        }
        if (UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable()) != 0) {
            return UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable());
        }
        if (UtilComparador.compareTo(this.getComun(), dataOriginal.getComun()) != 0) {
            return UtilComparador.compareTo(this.getComun(), dataOriginal.getComun());
        }
        if (UtilComparador.compareTo(this.getLopdResponsable(), dataOriginal.getLopdResponsable()) != 0) {
            return UtilComparador.compareTo(this.getLopdResponsable(), dataOriginal.getLopdResponsable());
        }
        if (UtilComparador.compareTo(this.isInterno(), dataOriginal.isInterno()) != 0) {
            return UtilComparador.compareTo(this.isInterno(), dataOriginal.isInterno());
        }
        if (UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado()) != 0) {
            return UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado());
        }
        if (UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario()) != 0) {
            return UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario());
        }
        /*if (UtilComparador.compareTo(this.getUsuarioAuditoria(), dataOriginal.getUsuarioAuditoria()) != 0) {
            return UtilComparador.compareTo(this.getUsuarioAuditoria(), dataOriginal.getUsuarioAuditoria());
        }*/

        //Tipos
        if (UtilComparador.compareTo(this.getSilencio(), dataOriginal.getSilencio()) != 0) {
            return UtilComparador.compareTo(this.getSilencio(), dataOriginal.getSilencio());
        }
        if (UtilComparador.compareTo(this.getDatosPersonalesLegitimacion(), dataOriginal.getDatosPersonalesLegitimacion()) != 0) {
            return UtilComparador.compareTo(this.getDatosPersonalesLegitimacion(), dataOriginal.getDatosPersonalesLegitimacion());
        }
        if (UtilComparador.compareTo(this.getIniciacion(), dataOriginal.getIniciacion()) != 0) {
            return UtilComparador.compareTo(this.getIniciacion(), dataOriginal.getIniciacion());
        }
        if (UtilComparador.compareTo(this.getUaResponsable(), dataOriginal.getUaResponsable()) != 0) {
            return UtilComparador.compareTo(this.getUaResponsable(), dataOriginal.getUaResponsable());
        }
        if (UtilComparador.compareTo(this.getUaInstructor(), dataOriginal.getUaInstructor()) != 0) {
            return UtilComparador.compareTo(this.getUaInstructor(), dataOriginal.getUaInstructor());
        }
        if (UtilComparador.compareTo(this.getTipoProcedimiento(), dataOriginal.getTipoProcedimiento()) != 0) {
            return UtilComparador.compareTo(this.getTipoProcedimiento(), dataOriginal.getTipoProcedimiento());
        }
        if (UtilComparador.compareTo(this.getTipoVia(), dataOriginal.getTipoVia()) != 0) {
            return UtilComparador.compareTo(this.getTipoVia(), dataOriginal.getTipoVia());
        }

        //Literal
        if (UtilComparador.compareTo(this.getNombreProcedimientoWorkFlow(), dataOriginal.getNombreProcedimientoWorkFlow()) != 0) {
            return UtilComparador.compareTo(this.getNombreProcedimientoWorkFlow(), dataOriginal.getNombreProcedimientoWorkFlow());
        }
        if (UtilComparador.compareTo(this.getLopdFinalidad(), dataOriginal.getLopdFinalidad()) != 0) {
            return UtilComparador.compareTo(this.getLopdFinalidad(), dataOriginal.getLopdFinalidad());
        }
        if (UtilComparador.compareTo(this.getLopdInfoAdicional(), dataOriginal.getLopdInfoAdicional()) != 0) {
            return UtilComparador.compareTo(this.getLopdInfoAdicional(), dataOriginal.getLopdInfoAdicional());
        }
        if (UtilComparador.compareTo(this.getObjeto(), dataOriginal.getObjeto()) != 0) {
            return UtilComparador.compareTo(this.getObjeto(), dataOriginal.getObjeto());
        }
        if (UtilComparador.compareTo(this.getDestinatarios(), dataOriginal.getDestinatarios()) != 0) {
            return UtilComparador.compareTo(this.getDestinatarios(), dataOriginal.getDestinatarios());
        }
        if (UtilComparador.compareTo(this.getTerminoResolucion(), dataOriginal.getTerminoResolucion()) != 0) {
            return UtilComparador.compareTo(this.getTerminoResolucion(), dataOriginal.getTerminoResolucion());
        }
        if (UtilComparador.compareTo(this.getObservaciones(), dataOriginal.getObservaciones()) != 0) {
            return UtilComparador.compareTo(this.getObservaciones(), dataOriginal.getObservaciones());
        }
        if (UtilComparador.compareTo(this.getRequisitos(), dataOriginal.getRequisitos()) != 0) {
            return UtilComparador.compareTo(this.getRequisitos(), dataOriginal.getRequisitos());
        }
        if (TipoPublicoObjetivoEntidadGridDTO.compareTo(this.getPublicosObjetivo(), dataOriginal.getPublicosObjetivo()) != 0) {
            return TipoPublicoObjetivoEntidadGridDTO.compareTo(this.getPublicosObjetivo(), dataOriginal.getPublicosObjetivo());
        }
        if (ProcedimientoDocumentoDTO.compareTo(this.getDocumentos(), dataOriginal.getDocumentos()) != 0) {
            return ProcedimientoDocumentoDTO.compareTo(this.getDocumentos(), dataOriginal.getDocumentos());
        }
        if (ProcedimientoDocumentoDTO.compareTo(this.getDocumentosLOPD(), dataOriginal.getDocumentosLOPD()) != 0) {
            return ProcedimientoDocumentoDTO.compareTo(this.getDocumentosLOPD(), dataOriginal.getDocumentosLOPD());
        }
        if (NormativaGridDTO.compareTo(this.getNormativas(), dataOriginal.getNormativas()) != 0) {
            return NormativaGridDTO.compareTo(this.getNormativas(), dataOriginal.getNormativas());
        }
        /*if (ProcedimientoTramiteDTO.compareTo(this.getTramites(), dataOriginal.getTramites()) != 0) {
            return ProcedimientoTramiteDTO.compareTo(this.getTramites(), dataOriginal.getTramites());
        }*/

        return 0;
    }

    public boolean esVisible() {
        final GregorianCalendar dataActual = new GregorianCalendar();
        Boolean visible;

        final Boolean esPublic = this.getWorkflow() == TypeProcedimientoWorkflow.DEFINITIVO && this.getEstado() == TypeProcedimientoEstado.PUBLICADO;
        final Boolean noCaducat = (this.getFechaCaducidad() != null && this.getFechaCaducidad().after(dataActual.getTime())) || this.getFechaCaducidad() == null;
        final Boolean esPublicat = (this.getFechaPublicacion() != null && this.getFechaPublicacion().before(dataActual.getTime())) || this.getFechaPublicacion() == null;

        if (esPublic && noCaducat && esPublicat) {
            visible = Boolean.TRUE;
        } else {
            visible = Boolean.FALSE;
        }
        return visible;
    }

    /**
     * Icono de visibilidad
     *
     * @return
     */
    public String getIcon() {
        if (this.esVisible()) {
            return "pi pi-eye iconoVerde";
        } else {
            return "pi pi-eye-slash iconoRojo";
        }

    }
}
