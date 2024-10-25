package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoEntidadFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogSeleccionTipoPublicoObjetivoEntidad extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionTipoPublicoObjetivoEntidad.class);

    private LazyDataModel<TipoPublicoObjetivoEntidadGridDTO> lazyModel;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    private TipoPublicoObjetivoEntidadGridDTO datoSeleccionado;

    private TipoPublicoObjetivoEntidadFiltro filtro;

    private List<TipoPublicoObjetivoEntidadGridDTO> tipoPublicoObjEntSeleccionadas;

    private TipoPublicoObjetivoEntidadGridDTO pubobjentGridSeleccionada;

    public LazyDataModel<TipoPublicoObjetivoEntidadGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoPublicoObjetivoEntidadFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());

        // Generamos una búsqueda
        buscar();

       /* tipoPublicoObjEntSeleccionadas = new ArrayList<>(
                (List<TipoPublicoObjetivoEntidadGridDTO>) UtilJSF.getValorMochilaByKey("tipoPubObjEntSeleccionadas"));
*/
        List<TipoPublicoObjetivoEntidadGridDTO> tiposObjetivoSesion =
                ( (List<TipoPublicoObjetivoEntidadGridDTO>) UtilJSF.getValorMochilaByKey("tipoPubObjEntSeleccionadas"));

        if (tiposObjetivoSesion == null) {
            tipoPublicoObjEntSeleccionadas = new ArrayList<>();
        }else{
            tipoPublicoObjEntSeleccionadas = new ArrayList<>(tiposObjetivoSesion);
        }
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<TipoPublicoObjetivoEntidadGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public TipoPublicoObjetivoEntidadGridDTO getRowData(String rowKey) {
                for (TipoPublicoObjetivoEntidadGridDTO tipoPubObjEnt : (List<TipoPublicoObjetivoEntidadGridDTO>) getWrappedData()) {
                    if (tipoPubObjEnt.getCodigo().toString().equals(rowKey)) return tipoPubObjEnt;
                }
                return null;
            }

            @Override
            public String getRowKey(TipoPublicoObjetivoEntidadGridDTO procedimiento) {
                return procedimiento.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<TipoPublicoObjetivoEntidadGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<TipoPublicoObjetivoEntidadGridDTO> pagina = maestrasSupService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoPublicoObjetivoEntidadGridDTO> pagina = new Pagina(new ArrayList(), 0);
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
        result.setResult(tipoPublicoObjEntSeleccionadas);
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

    public TipoPublicoObjetivoEntidadGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoPublicoObjetivoEntidadGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoPublicoObjetivoEntidadFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoPublicoObjetivoEntidadFiltro filtro) {
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

    public void borrarTipoPubObjEnt() {
        if (pubobjentGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            tipoPublicoObjEntSeleccionadas.remove(pubobjentGridSeleccionada);
            pubobjentGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public List<TipoPublicoObjetivoEntidadGridDTO> getTipoPublicoObjEntSeleccionadas() {
        return tipoPublicoObjEntSeleccionadas;
    }

    public void setTipoPublicoObjEntSeleccionadas(List<TipoPublicoObjetivoEntidadGridDTO> tipoPublicoObjEntSeleccionadas) {
        this.tipoPublicoObjEntSeleccionadas = tipoPublicoObjEntSeleccionadas;
    }

    public TipoPublicoObjetivoEntidadGridDTO getPubobjentGridSeleccionada() {
        return pubobjentGridSeleccionada;
    }

    public void setPubobjentGridSeleccionada(TipoPublicoObjetivoEntidadGridDTO pubobjentGridSeleccionada) {
        this.pubobjentGridSeleccionada = pubobjentGridSeleccionada;
    }

    public void anadirPubObjEntLista() {
        if (this.datoSeleccionado == null) {
            return;
        }

        borrarDistintoTipo(this.datoSeleccionado.isEmpleadoPublico());

        if (tipoPublicoObjEntSeleccionadas != null && contains(this.datoSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.yaEstaEnLaLista"));
        } else {
            tipoPublicoObjEntSeleccionadas.add(datoSeleccionado);
        }
    }

    /**
     * Borra los tipos asignados que no sean del mismo tipo de empleado publico.
     *
     * @param empleadoPublico
     */
    private void borrarDistintoTipo(boolean empleadoPublico) {
        if (tipoPublicoObjEntSeleccionadas == null || tipoPublicoObjEntSeleccionadas.isEmpty()) {
            return;
        }

        List<TipoPublicoObjetivoEntidadGridDTO> borrar = new ArrayList<>();
        for (TipoPublicoObjetivoEntidadGridDTO tipo : tipoPublicoObjEntSeleccionadas) {
            if (tipo.isEmpleadoPublico() != empleadoPublico) {
                borrar.add(tipo);
            }
        }
        if (!borrar.isEmpty()) {
            this.tipoPublicoObjEntSeleccionadas.removeAll(borrar);
        }
    }

    private boolean contains(TipoPublicoObjetivoEntidadGridDTO dat) {
        boolean contiene = false;
        if (dat != null) {
            if (tipoPublicoObjEntSeleccionadas != null && !tipoPublicoObjEntSeleccionadas.isEmpty()) {
                for (TipoPublicoObjetivoEntidadGridDTO tipo : tipoPublicoObjEntSeleccionadas) {
                    if (tipo.getCodigo().compareTo(dat.getCodigo()) == 0) {
                        contiene = true;
                        break;
                    }
                }
            }
        }
        return contiene;
    }



}
