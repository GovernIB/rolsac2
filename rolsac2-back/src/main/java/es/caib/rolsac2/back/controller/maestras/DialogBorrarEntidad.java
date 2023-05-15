package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

@Named
@ViewScoped
public class DialogBorrarEntidad extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogBorrarEntidad.class);

    @EJB
    private EntidadServiceFacade entidadServiceFacade;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    private Long numeroUas;

    private Long numeroNormativas;

    private Long numeroServicios;

    private Long numeroProcedimientos;

    private EntidadGridDTO entidadGridDTO;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();

        entidadGridDTO = (EntidadGridDTO) UtilJSF.getValorMochilaByKey("borrarEntidad");

        numeroUas = unidadAdministrativaServiceFacade.countByEntidad(entidadGridDTO.getCodigo());
        numeroNormativas = normativaServiceFacade.countByEntidad(entidadGridDTO.getCodigo());
        numeroProcedimientos = procedimientoServiceFacade.countByEntidad(entidadGridDTO.getCodigo());
        numeroServicios = procedimientoServiceFacade.countServicioByEntidad(entidadGridDTO.getCodigo());
    }

    public void borrar() {
        final DialogResult result = new DialogResult();
        try {
            entidadServiceFacade.delete(entidadGridDTO.getCodigo());
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
            UtilJSF.closeDialog(result);
        } catch (Exception e) {
            LOG.error("Error borrando la entidad " + entidadGridDTO.getCodigo(), e);
            addGlobalMessage("Error en la eliminacion");
        }

    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
            ;
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public Long getNumeroUas() {
        return numeroUas;
    }

    public void setNumeroUas(Long numeroUas) {
        this.numeroUas = numeroUas;
    }

    public Long getNumeroNormativas() {
        return numeroNormativas;
    }

    public void setNumeroNormativas(Long numeroNormativas) {
        this.numeroNormativas = numeroNormativas;
    }

    public Long getNumeroServicios() {
        return numeroServicios;
    }

    public void setNumeroServicios(Long numeroServicios) {
        this.numeroServicios = numeroServicios;
    }

    public Long getNumeroProcedimientos() {
        return numeroProcedimientos;
    }

    public void setNumeroProcedimientos(Long numeroProcedimientos) {
        this.numeroProcedimientos = numeroProcedimientos;
    }

    public EntidadGridDTO getEntidadGridDTO() {
        return entidadGridDTO;
    }

    public void setEntidadGridDTO(EntidadGridDTO entidadGridDTO) {
        this.entidadGridDTO = entidadGridDTO;
    }
}
