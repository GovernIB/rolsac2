package es.caib.rolsac2.back.controller.maestras;


import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;
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
 *
 */
@Named
@ViewScoped
public class DialogTipoPublicoObjetivoEntidad extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoPublicoObjetivoEntidad.class);

    @EJB
    private MaestrasSupServiceFacade serviceFacade;

    private String id;

    private TipoPublicoObjetivoEntidadDTO data;

    private TipoPublicoObjetivoEntidadDTO dataOriginal;

    private List<TipoPublicoObjetivoDTO> listaTipos;

    private String identificadorOld = "";

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        this.setearIdioma();
        data = new TipoPublicoObjetivoEntidadDTO();

        if (this.isModoAlta()) {
            data = new TipoPublicoObjetivoEntidadDTO();
            data.setEntidad(sessionBean.getEntidad());
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = serviceFacade.findTipoPublicoObjetivoEntidadById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
            dataOriginal = data.clone();
        }

        listaTipos = serviceFacade.findAllTiposPublicoObjetivo();
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }
        if (getModoAcceso().equals("ALTA")) {
            serviceFacade.create(this.data);
        }
        if (getModoAcceso().equals("EDICION")) {
            serviceFacade.update(this.data);
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

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        TipoPublicoObjetivoEntidadDTO datoDTO = (TipoPublicoObjetivoEntidadDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDescripcion(datoDTO.getDescripcion());
        }
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && serviceFacade.existeIdentificadorTipoPublicoObjetivoEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && serviceFacade.existeIdentificadorTipoPublicoObjetivoEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }
        List<String> idiomasPendientesUrl = ValidacionTipoUtils.esLiteralCorrecto(this.data.getDescripcion(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesUrl.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dict.descripcion", "dialogLiteral.validacion.idiomas", idiomasPendientesUrl), true);
            return false;
        }
        return true;
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0
                || UtilComparador.compareTo(data.getIdentificador(), dataOriginal.getIdentificador()) != 0
                || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoPublicoObjetivoEntidadDTO getData() {
        return data;
    }

    public void setData(TipoPublicoObjetivoEntidadDTO data) {
        this.data = data;
    }

    public List<TipoPublicoObjetivoDTO> getListaTipos() {
        return listaTipos;
    }

    public void setListaTipos(List<TipoPublicoObjetivoDTO> listaTipos) {
        this.listaTipos = listaTipos;
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
            data.setIdentificador((String) respuesta.getResult());
        }
    }

    public boolean modoConsultaCheck() {
        if (getModoAcceso().equals("CONSULTA")) {
            return true;
        }
        return false;
    }

}
