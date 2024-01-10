package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import es.caib.rolsac2.service.utils.AuditoriaUtil;
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
@Schema(name = "ProcedimientoDTO")
public class ProcedimientoDTO extends ProcedimientoBaseDTO implements Cloneable {

    private List<ProcedimientoTramiteDTO> tramites;

    public static ProcedimientoDTO createInstance(List<String> idiomas) {
        ProcedimientoDTO proc = new ProcedimientoDTO();
        proc.setWorkflow(TypeProcedimientoWorkflow.MODIFICACION);
        proc.setEstado(TypeProcedimientoEstado.MODIFICACION);
        proc.setNombreProcedimientoWorkFlow(Literal.createInstance(idiomas));
        proc.setLopdFinalidad(Literal.createInstance(idiomas));
        proc.setLopdInfoAdicional(Literal.createInstance(idiomas));
        proc.setLopdDerechos(Literal.createInstance(idiomas));
        proc.setRequisitos(Literal.createInstance(idiomas));
        proc.setObjeto(Literal.createInstance(idiomas));
        proc.setDestinatarios(Literal.createInstance(idiomas));
        proc.setTerminoResolucion(Literal.createInstance(idiomas));
        proc.setObservaciones(Literal.createInstance(idiomas));
        proc.setComun(0);
        return proc;
    }

    public List<ProcedimientoTramiteDTO> getTramites() {
        return tramites;
    }

    public void setTramites(List<ProcedimientoTramiteDTO> tramites) {
        this.tramites = tramites;
    }

    public void addtramite(ProcedimientoTramiteDTO procTramite) {
        boolean encontrado = false;
        for (int i = 0; i < this.getTramites().size(); i++) {
            ProcedimientoTramiteDTO tramite = this.getTramites().get(i);
            if (procTramite.getCodigo() == null && tramite.getCodigo() == null && procTramite.getCodigoString() != null && procTramite.getCodigoString().equals(tramite.getCodigoString())) {
                encontrado = true;
                this.getTramites().set(i, procTramite);
                break;
            } else if (procTramite.getCodigo() != null && tramite.getCodigo() != null && procTramite.getCodigo().compareTo(tramite.getCodigo()) == 0) {
                encontrado = true;
                this.getTramites().set(i, procTramite);
                break;
            }
        }

        if (!encontrado) {
            this.getTramites().add(procTramite);
        }

    }

    @Override
    public String toString() {
        return "ProcedimientoDTO{" + " codigo=" + getCodigo() + ", codigoWF='" + getCodigoWF() + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimientoDTO that = (ProcedimientoDTO) o;
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
        ProcedimientoDTO procClonado = new ProcedimientoDTO();
        procClonado.setCodigo(this.getCodigo());
        procClonado.setCodigoWF(this.getCodigoWF());
        procClonado.setWorkflow(this.getWorkflow());
        procClonado.setEstado(this.getEstado());
        procClonado.setTieneTasa(this.isTieneTasa());
        procClonado.setCodigoSIA(this.getCodigoSIA());
        procClonado.setEstadoSIA(this.getEstadoSIA());
        procClonado.setTipo(this.getTipo());
        procClonado.setPublicado(this.isPublicado());
        procClonado.setFechaCaducidad(this.getFechaCaducidad());
        procClonado.setFechaPublicacion(this.getFechaPublicacion());
        procClonado.setFechaActualizacion(this.getFechaActualizacion());

        procClonado.setResponsable(this.getResponsable());
        procClonado.setFechaSIA(this.getFechaSIA());
        procClonado.setComun(this.getComun());
        procClonado.setLopdResponsable(this.getLopdResponsable());
        procClonado.setInterno(this.isInterno());
        procClonado.setMensajes(this.getMensajes());
        procClonado.setHabilitadoApoderado(this.isHabilitadoApoderado());
        procClonado.setHabilitadoFuncionario(this.getHabilitadoFuncionario());
        procClonado.setUsuarioAuditoria(this.getUsuarioAuditoria());
        procClonado.setResponsable(this.getResponsable());
        procClonado.setResponsableEmail(this.getResponsableEmail());
        procClonado.setHabilitadoApoderado(this.isHabilitadoApoderado());
        procClonado.setHabilitadoFuncionario(this.getHabilitadoFuncionario());

        //Tipos
        if (this.getSilencio() != null) {
            procClonado.setSilencio((TipoSilencioAdministrativoDTO) this.getSilencio().clone());
        }
        if (this.getDatosPersonalesLegitimacion() != null) {
            procClonado.setDatosPersonalesLegitimacion((TipoLegitimacionDTO) this.getDatosPersonalesLegitimacion().clone());
        }
        if (this.getIniciacion() != null) {
            procClonado.setIniciacion((TipoFormaInicioDTO) this.getIniciacion().clone());
        }
        if (this.getUaResponsable() != null) {
            procClonado.setUaResponsable((UnidadAdministrativaDTO) this.getUaResponsable().clone());
        }
        if (this.getUaInstructor() != null) {
            procClonado.setUaInstructor((UnidadAdministrativaDTO) this.getUaInstructor().clone());
        }
        if (this.getTipoProcedimiento() != null) {
            procClonado.setTipoProcedimiento((TipoProcedimientoDTO) this.getTipoProcedimiento().clone());
        }
        if (this.getTipoVia() != null) {
            procClonado.setTipoVia((TipoViaDTO) this.getTipoVia().clone());
        }

        //Literal
        if (this.getNombreProcedimientoWorkFlow() != null) {
            procClonado.setNombreProcedimientoWorkFlow((Literal) this.getNombreProcedimientoWorkFlow().clone());
        }
        if (this.getLopdFinalidad() != null) {
            procClonado.setLopdFinalidad((Literal) this.getLopdFinalidad().clone());
        }
        if (this.getLopdInfoAdicional() != null) {
            procClonado.setLopdInfoAdicional((Literal) this.getLopdInfoAdicional().clone());
        }
        if (this.getObjeto() != null) {
            procClonado.setObjeto((Literal) this.getObjeto().clone());
        }
        if (this.getDestinatarios() != null) {
            procClonado.setDestinatarios((Literal) this.getDestinatarios().clone());
        }
        if (this.getTerminoResolucion() != null) {
            procClonado.setTerminoResolucion((Literal) this.getTerminoResolucion().clone());
        }
        if (this.getObservaciones() != null) {
            procClonado.setObservaciones((Literal) this.getObservaciones().clone());
        }
        if (this.getRequisitos() != null) {
            procClonado.setRequisitos((Literal) this.getRequisitos().clone());
        }

        //Relaciones
        if (getPublicosObjetivo() != null) {
            List<TipoPublicoObjetivoEntidadGridDTO> publicos = new ArrayList<>();
            for (TipoPublicoObjetivoEntidadGridDTO publico : getPublicosObjetivo()) {
                publicos.add((TipoPublicoObjetivoEntidadGridDTO) publico.clone());
            }
            procClonado.setPublicosObjetivo(publicos);

        }
        if (getDocumentos() != null) {
            List<ProcedimientoDocumentoDTO> docs = new ArrayList<>();
            for (ProcedimientoDocumentoDTO documento : getDocumentos()) {
                docs.add((ProcedimientoDocumentoDTO) documento.clone());
            }
            procClonado.setDocumentos(docs);

        }
        if (getDocumentosLOPD() != null) {
            List<ProcedimientoDocumentoDTO> docs = new ArrayList<>();
            for (ProcedimientoDocumentoDTO documento : getDocumentosLOPD()) {
                docs.add((ProcedimientoDocumentoDTO) documento.clone());
            }
            procClonado.setDocumentosLOPD(docs);

        }
        if (getNormativas() != null) {
            List<NormativaGridDTO> norms = new ArrayList<>();
            for (NormativaGridDTO normativa : getNormativas()) {
                norms.add((NormativaGridDTO) normativa.clone());
            }
            procClonado.setNormativas(norms);

        }
        if (getTemas() != null) {
            List<TemaGridDTO> temas = new ArrayList<>();
            for (TemaGridDTO tema : getTemas()) {
                temas.add((TemaGridDTO) tema.clone());
            }
            procClonado.setTemas(temas);
        }

        //Exclusivo Procedimiento DTO
        if (tramites != null) {
            List<ProcedimientoTramiteDTO> tramits = new ArrayList<>();
            for (ProcedimientoTramiteDTO tramite : tramites) {
                tramits.add((ProcedimientoTramiteDTO) tramite.clone());
            }
            procClonado.setTramites(tramits);

        }
        return procClonado;
    }


    public int compareTo(ProcedimientoDTO dataOriginal) {

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
        if (UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable()) != 0) {
            return UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable());
        }
        if (UtilComparador.compareTo(this.getResponsableEmail(), dataOriginal.getResponsableEmail()) != 0) {
            return UtilComparador.compareTo(this.getResponsableEmail(), dataOriginal.getResponsableEmail());
        }
        if (UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario()) != 0) {
            return UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario());
        }
        if (UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado()) != 0) {
            return UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado());
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
        if (ProcedimientoTramiteDTO.compareTo(this.getTramites(), dataOriginal.getTramites()) != 0) {
            return ProcedimientoTramiteDTO.compareTo(this.getTramites(), dataOriginal.getTramites());
        }
        if (TemaGridDTO.compareTo(this.getTemas(), dataOriginal.getTemas()) != 0) {
            return TemaGridDTO.compareTo(this.getTemas(), dataOriginal.getTemas());
        }

        return 0;
    }


    public static List<AuditoriaCambio> auditar(ProcedimientoBaseDTO data, ProcedimientoBaseDTO dataOriginal) {

        List<AuditoriaCambio> cambios = new ArrayList<>();
        if (dataOriginal == null) {
            return cambios;
        }

        //Estado
        AuditoriaUtil.auditar(data.getEstado(), dataOriginal.getEstado(), cambios);

        //Seccion Datos
        AuditoriaUtil.auditar(data.getFechaPublicacion(), dataOriginal.getFechaPublicacion(), cambios, "auditoria.procedimiento.fechaPublicacion");
        AuditoriaUtil.auditar(data.getFechaCaducidad(), dataOriginal.getFechaCaducidad(), cambios, "auditoria.procedimiento.fechaCaducidad");
        AuditoriaUtil.auditar(data.isTieneTasa(), dataOriginal.isTieneTasa(), cambios, "auditoria.procedimiento.tieneTasa");
        AuditoriaUtil.auditar(data.getNombreProcedimientoWorkFlow(), dataOriginal.getNombreProcedimientoWorkFlow(), cambios, "auditoria.procedimiento.nombre");
        AuditoriaUtil.auditar(data.getObjeto(), dataOriginal.getObjeto(), cambios, "auditoria.procedimiento.objeto");
        AuditoriaUtil.auditar(data.getDestinatarios(), dataOriginal.getDestinatarios(), cambios, "auditoria.procedimiento.destinatarios");
        AuditoriaUtil.auditar(data.getRequisitos(), dataOriginal.getRequisitos(), cambios, "auditoria.procedimiento.requisitos");
        AuditoriaUtil.auditar(data.getLopdInfoAdicional(), dataOriginal.getLopdInfoAdicional(), cambios, "auditoria.procedimiento.datosPersonalesDestinatario");
        AuditoriaUtil.auditar(data.getIniciacion(), dataOriginal.getIniciacion(), cambios, "auditoria.procedimiento.iniciacion");
        AuditoriaUtil.auditar(data.getSilencio(), dataOriginal.getSilencio(), cambios, "auditoria.procedimiento.silencio");
        AuditoriaUtil.auditar(data.getTipoProcedimiento(), dataOriginal.getTipoProcedimiento(), cambios, "auditoria.procedimiento.tipoProcedimiento");
        AuditoriaUtil.auditar(data.getTipoVia(), dataOriginal.getTipoVia(), cambios, "auditoria.procedimiento.tipoVia");
        AuditoriaUtil.auditar(data.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado(), cambios, "auditoria.procedimiento.habilitadoApoderado");
        AuditoriaUtil.auditar(data.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario(), cambios, "auditoria.procedimiento.habilitadoFuncionario");
        AuditoriaUtil.auditar(data.getUaInstructor(), dataOriginal.getUaInstructor(), cambios, "auditoria.procedimiento.uaInstructor");
        AuditoriaUtil.auditar(data.getComun(), dataOriginal.getComun(), cambios, "auditoria.procedimiento.comun");
        AuditoriaUtil.auditar(data.getTerminoResolucion(), dataOriginal.getTerminoResolucion(), cambios, "auditoria.procedimiento.terminoResolucion");
        AuditoriaUtil.auditar(data.getObservaciones(), dataOriginal.getObservaciones(), cambios, "auditoria.procedimiento.observaciones");

        //Seccion datos contacto
        AuditoriaUtil.auditar(data.getUaResponsable(), dataOriginal.getUaResponsable(), cambios, "auditoria.procedimiento.uaResponsable");
        AuditoriaUtil.auditar(data.getResponsable(), dataOriginal.getResponsable(), cambios, "auditoria.procedimiento.responsable");
        AuditoriaUtil.auditar(data.getResponsableEmail(), dataOriginal.getResponsableEmail(), cambios, "auditoria.procedimiento.responsableEmail");

        //Seccion datos LOPD
        AuditoriaUtil.auditar(data.isInterno(), dataOriginal.isInterno(), cambios, "auditoria.procedimiento.interno");
        AuditoriaUtil.auditar(data.getLopdResponsable(), dataOriginal.getLopdResponsable(), cambios, "auditoria.procedimiento.lopdResponsable");
        AuditoriaUtil.auditar(data.getDatosPersonalesLegitimacion(), dataOriginal.getDatosPersonalesLegitimacion(), cambios, "auditoria.procedimiento.datosPersonalesLegitimacion");
        AuditoriaUtil.auditar(data.getLopdFinalidad(), dataOriginal.getLopdFinalidad(), cambios, "auditoria.procedimiento.datosPersonalesFinalidad");

        if (data instanceof ServicioDTO) {
            AuditoriaUtil.auditar(((ServicioDTO) data).getTasa(), ((ServicioDTO) dataOriginal).getTasa(), cambios, "auditoria.servicio.tasa");
            AuditoriaUtil.auditar(((ServicioDTO) data).getTipoTramitacion(), ((ServicioDTO) dataOriginal).getTipoTramitacion(), cambios, "auditoria.servicio.tipoTramitacion");
            AuditoriaUtil.auditar(((ServicioDTO) data).getPlantillaSel(), ((ServicioDTO) dataOriginal).getPlantillaSel(), cambios, "auditoria.servicio.tipoTramitacionSel");
            AuditoriaUtil.auditar(((ServicioDTO) data).isTramitPresencial(), ((ServicioDTO) dataOriginal).isTramitPresencial(), cambios, "auditoria.servicio.isTramitPresencial");
            AuditoriaUtil.auditar(((ServicioDTO) data).isTramitElectronica(), ((ServicioDTO) dataOriginal).isTramitElectronica(), cambios, "auditoria.servicio.isTramitElectronica");
            AuditoriaUtil.auditar(((ServicioDTO) data).isTramitTelefonica(), ((ServicioDTO) dataOriginal).isTramitTelefonica(), cambios, "auditoria.servicio.isTramitTelefonica");
        }

        //Relaciones
        AuditoriaUtil.auditarTipoPublico(data.getPublicosObjetivo(), dataOriginal.getPublicosObjetivo(), cambios, "auditoria.procedimiento.publicosObjetivo");
        AuditoriaUtil.auditarDocumentos(data.getDocumentos(), dataOriginal.getDocumentos(), cambios, "auditoria.procedimiento.documentos");
        AuditoriaUtil.auditarDocumentos(data.getDocumentosLOPD(), dataOriginal.getDocumentosLOPD(), cambios, "auditoria.procedimiento.documentosLOPD");
        AuditoriaUtil.auditarNormativas(data.getNormativas(), dataOriginal.getNormativas(), cambios, "auditoria.procedimiento.normativas");
        AuditoriaUtil.auditarTemas(data.getTemas(), dataOriginal.getTemas(), cambios, "auditoria.procedimiento.temas");

        if (data instanceof ProcedimientoDTO) {
            AuditoriaUtil.auditarTramites(((ProcedimientoDTO) data).getTramites(), ((ProcedimientoDTO) dataOriginal).getTramites(), cambios, "auditoria.procedimiento.tramites");
        }


        return cambios;
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
