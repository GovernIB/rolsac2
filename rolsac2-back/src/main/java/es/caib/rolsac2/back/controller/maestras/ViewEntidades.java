package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.exception.ServiceException;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
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
 * @author Indra
 */
@Named
@ViewScoped
public class ViewEntidades extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewEntidades.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<EntidadGridDTO> lazyModel;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    /**
     * Dato seleccionado
     */
    private EntidadGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private EntidadFiltro filtro;


    public LazyDataModel<EntidadGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");

        // Inicializamos combos/desplegables/inputs/filtro
        permisoAccesoVentana(ViewEntidades.class);
        this.setearIdioma();
        filtro = new EntidadFiltro();
        //filtro.setIdUA(sessionBean.getUnidadActiva().getId());
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
            public EntidadGridDTO getRowData(String rowKey) {
                for (EntidadGridDTO pers : (List<EntidadGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(EntidadGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<EntidadGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<EntidadGridDTO> pagina = administracionSupServiceFacade.findEntidadByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<EntidadGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoEntidad() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarEntidad() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarEntidad() {
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
        UtilJSF.openDialog("dialogEntidad", modoAcceso, params, true, 780, 620);
    }

    private void abrirVentanaBorrar() {
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
            UtilJSF.anyadirMochila("borrarEntidad", datoSeleccionado);
        }
        UtilJSF.openDialog("dialogBorrarEntidad", TypeModoAcceso.CONSULTA, params, true, 700, 300);
    }

    public void returnDialogoBorrar(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    public void borrarEntidad() {
        try {
            if (datoSeleccionado == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
            } else {
                abrirVentanaBorrar();
                //                addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
            }
        } catch (ServiceException e) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.ua.relacionadas"));
        }

    }

    /*public void borrarEntidad() {
        try {
            if (datoSeleccionado == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.noBorrado.seleccioneElemento"));
            } else {
                administracionSupServiceFacade.deleteEntidad(datoSeleccionado.getCodigo());
                addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
            }
        } catch (ServiceException e) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.ua.relacionadas"));
        }

    }*/

    public EntidadGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(EntidadGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public EntidadFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(EntidadFiltro filtro) {
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
