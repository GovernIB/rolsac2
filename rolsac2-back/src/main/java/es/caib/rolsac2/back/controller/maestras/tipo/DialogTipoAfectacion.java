package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
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
 * Controlador para editar un tipo de afectación.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogTipoAfectacion extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoAfectacion.class);
    private static final long serialVersionUID = -978862425481233306L;

    private String id;

    private String identificadorAntiguo;

    private TipoAfectacionDTO data;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();

        data = new TipoAfectacionDTO();
        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = maestrasSupService.findTipoAfectacionById(Long.valueOf(id));
            identificadorAntiguo = data.getIdentificador();
        }

        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance());
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
                && maestrasSupService.existeIdentificadorTipoAfectacion(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorAntiguo.equals(this.data.getIdentificador())
                && maestrasSupService.existeIdentificadorTipoAfectacion(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
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

    public TipoAfectacionDTO getData() {
        return data;
    }

    public void setData(TipoAfectacionDTO data) {
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

}
