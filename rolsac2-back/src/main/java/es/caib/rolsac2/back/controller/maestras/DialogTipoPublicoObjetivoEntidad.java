package es.caib.rolsac2.back.controller.maestras;


import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**

 */
@Named
@ViewScoped
public class DialogTipoPublicoObjetivoEntidad extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoPublicoObjetivoEntidad.class);

    @EJB
    private MaestrasSupServiceFacade serviceFacade;

    private String id;

    private TipoPublicoObjetivoEntidadDTO data;

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
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = serviceFacade.findTipoPublicoObjetivoEntidadById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
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

    private boolean verificarGuardar() {
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

    public TipoPublicoObjetivoEntidadDTO getData() {
        return data;
    }

    public void setData(TipoPublicoObjetivoEntidadDTO data) {
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
