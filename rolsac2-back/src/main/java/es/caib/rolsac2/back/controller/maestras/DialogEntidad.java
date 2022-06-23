package es.caib.rolsac2.back.controller.maestras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

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

  @Inject
  private SessionBean sessionBean;

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
    }
  }

  public void guardar() {

    if (this.data.getId() == null) {
      administracionSupServiceFacade.createEntidad(this.data, sessionBean.getUnidadActiva().getId());
    } else {
      administracionSupServiceFacade.updateEntidad(this.data);
    }

    // Retornamos resultado
    final DialogResult result = new DialogResult();
    if(Objects.isNull(this.getModoAcceso())) {
      this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
    } else {
      result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
    }
    result.setResult(data);
    UtilJSF.closeDialog(result);
  }

  public void cerrar() {

    final DialogResult result = new DialogResult();
    if(Objects.isNull(this.getModoAcceso())) {
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

  public EntidadDTO getData() {
    return data;
  }

  public void setData(EntidadDTO data) {
    this.data = data;
  }
}
