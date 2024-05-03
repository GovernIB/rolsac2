package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.migracion.FicheroRolsac1;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;

import java.io.IOException;
import java.util.List;

/**
 * Interface de las operaciones básicas sobre ficheros.
 *
 * @author Indra
 */
public interface FicheroExternoRepository extends CrudRepository<JFicheroExterno, Long> {


    /**
     * Devuelve el contenido de un fichero.
     *
     * @param idFichero
     * @return
     */
    FicheroDTO getContentById(Long idFichero, String pathAlmacenamientoFicheros);

    FicheroDTO getMetadata(Long idFichero, String pathAlmacenamientoFicheros);


    /**
     * Crea fichero externo temporal. Deberá c posteriormente para consolidarse, si no se enlaza se borrará tras 24h.
     *
     * @param content                Contenido fichero
     * @param fileName               Nombre fichero
     * @param tipoFicheroExterno     Tipo fichero externo (referencia tabla donde se usa)
     * @param elementoFicheroExterno Elmento al que está asociado (entidad, ficha, procedimiento,...)
     * @return Código fichero.
     */
    Long createFicheroExterno(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno, String pathAlmacenamientoFicheros);

    /**
     * Crea fichero externo en la migracion (no será temporal).
     *
     * @param content
     * @param fileName
     * @param tipoFicheroExterno
     * @param elementoFicheroExterno
     * @param pathAlmacenamientoFicheros
     * @param codigoMigracion
     * @return
     */
    Long createFicheroExternoMigracion(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno, String pathAlmacenamientoFicheros, Long codigoMigracion);

    /**
     * Persiste fichero externo (pasa de borrador a consolidado). Solo se puede persistir un fichero que está en borrador.
     *
     * @param codigoFichero Código fichero.
     * @param idElemento    Id del elemento
     */
    void persistFicheroExterno(Long codigoFichero, Long idElemento, String pathAlmacenamientoFicheros);

    /**
     * Borra fichero externo (se marca para borrar y se procederá a borrar en proceso background).
     *
     * @param codigoFichero Código fichero
     */
    void deleteFicheroExterno(Long codigoFichero);

    void purgeFicheroExterno(String pathAlmacenamientoFicheros, JFicheroExterno jFicheroExterno);

    /**
     * Purga ficheros externos (marcados para borrar y temporales > 24h).
     */
    void purgeFicherosExternos(String pathAlmacenamientoFicheros);

    /**
     * Devuelve el archivo de Rolsac1 con toda su info
     *
     * @param idArchivo
     * @param rutaRolsac1
     * @return
     */
    FicheroRolsac1 getFicheroRolsac(long idArchivo, String rutaRolsac1) throws IOException;

    /**
     * Devuelve los ficheros que han sido temporales y que no se han consolidado.
     *
     * @return Lista de códigos de ficheros temporales.
     */
    List<Long> getFicherosTemporales();

    /**
     * Marca a borrar un fichero que es temporal.
     *
     * @return idFichero
     */
    List<Long> getFicherosMarcadosParaBorrar();

    /**
     * Devuelve el fichero temporal por id
     *
     * @param idFichero id del fichero
     * @return fichero temporal
     */
    JFicheroExterno findTemporalById(Long idFichero);

    /**
     * Devuelve el fichero marcado para borrar
     *
     * @param idFichero id del fichero
     * @return fichero marcado para borrar
     */
    JFicheroExterno findBorradoById(Long idFichero);
}
