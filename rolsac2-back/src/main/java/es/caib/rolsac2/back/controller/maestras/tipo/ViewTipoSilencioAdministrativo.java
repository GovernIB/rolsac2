package es.caib.rolsac2.back.controller.maestras.tipo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoSilencioAdministrativoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

/**
 * Controlador per obtenir la vista dels procediments d'una unitat orgànica. El definim a l'scope de view perquè a
 * nivell de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació. Amb view es manté
 * mentre no es canvii de vista.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class ViewTipoSilencioAdministrativo extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoSilencioAdministrativo.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoSilencioAdministrativoGridDTO> lazyModel;

    @EJB
    private MaestrasSupServiceFacade tipoSilencioAdministrativoService;

    /**
     * Dato seleccionado
     */
    private TipoSilencioAdministrativoGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoSilencioAdministrativoFiltro filtro;


    public LazyDataModel<TipoSilencioAdministrativoGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewTipoSilencioAdministrativo.class);
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoSilencioAdministrativoFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
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
            public TipoSilencioAdministrativoGridDTO getRowData(String rowKey) {
                for (TipoSilencioAdministrativoGridDTO pers : (List<TipoSilencioAdministrativoGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoSilencioAdministrativoGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<TipoSilencioAdministrativoGridDTO> load(int first, int pageSize, String sortField,
                                                                SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoSilencioAdministrativoGridDTO> pagina =
                            tipoSilencioAdministrativoService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoSilencioAdministrativoGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTipoSilencioAdministrativo() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoSilencioAdministrativo() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTipoSilencioAdministrativo() {
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
        if (this.datoSeleccionado != null
                && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogTipoSilencioAdministrativo", modoAcceso, params, true, 850, 290);
    }

    public void borrarTipoSilencioAdministrativo() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.noBorrado.seleccioneElemento"));
        } else {
            if (tipoSilencioAdministrativoService.existeProcedimientoConSilencio(datoSeleccionado.getCodigo())) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewTipoSilencioAdministrativo.existeRelacion"));
            } else {
                tipoSilencioAdministrativoService.deleteTipoSilencioAdministrativo(datoSeleccionado.getCodigo());
                addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
            }

        }
    }

    public TipoSilencioAdministrativoGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoSilencioAdministrativoGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoSilencioAdministrativoFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoSilencioAdministrativoFiltro filtro) {
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
