package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "normativa-sequence", sequenceName = "RS2_NORMA_SEQ", allocationSize = 1)
@Table(name = "RS2_NORMA",
        indexes = {
                @Index(name = "RS2_NORMA_PK_I", columnList = "NORM_CODIGO")
        })
public class JNormativa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normativa-sequence")
    @Column(name = "NORM_CODIGO", nullable = false)
    private Integer codigo;

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
    private JBoletinOficial boletin;

    @Column(name = "NORM_BOLEFEC")
    private LocalDate fechaBoletin;

    @Column(name = "NORM_BOLENUM", length = 50)
    private String numeroBoletin;

    @Column(name = "NORM_BOLEURL", length = 500)
    private String urlBoletin;

    @Column(name = "NORM_RESPNOM")
    private String nombreResponsable;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
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

    public JBoletinOficial getBoletin() {
        return boletin;
    }

    public void setBoletin(JBoletinOficial normBolecod) {
        this.boletin = normBolecod;
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

}