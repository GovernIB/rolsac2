package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
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
public class
ViewUsuario extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewUsuario.class);

    //List<String> idiomas;

    private LazyDataModel<UsuarioGridDTO> lazyModel;

    @EJB
    AdministracionEntServiceFacade administracionEntService;

    private UsuarioGridDTO datoSeleccionado;

    private UsuarioFiltro filtro;

    public LazyDataModel<UsuarioGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        LOG.debug("load");

        filtro = new UsuarioFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());

        LOG.debug("Rol admin: " + this.isUserRoleRSCAdmin());
        LOG.debug("Rol user: " + this.isUserRoleRSCUser());
        LOG.debug("Rol user: " + this.isUserRoleRSCMentira());
        LOG.debug("Username: " + this.getUserName());

        //idiomas = Arrays.asList("es", "ca");

        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public UsuarioGridDTO getRowData(String rowKey) {
                for (UsuarioGridDTO pers : (List<UsuarioGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(UsuarioGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<UsuarioGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                             Map<String, FilterMeta> filterBy) {
                try {
                    /*
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")){
                        filtro.setOrderBy(sortField);
                    }*/
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
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

    public void nuevoUsuario() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarUsuario() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarUsuario() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null
                && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogUsuario", modoAcceso, params, true, 850, 400);
    }

    public void borrarUsuario() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            administracionEntService.deleteUsuario(datoSeleccionado.getCodigo());
        }
    }

    public void setDatoSeleccionado(UsuarioGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public UsuarioGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public UsuarioFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(UsuarioFiltro filtro) {
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
}
