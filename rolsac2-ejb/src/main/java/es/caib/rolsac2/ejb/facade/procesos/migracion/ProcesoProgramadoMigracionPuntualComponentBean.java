package es.caib.rolsac2.ejb.facade.procesos.migracion;

import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.ejb.facade.procesos.ProcesosExecComponentFacade;
import es.caib.rolsac2.service.facade.MigracionServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.migracion.FicheroInfo;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Proceso migracion.
 *
 * @author Indra
 */
@Stateless(name = "procesoProgramadoMigracionComponent")
@Local(ProcesoProgramadoFacade.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public class ProcesoProgramadoMigracionPuntualComponentBean implements ProcesoProgramadoFacade {

    /**
     * Código interno del proceso
     */
    private static final String CODIGO_PROCESO = "MIGRA_PUNT";

    @Inject
    private ProcesoServiceFacade procesoService;


    @Inject
    private MigracionServiceFacade migracionService;

    @Inject
    private SystemServiceFacade systemService;

    /**
     * Componente ejecucion procesos.
     */
    @Inject
    ProcesosExecComponentFacade procesosExecComponent;


    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoMigracionPuntualComponentBean.class);

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getCodigoProceso() {
        return CODIGO_PROCESO;
    }

    private final static Integer TAMANYO_BLOQUE = 10;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ResultadoProcesoProgramado ejecutar(final Long instanciaProceso, final ListaPropiedades params, Long idEntidad) {
        log.info("Ejecución proceso migracion");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final StringBuilder mensajeTraza = new StringBuilder();
        String fechaInicio = "La dada de inici es " + sdf.format(new Date());
        detalles.addPropiedad("Informació del procés", fechaInicio);


        procesosExecComponent.auditarMitadProceso(instanciaProceso, mensajeTraza.toString() + "\n Estado actual: Inicio de la ejecución.");

        try {
            String borrarDatos = params.getPropiedad("borrarDatos");
            String cargarUsuarios = params.getPropiedad("cargarUsuarios");
            String cargarDatosTipos = params.getPropiedad("cargarDatosTipos");
            String cargarDatosUA = params.getPropiedad("cargarUas");
            String cargarDatosNormativas = params.getPropiedad("cargarNormativas");
            String cargarDatosProcedimientos = params.getPropiedad("cargarProcedimientos");
            String cargarDatosServicios = params.getPropiedad("cargarServicios");
            String cargarDocumentos = params.getPropiedad("cargarDocumentos");
            String usuarios = params.getPropiedad("usuarios");
            Long entidad = Long.valueOf(params.getPropiedad("entidad"));
            Long uaRaiz = Long.valueOf(params.getPropiedad("uaRaiz"));
            String estadoMigracion = "";
            detalles.addPropiedades(params);

            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            res.setFinalizadoOk(true);

            if (borrarDatos != null && "true".equals(borrarDatos)) {
                String result = migracionService.ejecutarMetodo("MIGRAR_BORRARDATOS", entidad.toString(), uaRaiz.toString()) + "\n";
                mensajeTraza.append(result);
                detalles.addPropiedad("Borrar datos", "Ejecutado correctamente");
                estadoMigracion += "Borrado los datos 100%\n";
            }

            if (cargarUsuarios != null && "true".equals(cargarUsuarios)) {
                String result = migracionService.migrarUsuarios();
                mensajeTraza.append(result);
                detalles.addPropiedad("Migrar usuarios", "Ejecutado correctamente");
                estadoMigracion += "Migrado los usuarios 100%\n";
            }


            if (cargarDatosTipos != null && "true".equals(cargarDatosTipos)) {
                detalles.addPropiedad("Cargar datos tipo", "Ejecutado correctamente");
            }

            if (cargarDatosUA != null && "true".equals(cargarDatosUA)) {

                mensajeTraza.append("INICI MIGRACIO UAs \n");
                List<BigDecimal> idUAs = migracionService.getUAs(idEntidad, uaRaiz);
                if (idUAs != null && !idUAs.isEmpty()) {
                    // Recorre el array en bloques de 10 elementos
                    for (int i = 0; i < idUAs.size(); i += TAMANYO_BLOQUE) {

                        // Obtiene el bloque actual de 10 elementos
                        List<BigDecimal> bloque = obtenerBloque(idUAs, i, TAMANYO_BLOQUE);
                        String result = migracionService.migrarUAs(bloque, entidad, uaRaiz, usuarios);
                        mensajeTraza.append(result);
                        procesosExecComponent.auditarMitadProceso(instanciaProceso, estadoMigracion + "\n Estado actual: Migrando UAs " + getPorcentaje(i, idUAs.size()) + " \n\n" + mensajeTraza.toString());

                    }

                }
                mensajeTraza.append("FI MIGRACIO UAs \n");
                detalles.addPropiedad("Cargar datos UA", "Ejecutado correctamente");
                estadoMigracion += "Migrado las uas 100%\n";
            }

            if (cargarDatosNormativas != null && "true".equals(cargarDatosNormativas)) {
                mensajeTraza.append("INICI MIGRACIO NORMATIVAs \n");
                List<BigDecimal> idNormativas = migracionService.getNormativas(idEntidad);
                if (idNormativas != null && !idNormativas.isEmpty()) {
                    // Recorre el array en bloques de 10 elementos
                    for (int i = 0; i < idNormativas.size(); i += TAMANYO_BLOQUE) {

                        // Obtiene el bloque actual de 10 elementos
                        List<BigDecimal> bloque = obtenerBloque(idNormativas, i, TAMANYO_BLOQUE);
                        String result = migracionService.migrarNormativas(bloque, entidad);
                        mensajeTraza.append(result);
                        procesosExecComponent.auditarMitadProceso(instanciaProceso, estadoMigracion + "\n Estado actual: Migrando Normativas " + getPorcentaje(i, idNormativas.size()) + " \n\n" + mensajeTraza.toString());

                    }

                }
                String result = migracionService.migrarNormativasAfe();
                mensajeTraza.append(result);
                mensajeTraza.append("FI MIGRACIO NORMATIVAs \n");
                detalles.addPropiedad("Cargar datos Normativas", "Ejecutado correctamente");
                estadoMigracion += "Migrado las normativas 100%\n";
            }

            if ((cargarDatosProcedimientos != null && "true".equals(cargarDatosProcedimientos)) || (cargarDatosServicios != null && "true".equals(cargarDatosServicios))) {
                mensajeTraza.append("DESACTIVAMOS RESTRICCION DOCUMENTOS \n");
                String result = migracionService.desactivarRestriccionDocumento();
                mensajeTraza.append(result + "\n");
            }
            if (cargarDatosProcedimientos != null && "true".equals(cargarDatosProcedimientos)) {

                mensajeTraza.append("INICI MIGRACIO PROCEDIMIENTOS \n");
                List<BigDecimal> idProcedimientos = migracionService.getProcedimientos(idEntidad, uaRaiz);
                if (idProcedimientos != null && !idProcedimientos.isEmpty()) {
                    // Recorre el array en bloques de 10 elementos
                    for (int i = 0; i < idProcedimientos.size(); i += TAMANYO_BLOQUE) {

                        // Obtiene el bloque actual de 10 elementos
                        List<BigDecimal> bloque = obtenerBloque(idProcedimientos, i, TAMANYO_BLOQUE);
                        String result = migracionService.migrarProcedimientos(bloque, entidad, uaRaiz);
                        mensajeTraza.append(result);
                        procesosExecComponent.auditarMitadProceso(instanciaProceso, estadoMigracion + "\n Estado actual: Migrando Procedimientos " + getPorcentaje(i, idProcedimientos.size()) + " \n\n" + mensajeTraza.toString());
                    }
                }
                mensajeTraza.append("FI MIGRACIO PROCEDIMIENTOS \n");
                detalles.addPropiedad("Cargar datos Procedimientos", "Ejecutado correctamente");
                estadoMigracion += "Migrado los procedimientos 100%\n";
            }

            if (cargarDatosServicios != null && "true".equals(cargarDatosServicios)) {

                mensajeTraza.append("INICI MIGRACIO SERVICIOS \n");
                List<BigDecimal> idServicios = migracionService.getServicios(idEntidad, uaRaiz);
                if (idServicios != null && !idServicios.isEmpty()) {
                    // Recorre el array en bloques de 10 elementos
                    for (int i = 0; i < idServicios.size(); i += TAMANYO_BLOQUE) {

                        // Obtiene el bloque actual de 10 elementos
                        List<BigDecimal> bloque = obtenerBloque(idServicios, i, TAMANYO_BLOQUE);
                        String result = migracionService.migrarServicios(bloque, entidad, uaRaiz);
                        mensajeTraza.append(result);
                        procesosExecComponent.auditarMitadProceso(instanciaProceso, estadoMigracion + "\n Estado actual: Migrando Servicios " + getPorcentaje(i, idServicios.size()) + " \n\n" + mensajeTraza.toString());
                    }
                }
                mensajeTraza.append("FI MIGRACIO SERVICIOS \n");
                detalles.addPropiedad("Cargar datos Servicios", "Ejecutado correctamente");
                estadoMigracion += "Migrado los servicios 100%\n";
            }
            if (cargarDocumentos != null && "true".equals(cargarDocumentos)) {
                mensajeTraza.append("MIGRAMOS DOCUMENTOS DOCUMENTOS \n");
                String rutaRolsac1 = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_ROLSAC1);
                String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
                List<FicheroInfo> idDocs = migracionService.getDocumentosProcedimientos(idEntidad, uaRaiz);

                if (idDocs != null && !idDocs.isEmpty()) {
                    // Lo vamos a recorrer uno a uno, porque como hay creación de documentos en físico, mejor que si falla 1, no falle todo un bloque
                    for (int i = 0; i < idDocs.size(); i++) {
                        String result = migracionService.migrarDocumentos(idDocs.get(i), entidad, uaRaiz, rutaRolsac1, ruta, TypeFicheroExterno.PROCEDIMIENTO_DOCUMENTOS);
                        mensajeTraza.append(result);
                        procesosExecComponent.auditarMitadProceso(instanciaProceso, estadoMigracion + "\n Estado actual: Migrando Doc proced/serv " + getPorcentaje(i, idDocs.size()) + " \n\n" + mensajeTraza.toString());
                    }
                    estadoMigracion += "Migrado los docs procedimientos/servicios 100%\n";
                }
                idDocs = migracionService.getDocumentosNormativas(idEntidad, uaRaiz);

                if (idDocs != null && !idDocs.isEmpty()) {
                    // Lo vamos a recorrer uno a uno, porque como hay creación de documentos en físico, mejor que si falla 1, no falle todo un bloque
                    for (int i = 0; i < idDocs.size(); i++) {
                        String result = migracionService.migrarDocumentos(idDocs.get(i), entidad, uaRaiz, rutaRolsac1, ruta, TypeFicheroExterno.NORMATIVA_DOCUMENTO);
                        mensajeTraza.append(result);
                        procesosExecComponent.auditarMitadProceso(instanciaProceso, estadoMigracion + "\n Estado actual: Migrando Doc normativas " + getPorcentaje(i, idDocs.size()) + " \n\n" + mensajeTraza.toString());
                    }
                    estadoMigracion += "Migrado los docs normativas 100%\n";
                }
                mensajeTraza.append("FI MIGRACIO DOCUMENTOS \n");
                detalles.addPropiedad("Cargar datos Documentos", "Ejecutado correctamente");
            }
            if ((cargarDatosProcedimientos != null && "true".equals(cargarDatosProcedimientos)) || (cargarDatosServicios != null && "true".equals(cargarDatosServicios))) {
                mensajeTraza.append("ACTIVAMOS RESTRICCION DOCUMENTOS \n");
                String result = migracionService.activarRestriccionDocumento();
                mensajeTraza.append(result + "\n");
            }
            detalles.addPropiedad("Fin del procés", fechaFin);
            res.setDetalles(detalles);
            res.setMensajeErrorTraza(mensajeTraza.toString());

        } catch (Exception e) {
            log.error("Error en el proceso programado", e);
            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            detalles.addPropiedad("Fin del procés", fechaFin);
            res.setDetalles(detalles);
            res.setMensajeErrorTraza(mensajeTraza.toString() + "Se ha producido un error no controlado en el proceso migracion. " + e.getLocalizedMessage());
            res.setFinalizadoOk(false);
        } finally {
            try {
                // migracionService.ejecutarActivarRestriccionesSecuencias();
                migracionService.ejecutarRevisarSecuencias();
            } catch (Exception e2) {
                log.error("Error seteando secuencias y restricciones", e2);
            }
        }
        return res;
    }

    private String getPorcentaje(int i, int size) {
        return ((i * 100) / size) + "%";
    }

    //Método para obtener un bloque de elementos
    private List<BigDecimal> obtenerBloque(List<BigDecimal> idUAs, int inicio, Integer tamanoBloque) {
        int fin = Math.min(inicio + tamanoBloque, idUAs.size());  // Calcula el índice final del bloque
        int tamano = fin - inicio;  // Calcula el tamaño real del bloque

        List<BigDecimal> bloque = new ArrayList<>();  // Crea un nuevo array para el bloque

        // Copia los elementos del array original al bloque
        for (int i = inicio; i < fin; i++) {
            bloque.add(idUAs.get(i));
        }

        return bloque;  // Retorna el bloque de elementos
    }

    //Método para obtener un bloque de elementos
    private List<Long> obtenerBloqueLong(List<Long> idUAs, int inicio, Integer tamanoBloque) {
        int fin = Math.min(inicio + tamanoBloque, idUAs.size());  // Calcula el índice final del bloque
        int tamano = fin - inicio;  // Calcula el tamaño real del bloque

        List<Long> bloque = new ArrayList<>();  // Crea un nuevo array para el bloque

        // Copia los elementos del array original al bloque
        for (int i = inicio; i < fin; i++) {
            bloque.add(idUAs.get(i));
        }

        return bloque;  // Retorna el bloque de elementos
    }

}
