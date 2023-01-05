package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * The type Normativa grid dto.
 */
@Schema(name = "Normativa")
public class NormativaGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

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
     * Obtiene tipo normativa.
     *
     * @return  tipo normativa
     */
    public String getTipoNormativa() {
        return tipoNormativa;
    }

    /**
     * Establece tipo normativa.
     *
     * @param tipoNormativa  tipo normativa
     */
    public void setTipoNormativa(String tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    /**
     * Obtiene fecha aprobacion.
     *
     * @return  fecha aprobacion
     */
    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    /**
     * Establece fecha aprobacion.
     *
     * @param fechaAprobacion  fecha aprobacion
     */
    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    /**
     * Obtiene boletin oficial.
     *
     * @return  boletin oficial
     */
    public String getBoletinOficial() {
        return boletinOficial;
    }

    /**
     * Establece boletin oficial.
     *
     * @param boletinOficial  boletin oficial
     */
    public void setBoletinOficial(String boletinOficial) {
        this.boletinOficial = boletinOficial;
    }

    /**
     * Obtiene titulo.
     *
     * @return  titulo
     */
    public Literal getTitulo() {
        return titulo;
    }

    /**
     * Establece titulo.
     *
     * @param titulo  titulo
     */
    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene numero.
     *
     * @return  numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece numero.
     *
     * @param numero  numero
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "NormativaGridDTO{" +
                "codigo=" + codigo +
                '}';
    }

}
