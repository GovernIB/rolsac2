package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.model.*;
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
import java.util.*;

@Named
@ViewScoped
public class DialogSeleccionNormativaAfectacion extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionNormativaAfectacion.class);

    private LazyDataModel<NormativaGridDTO> lazyModel;

    private String id = "";

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    private NormativaGridDTO datoSeleccionado;

    private NormativaFiltro filtro;

    private List<NormativaGridDTO> normativasSeleccionadas;

    private NormativaGridDTO normativaGridSeleccionada;

    public LazyDataModel<NormativaGridDTO> getLazyModel() {
        return lazyModel;
    }

    private String modoAccesoNormativa;

    private List<AfectacionDTO> afectacionesRelacionadas;
    private List<AfectacionDTO> afectacionesRelacionadasAux;

    private List<TipoAfectacionDTO> tiposAfectacion;

    private TipoAfectacionDTO tipoAfectacionSeleccionado;

    private AfectacionDTO afectacionSeleccionada;

    private NormativaDTO normativaAfectada;


    public void load() {
        LOG.debug("load");
        this.setearIdioma();
        modoAccesoNormativa = (String) UtilJSF.getDialogParam("modoAccesoNormativa");
        List<AfectacionDTO> afectaciones = (List<AfectacionDTO>) UtilJSF.getValorMochilaByKey("afectacionesNormativa");

        if(modoAccesoNormativa != null && modoAccesoNormativa.equals(TypeModoAcceso.EDICION.toString())) {
            afectacionesRelacionadas = normativaServiceFacade.findAfectacionesByNormativa(Long.valueOf(id));
            afectacionesRelacionadasAux = afectaciones == null ? new ArrayList<>() : afectaciones;
            normativaAfectada = normativaServiceFacade.findById(Long.valueOf(id));
            if(afectacionesRelacionadas != null) {
                for(AfectacionDTO afectacionDTO : afectacionesRelacionadas) {
                    afectacionDTO.setCodigoTabla(UUID.randomUUID().toString());
                }
            }
        } else {
            afectacionesRelacionadas = afectaciones == null ? new ArrayList<>() : afectaciones;
        }


        tiposAfectacion = maestrasSupServiceFacade.findTipoAfectaciones();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new NormativaFiltro();
        filtro.setIdioma(sessionBean.getLang());

        // Generamos una búsqueda
        buscar();
        //  normativasSeleccionadas.add(new NormativaGridDTO());
        // normativasSeleccionadas = new ArrayList<>(
        UtilJSF.vaciarMochila();

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

        if (modoAccesoNormativa!= null && modoAccesoNormativa.equals(TypeModoAcceso.EDICION.toString()) && isModoAlta()) {
            for(AfectacionDTO afectacionDTO : afectacionesRelacionadas) {
                if(afectacionDTO.getCodigo() == null) {
                    normativaServiceFacade.createAfectacion(afectacionDTO);
                } else if(hasChanged(afectacionDTO)) {
                    normativaServiceFacade.updateAfectacion(afectacionDTO);
                }
            }
        }

        result.setResult(afectacionesRelacionadas);

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

    public void borrarAfectacion() {
        if (afectacionSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            if(modoAccesoNormativa.equals(TypeModoAcceso.EDICION.toString()) && afectacionesRelacionadasAux.contains(afectacionSeleccionada)) {
                normativaServiceFacade.deleteAfectacion(afectacionSeleccionada.getCodigo());
                afectacionSeleccionada = null;
                addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
            } else {
                afectacionesRelacionadas.remove(afectacionSeleccionada);
                afectacionSeleccionada = null;
                addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
            }
        }
    }

    public void crearAfectacion() {
        if (this.datoSeleccionado == null || this.tipoAfectacionSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.yaEstaEnLaLista"));
            return;
        }
        if (afectacionesRelacionadas != null && contains(this.datoSeleccionado, this.tipoAfectacionSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.yaEstaEnLaLista"));
        } else {
            if (afectacionesRelacionadas == null) {
                afectacionesRelacionadas = new ArrayList<>();
            }
            AfectacionDTO afectacionDTO = new AfectacionDTO();
            afectacionDTO.setTipo(tipoAfectacionSeleccionado);
            afectacionDTO.setNormativaOrigen(normativaServiceFacade.findById(this.datoSeleccionado.getCodigo()).convertDTOtoGridDTO());
            if(modoAccesoNormativa.equals(TypeModoAcceso.EDICION.toString())) {
                afectacionDTO.setNormativaAfectada(normativaAfectada.convertDTOtoGridDTO());
            }
            afectacionDTO.setCodigoTabla(UUID.randomUUID().toString());
            afectacionesRelacionadas.add(afectacionDTO);
        }
    }

    public boolean esPosibleAnyadir() {
        if(datoSeleccionado == null || tipoAfectacionSeleccionado == null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean contains(NormativaGridDTO dat, TipoAfectacionDTO tipo) {
        boolean contiene = false;
        if (dat != null) {
            if (afectacionesRelacionadas != null && !afectacionesRelacionadas.isEmpty()) {
                for (AfectacionDTO afect : afectacionesRelacionadas) {
                    if (afect.getNormativaOrigen().getCodigo().compareTo(dat.getCodigo()) == 0
                            && afect.getTipo().getCodigo().compareTo(tipo.getCodigo()) == 0) {
                        contiene = true;
                        break;
                    }
                }
            }
        }
        return contiene;
    }

    private boolean hasChanged(AfectacionDTO afectacionDTO) {
        boolean changed = true;
        for(AfectacionDTO afect : afectacionesRelacionadasAux) {
            if(afect.equals(afectacionDTO)) {
                changed = false;
            }
        }
        return changed;
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

    public String getId() {  return id; }

    public void setId(String id) { this.id = id; }

    public List<AfectacionDTO> getAfectacionesRelacionadas() {
        return afectacionesRelacionadas;
    }

    public void setAfectacionesRelacionadas(List<AfectacionDTO> afectacionesRelacionadas) {
        this.afectacionesRelacionadas = afectacionesRelacionadas;
    }

    public List<TipoAfectacionDTO> getTiposAfectacion() {
        return tiposAfectacion;
    }

    public void setTiposAfectacion(List<TipoAfectacionDTO> tiposAfectacion) {
        this.tiposAfectacion = tiposAfectacion;
    }

    public TipoAfectacionDTO getTipoAfectacionSeleccionado() {
        return tipoAfectacionSeleccionado;
    }

    public void setTipoAfectacionSeleccionado(TipoAfectacionDTO tipoAfectacionSeleccionado) {
        this.tipoAfectacionSeleccionado = tipoAfectacionSeleccionado;
    }

    public AfectacionDTO getAfectacionSeleccionada() {
        return afectacionSeleccionada;
    }

    public void setAfectacionSeleccionada(AfectacionDTO afectacionSeleccionada) {
        this.afectacionSeleccionada = afectacionSeleccionada;
    }
}
