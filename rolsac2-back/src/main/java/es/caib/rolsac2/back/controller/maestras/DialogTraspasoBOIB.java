package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.facade.integracion.BoletinServiceFacade;
import es.caib.rolsac2.service.model.EdictoGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.EdictoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class DialogTraspasoBOIB extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTraspasoBOIB.class);

    @EJB
    BoletinServiceFacade boletinServiceFacade;

    @EJB
    UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @EJB
    MaestrasSupServiceFacade maestrasSupServiceFacade;

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
        if (Objects.nonNull(this.filtro)) {
            if (getFiltroNumBoletin().equals("") && getFiltroFechaBoletin() == null && getFiltroNumRegistro().equals("")) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraspasoBOIB.rellenarUnElemento"), true);
            } else {
                try {
                    data = boletinServiceFacade.listar(getFiltroNumBoletin(), getFiltroFechaBoletin() == null ? "" : getFecha(getFiltroFechaBoletin()), getFiltroNumRegistro(), sessionBean.getEntidad().getCodigo());
                    this.mostrarResultados = true;
                } catch (RuntimeException e) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraspasoBOIB.erroresBusqueda"), true);
                }

            }
        }
    }

    private String getFecha(Date filtroFechaBoletin) {

        // Crear un formato para la fecha
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        // Convertir Date a String con el formato especificado
        String dateString = formatter.format(filtroFechaBoletin);

        return dateString;


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
        if (datoSeleccionado == null) {
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
        if (boletinServiceFacade.obtenerBoletinPlugin(this.sessionBean.getEntidad().getCodigo()) != null) {
            Long tipoBoletinId = boletinServiceFacade.obtenerBoletinPlugin(this.sessionBean.getEntidad().getCodigo());
            normativaDTO.setBoletinOficial(maestrasSupServiceFacade.findTipoBoletinById(tipoBoletinId));
        }
        normativaDTO.setEntidad(sessionBean.getEntidad());
        normativaDTO.setTitulo(dto.getTitulo());
        normativaDTO.setUrlBoletin(dto.getUrl());
        normativaDTO.setFechaBoletin(dto.getFechaBoletin());
        normativaDTO.setNumeroBoletin(dto.getNumeroBoletin());
        //normativaDTO.setNumero(obtenerNumero(dto));
        normativaDTO.setNumero(null);
        List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
        UnidadAdministrativaGridDTO uaActiva = unidadAdministrativaServiceFacade.findById(sessionBean.getUnidadActiva().getCodigo()).convertDTOtoGridDTO();
        unidadesAdministrativas.add(uaActiva);
        normativaDTO.setUnidadesAdministrativas(unidadesAdministrativas);

        return normativaDTO;
    }

    private String obtenerNumero(EdictoGridDTO dto) {

        // Expresión regular para encontrar el patrón de 1 a 3 dígitos seguido de una barra y cuatro dígitos
        Pattern pattern = Pattern.compile("\\b\\d{1,3}/\\d{4}\\b");
        Matcher matcher = pattern.matcher(getTituloCaEs(dto.getTitulo()));

        if (matcher.find()) {
            // Extrae el valor que coincide con el patrón
            return matcher.group();
        } else {
            return dto.getNumeroRegistro();
        }
    }

    private String getTituloCaEs(Literal titulo) {
        if (titulo.getTraduccion("ca") != null && !titulo.getTraduccion("ca").isEmpty()) {
            return titulo.getTraduccion("ca");
        } else if (titulo.getTraduccion("es") != null && !titulo.getTraduccion("es").isEmpty()) {
            return titulo.getTraduccion("es");
        } else {
            String titulox = titulo.getTraduccion();
            if (titulox == null || titulox.isEmpty()) {
                return "";
            } else {
                return titulox;
            }
        }
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

    public void setFiltroFechaBoletin(Date fechaBoletin) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setFechaBoletin(fechaBoletin);
        }
    }

    public Date getFiltroFechaBoletin() {
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
