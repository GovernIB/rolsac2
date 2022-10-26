package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DialogProcedimientoTramite extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogProcedimientoTramite.class);

    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    private ProcedimientoTramiteDTO data;

    private String id = "";

    private List<String> idiomasPermitidos = new ArrayList<>();

    private List<String> idiomasObligatorios = new ArrayList<>();

    private List<PlatTramitElectronicaDTO> platTramitElectronica;

    private List<String> canalesSeleccionados;

    private Literal nombreProcedimiento;

    private List<TipoTramitacionDTO> plantillasTipoTramitacion;

    private PlatTramitElectronicaDTO platTramitElectronicaSel;

    private TipoTramitacionDTO plantillaSel;

    private TipoTramitacionDTO canalPresentacion;

    public void load() {
        this.setearIdioma();

        data = ProcedimientoTramiteDTO.createInstance();

        nombreProcedimiento = (Literal) UtilJSF.getValorMochilaByKey("nombreProcedimiento");

        platTramitElectronicaSel = new PlatTramitElectronicaDTO();

        plantillaSel = null;

        canalesSeleccionados = new ArrayList<>();

        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = (ProcedimientoTramiteDTO) UtilJSF.getValorMochilaByKey("tramiteSel");
            if (data != null && data.getProcedimiento() != null) {
                nombreProcedimiento = data.getProcedimiento().getNombre();
            }

            if (data != null && data.getTipoTramitacion() != null) {
                canalPresentacion = new TipoTramitacionDTO(data.getTipoTramitacion());
                platTramitElectronicaSel = canalPresentacion.getCodPlatTramitacion();

            }

            if (data != null && data.getTipoTramitacion() != null && data.getTipoTramitacion().isTramitPresencial())
                canalesSeleccionados.add("PRE");
            if (data != null && data.getTipoTramitacion() != null && data.getTipoTramitacion().isTramitElectronica())
                canalesSeleccionados.add("TEL");
            // if (data != null && data.getTipoTramitacion() != null && data.getTipoTramitacion().isTramitElectronica())
            // canalesSeleccionados.add("TFN");

        }

        platTramitElectronica = platTramitElectronicaServiceFacade.findAll();
        plantillasTipoTramitacion = maestrasSupServiceFacade.findPlantillasTiposTramitacion();

    }

    private boolean verificarGuardar() {
        return true;
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        if (data.getTipoTramitacion() != null && data.getTipoTramitacion().getCodigo() != null) {
            canalPresentacion.setCodigo(data.getTipoTramitacion().getCodigo());
        }

        data.setTipoTramitacion(canalPresentacion);

        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProcedimientoTramiteDTO getData() {
        return data;
    }

    public void setData(ProcedimientoTramiteDTO data) {
        this.data = data;
    }

    public void setIdiomas() {
        idiomasPermitidos = sessionBean.getIdiomasObligatoriosList();
        idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
    }

    public List<String> getIdiomasPermitidos() {
        return idiomasPermitidos;
    }

    public void setIdiomasPermitidos(List<String> idiomasPermitidos) {
        this.idiomasPermitidos = idiomasPermitidos;
    }

    public List<String> getIdiomasObligatorios() {
        return idiomasObligatorios;
    }

    public void setIdiomasObligatorios(List<String> idiomasObligatorios) {
        this.idiomasObligatorios = idiomasObligatorios;
    }

    public List<PlatTramitElectronicaDTO> getPlatTramitElectronica() {
        return platTramitElectronica;
    }

    public void setPlatTramitElectronica(List<PlatTramitElectronicaDTO> platTramitElectronica) {
        this.platTramitElectronica = platTramitElectronica;
    }

    public List<String> getCanalesSeleccionados() {
        return canalesSeleccionados;
    }

    public void setCanalesSeleccionados(List<String> canalesSeleccionados) {
        this.canalesSeleccionados = canalesSeleccionados;
    }

    public Literal getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(Literal nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public List<TipoTramitacionDTO> getPlantillasTipoTramitacion() {
        return plantillasTipoTramitacion;
    }

    public void setPlantillasTipoTramitacion(List<TipoTramitacionDTO> plantillasTipoTramitacion) {
        this.plantillasTipoTramitacion = plantillasTipoTramitacion;
    }

    public PlatTramitElectronicaDTO getPlatTramitElectronicaSel() {
        return platTramitElectronicaSel;
    }

    public void setPlatTramitElectronicaSel(PlatTramitElectronicaDTO platTramitElectronicaSel) {
        this.platTramitElectronicaSel = platTramitElectronicaSel;
    }

    public TipoTramitacionDTO getPlantillaSel() {
        return plantillaSel;
    }

    public void setTipoTramitacionSel(TipoTramitacionDTO plantillaSel) {
        this.plantillaSel = plantillaSel;
    }

    public void setPlantillaSel(TipoTramitacionDTO plantillaSel) {
        this.plantillaSel = plantillaSel;
    }

    public TipoTramitacionDTO getCanalPresentacion() {
        return canalPresentacion;
    }

    public void setCanalPresentacion(TipoTramitacionDTO canalPresentacion) {
        this.canalPresentacion = canalPresentacion;
    }

    public void cambiaTipo() {

        if (canalesSeleccionados != null && !canalesSeleccionados.isEmpty()) {

            if (plantillaSel == null && canalPresentacion == null) {
                canalPresentacion = new TipoTramitacionDTO();
            }

            if (canalesSeleccionados.stream().noneMatch(c -> "TEL".equals(c))) {
                plantillaSel = null;
            } else {
                if (plantillaSel != null) {
                    canalPresentacion = new TipoTramitacionDTO(plantillaSel);
                }
            }
            canalPresentacion.setTramitPresencial(canalesSeleccionados.contains("PRE"));
            canalPresentacion.setTramitElectronica(canalesSeleccionados.contains("TEL"));

            // data.getTipoTramitacion().setTramitTelefonico(Arrays.asList(canalesSeleccionados).contains("TFN"));
        } else {
            canalPresentacion = null;
            plantillaSel = null;
        }
    }

    public void testEvento() {
        LOG.info("evento");
        if (plantillaSel != null) {
            canalPresentacion = new TipoTramitacionDTO(plantillaSel);
        } else {
            canalPresentacion = new TipoTramitacionDTO();
        }
    }
}
