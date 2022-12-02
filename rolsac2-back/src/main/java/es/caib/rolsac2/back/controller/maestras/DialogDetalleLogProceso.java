package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcesoLogServiceFacade;

import es.caib.rolsac2.service.model.ProcesoLogDTO;
import es.caib.rolsac2.service.model.Propiedad;

import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named
@ViewScoped
public class DialogDetalleLogProceso extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogDetalleLogProceso.class);

    private String id;

    private ProcesoLogDTO data;

    /**
     * Propiedad seleccionada.
     */
    private Propiedad propiedadSeleccionada;

    @EJB
    ProcesoLogServiceFacade procesoLogServiceFacade;

    String estado;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = procesoLogServiceFacade.obtenerProcesoLogPorCodigo(Long.valueOf(id));
        data.setEstadoTexto(estado);
    }


    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }


    public void cerrar() {
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.CONSULTA);
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public void verTraza() {

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

    public Propiedad getPropiedadSeleccionada() {
        return propiedadSeleccionada;
    }

    public void setPropiedadSeleccionada(Propiedad propiedadSeleccionada) {
        this.propiedadSeleccionada = propiedadSeleccionada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
