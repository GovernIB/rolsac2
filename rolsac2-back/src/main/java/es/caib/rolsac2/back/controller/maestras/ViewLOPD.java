package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

/**
 * Controlador para editar un DialogEntidad.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class ViewLOPD extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ViewLOPD.class);

    private String id;

    private EntidadDTO data;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;


    public void load() {
        LOG.debug("init view configuracion entidad");
        permisoAccesoVentana(ViewLOPD.class);
        this.setearIdioma();

        data = UtilJSF.getSessionBean().getEntidad();

    }

    public void guardar() {

        if (!checkObligatorio()) {
            return;
        }

        administracionSupServiceFacade.updateEntidad(this.data);

        UtilJSF.getSessionBean().setEntidad(this.data);

        // addGlobalMessage(getLiteral("msg.creaciocorrecta"));

        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("viewConfiguracionEntidad.actualizado"));

    }

    private boolean checkObligatorio() {
        if (this.data.getDescripcion() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogEntidad.obligatorio.descripcion"));
            return false;
        }

        if (!this.data.getDescripcion().checkObligatorio()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogEntidad.obligatorio.descripcion"));
            return false;
        }

        if (Objects.isNull(this.data.getCodigo()) && administracionSupServiceFacade.existeIdentificadorEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        return true;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntidadDTO getData() {
        return data;
    }

    public void setData(EntidadDTO data) {
        this.data = data;
    }

}
