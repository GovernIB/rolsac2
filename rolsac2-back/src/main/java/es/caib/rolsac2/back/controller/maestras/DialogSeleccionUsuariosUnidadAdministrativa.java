package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;
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
public class DialogSeleccionUsuariosUnidadAdministrativa extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionUsuariosUnidadAdministrativa.class);

    private LazyDataModel<UsuarioGridDTO> lazyModel;

    @EJB
    private AdministracionEntServiceFacade administracionEntService;

    private UsuarioGridDTO datoSeleccionado;

    private UsuarioFiltro filtro;

    private List<UsuarioGridDTO> usuarioUASeleccionados;

    private UsuarioGridDTO usuarioGridSeleccionada;

    private UnidadAdministrativaDTO uaActual;

    public LazyDataModel<UsuarioGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new UsuarioFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());

        // Generamos una búsqueda
        buscar();

        uaActual = (UnidadAdministrativaDTO) UtilJSF.getValorMochilaByKey("unidadAdministrativa");
        usuarioUASeleccionados = ((List<UsuarioGridDTO>) UtilJSF.getValorMochilaByKey("usuariosUnidadAdministrativa"));
        if (usuarioUASeleccionados == null) {
            usuarioUASeleccionados = new ArrayList<>();
        }
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<UsuarioGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public UsuarioGridDTO getRowData(String rowKey) {
                for (UsuarioGridDTO usuario : (List<UsuarioGridDTO>) getWrappedData()) {
                    if (usuario.getCodigo().toString().equals(rowKey)) return usuario;
                }
                return null;
            }

            @Override
            public String getRowKey(UsuarioGridDTO objeto) {
                return objeto.getCodigo().toString();
            }


            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<UsuarioGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<UsuarioGridDTO> pagina = administracionEntService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<UsuarioGridDTO> pagina = new Pagina(new ArrayList(), 0);
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
        result.setResult(usuarioUASeleccionados);
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

    public void anyadirUsuario() {
        if (this.datoSeleccionado == null) {
            return;
        }
        if (usuarioUASeleccionados != null && contains(this.datoSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.yaEstaEnLaLista"));
        } else {
            usuarioUASeleccionados.add(datoSeleccionado);
        }
    }

    public void borrarUsuario() {
        if (usuarioGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {

            usuarioUASeleccionados.remove(usuarioGridSeleccionada);
            usuarioGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
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

    private boolean contains(UsuarioGridDTO dat) {
        boolean contiene = false;
        if (dat != null) {
            if (usuarioUASeleccionados != null && !usuarioUASeleccionados.isEmpty()) {
                for (UsuarioGridDTO usu : usuarioUASeleccionados) {
                    if (usu.getCodigo().compareTo(dat.getCodigo()) == 0) {
                        contiene = true;
                        break;
                    }
                }
            }
        }
        return contiene;
    }

    public UsuarioGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(UsuarioGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public UsuarioFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(UsuarioFiltro filtro) {
        this.filtro = filtro;
    }

    public List<UsuarioGridDTO> getUsuarioUASeleccionados() {
        return usuarioUASeleccionados;
    }

    public void setUsuarioUASeleccionados(List<UsuarioGridDTO> usuarioUASeleccionados) {
        this.usuarioUASeleccionados = usuarioUASeleccionados;
    }

    public UsuarioGridDTO getUsuarioGridSeleccionada() {
        return usuarioGridSeleccionada;
    }

    public void setUsuarioGridSeleccionada(UsuarioGridDTO usuarioGridSeleccionada) {
        this.usuarioGridSeleccionada = usuarioGridSeleccionada;
    }

    public UnidadAdministrativaDTO getUaActual() {
        return uaActual;
    }

    public void setUaActual(UnidadAdministrativaDTO uaActual) {
        this.uaActual = uaActual;
    }
}
