package es.caib.rolsac2.back.controller.maestras.tipo;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TipoMateriaSIAServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

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

    private Literal descripcion;
    private Date fecha;

    @EJB
    TipoMateriaSIAServiceFacade tipoMateriaSIAService;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        this.setearIdioma();
        data = new TipoMateriaSIADTO();
        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoMateriaSIAService.findById(Long.valueOf(id));
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
            tipoMateriaSIAService.create(this.data);
        } else {
            tipoMateriaSIAService.update(this.data);
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
        if (Objects.isNull(this.data.getId())
                        && tipoMateriaSIAService.existeIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getId()) && !identificadorAntiguo.equals(this.data.getIdentificador())
                        && tipoMateriaSIAService.existeIdentificador(this.data.getIdentificador())) {
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

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
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
}