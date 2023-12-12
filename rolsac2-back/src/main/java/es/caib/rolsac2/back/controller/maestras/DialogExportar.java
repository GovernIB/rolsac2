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

    public void load() {

        LOG.debug("init");
        this.setearIdioma();
        List<ExportarCampos> campos = new ArrayList<>();
        if (UtilJSF.getValorMochilaByKey("exportar") == null) {
            if (tipo != null) {
                if (tipo.equals("NORMATIVA")) {
                    campos.add(new ExportarCampos("dict.codigo", "codigo", "CODI_NORMA", true));
                    campos.add(new ExportarCampos("dict.tituloCat", "normaCat", "NOM_NORMA_CA", true, 3));
                    campos.add(new ExportarCampos("dict.tituloEsp", "normaEsp", "NOM_NORMA_ES", true, 3));
                    campos.add(new ExportarCampos("dialogNormativa.vigencia", "estado", "ESTAT_NORMA", true));
                    //datos.add(new ExportarCampos("dialogExportar.rangoLegal", "rangoLegal", "RANG_LEGAL", true));
                    campos.add(new ExportarCampos("dialogNormativa.boletinOficial", "tipoBoletin", "TIPUS_BUTLLETI", true));
                    campos.add(new ExportarCampos("dialogNormativa.numeroBoletin", "numeroBoletin", "NUM_BUTLLETI", true));
                    campos.add(new ExportarCampos("dialogNormativa.urlBoletin", "enlace", "ENLLAÇ", true, 2));
                    campos.add(new ExportarCampos("dialogNormativa.fechaAprobacion", "fechaAprobacion", "DATA_APROVACIO", true));

                    campos.add(new ExportarCampos("dialogNormativa.tipoNormativa", "tipoNormativa", "TIPO_NORMA", false));
                    campos.add(new ExportarCampos("dialogNormativa.numero", "numero", "NUM_NORMA", false));
                    campos.add(new ExportarCampos("dialogNormativa.fechaBoletin", "fechaBoletin", "DATA_BOLETIN", false));
                    campos.add(new ExportarCampos("dict.responsableCat", "responsableCat", "RESPONSABLE_NORMA_CA", false, 2));
                    campos.add(new ExportarCampos("dict.responsableEsp", "responsableEsp", "RESPONSABLE_NORMA_ES", false, 2));
                    campos.add(new ExportarCampos("dict.enlaceCat", "enlaceCat", "ENLLAC_NORMA_CA", false, 2));
                    campos.add(new ExportarCampos("dict.enlaceEsp", "enlaceEsp", "ENLLAC_NORMA_ES", false, 2));
                } else if (tipo.equals("UA")) {
                    campos.add(new ExportarCampos("dict.codigo", "codigo", "CODI_UA", true));
                    campos.add(new ExportarCampos("dict.nombreCat", "nombreCat", "NOM_UA_CA", true));
                    campos.add(new ExportarCampos("dict.nombreEsp", "nombreEsp", "NOM_UA_ES", true));
                    campos.add(new ExportarCampos("dict.identificador", "identificador", "IDENTIFICADOR", true));
                    campos.add(new ExportarCampos("dict.codigoDIR3", "codigoDIR3", "CODIGO_DIR3", true));
                    campos.add(new ExportarCampos("dict.tipo", "tipo", "TIPO", true));
                    campos.add(new ExportarCampos("dict.padre", "nombrePadre", "NOMBRE_PADRE", true));
                    campos.add(new ExportarCampos("dict.orden", "orden", "ORDEN", true));

                    campos.add(new ExportarCampos("dict.version", "version", "VERSION", false));
                    campos.add(new ExportarCampos("dialogUnidadAdministrativa.abreviatura", "abreviatura", "ABREVIATURA", false));
                    campos.add(new ExportarCampos("dict.url", "url", "URL", false));
                    campos.add(new ExportarCampos("dict.presentacion", "presentacion", "PRESENTACION", false));
                    campos.add(new ExportarCampos("dialogUnidadAdministrativa.responsableNombre", "responsableNombre", "RESPONSABLE_NOMBRE", false));
                    campos.add(new ExportarCampos("dialogUnidadAdministrativa.responsableEmail", "responsableEmail", "RESPONSABLE_EMAIL", false));
                    campos.add(new ExportarCampos("dialogUnidadAdministrativa.tipoSexo", "responsableNombre", "RESPONSABLE_SEXO", false));
                    campos.add(new ExportarCampos("dict.contactoTelf", "contactoTelf", "CONTACTO_TELF", false));
                    campos.add(new ExportarCampos("dict.contactoFax", "contactoFax", "CONTACTO_FAX", false));
                    campos.add(new ExportarCampos("dict.contactoEmail", "contactoEmail", "CONTACTO_EMAIL", false));
                    campos.add(new ExportarCampos("dict.contactoDominio", "contactoDominio", "CONTACTO_DOM", false));


                } else if (tipo.equals("PROC")) {

                    campos.add(new ExportarCampos("dict.codigo", "codigo", "CODI_PROCEDIMENT", true, 1));
                    campos.add(new ExportarCampos("dict.codigoWF", "codigoWF", "CODI_PROCEDIMENT_WF", true, 1));
                    campos.add(new ExportarCampos("dict.fechaPublicacion", "fechaPub", "DATA_PUBLICACIO", true, 1));
                    campos.add(new ExportarCampos("dict.fechaCaducidad", "fechaCad", "DATA_CADUCITAT", false, 1));
                    campos.add(new ExportarCampos("dict.codigoSIA", "codigoSIA", "CODI_SIA", true, 1));
                    campos.add(new ExportarCampos("dict.estadoSIA", "estadoSIA", "ESTAT_SIA", true, 1));
                    campos.add(new ExportarCampos("dict.fechaSIA", "fechaSIA", "DATA_ACTUALITZACIO_SIA", true, 1));
                    campos.add(new ExportarCampos("dict.flujo", "wf", "WF_PROCEDIMENT", true, 1));
                    campos.add(new ExportarCampos("dict.estado", "estado", "ESTAT_PROCEDIMENT", true, 1));
                    campos.add(new ExportarCampos("dict.visibilidad", "visibilidad", "VISIBILITAT_PROCEDIMENT", true, 1));
                    /* campos.add(new ExportarCampos("dict.pendienteValidar", "pendienteValidar", "PENDENT_VALIDAR", true));*/
                    campos.add(new ExportarCampos("dict.nombreCat", "nombreCat", "NOM_PROCEDIMENT_CA", true, 10));
                    campos.add(new ExportarCampos("dict.nombreEsp", "nombreEsp", "NOM_PROCEDIMENT_ES", true, 10));
                    campos.add(new ExportarCampos("dict.objetoCat", "objetoCat", "OBJECTE_CA", true, 3));
                    campos.add(new ExportarCampos("dict.objetoEsp", "objetoEsp", "OBJECTE_ES", true, 3));
                    campos.add(new ExportarCampos("dict.publicoObjetivo", "publicoObjetivo", "PUBLIC_OBJECTIU", true, 1));
                    campos.add(new ExportarCampos("dialogProcedimiento.organoInstructor", "unidadAdministrativaInstructora", "NOM_UA_INSTRUCTURA", true, 2));
                    campos.add(new ExportarCampos("dialogProcedimiento.organoCompetente", "unidadAdministrativaResponsable", "NOM_UA_RESPONSABLE", true, 2));
                    campos.add(new ExportarCampos("dict.responsable", "responsable", "NOM_RESPONSABLE", true, 1));
                    /*campos.add(new ExportarCampos("dict.unidadAdministrativaResolutoria", "unidadAdministrativaResolutoria", "NOM_UA_RESOLUTORIA", true));*/
                    campos.add(new ExportarCampos("dict.numeroTramites", "numeroTramites", "NUM_TRAMITS", true, 1));
                    campos.add(new ExportarCampos("dict.numeroTramitesTelematicos", "numeroTramitesTelematicos", "NUM_TRAMITS_TELEMATICS", true, 1));
                    campos.add(new ExportarCampos("dict.numeroNormativas", "numeroNormas", "NUM_NORMES", true, 1));
                    campos.add(new ExportarCampos("dict.fechaActualizacion", "fechaActualizacion", "DATA_ACTUALITZACIO", true, 1));
                    campos.add(new ExportarCampos("dict.usuarioUltimaActualizacion", "usuarioUltimaActualizacion", "NOM_USUARI_DARRERA_ACT", true, 1));
                    campos.add(new ExportarCampos("dict.comun", "comun", "COMU", true, 1));

                } else if (tipo.equals("SERV")) {

                    campos.add(new ExportarCampos("dict.codigo", "codigo", "CODI_SERVEI", true, 1));
                    campos.add(new ExportarCampos("dict.codigoWF", "codigoWF", "CODI_SERVEI_WF", true, 1));
                    campos.add(new ExportarCampos("dict.fechaPublicacion", "fechaPub", "DATA_PUBLICACIO", true, 1));
                    campos.add(new ExportarCampos("dict.fechaDespublicacion", "fechaDespub", "DATA_DESPUBLICACIO", false, 1));
                    campos.add(new ExportarCampos("dict.codigoSIA", "codigoSIA", "CODI_SIA", true, 1));
                    campos.add(new ExportarCampos("dict.estadoSIA", "estadoSIA", "ESTAT_SIA", true, 1));
                    campos.add(new ExportarCampos("dict.fechaSIA", "fechaSIA", "DATA_ACTUALITZACIO_SIA", true, 1));
                    campos.add(new ExportarCampos("dict.wf", "wf", "WF_SERVEI", true, 1));
                    campos.add(new ExportarCampos("dict.estado", "estado", "ESTAT_SERVEI", true, 1));
                    campos.add(new ExportarCampos("dict.visibilidad", "visibilidad", "VISIBILITAT_SERVEI", true, 1));
                    campos.add(new ExportarCampos("dict.nombreCat", "nombreCat", "NOM_SERVEI_CA", true, 10));
                    campos.add(new ExportarCampos("dict.nombreEsp", "nombreEsp", "NOM_SERVEI_ES", true, 10));
                    campos.add(new ExportarCampos("dict.objetoCat", "objetoCat", "OBJECTE_CA", true, 3));
                    campos.add(new ExportarCampos("dict.objetoEsp", "objetoEsp", "OBJECTE_ES", true, 3));
                    campos.add(new ExportarCampos("dict.publicoObjetivo", "publicoObjetivo", "PUBLIC_OBJECTIU", true, 1));
                    campos.add(new ExportarCampos("dialogProcedimiento.organoCompetente", "unidadAdministrativaCompetente", "NOM_UA_RESPONSABLE", true, 2));
                    /*campos.add(new ExportarCampos("dialogProcedimiento.organoInstructor", "unidadAdministrativaInstructora", "NOM_UA_INSTRUCTOR", true));*/
                    campos.add(new ExportarCampos("dict.responsable", "responsable", "NOM_RESPONSABLE", true, 1));
                    campos.add(new ExportarCampos("dict.numeroNormativas", "numeroNormas", "NUM_NORMES", true, 1));
                    campos.add(new ExportarCampos("dict.fechaActualizacion", "fechaActualizacion", "DATA_ACTUALITZACIO", true, 1));
                    campos.add(new ExportarCampos("dict.usuarioUltimaActualizacion", "usuarioUltimaActualizacion", "NOM_USUARI_DARRERA_ACT", true, 1));
                    campos.add(new ExportarCampos("dict.comun", "comun", "COMU", true, 1));
                    campos.add(new ExportarCampos("dict.presencial", "presencial", "PRESENCIAL", true, 1));
                    campos.add(new ExportarCampos("dict.telematico", "telematico", "TELEMATICO", true, 1));
                    campos.add(new ExportarCampos("dict.telefonico", "telefonico", "TELEFONICO", true, 1));
                }
            }

            datos = new ExportarDatos(campos);
            datos.setTipo(tipo);
            datos.setFormato(TypeExportarFormato.CSV);
        } else {
            datos = (ExportarDatos) UtilJSF.getValorMochilaByKey("exportar");
            UtilJSF.vaciarMochila();
        }
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
        int posicion = event.getFromIndex();
        int posicion2 = event.getToIndex();
        if (posicion2 > posicion) {
            String valor = datos.getCampos().get(posicion).getNombreCampo();
            for (int i = (posicion + 1); i <= posicion2; i++) {
                datos.getCampos().get(i - 1).setNombreCampo(datos.getCampos().get(i).getNombreCampo());
            }
            datos.getCampos().get(posicion2).setNombreCampo(valor);
        } else {
            String valor = datos.getCampos().get(posicion).getNombreCampo();
            for (int i = posicion; i >= (posicion2 + 1); i--) {
                datos.getCampos().get(i).setNombreCampo(datos.getCampos().get(i - 1).getNombreCampo());
            }
            datos.getCampos().get(posicion2).setNombreCampo(valor);
        }
        //datos.getCampos().get(posicion2).setNombreCampo(valor);
        //datos.getCampos().get(posicion).setNombreCampo(valor2);
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
