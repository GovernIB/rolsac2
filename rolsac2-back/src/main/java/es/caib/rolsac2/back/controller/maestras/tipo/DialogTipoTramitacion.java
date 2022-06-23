package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
import es.caib.rolsac2.service.facade.TipoTramitacionServiceFacade;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

/**
 * Controlador para editar un tipo de tramitación.
 *
 * @author jrodrigof
 */
@Named
@ViewScoped
public class DialogTipoTramitacion extends AbstractController implements Serializable {
  private static final Logger LOG = LoggerFactory.getLogger(DialogTipoTramitacion.class);

  private static final long serialVersionUID = -7363485737973780243L;

  private String id;

  private TipoTramitacionDTO data;

  private List<PlatTramitElectronicaDTO> plataformasTramiteList;

  @EJB
  TipoTramitacionServiceFacade tipoTramitacionService;

  @EJB
  PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;

  public void load() {
    LOG.debug("init");
    // Inicializamos combos/desplegables/inputs
    // De momento, no tenemos desplegables.

    data = new TipoTramitacionDTO();
    if (this.isModoAlta()) {
      // data.setUnidadAdministrativa(sessionBean.getUnidadActiva().getId());
    } else if (this.isModoEdicion() || this.isModoConsulta()) {
      data = tipoTramitacionService.findById(Long.valueOf(id));
    }

    plataformasTramiteList = platTramitElectronicaServiceFacade.findAll();

  }

  public void abrirDlg() {
    final Map<String, String> params = new HashMap<>();
    UtilJSF.openDialog("dialogTipoTramitacion", TypeModoAcceso.ALTA, params, true, 1050, 550);
  }

  public void guardar() {

    if (this.data.getId() == null) {
      tipoTramitacionService.create(this.data);
    } else {
      tipoTramitacionService.update(this.data);
    }

    // Retornamos resultado
    final DialogResult result = new DialogResult();
    if (Objects.isNull(this.getModoAcceso())) {
      result.setModoAcceso(TypeModoAcceso.ALTA);
    } else {
      result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
    }
    result.setResult(data);
    UtilJSF.closeDialog(result);
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

  public TipoTramitacionDTO getData() {
    return data;
  }

  public void setData(TipoTramitacionDTO data) {
    this.data = data;
  }

  public List<PlatTramitElectronicaDTO> getPlataformasTramiteList() {
    return plataformasTramiteList;
  }

  public void setPlataformasTramiteList(List<PlatTramitElectronicaDTO> plataformasTramiteList) {
    this.plataformasTramiteList = plataformasTramiteList;
  }
}
