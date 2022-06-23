package es.caib.rolsac2.back.controller.maestras.tipo;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TipoFormaInicioServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

/**
 * Controlador para editar un tipo de forma de inicio
 *
 * @author jrodrigof
 */
@Named
@ViewScoped
public class DialogTipoFormaInicio extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoFormaInicio.class);
    private static final long serialVersionUID = -978862425481233306L;

    private String id;

    private String identificadorAntiguo;

    private TipoFormaInicioDTO data;

    private Literal descripcion;

    @EJB
    TipoFormaInicioServiceFacade tipoFormaInicioService;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = new TipoFormaInicioDTO();
        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoFormaInicioService.findById(Long.valueOf(id));
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

        if (this.data.getId() == null) {
            tipoFormaInicioService.create(this.data);
        } else {
            tipoFormaInicioService.update(this.data);
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
        if (Objects.isNull(this.data.getId())
                        && tipoFormaInicioService.existeIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getId()) && !identificadorAntiguo.equals(this.data.getIdentificador())
                        && tipoFormaInicioService.existeIdentificador(this.data.getIdentificador())) {
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

    public TipoFormaInicioDTO getData() {
        return data;
    }

    public void setData(TipoFormaInicioDTO data) {
        this.data = data;
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

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }
}
