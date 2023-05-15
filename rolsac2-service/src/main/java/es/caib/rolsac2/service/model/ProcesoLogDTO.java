package es.caib.rolsac2.service.model;

public class ProcesoLogDTO extends ProcesoLogGridDTO {
    /**
     * Serial version UID.
     **/
    private static final long serialVersionUID = 1L;


    /**
     * Constructor.
     **/
    public ProcesoLogDTO() {
        super();
    }


    /**
     * Devuelve una instancia de instancia.
     *
     * @return
     */
    public static ProcesoLogDTO createInstance() {
        return new ProcesoLogDTO();
    }
 
}
