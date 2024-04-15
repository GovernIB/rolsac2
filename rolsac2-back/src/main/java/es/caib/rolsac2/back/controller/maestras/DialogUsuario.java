package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePerfiles;
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
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class DialogUsuario extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogUsuario.class);

    private String id;

    private UsuarioDTO data;

    private UsuarioDTO dataOriginal;

    private String identificadorOld = "";

    private UnidadAdministrativaGridDTO uaSeleccionada;

    private List<UnidadAdministrativaGridDTO> unidadesAdministrativasEntidad;

    @Inject
    private SessionBean sessionBean;

    @EJB
    private AdministracionEntServiceFacade administracionEntService;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    private EntidadGridDTO entidadSeleccionada;

    private List<EntidadGridDTO> entidades;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = new UsuarioDTO();
        if (this.isModoAlta()) {
            data = new UsuarioDTO();
            data.setEntidades(new ArrayList<>());

            data.setObservaciones(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            if (sessionBean.getPerfil() != TypePerfiles.SUPER_ADMINISTRADOR) {
                data.getEntidades().add(sessionBean.getEntidad().toGridDTO());
                data.setUnidadesAdministrativas(new ArrayList<>());
            }

            dataOriginal = data.clone();
            dataOriginal.setUnidadesAdministrativas(new ArrayList<>());
            unidadesAdministrativasEntidad = new ArrayList<>();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionEntService.findUsuarioById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
            dataOriginal = data.clone();
            if (sessionBean.getPerfil() != TypePerfiles.SUPER_ADMINISTRADOR) {
                dataOriginal.setUnidadesAdministrativas(new ArrayList<>(data.getUnidadesAdministrativas()));
                unidadesAdministrativasEntidad = this.data.getUnidadesAdministrativas().stream().filter(ua -> ua.getIdEntidad().compareTo(sessionBean.getEntidad().getCodigo()) == 0).collect(Collectors.toList());
            }
        }
    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        UsuarioDTO datoDTO = (UsuarioDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setObservaciones(datoDTO.getObservaciones());
        }
    }

    public void checkUsuario() {
        if (this.data.getCodigo() == null) {
            if (administracionEntService.checkIdentificadorUsuario(this.data.getIdentificador())) {
                if (!isModoAlta()) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
                } else {
                    PrimeFaces.current().executeScript("PF('usuarioAlta').show()");
                }
            } else {
                guardar();
            }
        } else {
            guardar();
        }
    }

    public void altaUsuarioEntidad() {
        this.data = administracionEntService.findUsuarioByIdentificador(this.data.getIdentificador());
        administracionEntService.createUsuarioEntidad(this.data, sessionBean.getEntidad().getCodigo());
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            administracionEntService.create(this.data);
        } else {
            administracionEntService.update(this.data);
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

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo()) && administracionEntService.checkIdentificadorUsuario(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador()) && administracionEntService.checkIdentificadorUsuario(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        return true;
    }

    public void actualizarUasEntidad() {
        unidadesAdministrativasEntidad = this.data.getUnidadesAdministrativas().stream().filter(ua -> ua.getIdEntidad().compareTo(sessionBean.getEntidad().getCodigo()) == 0).collect(Collectors.toList());
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    public boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0 || UtilComparador.compareTo(data.getIdentificador(), dataOriginal.getIdentificador()) != 0 || UtilComparador.compareTo(data.getNombre(), dataOriginal.getNombre()) != 0 || UtilComparador.compareTo(data.getEmail(), dataOriginal.getEmail()) != 0 || UtilComparador.compareTo(data.getObservaciones(), dataOriginal.getObservaciones()) != 0 || !data.getUnidadesAdministrativas().equals(dataOriginal.getUnidadesAdministrativas());
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


    /**
     * Abrir dialogo de Selección de Unidades Administrativas
     */
    public void abrirDialogUAs(TypeModoAcceso modoAcceso) {

        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", uaSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("dialogUnidadAdministrativa", modoAcceso, params, true, 1530, 733);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("unidadesAdministrativas", data.getUnidadesAdministrativas());
            final Map<String, String> params = new HashMap<>();
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.ALTA.toString());
            //params.put("esCabecera", "true");
            String direccion = "/comun/dialogSeleccionarUA";
            UtilJSF.openDialog(direccion, modoAcceso, params, true, 850, 575);
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            uaSeleccionada = unidadAdministrativaServiceFacade.findById(uaSeleccionada.getCodigo());
            if (uaSeleccionada != null) {
                UnidadAdministrativaGridDTO uaSeleccionadaGrid = uaSeleccionada.convertDTOtoGridDTO();
                if (uaSeleccionadaGrid.getIdEntidad() == null) {
                    uaSeleccionadaGrid.setIdEntidad(sessionBean.getEntidad().getCodigo());
                }
                //verificamos qeu la UA no esté seleccionada ya, en caso de estarlo mostramos mensaje
                if (this.data.getUnidadesAdministrativas() == null) {
                    this.data.setUnidadesAdministrativas(new ArrayList<>());
                }
                if (this.data.getUnidadesAdministrativas().contains(uaSeleccionadaGrid)) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.elementoRepetido"));
                } else {
                    this.data.getUnidadesAdministrativas().add(uaSeleccionadaGrid);
                    actualizarUasEntidad();
                }
            }
        }
    }

    /*
     * SELECCION ENTIDAD
     *
     * */

    public void abrirDialogSelecEnti(TypeModoAcceso modoAcceso) {
        if (TypeModoAcceso.CONSULTA.equals(modoAcceso) && entidadSeleccionada != null) {
            final Map<String, String> params = new HashMap<>();
            params.put(TypeParametroVentana.ID.toString(), entidadSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("/superadministrador/dialogEntidad", modoAcceso, params, true, 700, 620);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("entidadesSeleccionada", data.getEntidades());
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("dialogSeleccionEntidad", modoAcceso, params, true, 1040, 460);
        }
    }

    public void returnDialogEntidad(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<EntidadGridDTO> entidadesSeleccionadas = (List<EntidadGridDTO>) respuesta.getResult();
            if (entidadesSeleccionadas == null) {
                data.setEntidades(new ArrayList<>());
            } else {
                if (data.getEntidades() == null) {
                    data.setEntidades(new ArrayList<>());
                }
                data.setEntidades(new ArrayList<>());
                data.getEntidades().addAll(entidadesSeleccionadas);

            }
        }
    }

    public void anyadirEntidades() {
        abrirDialogSelecEnti(TypeModoAcceso.ALTA);
    }

    public void consultarEntidad() {
        if (entidadSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogSelecEnti(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarEntidad() {
        if (entidadSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getEntidades().remove(entidadSeleccionada);
            entidadSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    /**
     * Método para dar de alta UAs en un usuario
     */
    public void anyadirUAs() {
        abrirDialogUAs(TypeModoAcceso.ALTA);
    }

    /**
     * Método para consultar el detalle de una UA
     */
    public void consultarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogUAs(TypeModoAcceso.CONSULTA);
        }
    }

    /**
     * Método para borrar un usuario en una UA
     */
    public void borrarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getUnidadesAdministrativas().remove(uaSeleccionada);
            unidadesAdministrativasEntidad.remove(uaSeleccionada);
            uaSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsuarioDTO getData() {
        return data;
    }

    public void setData(UsuarioDTO data) {
        this.data = data;
    }

    public UnidadAdministrativaGridDTO getUaSeleccionada() {
        return uaSeleccionada;
    }

    public void setUaSeleccionada(UnidadAdministrativaGridDTO uaSeleccionada) {
        this.uaSeleccionada = uaSeleccionada;
    }

    public List<UnidadAdministrativaGridDTO> getUnidadesAdministrativasEntidad() {
        return unidadesAdministrativasEntidad;
    }

    public void setUnidadesAdministrativasEntidad(List<UnidadAdministrativaGridDTO> unidadesAdministrativasEntidad) {
        this.unidadesAdministrativasEntidad = unidadesAdministrativasEntidad;
    }

    public EntidadGridDTO getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(EntidadGridDTO entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    public List<EntidadGridDTO> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<EntidadGridDTO> entidades) {
        this.entidades = entidades;
    }
}
