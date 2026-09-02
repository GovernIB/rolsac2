package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JDocumentoNormativaTraduccion;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProceso;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoDocumentoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Mensaje;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.migracion.FicheroInfo;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.utils.UtilJSON;
import org.hibernate.procedure.ProcedureOutputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(ProcesoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class MigracionRepositoryBean extends AbstractCrudRepository<JProceso, Long> implements MigracionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MigracionRepositoryBean.class);

    protected MigracionRepositoryBean() {
        super(JProceso.class);
    }


    @Override
    public String importarUA(long idUA, Long codigoEntidad, Long idUARaiz) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("MIGRAR_UA");
        String resultado = "";
        try {
            query.registerStoredProcedureParameter("codigoUA", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("codigoEntidad", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("codigoUARaiz", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

            query.setParameter("codigoUA", idUA);
            query.setParameter("codigoEntidad", codigoEntidad);
            query.setParameter("codigoUARaiz", idUARaiz);
            query.setParameter("resultado", resultado);

            // call the stored procedure and get the result
            query.execute();
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando ua ", e);
            return e.getMessage();
        }
        String retorno = "     " + query.getOutputParameterValue("resultado") + "\n";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }

    @Override
    public String ejecutarMetodo(String metodo, String param1, String param2) {

        String resultado = "";
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery(metodo);
        query.registerStoredProcedureParameter("codigoUA", String.class/*Long.class*/, ParameterMode.IN);
        query.registerStoredProcedureParameter("codigoEntidad", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

        query.setParameter("codigoEntidad", param1);
        query.setParameter("codigoUA", param2);
        query.setParameter("resultado", resultado);

        // call the stored procedure and get the result
        query.execute();
        return (String) query.getOutputParameterValue("resultado");
    }

    @Override
    public void ejecutarMetodo(String metodo) {

        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery(metodo);
        query.execute();
    }

    /**
     * Se tiene que devolver la lista de ambas query quitando repetidos.
     *
     * @param idEntidad Codigo entidad
     * @return
     */
    @Override
    public List<BigDecimal> getNormativas(Long idEntidad) {
        Query query = this.entityManager.createNativeQuery("   SELECT NOR_CODI  FROM R1_NORMAT WHERE NOR_CODUNA IN (SELECT UNAD_CODIGO FROM RS2_UNIADM) " +
                " UNION " +
                " SELECT DISTINCT UNN_CODNOR FROM R1_UNIADM_NORM WHERE UNN_CODUNA IN (SELECT UNAD_CODIGO FROM RS2_UNIADM) ");

        List<BigDecimal> retornoSQL = query.getResultList();
        //Quitar duplicados
        List<BigDecimal> retorno = new ArrayList<>();
        for (BigDecimal bigDecimal : retornoSQL) {
            if (!retorno.contains(bigDecimal)) {
                retorno.add(bigDecimal);
            }
        }
        return retorno;
    }

    @Override
    public List<BigDecimal> getProcedimientos(Long idEntidad, Long codigoUARaiz) {
        Query query = this.entityManager.createNativeQuery("   SELECT PRO_CODI  FROM R1_PROCEDIMIENTOS WHERE CHECK_CUELGA_UA_PROC(PRO_CODUNA, " + codigoUARaiz + ") = 1 ");
        return query.getResultList();
    }

    @Override
    public List<BigDecimal> getServicios(Long idEntidad, Long codigoUARaiz) {
        Query query = this.entityManager.createNativeQuery("   SELECT SER_CODI  FROM R1_SERVICIOS WHERE CHECK_CUELGA_UA_PROC(SER_SERRSP, " + codigoUARaiz + ") = 1");
        return query.getResultList();
    }


    @Override
    public String importarNormativa(Long idNormativa, Long codigoEntidad) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("MIGRAR_NORMATIVA");
        String resultado = "";
        try {
            query.registerStoredProcedureParameter("codigoNormativa", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("codigoEntidad", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

            query.setParameter("codigoNormativa", idNormativa);
            query.setParameter("codigoEntidad", codigoEntidad);
            query.setParameter("resultado", resultado);

            // call the stored procedure and get the result
            query.execute();
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando normativa ", e);
            return e.getMessage();
        }
        String retorno = "     " + query.getOutputParameterValue("resultado") + "\n";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }

    @Override
    public String importarNormativasAfectaciones() {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("MIGRAR_NORMATIVAS_AFE");
        String resultado = "";
        try {
            query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

            query.setParameter("resultado", resultado);

            // call the stored procedure and get the result
            query.execute();
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando normativas afectaciones ", e);
            return e.getMessage();
        }
        String retorno = "     " + query.getOutputParameterValue("resultado") + "\n";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }

    @Override
    public String desactivarRestriccionDocumento() {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("DESACTIVAR_RESTRICCIONES_DOCS");
        String resultado = "";
        try {

            // call the stored procedure and get the result
            query.execute();
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando usuario ", e);
            return e.getMessage();
        }
        String retorno = "ok";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }

    @Override
    public String activarRestriccionDocumento() {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("ACTIVAR_RESTRICCIONES_DOCS");
        String resultado = "";
        try {

            // call the stored procedure and get the result
            query.execute();
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando usuario ", e);
            return e.getMessage();
        }
        String retorno = "ok";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }

    @Override
    public List<FicheroInfo> getDocumentos(Long idEntidad, Long uaRaiz, boolean soloProcedimiento, boolean soloNormativas) {
        Query query;
        if (soloProcedimiento) {
            query = this.entityManager.createQuery(" SELECT p.codigo, p.ficheroRolsac1  FROM JProcedimientoDocumentoTraduccion p where p.fichero is null and p.ficheroRolsac1 is not null");
        } else {
            query = this.entityManager.createQuery(" SELECT p.codigo, p.ficheroRolsac1  FROM JDocumentoNormativaTraduccion p where p.documento is null and p.ficheroRolsac1 is not null");
        }

        List<FicheroInfo> retorno = new ArrayList<>();
        List<Object[]> resultados = query.getResultList();
        if (resultados != null) {
            for (Object[] resultado : resultados) {
                FicheroInfo fichero = new FicheroInfo();
                fichero.setCodigoDocumentoTraduccion((Long) resultado[0]);
                fichero.setCodigoFicheroRolsac1((Long) resultado[1]);
                retorno.add(fichero);
            }
        }
        return retorno;
    }


    @Override
    public Long getProcedimiento(Long codigo) {
        JProcedimientoDocumentoTraduccion jProcedimientoDocumentoTraduccion = entityManager.find(JProcedimientoDocumentoTraduccion.class, codigo);
        Long codigoListaDoc = jProcedimientoDocumentoTraduccion.getDocumento().getListaDocumentos();
        List<Long> resultados = entityManager.createQuery("Select procwf.procedimiento.codigo from JProcedimientoWorkflow procwf where procwf.listaDocumentos = " + codigoListaDoc + " or procwf.listaDocumentosLOPD = " + codigoListaDoc).getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            //Lo devolvemos el procedimiento base
            return (Long) resultados.get(0);
        }

        List<Long> resultadosTramite = entityManager.createQuery("Select tram.procedimiento.procedimiento.codigo from JProcedimientoTramite tram where tram.listaDocumentos = " + codigoListaDoc + " or tram.listaModelos = " + codigoListaDoc).getResultList();
        if (resultadosTramite != null && !resultadosTramite.isEmpty()) {
            return (Long) resultadosTramite.get(0);
        }
        return null;
    }

    @Override
    public void migrarArchivo(Long idFichero, Long codigoPadre, TypeFicheroExterno tipoficheroExterno) {
        if (tipoficheroExterno == TypeFicheroExterno.PROCEDIMIENTO_DOCUMENTOS) {
            JProcedimientoDocumentoTraduccion doc = this.entityManager.find(JProcedimientoDocumentoTraduccion.class, codigoPadre);
            doc.setFichero(idFichero);
            entityManager.merge(doc);
        } else {
            JDocumentoNormativaTraduccion doc = this.entityManager.find(JDocumentoNormativaTraduccion.class, codigoPadre);
            JFicheroExterno jficheroExterno = this.entityManager.find(JFicheroExterno.class, idFichero);
            doc.setDocumento(jficheroExterno);
            entityManager.merge(doc);
        }
    }

    @Override
    public List<BigDecimal> getProcedimientosMensajes(Long idEntidad, Long uaRaiz) {
        /** Obtiene los procedimientos que tienen mensajes asociados, tabla R1_PROCEDIMIENTOS_MENSAJES, y que cuelgan de la UA raiz */
        Query query = this.entityManager.createNativeQuery("   SELECT DISTINCT PMN_PROCODI  FROM R1_PROCEDIMIENTOS_MENSAJES WHERE PMN_PROCODI IN  (SELECT PROC_CODIGO FROM RS2_PROC )  ");
        List<BigDecimal> resultados = query.getResultList();
        Query query2 = this.entityManager.createNativeQuery("   SELECT DISTINCT SMN_SERCODI  FROM R1_SERVICIOS_MENSAJES WHERE SMN_SERCODI IN  (SELECT PROC_CODIGO FROM RS2_PROC )  ");
        List<BigDecimal> resultados2 = query2.getResultList();
        if (resultados2 != null && !resultados2.isEmpty()) {
            resultados.addAll(resultados2);
        }
        return resultados;
    }

    @Override
    public String importarMensajes(long idProcMsg, Long entidad) {
        List<Object[]> resultados = obtenerMensajesRolsac1(idProcMsg);
        List<Mensaje> mensajes = new ArrayList<>();
        boolean pendienteAdmContenido = false;
        boolean pendienteGestor = false;
        if (resultados != null) {
            for (Object[] resultado : resultados) {
                Mensaje mensajeObj = new Mensaje();
                mensajeObj.setUsuario((String) resultado[1]);
                mensajeObj.setUsuarioLeido((String) resultado[7]);
                if (((BigDecimal) resultado[2]).intValue() == 1) {
                    // Es gestor
                    mensajeObj.setAdmContenido(false);
                    mensajeObj.setPendienteMensajesSupervisor(true);
                    if (resultado[6] != null) {
                        mensajeObj.setPendienteMensajesGestor(false);
                        mensajeObj.setPendienteMensajesSupervisor(0 == ((BigDecimal) resultado[6]).intValue());
                        if (0 == ((BigDecimal) resultado[6]).intValue()) {
                            pendienteAdmContenido = true;
                        }
                    }
                } else {
                    // Es adm. contenido
                    mensajeObj.setAdmContenido(true);
                    mensajeObj.setPendienteMensajesGestor(true);
                    if (resultado[6] != null) {
                        mensajeObj.setPendienteMensajesGestor((0 == ((BigDecimal) resultado[6]).intValue()));
                        mensajeObj.setPendienteMensajesSupervisor(false);
                        if (0 == ((BigDecimal) resultado[6]).intValue()) {
                            pendienteGestor = true;
                        }
                    }
                }
                mensajeObj.setMensaje((String) resultado[3]);
                if (resultado[4] != null) {
                    mensajeObj.setFechaReal((java.util.Date) resultado[4]);
                }
                if (resultado[5] != null) {
                    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    mensajeObj.setFechaLeido(sdf.format((java.util.Date) resultado[5]));
                }
                mensajes.add(mensajeObj);
            }
        }
        JProcedimiento procedimiento = this.entityManager.find(JProcedimiento.class, idProcMsg);
        if (procedimiento == null) {
            return "No se encuentra procedimiento/servicio RS2 para migrar mensajes: " + idProcMsg + "\n";
        }
        procedimiento.setMensajes(UtilJSON.toJSON(mensajes));
        procedimiento.setMensajesPendienteGestor(pendienteGestor);
        procedimiento.setMensajesPendienteSupervisor(pendienteAdmContenido);
        entityManager.merge(procedimiento);
        String tipo = "P".equals(procedimiento.getTipo()) ? "proc" : "serv";
        return "Migracio missatges " + tipo + " " + idProcMsg + " : " + mensajes.size() + " mensajes migrados. \n ";
    }

    private List<Object[]> obtenerMensajesRolsac1(long idProcMsg) {
        String sqlProc = "SELECT PMN_PROCODI, PMN_USUARIO, PMN_GESTOR, PMN_TEXTO, PMN_FECCRE, PMN_FECLEC, PMN_LEIDO, PMN_USULEC " +
                "FROM R1_PROCEDIMIENTOS_MENSAJES WHERE PMN_PROCODI = " + idProcMsg + " ORDER BY PMN_FECCRE";
        List<Object[]> resultados = ejecutarQueryMensajes(sqlProc);
        if (resultados != null && !resultados.isEmpty()) {
            return resultados;
        }

        // Compatibilidad con entornos donde el prefijo de columnas en servicios es SMN_*.
        String sqlServSmn = "SELECT SMN_SERCODI, SMN_USUARIO, SMN_GESTOR, SMN_TEXTO, SMN_FECCRE, SMN_FECLEC, SMN_LEIDO, SMN_USULEC " +
                "FROM R1_SERVICIOS_MENSAJES WHERE SMN_SERCODI = " + idProcMsg + " ORDER BY SMN_FECCRE";
        return ejecutarQueryMensajes(sqlServSmn);

    }

    private List<Object[]> ejecutarQueryMensajes(String sql) {
        try {
            Query query = this.entityManager.createNativeQuery(sql);
            return query.getResultList();
        } catch (Exception e) {
            LOG.debug("No se han podido obtener mensajes con query [{}]", sql, e);
            return new ArrayList<>();
        }
    }

    @Override
    public Long getNormativa(Long codigo) {
        JDocumentoNormativaTraduccion jDocumentoNormativaTraduccion = entityManager.find(JDocumentoNormativaTraduccion.class, codigo);
        return jDocumentoNormativaTraduccion.getDocumentoNormativa().getNormativa().getCodigo();
    }

    @Override
    public boolean existeArchivo(Long idDoc) {
        Query query = this.entityManager.createQuery("select count(p) from JFicheroExterno p where p.codigo = " + idDoc);
        return ((Long) query.getSingleResult()) > 0l;
    }


    @Override
    public String importarProcedimiento(Long idProc, Long codigoEntidad) {
        return importarProcedimientoServicio("MIGRAR_PROC", idProc, codigoEntidad);
    }

    private String importarProcedimientoServicio(String metodo, Long id, Long codigoEntidad) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery(metodo);
        String resultado = "";
        try {
            query.registerStoredProcedureParameter("codigo", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("codigoEntidad", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

            query.setParameter("codigo", id);
            query.setParameter("codigoEntidad", codigoEntidad);
            query.setParameter("resultado", resultado);

            // call the stored procedure and get the result
            boolean resultadox = query.execute();
            String parar = null;
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando normativa ", e);
            return e.getMessage();
        }
        String retorno = "     " + query.getOutputParameterValue("resultado") + "\n";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }

    @Override
    public String importarServicio(Long idServicio, Long codigoEntidad) {
        return importarProcedimientoServicio("MIGRAR_SERV", idServicio, codigoEntidad);
    }

    @Override
    public List<BigDecimal> getUAs(Long idEntidad, Long idUARaiz) {

        String sql = " SELECT UNA_CODI  FROM R1_UNIADM  WHERE  CHECK_CUELGA_UA_PROC(UNA_CODI, " + idUARaiz + ") = 1 ORDER BY OBTENER_PROF_UA(UNA_CODI) ";
        Query query = this.entityManager.createNativeQuery(sql);
        return query.getResultList();
    }


    @Override
    public List<UnidadAdministrativaDTO> getUnidadAdministrativasRaiz() {
        String sql = "select una_codi, " + "    ( SELECT TUN_NOMBRE FROM  R1_UNIADM_TRAD WHERE TUN_CODUNA = UA.una_codi and tun_codidi = 'ca'), " + "    ( SELECT TUN_NOMBRE FROM  R1_UNIADM_TRAD WHERE TUN_CODUNA = UA.una_codi and tun_codidi = 'es')  " + " from R1_UNIADM UA where una_coduna is null";
        Query query = this.entityManager.createNativeQuery(sql);
        List<Object[]> valores = query.getResultList();
        List<UnidadAdministrativaDTO> retorno = new ArrayList<>();
        if (valores != null && !valores.isEmpty()) {
            for (Object[] valor : valores) {
                UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
                ua.setCodigo(new Long(valor[0].toString()));
                Literal nombre = new Literal();
                Traduccion tradCa = new Traduccion();
                tradCa.setIdioma("ca");
                tradCa.setLiteral((String) valor[1]);
                Traduccion tradEs = new Traduccion();
                tradEs.setIdioma("es");
                tradEs.setLiteral((String) valor[2]);
                nombre.add(tradCa);
                nombre.add(tradEs);
                ua.setNombre(nombre);
                retorno.add(ua);
            }
        }
        return retorno;
    }

    @Override
    public List<String> getUsuarios() {
        Query query = this.entityManager.createNativeQuery(" SELECT USU_USERNA  FROM R1_USUARIO");
        return query.getResultList();
    }

    @Override
    public String importarUsuario(String idUsuario) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("MIGRAR_USUARIO");
        String resultado = "";
        try {
            query.registerStoredProcedureParameter("usuario", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

            query.setParameter("usuario", idUsuario);
            query.setParameter("resultado", resultado);

            // call the stored procedure and get the result
            query.execute();
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando usuario ", e);
            return e.getMessage();
        }
        String retorno = "     " + query.getOutputParameterValue("resultado") + "\n";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }


}