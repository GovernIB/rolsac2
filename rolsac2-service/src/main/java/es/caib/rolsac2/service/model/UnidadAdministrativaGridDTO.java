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

    private String identificador;

    private Integer numero = 0;

    /**
     * Codigo DIR3
     */
    private String codigoDIR3;
    //private String entidad;

    private Long idEntidad;

    /**
     * Nombre del padre
     */
    private Literal nombrePadre;

    /**
     * Tipo
     */
    private String tipo;

    /**
     * Padre
     **/
    private UnidadAdministrativaGridDTO padre;

    /**
     * Orden
     */
    private Integer orden;

    /**
     * Nombre
     */
    private Literal nombre;
    /**
     * Estado, siendo:
     * V (Vigente)
     * X (Borrado)
     */
    private String estado;

    /**
     * Instancia una nueva Unidad administrativa grid dto.
     */
    public UnidadAdministrativaGridDTO() {
    }

    /**
     * Obtiene id string.
     *
     * @return id string
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
     * @param idString codigo to set
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
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene nombre padre.
     *
     * @return nombre padre
     */
    public Literal getNombrePadre() {
        return nombrePadre;
    }

    /**
     * Establece nombre padre.
     *
     * @param nombrePadre nombre padre
     */
    public void setNombrePadre(Literal nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    /**
     * Obtiene tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene nombre.
     *
     * @return nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene orden.
     *
     * @return orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * Establece orden.
     *
     * @param orden orden
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Obtiene codigo dir 3.
     *
     * @return codigo dir 3
     */
    public String getCodigoDIR3() {
        return codigoDIR3;
    }

    /**
     * Establece codigo dir 3.
     *
     * @param codigoDIR3 codigo dir 3
     */
    public void setCodigoDIR3(String codigoDIR3) {
        this.codigoDIR3 = codigoDIR3;
    }

    public UnidadAdministrativaGridDTO getPadre() {
        return padre;
    }

    public void setPadre(UnidadAdministrativaGridDTO padre) {
        this.padre = padre;
    }

    public boolean esRaiz() {
        return this.padre == null;
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Icono de visibilidad
     *
     * @return
     */
    public String getIcon() {
        //El valor V es de Vigente
        if (this.getEstado() != null && this.getEstado().equals("V")) {
            return "pi pi-eye iconoVerde";
        } else {
            return "pi pi-eye-slash iconoRojo";
        }
    }

    /**
     * Comprueba si es vigente
     *
     * @return
     */
    public boolean isVigente() {
        return (this.getEstado() != null && this.getEstado().equals("V"));
    }

    /**
     * Comprueba si no es vigente.
     *
     * @return
     */
    public boolean isNotVigente() {
        return !(this.getEstado() != null && this.getEstado().equals("V"));
    }
}
