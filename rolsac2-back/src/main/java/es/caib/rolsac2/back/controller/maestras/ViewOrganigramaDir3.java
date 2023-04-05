package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.plugins.dir3.api.Dir3ErrorException;
import es.caib.rolsac2.commons.plugins.dir3.api.model.ParametrosDir3;
import es.caib.rolsac2.service.facade.ProcesoLogServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoTimerServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.facade.integracion.Dir3ServiceFacade;
import es.caib.rolsac2.service.model.UnidadOrganicaDTO;
import es.caib.rolsac2.service.model.types.TypeEstadoDir3;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeTipoProceso;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tree.Tree;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class ViewOrganigramaDir3 extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewOrganigramaDir3.class);

    @EJB
    private UnidadAdministrativaServiceFacade uaService;

    @EJB
    private ProcesoLogServiceFacade procesoLogServiceFacade;

    @EJB
    private Dir3ServiceFacade dir3ServiceFacade;

    @EJB
    private ProcesoTimerServiceFacade procesoTimerServiceFacade;

    private TreeNode rootRolsac;

    private TreeNode rootDir3;

    private TreeNode arbolHistoricos;

    private TreeNode datoSeleccionadoRolsac;

    private TreeNode[] datosSeleccionadosDir3;

    private Date fechaUltimaActualizacion;

    private Boolean cargaArbol = Boolean.FALSE;

    private Boolean showHistorico = Boolean.FALSE;

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewOrganigramaDir3.class);
        try {
            fechaUltimaActualizacion = procesoLogServiceFacade.obtenerFechaUltimaEjecucionCorrecta(TypeTipoProceso.DIR3.toString(), sessionBean.getEntidad().getCodigo());
            PrimeFaces.current().executeScript("document.getElementById('form:btnCarga').click()");
        } catch (Exception e) {
          UtilJSF.redirectJsfPage("/error/procesoDIR3Exception.xhtml");
        }

    }

    public void cargarArbol() {
        UnidadOrganicaDTO unidadRaizDir3 = uaService.obtenerUnidadRaizDir3(sessionBean.getEntidad().getCodigo());
        UnidadOrganicaDTO unidadRaizRolsac = uaService.obtenerUnidadRaizRolsac(sessionBean.getEntidad().getCodigo());
        rootDir3 = new DefaultTreeNode(unidadRaizDir3, null);
        construirArbolDesdeRaizDir3(rootDir3, unidadRaizDir3);
        rootRolsac = new DefaultTreeNode(unidadRaizRolsac, null);
        construirArbolDesdeRaizRolsac(rootRolsac, unidadRaizRolsac);
        cargaArbol = Boolean.TRUE;
    }

    public void construirArbolDesdeRaizDir3(TreeNode raiz, UnidadOrganicaDTO padre) {
        List<UnidadOrganicaDTO> unidadesHijas = uaService.obtenerUnidadesHijasDir3(padre.getCodigoDir3(), sessionBean.getEntidad().getCodigo());
        Collections.sort(unidadesHijas);
        if(unidadesHijas == null || unidadesHijas.isEmpty()) {
            return;
        } else {
            if(padre.getEstado() == null && unidadesHijas.stream().anyMatch(u -> u.getEstado() != null)) {
                padre.setEstado(TypeEstadoDir3.PADRE_CAMBIO);
            }
            for(UnidadOrganicaDTO unidad : unidadesHijas) {
                TreeNode node = new DefaultTreeNode(unidad, raiz);
                construirArbolDesdeRaizDir3(node, unidad);
            }
        }
    }

    public void construirArbolDesdeRaizRolsac(TreeNode raiz, UnidadOrganicaDTO padre) {
        List<UnidadOrganicaDTO> unidadesHijas = uaService.obtenerUnidadesHijasRolsac(padre.getCodigoDir3(), sessionBean.getEntidad().getCodigo());
        Collections.sort(unidadesHijas);
        if(unidadesHijas == null || unidadesHijas.isEmpty()) {
            return;
        } else {
            for(UnidadOrganicaDTO unidad : unidadesHijas) {
                TreeNode node = new DefaultTreeNode(unidad, raiz);
                construirArbolDesdeRaizRolsac(node, unidad);
            }
        }
    }

    public void consultarHistorico(UnidadOrganicaDTO unidadOrganicaDTO) {
        ParametrosDir3 parametrosDir3 = new ParametrosDir3();
        parametrosDir3.setDenominacionCooficial(true);
        parametrosDir3.setCodigo(unidadOrganicaDTO.getCodigoDir3());
        List<UnidadOrganicaDTO> unidadesHistorico = new ArrayList<>();
        try {
            unidadesHistorico = dir3ServiceFacade.obtenerHistoricosFinales(sessionBean.getEntidad().getCodigo(), parametrosDir3);
        } catch (Dir3ErrorException e) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewOrganigramaDir3.errorConsulta"));
            return;
        }
        if(!unidadesHistorico.isEmpty()) {
            unidadOrganicaDTO.setAplicacion("ROLSAC2");
            arbolHistoricos = new DefaultTreeNode(unidadOrganicaDTO, null);
            arbolHistoricos.setExpanded(true);
            construirArbolHistoricos(arbolHistoricos, unidadesHistorico);
            showHistorico = Boolean.TRUE;
            PrimeFaces.current().ajax().update(":form:arbolHistoricos");
        } else {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewOrganigramaDir3.sinHistorico"));
        }
    }

    private void construirArbolHistoricos(TreeNode raiz, List<UnidadOrganicaDTO> unidadesHistorico) {
        if(unidadesHistorico != null) {
            for(UnidadOrganicaDTO unidad : unidadesHistorico) {
                unidad.setAplicacion("DIR3CAIB");
                TreeNode node = new DefaultTreeNode(unidad, raiz);
                ParametrosDir3 parametrosDir3 = new ParametrosDir3();
                parametrosDir3.setDenominacionCooficial(true);
                parametrosDir3.setCodigo(unidad.getCodigoDir3());
                List<UnidadOrganicaDTO> unidades = new ArrayList<>();
                try {
                    unidades = dir3ServiceFacade.obtenerHistoricosFinales(sessionBean.getEntidad().getCodigo(), parametrosDir3);
                    construirArbolHistoricos(node, unidades);
                } catch (Dir3ErrorException e) {
                    unidades.clear();
                }
            }
        }
    }

    public void filtrarDir3() {
        String script = "document.getElementById('form:dataTable1:dir3Col:filter').value='";
        if(datosSeleccionadosDir3.length > 0) {
            for(TreeNode dato : datosSeleccionadosDir3) {
                if(!((UnidadOrganicaDTO)dato.getData()).getAplicacion().equals("ROLSAC2")) {
                    script += ((UnidadOrganicaDTO)dato.getData()).getCodigoDir3() + ";";
                }
            }
            script += "';";
            PrimeFaces.current().executeScript(script);
            PrimeFaces.current().executeScript("PF('arbolDir3').filter()");
        }
    }

    public boolean filtroDir3Tree(Object value, Object filter, Locale locale) {
        if(filter instanceof String && value instanceof String) {
            if(filtroDir3((String) filter)) {
                List<String> unidadesFiltro = List.of(((String) filter).split(";"));
                String dir3 = findParenthesis((String) value);
                if(unidadesFiltro.contains(dir3)) {
                    return true;
                }
            } else {
                return ((String) value).contains((String) filter);
            }
        }
        return false;
    }

    private boolean filtroDir3(String filtro) {
        Pattern p = Pattern.compile("[A-Z]{1}[0-9]{8}");
        Matcher m = p.matcher(filtro);
        return m.find();
    }

    private String findParenthesis(String exp){
        String dir3 = "";
        Pattern p = Pattern.compile("\\(.+?\\)");
        Matcher m = p.matcher(exp);
        if(m.find()) {
            dir3 = exp.substring(m.start()+1, m.start()+10);
        }
        return dir3;
    }


    public void actualizarOrganigramaDir3() {
        procesoTimerServiceFacade.procesadoManual(TypeTipoProceso.DIR3.toString(), sessionBean.getEntidad().getCodigo());
    }

    public TreeNode getRootRolsac() {
        return rootRolsac;
    }

    public void setRootRolsac(TreeNode rootRolsac) {
        this.rootRolsac = rootRolsac;
    }

    public TreeNode getRootDir3() {
        return rootDir3;
    }

    public void setRootDir3(TreeNode rootDir3) {
        this.rootDir3 = rootDir3;
    }

    public TreeNode getDatoSeleccionadoRolsac() {
        return datoSeleccionadoRolsac;
    }

    public void setDatoSeleccionadoRolsac(TreeNode datoSeleccionadoRolsac) {
        this.datoSeleccionadoRolsac = datoSeleccionadoRolsac;
    }

    public TreeNode[] getDatosSeleccionadosDir3() {
        return datosSeleccionadosDir3;
    }

    public void setDatosSeleccionadosDir3(TreeNode[] datosSeleccionadosDir3) {
        this.datosSeleccionadosDir3 = datosSeleccionadosDir3;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public TreeNode getArbolHistoricos() {
        return arbolHistoricos;
    }

    public void setArbolHistoricos(TreeNode arbolHistoricos) {
        this.arbolHistoricos = arbolHistoricos;
    }

    public Boolean getCargaArbol() {
        return cargaArbol;
    }

    public void setCargaArbol(Boolean cargaArbol) {
        this.cargaArbol = cargaArbol;
    }

    public Boolean getShowHistorico() {
        return showHistorico;
    }

    public void setShowHistorico(Boolean showHistorico) {
        this.showHistorico = showHistorico;
    }

}
