package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.facade.integracion.BoletinServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EdictoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Named
@ViewScoped
public class DialogTraspasoBOIB extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTraspasoBOIB.class);

    @EJB
    BoletinServiceFacade boletinServiceFacade;

    @EJB
    UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    private List<EdictoGridDTO> data;

    private EdictoGridDTO datoSeleccionado;

    private EdictoFiltro filtro;

    private boolean mostrarResultados;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        filtro = new EdictoFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        this.mostrarResultados = false;

    }

    public void buscar() {
        if(Objects.nonNull(this.filtro)) {
            if(getFiltroNumBoletin().equals("") && getFiltroFechaBoletin() == null && getFiltroNumRegistro().equals("")) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraspasoBOIB.rellenarUnElemento"), true);
            } else {
                try{
                    data = boletinServiceFacade.listar(getFiltroNumBoletin(), getFiltroFechaBoletin() == null ? "" : getFiltroFechaBoletin().toString(),
                            getFiltroNumRegistro(), sessionBean.getEntidad().getCodigo());
                    this.mostrarResultados = true;
                } catch (RuntimeException e) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraspasoBOIB.erroresBusqueda"), true);
                }

            }
        }
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

    public void crearNormativa() {
        if(datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("isTraspaso", "true");
            //UtilJSF.anyadirMochila("normativaBOIB", createNormativaDTO(datoSeleccionado));
            NormativaDTO normativaDTO = createNormativaDTO(datoSeleccionado);
            final DialogResult result = new DialogResult();
            if (this.getModoAcceso() != null) {
                result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
            } else {
                result.setModoAcceso(TypeModoAcceso.CONSULTA);
            }
            result.setResult(normativaDTO);
            UtilJSF.closeDialog(result);
        }
    }

    private NormativaDTO createNormativaDTO(EdictoGridDTO dto) {
        NormativaDTO normativaDTO = new NormativaDTO();
        normativaDTO.setEntidad(sessionBean.getEntidad());
        normativaDTO.setNombre(dto.getTitulo());
        normativaDTO.setUrlBoletin(dto.getUrl());
        normativaDTO.setFechaBoletin(dto.getFechaBoletin());
        normativaDTO.setNumeroBoletin(dto.getNumeroBoletin());
        normativaDTO.setNumero(dto.getNumeroRegistro());
        List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
        UnidadAdministrativaGridDTO uaActiva = unidadAdministrativaServiceFacade.findById(sessionBean.getUnidadActiva().getCodigo()).convertDTOtoGridDTO();
        unidadesAdministrativas.add(uaActiva);
        normativaDTO.setUnidadesAdministrativas(unidadesAdministrativas);

        return normativaDTO;
    }

    public List<EdictoGridDTO> getData() {
        return data;
    }

    public void setData(List<EdictoGridDTO> data) {
        this.data = data;
    }

    public EdictoGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(EdictoGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public void setFiltroNumBoletin(String texto) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setNumeroBoletin(texto);
        }
    }

    public String getFiltroNumBoletin() {
        if (Objects.nonNull(this.filtro) && Objects.nonNull(this.filtro.getNumeroBoletin())) {
            return this.filtro.getNumeroBoletin();
        }
        return "";
    }

    public void setFiltroNumRegistro(String texto) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setNumeroRegistro(texto);
        }
    }

    public String getFiltroNumRegistro() {
        if (Objects.nonNull(this.filtro) && Objects.nonNull(this.filtro.getNumeroRegistro())) {
            return this.filtro.getNumeroRegistro();
        }
        return "";
    }

    public void setFiltroFechaBoletin(LocalDate fechaBoletin) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setFechaBoletin(fechaBoletin);
        }
    }

    public LocalDate getFiltroFechaBoletin() {
        if (Objects.nonNull(this.filtro)) {
            return this.filtro.getFechaBoletin();
        }
        return null;
    }

    public EdictoFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(EdictoFiltro filtro) {
        this.filtro = filtro;
    }

    public boolean isMostrarResultados() {
        return mostrarResultados;
    }

    public void setMostrarResultados(boolean mostrarResultados) {
        this.mostrarResultados = mostrarResultados;
    }
}
