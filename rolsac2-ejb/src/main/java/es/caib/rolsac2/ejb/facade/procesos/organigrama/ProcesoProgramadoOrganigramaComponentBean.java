package es.caib.rolsac2.ejb.facade.procesos.organigrama;

import es.caib.rolsac2.commons.plugins.dir3.api.Dir3ErrorException;
import es.caib.rolsac2.commons.plugins.dir3.api.IPluginDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.ParametrosDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.UnidadOrganica;
import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.facade.integracion.Dir3ServiceFacade;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadOrganicaDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Proceso test.
 *
 * @author Indra
 */

@Stateless(name = "procesoProgramadoOrganigramaComponent")
@Local(ProcesoProgramadoFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public class ProcesoProgramadoOrganigramaComponentBean implements ProcesoProgramadoFacade {

    /**
     * Código interno del proceso
     */
    private static final String CODIGO_PROCESO = "DIR3";

    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoOrganigramaComponentBean.class);

    @Inject
    private Dir3ServiceFacade dir3ServiceFacade;

    @Inject
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getCodigoProceso() {
        return CODIGO_PROCESO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ResultadoProcesoProgramado ejecutar(final ListaPropiedades params, Long idEntidad) {
        log.info("Ejecución proceso organigrama DIR3");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();

        if(params != null) {
            if(params.getPropiedad("codigoDir3") == null) {
                res.setFinalizadoOk(false);
                detalles.addPropiedad("Informació del procés", "No s'ha especificat codi Dir3 per a realitzar la cerca");
                res.setDetalles(detalles);
                return res;
            }
            ParametrosDir3 parametrosDir3 = new ParametrosDir3();
            parametrosDir3.setCodigo(params.getPropiedad("codigoDir3"));
            parametrosDir3.setDenominacionCooficial(params.getPropiedad("denominacionCooficial") == null ? false : params.getPropiedad("denominacionCooficial").equals("true"));
            try {

                List<UnidadOrganicaDTO> unidadesOrganicas = dir3ServiceFacade.obtenerArbolUnidades(idEntidad, parametrosDir3, params.getPropiedad("codigoDir3"));
                unidadAdministrativaServiceFacade.eliminarOrganigrama(idEntidad);
                unidadAdministrativaServiceFacade.crearOrganigrama(unidadesOrganicas, idEntidad);
                res.setFinalizadoOk(true);
                return res;
            } catch (Exception e) {
                res.setFinalizadoOk(false);
                detalles.addPropiedad("Informació del procés", "No s'ha pogut obtenir l'organigrama DIR3");
                res.setMensajeError(ExceptionUtils.getStackTrace(e));
                res.setDetalles(detalles);
                return res;
            }
        } else {
            res.setFinalizadoOk(false);
            detalles.addPropiedad("Informació del procés", "No s'ha especificat cap paràmetre en la definició del procès.");
            res.setDetalles(detalles);
            return res;
        }
    }



}
