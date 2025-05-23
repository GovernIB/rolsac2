package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.ResultadoPdu;
import es.caib.rolsac2.service.model.CategoriaPDUDTO;
import es.caib.rolsac2.service.model.IndexacionPDUDto;
import es.caib.rolsac2.service.model.Pagina;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Servicio con funciones para interactura con PDU ( Pasarela Digital Única) que es un portal a nivel unión europea multiidioma para información de interes
 * de las administraciones o similates dentro de cada país (https://europa.eu/youreurope/).
 */
public interface PduServiceFacade {


    /**
     * Crea una petición de importar enlace de un procedimiento o servicion en PDU en los idiomas castellano e inglés.
     *
     * @param indexacionDTO Indeaxación de un procedimiento o servicio a integrar en PDU.
     * @return Pair con la petición de importar enlace y un mensaje de traza. Si el proceso da algún error de procesamiento o de validacion,
     * se devolverá null en la petición y un mensaje de traza con la descripción del error. Si no hay errores, se devolverá la petición y un mensaje de traza vacío.
     */
    Pair<RPeticionImportarEnlace, String> crearPeticionPdu(IndexacionPDUDto indexacionDTO);

    Pair<RPeticionImportarEnlace, String> peticionEliminarElementoPdu(IndexacionPDUDto indexacionDTO);

    void deleteIndexacion(Long codElemento);

    void actualizarPDU(IndexacionPDUDto pduDto, ResultadoPdu resultadoPDU);

    void actualizarPDUfuturo(IndexacionPDUDto indexacionPDU, String mensaje);

    Pagina<IndexacionPDUDto> getProcedimientosIntegrado(Long idEntidad);

    CategoriaPDUDTO findCategoriaById(long id);

    /**
     * Obtiene todos los datos de indexación PDU de procedimientos y servicios que ya están publicados
     *
     * @param idEntidad
     * @return
     */
    Pagina<IndexacionPDUDto> getPendientesIntegrar(Long idEntidad);

    /**
     * Obtiene todos los datos de indexación PDU de procedimientos que ya están publicados
     *
     * @param indexacionDTO IndexacionDTo
     * @return Los enlaces
     */
    List<String> obtenerEnlaces(IndexacionPDUDto indexacionDTO);

}
