package es.caib.rolsac2.ejb.facade.procesos.test;

import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
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

/**
 * Proceso test.
 *
 * @author Indra
 */
@Stateless(name = "procesoProgramadoTestComponent")
@Local(ProcesoProgramadoFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public class ProcesoProgramadoTestComponentBean implements ProcesoProgramadoFacade {

    /**
     * Código interno del proceso
     */
    private static final String CODIGO_PROCESO = "TEST";

    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoTestComponentBean.class);

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getCodigoProceso() {
        return CODIGO_PROCESO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ResultadoProcesoProgramado ejecutar(final ListaPropiedades params) {
        log.info("Ejecución proceso test");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        if (params != null) {
            detalles.addPropiedades(params);
            res.setFinalizadoOk(true);
            if(params.getPropiedad("valida") != null) {
                Boolean valida = params.getPropiedad("valida").equals("true");
                if (!valida) {
                    res.setFinalizadoOk(false);
                    res.setMensajeError("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ullamcorper semper laoreet. Nulla ut ex felis. " +
                            "Proin metus urna, venenatis blandit commodo sit amet, dictum eget diam. Nullam tempor tempus nunc, commodo ornare elit dictum id. " +
                            "Suspendisse semper blandit felis et facilisis. Donec sodales quam vitae lorem iaculis aliquam. Quisque faucibus, lorem in rutrum egestas, " +
                            "turpis orci lobortis elit, eget sagittis libero ligula at nulla. Praesent consectetur nisl ac orci faucibus fringilla ac a libero. " +
                            "Etiam tristique mauris massa, ac laoreet dolor tincidunt ac. Fusce finibus eget ex a mollis. Aliquam vel aliquam velit. " +
                            "Curabitur placerat mi non eros commodo molestie. Proin venenatis turpis tincidunt felis dignissim pretium id nec lectus." );
                } else {
                    res.setFinalizadoOk(true);
                }
            }
        }
        detalles.addPropiedad("Informació del procés", "Indexació de procediments correcte");
        detalles.addPropiedad("Informació del procés", "Indexació normativa correcte");
        res.setDetalles(detalles);
        return res;
    }

}
