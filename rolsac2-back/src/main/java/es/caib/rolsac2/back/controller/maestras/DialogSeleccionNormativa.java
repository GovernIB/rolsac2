package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class DialogSeleccionNormativa extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionNormativa.class);

    private LazyDataModel<NormativaGridDTO> lazyModel;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    private NormativaGridDTO datoSeleccionado;

    private NormativaFiltro filtro;

    private List<NormativaGridDTO> normativasSeleccionadas;

    private NormativaGridDTO normativaGridSeleccionada;
    private List<TipoNormativaDTO> listTipoNormativa;
    private List<TipoBoletinDTO> listTipoBoletin;

    public LazyDataModel<NormativaGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new NormativaFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        cargarFiltros();

        // Generamos una búsqueda
        buscar();
        //  normativasSeleccionadas.add(new NormativaGridDTO());
        // normativasSeleccionadas = new ArrayList<>(
        normativasSeleccionadas = (List<NormativaGridDTO>) UtilJSF.getValorMochilaByKey("normativasSeleccionadas");

    }

    /**
     * Limpia el filtro.
     */
    public void limpiarFiltro() {
        filtro = new NormativaFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setOrder("DESCENDING");
    }

    public void update() {
        buscar();
    }

    /**
     * Carga los filtros de la ventana.
     */
    private void cargarFiltros() {
        listTipoBoletin = maestrasSupServiceFacade.findBoletines();
        listTipoNormativa = maestrasSupServiceFacade.findTipoNormativa();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public NormativaGridDTO getRowData(String rowKey) {
                for (NormativaGridDTO pers : (List<NormativaGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(NormativaGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<NormativaGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    filtro.setSoloValidas(true);
                    Pagina<NormativaGridDTO> pagina = normativaServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<NormativaGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void guardar() {
        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(normativasSeleccionadas);

        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public NormativaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(NormativaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public NormativaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(NormativaFiltro filtro) {
        this.filtro = filtro;
    }

    public List<NormativaGridDTO> getNormativasSeleccionadas() {
        return normativasSeleccionadas;
    }

    public void setNormativasSeleccionadas(List<NormativaGridDTO> normativasSeleccionadas) {
        this.normativasSeleccionadas = normativasSeleccionadas;
    }

    public NormativaGridDTO getNormativaGridSeleccionada() {
        return normativaGridSeleccionada;
    }

    public void setNormativaGridSeleccionada(NormativaGridDTO n) {
        this.normativaGridSeleccionada = n;
    }


    public void borrarNormativa() {
        if (normativaGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            normativasSeleccionadas.remove(normativaGridSeleccionada);
            normativaGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void anadirNormativaLista() {
        if (this.datoSeleccionado == null) {
            return;
        }
        if (normativasSeleccionadas != null && contains(this.datoSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.yaEstaEnLaLista"));
        } else {
            if (normativasSeleccionadas == null) {
                normativasSeleccionadas = new ArrayList<>();
            }
            normativasSeleccionadas.add(datoSeleccionado);
        }
    }

    private boolean contains(NormativaGridDTO dat) {
        boolean contiene = false;
        if (dat != null) {
            if (normativasSeleccionadas != null && !normativasSeleccionadas.isEmpty()) {
                for (NormativaGridDTO tipo : normativasSeleccionadas) {
                    if (tipo.getCodigo().compareTo(dat.getCodigo()) == 0) {
                        contiene = true;
                        break;
                    }
                }
            }
        }
        return contiene;
    }

    public List<TipoNormativaDTO> getListTipoNormativa() {
        return listTipoNormativa;
    }

    public void setListTipoNormativa(List<TipoNormativaDTO> listTipoNormativa) {
        this.listTipoNormativa = listTipoNormativa;
    }

    public List<TipoBoletinDTO> getListTipoBoletin() {
        return listTipoBoletin;
    }

    public void setListTipoBoletin(List<TipoBoletinDTO> listTipoBoletin) {
        this.listTipoBoletin = listTipoBoletin;
    }
}
