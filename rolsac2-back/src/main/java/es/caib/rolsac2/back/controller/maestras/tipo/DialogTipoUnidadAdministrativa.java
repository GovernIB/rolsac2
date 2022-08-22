package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.TipoUnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

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
            data = new TipoUnidadAdministrativaDTO();
            data.setEntidad(UtilJSF.getSessionBean().getEntidad());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoUnidadAdministrativaServiceFacade.findById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
        }

        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance());
        }

        if (data.getCargoMasculino() == null) {
            data.setCargoMasculino(Literal.createInstance());
        }

        if (data.getCargoFemenino() == null) {
            data.setCargoFemenino(Literal.createInstance());
        }

        if (data.getTratamientoMasculino() == null) {
            data.setTratamientoMasculino(Literal.createInstance());
        }

        if (data.getTratamientoFemenino() == null) {
            data.setTratamientoFemenino(Literal.createInstance());
        }
    }

    public void guardar() {

        if (this.identificadorOld != null && !this.identificadorOld.equalsIgnoreCase(this.data.getIdentificador()) && Boolean.TRUE.equals(tipoUnidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador(), UtilJSF.getSessionBean().getEntidad().getCodigo()))) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return;
        }

        if (this.data.getCodigo() == null && Boolean.TRUE.equals(tipoUnidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador(), UtilJSF.getSessionBean().getEntidad().getCodigo()))) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
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

    public TipoUnidadAdministrativaDTO getData() {
        return data;
    }

    public void setData(TipoUnidadAdministrativaDTO data) {
        this.data = data;
    }

}
