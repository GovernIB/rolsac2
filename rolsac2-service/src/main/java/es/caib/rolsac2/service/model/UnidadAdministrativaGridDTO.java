package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "UnidadAdministrativa")
public class UnidadAdministrativaGridDTO extends ModelApi {

    private Long codigo;
    private String codigoDIR3;
    //private String entidad;
    private Literal nombrePadre;
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
    private Integer orden;
    private Literal nombre;
    //private Literal presentacion;
    //private Literal url;
    //private Literal responsable;

    public UnidadAdministrativaGridDTO() {
    }

    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Literal getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(Literal nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Literal getNombre() {
        return nombre;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getCodigoDIR3() {
        return codigoDIR3;
    }

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
