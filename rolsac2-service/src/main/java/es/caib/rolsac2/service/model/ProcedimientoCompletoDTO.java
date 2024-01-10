package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un Procedimiento completo.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoCompleto")
public class ProcedimientoCompletoDTO extends ModelApi {
    /**
     * Codigo
     */
    private Long codigo;

    /**
     * El procedimiento publicado.
     **/
    private ProcedimientoBaseDTO procedimientoPub;

    /**
     * El procedimiento modificado.
     **/
    private ProcedimientoBaseDTO procedimientoMod;

    /**
     * Opcion de unidad administrativa destino
     */
    private Long opcionUAdestino;


    /**
     * Obtener codigo
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establecer codigo
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener procedimiento publicado
     *
     * @return procedimiento publicado
     */
    public ProcedimientoDTO getProcedimientoPub() {
        if (procedimientoPub == null || procedimientoPub.getCodigo() == null) {
            return new ProcedimientoDTO();
        } else {
            return (ProcedimientoDTO) procedimientoPub;
        }

    }

    /**
     * Obtener servicio publicado
     *
     * @return servicio publicado
     */
    public ProcedimientoBaseDTO getProcedimientoBaseDTOPub() {
        return procedimientoPub;
    }

    /**
     * Obtener servicio publicado
     *
     * @return servicio publicado
     */
    public ServicioDTO getServicioPub() {
        if (procedimientoPub == null || procedimientoPub.getCodigo() == null) {
            return new ServicioDTO();
        } else {
            return (ServicioDTO) procedimientoPub;
        }
    }

    /**
     * Establecer procedimiento publicado
     *
     * @param procedimientoPub procedimiento publicado
     */
    public void setProcedimientoPub(ProcedimientoBaseDTO procedimientoPub) {
        this.procedimientoPub = procedimientoPub;
    }

    /**
     * Obtener procedimiento modificado
     *
     * @return procedimiento modificado
     */
    public ProcedimientoDTO getProcedimientoMod() {
        if (procedimientoMod == null || procedimientoMod.getCodigo() == null) {
            return new ProcedimientoDTO();
        } else {
            return (ProcedimientoDTO) procedimientoMod;
        }
    }

    /**
     * Obtener servicio modificado
     *
     * @return servicio modificado
     */
    public ServicioDTO getServicioMod() {
        if (procedimientoMod == null || procedimientoMod.getCodigo() == null) {
            return new ServicioDTO();
        } else {
            return (ServicioDTO) procedimientoMod;
        }
    }

    /**
     * Obtener servicio modificado
     *
     * @return servicio modificado
     */
    public ProcedimientoBaseDTO getProcedimientoBaseDTOMod() {
        return procedimientoMod;
    }


    /**
     * Establecer procedimiento modificado
     *
     * @param procedimientoMod procedimiento modificado
     */
    public void setProcedimientoMod(ProcedimientoBaseDTO procedimientoMod) {
        this.procedimientoMod = procedimientoMod;
    }

    /**
     * Obtener opcion de unidad administrativa destino
     *
     * @return opcion de unidad administrativa destino
     */
    public Long getOpcionUAdestino() {
        return opcionUAdestino;
    }

    /**
     * Establecer opcion de unidad administrativa destino
     *
     * @param opcionUAdestino opcion de unidad administrativa destino
     */
    public void setOpcionUAdestino(Long opcionUAdestino) {
        this.opcionUAdestino = opcionUAdestino;
    }

    /**
     * Añade un procedimiento base
     *
     * @param proc procedimiento base
     */
    public void addProcedimientoBase(ProcedimientoBaseDTO proc) {
        if (proc == null) {
            return;
        }
        if (proc.getEstado().equals("P")) {
            this.procedimientoPub = proc;
        } else {
            this.procedimientoMod = proc;
        }
    }

    /**
     * Comprueba si está asignado el procedimiento publicado
     *
     * @return
     */
    public boolean isProcedimientoPub() {
        return procedimientoPub == null || procedimientoPub.getCodigo() != null;
    }

    /**
     * Comprueba si está asignado el procedimiento modificado
     *
     * @return
     */
    public boolean isProcedimientoMod() {
        return procedimientoMod == null || procedimientoMod.getCodigo() != null;
    }

    /**
     * Obtiene el nombre.
     *
     * @param lang
     * @return
     */
    public String getNombre(String lang) {
        if (isProcedimientoPub()) {
            return procedimientoPub.getNombreProcedimientoWorkFlow().getTraduccion(lang);
        } else if (isProcedimientoMod()) {
            return procedimientoMod.getNombreProcedimientoWorkFlow().getTraduccion(lang);
        } else {
            return "";
        }
    }

    public String getEstado() {
        StringBuilder sb = new StringBuilder();
        if (isProcedimientoPub()) {
            sb.append(procedimientoPub.getEstado().toString());
        }
        if (isProcedimientoMod()) {
            sb.append(procedimientoMod.getEstado().toString());
        }
        return sb.toString();
    }

    /**
     * Devuelve si el procedimiento publicado es visible
     *
     * @return visible
     */
    public boolean esVisiblePub() {
        if (procedimientoPub instanceof ProcedimientoDTO) {
            return ((ProcedimientoDTO) procedimientoPub).esVisible();
        } else {
            if (procedimientoPub instanceof ServicioDTO) {
                return ((ServicioDTO) procedimientoPub).esVisible();
            } else {
                return false;
            }
        }
    }

    /**
     * Devuelve si el procedimiento modificado es visible
     *
     * @return visible
     */
    public boolean esVisibleMod() {
        if (procedimientoMod instanceof ProcedimientoDTO) {
            return ((ProcedimientoDTO) procedimientoMod).esVisible();
        } else {
            if (procedimientoMod instanceof ServicioDTO) {
                return ((ServicioDTO) procedimientoMod).esVisible();
            } else {
                return false;
            }
        }
    }

    public boolean isSoloReserva() {
        if (isProcedimientoPub() && !isProcedimientoMod()) {
            return getProcedimientoBaseDTOPub().getEstado() == TypeProcedimientoEstado.RESERVA;
        }
        return false;
    }

    /**
     * Borra un estado
     *
     * @param estado
     */
    public void borrarEstado(TypeProcedimientoEstado estado) {
        if (estado.getWorkflowSegunEstado() == TypeProcedimientoWorkflow.DEFINITIVO) {
            if (isProcedimientoPub() && this.getProcedimientoPub().getEstado() != null && this.getProcedimientoPub().getEstado() == estado) {
                setProcedimientoPub(new ProcedimientoBaseDTO());
            }
        } else {
            if (isProcedimientoMod() && this.getProcedimientoMod().getEstado() != null && this.getProcedimientoMod().getEstado() == estado) {
                setProcedimientoMod(new ProcedimientoBaseDTO());
            }
        }
    }

    public String getIcon() {
        if (isProcedimientoPub()) {
            if (procedimientoPub instanceof ProcedimientoDTO) {
                return ((ProcedimientoDTO) procedimientoPub).getIcon();
            } else if (procedimientoPub instanceof ServicioDTO) {
                return ((ServicioDTO) procedimientoPub).getIcon();
            } else {
                return "pi pi-eye-slash iconoRojo";
            }
        } else {
            return "pi pi-eye-slash iconoRojo";
        }
    }



}
