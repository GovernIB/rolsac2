package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Controlador para exportar.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogClonar extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogClonar.class);
    private static final long serialVersionUID = -978862425481233206L;

    /**
     * Identificador
     */
    private String id;
    private Long idLong;

    /**
     * Tipo de clonacion (P: Procedimiento, S: Servicio)
     */
    private String tipo;

    /**
     * Indica si es tipo Proc, siendo true procedimiento, false servicio
     */
    private boolean tipoProc;

    /**
     * Indica si tiene wf definitivo
     */
    private boolean tieneWFDefinitivo;

    /**
     * Indica si tiene wf en modificacion
     */
    private boolean tieneWFEnModificacion;

    /**
     * Todos los datos
     **/
    private boolean estadoWF = true;

    /**
     * El procedimiento base.
     */
    ProcedimientoBaseDTO data;

    @Inject
    ProcedimientoServiceFacade procedimentoService;

    @Inject
    SystemServiceFacade systemService;

    public void load() {

        LOG.debug("init");
        this.setearIdioma();
        idLong = Long.valueOf(id);
        if ("P".equals(tipo)) {
            tipoProc = true;
            //data = procedimentoService.findProcedimientoByCodigo(Long.valueOf(id));
        } else {
            tipoProc = false;
            //data = procedimentoService.findServicioByCodigo(Long.valueOf(id));
        }

        tieneWFDefinitivo = procedimentoService.tieneWF(Long.valueOf(id), TypeProcedimientoWorkflow.DEFINITIVO.getValor());
        tieneWFEnModificacion = procedimentoService.tieneWF(Long.valueOf(id), TypeProcedimientoWorkflow.MODIFICACION.getValor());

        estadoWF = tieneWFDefinitivo ? false : true;
    }


    public void clonarDef() {
        clonar(TypeProcedimientoWorkflow.DEFINITIVO.getValor());
    }

    public void clonarMod() {
        clonar(TypeProcedimientoWorkflow.MODIFICACION.getValor());
    }

    /**
     * Guarda.
     */
    public void guardar() {
        estadoWF = true;
        clonar(estadoWF);
    }

    public void clonar(boolean iEstadoWF) {
        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        Long idProcedimientoClonado = procedimentoService.clonarProcedimiento(idLong, iEstadoWF, usuario, ruta);

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.CONSULTA);
        result.setResult(idProcedimientoClonado);
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

    /**
     * Cerra definitivo.
     */
    public void cerrar() {

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.CONSULTA);
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    /**
     * Ayuda
     */
    public void ayuda() {
        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No esta implementado");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isTipoProc() {
        return tipoProc;
    }

    public void setTipoProc(boolean tipoProc) {
        this.tipoProc = tipoProc;
    }

    public boolean isTieneWFDefinitivo() {
        return tieneWFDefinitivo;
    }

    public void setTieneWFDefinitivo(boolean tieneWFDefinitivo) {
        this.tieneWFDefinitivo = tieneWFDefinitivo;
    }

    public boolean isTieneWFEnModificacion() {
        return tieneWFEnModificacion;
    }

    public void setTieneWFEnModificacion(boolean tieneWFEnModificacion) {
        this.tieneWFEnModificacion = tieneWFEnModificacion;
    }

    public ProcedimientoBaseDTO getData() {
        return data;
    }

    public void setData(ProcedimientoBaseDTO data) {
        this.data = data;
    }

    public Long getIdLong() {
        return idLong;
    }

    public void setIdLong(Long idLong) {
        this.idLong = idLong;
    }

    public boolean isEstadoWF() {
        return estadoWF;
    }

    public void setEstadoWF(boolean estadoWF) {
        this.estadoWF = estadoWF;
    }
}
