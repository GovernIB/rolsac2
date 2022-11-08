package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DialogSeleccionNormativa extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionNormativa.class);

    private LazyDataModel<NormativaGridDTO> lazyModel;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    private NormativaGridDTO datoSeleccionado;

    private NormativaFiltro filtro;

    private List<NormativaGridDTO> normativasSeleccionadas;

    private NormativaGridDTO normativaGridSeleccionada;

    public LazyDataModel<NormativaGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new NormativaFiltro();
        filtro.setIdioma(sessionBean.getLang());

        // Generamos una búsqueda
        buscar();
        //  normativasSeleccionadas.add(new NormativaGridDTO());
        // normativasSeleccionadas = new ArrayList<>(
        normativasSeleccionadas =  (List<NormativaGridDTO>) UtilJSF.getValorMochilaByKey("normativasSeleccionadas");
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public NormativaGridDTO getRowData(String rowKey) {
                for (NormativaGridDTO pers : (List<NormativaGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(NormativaGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<NormativaGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                   Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
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
        if (normativasSeleccionadas != null && normativasSeleccionadas.contains(this.datoSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Ya está en la lista");
        } else {
            if (normativasSeleccionadas == null) {
                normativasSeleccionadas = new ArrayList<>();
            }
            normativasSeleccionadas.add(datoSeleccionado);
        }
    }

}
