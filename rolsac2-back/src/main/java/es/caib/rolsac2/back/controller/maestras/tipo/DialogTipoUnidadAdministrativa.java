package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.TipoUnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
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
 * Controlador para editar un tipo de unidad administrativa.
 *
 * @author jsegovia
 */
@Named
@ViewScoped
public class DialogTipoUnidadAdministrativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoUnidadAdministrativa.class);
    private String id;
    private TipoUnidadAdministrativaDTO data;
    private String identificadorOld;
    private TipoUnidadAdministrativaDTO dataOriginal;

    @EJB
    private TipoUnidadAdministrativaServiceFacade tipoUnidadAdministrativaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.
        this.setearIdioma();
        data = new TipoUnidadAdministrativaDTO();
        if (this.isModoAlta()) {
            //data = new TipoUnidadAdministrativaDTO();
            data.setEntidad(UtilJSF.getSessionBean().getEntidad());

            if (data.getDescripcion() == null) {
                data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            }

            if (data.getCargoMasculino() == null) {
                data.setCargoMasculino(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            }

            if (data.getCargoFemenino() == null) {
                data.setCargoFemenino(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            }

            if (data.getTratamientoMasculino() == null) {
                data.setTratamientoMasculino(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            }

            if (data.getTratamientoFemenino() == null) {
                data.setTratamientoFemenino(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            }
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoUnidadAdministrativaServiceFacade.findById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
            dataOriginal = data.clone();
        }



    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        TipoUnidadAdministrativaDTO datoDTO = (TipoUnidadAdministrativaDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDescripcion(datoDTO.getDescripcion());
            data.setCargoMasculino(datoDTO.getCargoMasculino());
            data.setCargoFemenino(datoDTO.getCargoFemenino());
            data.setTratamientoMasculino(datoDTO.getTratamientoMasculino());
            data.setTratamientoFemenino(datoDTO.getTratamientoFemenino());
        }
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            tipoUnidadAdministrativaServiceFacade.create(this.data, sessionBean.getUnidadActiva().getCodigo());
        } else {
            tipoUnidadAdministrativaServiceFacade.update(this.data);
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

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    public boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0
                || UtilComparador.compareTo(data.getEntidad().getCodigo(), dataOriginal.getEntidad().getCodigo()) != 0
                || UtilComparador.compareTo(data.getIdentificador(), dataOriginal.getIdentificador()) != 0
                || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0
                || UtilComparador.compareTo(data.getCargoFemenino(), dataOriginal.getCargoFemenino()) != 0
                || UtilComparador.compareTo(data.getTratamientoFemenino(), dataOriginal.getTratamientoFemenino()) != 0
                || UtilComparador.compareTo(data.getTratamientoMasculino(), dataOriginal.getTratamientoMasculino()) != 0
                || UtilComparador.compareTo(data.getCargoMasculino(), dataOriginal.getCargoMasculino()) != 0;
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

    public boolean verificarGuardar() {
        if (this.identificadorOld != null && !this.identificadorOld.equalsIgnoreCase(this.data.getIdentificador()) && Boolean.TRUE.equals(tipoUnidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador(), UtilJSF.getSessionBean().getEntidad().getCodigo()))) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (this.data.getCodigo() == null && Boolean.TRUE.equals(tipoUnidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador(), UtilJSF.getSessionBean().getEntidad().getCodigo()))) {
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoUnidadAdministrativaDTO getData() {
        return data;
    }

    public void setData(TipoUnidadAdministrativaDTO data) {
        this.data = data;
    }

    public TipoUnidadAdministrativaDTO getDataOriginal() {
        return dataOriginal;
    }

    public void setDataOriginal(TipoUnidadAdministrativaDTO dataOriginal) {
        this.dataOriginal = dataOriginal;
    }
}
