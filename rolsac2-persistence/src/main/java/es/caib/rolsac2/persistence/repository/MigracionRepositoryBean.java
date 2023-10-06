package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JDocumentoNormativaTraduccion;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.model.JProceso;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoDocumentoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.migracion.FicheroInfo;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
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
    public String ejecutarMetodo(String metodo, String param1, String param2) {

        String resultado = "";
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery(metodo);
        query.registerStoredProcedureParameter("codigoUA", String.class/*Long.class*/, ParameterMode.IN);
        query.registerStoredProcedureParameter("codigoEntidad", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

        query.setParameter("codigoUA", param1);
        query.setParameter("codigoEntidad", param2);
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

    @Override
    public List<BigDecimal> getNormativas(Long idEntidad) {
        Query query = this.entityManager.createNativeQuery("   SELECT NOR_CODI  FROM R1_NORMAT");
        return query.getResultList();
    }

    @Override
    public List<BigDecimal> getProcedimientos(Long idEntidad, Long codigoUARaiz) {
        Query query = this.entityManager.createNativeQuery("   SELECT PRO_CODI  FROM R1_PROCEDIMIENTOS WHERE CHECK_CUELGA_UA_PROC(PRO_CODUNA, " + codigoUARaiz + ") = 1");
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
            return e.getLocalizedMessage();
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
            return e.getLocalizedMessage();
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
            return e.getLocalizedMessage();
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
            return e.getLocalizedMessage();
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
            query.execute();
            //query.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error importando normativa ", e);
            return e.getLocalizedMessage();
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
        Query query = this.entityManager.createNativeQuery(" SELECT UNA_CODI  FROM R1_UNIADM " + " WHERE CHECK_CUELGA_UA_PROC(UNA_CODI, " + idUARaiz + ") = 1 " + " ORDER BY OBTENER_PROF_UA(UNA_CODI) ");
        return query.getResultList();
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
            return e.getLocalizedMessage();
        }
        String retorno = "     " + query.getOutputParameterValue("resultado") + "\n";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
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
            return e.getLocalizedMessage();
        }
        String retorno = "     " + query.getOutputParameterValue("resultado") + "\n";
        query.unwrap(ProcedureOutputs.class).release();
        return retorno;
    }


}


