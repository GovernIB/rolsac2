package es.caib.rolsac2.ejb.facade.procesos.solr;

import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class IndexacionUtil {

    /**
     * Devuelve true/false dependiendo de si cumple el mínimo exigido ene l fichero.
     *
     * @param archivo
     * @return
     */
    public static boolean isIndexableSolr(final ProcedimientoDocumentoDTO archivo) {
        boolean retorno = true;
        /*
        //Si archivo es nulo, no intentar indexar.
        if (archivo == null || archivo.getDatos() == null || archivo.getNombre() == null || archivo.getNombre().isEmpty()) {
            retorno = false;
        } else {
            final String sTamanyoMaximo = System.getProperty("es.caib.rolsac.solr.tamanyomaximo");
            Long tamanyoMaximo = 10l;
            try {
                tamanyoMaximo = Long.valueOf(sTamanyoMaximo.trim());
            } catch (Exception e) {
                log.error("Error tratanto de convertir a long el tamanyoMaximo" + sTamanyoMaximo, e);
            }

            if (archivo.getPeso() > tamanyoMaximo * 1024l * 1024l) {
                retorno = false;
            } else {
                //Preparamos la variable extensiones si está a null.
                if (IndexacionUtil.extensiones == null) {
                    final String ficheroPermitidos = System.getProperty("es.caib.rolsac.solr.ficheros");
                    IndexacionUtil.extensiones = new HashMap<String, String>();
                    String[] extensionesSplit = ficheroPermitidos.split(",");
                    for (String extensionSplit : extensionesSplit) {
                        //Se limpian las extensiones.
                        extensiones.put(extensionSplit.trim().toLowerCase(Locale.ITALIAN), extensionSplit);
                    }
                }

                //Si el nombre esta vacío, entonces se da por incorrecto.
                if (archivo.getNombre() == null || archivo.getNombre().isEmpty()) {
                    retorno = false;
                } else {
                    //Extraemos la extensión.
                    final String extension = FilenameUtils.getExtension(archivo.getNombre().trim()).toLowerCase(Locale.ITALIAN);

                    //Comprobamos si
                    if (extension == null || extension.isEmpty()) {
                        retorno = false;
                    } else if (!extensiones.containsKey(extension)) {
                        retorno = false;
                    }

                }
            }
        } */
        return retorno;
    }


    public static String calcularPathTextUO(UnidadAdministrativaDTO unidadAdministrativa, String idioma) {

        if (unidadAdministrativa == null) {
            return null;
        }


        StringBuffer textoOptional = new StringBuffer();
        //TODO Pendiente
        return "";
    }


    public static String calcularExtensionArchivo(String filename) {
        if (filename == null) return null;
        String extension = FilenameUtils.getExtension(StringUtils.trim(filename));
        return StringUtils.lowerCase(extension);
    }


    /**
     * Compruebo si es telemático algún tramite del procedimiento.
     *
     * @param trams
     * @return
     */
    public static boolean isTelematicoProcedimiento(List<ProcedimientoTramiteDTO> trams) {
        boolean telematico = false;
        if (trams != null) {
            for (ProcedimientoTramiteDTO tramite : trams) {
                if (tramite != null && tramite.isTramitElectronica()) {
                    telematico = true;
                    break;
                }
            }
        }
        return telematico;
    }

    /**
     * Se obtiene el trámite de inicio de un procedimiento, en caso de no tenerlo, devuelve nulo.
     *
     * @param trams
     * @return
     */
    public static ProcedimientoTramiteDTO getTramiteInicio(List<ProcedimientoTramiteDTO> trams) {
        ProcedimientoTramiteDTO tramiteInicio = null;
        if (trams != null) {
            final Integer FASE_INICIO = 1;
            for (ProcedimientoTramiteDTO tramite : trams) {

                if (tramite != null && tramite.getFase() == FASE_INICIO) {
                    tramiteInicio = tramite;
                    break;
                }
            }
        }
        return tramiteInicio;
    }

}
