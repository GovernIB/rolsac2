package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.facade.TestServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;
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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

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

    @EJB
    TestServiceFacade testService;
 
    private PersonalGridDTO datoSeleccionado;
 
    private PersonalFiltro filtro;


    public LazyDataModel<PersonalGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    public void load() {
        LOG.debug("load");

        //this.tienePermisos(this.getClass().getName());

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new PersonalFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getId());//UtilJSF.getSessionUnidadActiva());
        filtro.setIdioma(sessionBean.getLang());//UtilJSF.getSessionLang());
 

        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public PersonalGridDTO getRowData(String rowKey) {
                for (PersonalGridDTO pers : (List<PersonalGridDTO>) getWrappedData()) {
                    if (pers.getId().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(PersonalGridDTO pers) {
                return pers.getId().toString();
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
                    Pagina<PersonalGridDTO> pagina = pagina = new Pagina(new ArrayList(), 3);
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
        if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
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
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getId().toString());
        }
        
    }


    public void borrarPersonal() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            personalService.delete(datoSeleccionado.getId());
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
      

        
        final String secOpenDialog = "CONSULTA"/*modoAcceso.toString()*/ + "-" + idParam + "-" + System.currentTimeMillis();
       
        PrimeFaces.current().dialog().openDynamic("dialogPersonal", options, paramsDialog);
    }


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