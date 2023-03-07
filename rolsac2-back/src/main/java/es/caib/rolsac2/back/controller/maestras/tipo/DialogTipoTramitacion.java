package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controlador para editar un tipo de tramitación.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogTipoTramitacion extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoTramitacion.class);

    private static final long serialVersionUID = -7363485737973780243L;

    private String id;

    private TipoTramitacionDTO data;

    private TipoTramitacionDTO dataOriginal;

    private List<PlatTramitElectronicaDTO> plataformasTramiteList;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    @EJB
    private PlatTramitElectronicaServiceFacade platTramitElectronicaService;

    private String plantilla;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();

        if (this.isModoAlta()) {
            data = new TipoTramitacionDTO();
            data.setPlantilla("S".equals(plantilla));
            if ("S".equals(plantilla)) {
                data.setEntidad(sessionBean.getEntidad());
            }
            if (data.getDescripcion() == null) {
                data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            }

            if (data.getUrl() == null) {
                data.setUrl(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            }
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = maestrasSupService.findTipoTramitacionById(Long.valueOf(id));
            dataOriginal = data.clone();
        }



        plataformasTramiteList = platTramitElectronicaService.findAll(sessionBean.getEntidad().getCodigo());
    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        TipoTramitacionDTO datoDTO = (TipoTramitacionDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDescripcion(datoDTO.getDescripcion());
        }
    }

    public void guardar() {

        if (this.data.getCodigo() == null) {
            maestrasSupService.create(this.data);
        } else {
            maestrasSupService.update(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            result.setModoAcceso(TypeModoAcceso.ALTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0
                || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0
                || UtilComparador.compareTo(data.getTramiteId(), dataOriginal.getTramiteId()) != 0
                || UtilComparador.compareTo(data.getTramiteParametros(), dataOriginal.getTramiteParametros()) != 0
                || UtilComparador.compareTo(data.getTramiteVersion(), dataOriginal.getTramiteVersion()) != 0
                || UtilComparador.compareTo(data.getEntidad().getCodigo(), dataOriginal.getEntidad().getCodigo()) != 0
                || UtilComparador.compareTo(data.getUrlTramitacion(), dataOriginal.getUrlTramitacion()) != 0
                || UtilComparador.compareTo(data.isTramitPresencial(), dataOriginal.isTramitPresencial()) != 0
                || UtilComparador.compareTo(data.isPlantilla(), dataOriginal.isPlantilla()) != 0
                || UtilComparador.compareTo(data.isTramitElectronica(), dataOriginal.isTramitElectronica()) != 0
                || UtilComparador.compareTo(data.getUrl(), dataOriginal.getUrl()) != 0;
    }

    public void cerrarDefinitivo() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
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

    public TipoTramitacionDTO getData() {
        return data;
    }

    public void setData(TipoTramitacionDTO data) {
        this.data = data;
    }

    public List<PlatTramitElectronicaDTO> getPlataformasTramiteList() {
        return plataformasTramiteList;
    }

    public void setPlataformasTramiteList(List<PlatTramitElectronicaDTO> plataformasTramiteList) {
        this.plataformasTramiteList = plataformasTramiteList;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }
}
