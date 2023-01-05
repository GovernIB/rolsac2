package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorfklow;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoDTO")
public class ProcedimientoDTO extends ProcedimientoBaseDTO {

    private List<ProcedimientoTramiteDTO> tramites;

    public static ProcedimientoDTO createInstance(List<String> idiomas) {
        ProcedimientoDTO proc = new ProcedimientoDTO();
        proc.setWorkflow(TypeProcedimientoWorfklow.MODIFICACION);
        proc.setEstado(TypeProcedimientoEstado.MODIFICACION);
        proc.setNombreProcedimientoWorkFlow(Literal.createInstance(idiomas));
        proc.setDatosPersonalesDestinatario(Literal.createInstance(idiomas));
        proc.setDatosPersonalesFinalidad(Literal.createInstance(idiomas));
        proc.setRequisitos(Literal.createInstance(idiomas));
        proc.setObjeto(Literal.createInstance(idiomas));
        proc.setDestinatarios(Literal.createInstance(idiomas));
        proc.setTerminoResolucion(Literal.createInstance(idiomas));
        proc.setObservaciones(Literal.createInstance(idiomas));
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
        return "ProcedimientoDTO{" +
                " codigo=" + getCodigo() +
                ", codigoWF='" + getCodigoWF() + '\'' +
                '}';
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


}
