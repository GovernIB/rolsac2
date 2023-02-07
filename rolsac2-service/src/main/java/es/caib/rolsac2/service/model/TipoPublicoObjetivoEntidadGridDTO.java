package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

/**
 * Dades d'un Tipo Publico Objetivo.
 *
 * @author Indra
 */
@Schema(name = "TipoPublicoObjetivoEntidadGrid")
public class TipoPublicoObjetivoEntidadGridDTO extends ModelApi implements Cloneable {

    private Long codigo;
    private Literal tipo;
    private String identificador;

    private Literal descripcion;

    /**
     * Se utilizan la siguiente para el selector de procedimientos.
     **/
    private Long codigoProcWF;

    private boolean empleadoPublico;

    /**
     * Instancia un nuevo Tipo publico objetivo entidad grid dto.
     */
    public TipoPublicoObjetivoEntidadGridDTO() {
    }


    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return codigo
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
     * Instancia un nuevo Tipo publico objetivo entidad grid dto.
     *
     * @param id id
     */
    public TipoPublicoObjetivoEntidadGridDTO(Long id) {
        this.codigo = id;
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

    /**
     * Obtiene tipo.
     *
     * @return tipo
     */
    public Literal getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(Literal tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene codigo proc wf.
     *
     * @return codigo proc wf
     */
    public Long getCodigoProcWF() {
        return codigoProcWF;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece codigo proc wf.
     *
     * @param codigoProcWF codigo proc wf
     */
    public void setCodigoProcWF(Long codigoProcWF) {
        this.codigoProcWF = codigoProcWF;
    }

    /**
     * Obtiene si es empleado publico.
     *
     * @return empleadoPublico
     */
    public boolean isEmpleadoPublico() {
        return empleadoPublico;
    }

    /**
     * Establece el empleado publico
     *
     * @param empleadoPublico Si es empleado publico
     */
    public void setEmpleadoPublico(boolean empleadoPublico) {
        this.empleadoPublico = empleadoPublico;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        TipoPublicoObjetivoEntidadGridDTO tipo = new TipoPublicoObjetivoEntidadGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        tipo.setCodigoProcWF(this.getCodigoProcWF());
        tipo.setEmpleadoPublico(this.isEmpleadoPublico());
        tipo.setIdString(this.getIdString());
        if (this.getDescripcion() != null) {
            tipo.setDescripcion((Literal) this.getDescripcion().clone());
        }
        return tipo;
    }

    public int compareTo(TipoPublicoObjetivoEntidadGridDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }

        if (UtilComparador.compareTo(this.getIdentificador(), data2.getIdentificador()) != 0) {
            return UtilComparador.compareTo(this.getIdentificador(), data2.getIdentificador());
        }

        return 0;
    }


    public static int compareTo(List<TipoPublicoObjetivoEntidadGridDTO> dato, List<TipoPublicoObjetivoEntidadGridDTO> dato2) {
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
            for (TipoPublicoObjetivoEntidadGridDTO tipo : dato) {
                boolean existe = false;
                for (TipoPublicoObjetivoEntidadGridDTO tipo2 : dato2) {
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
}
