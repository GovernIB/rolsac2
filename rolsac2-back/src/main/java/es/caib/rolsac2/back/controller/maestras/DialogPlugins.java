package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.PluginDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
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
public class DialogPlugins extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogUsuario.class);

    private static final String TIPO_BOLETIN_PROPIEDAD = "tipoBoletin";

    private String id;

    private PluginDTO data;

    private PluginDTO dataOriginal;

    private List<TipoBoletinDTO> tiposBoletin;

    private TipoBoletinDTO boletinSeleccionado;

    private List<String> tiposPlugin;

    /**
     * Propiedad seleccionada.
     */
    private Propiedad propiedadSeleccionada;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @EJB
    AdministracionEntServiceFacade administracionEntService;


    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        tiposPlugin = new ArrayList<>();
        tiposBoletin = maestrasSupServiceFacade.findBoletines();
        for (TypePluginEntidad tipoPlugin : TypePluginEntidad.values()) {
            tiposPlugin.add(tipoPlugin.toString());
        }
        data = new PluginDTO();
        if (this.isModoAlta()) {
            data = new PluginDTO();
            data.setEntidad(sessionBean.getEntidad());
            data.setPropiedades(new ArrayList<>());
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionEntService.findPluginById(Long.valueOf(id));
            if(this.data.getTipo().equals(TypePluginEntidad.BOLETIN.toString())) {
                Propiedad prop = this.data.getPropiedades().stream().
                        filter(propiedad -> propiedad.getCodigo().equals(TIPO_BOLETIN_PROPIEDAD)).findFirst().get();
                this.data.getPropiedades().remove(prop);
                boletinSeleccionado = maestrasSupServiceFacade.findTipoBoletinById(Long.valueOf(prop.getValor()));
            }
            dataOriginal = data.clone();
            dataOriginal.setPropiedades(new ArrayList<>(data.getPropiedades()));
        }

    }


    public void guardar() {

        boolean existe = administracionEntService.existePluginTipo(this.data.getCodigo(), this.data.getTipo());
        if (existe) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogPlugins.error.yaExisteTipo"), true);
            return;
        }

        if(this.data.getTipo().equals(TypePluginEntidad.BOLETIN.toString())) {
            this.altaPropiedadNormativa();
        }

        if (this.data.getCodigo() == null) {
            administracionEntService.createPlugin(this.data);
        } else {
            administracionEntService.updatePlugin(this.data);
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

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }
/*
    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getId())
                && administracionEntService.checkIdentificadorUsuario(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        return true;
    }*/

    /**
     * Crea nueva propiedad.
     */
    public void nuevaPropiedad() {
        UtilJSF.openDialog("/entidades/dialogPropiedad", TypeModoAcceso.ALTA, null, true, 500, 200);
    }

    /**
     * Edita una propiedad.
     */
    public void editarPropiedad() {

        if (!verificarFilaSeleccionada())
            return;
        String direccion = "/entidades/dialogPropiedad";
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.propiedadSeleccionada));

        UtilJSF.openDialog(direccion, TypeModoAcceso.EDICION, params, true, 500, 200);
    }

    /**
     * Quita una propiedad.
     */
    public void quitarPropiedad() {
        if (!verificarFilaSeleccionada())
            return;

        this.data.getPropiedades().remove(this.propiedadSeleccionada);

    }

    /**
     * Baja la propiedad de posición.
     */
    public void bajarPropiedad() {
        if (!verificarFilaSeleccionada())
            return;

        final int posicion = this.data.getPropiedades().indexOf(this.propiedadSeleccionada);
        if (posicion >= this.data.getPropiedades().size() - 1) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error mover abajo");
            return;
        }

        final Propiedad propiedad = this.data.getPropiedades().remove(posicion);
        this.data.getPropiedades().add(posicion + 1, propiedad);
    }

    /**
     * Sube la propiedad de posición.
     */
    public void subirPropiedad() {
        if (!verificarFilaSeleccionada())
            return;

        final int posicion = this.data.getPropiedades().indexOf(this.propiedadSeleccionada);
        if (posicion <= 0) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error mover arriba");
            return;
        }

        final Propiedad propiedad = this.data.getPropiedades().remove(posicion);
        this.data.getPropiedades().add(posicion - 1, propiedad);
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

                    if (data.getPropiedades() == null) {
                        data.setPropiedades(new ArrayList<>());
                    }
                    for (final Propiedad prop : data.getPropiedades()) {
                        if (prop.getCodigo().equals(propiedad.getCodigo())) {
                            duplicado = true;
                            break;
                        }
                    }

                    if (duplicado) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error duplicado");
                    } else {
                        this.data.getPropiedades().add(propiedad);
                    }

                    break;
                case EDICION:
                    // Actualizamos fila actual
                    final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
                    // Muestra dialogo
                    final int posicion = this.data.getPropiedades().indexOf(this.propiedadSeleccionada);

                    boolean duplicadoEdicion = false;

                    for (final Propiedad prop : data.getPropiedades()) {
                        if (prop.getCodigo().equals(propiedadEdicion.getCodigo())) {
                            duplicadoEdicion = true;
                            break;
                        }
                    }

                    if (duplicadoEdicion && !propiedadSeleccionada.getCodigo().equals(propiedadEdicion.getCodigo())) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "error duplicado");
                    } else {
                        this.data.getPropiedades().remove(posicion);
                        this.data.getPropiedades().add(posicion, propiedadEdicion);
                        this.propiedadSeleccionada = propiedadEdicion;
                    }
                    break;
                case CONSULTA:
                    // No hay que hacer nada
                    break;
            }
        }
    }

    private void altaPropiedadNormativa() {
        // Refrescamos datos
        final Propiedad propiedad = new Propiedad();
        propiedad.setCodigo(TIPO_BOLETIN_PROPIEDAD);
        if (boletinSeleccionado != null) {
            propiedad.setValor(boletinSeleccionado.getCodigo().toString());
        } else {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogPlugins.boletin.seleccionTipoNor"));
        }

        if (data.getPropiedades() == null) {
            data.setPropiedades(new ArrayList<>());
        }
        for (final Propiedad prop : data.getPropiedades()) {
            if (prop.getCodigo().equals(propiedad.getCodigo())) {
                data.getPropiedades().remove(prop);
            }
        }

        this.data.getPropiedades().add(propiedad);
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    public boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0
                || UtilComparador.compareTo(data.getEntidad().getCodigo(), dataOriginal.getEntidad().getCodigo()) != 0
                || UtilComparador.compareTo(data.getTipo(), dataOriginal.getTipo()) != 0
                || UtilComparador.compareTo(data.getClassname(), dataOriginal.getClassname()) != 0
                || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0
                || !data.getPropiedades().equals(dataOriginal.getPropiedades())
                || UtilComparador.compareTo(data.getPrefijoPropiedades(), dataOriginal.getPrefijoPropiedades()) != 0;

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

    public PluginDTO getData() {
        return data;
    }

    public void setData(PluginDTO data) {
        this.data = data;
    }

    public Propiedad getPropiedadSeleccionada() {
        return propiedadSeleccionada;
    }

    public void setPropiedadSeleccionada(Propiedad propiedadSeleccionada) {
        this.propiedadSeleccionada = propiedadSeleccionada;
    }

    public List<TipoBoletinDTO> getTiposBoletin() {
        return tiposBoletin;
    }

    public void setTiposBoletin(List<TipoBoletinDTO> tiposBoletin) {
        this.tiposBoletin = tiposBoletin;
    }

    public TipoBoletinDTO getBoletinSeleccionado() {
        return boletinSeleccionado;
    }

    public void setBoletinSeleccionado(TipoBoletinDTO boletinSeleccionado) {
        this.boletinSeleccionado = boletinSeleccionado;
    }

    public List<String> getTiposPlugin() {
        return tiposPlugin;
    }

    public void setTiposPlugin(List<String> tiposPlugin) {
        this.tiposPlugin = tiposPlugin;
    }
}
