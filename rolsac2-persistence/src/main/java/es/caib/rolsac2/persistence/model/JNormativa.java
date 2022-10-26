package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JEntidadTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JNormativaTraduccion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "normativa-sequence", sequenceName = "RS2_NORMA_SEQ", allocationSize = 1)
@Table(name = "RS2_NORMA",
        indexes = {
                @Index(name = "RS2_NORMA_PK_I", columnList = "NORM_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JNormativa.FIND_BY_ID,
                query = "select p from JNormativa p where p.codigo = :codigo")
})
public class JNormativa extends BaseEntity {

    public static final String FIND_BY_ID = "normativa.FIND_BY_ID";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normativa-sequence")
    @Column(name = "NORM_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NORM_CODENTI", nullable = false)
    private JEntidad entidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NORM_TIPNOR", nullable = false)
    private JTipoNormativa tipoNormativa;

    @Column(name = "NORM_NUMERO", length = 50)
    private String numero;

    @Column(name = "NORM_FCAPRO", nullable = false)
    private LocalDate fechaAprobacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NORM_BOLECOD", nullable = false)
    private JBoletinOficial boletinOficial;

    @Column(name = "NORM_BOLEFEC")
    private LocalDate fechaBoletin;

    @Column(name = "NORM_BOLENUM", length = 50)
    private String numeroBoletin;

    @Column(name = "NORM_BOLEURL", length = 500)
    private String urlBoletin;

    @Column(name = "NORM_RESPNOM")
    private String nombreResponsable;

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "normativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JNormativaTraduccion> descripcion;

    @OneToMany(mappedBy = "normativa", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JDocumentoNormativa> documentosNormativa;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad normCodenti) {
        this.entidad = normCodenti;
    }

    public JTipoNormativa getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(JTipoNormativa normTipnor) {
        this.tipoNormativa = normTipnor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String normNumero) {
        this.numero = normNumero;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate normFcapro) {
        this.fechaAprobacion = normFcapro;
    }

    public JBoletinOficial getBoletinOficial() {
        return boletinOficial;
    }

    public void setBoletinOficial(JBoletinOficial normBolecod) {
        this.boletinOficial = normBolecod;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate normBolefec) {
        this.fechaBoletin = normBolefec;
    }

    public String getNumeroBoletin() {
        return numeroBoletin;
    }

    public void setNumeroBoletin(String normBolenum) {
        this.numeroBoletin = normBolenum;
    }

    public String getUrlBoletin() {
        return urlBoletin;
    }

    public void setUrlBoletin(String normBoleurl) {
        this.urlBoletin = normBoleurl;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String normRespnom) {
        this.nombreResponsable = normRespnom;
    }

    public List<JNormativaTraduccion> getDescripcion() {
        return descripcion;
    }

    public List<JDocumentoNormativa> getDocumentosNormativa() { return documentosNormativa; }

    public void setDocumentosNormativa(List<JDocumentoNormativa> documentosNormativa) { this.documentosNormativa = documentosNormativa; }

    public void setDescripcion(List<JNormativaTraduccion> descripcion) {
        if (this.descripcion == null || this.descripcion.isEmpty()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion.addAll(descripcion);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JNormativa that = (JNormativa) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JNormativa{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", tipoNormativa=" + tipoNormativa +
                ", numero='" + numero + '\'' +
                ", fechaAprobacion=" + fechaAprobacion +
                ", boletinOficial=" + boletinOficial +
                ", fechaBoletin=" + fechaBoletin +
                ", numeroBoletin='" + numeroBoletin + '\'' +
                ", urlBoletin='" + urlBoletin + '\'' +
                ", nombreResponsable='" + nombreResponsable + '\'' +
                ", descripcion=" + descripcion +
                ", documentosNormativa=" + documentosNormativa +
                '}';
    }
}