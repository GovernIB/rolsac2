package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Controlador para editar un  DialogTipoNormativa.
 *
 * @author jsegovia
 */
@Named
@ViewScoped
public class DialogTipoNormativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoNormativa.class);

    private String id;

    private TipoNormativaDTO data;

    @EJB
    MaestrasSupServiceFacade maestrasSupService;

    private String identificadorOld;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.
        this.setearIdioma();
        data = new TipoNormativaDTO();
        if (this.isModoAlta()) {
            data = new TipoNormativaDTO();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = maestrasSupService.findTipoNormativaById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
        }
        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
        }

    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        if (Objects.isNull(this.data.getCodigo())) {
            maestrasSupService.create(this.data, sessionBean.getUnidadActiva().getCodigo());
        } else {
            maestrasSupService.update(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && maestrasSupService.checkIdentificadorTipoNormativa(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && maestrasSupService.checkIdentificadorTipoNormativa(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getDescripcion(), sessionBean.getIdiomasObligatoriosList());
        if(!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogPlatTramitElectronica.descripcion", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }

        return true;
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

    public TipoNormativaDTO getData() {
        return data;
    }

    public void setData(TipoNormativaDTO data) {
        this.data = data;
    }
}
