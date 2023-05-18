package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcesoLogServiceFacade;
import es.caib.rolsac2.service.model.ProcesoLogDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

/**
 * Controlador para consultar un proceso log.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogProcesoLog extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogProcesoLog.class);
    private static final long serialVersionUID = -978862425481233306L;

    private String id;
    private ProcesoLogDTO data;

    @EJB
    private ProcesoLogServiceFacade procesoLogServiceFacade;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();

        data = procesoLogServiceFacade.obtenerProcesoLogPorCodigo(Long.valueOf(id));
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

    public ProcesoLogDTO getData() {
        return data;
    }

    public void setData(ProcesoLogDTO data) {
        this.data = data;
    }


}
