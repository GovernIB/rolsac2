package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * The type Normativa grid dto.
 */
@XmlRootElement
@Schema(name = "NormativaGrid")
public class NormativaGridDTO extends ModelApi implements Cloneable, Comparable<NormativaGridDTO> {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Orden
     */
    private Integer orden;

    /**
     * Titulo
     */
    private Literal titulo;

    /**
     * Tipo normativa
     */
    private String tipoNormativa;

    /**
     * Numero
     */
    private String numero;

    /**
     * Boletin oficial
     */
    private String boletinOficial;

    /**
     * Fecha de aprobacion
     */
    private String fechaAprobacion;


    /**
     * Instancia una nueva Normativa grid dto.
     */
    public NormativaGridDTO() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormativaGridDTO that = (NormativaGridDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
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
     * Obtiene tipo normativa.
     *
     * @return tipo normativa
     */
    public String getTipoNormativa() {
        return tipoNormativa;
    }

    /**
     * Establece tipo normativa.
     *
     * @param tipoNormativa tipo normativa
     */
    public void setTipoNormativa(String tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    /**
     * Obtiene fecha aprobacion.
     *
     * @return fecha aprobacion
     */
    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    /**
     * Establece fecha aprobacion.
     *
     * @param fechaAprobacion fecha aprobacion
     */
    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    /**
     * Obtiene boletin oficial.
     *
     * @return boletin oficial
     */
    public String getBoletinOficial() {
        return boletinOficial;
    }

    /**
     * Establece boletin oficial.
     *
     * @param boletinOficial boletin oficial
     */
    public void setBoletinOficial(String boletinOficial) {
        this.boletinOficial = boletinOficial;
    }

    /**
     * Obtiene titulo.
     *
     * @return titulo
     */
    public Literal getTitulo() {
        return titulo;
    }

    /**
     * Establece titulo.
     *
     * @param titulo titulo
     */
    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene numero.
     *
     * @return numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece numero.
     *
     * @param numero numero
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        NormativaGridDTO tipo = new NormativaGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setTipoNormativa(this.getTipoNormativa());
        tipo.setNumero(this.getNumero());
        tipo.setOrden(this.getOrden());
        tipo.setFechaAprobacion(this.getFechaAprobacion());
        tipo.setIdString(this.getIdString());
        if (this.getTitulo() != null) {
            tipo.setTitulo((Literal) this.getTitulo().clone());
        }
        return tipo;
    }

    @Override
    public String toString() {
        return "NormativaGridDTO{" +
                "codigo=" + codigo +
                '}';
    }

    /*
        @Override
        public int compare(NormativaGridDTO o1, NormativaGridDTO data2) {
            if (data2 == null) {
                return 1;
            }

            if (UtilComparador.compareTo(o1.getCodigo(), data2.getCodigo()) != 0) {
                return UtilComparador.compareTo(o1.getCodigo(), data2.getCodigo());
            }

            if (UtilComparador.compareTo(o1.getTipoNormativa(), data2.getTipoNormativa()) != 0) {
                return UtilComparador.compareTo(o1.getTipoNormativa(), data2.getTipoNormativa());
            }

            if (UtilComparador.compareTo(o1.getTitulo(), data2.getTitulo()) != 0) {
                return UtilComparador.compareTo(o1.getTitulo(), data2.getTitulo());
            }

            if (UtilComparador.compareTo(o1.getBoletinOficial(), data2.getBoletinOficial()) != 0) {
                return UtilComparador.compareTo(o1.getBoletinOficial(), data2.getBoletinOficial());
            }

            if (UtilComparador.compareTo(o1.getNumero(), data2.getNumero()) != 0) {
                return UtilComparador.compareTo(o1.getNumero(), data2.getNumero());
            }
            return 0;
        }
        */
    @Override
    public int compareTo(NormativaGridDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getOrden(), data2.getOrden()) != 0) {
            return UtilComparador.compareTo(this.getOrden(), data2.getOrden());
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getTipoNormativa(), data2.getTipoNormativa()) != 0) {
            return UtilComparador.compareTo(this.getTipoNormativa(), data2.getTipoNormativa());
        }

        if (UtilComparador.compareTo(this.getTitulo(), data2.getTitulo()) != 0) {
            return UtilComparador.compareTo(this.getTitulo(), data2.getTitulo());
        }

        if (UtilComparador.compareTo(this.getBoletinOficial(), data2.getBoletinOficial()) != 0) {
            return UtilComparador.compareTo(this.getBoletinOficial(), data2.getBoletinOficial());
        }

        if (UtilComparador.compareTo(this.getNumero(), data2.getNumero()) != 0) {
            return UtilComparador.compareTo(this.getNumero(), data2.getNumero());
        }
        return 0;
    }

    public static int compareTo(List<NormativaGridDTO> dato, List<NormativaGridDTO> dato2) {
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
            for (NormativaGridDTO tipo : dato) {
                boolean existe = false;
                for (NormativaGridDTO tipo2 : dato2) {
                    if (tipo2.getCodigo() != null && tipo.getCodigo() != null && tipo.getCodigo().compareTo(tipo2.getCodigo()) == 0) {
                        if (tipo.getOrden().compareTo(tipo2.getOrden()) != 0) {
                            return tipo.getOrden().compareTo(tipo2.getOrden());
                        }
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
