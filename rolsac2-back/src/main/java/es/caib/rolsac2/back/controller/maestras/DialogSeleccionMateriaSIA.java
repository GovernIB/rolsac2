package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;
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
import java.util.Objects;

@Named
@ViewScoped
public class DialogSeleccionMateriaSIA extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionMateriaSIA.class);

    private LazyDataModel<TipoMateriaSIAGridDTO> lazyModel;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    private TipoMateriaSIAGridDTO datoSeleccionado;

    private TipoMateriaSIAFiltro filtro;

    private List<TipoMateriaSIAGridDTO> materiasSeleccionadas;

    private TipoMateriaSIAGridDTO materiaSIAGridSeleccionada;

    public LazyDataModel<TipoMateriaSIAGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoMateriaSIAFiltro();
        filtro.setIdioma(sessionBean.getLang());

        // Generamos una búsqueda
        buscar();

        materiasSeleccionadas = (List<TipoMateriaSIAGridDTO>) UtilJSF.getValorMochilaByKey("materiasSeleccionadas");
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<TipoMateriaSIAGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public TipoMateriaSIAGridDTO getRowData(String rowKey) {
                for (TipoMateriaSIAGridDTO tipoMateriaSIA : (List<TipoMateriaSIAGridDTO>) getWrappedData()) {
                    if (tipoMateriaSIA.getCodigo().toString().equals(rowKey))
                        return tipoMateriaSIA;
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoMateriaSIAGridDTO tipoMateriaSIA) {
                return tipoMateriaSIA.getCodigo().toString();
            }

            @Override
            public List<TipoMateriaSIAGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                    Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoMateriaSIAGridDTO> pagina = maestrasSupService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoMateriaSIAGridDTO> pagina = new Pagina(new ArrayList(), 0);
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
        result.setResult(materiasSeleccionadas);
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

    public TipoMateriaSIAGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoMateriaSIAGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoMateriaSIAFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoMateriaSIAFiltro filtro) {
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

    public void borrarMateriaSIA() {
        if (materiaSIAGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            // maestrasSupService.deleteTipoMateriaSIA(materiaSIAGridSeleccionada.getCodigo());
            materiasSeleccionadas.remove(materiaSIAGridSeleccionada);
            materiaSIAGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public List<TipoMateriaSIAGridDTO> getMateriasSeleccionadas() {
        return materiasSeleccionadas;
    }

    public void setMateriasSeleccionadas(List<TipoMateriaSIAGridDTO> materiasSeleccionadas) {
        this.materiasSeleccionadas = materiasSeleccionadas;
    }

    public TipoMateriaSIAGridDTO getMateriaSIAGridSeleccionada() {
        return materiaSIAGridSeleccionada;
    }

    public void setMateriaSIAGridSeleccionada(TipoMateriaSIAGridDTO materiaSIAGridSeleccionada) {
        this.materiaSIAGridSeleccionada = materiaSIAGridSeleccionada;
    }

    public void anadirMateriaLista() {
        if (materiasSeleccionadas != null && materiasSeleccionadas.contains(this.datoSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Ya está en la lista");
        } else {
            if (materiasSeleccionadas == null) {
                materiasSeleccionadas = new ArrayList<>();
            }
            materiasSeleccionadas.add(datoSeleccionado);
        }
    }



}
