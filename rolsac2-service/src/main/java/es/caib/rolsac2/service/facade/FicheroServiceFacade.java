package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;

import java.util.List;

/**
 * Servicio para los casos de uso de mantenimiento de la entidad y la configuración global.
 *
 * @author Indra
 */
public interface FicheroServiceFacade {


    /**
     * Devuelve el contenido de un fichero.
     *
     * @param idFichero Código fichero
     * @param path      Ruta del fichero
     * @return FicheroDTO
     */
    FicheroDTO getContentById(Long idFichero, String path);

    /**
     * Devuelve el contenido metadata de un fichero.
     *
     * @param idFichero Código fichero
     * @param path      Ruta del fichero
     * @return FicheroDTO
     */
    FicheroDTO getContentMetadata(Long idFichero, String path);

    /**
     * Devuelve el ficheroDTO sin el contenido
     *
     * @param idFichero Código fichero
     * @return FicheroDTO
     */
    FicheroDTO getFicheroDTOById(Long idFichero);


    /**
     * Crea fichero externo temporal. Deberá c posteriormente para consolidarse, si no se enlaza se borrará tras 24h.
     *
     * @param content                Contenido fichero
     * @param fileName               Nombre fichero
     * @param tipoFicheroExterno     Tipo fichero externo (referencia tabla donde se usa)
     * @param elementoFicheroExterno Elmento al que está asociado (entidad, ficha, procedimiento,...)
     * @param path                   Ruta del fichero
     * @return Código fichero.
     */
    Long createFicheroExterno(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno, String path);

    /**
     * Crea fichero de ayuda.
     *
     * @param bytes              Contenido fichero
     * @param fileName           Nombre fichero
     * @param typeFicheroExterno Tipo fichero ayuda (imagen o documento)
     * @param path               Ruta del fichero
     * @return Código fichero.
     */
    String createFicheroAyuda(byte[] bytes, String fileName, TypeFicheroExterno typeFicheroExterno, String path);

    /**
     * Persiste fichero externo (pasa de borrador a consolidado). Solo se puede persistir un fichero que está en borrador.
     *
     * @param codigoFichero Código fichero.
     * @param id            Código padre
     * @param path          Ruta del fichero
     */
    void persistFicheroExterno(Long codigoFichero, Long id, String path);


    /**
     * Devuelve los ficheros que han sido temporales y que no se han consolidado.
     *
     * @return Lista de códigos de ficheros temporales.
     */
    List<Long> getFicherosTemporales();


    /**
     * Borra definitivamente un fichero que ha sido marcado como temporal.
     *
     * @param path      Ruta del fichero
     * @param idFichero Código fichero
     */
    void borrarFicheroTemporal(String path, Long idFichero);

    /**
     * Devuelve los ficheros que han sido marcados para borrar.
     *
     * @return Lista de códigos de ficheros marcados para borrar.
     */
    List<Long> getFicherosMarcadosParaBorrar();


    /**
     * Borra definitivamente un fichero que ha sido marcado para borrar.
     *
     * @param path      Ruta del fichero
     * @param idFichero Código fichero
     */
    void borrarFicheroDefinitivamente(String path, Long idFichero);

    /**
     * Devuelve el contenido de un fichero a partir de una ruta.
     *
     * @param ruta La ruta
     * @return El contenido del fichero
     */
    byte[] getContentByRuta(String ruta);

    /**
     * Borra un fichero definitivamente.
     *
     * @param path Path
     */
    void borrarFicheroPerdido(String path);

    /**
     * Borra el fichero de ayuda.
     *
     * @param path               Path
     * @param typeFicheroExterno TypeFicheroExterno
     * @param codigo             Código
     */
    void borrarFicheroAyuda(String path, TypeFicheroExterno typeFicheroExterno, Long codigo);

    /**
     * Obtener el contenido de un fichero de ayuda por referencia.
     *
     * @param id   Código fichero
     * @param path Ruta del fichero
     * @return FicheroDTO
     */
    FicheroDTO getContentAyudaByReferencia(String id, String path);
}
