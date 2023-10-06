package es.caib.rolsac2.ejb.facade.procesos.solr;

import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Proceso solr.
 *
 * @author Indra
 */
@Stateless(name = "procesoProgramadoSolrPuntualComponent")
@Local(ProcesoProgramadoFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public class ProcesoProgramadoSolrPuntualComponentBean extends ProcesoProgramadoBaseSolrComponentBean implements ProcesoProgramadoFacade {

    /**
     * Código interno del proceso
     */
    private static final String CODIGO_PROCESO = "SOLR_PUNT";

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private NormativaServiceFacade normativaService;


    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoSolrPuntualComponentBean.class);

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getCodigoProceso() {
        return CODIGO_PROCESO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ResultadoProcesoProgramado ejecutar(final Long instanciaProceso, final ListaPropiedades params, Long idEntidad) {
        return super.ejecutarPadre(instanciaProceso, params, false, idEntidad);
    }



}
