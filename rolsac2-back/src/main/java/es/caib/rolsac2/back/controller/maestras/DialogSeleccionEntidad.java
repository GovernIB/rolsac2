package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
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
public class DialogSeleccionEntidad extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionEntidad.class);

    private LazyDataModel<EntidadGridDTO> lazyModel;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    private EntidadGridDTO datoSeleccionado;

    private EntidadFiltro filtro;

    private List<EntidadGridDTO> entidadesSeleccionadas;

    private EntidadGridDTO entidadSeleccionada;

    public LazyDataModel<EntidadGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        filtro = new EntidadFiltro();
        filtro.setIdioma(sessionBean.getLang());

        buscar();

        entidadesSeleccionadas = ((List<EntidadGridDTO>) UtilJSF.getValorMochilaByKey("entidadesSeleccionada"));
        if (entidadesSeleccionadas == null) {
            entidadesSeleccionadas = new ArrayList<>();
        }
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<EntidadGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public EntidadGridDTO getRowData(String rowKey) {
                for (EntidadGridDTO ent : (List<EntidadGridDTO>) getWrappedData()) {
                    if (ent.getCodigo().toString().equals(rowKey)) {
                        return ent;
                    }
                }
                return null;
            }

            @Override
            public List<EntidadGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                             Map<String, FilterMeta> filterBy) {
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
                    Pagina<EntidadGridDTO> pagina = new Pagina<>(new ArrayList<>(), 0);
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
        result.setResult(entidadesSeleccionadas);
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

    public void borrarEntidad() {
        if (entidadSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            entidadesSeleccionadas.remove(entidadSeleccionada);
            entidadSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void anadirEntidadLista() {
        if (this.datoSeleccionado == null) {
            return;
        }

        if (entidadesSeleccionadas != null && contains(this.datoSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.yaEstaEnLaLista"));
        } else {
            entidadesSeleccionadas.add(datoSeleccionado);
        }
    }

    private boolean contains(EntidadGridDTO dat) {
        boolean contiene = false;
        if (dat != null) {
            if (entidadesSeleccionadas != null && !entidadesSeleccionadas.isEmpty()) {
                for (EntidadGridDTO enti : entidadesSeleccionadas) {
                    if (enti.getCodigo().compareTo(dat.getCodigo()) == 0) {
                        contiene = true;
                        break;
                    }
                }
            }
        }
        return contiene;
    }

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

    public List<EntidadGridDTO> getEntidadesSeleccionadas() {
        return entidadesSeleccionadas;
    }

    public void setEntidadesSeleccionadas(List<EntidadGridDTO> entidadesSeleccionadas) {
        this.entidadesSeleccionadas = entidadesSeleccionadas;
    }

    public EntidadGridDTO getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(EntidadGridDTO entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }
}
