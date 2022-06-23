package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TestServiceFacade;
import es.caib.rolsac2.service.facade.TipoPublicoObjetivoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;
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
 * Controlador per obtenir la vista dels procediments d'una unitat orgànica. El definim a l'scope de view perquè
 * a nivell de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació.
 * Amb view es manté mentre no es canvii de vista.
 *
 * @author jsegovia
 */
@Named
@ViewScoped
public class ViewTipoPublicoObjetivo extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoPublicoObjetivo.class);

    List<String> idiomas;


    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoPublicoObjetivoGridDTO> lazyModel;

    @EJB
    TipoPublicoObjetivoServiceFacade tipoPublicoObjetivoService;

    @EJB
    TestServiceFacade testService;

    /**
     * Dato seleccionado
     */
    private TipoPublicoObjetivoGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoPublicoObjetivoFiltro filtro;


    public LazyDataModel<TipoPublicoObjetivoGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        this.setearIdioma();
        LOG.debug("load");
        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoPublicoObjetivoFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getId());//UtilJSF.getSessionUnidadActiva());
        filtro.setIdioma(sessionBean.getLang());//UtilJSF.getSessionLang());

        LOG.error("Rol admin: " + this.isUserRoleRSCAdmin());
        LOG.error("Rol user: " + this.isUserRoleRSCUser());
        LOG.error("Rol user: " + this.isUserRoleRSCMentira());
        LOG.error("Username: " + this.getUserName());

        idiomas = Arrays.asList("es", "ca");

        //Generamos una búsqueda
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
            public TipoPublicoObjetivoGridDTO getRowData(String rowKey) {
                for (TipoPublicoObjetivoGridDTO pers : (List<TipoPublicoObjetivoGridDTO>) getWrappedData()) {
                    if (pers.getId().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoPublicoObjetivoGridDTO pers) {
                return pers.getId().toString();
            }

            @Override
            public List<TipoPublicoObjetivoGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                         Map<String, FilterMeta> filterBy) {
                try {
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoPublicoObjetivoGridDTO> pagina = tipoPublicoObjetivoService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoPublicoObjetivoGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTipoPublicoObjetivo() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoPublicoObjetivo() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTipoPublicoObjetivo() {
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
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getId().toString());
        }
        UtilJSF.openDialog("dialogTipoPublicoObjetivo", modoAcceso, params, true, 850, 230);
    }


    public void borrarTipoPublicoObjetivo() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            tipoPublicoObjetivoService.delete(datoSeleccionado.getId());
        }
    }

    public void borrar() {
        // Opciones dialogo
        LOG.error("llega");
        final Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 800);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("id", "someId");

        // Parametros
        String idParam = "";
        final Map<String, List<String>> paramsDialog = new HashMap<>();
        paramsDialog.put(/*TypeParametroVentana.MODO_ACCESO.toString()*/"MODO_ACCESO", Collections.singletonList(/*modoAcceso.toString()*/"CONSULTA"));
        /*if (params != null) {
            for (final String key : params.keySet()) {
                paramsDialog.put(key, Collections.singletonList(params.get(key)));
                if (TypeParametroVentana.ID.toString().equals(key)) {
                    idParam = params.get(key);
                }
            }
        }*/

        // Metemos en sessionbean un parámetro de seguridad para evitar que se
        // pueda cambiar el modo de acceso
        final String secOpenDialog = "CONSULTA"/*modoAcceso.toString()*/ + "-" + idParam + "-" + System.currentTimeMillis();
        //getSessionBean().getMochilaDatos().put(SEC_OPEN_DIALOG, secOpenDialog);

        // Abre dialogo
        PrimeFaces.current().dialog().openDynamic("dialogTipoPublicoObjetivo", options, paramsDialog);
    }


    /**
     * Esborra el procediment l'identificador indicat. El mètode retorna void perquè no cal navegació ja que
     * l'eliminació es realitza des de la pàgina de llistat, i quedam en aquesta pàgina.
     *
     * @param id identificador de del procediment.
     */
    public void delete(Long id) {
        LOG.debug("delete");

        // procedimentService.delete(id);

        ResourceBundle labelsBundle = getBundle("labels");
        addGlobalMessage(labelsBundle.getString("msg.eliminaciocorrecta"));
    }

    public TipoPublicoObjetivoGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoPublicoObjetivoGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoPublicoObjetivoFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoPublicoObjetivoFiltro filtro) {
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

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }
}
