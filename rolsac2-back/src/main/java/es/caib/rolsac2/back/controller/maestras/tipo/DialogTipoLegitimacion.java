package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TipoLegitimacionServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogTipoLegitimacion extends AbstractController implements Serializable {
  private static final Logger LOG = LoggerFactory.getLogger(DialogTipoLegitimacion.class);
  private static final long serialVersionUID = -978862425481233306L;

  private String id;

  @EJB
  TipoLegitimacionServiceFacade tipoLegitimacionService;

  private TipoLegitimacionDTO data;
  private String identificadorAntiguo;
  private Literal descripcion;

  public void load() {
    this.setearIdioma();
    LOG.debug("init");
    // Inicializamos combos/desplegables/inputs
    // De momento, no tenemos desplegables.

    data = new TipoLegitimacionDTO();
    if (this.isModoAlta()) {
      // data.setUnidadAdministrativa(sessionBean.getUnidadActiva().getId());
    }
    else if (this.isModoEdicion() || this.isModoConsulta()) {
      data = tipoLegitimacionService.findById(Long.valueOf(id));
      identificadorAntiguo = data.getIdentificador();
    }

    if (data.getDescripcion() == null) {
      data.setDescripcion(Literal.createInstance());
    }
  }

  public void initMockup() {
    data = new TipoLegitimacionDTO();
    data.setId(1l);
    data.setIdentificador("T01");

  }

  public void abrirDlg() {
    final Map<String, String> params = new HashMap<>();
    UtilJSF.openDialog("dialogTipoLegitimacion", TypeModoAcceso.ALTA, params, true, 1050, 550);
  }

  public void guardar() {

    if (!verificarGuardar()) {
      return;
    }

    if (this.data.getId() == null) {
      tipoLegitimacionService.create(this.data);
    }
    else {
      tipoLegitimacionService.update(this.data);
    }

    // Retornamos resultado
    final DialogResult result = new DialogResult();
    //TODO Se produce un error por eso se pone que si null, entonces poner alta
    if (this.getModoAcceso() == null) {
      result.setModoAcceso(TypeModoAcceso.ALTA);
    }
    else {
      result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
    }
    result.setResult(data);
    UtilJSF.closeDialog(result);
  }

  private boolean verificarGuardar() {
    if (Objects.isNull(this.data.getId()) && tipoLegitimacionService.existeIdentificador(
      this.data.getIdentificador())) {
      UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
      return false;
    }

    if (Objects.nonNull(this.data.getId()) && !identificadorAntiguo.equals(this.data.getIdentificador())
      && tipoLegitimacionService.existeIdentificador(this.data.getIdentificador())) {
      UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
      return false;
    }

    return true;
  }

  public void cerrar() {

    final DialogResult result = new DialogResult();
    result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
    result.setCanceled(true);
    UtilJSF.closeDialog(result);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TipoLegitimacionDTO getData() {
    return data;
  }

  public void setData(TipoLegitimacionDTO data) {
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

  public String getIdentificadorAntiguo() {
    return identificadorAntiguo;
  }

  public void setIdentificadorAntiguo(String identificadorAntiguo) {
    this.identificadorAntiguo = identificadorAntiguo;
  }

}
