package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

/**
 * Controlador per obtenir la vista dels procediments d'una unitat orgànica. El definim a l'scope de view perquè
 * a nivell de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació.
 * Amb view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
public class ViewPersonal extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewPersonal.class);


    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<PersonalGridDTO> lazyModel;

    @EJB
    PersonalServiceFacade personalService;


    /**
     * Dato seleccionado
     */
    private PersonalGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private PersonalFiltro filtro;


    public LazyDataModel<PersonalGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewPersonal.class);
        //this.tienePermisos(this.getClass().getName());

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new PersonalFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());//UtilJSF.getSessionUnidadActiva());
        filtro.setIdioma(sessionBean.getLang());//UtilJSF.getSessionLang());

        LOG.debug("Rol admin: " + this.isUserRoleRSCAdmin());
        LOG.debug("Rol user: " + this.isUserRoleRSCUser());
        LOG.debug("Rol user: " + this.isUserRoleRSCMentira());
        LOG.debug("Username: " + this.getUserName());

        //Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }

    public void cambiarUAbuscarEvt(UnidadAdministrativaDTO ua) {
        sessionBean.cambiarUnidadAdministrativa(ua);
        buscarEvt();
    }

    /**
     * El buscar desde el evento de seleccionar una UA.
     */
    public void buscarEvt() {
        if (filtro.getIdUA() == null || filtro.getIdUA().compareTo(sessionBean.getUnidadActiva().getCodigo()) != 0) {
            buscar();
        }
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public PersonalGridDTO getRowData(String rowKey) {
                for (PersonalGridDTO pers : (List<PersonalGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(PersonalGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<PersonalGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                              Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<PersonalGridDTO> pagina = pagina = personalService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<PersonalGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoPersonal() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarPersonal() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarPersonal() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }


    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
            if (respuesta.isAlta()) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.creaciocorrecta"));
            } else if (respuesta.isEdicion()) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.actualitzaciocorrecta"));
            }
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogPersonal", modoAcceso, params, true, 800, 435);
    }

    public void borrarPersonal() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            personalService.delete(datoSeleccionado.getCodigo());
        }
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

    public PersonalGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(PersonalGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public PersonalFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(PersonalFiltro filtro) {
        this.filtro = filtro;
    }
}
