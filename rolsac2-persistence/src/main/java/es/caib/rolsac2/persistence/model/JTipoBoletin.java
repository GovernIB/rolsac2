package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * La clase J tipo boletin.
 */
@Entity
@SequenceGenerator(name = "tipo-boletin-sequence", sequenceName = "RS2_BOLETI_SEQ", allocationSize = 1)
@Table(name = "RS2_BOLETI",
        indexes = {
                @Index(name = "RS2_BOLETI_PK", columnList = "BOLE_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoBoletin.FIND_BY_ID,
                query = "select p from JTipoBoletin p where p.codigo = :id"),
        @NamedQuery(name = JTipoBoletin.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoBoletin p where p.identificador = :identificador")
})
public class JTipoBoletin extends BaseEntity {

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoBoletin.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoBoletin.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-boletin-sequence")
    @Column(name = "BOLE_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Identificador
     */
    @Column(name = "BOLE_IDENTI")
    private String identificador;

    /**
     * Nombre
     */
    @Column(name = "BOLE_NOMBRE")
    private String nombre;

    /**
     * Nombre
     */
    @Column(name = "BOLE_URL")
    private String url;

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
     * @param id  id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene url.
     *
     * @return  url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url  url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JTipoBoletin that = (JTipoBoletin) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(identificador,
                that.identificador) && Objects.equals(nombre, that.nombre) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, nombre, url);
    }
}
