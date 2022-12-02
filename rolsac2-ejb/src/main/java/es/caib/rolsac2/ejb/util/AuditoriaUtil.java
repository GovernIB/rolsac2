/**
 *
 *//*

package es.caib.rolsac2.ejb.util;

import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaIdioma;
import es.caib.rolsac2.service.model.auditoria.AuditoriaValorCampo;

*/
/**
 * @author Indra
 *//*

public final class AuditoriaUtil {


    private AuditoriaUtil() {
        super();
    }


    */
/**
     * Compara dos valores String y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @param idioma
     * @return
     *//*

    public static final AuditoriaCambio auditarCampoCadena(final String valorPublicado, final String valorModificado, final String idCampo,
                                                           final AuditoriaIdioma idioma) {
        AuditoriaCambio cambio = null;

        if (valorPublicado != null && valorModificado != null) {
            if (!valorPublicado.equals(valorModificado)) {
                cambio = agregarAuditoriaValorCampo(cambio, idioma, valorPublicado, valorModificado, idCampo);
            }
        } else if (valorPublicado != null || valorModificado != null) {
            cambio =
                    agregarAuditoriaValorCampo(cambio, idioma, valorPublicado == null ? "" : valorPublicado, valorModificado == null ? "" : valorModificado, idCampo);

        }

        return cambio;
    }

    */
/**
     * Método generico para crear valores de AuditoriaValorCampo
     *
     * @param cambioAcumulado Objeto que va guardando los cambios
     * @param idioma          Idioma que va a tener el valor
     * @param valorAnterior   Valor anterior
     * @param valorNuevo      Valor nuevo
     * @param idCampo         Identificación del campo en la auditoría
     * @return
     *//*

    private static AuditoriaCambio agregarAuditoriaValorCampo(final AuditoriaCambio cambioAcumulado, final AuditoriaIdioma idioma, final String valorAnterior,
                                                              final String valorNuevo, final String idCampo) {
        AuditoriaCambio cambio = cambioAcumulado;

        if (cambio == null) {
            cambio = new AuditoriaCambio();
            cambio.setIdCampo(idCampo);
        }

        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        valorCampo.setIdioma(idioma);
        valorCampo.setValorAnterior(valorAnterior);
        valorCampo.setValorNuevo(valorNuevo);

        cambio.getValoresModificados().add(valorCampo);

        return cambio;
    }


}
*/
