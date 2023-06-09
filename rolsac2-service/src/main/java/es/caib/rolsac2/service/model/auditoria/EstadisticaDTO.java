package es.caib.rolsac2.service.model.auditoria;

import java.util.Objects;

/**
 * Clase de negocio necesaria para la impresión del report de estadística.
 *
 * @author INDRA
 */
public class EstadisticaDTO implements Comparable<EstadisticaDTO> {

    /**
     * Serial version UID.
     **/
    private static final long serialVersionUID = 133830303728753054L;

    /**
     * Codigo
     **/
    private Long codigo;

    /**
     * Organo
     **/
    private String organo;

    /**
     * Nombre
     **/
    private String nombre;

    /**
     * Apellido1
     **/
    private String ape1;

    /**
     * Apellido2
     **/
    private String ape2;

    /**
     * Contenido
     **/
    private String contenido;

    /**
     * Número de modificaciones
     **/
    private Integer modificaciones;


    /**
     * Constructor.
     **/
    public EstadisticaDTO() {
        super();
    }


    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }


    /**
     * Establece codigo.
     *
     * @param codigo codigo a establecer
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }


    /**
     * Obtiene organo.
     *
     * @return organo
     */
    public String getOrgano() {
        return organo;
    }


    /**
     * Establece organo.
     *
     * @param organo organo a establecer
     */
    public void setOrgano(final String organo) {
        this.organo = organo;
    }


    /**
     * Obtiene nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * Establece nombre.
     *
     * @param nombre nombre a establecer
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }


    /**
     * Obtiene ape1.
     *
     * @return ape1
     */
    public String getApe1() {
        return ape1;
    }


    /**
     * Establece ape1.
     *
     * @param ape1 ape1 a establecer
     */
    public void setApe1(final String ape1) {
        this.ape1 = ape1;
    }


    /**
     * Obtiene ape2.
     *
     * @return ape2
     */
    public String getApe2() {
        return ape2;
    }


    /**
     * Establece ape2.
     *
     * @param ape2 ape2 a establecer
     */
    public void setApe2(final String ape2) {
        this.ape2 = ape2;
    }


    /**
     * Obtiene contenido.
     *
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }


    /**
     * Establece contenido.
     *
     * @param contenido contenido a establecer
     */
    public void setContenido(final String contenido) {
        this.contenido = contenido;
    }


    /**
     * Obtiene modificaciones.
     *
     * @return modificaciones
     */
    public Integer getModificaciones() {
        return modificaciones;
    }


    /**
     * Establece modificaciones.
     *
     * @param modificaciones modificaciones a establecer
     */
    public void setModificaciones(final Integer modificaciones) {
        this.modificaciones = modificaciones;
    }


    @Override
    public int compareTo(final EstadisticaDTO o) {
        return this.getCodigo().compareTo(o.getCodigo());
    }

    @Override
    public boolean equals(final Object objeto) {
        boolean retorno;
        if (objeto == null) {
            retorno = false;
        } else if (!(objeto instanceof EstadisticaDTO)) {
            retorno = false;
        } else {
            final EstadisticaDTO est = (EstadisticaDTO) objeto;
            if (est.getCodigo() == null || this.getCodigo() == null) {
                retorno = false;
            } else {
                retorno = est.getCodigo().compareTo(this.getCodigo()) == 0;
            }
        }
        return retorno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCodigo());
    }

    public static EstadisticaDTO cast(final Object[] resultado, final String tipo) {
        final EstadisticaDTO est = new EstadisticaDTO();
        est.setNombre((String) resultado[1]);
        est.setApe1((String) resultado[2]);
        est.setApe2((String) resultado[3]);
        est.setOrgano((String) resultado[4]);
        est.setModificaciones(resultado[5] == null ? 0 : Integer.valueOf(resultado[5].toString()));
        est.setContenido(tipo);

        return est;
    }

}
