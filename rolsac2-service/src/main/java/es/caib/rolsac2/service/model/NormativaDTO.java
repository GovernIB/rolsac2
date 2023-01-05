package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Dades de una normativa.
 *
 * @author Indra
 */
@Schema(name = "Normativa")
public class NormativaDTO extends ModelApi {
    private Long codigo;

    private TipoBoletinDTO boletinOficial;

    //private AfectacionDTO afectacion;
    private TipoNormativaDTO tipoNormativa;
    private String numero;
    private LocalDate fechaAprobacion;
    //private TipoBoletinDTO tipoBoletin;
    private LocalDate fechaBoletin;
    private String numeroBoletin;
    private Literal urlBoletin;
    private String nombreResponsable;
    private Literal nombre;
    private EntidadDTO entidad;

    private List<DocumentoNormativaDTO> documentosNormativa;

    private List<UnidadAdministrativaGridDTO> unidadesAdministrativas;

    private List<AfectacionDTO> afectaciones;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public TipoNormativaDTO getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(TipoNormativaDTO tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }


    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    public String getNumeroBoletin() {
        return numeroBoletin;
    }

    public void setNumeroBoletin(String numeroBoletin) {
        this.numeroBoletin = numeroBoletin;
    }

    public Literal getUrlBoletin() {
        return urlBoletin;
    }

    public void setUrlBoletin(Literal urlBoletin) {
        this.urlBoletin = urlBoletin;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public TipoBoletinDTO getBoletinOficial() {
        return boletinOficial;
    }

    public void setBoletinOficial(TipoBoletinDTO boletin) {
        this.boletinOficial = boletin;
    }

    public Literal getNombre() {
        return nombre;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public List<DocumentoNormativaDTO> getDocumentosNormativa() { return documentosNormativa; }

    public void setDocumentosNormativa(List<DocumentoNormativaDTO> documentosNormativa) { this.documentosNormativa = documentosNormativa; }

    public List<UnidadAdministrativaGridDTO> getUnidadesAdministrativas() { return unidadesAdministrativas; }

    public void setUnidadesAdministrativas(List<UnidadAdministrativaGridDTO> unidadesAdministrativas) { this.unidadesAdministrativas = unidadesAdministrativas; }

    public List<AfectacionDTO> getAfectaciones() { return afectaciones; }

    public void setAfectaciones(List<AfectacionDTO> afectaciones) { this.afectaciones = afectaciones; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormativaDTO that = (NormativaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "NormativaDTO{" +
                "id=" + codigo +
                ", numero='" + numero + '\'' +
                '}';
    }

    public NormativaGridDTO convertDTOtoGridDTO() {
        NormativaGridDTO normativaGridDTO = new NormativaGridDTO();
        normativaGridDTO.setCodigo(this.codigo);
        normativaGridDTO.setTipoNormativa(this.tipoNormativa == null ? null : this.tipoNormativa.getIdentificador());
        normativaGridDTO.setNumero(this.numero);
        normativaGridDTO.setBoletinOficial(this.boletinOficial == null ? null : this.boletinOficial.getIdentificador());
        normativaGridDTO.setFechaAprobacion(this.fechaAprobacion == null ? null : this.fechaAprobacion.toString());
        normativaGridDTO.setTitulo(this.nombre);
        return normativaGridDTO;

    }
}
