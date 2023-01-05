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
@Schema(name = "ServicioDTO")
public class ServicioDTO extends ProcedimientoBaseDTO {

    private String tasa;
    private TipoTramitacionDTO tipoTramitacion;

    private TipoTramitacionDTO plantillaSel;

    private boolean tramitPresencial;

    private boolean tramitElectronica;

    private boolean tramitTelefonica;

    public static ServicioDTO createInstance(List<String> idiomas) {
        ServicioDTO serv = new ServicioDTO();
        serv.setWorkflow(TypeProcedimientoWorfklow.MODIFICACION);
        serv.setEstado(TypeProcedimientoEstado.MODIFICACION);
        serv.setNombreProcedimientoWorkFlow(Literal.createInstance(idiomas));
        serv.setDatosPersonalesDestinatario(Literal.createInstance(idiomas));
        serv.setDatosPersonalesFinalidad(Literal.createInstance(idiomas));
        serv.setRequisitos(Literal.createInstance(idiomas));
        serv.setObjeto(Literal.createInstance(idiomas));
        serv.setDestinatarios(Literal.createInstance(idiomas));
        serv.setTerminoResolucion(Literal.createInstance(idiomas));
        serv.setObservaciones(Literal.createInstance(idiomas));
        serv.setTipoTramitacion(TipoTramitacionDTO.createInstance(idiomas));
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

    @Override
    public String toString() {
        return "ServicioDTO{" +
                "codigo=" + getCodigo() +
                ", codigoWF='" + getCodigoWF() + '\'' +
                '}';
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



}
