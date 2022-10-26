package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "ua-sequence", sequenceName = "RS2_UNIADM_SEQ", allocationSize = 1)
@Table(name = "RS2_UNIADM", indexes = {@Index(name = "RS2_UNIADM_PK_I", columnList = "UNAD_CODIGO")})

@NamedQueries({
        @NamedQuery(name = JUnidadAdministrativa.FIND_BY_ID,
                query = "select p from JUnidadAdministrativa p where p.codigo = :id"),
        @NamedQuery(name = JUnidadAdministrativa.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JUnidadAdministrativa p where p.identificador = :identificador")
})

public class JUnidadAdministrativa extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "UnidadAdministrativa.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "UnidadAdministrativa.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-sequence")
    @Column(name = "UNAD_CODIGO", nullable = false)
    private Long codigo;

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
    private String identificador;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNAD_RSPSEX")
    private JTipoSexo responsableSexo;

    @Column(name = "UNAD_ORDEN", nullable = false, length = 3)
    private Integer orden;

    @OneToMany(mappedBy = "unidadAdministrativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JUnidadAdministrativaTraduccion> traducciones;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String unadIdenti) {
        this.identificador = unadIdenti;
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

    public JTipoSexo getResponsableSexo() {
        return responsableSexo;
    }

    public void setResponsableSexo(JTipoSexo responsableSexo) {
        this.responsableSexo = responsableSexo;
    }

    public List<JUnidadAdministrativaTraduccion> getTraducciones() {
        return traducciones;
    }

    public void setTraducciones(List<JUnidadAdministrativaTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JUnidadAdministrativa that = (JUnidadAdministrativa) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JUnidadAdministrativa{" +
                "id=" + codigo +
                ", entidad=" + entidad +
                ", tipo=" + tipo +
                ", padre=" + padre +
                ", codigoDIR3='" + codigoDIR3 + '\'' +
                ", identificacion='" + identificador + '\'' +
                ", abreviatura='" + abreviatura + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", dominio='" + dominio + '\'' +
                ", responsableNombre='" + responsableNombre + '\'' +
                ", responsableEmail='" + responsableEmail + '\'' +
                ", responsableSexo=" + responsableSexo +
                ", orden=" + orden +
                '}';
    }

    public UnidadAdministrativaDTO toDTO() {
        UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        ua.setCodigo(this.getCodigo());
        ua.setCodigoDIR3(this.getCodigoDIR3());
        ua.setIdentificador(this.getIdentificador());
        ua.setOrden(this.getOrden());
        ua.setAbreviatura(this.getAbreviatura());
        ua.setDominio(this.getDominio());

        Literal nombre = new Literal();
        Literal url = new Literal();
        Literal presentacion = new Literal();
        Literal responsableCV = new Literal();
        if (this.getTraducciones() != null) {
            for (JUnidadAdministrativaTraduccion trad : this.getTraducciones()) {
                nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
                url.add(new Traduccion(trad.getIdioma(), trad.getUrl()));
                presentacion.add(new Traduccion(trad.getIdioma(), trad.getPresentacion()));
                responsableCV.add(new Traduccion(trad.getIdioma(), trad.getResponsableCV()));
            }
        }
        ua.setNombre(nombre);
        ua.setUrl(url);
        ua.setPresentacion(presentacion);
        ua.setResponsable(responsableCV);
        return ua;
    }
}
