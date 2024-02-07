package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AlertaServiceFacade;
import es.caib.rolsac2.service.model.AlertaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogConfiguracionAlerta extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogConfiguracionAlerta.class);


    private String id;

    private AlertaDTO data;
    private AlertaDTO dataOriginal;

    @EJB
    private AlertaServiceFacade alertaSupServiceFacade;


    public void load() {
        LOG.debug("init");

        this.setearIdioma();

        data = new AlertaDTO();
        if (this.isModoAlta()) {
            data = AlertaDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            data.setEntidad(sessionBean.getEntidad());
            data.setFechaIni(new Date());
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = alertaSupServiceFacade.findById(Long.valueOf(id), this.getIdioma());
            dataOriginal = data.clone();
        }
    }


    public void guardar() {

        if (!"PER".equals(data.getAmbito())) {
            data.setPerfil(null);
        }

        if (!"UNA".equals(data.getAmbito())) {
            data.setUnidadAdministrativa(null);
        }

        if ("UNA".equals(data.getAmbito())) {
            if (data.getUnidadAdministrativa() == null || data.getUnidadAdministrativa().getCodigo() == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogConfiguracionAlerta.obligatorio.ua"));
                return;
            } else {
                data.getUnidadAdministrativa().setEntidad(sessionBean.getEntidad());
            }
        }

        if ("PER".equals(data.getAmbito()) && data.getPerfil() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogConfiguracionAlerta.obligatorio.perfil"));
            return;
        }

        if (this.data.getCodigo() == null) {

            alertaSupServiceFacade.create(this.data);
        } else {
            alertaSupServiceFacade.update(this.data, this.getIdioma());
        }

        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }

        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    /**
     * Traducir
     */
    public void traducir() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    /**
     * Return dialog traduccion
     *
     * @param event
     */
    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        AlertaDTO datoDTO = (AlertaDTO) respuesta.getResult();

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

    public boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0 || UtilComparador.compareTo(data.getEntidad().getCodigo(), dataOriginal.getEntidad().getCodigo()) != 0 || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0;

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

    public AlertaDTO getData() {
        return data;
    }

    public void setData(AlertaDTO data) {
        this.data = data;
    }


}
