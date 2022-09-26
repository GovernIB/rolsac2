package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
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
public class DialogNormativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogNormativa.class);

    private String id = "";

    private NormativaDTO data;

    private List<EntidadDTO> entidadesActivas;

    private List<AfectacionDTO> afectacion;

    private List<TipoNormativaDTO> tipoNormativa;

    private List<TipoBoletinDTO> tipoBoletin;

    private List<BoletinOficialDTO> boletinOficial;

    private String identificadorOld;

    @Inject
    private SessionBean sessionBean;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = new NormativaDTO();

        tipoNormativa = maestrasSupServiceFacade.findTipoNormativa();
        tipoBoletin = maestrasSupServiceFacade.findBoletines();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();
        afectacion = normativaServiceFacade.findAfectacion();
        boletinOficial = normativaServiceFacade.findBoletinOficial();

        if (this.isModoAlta()) {
            data = new NormativaDTO();
            data.setEntidad(sessionBean.getEntidad());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = normativaServiceFacade.findById(Long.valueOf(id));
        }
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }
        if (this.data.getCodigo() == null) {
            normativaServiceFacade.create(this.data);
        } else {
            normativaServiceFacade.update(this.data);
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

    public boolean verificarGuardar() {
        if (Objects.nonNull(this.data.getUrlBoletin()) && !ValidacionTipoUtils.esUrlValido(this.data.getUrlBoletin())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.url.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getNumero()) && !ValidacionTipoUtils.esEntero(this.data.getNumero())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.numero.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getNumeroBoletin()) && !ValidacionTipoUtils.esEntero(this.data.getNumero())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.numero.novalido"), true);
            return false;
        }

        return true;
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

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NormativaDTO getData() {
        return data;
    }

    public void setData(NormativaDTO data) {
        this.data = data;
    }

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
    }

    public List<AfectacionDTO> getAfectacion() {
        return afectacion;
    }

    public void setAfectacion(List<AfectacionDTO> afectacion) {
        this.afectacion = afectacion;
    }

    public List<TipoNormativaDTO> getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(List<TipoNormativaDTO> tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public List<TipoBoletinDTO> getTipoBoletin() {
        return tipoBoletin;
    }

    public void setTipoBoletin(List<TipoBoletinDTO> tipoBoletin) {
        this.tipoBoletin = tipoBoletin;
    }

    public List<BoletinOficialDTO> getBoletinOficial() {
        return boletinOficial;
    }

    public void setBoletinOficial(List<BoletinOficialDTO> boletinOficial) {
        this.boletinOficial = boletinOficial;
    }
}
