package es.caib.rolsac2.commons.plugins.sia.sia;

import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIA;
import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIAException;
import es.caib.rolsac2.commons.plugins.sia.api.model.EnvioSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.NormativaSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.Properties;

public class SiaWSPlugin extends AbstractPluginProperties implements IPluginSIA {

    private static final String ACTIVO_ENVIO = "activarEnvio";

    private static final String USER = "usr";

    private static final String PASSWORD = "pwd";

    private static final String URL = "url";


    public SiaWSPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public ResultadoSIA enviarSIA(EnvioSIA envioSIA, final boolean borrado, final boolean noactivo) throws IPluginSIAException {
        final ResultadoSIA resultadoSIA = new ResultadoSIA();
        resultadoSIA.setOperacion(envioSIA.getOperacion());
        if (isActivoEnvio()) {
            final String usuario = getProperty(USER);
            final String password = getProperty(PASSWORD);
            final String url = getProperty(URL);
            SiaClient client;
            try {
                client = new SiaClient(url, usuario, password);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            ParamSIA.ACTUACIONES actuaciones;
            try {
                if (noactivo) {
                    actuaciones = cargarDatosSiaNoActivo(envioSIA);
                } else if (borrado) {
                    actuaciones = cargarDatosSiaBorrado(envioSIA);
                } else {
                    actuaciones = cargarDatosSia(envioSIA);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            final ParamSIA parameters = new ParamSIA();
            parameters.setUser(usuario);
            parameters.setPassword(password);
            parameters.setACTUACIONES(actuaciones);

            final EnviaSIA resultado = client.actualizarSIAV31(parameters);

            final EnviaSIA.ACTUACIONES res = resultado.getACTUACIONES();

            int correctos = 0;
            int incorrectos = 0;

            if (res != null) {
                resultadoSIA.setResultado(ResultadoSIA.RESULTADO_OK);
                for (final EnviaSIA.ACTUACIONES.ACTUACION envia : res.getACTUACION()) {
                    if (envia.getCORRECTO().equals(SiaConstantes.SI)) {
                        correctos++;
                        resultadoSIA.setCodSIA(envia.getCODIGOACTUACION());
                    } else {
                        resultadoSIA.setResultado(ResultadoSIA.RESULTADO_ERROR);
                        incorrectos++;
                        final ERRORES errores = envia.getERRORES();
                        resultadoSIA.setMensaje("");
                        for (final ERRORES.ERROR error : errores.getERROR()) {
                            resultadoSIA.setMensaje(resultadoSIA.getMensaje() + error.getERROR() + ":" + error.getDESCERROR() + " " + resultadoSIA.getMensaje() + "<br />");
                        }
                    }
                    if (SiaConstantes.ESTADO_BAJA.compareTo(envia.getOPERACION()) == 0) {
                        resultadoSIA.setEstadoSIA(SiaConstantes.ESTADO_BAJA);
                    } else {
                        resultadoSIA.setEstadoSIA(SiaConstantes.ESTADO_ALTA);
                    }
                }

                resultadoSIA.setCorrectos(correctos);
                resultadoSIA.setIncorrectos(incorrectos);
            } else {
                final int aleatorio = (int) (Math.random() * (100));
                if (aleatorio % 8 == 0) {
                    resultadoSIA.setMensaje("Error aleatorio, estas en modo prueba!!");
                    resultadoSIA.setCorrectos(0);
                    resultadoSIA.setIncorrectos(1);
                    resultadoSIA.setResultado(ResultadoSIA.RESULTADO_ERROR);
                } else {
                    resultadoSIA.setCodSIA(String.valueOf((int) (Math.random() * (10000000))));
                    resultadoSIA.setCorrectos(1);
                    resultadoSIA.setIncorrectos(0);
                    resultadoSIA.setResultado(ResultadoSIA.RESULTADO_OK);
                    if (aleatorio % 8 == 2 || aleatorio % 8 == 4) {
                        resultadoSIA.setEstadoSIA(SiaConstantes.ESTADO_BAJA);
                    } else {
                        resultadoSIA.setEstadoSIA(SiaConstantes.ESTADO_ALTA);
                    }
                }
            }
        }
        return resultadoSIA;
    }


    /**
     * @param sia
     * @return
     * @throws Exception
     */
    private static ParamSIA.ACTUACIONES cargarDatosSiaNoActivo(final EnvioSIA sia) throws Exception {

        final ParamSIA.ACTUACIONES.ACTUACION paramSia = new ParamSIA.ACTUACIONES.ACTUACION();
        paramSia.setACTIVO("N");

        if (sia.getIdSia() == null || sia.getIdSia().isEmpty()) {
            paramSia.setCODIGOACTUACION("inventadoCAIB"); // Obligan a que se introduzca, en caso de alta, lo generan
            // ellos en la respuesta.
        } else {
            paramSia.setCODIGOACTUACION(sia.getIdSia());
        }
        paramSia.setCODIGOORIGEN(sia.getCdExpediente());

        paramSia.setDENOMINACION(sia.getDsObjeto());
        paramSia.setDESCRIPCION(sia.getDsProcedimiento());
        final ORGANISMORESPONSABLE organismoResponsable = new ORGANISMORESPONSABLE();
        // Fix 17/02. Pasado el id del centro a nivel 2 e incluido como nivel1 el
        // departamento que viene por propiedades.
        organismoResponsable.setCODORGANISMORESPONSABLEN1(sia.getIdDepartamento());
        organismoResponsable.setCODORGANISMORESPONSABLEN2(sia.getIdCentroDirectivo());
        paramSia.setORGANISMORESPONSABLE(organismoResponsable);

        final DESTINATARIOS destinatarios = new DESTINATARIOS();

        for (final String pObj : sia.getIdDestinatario()) {
            DESTINATARIOS.DESTINATARIO destinatario = new DESTINATARIOS.DESTINATARIO();
            destinatario.setCODDESTINATARIO(pObj);
            destinatarios.getDESTINATARIO().add(destinatario);
        }
        paramSia.setDESTINATARIOS(destinatarios);
        paramSia.setCODNIVELADMINISTRACIONELECTRONICA(sia.getNivelAdminElectronica());

        final NORMATIVAS normativasCorrectas = new NORMATIVAS();
        for (final NormativaSIA norm : sia.getNormativas()) {
            final NORMATIVAS.NORMATIVA nor = new NORMATIVAS.NORMATIVA();
            if (norm == null || norm.getTipoSia() == null /*!norm.isVisible()*/ /*|| norm.getTipo().getTipoSia() == null*/) {
                continue;
            }
            nor.setCODRANGO(norm.getTipoSia());
            nor.setTITULO(norm.getTitulo());
            normativasCorrectas.getNORMATIVA().add(nor);
        }

        if (!normativasCorrectas.getNORMATIVA().isEmpty()) {
            final NORMATIVAS normativas = new NORMATIVAS();
            normativas.getNORMATIVA().addAll(normativasCorrectas.getNORMATIVA());
            paramSia.setNORMATIVAS(normativas);
        }

        if (!sia.getMaterias().isEmpty()) {
            final MATERIAS materias = new MATERIAS();
            for (String mat : sia.getMaterias()) {
                MATERIAS.MATERIA materia = new MATERIAS.MATERIA();
                materia.setCODMATERIA(mat);
                materias.getMATERIA().add(materia);
            }
            paramSia.setMATERIAS(materias);
        }
        paramSia.setFINVIA(sia.getFinVia());

        if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_INTERNO_COMUN) {
            paramSia.setINTERNO(SiaConstantes.SI);
            paramSia.setESCOMUN(SiaConstantes.SI);
        } else if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_INTERNO_ESPECIFICO) {
            paramSia.setINTERNO(SiaConstantes.SI);
            paramSia.setESCOMUN(SiaConstantes.NO);
        } else if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_EXTERNO_COMUN) {
            paramSia.setINTERNO(SiaConstantes.NO);
            paramSia.setESCOMUN(SiaConstantes.SI);
        } else if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_EXTERNO_ESPECIFICO) {
            paramSia.setINTERNO(SiaConstantes.NO);
            paramSia.setESCOMUN(SiaConstantes.NO);
        }

        ObjectFactory factory = new ObjectFactory();

        if (sia.getDisponibleApoderadoHabilitado()) {
            paramSia.setDISPONIBLEAPODERADOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEAPODERADOHABILITADO(SiaConstantes.SI));
        } else {
            paramSia.setDISPONIBLEAPODERADOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEAPODERADOHABILITADO(SiaConstantes.NO));
        }

        if (sia.getDisponibleFuncionarioHabilitado()) {
            paramSia.setDISPONIBLEFUNCIONARIOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEFUNCIONARIOHABILITADO(SiaConstantes.SI));
        } else {
            paramSia.setDISPONIBLEFUNCIONARIOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEFUNCIONARIOHABILITADO(SiaConstantes.NO));
        }

        paramSia.setTIPOTRAMITE(sia.getTipoTramite());

        paramSia.setUNIDADGESTORATRAMITE(sia.getUnidadGestora());


        paramSia.setENLACEWEB(sia.getEnlaceWeb());
        paramSia.setOPERACION(sia.getOperacion());


        final ParamSIA.ACTUACIONES actuaciones = new ParamSIA.ACTUACIONES();
        actuaciones.getACTUACION().add(paramSia);

        return actuaciones;
    }

    /**
     * @param sia
     * @return
     * @throws Exception
     */
    private static ParamSIA.ACTUACIONES cargarDatosSiaBorrado(final EnvioSIA sia) throws Exception {
        final ParamSIA.ACTUACIONES.ACTUACION paramSia = new ParamSIA.ACTUACIONES.ACTUACION();

        paramSia.setCODIGOACTUACION(sia.getIdSia());
        paramSia.setCODIGOORIGEN(sia.getCdExpediente());
        paramSia.setOPERACION(SiaConstantes.ESTADO_BAJA);

        final ParamSIA.ACTUACIONES actuaciones = new ParamSIA.ACTUACIONES();
        actuaciones.getACTUACION().add(paramSia);

        return actuaciones;
    }

    /**
     * @param sia
     * @return
     * @throws Exception
     */
    private static ParamSIA.ACTUACIONES cargarDatosSia(final EnvioSIA sia) throws Exception {
        final ParamSIA.ACTUACIONES.ACTUACION paramSia = new ParamSIA.ACTUACIONES.ACTUACION();

        if (sia.getOperacion() != null && SiaConstantes.ESTADO_ALTA.equals(sia.getOperacion())) {
            paramSia.setCODIGOACTUACION("inventadoCAIB"); // Obligan a que se introduzca, en caso de alta, lo generan
            // ellos en la respuesta.
        } else {
            paramSia.setCODIGOACTUACION(sia.getIdSia());
        }
        paramSia.setCODIGOORIGEN(sia.getCdExpediente());

        paramSia.setDENOMINACION(sia.getDsProcedimiento());
        // paramSia.setTITULOCIUDADANO(sia.getTitulo());
        paramSia.setDESCRIPCION(sia.getDsObjeto());
        final ORGANISMORESPONSABLE organismoResponsable = new ORGANISMORESPONSABLE();
        // Fix 17/02. Pasado el id del centro a nivel 2 e incluido como nivel1 el
        // departamento que viene por propiedades.
        organismoResponsable.setCODORGANISMORESPONSABLEN1(sia.getIdDepartamento());
        organismoResponsable.setCODORGANISMORESPONSABLEN2(sia.getIdCentroDirectivo());
        paramSia.setORGANISMORESPONSABLE(organismoResponsable);

        final DESTINATARIOS destinatarios = new DESTINATARIOS();
        for (final String pObj : sia.getIdDestinatario()) {
            DESTINATARIOS.DESTINATARIO destinatario = new DESTINATARIOS.DESTINATARIO();
            destinatario.setCODDESTINATARIO(pObj);
            destinatarios.getDESTINATARIO().add(destinatario);
        }
        paramSia.setDESTINATARIOS(destinatarios);
        paramSia.setCODNIVELADMINISTRACIONELECTRONICA(sia.getNivelAdminElectronica());

        final NORMATIVAS normativasCorrectas = new NORMATIVAS();
        for (final NormativaSIA norm : sia.getNormativas()) {
            final NORMATIVAS.NORMATIVA nor = new NORMATIVAS.NORMATIVA();
            if (norm == null || norm.getTipoSia() == null /*|| !norm.isVisible() || norm.getTipo().getTipoSia() == null*/) {
                continue;
            }
            nor.setCODRANGO(norm.getTipoSia());
            nor.setTITULO(norm.getTitulo());
            normativasCorrectas.getNORMATIVA().add(nor);
        }

        paramSia.setNORMATIVAS(normativasCorrectas);

        final MATERIAS materias = new MATERIAS();

        for (final String mat : sia.getMaterias()) {
            MATERIAS.MATERIA materia = new MATERIAS.MATERIA();
            materia.setCODMATERIA(mat);
            materias.getMATERIA().add(materia);
        }
        paramSia.setMATERIAS(materias);


        paramSia.setFINVIA(sia.getFinVia());

        if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_INTERNO_COMUN) {
            paramSia.setINTERNO(SiaConstantes.SI);
            paramSia.setESCOMUN(SiaConstantes.SI);
        } else if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_INTERNO_ESPECIFICO) {
            paramSia.setINTERNO(SiaConstantes.SI);
            paramSia.setESCOMUN(SiaConstantes.NO);
        } else if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_EXTERNO_COMUN) {
            paramSia.setINTERNO(SiaConstantes.NO);
            paramSia.setESCOMUN(SiaConstantes.SI);
        } else if (sia.getTipologia() == SiaConstantes.TIPOLOGIA_EXTERNO_ESPECIFICO) {
            paramSia.setINTERNO(SiaConstantes.NO);
            paramSia.setESCOMUN(SiaConstantes.NO);
        }

        ObjectFactory factory = new ObjectFactory();

        if (sia.getDisponibleApoderadoHabilitado()) {
            paramSia.setDISPONIBLEAPODERADOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEAPODERADOHABILITADO(SiaConstantes.SI));
        } else {
            paramSia.setDISPONIBLEAPODERADOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEAPODERADOHABILITADO(SiaConstantes.NO));
        }

        if (sia.getDisponibleFuncionarioHabilitado()) {
            paramSia.setDISPONIBLEFUNCIONARIOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEFUNCIONARIOHABILITADO(SiaConstantes.SI));
        } else {
            paramSia.setDISPONIBLEFUNCIONARIOHABILITADO(factory.createParamSIAACTUACIONESACTUACIONDISPONIBLEFUNCIONARIOHABILITADO(SiaConstantes.NO));
        }


        paramSia.setTIPOTRAMITE(sia.getTipoTramite());

        paramSia.setUNIDADGESTORATRAMITE(sia.getUnidadGestora());

        paramSia.setENLACEWEB(sia.getEnlaceWeb());

        paramSia.setACTIVO(SiaConstantes.SI);

        paramSia.setOPERACION(sia.getOperacion());

        final ParamSIA.ACTUACIONES actuaciones = new ParamSIA.ACTUACIONES();
        actuaciones.getACTUACION().add(paramSia);

        return actuaciones;
    }

    /**
     * Se considera activo si no está introducido o si está introducido y es true
     *
     * @return
     */
    private Boolean isActivoEnvio() {
        return getProperty(ACTIVO_ENVIO) == null || (getProperty(ACTIVO_ENVIO) != null && getProperty(ACTIVO_ENVIO).equals("true"));
    }
}

