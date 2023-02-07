package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.exception.ServiceException;
import es.caib.rolsac2.service.facade.TemaServiceFacade;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.filtro.TemaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class ViewTema extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTema.class);

    private LazyDataModel<TemaGridDTO> lazyModel;

    @EJB
    private TemaServiceFacade temaServiceFacade;

    private TreeNode datoSeleccionado;

    private TemaFiltro filtro;

    private TreeNode root;

    public LazyDataModel<TemaGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewTema.class);
        LOG.debug("load");
        filtro = new TemaFiltro();
        filtro.setIdioma(sessionBean.getLang());
        construirArbol();
    }

    private void construirArbolDesdeHoja(TemaGridDTO hoja, TreeNode arbol) {
        DefaultTreeNode nodo = null;
        List<TemaGridDTO> hijos = temaServiceFacade.getGridHijos(hoja.getCodigo(), sessionBean.getLang());
        for (TemaGridDTO hijo : hijos) {
            nodo = new DefaultTreeNode(hijo, arbol);
            this.construirArbolDesdeHoja(hijo, nodo);
        }
    }

    private void construirArbol() {
        root = new DefaultTreeNode(new TemaGridDTO(), null);
        List<TemaGridDTO> temasPadre = temaServiceFacade.getGridRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
        for(TemaGridDTO tema : temasPadre) {
            TreeNode nodo = new DefaultTreeNode(tema, root);
            this.construirArbolDesdeHoja(tema, nodo);
        }
    }

    public void nuevoTema() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTema() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTema() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.construirArbol();
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null
                && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            TemaGridDTO temaSeleccionado = (TemaGridDTO) this.datoSeleccionado.getData();
            params.put(TypeParametroVentana.ID.toString(), temaSeleccionado.getCodigo().toString());
        } else if(this.datoSeleccionado != null && modoAcceso.equals(TypeModoAcceso.ALTA)) {
            params.put("padreSeleccionado", ((TemaGridDTO) this.datoSeleccionado.getData()).getCodigo().toString());
        }
        UtilJSF.openDialog("dialogTema", modoAcceso, params, true, 850, 320);
    }

    public void borrarTema() {
        try {
            if (datoSeleccionado == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
            } else {
                TemaGridDTO temaSeleccionado = (TemaGridDTO) this.datoSeleccionado.getData();
                temaServiceFacade.delete(temaSeleccionado.getCodigo());
                construirArbol();
            }
        } catch (ServiceException e) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.hijos.relacionados"));
        }

    }

    public TreeNode getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TreeNode datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TemaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TemaFiltro filtro) {
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

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
}
