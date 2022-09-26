package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.EdificioDTO;
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
import java.util.Date;
import java.util.Objects;

/**
 * Controlador para editar un tipo materia SIA
 *
 * @author jrodrigof
 */
@Named
@ViewScoped
public class DialogEdificio extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogEdificio.class);

    private String id;

    private EdificioDTO data;

    private Date fecha;

    @EJB
    private AdministracionEntServiceFacade administracionEntService;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        this.setearIdioma();
        data = new EdificioDTO();
        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionEntService.findById(Long.valueOf(id));
        }

        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance());
        }
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            administracionEntService.create(this.data);
        } else {
            administracionEntService.update(this.data);
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
        if (Objects.nonNull(this.data.getEmail()) && !ValidacionTipoUtils.esEmailValido(this.data.getEmail())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.email.novalido"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCp())
                && !ValidacionTipoUtils.esCodPostalValido(this.data.getCp())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.codPostal.novalido"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getTelefono())
                && !ValidacionTipoUtils.esTelefonoValido(this.data.getTelefono())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.telefono.novalido"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getFax()) && !ValidacionTipoUtils.esTelefonoValido(this.data.getFax())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.fax.novalido"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getLatitud()) && !ValidacionTipoUtils.esLatitudValido(this.data.getLatitud())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.latitud.novalido"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getLongitud())
                && !ValidacionTipoUtils.esLongitudValido(this.data.getLongitud())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.longitud.novalido"), true);
            return false;
        }

        if (Objects.isNull(this.data.getDescripcion())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogEntidad.obligatorio.descripcion"));
            return false;
        }

        if (!this.data.getDescripcion().checkObligatorio()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogEntidad.obligatorio.descripcion"));
            return false;
        }


        return true;
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
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

    public EdificioDTO getData() {
        return data;
    }

    public void setData(EdificioDTO data) {
        this.data = data;
    }

    /**
     * Abre explorar Descripcion.
     */
    public void explorarDescripcion() {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
