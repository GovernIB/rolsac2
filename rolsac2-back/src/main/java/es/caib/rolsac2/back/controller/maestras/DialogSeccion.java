package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.SeccionServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.SeccionDTO;
import es.caib.rolsac2.service.model.SeccionGridDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogSeccion extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeccion.class);

    private String id = "";

    private SeccionDTO data;

    private List<EntidadDTO> entidadesActivas;

    private List<SeccionDTO> padreSeleccionado;

    private String identificadorOld;

    private String seccionActual;

    private String textoValor;

    @Inject
    private SessionBean sessionBean;

    @EJB
    private SeccionServiceFacade seccionServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = SeccionDTO.createInstance();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();

        if (this.isModoAlta()) {
            data.setEntidad(sessionBean.getEntidad());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = seccionServiceFacade.findById((Long.valueOf(id)));
            this.identificadorOld = data.getIdentificador();
        }
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }
        if (this.data.getPadre() != null && this.data.getPadre().getCodigo() == null) {
            this.data.setPadre(null);
        }
        if (this.data.getCodigo() == null) {
            seccionServiceFacade.create(this.data);
        } else {
            seccionServiceFacade.update(this.data);
        }

        //Retornamos resultado
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }


    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && seccionServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && seccionServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }
        return true;
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
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

    private void setearTextos(SeccionGridDTO seccion) {
        if (seccion != null) {
            String idioma = sessionBean.getLang();
            textoValor = seccion.getNombre().getTraduccion(idioma);

        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        // Verificamos si se ha modificado
        SeccionGridDTO seccionSel = (SeccionGridDTO) respuesta.getResult();
        if (seccionSel != null) {
            setearTextos(seccionSel);
        }
    }

    public void abrirDialogSelect() {
        if (data == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("dialogSelectSeccion", TypeModoAcceso.ALTA, params, true, 950, 300);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SeccionDTO getData() {
        return data;
    }

    public void setData(SeccionDTO data) {
        this.data = data;
    }

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
    }

    public List<SeccionDTO> getPadreSeleccionado() {
        return padreSeleccionado;
    }

    public void setPadreSeleccionado(List<SeccionDTO> padreSeleccionado) {
        this.padreSeleccionado = padreSeleccionado;
    }

    public String getIdentificadorOld() {
        return identificadorOld;
    }

    public void setIdentificadorOld(String identificadorOld) {
        this.identificadorOld = identificadorOld;
    }

    public String getSeccionActual() {
        return seccionActual;
    }

    public void setSeccionActual(String seccionActual) {
        this.seccionActual = seccionActual;
    }

    public String getTextoValor() {
        return textoValor;
    }

    public void setTextoValor(String textoValor) {
        this.textoValor = textoValor;
    }
}
