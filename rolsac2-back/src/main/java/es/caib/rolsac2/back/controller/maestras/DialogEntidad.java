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
 * @author jsegovia
 */
@Named
@ViewScoped
public class DialogEntidad extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEntidad.class);

    private String id;

    private EntidadDTO data;

    private String identificadorAntiguo;
    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();

        data = new EntidadDTO();
        if (this.isModoAlta()) {
            data = new EntidadDTO();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionSupServiceFacade.findEntidadById(Long.valueOf(id));
            identificadorAntiguo = data.getIdentificador();
        }
    }

    public void guardar() {

        if (!checkObligatorio()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            administracionSupServiceFacade.createEntidad(this.data, sessionBean.getUnidadActiva().getCodigo());
        } else {
            administracionSupServiceFacade.updateEntidad(this.data);
        }

        // Cerramos y retornamos resultado
        cerrar();
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

        if (Objects.isNull(this.data.getCodigo())
                && administracionSupServiceFacade.existeIdentificadorEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorAntiguo.equalsIgnoreCase(this.data.getIdentificador())
                && administracionSupServiceFacade.existeIdentificadorEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        return true;
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public void cerrar() {

        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
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
