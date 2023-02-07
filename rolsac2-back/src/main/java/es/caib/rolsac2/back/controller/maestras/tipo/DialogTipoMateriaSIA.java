package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Controlador para editar un tipo materia SIA
 *
 * @author jrodrigof
 */
@Named
@ViewScoped
public class DialogTipoMateriaSIA extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoMateriaSIA.class);
    private static final long serialVersionUID = -978862425481233306L;

    private String id;

    private String identificadorAntiguo;

    private TipoMateriaSIADTO data;

    private TipoMateriaSIADTO dataOriginal;

    private Date fecha;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        this.setearIdioma();
        data = new TipoMateriaSIADTO();
        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = maestrasSupService.findTipoMateriaSIAById(Long.valueOf(id));
            dataOriginal = data.clone();
            identificadorAntiguo = data.getIdentificador();
        }

        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
        }
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            maestrasSupService.create(this.data);
        } else {
            maestrasSupService.update(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        // TODO Se produce un error por eso se pone que si null, entonces poner alta
        if (this.getModoAcceso() == null) {
            result.setModoAcceso(TypeModoAcceso.ALTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && maestrasSupService.existeIdentificadorTipoMateriaSIA(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorAntiguo.equals(this.data.getIdentificador())
                && maestrasSupService.existeIdentificadorTipoMateriaSIA(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }
        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getDescripcion(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogPlatTramitElectronica.descripcion", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }

        return true;
    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        TipoMateriaSIADTO datoDTO = (TipoMateriaSIADTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDescripcion(datoDTO.getDescripcion());
        }
    }


    public void cerrarDefinitivo() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        if(comprobarModificacion()) {
        	PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return !data.getCodigo().equals(dataOriginal.getCodigo())
                || !data.getIdentificador().equals(dataOriginal.getIdentificador())
                || !data.getDescripcion().equals(dataOriginal.getDescripcion());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoMateriaSIADTO getData() {
        return data;
    }

    public void setData(TipoMateriaSIADTO data) {
        this.data = data;
    }

    /**
     * Abre explorar Descripcion.
     */
    public void explorarDescripcion() {
        // explorarLiteral(data.getAvisoLegal());
        LOG.debug("Sin implementar");
    }

    /**
     * Gestión de retorno Descripcion.
     *
     * @param event
     */
    public void returnDialogoDescripcion(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
            final Literal literales = (Literal) respuesta.getResult();
            data.setDescripcion(literales);
        }
    }

    public String getIdentificadorAntiguo() {
        return identificadorAntiguo;
    }

    public void setIdentificadorAntiguo(String identificadorAntiguo) {
        this.identificadorAntiguo = identificadorAntiguo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

	public TipoMateriaSIADTO getDataOriginal() {
		return dataOriginal;
	}

	public void setDataOriginal(TipoMateriaSIADTO dataOriginal) {
		this.dataOriginal = dataOriginal;
	}
}
