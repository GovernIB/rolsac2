package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.ConfiguracionGlobalGridDTO;
import es.caib.rolsac2.service.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Controlador per obtenir la vista dels procediments d'una unitat orgànica. El definim a l'scope de view perquè a
 * nivell de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació. Amb view es manté
 * mentre no es canvii de vista.
 *
 * @author jrodrigof
 */
@Named
@ViewScoped
public class ViewConfiguracionesGlobales extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewConfiguracionesGlobales.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<ConfiguracionGlobalGridDTO> lazyModel;

    @EJB
    private AdministracionSupServiceFacade administracionSupService;

    /**
     * Dato seleccionado
     */
    private ConfiguracionGlobalGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private ConfiguracionGlobalFiltro filtro;


    public LazyDataModel<ConfiguracionGlobalGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewConfiguracionesGlobales.class);
        this.setearIdioma();
        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new ConfiguracionGlobalFiltro();
        //filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());

        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscarAvanzada() {
        System.out.println();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getRowKey(ConfiguracionGlobalGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            @Override
            public ConfiguracionGlobalGridDTO getRowData(String rowKey) {
                for (ConfiguracionGlobalGridDTO pers : getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }


            public int count(Map<String, FilterMeta> filterBy) {
                try {
                    return administracionSupService.countConfGlobalByFiltro(filtro);
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    return 0;
                }
            }

            @Override
            public List<ConfiguracionGlobalGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    return administracionSupService.listConfGlobalByFiltro(filtro);
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    return new ArrayList<>();
                }
            }
        };
    }

    public void editarConfiguracionGlobal() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            if (datoSeleccionado.getNoModificable()) {
                abrirVentana(TypeModoAcceso.CONSULTA);
            } else {
                abrirVentana(TypeModoAcceso.EDICION);
            }
        }
    }

    public void consultarConfiguracionGlobal() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }


    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogConfiguracionGlobal", modoAcceso, params, true, 780, 450);
    }

    public ConfiguracionGlobalGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(ConfiguracionGlobalGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ConfiguracionGlobalFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ConfiguracionGlobalFiltro filtro) {
        this.filtro = filtro;
    }

    public void setFiltroTexto(String texto) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setTexto(texto);
        }
    }

    public String getFiltroTexto() {
        if (Objects.nonNull(this.filtro)) {
            return this.filtro.getTexto();
        }
        return "";
    }

}
