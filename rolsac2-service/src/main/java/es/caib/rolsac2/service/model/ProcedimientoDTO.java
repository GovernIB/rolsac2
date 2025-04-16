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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoDTO")
public class ProcedimientoDTO extends ProcedimientoBaseDTO implements Cloneable {

	private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoDTO.class);

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
        proc.setKeywords(Literal.createInstance(idiomas));
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
        procClonado.setIncidenciasEmail(this.getIncidenciasEmail());
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
        LOG.error("Clone. UACompetente" + this.getUaCompetente());
        if (this.getUaCompetente() != null) {
            procClonado.setUaCompetente((UnidadAdministrativaDTO) this.getUaCompetente().clone());
            LOG.error("Clonado. UACompetente" + procClonado.getUaCompetente());
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
        return compareTo(dataOriginal, false);
    }

    public int compareTo(ProcedimientoDTO dataOriginal, boolean mostrarLog) {

        if (dataOriginal == null) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: dataOriginal es null");
            }
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), dataOriginal.getCodigo()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getCodigo() != dataOriginal.getCodigo(). this.getCodigo()=" + this.getCodigo() + " dataOriginal.getCodigo()=" + dataOriginal.getCodigo());
            }
            return UtilComparador.compareTo(this.getCodigo(), dataOriginal.getCodigo());
        }

        if (UtilComparador.compareTo(this.getCodigoSIA(), dataOriginal.getCodigoSIA()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getCodigoSIA() != dataOriginal.getCodigoSIA(). this.getCodigoSIA()=" + this.getCodigoSIA() + " dataOriginal.getCodigoSIA()=" + dataOriginal.getCodigoSIA());
            }
            return UtilComparador.compareTo(this.getCodigoSIA(), dataOriginal.getCodigoSIA());
        }

        if (UtilComparador.compareTo(this.getWorkflow(), dataOriginal.getWorkflow()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getWorkflow() != dataOriginal.getWorkflow(). this.getWorkflow()=" + this.getWorkflow() + " dataOriginal.getWorkflow()=" + dataOriginal.getWorkflow());
            }

            return UtilComparador.compareTo(this.getWorkflow(), dataOriginal.getWorkflow());
        }

        if (UtilComparador.compareTo(this.getEstado(), dataOriginal.getEstado()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getEstado() != dataOriginal.getEstado(). this.getEstado()=" + this.getEstado() + " dataOriginal.getEstado()=" + dataOriginal.getEstado());
            }
            return UtilComparador.compareTo(this.getEstado(), dataOriginal.getEstado());
        }
        if (UtilComparador.compareTo(this.getEstadoSIA(), dataOriginal.getEstadoSIA()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getEstadoSIA() != dataOriginal.getEstadoSIA(). this.getEstadoSIA()=" + this.getEstadoSIA() + " dataOriginal.getEstadoSIA()=" + dataOriginal.getEstadoSIA());
            }
            return UtilComparador.compareTo(this.getEstadoSIA(), dataOriginal.getEstadoSIA());
        }
        if (UtilComparador.compareTo(this.getFechaSIA(), dataOriginal.getFechaSIA()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getFechaSIA() != dataOriginal.getFechaSIA(). this.getFechaSIA()=" + this.getFechaSIA() + " dataOriginal.getFechaSIA()=" + dataOriginal.getFechaSIA());
            }
            return UtilComparador.compareTo(this.getFechaSIA(), dataOriginal.getFechaSIA());
        }
        if (UtilComparador.compareTo(this.getTipo(), dataOriginal.getTipo()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getTipo() != dataOriginal.getTipo(). this.getTipo()=" + this.getTipo() + " dataOriginal.getTipo()=" + dataOriginal.getTipo());
            }
            return UtilComparador.compareTo(this.getTipo(), dataOriginal.getTipo());
        }
        if (UtilComparador.compareTo(this.isPublicado(), dataOriginal.isPublicado()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.isPublicado() != dataOriginal.isPublicado(). this.isPublicado()=" + this.isPublicado() + " dataOriginal.isPublicado()=" + dataOriginal.isPublicado());
            }
            return UtilComparador.compareTo(this.isPublicado(), dataOriginal.isPublicado());
        }
        if (UtilComparador.compareTo(this.getFechaCaducidad(), dataOriginal.getFechaCaducidad()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getFechaCaducidad() != dataOriginal.getFechaCaducidad(). this.getFechaCaducidad()=" + this.getFechaCaducidad() + " dataOriginal.getFechaCaducidad()=" + dataOriginal.getFechaCaducidad());
            }
            return UtilComparador.compareTo(this.getFechaCaducidad(), dataOriginal.getFechaCaducidad());
        }
        if (UtilComparador.compareTo(this.getFechaPublicacion(), dataOriginal.getFechaPublicacion()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getFechaPublicacion() != dataOriginal.getFechaPublicacion(). this.getFechaPublicacion()=" + this.getFechaPublicacion() + " dataOriginal.getFechaPublicacion()=" + dataOriginal.getFechaPublicacion());
            }
            return UtilComparador.compareTo(this.getFechaPublicacion(), dataOriginal.getFechaPublicacion());
        }

        if (UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getResponsable() != dataOriginal.getResponsable(). this.getResponsable()=" + this.getResponsable() + " dataOriginal.getResponsable()=" + dataOriginal.getResponsable());
            }
            return UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable());
        }
        if (UtilComparador.compareTo(this.getComun(), dataOriginal.getComun()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getComun() != dataOriginal.getComun(). this.getComun()=" + this.getComun() + " dataOriginal.getComun()=" + dataOriginal.getComun());
            }
            return UtilComparador.compareTo(this.getComun(), dataOriginal.getComun());
        }

        if (UtilComparador.compareTo(this.isInterno(), dataOriginal.isInterno()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.isInterno() != dataOriginal.isInterno(). this.isInterno()=" + this.isInterno() + " dataOriginal.isInterno()=" + dataOriginal.isInterno());
            }
            return UtilComparador.compareTo(this.isInterno(), dataOriginal.isInterno());
        }

        if (UtilComparador.compareTo(this.getTerminoResolucion(), dataOriginal.getTerminoResolucion()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getTerminoResolucion() != dataOriginal.getTerminoResolucion(). this.getTerminoResolucion()=" + this.getTerminoResolucion() + " dataOriginal.getTerminoResolucion()=" + dataOriginal.getTerminoResolucion());
            }
            return UtilComparador.compareTo(this.getTerminoResolucion(), dataOriginal.getTerminoResolucion());
        }
        if (UtilComparador.compareTo(this.getSilencio(), dataOriginal.getSilencio()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getSilencio() != dataOriginal.getSilencio(). this.getSilencio()=" + this.getSilencio() + " dataOriginal.getSilencio()=" + dataOriginal.getSilencio());
            }
            return UtilComparador.compareTo(this.getSilencio(), dataOriginal.getSilencio());
        }
        if (UtilComparador.compareTo(this.getTipoVia(), dataOriginal.getTipoVia()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getTipoVia() != dataOriginal.getTipoVia(). this.getTipoVia()=" + this.getTipoVia() + " dataOriginal.getTipoVia()=" + dataOriginal.getTipoVia());
            }
            return UtilComparador.compareTo(this.getTipoVia(), dataOriginal.getTipoVia());
        }
        if (UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.isHabilitadoApoderado() != dataOriginal.isHabilitadoApoderado(). this.isHabilitadoApoderado()=" + this.isHabilitadoApoderado() + " dataOriginal.isHabilitadoApoderado()=" + dataOriginal.isHabilitadoApoderado());
            }
            return UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado());
        }
        if (UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getHabilitadoFuncionario() != dataOriginal.getHabilitadoFuncionario(). this.getHabilitadoFuncionario()=" + this.getHabilitadoFuncionario() + " dataOriginal.getHabilitadoFuncionario()=" + dataOriginal.getHabilitadoFuncionario());
            }
            return UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario());
        }
        if (UtilComparador.compareTo(this.getIniciacion(), dataOriginal.getIniciacion()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getIniciacion() != dataOriginal.getIniciacion(). this.getIniciacion()=" + this.getIniciacion() + " dataOriginal.getIniciacion()=" + dataOriginal.getIniciacion());
            }
            return UtilComparador.compareTo(this.getIniciacion(), dataOriginal.getIniciacion());
        }

        // Organos
        if (UtilComparador.compareTo(this.getUaResponsable(), dataOriginal.getUaResponsable()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getUaResponsable() != dataOriginal.getUaResponsable(). this.getUaResponsable()=" + this.getUaResponsable() + " dataOriginal.getUaResponsable()=" + dataOriginal.getUaResponsable());
            }
            return UtilComparador.compareTo(this.getUaResponsable(), dataOriginal.getUaResponsable());
        }
        if (UtilComparador.compareTo(this.getUaInstructor(), dataOriginal.getUaInstructor()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getUaInstructor() != dataOriginal.getUaInstructor(). this.getUaInstructor()=" + this.getUaInstructor() + " dataOriginal.getUaInstructor()=" + dataOriginal.getUaInstructor());
            }
            return UtilComparador.compareTo(this.getUaInstructor(), dataOriginal.getUaInstructor());
        }
        if (UtilComparador.compareTo(this.getTipoProcedimiento(), dataOriginal.getTipoProcedimiento()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getTipoProcedimiento() != dataOriginal.getTipoProcedimiento(). this.getTipoProcedimiento()=" + this.getTipoProcedimiento() + " dataOriginal.getTipoProcedimiento()=" + dataOriginal.getTipoProcedimiento());
            }
            return UtilComparador.compareTo(this.getTipoProcedimiento(), dataOriginal.getTipoProcedimiento());
        }


        // Datos contacto

        if (UtilComparador.compareTo(this.getUaCompetente(), dataOriginal.getUaCompetente()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getUaCompetente() != dataOriginal.getUaCompetente(). this.getUaCompetente()=" + this.getUaCompetente() + " dataOriginal.getUaCompetente()=" + dataOriginal.getUaCompetente());
            }
            return UtilComparador.compareTo(this.getUaCompetente(), dataOriginal.getUaCompetente());
        }

        if (UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getResponsable() != dataOriginal.getResponsable(). this.getResponsable()=" + this.getResponsable() + " dataOriginal.getResponsable()=" + dataOriginal.getResponsable());
            }
            return UtilComparador.compareTo(this.getResponsable(), dataOriginal.getResponsable());
        }
        if (UtilComparador.compareTo(this.getResponsableEmail(), dataOriginal.getResponsableEmail()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getResponsableEmail() != dataOriginal.getResponsableEmail(). this.getResponsableEmail()=" + this.getResponsableEmail() + " dataOriginal.getResponsableEmail()=" + dataOriginal.getResponsableEmail());
            }
            return UtilComparador.compareTo(this.getResponsableEmail(), dataOriginal.getResponsableEmail());
        }

        if (UtilComparador.compareTo(this.getResponsableTelefono(), dataOriginal.getResponsableTelefono()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getResponsableTelefono() != dataOriginal.getResponsableTelefono(). this.getResponsableTelefono()=" + this.getResponsableTelefono() + " dataOriginal.getResponsableTelefono()=" + dataOriginal.getResponsableTelefono());
            }
            return UtilComparador.compareTo(this.getResponsableTelefono(), dataOriginal.getResponsableTelefono());
        }

        if (UtilComparador.compareTo(this.getIncidenciasEmail(), dataOriginal.getIncidenciasEmail()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getIncidenciasEmail() != dataOriginal.getIncidenciasEmail(). this.getIncidenciasEmail()=" + this.getIncidenciasEmail() + " dataOriginal.getIncidenciasEmail()=" + dataOriginal.getIncidenciasEmail());
            }
            return UtilComparador.compareTo(this.getIncidenciasEmail(), dataOriginal.getIncidenciasEmail());
        }


        if (UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getHabilitadoFuncionario() != dataOriginal.getHabilitadoFuncionario(). this.getHabilitadoFuncionario()=" + this.getHabilitadoFuncionario() + " dataOriginal.getHabilitadoFuncionario()=" + dataOriginal.getHabilitadoFuncionario());
            }
            return UtilComparador.compareTo(this.getHabilitadoFuncionario(), dataOriginal.getHabilitadoFuncionario());
        }
        if (UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.isHabilitadoApoderado() != dataOriginal.isHabilitadoApoderado(). this.isHabilitadoApoderado()=" + this.isHabilitadoApoderado() + " dataOriginal.isHabilitadoApoderado()=" + dataOriginal.isHabilitadoApoderado());
            }
            return UtilComparador.compareTo(this.isHabilitadoApoderado(), dataOriginal.isHabilitadoApoderado());
        }


        //Literal
        if (UtilComparador.compareTo(this.getNombreProcedimientoWorkFlow(), dataOriginal.getNombreProcedimientoWorkFlow()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getNombreProcedimientoWorkFlow() != dataOriginal.getNombreProcedimientoWorkFlow(). this.getNombreProcedimientoWorkFlow()=" + this.getNombreProcedimientoWorkFlow() + " dataOriginal.getNombreProcedimientoWorkFlow()=" + dataOriginal.getNombreProcedimientoWorkFlow());
            }
            return UtilComparador.compareTo(this.getNombreProcedimientoWorkFlow(), dataOriginal.getNombreProcedimientoWorkFlow());
        }
        if (UtilComparador.compareTo(this.getObjeto(), dataOriginal.getObjeto()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getObjeto() != dataOriginal.getObjeto(). this.getObjeto()=" + this.getObjeto() + " dataOriginal.getObjeto()=" + dataOriginal.getObjeto());
            }
            return UtilComparador.compareTo(this.getObjeto(), dataOriginal.getObjeto());
        }
        if (UtilComparador.compareTo(this.getDestinatarios(), dataOriginal.getDestinatarios()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getDestinatarios() != dataOriginal.getDestinatarios(). this.getDestinatarios()=" + this.getDestinatarios() + " dataOriginal.getDestinatarios()=" + dataOriginal.getDestinatarios());
            }
            return UtilComparador.compareTo(this.getDestinatarios(), dataOriginal.getDestinatarios());
        }

        // LOPD
        if (UtilComparador.compareTo(this.getLopdResponsable(), dataOriginal.getLopdResponsable()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getLopdResponsable() != dataOriginal.getLopdResponsable(). this.getLopdResponsable()=" + this.getLopdResponsable() + " dataOriginal.getLopdResponsable()=" + dataOriginal.getLopdResponsable());
            }
            return UtilComparador.compareTo(this.getLopdResponsable(), dataOriginal.getLopdResponsable());
        }
        if (UtilComparador.compareTo(this.getDatosPersonalesLegitimacion(), dataOriginal.getDatosPersonalesLegitimacion()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getDatosPersonalesLegitimacion() != dataOriginal.getDatosPersonalesLegitimacion(). this.getDatosPersonalesLegitimacion()=" + this.getDatosPersonalesLegitimacion() + " dataOriginal.getDatosPersonalesLegitimacion()=" + dataOriginal.getDatosPersonalesLegitimacion());
            }
            return UtilComparador.compareTo(this.getDatosPersonalesLegitimacion(), dataOriginal.getDatosPersonalesLegitimacion());
        }
        if (UtilComparador.compareTo(this.getLopdFinalidad(), dataOriginal.getLopdFinalidad()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getLopdFinalidad() != dataOriginal.getLopdFinalidad(). this.getLopdFinalidad()=" + this.getLopdFinalidad() + " dataOriginal.getLopdFinalidad()=" + dataOriginal.getLopdFinalidad());
            }
            return UtilComparador.compareTo(this.getLopdFinalidad(), dataOriginal.getLopdFinalidad());
        }

        if (UtilComparador.compareTo(this.getLopdDestinatario(), dataOriginal.getLopdDestinatario()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getLopdDestinatario() != dataOriginal.getLopdDestinatario(). this.getLopdDestinatario()=" + this.getLopdDestinatario() + " dataOriginal.getLopdDestinatario()=" + dataOriginal.getLopdDestinatario());
            }
            return UtilComparador.compareTo(this.getLopdDestinatario(), dataOriginal.getLopdDestinatario());
        }

        if (UtilComparador.compareTo(this.getLopdDerechos(), dataOriginal.getLopdDerechos()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getLopdDerechos() != dataOriginal.getLopdDerechos(). this.getLopdDerechos()=" + this.getLopdDerechos() + " dataOriginal.getLopdDerechos()=" + dataOriginal.getLopdDerechos());
            }
            return UtilComparador.compareTo(this.getLopdDerechos(), dataOriginal.getLopdDerechos());
        }


        if (UtilComparador.compareTo(this.getLopdInfoAdicional(), dataOriginal.getLopdInfoAdicional()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getLopdInfoAdicional() != dataOriginal.getLopdInfoAdicional(). this.getLopdInfoAdicional()=" + this.getLopdInfoAdicional() + " dataOriginal.getLopdInfoAdicional()=" + dataOriginal.getLopdInfoAdicional());
            }
            return UtilComparador.compareTo(this.getLopdInfoAdicional(), dataOriginal.getLopdInfoAdicional());
        }


        if (UtilComparador.compareTo(this.isTieneTasa(), dataOriginal.isTieneTasa()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.isTieneTasa() != dataOriginal.isTieneTasa(). this.isTieneTasa()=" + this.isTieneTasa() + " dataOriginal.isTieneTasa()=" + dataOriginal.isTieneTasa());
            }
            return UtilComparador.compareTo(this.isTieneTasa(), dataOriginal.isTieneTasa());
        }

        if (UtilComparador.compareTo(this.getObservaciones(), dataOriginal.getObservaciones()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getObservaciones() != dataOriginal.getObservaciones(). this.getObservaciones()=" + this.getObservaciones() + " dataOriginal.getObservaciones()=" + dataOriginal.getObservaciones());
            }
            return UtilComparador.compareTo(this.getObservaciones(), dataOriginal.getObservaciones());
        }

        if (UtilComparador.compareTo(this.getKeywords(), dataOriginal.getKeywords()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getKeywords() != dataOriginal.getKeywords(). this.getKeywords()=" + this.getKeywords() + " dataOriginal.getKeywords()=" + dataOriginal.getKeywords());
            }
            return UtilComparador.compareTo(this.getKeywords(), dataOriginal.getKeywords());
        }

        if (UtilComparador.compareTo(this.getRequisitos(), dataOriginal.getRequisitos()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getRequisitos() != dataOriginal.getRequisitos(). this.getRequisitos()=" + this.getRequisitos() + " dataOriginal.getRequisitos()=" + dataOriginal.getRequisitos());
            }
            return UtilComparador.compareTo(this.getRequisitos(), dataOriginal.getRequisitos());
        }
        if (TipoPublicoObjetivoEntidadGridDTO.compareTo(this.getPublicosObjetivo(), dataOriginal.getPublicosObjetivo()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getPublicosObjetivo() != dataOriginal.getPublicosObjetivo(). this.getPublicosObjetivo()=" + this.getPublicosObjetivo() + " dataOriginal.getPublicosObjetivo()=" + dataOriginal.getPublicosObjetivo());
            }
            return TipoPublicoObjetivoEntidadGridDTO.compareTo(this.getPublicosObjetivo(), dataOriginal.getPublicosObjetivo());
        }
        if (ProcedimientoDocumentoDTO.compareTo(this.getDocumentos(), dataOriginal.getDocumentos()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getDocumentos() != dataOriginal.getDocumentos(). this.getDocumentos()=" + this.getDocumentos() + " dataOriginal.getDocumentos()=" + dataOriginal.getDocumentos());
            }
            return ProcedimientoDocumentoDTO.compareTo(this.getDocumentos(), dataOriginal.getDocumentos());
        }
        if (ProcedimientoDocumentoDTO.compareTo(this.getDocumentosLOPD(), dataOriginal.getDocumentosLOPD()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getDocumentosLOPD() != dataOriginal.getDocumentosLOPD(). this.getDocumentosLOPD()=" + this.getDocumentosLOPD() + " dataOriginal.getDocumentosLOPD()=" + dataOriginal.getDocumentosLOPD());
            }
            return ProcedimientoDocumentoDTO.compareTo(this.getDocumentosLOPD(), dataOriginal.getDocumentosLOPD());
        }
        if (NormativaGridDTO.compareTo(this.getNormativas(), dataOriginal.getNormativas()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getNormativas() != dataOriginal.getNormativas(). this.getNormativas()=" + this.getNormativas() + " dataOriginal.getNormativas()=" + dataOriginal.getNormativas());
            }
            return NormativaGridDTO.compareTo(this.getNormativas(), dataOriginal.getNormativas());
        }
        if (ProcedimientoTramiteDTO.compareTo(this.getTramites(), dataOriginal.getTramites()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getTramites() != dataOriginal.getTramites(). this.getTramites()=" + this.getTramites() + " dataOriginal.getTramites()=" + dataOriginal.getTramites());
            }
            return ProcedimientoTramiteDTO.compareTo(this.getTramites(), dataOriginal.getTramites(), mostrarLog);
        }
        if (TemaGridDTO.compareTo(this.getTemas(), dataOriginal.getTemas()) != 0) {
            if (mostrarLog) {
                LOG.error("ProcedimientoDTO.compareTo: this.getTemas() != dataOriginal.getTemas(). this.getTemas()=" + this.getTemas() + " dataOriginal.getTemas()=" + dataOriginal.getTemas());
            }
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
