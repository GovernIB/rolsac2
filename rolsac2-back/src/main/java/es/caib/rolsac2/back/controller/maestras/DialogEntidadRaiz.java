package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.EntidadRaizDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

@Named
@ViewScoped
public class DialogEntidadRaiz extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEntidadRaiz.class);
    private static final long serialVersionUID = -978862425481233306L;

    @EJB
    private AdministracionEntServiceFacade administracionEntServiceFacade;

    private String id;
    private EntidadRaizDTO data;
    private EntidadRaizDTO dataOriginal;

    public void load() {
        this.setearIdioma();
        LOG.debug("init");

        data = new EntidadRaizDTO();

        if (this.isModoAlta()) {
            data.setUa(sessionBean.getUnidadActiva());
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionEntServiceFacade.findEntidadRaizById(Long.valueOf(id));
            dataOriginal = data.clone();
        }
    }

    public void guardar() {
        if (this.data.getCodigo() == null) {
            administracionEntServiceFacade.createEntidadRaiz(this.data);
        } else {
            administracionEntServiceFacade.updateEntidadRaiz(this.data);
        }

        // Retornamos resultados
        final DialogResult result = new DialogResult();
        //TODO Se produce un error por eso se pone que si null, entonces poner alta
        if (this.getModoAcceso() == null) {
            result.setModoAcceso(TypeModoAcceso.ALTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0
                || UtilComparador.compareTo(data.getUa().getCodigo(), dataOriginal.getUa().getCodigo()) != 0
                || UtilComparador.compareTo(data.getUser(), dataOriginal.getUser()) != 0
                || UtilComparador.compareTo(data.getPwd(), dataOriginal.getPwd()) != 0;
    }

    public void cerrarDefinitivo() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntidadRaizDTO getData() {
        return data;
    }

    public void setData(EntidadRaizDTO data) {
        this.data = data;
    }

    public EntidadRaizDTO getDataOriginal() {
        return dataOriginal;
    }

    public void setDataOriginal(EntidadRaizDTO dataOriginal) {
        this.dataOriginal = dataOriginal;
    }
}
