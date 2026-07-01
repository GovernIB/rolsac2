package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.exportar.ExportarCampos;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.types.TypeExportarFormato;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.event.ReorderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para exportar.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogExportar extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogExportar.class);
    private static final long serialVersionUID = -978862425481233206L;

    /**
     * Identificador
     */
    String id;

    /**
     * Tipo de exportación
     */
    String tipo;

    /**
     * Datos
     **/
    private ExportarDatos datos;

    boolean tipoProcServ = false;

    public void load() {

        LOG.debug("init");
        this.setearIdioma();
        List<ExportarCampos> campos = new ArrayList<>();
        if (UtilJSF.getValorMochilaByKey("exportar") == null) {
            if (tipo != null) {
                if (tipo.equals("NORMATIVA")) {
                    campos.add(new ExportarCampos(getLiteral("dict.codigo"), "codigo", "CODI_NORMA", true));
                    campos.add(new ExportarCampos(getLiteral("dict.tituloCat"), "normaCat", "NOM_NORMA_CA", true, 3));
                    campos.add(new ExportarCampos(getLiteral("dict.tituloEsp"), "normaEsp", "NOM_NORMA_ES", true, 3));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.vigencia"), "estado", "ESTAT_NORMA", true));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.boletinOficial"), "tipoBoletin", "TIPUS_BUTLLETI", true));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.numeroBoletin"), "numeroBoletin", "NUM_BUTLLETI", true));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.urlBoletin"), "enlace", "ENLLAÇ", true, 2));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.fechaAprobacion"), "fechaAprobacion", "DATA_APROVACIO", true));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.tipoNormativa"), "tipoNormativa", "TIPO_NORMA", false));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.numero"), "numero", "NUM_NORMA", false));
                    campos.add(new ExportarCampos(getLiteral("dialogNormativa.fechaBoletin"), "fechaBoletin", "DATA_BOLETIN", false));
                    campos.add(new ExportarCampos(getLiteral("dict.responsableCat"), "responsableCat", "RESPONSABLE_NORMA_CA", false, 2));
                    campos.add(new ExportarCampos(getLiteral("dict.responsableEsp"), "responsableEsp", "RESPONSABLE_NORMA_ES", false, 2));
                    campos.add(new ExportarCampos(getLiteral("dict.enlaceCat"), "enlaceCat", "ENLLAC_NORMA_CA", false, 2));
                    campos.add(new ExportarCampos(getLiteral("dict.enlaceEsp"), "enlaceEsp", "ENLLAC_NORMA_ES", false, 2));
                } else if (tipo.equals("UA")) {
                    campos.add(new ExportarCampos(getLiteral("dict.codigo"), "codigo", "CODI_UA", true));
                    campos.add(new ExportarCampos(getLiteral("dict.nombreCat"), "nombreCat", "NOM_UA_CA", true));
                    campos.add(new ExportarCampos(getLiteral("dict.nombreEsp"), "nombreEsp", "NOM_UA_ES", true));
                    campos.add(new ExportarCampos(getLiteral("dict.identificador"), "identificador", "IDENTIFICADOR", true));
                    campos.add(new ExportarCampos(getLiteral("dict.codigoDIR3"), "codigoDIR3", "CODIGO_DIR3", true));
                    campos.add(new ExportarCampos(getLiteral("dict.tipo"), "tipo", "TIPO", true));
                    campos.add(new ExportarCampos(getLiteral("dict.padre"), "nombrePadre", "NOMBRE_PADRE", true));
                    campos.add(new ExportarCampos(getLiteral("dict.orden"), "orden", "ORDEN", true));
                    campos.add(new ExportarCampos(getLiteral("dict.version"), "version", "VERSION", false));
                    campos.add(new ExportarCampos(getLiteral("dialogUnidadAdministrativa.abreviatura"), "abreviatura", "ABREVIATURA", false));
                    campos.add(new ExportarCampos(getLiteral("dict.url"), "url", "URL", false));
                    campos.add(new ExportarCampos(getLiteral("dict.presentacion"), "presentacion", "PRESENTACION", false));
                    campos.add(new ExportarCampos(getLiteral("dialogUnidadAdministrativa.responsableNombre"), "responsableNombre", "RESPONSABLE_NOMBRE", false));
                    campos.add(new ExportarCampos(getLiteral("dialogUnidadAdministrativa.responsableEmail"), "responsableEmail", "RESPONSABLE_EMAIL", false));
                    campos.add(new ExportarCampos(getLiteral("dialogUnidadAdministrativa.tipoSexo"), "responsableNombre", "RESPONSABLE_SEXO", false));
                    campos.add(new ExportarCampos(getLiteral("dict.contactoTelf"), "contactoTelf", "CONTACTO_TELF", false));
                    campos.add(new ExportarCampos(getLiteral("dict.contactoFax"), "contactoFax", "CONTACTO_FAX", false));
                    campos.add(new ExportarCampos(getLiteral("dict.contactoEmail"), "contactoEmail", "CONTACTO_EMAIL", false));
                    campos.add(new ExportarCampos(getLiteral("dict.contactoDominio"), "contactoDominio", "CONTACTO_DOM", false));


                } else if (tipo.equals("PROC")) {
                    tipoProcServ = true;
                    campos.add(new ExportarCampos(getLiteral("dict.codigo"), "codigo", "CODI_PROCEDIMENT", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.codigoWF"), "codigoWF", "CODI_PROCEDIMENT_WF", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaPublicacion"), "fechaPub", "DATA_PUBLICACIO", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaCaducidad"), "fechaCad", "DATA_CADUCITAT", false, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.codigoSIA"), "codigoSIA", "CODI_SIA", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.estadoSIA"), "estadoSIA", "ESTAT_SIA", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaSIA"), "fechaSIA", "DATA_ACTUALITZACIO_SIA", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.flujo"), "wf", "WF_PROCEDIMENT", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.estado"), "estado", "ESTAT_PROCEDIMENT", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.visibilidad"), "visibilidad", "VISIBILITAT_PROCEDIMENT", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.nombreCat"), "nombreCat", "NOM_PROCEDIMENT_CA", true, 10));
                    campos.add(new ExportarCampos(getLiteral("dict.nombreEsp"), "nombreEsp", "NOM_PROCEDIMENT_ES", true, 10));
                    campos.add(new ExportarCampos(getLiteral("dict.objetoCat"), "objetoCat", "OBJECTE_CA", true, 3));
                    campos.add(new ExportarCampos(getLiteral("dict.objetoEsp"), "objetoEsp", "OBJECTE_ES", true, 3));
                    campos.add(new ExportarCampos(getLiteral("dict.publicoObjetivo"), "publicoObjetivo", "PUBLIC_OBJECTIU", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dialogProcedimiento.organoInstructor"), "unidadAdministrativaInstructora", "NOM_UA_INSTRUCTURA", true, 2));
                    campos.add(new ExportarCampos(getLiteral("dialogProcedimiento.organoCompetente"), "unidadAdministrativaResponsable", "NOM_UA_RESPONSABLE", true, 2));
                    campos.add(new ExportarCampos(getLiteral("dict.responsable"), "responsable", "NOM_RESPONSABLE", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.numeroTramites"), "numeroTramites", "NUM_TRAMITS", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.numeroTramitesTelematicos"), "numeroTramitesTelematicos", "NUM_TRAMITS_TELEMATICS", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.numeroNormativas"), "numeroNormas", "NUM_NORMES", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaActualizacion"), "fechaActualizacion", "DATA_ACTUALITZACIO", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.usuarioUltimaActualizacion"), "usuarioUltimaActualizacion", "NOM_USUARI_DARRERA_ACT", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.comun"), "comun", "COMU", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.exportarTramites"), "exportarTramites", null, true, 1));

                } else if (tipo.equals("SERV")) {
                    tipoProcServ = true;
                    campos.add(new ExportarCampos(getLiteral("dict.codigo"), "codigo", "CODI_SERVEI", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.codigoWF"), "codigoWF", "CODI_SERVEI_WF", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaPublicacion"), "fechaPub", "DATA_PUBLICACIO", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaDespublicacion"), "fechaDespub", "DATA_DESPUBLICACIO", false, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.codigoSIA"), "codigoSIA", "CODI_SIA", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.estadoSIA"), "estadoSIA", "ESTAT_SIA", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaSIA"), "fechaSIA", "DATA_ACTUALITZACIO_SIA", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.wf"), "wf", "WF_SERVEI", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.estado"), "estado", "ESTAT_SERVEI", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.visibilidad"), "visibilidad", "VISIBILITAT_SERVEI", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.nombreCat"), "nombreCat", "NOM_SERVEI_CA", true, 10));
                    campos.add(new ExportarCampos(getLiteral("dict.nombreEsp"), "nombreEsp", "NOM_SERVEI_ES", true, 10));
                    campos.add(new ExportarCampos(getLiteral("dict.objetoCat"), "objetoCat", "OBJECTE_CA", true, 3));
                    campos.add(new ExportarCampos(getLiteral("dict.objetoEsp"), "objetoEsp", "OBJECTE_ES", true, 3));
                    campos.add(new ExportarCampos(getLiteral("dict.publicoObjetivo"), "publicoObjetivo", "PUBLIC_OBJECTIU", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dialogProcedimiento.organoCompetente"), "unidadAdministrativaCompetente", "NOM_UA_RESPONSABLE", true, 2));
                    campos.add(new ExportarCampos(getLiteral("dict.responsable"), "responsable", "NOM_RESPONSABLE", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.numeroNormativas"), "numeroNormas", "NUM_NORMES", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.fechaActualizacion"), "fechaActualizacion", "DATA_ACTUALITZACIO", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.usuarioUltimaActualizacion"), "usuarioUltimaActualizacion", "NOM_USUARI_DARRERA_ACT", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.comun"), "comun", "COMU", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.presencial"), "presencial", "PRESENCIAL", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.telematico"), "telematico", "TELEMATICO", true, 1));
                    campos.add(new ExportarCampos(getLiteral("dict.telefonico"), "telefonico", "TELEFONICO", true, 1));
                }
            }

            datos = new ExportarDatos(campos);
            datos.setTipo(tipo);
            datos.setFormato(TypeExportarFormato.CSV);
        } else {
            datos = (ExportarDatos) UtilJSF.getValorMochilaByKey("exportar");
            UtilJSF.vaciarMochila();
            if (tipo.equals("SERV") || tipo.equals("PROC")) {
                tipoProcServ = true;
            }
        }
    }

    /**
     * Devuelve si es tipo procedimiento/servicio.
     *
     * @return
     */
    public boolean isProcServ() {
        return tipoProcServ;
    }


    /**
     * Guarda.
     */
    public void guardar() {
        boolean alguno = false;
        for (ExportarCampos dato : datos.getCampos()) {
            if (dato.isSeleccionado()) {
                alguno = true;
                break;
            }
        }
        if (!alguno) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("msg.seleccioneElemento"));
            return;
        }

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.CONSULTA);
        result.setResult(datos);
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

    /**
     * Metodo cuando se reordena el datatable. Normalmente no es necesario pero parece que no es capaz de reordenar los nombres campos.
     *
     * @param event
     */
    public void onRowReorder(ReorderEvent event) {
   /*
        int fromIndex = event.getFromIndex();
        int toIndex = event.getToIndex();

        // Mover el objeto completo en lugar de solo intercambiar nombres
        ExportarCampos movedItem = datos.getCampos().remove(fromIndex);
        datos.getCampos().add(toIndex, movedItem);

        LOG.debug("Fila movida de {} a {}", fromIndex, toIndex);*/

        // Forzar la actualización de todos los valores en la lista
        LOG.debug("Fila movida de {} a {}", event.getFromIndex(), event.getToIndex());


    }

    /**
     * Cerra definitivo.
     */
    public void cerrar() {

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.CONSULTA);
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    /**
     * Ayuda
     */
    public void ayuda() {
        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No esta implementado");
    }

    /**
     * Obtiene id.
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Setea id.
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene tipo.
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Setea tipo.
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene datos.
     *
     * @return
     */
    public ExportarDatos getDatos() {
        return datos;
    }

    /**
     * Setea datos.
     *
     * @param datos
     */
    public void setDatos(ExportarDatos datos) {
        this.datos = datos;
    }
}
