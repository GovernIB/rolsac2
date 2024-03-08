package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.model.ProcesoDTO;
import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.utils.UtilComparador;
import es.caib.rolsac2.service.utils.UtilJSON;
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

@Named
@ViewScoped
public class DialogProceso extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogProceso.class);

    private String id;

    private ProcesoDTO data;

    private ProcesoDTO dataOriginal;

    /**
     * Propiedad seleccionada.
     */
    private Propiedad propiedadSeleccionada;


    @Inject
    private SessionBean sessionBean;

    @EJB
    ProcesoServiceFacade procesoServiceFacade;

    @EJB
    AdministracionEntServiceFacade administracionEntServiceFacade;


    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        if (isModoAlta()) {
            data = new ProcesoDTO();
            data.setEntidad(sessionBean.getEntidad());
            data.setParametrosInvocacion(new ArrayList<>());
            dataOriginal = data.clone();
            dataOriginal.setParametrosInvocacion(new ArrayList<>());
        } else {
            data = procesoServiceFacade.obtenerProcesoPorCodigo(Long.valueOf(id));
            dataOriginal = data.clone();
            dataOriginal.setParametrosInvocacion(new ArrayList<>(data.getParametrosInvocacion()));
        }
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        procesoServiceFacade.guardar(data);

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
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public boolean existeTipoProcesoEntidad() {
        ProcesoFiltro filtro = new ProcesoFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        List<ProcesoGridDTO> procesosEntidad = procesoServiceFacade.listar(filtro);
        for (ProcesoGridDTO proceso : procesosEntidad) {
            if (proceso.getIdentificadorProceso().equals(data.getIdentificadorProceso())) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarGuardar() {
        if (isModoAlta() && existeTipoProcesoEntidad()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProcesos.existe.tipo"), true);
            return false;
        }

        //Verificamos que si se da de alta un proceso dependiente de un plugin, el plugin esté dado de alta
        switch (data.getIdentificadorProceso()) {
            case "SOLR": {
                if (!administracionEntServiceFacade.existePluginTipoByEntidad(sessionBean.getEntidad().getCodigo(), TypePluginEntidad.INDEXACION.toString())) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProceso.faltaPluginSOLR"), true);
                    return false;
                }
                break;
            }
            case "SOLR_PUNT": {
                if (!administracionEntServiceFacade.existePluginTipoByEntidad(sessionBean.getEntidad().getCodigo(), TypePluginEntidad.INDEXACION.toString())) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProceso.faltaPluginSOLR"), true);
                    return false;
                }
                break;
            }
            case "SIA": {
                if (!administracionEntServiceFacade.existePluginTipoByEntidad(sessionBean.getEntidad().getCodigo(), TypePluginEntidad.SIA.toString())) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProceso.faltaPluginSIA"), true);
                    return false;
                }
                break;
            }
            case "SIA_PUNT": {
                if (!administracionEntServiceFacade.existePluginTipoByEntidad(sessionBean.getEntidad().getCodigo(), TypePluginEntidad.SIA.toString())) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProceso.faltaPluginSIA"), true);
                    return false;
                }
                break;
            }
            case "DIR3": {
                if (!administracionEntServiceFacade.existePluginTipoByEntidad(sessionBean.getEntidad().getCodigo(), TypePluginEntidad.DIR3.toString())) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProceso.faltaPluginDIR3"), true);
                    return false;
                }
                break;
            }
        }
        return true;
    }

    /**
     * Crea nueva propiedad.
     */
    public void nuevaPropiedad() {
        UtilJSF.openDialog("/entidades/dialogPropiedad", TypeModoAcceso.ALTA, null, true, 500, 220);
    }

    /**
     * Edita una propiedad.
     */
    public void editarPropiedad() {

        if (!verificarFilaSeleccionada()) return;
        String direccion = "/entidades/dialogPropiedad";
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.propiedadSeleccionada));

        UtilJSF.openDialog(direccion, TypeModoAcceso.EDICION, params, true, 500, 220);
    }

    /**
     * Quita una propiedad.
     */
    public void quitarPropiedad() {
        if (!verificarFilaSeleccionada()) return;

        this.data.getParametrosInvocacion().remove(this.propiedadSeleccionada);

    }

    /**
     * Baja la propiedad de posición.
     */
    public void bajarPropiedad() {
        if (!verificarFilaSeleccionada()) return;

        final int posicion = this.data.getParametrosInvocacion().indexOf(this.propiedadSeleccionada);
        if (posicion >= this.data.getParametrosInvocacion().size() - 1) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error mover abajo");
            return;
        }

        final Propiedad propiedad = this.data.getParametrosInvocacion().remove(posicion);
        this.data.getParametrosInvocacion().add(posicion + 1, propiedad);
    }

    /**
     * Sube la propiedad de posición.
     */
    public void subirPropiedad() {
        if (!verificarFilaSeleccionada()) return;

        final int posicion = this.data.getParametrosInvocacion().indexOf(this.propiedadSeleccionada);
        if (posicion <= 0) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error mover arriba");
            return;
        }

        final Propiedad propiedad = this.data.getParametrosInvocacion().remove(posicion);
        this.data.getParametrosInvocacion().add(posicion - 1, propiedad);
    }

    /**
     * Verifica si hay fila seleccionada.
     *
     * @return
     */
    private boolean verificarFilaSeleccionada() {
        boolean filaSeleccionada = true;

        if (this.propiedadSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error fila no seleccionada");
            filaSeleccionada = false;
        }
        return filaSeleccionada;
    }

    /**
     * Retorno dialogo de los botones de propiedades.
     *
     * @param event respuesta dialogo
     */
    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        if (!respuesta.isCanceled()) {
            switch (respuesta.getModoAcceso()) {
                case ALTA:
                    // Refrescamos datos
                    final Propiedad propiedad = (Propiedad) respuesta.getResult();

                    boolean duplicado = false;

                    if (data.getParametrosInvocacion() == null) {
                        data.setParametrosInvocacion(new ArrayList<>());
                    }
                    for (final Propiedad prop : data.getParametrosInvocacion()) {
                        if (prop.getCodigo().equals(propiedad.getCodigo())) {
                            duplicado = true;
                            break;
                        }
                    }

                    if (duplicado) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error duplicado");
                    } else {
                        this.data.getParametrosInvocacion().add(propiedad);
                    }

                    break;
                case EDICION:
                    // Actualizamos fila actual
                    final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
                    // Muestra dialogo
                    final int posicion = this.data.getParametrosInvocacion().indexOf(this.propiedadSeleccionada);

                    boolean duplicadoEdicion = false;

                    for (final Propiedad prop : data.getParametrosInvocacion()) {
                        if (prop.getCodigo().equals(propiedadEdicion.getCodigo())) {
                            duplicadoEdicion = true;
                            break;
                        }
                    }

                    if (duplicadoEdicion && !propiedadSeleccionada.getCodigo().equals(propiedadEdicion.getCodigo())) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error duplicado");
                    } else {
                        this.data.getParametrosInvocacion().remove(posicion);
                        this.data.getParametrosInvocacion().add(posicion, propiedadEdicion);
                        this.propiedadSeleccionada = propiedadEdicion;
                    }
                    break;
                case CONSULTA:
                    // No hay que hacer nada
                    break;
            }
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
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0 || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0 || !dataOriginal.getParametrosInvocacion().equals(data.getParametrosInvocacion()) || UtilComparador.compareTo(data.getCron(), dataOriginal.getCron()) != 0;
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

    public ProcesoDTO getData() {
        return data;
    }

    public void setData(ProcesoDTO data) {
        this.data = data;
    }

    public Propiedad getPropiedadSeleccionada() {
        return propiedadSeleccionada;
    }

    public void setPropiedadSeleccionada(Propiedad propiedadSeleccionada) {
        this.propiedadSeleccionada = propiedadSeleccionada;
    }

}
