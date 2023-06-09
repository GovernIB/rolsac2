package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@SequenceGenerator(name = "ua-sequence", sequenceName = "RS2_UNIADM_SEQ", allocationSize = 1)
@Table(name = "RS2_UNIADM", indexes = {@Index(name = "RS2_UNIADM_PK_I", columnList = "UNAD_CODIGO")})

@NamedQueries({@NamedQuery(name = JUnidadAdministrativa.FIND_BY_ID, query = "select p from JUnidadAdministrativa p where p.codigo = :id"), @NamedQuery(name = JUnidadAdministrativa.COUNT_BY_IDENTIFICADOR, query = "select count(p) from JUnidadAdministrativa p where p.identificador = :identificador")})

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

    @Column(name = "UNAD_VERSION", nullable = false, length = 3)
    private Integer version;

    @OneToMany(mappedBy = "unidadAdministrativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JUnidadAdministrativaTraduccion> traducciones;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RS2_USERUA", joinColumns = {@JoinColumn(name = "UAUS_CODUA")}, inverseJoinColumns = {@JoinColumn(name = "UAUS_CODUSER")})
    private Set<JUsuario> usuarios;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RS2_UADNOR", joinColumns = {@JoinColumn(name = "UANO_CODUNA")}, inverseJoinColumns = {@JoinColumn(name = "UANO_CODNORM")})
    private Set<JNormativa> normativas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RS2_UATEMA", joinColumns = {@JoinColumn(name = "UATE_CODUNA")}, inverseJoinColumns = {@JoinColumn(name = "UATE_CODTEMA")})
    private Set<JTema> temas;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Set<JUsuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<JUsuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Set<JNormativa> getNormativas() {
        return normativas;
    }

    public void setNormativas(Set<JNormativa> normativas) {
        this.normativas = normativas;
    }

    public Set<JTema> getTemas() {
        return temas;
    }

    public void setTemas(Set<JTema> temas) {
        this.temas = temas;
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
        return "JUnidadAdministrativa{" + "id=" + codigo + ", tipo=" + tipo + ", padre=" + padre + ", codigoDIR3='" + codigoDIR3 + '\'' + ", identificacion='" + identificador + '\'' + ", telefono='" + telefono + '\'' + ", fax='" + fax + '\'' + ", email='" + email + '\'' + ", dominio='" + dominio + '\'' + ", responsableNombre='" + responsableNombre + '\'' + ", responsableEmail='" + responsableEmail + '\'' + ", responsableSexo=" + responsableSexo + ", orden=" + orden + ", version=" + version + '}';
    }

    public UnidadAdministrativaDTO toDTO() {
        UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        ua.setCodigo(this.getCodigo());
        ua.setCodigoDIR3(this.getCodigoDIR3());
        ua.setIdentificador(this.getIdentificador());
        ua.setOrden(this.getOrden());
        ua.setDominio(this.getDominio());
        ua.setVersion(this.getVersion());

        if (padre != null) {
            UnidadAdministrativaDTO uaPadre = new UnidadAdministrativaDTO();
            uaPadre.setCodigo(padre.getCodigo());
            Literal nombre = new Literal();
            if (padre.getTraducciones() != null) {
                for (JUnidadAdministrativaTraduccion trad : padre.getTraducciones()) {
                    nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
                }
            }
            uaPadre.setNombre(nombre);
            ua.setPadre(uaPadre);
        }
        Literal nombre = new Literal();
        Literal url = new Literal();
        Literal presentacion = new Literal();
        Literal responsableCV = new Literal();
        Literal abreviatura = new Literal();
        if (this.getTraducciones() != null) {
            for (JUnidadAdministrativaTraduccion trad : this.getTraducciones()) {
                nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
                url.add(new Traduccion(trad.getIdioma(), trad.getUrl()));
                presentacion.add(new Traduccion(trad.getIdioma(), trad.getPresentacion()));
                responsableCV.add(new Traduccion(trad.getIdioma(), trad.getResponsableCV()));
                abreviatura.add(new Traduccion(trad.getIdioma(), trad.getAbreviatura()));
            }
        }
        ua.setNombre(nombre);
        ua.setUrl(url);
        ua.setPresentacion(presentacion);
        ua.setResponsable(responsableCV);
        ua.setAbreviatura(abreviatura);

        return ua;
    }
}
