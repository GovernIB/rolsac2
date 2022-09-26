package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.TipoUnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class DialogUnidadAdministrativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogUnidadAdministrativa.class);

    private String id = "";

    private UnidadAdministrativaDTO data;

    private List<EntidadDTO> entidadesActivas;

    private List<UnidadAdministrativaDTO> padreSeleccionado;

    private List<TipoUnidadAdministrativaDTO> tipos;

    private List<TipoSexoDTO> tiposSexo;

    private String identificadorOld;

    private String modoAccesoAux;

    @Inject
    private SessionBean sessionBean;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private TipoUnidadAdministrativaServiceFacade tipoUnidadAdministrativaServiceFacade;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = new UnidadAdministrativaDTO();

        if(this.getModoAcceso()==null && modoAccesoAux !=null) {
        	this.setModoAcceso(modoAccesoAux);
        }

        tiposSexo = unidadAdministrativaServiceFacade.findTipoSexo();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();
        tipos = tipoUnidadAdministrativaServiceFacade.findTipo();

        if (this.isModoAlta()) {
            data = new UnidadAdministrativaDTO();
            data.setEntidad(sessionBean.getEntidad());
            data.setPadre(sessionBean.getUnidadActiva());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = unidadAdministrativaServiceFacade.findById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
        }

    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            unidadAdministrativaServiceFacade.create(this.data);
        } else {
            unidadAdministrativaServiceFacade.update(this.data);
        }

        // Retornamos resultados
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
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

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && unidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && unidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getEmail()) && !ValidacionTipoUtils.esEmailValido(this.data.getEmail())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.email.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getTelefono())
                && !ValidacionTipoUtils.esTelefonoValido(this.data.getTelefono())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.telefono.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getFax()) && !ValidacionTipoUtils.esNumerico(this.data.getFax())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.fax.novalido"), true);
            return false;
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UnidadAdministrativaDTO getData() {
        return data;
    }

    public void setData(UnidadAdministrativaDTO data) {
        this.data = data;
    }

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
    }

    public List<UnidadAdministrativaDTO> getPadreSeleccionado() {
        return padreSeleccionado;
    }

    public void setPadreSeleccionado(List<UnidadAdministrativaDTO> padreSeleccionado) {
        this.padreSeleccionado = padreSeleccionado;
    }

    public List<TipoUnidadAdministrativaDTO> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoUnidadAdministrativaDTO> tipoSeleccionado) {
        this.tipos = tipoSeleccionado;
    }

    public List<TipoSexoDTO> getTiposSexo() {
        return tiposSexo;
    }

    public void setTiposSexo(List<TipoSexoDTO> tiposSexo) {
        this.tiposSexo = tiposSexo;
    }

	public String getModoAccesoAux() {
		return modoAccesoAux;
	}

	public void setModoAccesoAux(String modoAccesoAux) {
		this.modoAccesoAux = modoAccesoAux;
	}

}
