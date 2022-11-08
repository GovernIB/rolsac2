package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "Normativa")
public class NormativaGridDTO extends ModelApi {

    private Long codigo;
    private Literal titulo;
    private String tipoNormativa;
    private String numero;
    private String boletinOficial;
    private String fechaAprobacion;



    public NormativaGridDTO() {

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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(String tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getBoletinOficial() {
        return boletinOficial;
    }

    public void setBoletinOficial(String boletinOficial) {
        this.boletinOficial = boletinOficial;
    }

    public Literal getTitulo() {
        return titulo;
    }

    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
