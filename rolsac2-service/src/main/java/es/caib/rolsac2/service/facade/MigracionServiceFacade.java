package es.caib.rolsac2.service.facade;


/**
 * Instancia service.
 *
 * @author Indra
 */
public interface MigracionServiceFacade {

    /**
     * Ejecuta un método.
     *
     * @param metodo
     * @param param1
     * @param param2
     * @return Peticion que tenga el código
     */
    String ejecutarMetodo(String metodo, Long param1, Long param2);

}
