package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Unidad administrativa grid dto.
 */
@Schema(name = "UnidadAdministrativa")
public class UnidadAdministrativaGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Codigo DIR3
     */
    private String codigoDIR3;
    //private String entidad;

    /**
     * Nombre del padre
     */
    private Literal nombrePadre;

    /**
     * Tipo
     */
    private String tipo;
    //private List<UnidadAdministrativaDTO> hijos;
    //private String identificador;
    //private String abreviatura;
    //private String telefono;
    //private String fax;
    //private String email;
    //private String dominio;
    //private String responsableNombre;
    //private String responsableSexo;
    //private String responsableEmail;

    /**
     * Orden
     */
    private Integer orden;

    /**
     * Nombre
     */
    private Literal nombre;
    //private Literal presentacion;
    //private Literal url;
    //private Literal responsable;

    /**
     * Instancia una nueva Unidad administrativa grid dto.
     */
    public UnidadAdministrativaGridDTO() {
    }

    /**
     * Obtiene id string.
     *
     * @return  id string
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene nombre padre.
     *
     * @return  nombre padre
     */
    public Literal getNombrePadre() {
        return nombrePadre;
    }

    /**
     * Establece nombre padre.
     *
     * @param nombrePadre  nombre padre
     */
    public void setNombrePadre(Literal nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    /**
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo  tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene orden.
     *
     * @return  orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * Establece orden.
     *
     * @param orden  orden
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Obtiene codigo dir 3.
     *
     * @return  codigo dir 3
     */
    public String getCodigoDIR3() {
        return codigoDIR3;
    }

    /**
     * Establece codigo dir 3.
     *
     * @param codigoDIR3  codigo dir 3
     */
    public void setCodigoDIR3(String codigoDIR3) {
        this.codigoDIR3 = codigoDIR3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadAdministrativaGridDTO that = (UnidadAdministrativaGridDTO) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, codigoDIR3, nombrePadre, tipo, orden, nombre);
    }
}
