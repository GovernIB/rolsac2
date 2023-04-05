package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.TemaServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
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
public class DialogTema extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTema.class);

    private String id = "";

    private TemaDTO data;

    private TemaDTO dataOriginal;

    private List<EntidadDTO> entidadesActivas;

    private List<TemaDTO> padreSeleccionado;

    private String identificadorOld;

    @Inject
    private SessionBean sessionBean;

    @EJB
    TemaServiceFacade temaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = TemaDTO.createInstance();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();
        String idPadre = (String) UtilJSF.getDialogParam("padreSeleccionado");

        if (this.isModoAlta()) {
            data.setEntidad(sessionBean.getEntidad());
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            if(idPadre != null) {
                TemaDTO padreDefecto = temaServiceFacade.findById(Long.valueOf(idPadre));
                data.setTemaPadre(padreDefecto);
            } else {
                data.setTemaPadre(new TemaDTO());
            }
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = temaServiceFacade.findById((Long.valueOf(id)));
            this.identificadorOld = data.getIdentificador();
            dataOriginal = data.clone();
        }
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }
        if (this.data.getTemaPadre() != null && this.data.getTemaPadre().getCodigo() == null) {
            this.data.setTemaPadre(null);
        }

        if (this.data.getCodigo() == null) {
            temaServiceFacade.create(this.data);
        } else {
            if(this.data.getTemaPadre() != null && this.data.getTemaPadre().getCodigo() != null) {
                if(this.data.getTemaPadre().getMathPath() == null) {
                    this.data.setMathPath(this.data.getTemaPadre().getCodigo().toString());
                } else {
                    String path = this.data.getTemaPadre().getMathPath();
                    path += ";" + this.data.getTemaPadre().getCodigo();
                }
            }
            temaServiceFacade.update(this.data, this.getIdioma());

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

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        TemaDTO datoDTO = (TemaDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDescripcion(datoDTO.getDescripcion());
        }
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
                || UtilComparador.compareTo(data.getIdentificador(), dataOriginal.getIdentificador()) != 0
                || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0
                || (data.getTemaPadre() != null && dataOriginal.getTemaPadre() != null && UtilComparador.compareTo(data.getTemaPadre().getCodigo(), dataOriginal.getTemaPadre().getCodigo()) != 0)
                || ((data.getTemaPadre() == null || dataOriginal.getTemaPadre() == null) && (data.getTemaPadre() != null || dataOriginal.getTemaPadre() != null));
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

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && temaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && temaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getDescripcion(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogPlatTramitElectronica.descripcion", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
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

    public TemaDTO getData() {
        return data;
    }

    public void setData(TemaDTO data) {
        this.data = data;
    }

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
    }

    public List<TemaDTO> getPadreSeleccionado() {
        return padreSeleccionado;
    }

    public void setPadreSeleccionado(List<TemaDTO> padreSeleccionado) {
        this.padreSeleccionado = padreSeleccionado;
    }
}
