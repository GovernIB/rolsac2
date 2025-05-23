package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The type Ayuda Imagen grid dto.
 */
@Schema(name = "AyudaImagenGrid")
public class AyudaImagenGridDTO extends ModelApi implements Comparable<AyudaImagenGridDTO> {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String filename;

    /**
     * Identificador
     */
    private String ruta;

    /**
     * Perfil
     */
    private Long total;

    /**
     * Existe JFichero
     */
    private boolean existeJFichero;

    /**
     * Existe fichero fisico.
     */
    private boolean existeFicheroFisico;
    /**
     * Fecha modificacion
     */
    private Date fechaCreacion;

    /**
     * De apoyo para seleccionar.
     */
    private boolean seleccionado;

    /**
     * Instancia un nuevo Tema grid dto.
     */
    public AyudaImagenGridDTO() {

    }

    @Override
    public int compareTo(AyudaImagenGridDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getFilename(), data2.getFilename()) != 0) {
            return UtilComparador.compareTo(this.getFilename(), data2.getFilename());
        }

        return 0;
    }

    public static int compareTo(List<AyudaImagenGridDTO> dato, List<AyudaImagenGridDTO> dato2) {
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
            for (AyudaImagenGridDTO tipo : dato) {
                boolean existe = false;
                for (AyudaImagenGridDTO tipo2 : dato2) {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isExisteJFichero() {
        return existeJFichero;
    }

    public void setExisteJFichero(boolean existeJFichero) {
        this.existeJFichero = existeJFichero;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean isExisteFicheroFisico() {
        return existeFicheroFisico;
    }

    public void setExisteFicheroFisico(boolean existeFicheroFisico) {
        this.existeFicheroFisico = existeFicheroFisico;
    }

    @Override
    public String toString() {
        return "AyudaGridDTO{" + "codigo=" + codigo + ", filename=" + filename + ", total='" + total + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AyudaImagenGridDTO that = (AyudaImagenGridDTO) o;
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
        AyudaImagenGridDTO tipo = new AyudaImagenGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdString(this.getIdString());
        tipo.setFechaCreacion(this.getFechaCreacion());
        tipo.setRuta(this.getRuta());
        tipo.setTotal(this.getTotal());
        tipo.setFilename(this.getFilename());
        tipo.setExisteJFichero(this.isExisteJFichero());
        tipo.setExisteFicheroFisico(this.isExisteFicheroFisico());
        return tipo;
    }

    public boolean booleanTotal() {
        return total != null && total > 0;
    }

    public boolean booleanExisteFicheroFisico() {
        return existeFicheroFisico;
    }
}
