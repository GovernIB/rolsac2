package es.caib.rolsac2.ejb.facade.procesos.sia;

import es.caib.rolsac2.commons.plugins.sia.api.model.EnvioSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.NormativaSIA;
import es.caib.rolsac2.ejb.facade.procesos.solr.CastUtil;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SiaUtils {

    public static final Integer SIAJOB_ESTADO_CREADO = 0;
    public static final Integer SIAJOB_ESTADO_EN_EJECUCION = 1;
    public static final Integer SIAJOB_ESTADO_ENVIADO = 2;
    public static final Integer SIAJOB_ESTADO_ENVIADO_CON_ERRORES = 3;
    public static final Integer SIAJOB_ESTADO_ERROR_GRAVE = -1;

    public static final Integer SIAPENDIENTE_ESTADO_CREADO = 0;
    public static final Integer SIAPENDIENTE_ESTADO_CORRECTO = 1;
    public static final Integer SIAPENDIENTE_ESTADO_INCORRECTO = -1;
    public static final Integer SIAPENDIENTE_ESTADO_NO_CUMPLE_DATOS = -2;

    public static final String ESTADO_BAJA = "B";
    public static final String ESTADO_ALTA = "A";
    public static final String ESTADO_MODIFICACION = "M";
    public static final String ESTADO_REACTIVACION = "AC";

    public static final Integer TIPOLOGIA_INTERNO_COMUN = 1;
    public static final Integer TIPOLOGIA_INTERNO_ESPECIFICO = 2;
    public static final Integer TIPOLOGIA_EXTERNO_COMUN = 3;
    public static final Integer TIPOLOGIA_EXTERNO_ESPECIFICO = 4;

    public static final Integer TIPO_TRAMITE_PROC = 1;
    public static final String SI = "S";
    public static final String NO = "N";


    public static final Integer SIAPENDIENTE_PROCEDIMIENTO_EXISTE = 1;
    public static final Integer SIAPENDIENTE_PROCEDIMIENTO_BORRADO = 0;
    public static final Integer SIAPENDIENTE_SERVICIO_EXISTE = 1;
    public static final Integer SIAPENDIENTE_SERVICIO_BORRADO = 0;
    public static final String TRAMITE_PROC = "P";
    public static final String TRAMITE_SERV = "S";


    /**
     * Comprueba si un procedimiento es enviable a SIA. Checks: - Es visible. - Es
     * visible UA. - Tiene codigo DIR3. - Está en SiaUA.
     *
     * @param procedimiento
     * @return
     */
    public static SiaEnviableResultado isEnviable(final UnidadAdministrativaServiceFacade uaService, final ProcedimientoDTO procedimiento, final boolean indexacionForzada) {
        final SiaEnviableResultado resultado = new SiaEnviableResultado(false);
        final StringBuilder mensajeError = new StringBuilder();

        // Si el procedimiento que se pasa es nulo, tiene que salir.
        if (procedimiento == null) {
            resultado.setRespuesta("El procediment és nul.");
            resultado.setIdCentro("");
            resultado.setNotificarSIA(false);
            return resultado;
        }

        // #427 Actualizado el isEnviable para que si una normativa tiene alguna
        // normativa con datos no validos, entonces no enviar pase lo que pase.
        if (procedimiento.getNormativas() != null) {
            for (final NormativaGridDTO normativa : procedimiento.getNormativas()) {
                /* SE ENTIENDE QUE TODAS LAS NORMATIVAS SERAN VALIDAS
                if (normativa.getDatosValidos() == null || !normativa.getDatosValidos()) {
                    resultado.setRespuesta("Té alguna normativa no vàlida.");
                    resultado.setIdCentro("");
                    resultado.setNotificarSIA(false);
                    return resultado;
                }*/
            }
        }

        // Es visible.
        boolean esVisible = true;
        if (!indexacionForzada) {
            esVisible = procedimiento.esVisible();
            if (!esVisible) {
                mensajeError.append("El procediment no és visible.");
            }
        }


        // Es visible UA.
        final boolean isVisibleUA = uaService.isVisibleUA(procedimiento.getUaResponsable());
        if (!isVisibleUA) {
            mensajeError.append("La unitat de l'òrgan resolutori o d'alguns dels seus predecessors és no visible.");
        }

        // Tiene código centro.
        boolean tieneCodigoCentro;
        if (procedimiento.esComun()) {
            tieneCodigoCentro = true;
        } else {
            final String codigoIdCentro = uaService.obtenerCodigoDIR3(procedimiento.getUaResponsable().getCodigo());
            if (codigoIdCentro == null) {
                tieneCodigoCentro = false;
                mensajeError.append("No té codi DIR ni l'òrgan resolutori ni predecessors.");
            } else {
                tieneCodigoCentro = true;
                resultado.setIdCentro(codigoIdCentro);
            }
        }

        // Comprobamos
        // Si cumple los 3 checks.
        // Si nunca ha estado en SIA --> es enviable a SIA como Alta (A)
        // Si esta de baja --> es enviable a SIA como Reactivación (R)
        // Si no.................. --> es enviable a SIA como modificación (M)
        // Si no cumple alguno de los 34 checks.
        // Si está de baja o nunca ha estado en SIA ---> NO es enviable
        // Si no ..... --> es enviable a SIA como baja.
        if (esVisible && tieneCodigoCentro && isVisibleUA) {
            resultado.setNotificarSIA(true);
            if (procedimiento.getEstadoSIA() == null) {
                resultado.setOperacion(SiaUtils.ESTADO_ALTA);
            } else {
                if (SiaUtils.ESTADO_BAJA.equals(procedimiento.getEstadoSIA())) {
                    resultado.setOperacion(SiaUtils.ESTADO_REACTIVACION);
                } else {
                    resultado.setOperacion(SiaUtils.ESTADO_MODIFICACION);
                }
            }

            /*if (indexacionForzada) {
                resultado.setOperacion(SiaUtils.ESTADO_BAJA);
            }*/
        } else {
            if (procedimiento.getEstadoSIA() == null || SiaUtils.ESTADO_BAJA.equals(procedimiento.getEstadoSIA())) {
                resultado.setNotificarSIA(false);
                resultado.setRespuesta(mensajeError.toString());
            } else {
                resultado.setNotificarSIA(true);
                resultado.setOperacion(SiaUtils.ESTADO_BAJA);
            }
        }

        return resultado;
    }

    /**
     * Comprueba si un procedimiento es enviable a SIA. Checks: - Es visible. - Es
     * visible UA. - Tiene codigo DIR3. - Está en SiaUA.
     *
     * @param servicio
     * @return
     */
    public static SiaEnviableResultado isEnviable(final UnidadAdministrativaServiceFacade uaService, final ServicioDTO servicio, final boolean indexacionForzada) {
        final SiaEnviableResultado resultado = new SiaEnviableResultado(false);
        final StringBuilder mensajeError = new StringBuilder();

        // Si el procedimiento que se pasa es nulo, tiene que salir.
        if (servicio == null) {
            resultado.setRespuesta("El procediment és nul.");
            resultado.setIdCentro("");
            resultado.setNotificarSIA(false);
            return resultado;
        }

        // Es visible.
        boolean esVisible = true;
        if (!indexacionForzada) {
            esVisible = servicio.esVisible();
            if (!esVisible) {
                mensajeError.append("El servei no és visible.");
            }
        }

        // Es visible UA.
        final boolean isVisibleUA = uaService.isVisibleUA(servicio.getUaResponsable());
        if (!isVisibleUA) {
            mensajeError.append("La unitat de l'òrgan resolutori o d'alguns dels seus predecessors és no visible.");
        }

        // Tiene código centro.
        boolean tieneCodigoCentro;
        if (servicio.esComun()) {
            tieneCodigoCentro = true;
        } else {
            final String codigoIdCentro = uaService.obtenerCodigoDIR3(servicio.getUaResponsable().getCodigo());
            if (codigoIdCentro == null) {
                tieneCodigoCentro = false;
                mensajeError.append("No té codi DIR ni l'òrgan resolutori ni predecessors.");
            } else {
                tieneCodigoCentro = true;
                resultado.setIdCentro(codigoIdCentro);
            }
        }
        // Comprobamos
        // Si cumple los 3 checks.
        // Si nunca ha estado en SIA --> es enviable a SIA como Alta (A)
        // Si esta de baja --> es enviable a SIA como Reactivación (R)
        // Si no.................. --> es enviable a SIA como modificación (M)
        // Si no cumple alguno de los 34 checks.
        // Si está de baja o nunca ha estado en SIA ---> NO es enviable
        // Si no ..... --> es enviable a SIA como baja.
        if (esVisible && tieneCodigoCentro && isVisibleUA) {
            resultado.setNotificarSIA(true);
            if (servicio.getEstadoSIA() == null) {
                resultado.setOperacion(SiaUtils.ESTADO_ALTA);
            } else {
                if (SiaUtils.ESTADO_BAJA.equals(servicio.getEstadoSIA())) {
                    resultado.setOperacion(SiaUtils.ESTADO_REACTIVACION);
                } else {
                    resultado.setOperacion(SiaUtils.ESTADO_MODIFICACION);
                }
            }

            /*if (indexacionForzada) {
                resultado.setOperacion(SiaUtils.ESTADO_BAJA);
            }*/
        } else {
            if (servicio.getEstadoSIA() == null || SiaUtils.ESTADO_BAJA.equals(servicio.getEstadoSIA())) {
                resultado.setNotificarSIA(false);
                resultado.setRespuesta(mensajeError.toString());
            } else {
                // Sin código SiaUA no se puede enviar
                resultado.setNotificarSIA(true);
                resultado.setOperacion(SiaUtils.ESTADO_BAJA);
            }
        }

        return resultado;
    }

    public static EnvioSIA cast(UnidadAdministrativaServiceFacade uaService, ProcedimientoBaseDTO procedimiento, final SiaEnviableResultado siaEnviableResultado, final SiaCumpleDatos siaCumpleDatos) {
        final EnvioSIA sia = new EnvioSIA();

        final boolean esProcSerInterno = CastUtil.contienePOInterno(procedimiento.getPublicosObjetivo());

        sia.setCdExpediente(procedimiento.getCodigo().toString());
        if (procedimiento.getCodigoSIA() != null) {
            sia.setIdSia(procedimiento.getCodigoSIA().toString());
        }

        sia.setDsProcedimiento(siaCumpleDatos.getNombre());

        sia.setDsObjeto(siaCumpleDatos.getResumen());

        sia.setIdCentroDirectivo(siaEnviableResultado.getIdCentro());
        sia.setIdDepartamento(uaService.obtenerCodigoDIR3(siaCumpleDatos.getSiaUA().getUa().getCodigo()));
        if (procedimiento.esComun()) {
            //TODO Pendiente
            //sia.setUaGest(RolsacPropertiesUtil.getUAComun(false));
        } else if (procedimiento.getUaResponsable() != null && procedimiento.getUaResponsable().getNombre() != null && procedimiento.getUaResponsable().getNombre().getTraduccion("es") != null) {
            sia.setUnidadGestora(procedimiento.getUaResponsable().getNombre().getTraduccion("es"));
        } else {
            sia.setUnidadGestora(procedimiento.getUaResponsable().getNombre().getTraduccion("ca"));
        }

        if (esProcSerInterno) {
            // si es interno siempre se envia como Administracion, y es el único publico
            // objetivo.
            final List dest = new ArrayList();
            dest.add("3");
            sia.setIdDestinatario(dest);
        } else {
            final List destinatarios = new ArrayList();
            final List<TipoPublicoObjetivoEntidadGridDTO> publicoObjs = procedimiento.getPublicosObjetivo();
            int i = 0;
            for (final TipoPublicoObjetivoEntidadGridDTO pObj : publicoObjs) {
                switch (pObj.getCodigo().intValue()) {
                    case 200:
                        destinatarios.add("1");
                        i++;
                        break;
                    case 201:
                        destinatarios.add("2");
                        i++;
                        break;
                    case 202:
                        destinatarios.add("3");
                        i++;
                        break;
                    default:
                        break;
                }
            }
            //TODO para poder avanzar, se añade uno por defecto
            if (destinatarios.isEmpty()) {
                destinatarios.add("1");
            }
            sia.setIdDestinatario(destinatarios);
        }


        Integer nivelAdministrativo = 1;
        if (procedimiento instanceof ProcedimientoDTO) {
            final List<ProcedimientoTramiteDTO> tramites = ((ProcedimientoDTO) procedimiento).getTramites();
            for (final ProcedimientoTramiteDTO tramite : tramites) {
                if (tramite != null && tramite.getFase() == 1) {
                    if (tramite.isTramitElectronica()) {
                        //if (tramite.getVersio() != null
                        //        || (tramite.getUrlExterna() != null && !tramite.getUrlExterna().isEmpty())) {
                        nivelAdministrativo = 4;
                        break;
                    }

                    if (tramite.getListaDocumentos() != null) {
                        for (final ProcedimientoDocumentoDTO documentTramit : tramite.getListaDocumentos()) {
                            //TODO Pendiente
                            //if (documentTramit.get() == 1) {
                            //    nivelAdministrativo = 2;
                            //}
                        }
                    }
                }
            }
        }
        if (procedimiento instanceof ServicioDTO) {

            if (((ServicioDTO) procedimiento).isTramitElectronica()) {
                nivelAdministrativo = 4;
            } else {
                nivelAdministrativo = 1;
            }
        }
        sia.setNivelAdminElectronica(nivelAdministrativo.toString());

        sia.setNormativas(getNormativas(procedimiento.getNormativas()));

        final List<String> materias = new ArrayList<String>();

        if (procedimiento.getTipoVia() == null) {
            sia.setFinVia(SiaUtils.NO);
        } else {
            //TODO Faltaría ver como se hace
            //sia.setFiVia(SiaUtils.NO);
            sia.setFinVia(procedimiento.getCodigo().equals("1") ? SiaUtils.SI : SiaUtils.NO);
        }

        boolean esComunProc = false;
        if (SiaUtils.isActivoSiaComunes()) {
            esComunProc = procedimiento.esComun();
        }

        sia.setTipologia(SiaUtils.getTipologiaTramitacion(esProcSerInterno, esComunProc));

        sia.setEnlaceWeb(SiaUtils.getUrlProcedimiento(esProcSerInterno) + procedimiento.getCodigo().toString());

        //sia.setEstado(procedimiento.getEstadoSIA());


        // Obtenemos el tipo de operacion a partir de validar el procedimiento.
        sia.setOperacion(siaEnviableResultado.getOperacion());

        // Se ha tenido que poner aqui (y se ha simplificado) pq se produce un error al
        // enviar una modificacion.
        if (SiaUtils.ESTADO_ALTA.equals(sia.getOperacion())) {
            sia.setTipoTramite(SiaUtils.TRAMITE_PROC);
        } else {
            sia.setTipoTramite(null);
        }

        sia.setUsuario(siaCumpleDatos.getSiaUA().getUser());
        sia.setPassword(siaCumpleDatos.getSiaUA().getPwd());
        sia.setTipoTramite((procedimiento instanceof ProcedimientoDTO) ? SiaUtils.TRAMITE_PROC : SiaUtils.TRAMITE_SERV);
        //sia.setTipologia(procedimiento.esComun() ? 1 : 2);

        sia.setDisponibleApoderadoHabilitado(procedimiento.isHabilitadoApoderado());
        if (procedimiento.getHabilitadoFuncionario() != null) {
            sia.setDisponibleFuncionarioHabilitado(procedimiento.getHabilitadoFuncionario().equals("S"));
        }
        return sia;
    }

    /**
     * Get tipologia envio SIA.
     *
     * @return
     */
    public static Integer getTipologiaTramitacion(final boolean esInterno, final boolean esComun) {

        int res;
        if (esInterno) {
            if (esComun) {
                res = TIPOLOGIA_INTERNO_COMUN;
            } else {
                res = TIPOLOGIA_INTERNO_ESPECIFICO;
            }
        } else {
            if (esComun) {
                res = TIPOLOGIA_EXTERNO_COMUN;
            } else {
                res = TIPOLOGIA_EXTERNO_ESPECIFICO;
            }
        }

        return res;
    }

    /**
     * Get url envio SIA.
     *
     * @return
     */
    public static String getUrlProcedimiento(final boolean esInterno) {
        if (esInterno) {
            //TODO
            // return System.getProperty("es.caib.rolsac.sia.url.procedimiento.interno").trim();
            return "";
        } else {
            //return System.getProperty("es.caib.rolsac.sia.url").trim() + "tramite/";
            return "www.caib.es/seucaib/";
        }

    }

    private static List<NormativaSIA> getNormativas(List<NormativaGridDTO> normativas) {
        List<NormativaSIA> normativaSIAS = new ArrayList<>();
        if (normativas != null) {
            for (NormativaGridDTO normativa : normativas) {
                NormativaSIA normativaSIA = new NormativaSIA();
                normativaSIA.setTitulo(normativa.getTitulo().getTraduccion("es"));
                normativaSIA.setTipoSia(normativa.getTipoNormativa());

            }
        }
        return normativaSIAS;
    }

    private static boolean isActivoSiaComunes() {
        //TODO pendiente ver que hacen aqui
        return true;
    }

    /**
     * Comprueba si le falta algún dato. Condiciones: - Tiene materias (se comprueba
     * si está activo). - Tiene normativas y uno de tipo SIA (se comprueba si está
     * activo). - Tiene descripción. - Tiene resumen. - Depende de una UA asociada a
     * una entidad raiz. - No está asociado directamente a la entidad raíz.
     *
     * @param procedimiento
     * @param siaEnviableResultado
     * @param activo               Indica si se envia el servicio como activo o no activo (el botón
     *                             para enviar a SIA sin estar visible)
     * @return
     */
    public static SiaCumpleDatos cumpleDatos(final UnidadAdministrativaServiceFacade uaService, final ProcedimientoBaseDTO procedimiento, final SiaEnviableResultado siaEnviableResultado, final boolean activo, final EntidadRaizDTO siaUA) {
        final SiaCumpleDatos resultado = new SiaCumpleDatos(false);
        final StringBuffer mensajeError = new StringBuffer();

        if (procedimiento == null) {
            resultado.setRespuesta("El procediment està nul.");
            resultado.setCumpleDatos(false);
            return resultado;
        }

        boolean tieneSiaUA;
        if (siaUA == null) {
            tieneSiaUA = false;
            mensajeError.append("El procediment no té associat a una entitat arrel.");
        } else {
            tieneSiaUA = true;
            resultado.setSiaUA(siaUA);
        }

        boolean noAsociadoSiaUA = true;
        // No se comprueba si está asociado a la Raíz si es comun
        if (!procedimiento.esComun() && tieneSiaUA) {
            final String codigoDir3IdCentro = uaService.obtenerCodigoDIR3(procedimiento.getUaResponsable().getCodigo());
            final String codigoDir3SiaUA = uaService.obtenerCodigoDIR3(siaUA.getUa().getCodigo());
            if (codigoDir3SiaUA.equals(codigoDir3IdCentro)) {
                mensajeError.append("El procedimiento esta asociado directamente a la entidad raiz.");
                noAsociadoSiaUA = false;
            }
        }

        boolean tieneNombre, tieneResumen, tieneMaterias, tieneNormativas, encontradoTipo;
        if (siaEnviableResultado.getOperacion() != null && SiaUtils.ESTADO_BAJA.equals(siaEnviableResultado.getOperacion())) {
            // En caso de baja, no hace falta comprobar ni normativas, ni materia, ni si
            // tiene tipo, ni nombre ni resumen.
            // Eso si, sin siaUA, es imposible enviar una baja.
            tieneMaterias = true;
            tieneNormativas = true;
            encontradoTipo = true;
            tieneResumen = true;
            tieneNombre = true;
        } else {

            final String nombre = getNombreProcedimiento(procedimiento);

            if (StringUtils.isBlank(nombre)) {
                mensajeError.append("El procediment no té títol.");
                tieneNombre = false;
            } else {
                tieneNombre = true;
                resultado.setNombre(nombre);
            }

            final String resumen = getResumenProcedimiento(procedimiento);
            if (StringUtils.isBlank(resumen)) {
                mensajeError.append("El procediment no té resum.");
                tieneResumen = false;
            } else {
                tieneResumen = true;
                resultado.setResumen(resumen);
            }

            tieneMaterias = true; //TODO Pendiente

            tieneNormativas = procedimiento.getNormativas().size() > 0;
            if (!tieneNormativas && activo) {
                mensajeError.append("No té normatives.");
            }

            encontradoTipo = false;
            if (procedimiento.getNormativas().size() > 0 && activo) {
                for (final NormativaGridDTO norm : procedimiento.getNormativas()) {
                    //if (norm != null && norm.isVisible() && norm.getTipo() != null
                    //        && norm.getTipo().getTipoSia() != null) {
                    //    encontradoTipo = true;
                    //}
                    encontradoTipo = true;
                }
            }

            if (!encontradoTipo) {
                mensajeError.append("Cap de les normatives es visible o té associat un tipus sia.");
            }
        }

        /** Si cumple todos los datos ok, sino incrustamos el mensaje de error. **/
        if (tieneMaterias && tieneNormativas && encontradoTipo && tieneNombre && tieneResumen && tieneSiaUA && noAsociadoSiaUA) {
            resultado.setCumpleDatos(true);
        } else {
            resultado.setCumpleDatos(false);
            resultado.setRespuesta(mensajeError.toString());
        }

        return resultado;
    }

    private static String getNombreProcedimiento(ProcedimientoBaseDTO procedimiento) {
        String tradEs = procedimiento.getNombreProcedimientoWorkFlow().getTraduccion("es");
        String tradCa = procedimiento.getNombreProcedimientoWorkFlow().getTraduccion("ca");
        String resumen = null;
        if (tradEs != null && StringUtils.isNotBlank(tradEs)) {
            resumen = tradEs;
        } else if (tradCa != null) {
            resumen = tradCa;
        }
        if (resumen != null && resumen.length() >= 4000) {
            resumen = resumen.substring(0, 3999);
        }
        return resumen;
    }

    private static String getResumenProcedimiento(ProcedimientoBaseDTO procedimiento) {
        String tradEs = procedimiento.getObjeto().getTraduccion("es");
        String tradCa = procedimiento.getObjeto().getTraduccion("ca");
        String resumen = null;
        if (tradEs != null && StringUtils.isNotBlank(tradEs)) {
            resumen = tradEs;
        } else if (tradCa != null) {
            resumen = tradCa;
        }
        if (resumen != null && resumen.length() >= 4000) {
            resumen = resumen.substring(0, 3999);
        }
        return resumen;
    }

}
