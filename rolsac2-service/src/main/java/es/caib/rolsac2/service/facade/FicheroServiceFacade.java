package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;

/**
 * Servicio para los casos de uso de mantenimiento de la entidad y la configuración global.
 *
 * @author jsegovia
 */
public interface FicheroServiceFacade {


    /**
     * Devuelve el contenido de un fichero.
     *
     * @param idFichero
     * @return
     */
    FicheroDTO getContentById(Long idFichero);


    /**
     * Crea fichero externo temporal. Deberá c posteriormente para consolidarse, si no se enlaza se borrará tras 24h.
     *
     * @param content                Contenido fichero
     * @param fileName               Nombre fichero
     * @param tipoFicheroExterno     Tipo fichero externo (referencia tabla donde se usa)
     * @param elementoFicheroExterno Elmento al que está asociado (entidad, ficha, procedimiento,...)
     * @return Código fichero.
     */
    Long createFicheroExterno(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno);

    /**
     * Persiste fichero externo (pasa de borrador a consolidado). Solo se puede persistir un fichero que está en borrador.
     *
     * @param codigoFichero Código fichero.
     */
    void persistFicheroExterno(Long codigoFichero, Long id);

    /**
     * Borra fichero externo (se marca para borrar y se procederá a borrar en proceso background).
     *
     * @param codigoFichero Código fichero
     */
    void deleteFicheroExterno(Long codigoFichero);

    /**
     * Purga ficheros externos (marcados para borrar y temporales > 24h).
     */
    void purgeFicherosExternos(String pathAlmacenamientoFicheros);


}