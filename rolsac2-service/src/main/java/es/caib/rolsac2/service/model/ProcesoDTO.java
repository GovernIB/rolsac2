package es.caib.rolsac2.service.model;


/**
 * Clase de negocio equivalente a la entidad Proceso.
 *
 * @author INDRA
 */
public class ProcesoDTO extends ProcesoGridDTO {


    /**
     * Serial version UID.
     **/
    private static final long serialVersionUID = 1L;


    /**
     * Constructor.
     **/
    public ProcesoDTO() {
        super();
    }

    public ProcesoDTO(ProcesoDTO otro) {
        this.setCodigo(otro.getCodigo());
        this.setDescripcion(otro.getDescripcion());
        this.setActivo(otro.getActivo());
        this.setParametrosInvocacion(otro.getParametrosInvocacion());
        this.setCron(otro.getCron());
    }

    /**
     * Devuelve una instancia de instancia.
     *
     * @return
     */
    public static ProcesoDTO createInstance() {
        return new ProcesoDTO();
    }

    @Override
    public ProcesoDTO clone() {
        return new ProcesoDTO(this);
    }

}
