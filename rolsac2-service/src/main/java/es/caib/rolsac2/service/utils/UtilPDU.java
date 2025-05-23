package es.caib.rolsac2.service.utils;

import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;

public class UtilPDU {

    public static Integer ACCION_PDU_ALTA = 1;
    public static Integer ACCION_PDU_BAJA = 2;

    /**
     * <P>Accion a realizar en PDU</P>
     * <P>1: Alta</P>
     * <P>0: Baja</P>
     * <P>NULO: No realizar nada</P>
     *
     * @param procedimientoNuevo Procedimiento nuevo
     * @param procedimientoViejo Procedimiento antiguo
     * @return Devuelve la acción, si es nulo, es que no se ha de hacer nada
     */
    public static Integer getAccion(ProcedimientoDTO procedimientoNuevo, ProcedimientoDTO procedimientoViejo, TypeProcedimientoEstado estadoDestino) {

        // Si el estadoDestino es no nulo es porque viene desde el botón del flujo
        // Si el estadoDestino es nulo es porque se ha forzado la indexación de todos los procedimientos
        TypeProcedimientoEstado estado = (estadoDestino != null) ? estadoDestino : procedimientoNuevo.getEstado();

        if (estado == TypeProcedimientoEstado.PUBLICADO) {
            // Si el dato NO está integrado con PDU o esta de baja, entonces dar de alta
            if (procedimientoNuevo.getEstadoPdu() == null || procedimientoNuevo.getEstadoPdu() == 0) {
                if (procedimientoNuevo.isIntegrarPdu()) {
                    return ACCION_PDU_ALTA;
                }
            }
            // Si el dato SI está integrado con PDU
            else {
                if (procedimientoNuevo.isIntegrarPdu()) {
                    if (procedimientoViejo == null) {
                        return ACCION_PDU_ALTA;
                    } else {
                        boolean isIndexable = indexablePDU(procedimientoNuevo, procedimientoViejo);
                        if (isIndexable) {
                            return ACCION_PDU_ALTA;
                        }
                    }
                } else {
                    return ACCION_PDU_BAJA;
                }
            }
        } else if (estado == TypeProcedimientoEstado.CERRADO) {
            if (procedimientoNuevo.isIntegrarPdu()) {
                return ACCION_PDU_BAJA;
            }
        }
        return null;

    }

    private static boolean indexablePDU(ProcedimientoBaseDTO procDestino, ProcedimientoBaseDTO data) {
        if (UtilComparador.compareTo(data.getCategoriasPDU(), procDestino.getCategoriasPDU()) != 0) {
            return true;
        }
        //Comparar el nombre, objeto, destinatario y terminio
        if (UtilComparador.compareTo(data.getNombreProcedimientoWorkFlow(), procDestino.getNombreProcedimientoWorkFlow()) != 0) {
            return true;
        }
        if (UtilComparador.compareTo(data.getObjeto(), procDestino.getObjeto()) != 0) {
            return true;
        }/* No es necesario las siguientes 2 literales de momento
        if (UtilComparador.compareTo(data.getDestinatarios(), procDestino.getDestinatarios()) != 0) {
            return true;
        }
        if (UtilComparador.compareTo(data.getTerminoResolucion(), procDestino.getTerminoResolucion()) != 0) {
            return true;
        }*/
        return false;
    }


}
