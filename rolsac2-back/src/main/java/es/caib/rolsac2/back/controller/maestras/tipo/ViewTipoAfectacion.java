package es.caib.rolsac2.back.controller.maestras.tipo;

import org.primefaces.PrimeFaces;
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

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TipoAfectacionServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoAfectacionGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoAfectacionFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;


@Named
@ViewScoped
public class ViewTipoAfectacion extends AbstractController implements Serializable {

  private static final long serialVersionUID = 8458061227203462410L;

  private static final Logger LOG = LoggerFactory.getLogger(ViewTipoAfectacion.class);

  /**
   * Model de dades emprat pel compoment dataTable de primefaces.
   */
  private LazyDataModel<TipoAfectacionGridDTO> lazyModel;

  @EJB
  TipoAfectacionServiceFacade tipoAfectacionService;

  /**
   * Dato seleccionado
   */
  private TipoAfectacionGridDTO datoSeleccionado;

  /**
   * Filtro
   **/
  private TipoAfectacionFiltro filtro;

  public LazyDataModel<TipoAfectacionGridDTO> getLazyModel() {
    return lazyModel;
  }


  // ACCIONS

  /**
   * Carrega la unitat orgànica i els procediments.
   */
  public void load() {
    LOG.debug("load");
    this.setearIdioma();
    // Inicializamos combos/desplegables/inputs/filtro
    filtro = new TipoAfectacionFiltro();
    filtro.setIdUA(sessionBean.getUnidadActiva().getId());// UtilJSF.getSessionUnidadActiva());
    filtro.setIdioma(sessionBean.getLang());// UtilJSF.getSessionLang());

    // Generamos una búsqueda
    buscar();
  }

  public void update() {
    buscar();
  }

  public void buscar() {
    lazyModel = new LazyDataModel<>() {
      private static final long serialVersionUID = 1L;

      @Override
      public TipoAfectacionGridDTO getRowData(String rowKey) {
        for (TipoAfectacionGridDTO tipoAfectacion : getWrappedData()) {
          if (tipoAfectacion.getId().toString().equals(rowKey))
            return tipoAfectacion;
        }
        return null;
      }

      @Override
      public Object getRowKey(TipoAfectacionGridDTO tipoAfectacion) {
        return tipoAfectacion.getId().toString();
      }

      @Override
      public List<TipoAfectacionGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
          Map<String, FilterMeta> filterBy) {
        try {
          if (!sortField.equals("filtro.orderBy")) {
            filtro.setOrderBy(sortField);
          }
          filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
          Pagina<TipoAfectacionGridDTO> pagina = tipoAfectacionService.findByFiltro(filtro);
          setRowCount((int) pagina.getTotal());
          return pagina.getItems();
        } catch (Exception e) {
          LOG.error("Error llamando", e);
          Pagina<TipoAfectacionGridDTO> pagina = new Pagina(new ArrayList(), 0);
          setRowCount((int) pagina.getTotal());
          return pagina.getItems();
        }
      }
    };
  }

  public void nuevoTipoAfectacion() {
    abrirVentana(TypeModoAcceso.ALTA);
  }

  public void editarTipoAfectacion() {
    if (datoSeleccionado == null) {
      UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
    } else {
      abrirVentana(TypeModoAcceso.EDICION);
    }
  }

  public void consultarTipoAfectacion() {
    if (datoSeleccionado != null) {
      abrirVentana(TypeModoAcceso.CONSULTA);
    }
  }

  public void returnDialogo(final SelectEvent event) {
    final DialogResult respuesta = (DialogResult) event.getObject();

    // Verificamos si se ha modificado
    if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
      this.buscar();
    }
  }

  private void abrirVentana(TypeModoAcceso modoAcceso) {
    // Muestra dialogo
    final Map<String, String> params = new HashMap<>();
    if (this.datoSeleccionado != null
        && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
      params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getId().toString());
    }
    UtilJSF.openDialog("dialogTipoAfectacion", modoAcceso, params, true, 780, 200);
  }


  public void borrarTipoAfectacion() {
    if (datoSeleccionado == null) {
      UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
    } else {
      tipoAfectacionService.delete(datoSeleccionado.getId());
      addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
    }
  }

  public TipoAfectacionGridDTO getDatoSeleccionado() {
    return datoSeleccionado;
  }

  public void setDatoSeleccionado(TipoAfectacionGridDTO datoSeleccionado) {
    this.datoSeleccionado = datoSeleccionado;
  }

  public TipoAfectacionFiltro getFiltro() {
    return filtro;
  }

  public void setFiltro(TipoAfectacionFiltro filtro) {
    this.filtro = filtro;
  }

}
