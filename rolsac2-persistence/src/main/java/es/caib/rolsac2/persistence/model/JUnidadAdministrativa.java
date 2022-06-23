package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ua-sequence", sequenceName = "RS2_UNIADM_SEQ", allocationSize = 1)
@Table(name = "RS2_UNIADM",
        indexes = {
                @Index(name = "RS2_UNIADM_PK_I", columnList = "UNAD_CODIGO")
        }
)
public class JUnidadAdministrativa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-sequence")
    @Column(name = "UNAD_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UNAD_CODENTI", nullable = false)
    private JEntidad entidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNAD_TIPOUA")
    private JTipoUnidadAdministrativa tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNAD_UNADPADRE")
    private JUnidadAdministrativa padre;

    @Column(name = "UNAD_DIR3", length = 20)
    private String codigoDIR3;

    @Column(name = "UNAD_IDENTI", length = 50)
    private String identificacion;

    @Column(name = "UNAD_ABREVI", length = 10)
    private String abreviatura;

    @Column(name = "UNAD_TFNO", length = 9)
    private String telefono;

    @Column(name = "UNAD_FAX", length = 9)
    private String fax;

    @Column(name = "UNAD_EMAIL", length = 100)
    private String email;

    @Column(name = "UNAD_DOMINI")
    private String dominio;

    @Column(name = "UNAD_RSPNOM")
    private String responsableNombre;

    @Column(name = "UNAD_RSPEMA", length = 100)
    private String responsableEmail;

    @Column(name = "UNAD_RSPSEX", length = 100)
    private String responsableSexo;

    @Column(name = "UNAD_ORDEN", nullable = false)
    private Integer orden;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad unadCodenti) {
        this.entidad = unadCodenti;
    }

    public JTipoUnidadAdministrativa getTipo() {
        return tipo;
    }

    public void setTipo(JTipoUnidadAdministrativa unadTipoua) {
        this.tipo = unadTipoua;
    }

    public JUnidadAdministrativa getPadre() {
        return padre;
    }

    public void setPadre(JUnidadAdministrativa unadUnadpadre) {
        this.padre = unadUnadpadre;
    }

    public String getCodigoDIR3() {
        return codigoDIR3;
    }

    public void setCodigoDIR3(String unadDir3) {
        this.codigoDIR3 = unadDir3;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String unadIdenti) {
        this.identificacion = unadIdenti;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String unadAbrevi) {
        this.abreviatura = unadAbrevi;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String unadTfno) {
        this.telefono = unadTfno;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String unadFax) {
        this.fax = unadFax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String unadEmail) {
        this.email = unadEmail;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String unadDomini) {
        this.dominio = unadDomini;
    }

    public String getResponsableNombre() {
        return responsableNombre;
    }

    public void setResponsableNombre(String unadRspnom) {
        this.responsableNombre = unadRspnom;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public void setResponsableEmail(String unadRspema) {
        this.responsableEmail = unadRspema;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer unadOrden) {
        this.orden = unadOrden;
    }

    public String getResponsableSexo() {
        return responsableSexo;
    }

    public void setResponsableSexo(String responsableSexo) {
        this.responsableSexo = responsableSexo;
    }
}