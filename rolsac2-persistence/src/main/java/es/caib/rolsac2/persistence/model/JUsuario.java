package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "usuario-sequence", sequenceName = "RS2_USER_SEQ", allocationSize = 1)
@Table(name = "RS2_USER",
        indexes = {
                @Index(name = "RS2_USER_PK_I", columnList = "USER_CODIGO")
        }
)
public class JUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario-sequence")
    @Column(name = "USER_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "USER_USER", nullable = false, length = 100)
    private String identificador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad userCodenti) {
        this.entidad = userCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String userUser) {
        this.identificador = userUser;
    }

}