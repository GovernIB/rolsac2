package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The type Ayuda grid dto.
 */
@Schema(name = "AyudaGrid")
public class AyudaGridDTO extends ModelApi implements Comparable<AyudaGridDTO> {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Perfil
     */
    private String perfil;

    /**
     * Descripción
     */
    private Literal descripcion;

    /**
     * Fecha creacion
     */
    private Date fechaCreacion;

    /**
     * Fecha modificacion
     */
    private Date fechaModificacion;


    /**
     * Instancia un nuevo Tema grid dto.
     */
    public AyudaGridDTO() {
    }

    @Override
    public int compareTo(AyudaGridDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }

        return 0;
    }

    public static int compareTo(List<AyudaGridDTO> dato, List<AyudaGridDTO> dato2) {
        if ((dato == null || dato.size() == 0) && (dato2 == null || dato2.size() == 0)) {
            return 0;
        }
        if ((dato == null || dato.size() == 0) && (dato2 != null && dato2.size() > 0)) {
            return -1;
        }
        if ((dato != null && dato.size() > 0) && (dato2 == null || dato2.size() == 0)) {
            return 1;
        }

        if (dato.size() > dato2.size()) {
            return 1;
        } else if (dato2.size() > dato.size()) {
            return -1;
        } else {
            for (AyudaGridDTO tipo : dato) {
                boolean existe = false;
                for (AyudaGridDTO tipo2 : dato2) {
                    if (tipo.compareTo(tipo2) == 0) {
                        existe = true;
                    }
                }
                if (!existe) {
                    return 1;
                }
            }
        }
        return 0;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return "AyudaGridDTO{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", perfil='" + perfil + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AyudaGridDTO that = (AyudaGridDTO) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public Object clone() {
        AyudaGridDTO tipo = new AyudaGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setDescripcion(this.getDescripcion());
        tipo.setIdString(this.getIdString());
        tipo.setPerfil(this.getPerfil());
        tipo.setFechaCreacion(this.getFechaCreacion());
        tipo.setFechaModificacion(this.getFechaModificacion());
        return tipo;
    }

}
