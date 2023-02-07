package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.AuditoriaIdioma;
import es.caib.rolsac2.service.model.auditoria.AuditoriaValorCampo;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.utils.AuditoriaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DialogAuditoria extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogAuditoria.class);

    private String id;
    private String tipo;


    private List<AuditoriaGridDTO> data;


    private AuditoriaGridDTO datoSeleccionado;

    private List<AuditoriaCambio> cambios;
    @EJB
    ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    public void load() {
        LOG.debug("init");
        this.setearIdioma();
        if (tipo.equals("PROC")) {
            data = procedimientoServiceFacade.findProcedimientoAuditoriasById(Long.valueOf(id));
        } else {
            data = unidadAdministrativaServiceFacade.findUaAuditoriasById(Long.valueOf(id));
        }

        if (data != null && !data.isEmpty()) {
            datoSeleccionado = data.get(0);
            cambios = datoSeleccionado.getCambios();
        }
    }

    public String getMensajeTraducido(AuditoriaCambio cambio) {

        Object[] valores = null;
        if (cambio.getValoresModificados() != null && cambio.getValoresModificados().size() >= 1) {
            AuditoriaValorCampo valorCampo = cambio.getValoresModificados().get(0);
            int total = 0;
            if (valorCampo.getIdioma() != null) {
                total++;
            }
            if (valorCampo.getValorAnterior() != null) {
                total++;
            }
            if (valorCampo.getValorNuevo() != null) {
                total++;
            }
            if (valorCampo.getElemento() != null) {
                total++;
            }

            if (total > 0) {
                valores = new Object[total];
                int posicion = 0;
                if (valorCampo.getElemento() != null) {
                    valores[posicion] = valorCampo.getElemento();
                    posicion++;
                }
                if (valorCampo.getIdioma() != null) {
                    valores[posicion] = getTexto(valorCampo.getIdioma());
                    posicion++;
                }
                if (valorCampo.getValorAnterior() != null) {
                    valores[posicion] = AuditoriaUtil.getValor(valorCampo.getValorAnterior());
                    posicion++;
                }
                if (valorCampo.getValorNuevo() != null) {
                    valores[posicion] = AuditoriaUtil.getValor(valorCampo.getValorNuevo());
                    posicion++;
                }
            }

        }

        if (valores != null) {
            return this.getLiteral(cambio.getIdCampo(), valores);
        } else {
            return this.getLiteral(cambio.getIdCampo());
        }
    }

    private String getTexto(AuditoriaIdioma auditoriaIdioma) {
        String lang = UtilJSF.getSessionBean().getLang();
        if (lang == null) {
            return "";
        }
        switch (auditoriaIdioma) {
            case ALEMAN:
                return "ca".equals(lang) ? "alemany" : "alemán";
            case CASTELLANO:
                return "ca".equals(lang) ? "castellà" : "castellano";
            case CATALAN:
                return "ca".equals(lang) ? "català" : "catalán";
            case FRANCES:
                return "ca".equals(lang) ? "francès" : "francés";
            case INGLES:
                return "ca".equals(lang) ? "anglès" : "inglés";
            case ITALIANO:
                return "ca".equals(lang) ? "italià" : "italiano";
            case NO_IDIOMA:
                return "";
        }
        return "";
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

    private void actualizarCambios() {
        if (this.datoSeleccionado == null) {
            cambios = new ArrayList<>();
        } else {
            cambios = datoSeleccionado.getCambios();
        }
    }

    public void selectDato() {
        actualizarCambios();
    }

    public void unselectDato() {
        actualizarCambios();
    }

    public List<AuditoriaCambio> getCambios() {
        return cambios;
    }

    public void setCambios(List<AuditoriaCambio> cambios) {
        this.cambios = cambios;
    }

    public void eventoUnselect() {
        this.datoSeleccionado = null;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AuditoriaGridDTO> getData() {
        return data;
    }

    public void setData(List<AuditoriaGridDTO> data) {
        this.data = data;
    }

    public AuditoriaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(AuditoriaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
