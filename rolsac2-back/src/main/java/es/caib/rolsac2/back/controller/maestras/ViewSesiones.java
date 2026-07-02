package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.exception.ServiceException;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.SesionDTO;
import es.caib.rolsac2.service.model.UsuarioDTO;
import es.caib.rolsac2.service.model.filtro.SesionFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.event.SelectEvent;
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
public class ViewSesiones extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewSesiones.class);

    private LazyDataModel<SesionDTO> lazyModel;

    @EJB
    private SystemServiceFacade systemServiceFacade;

    @EJB
    private AdministracionEntServiceFacade administracionEntServiceFacade;

    private SesionDTO datoSeleccionado;

    private SesionFiltro filtro;

    public LazyDataModel<SesionDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewSesiones.class);
        LOG.debug("load");
        filtro = new SesionFiltro();

        // orden inicial al abrir la vista
        filtro.setOrderBy("fechaUltimaSesion");
        filtro.setAscendente(false); // DESC
        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscarAvanzada() {
        System.out.println();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<SesionDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public SesionDTO getRowData(String rowKey) {
                for (SesionDTO sesion : (List<SesionDTO>) getWrappedData()) {
                    if (sesion.getIdUsuario().toString().equals(rowKey)) return sesion;
                }
                return null;
            }

            @Override
            public String getRowKey(SesionDTO sesion) {
                return sesion.getIdUsuario().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<SesionDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {

                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    Pagina<SesionDTO> pagina = new Pagina(new ArrayList(), 0);
                    if (getFiltroIdentificador() != null && !getFiltroIdentificador().equals("")) {

                        UsuarioDTO usuario = administracionEntServiceFacade.findUsuarioByIdentificador(getFiltroIdentificador());
                        if (usuario != null) {
                            filtro.setIdUsuario(usuario.getCodigo());
                            pagina = systemServiceFacade.findByFiltro(filtro);
                            setRowCount((int) pagina.getTotal());

                        } else {
                            LOG.error("No existe ese identificador de usuario");
                            pagina = new Pagina(new ArrayList(), 0);
                            setRowCount((int) pagina.getTotal());
                            return pagina.getItems();
                        }

                    } else {
                        pagina = systemServiceFacade.findAllSesiones();
                    }
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<SesionDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }


    public void borrarSesion() {
        try {
            if (datoSeleccionado == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
            } else {
                systemServiceFacade.deleteSesion(datoSeleccionado.getIdUsuario());
            }
        } catch (ServiceException e) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.errorEliminar"));
        }

    }

    public void borrarTodasSesiones() {
        try {

            systemServiceFacade.deleteAllSesion();

        } catch (ServiceException e) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.errorEliminar"));
        }

    }


    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    public SesionDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(SesionDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public SesionFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(SesionFiltro filtro) {
        this.filtro = filtro;
    }

    public String getFiltroIdentificador() {
        if (Objects.nonNull(this.filtro)) {
            return this.filtro.getIdentificador();
        }
        return "";
    }

    public void setFiltroIdentificador(String identificador) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setIdentificador(identificador);
        }

    }


}
