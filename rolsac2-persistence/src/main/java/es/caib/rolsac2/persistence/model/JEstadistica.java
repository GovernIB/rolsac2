package es.caib.rolsac2.persistence.model;


import jdk.jfr.Name;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "estadistica-sequence", sequenceName = "RS2_ESTADI_SEQ", allocationSize = 1)
@Table(name = "RS2_ESTADI", indexes = {@Index(name = "RS2_ESTADI_PK_I", columnList = "EST_CODI")})
@NamedQueries({
        @NamedQuery(name=JEstadistica.FIND_BY_ID, query = "select p from JEstadistica p where p.codigo = :id"),
        @NamedQuery(name=JEstadistica.FIND_BY_UK_PROC, query = "SELECT j FROM JEstadistica j WHERE j.procedimiento.codigo = :codigo AND j.identificadorApp = :idApp AND j.tipo = :tipo"),
        @NamedQuery(name=JEstadistica.COUNT_BY_UK_PROC, query = "SELECT COUNT(j) FROM JEstadistica j WHERE j.procedimiento.codigo = :codigo AND j.identificadorApp = :idApp AND j.tipo = :tipo"),
        @NamedQuery(name=JEstadistica.FIND_BY_UK_UA, query = "SELECT j FROM JEstadistica j WHERE j.unidadAdministrativa.codigo = :codigo AND j.identificadorApp = :idApp AND j.tipo = :tipo"),
        @NamedQuery(name=JEstadistica.COUNT_BY_UK_UA, query = "SELECT COUNT(j) FROM JEstadistica j WHERE j.unidadAdministrativa.codigo = :codigo AND j.identificadorApp = :idApp AND j.tipo = :tipo"),
})
public class JEstadistica extends BaseEntity {
    public static final String FIND_BY_ID = "Estadistica.FIND_BY_ID";
    public static final String FIND_BY_UK_PROC = "Estadistica.FIND_BY_UK_PROC";

    public static final String COUNT_BY_UK_PROC = "Estadistica.COUNT_BY_UK_PROC";

    public static final String FIND_BY_UK_UA = "Estadistica.FIND_BY_UK_UA";

    public static final String COUNT_BY_UK_UA = "Estadistica.COUNT_BY_UK_UA";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estadistica-sequence")
    @Column(name = "EST_CODI", nullable = false)
    private Long codigo;

    @JoinColumn(name = "EST_CODPRO")
    @ManyToOne(fetch = FetchType.LAZY)
    private JProcedimiento procedimiento;

    @JoinColumn(name = "EST_CODUA")
    @ManyToOne(fetch = FetchType.LAZY)
    private JUnidadAdministrativa unidadAdministrativa;

    @Column(name = "EST_TIPO", nullable = false, length = 2)
    private String tipo;

    @Column(name = "EST_APP", nullable = false, length = 20)
    private String identificadorApp;

    @OneToMany(mappedBy = "codEstadistica", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<JEstadisticaAcceso> listadoAccesos;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public JProcedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificadorApp() {
        return identificadorApp;
    }

    public void setIdentificadorApp(String identificadorApp) {
        this.identificadorApp = identificadorApp;
    }

    public List<JEstadisticaAcceso> getListadoAccesos() {
        return listadoAccesos;
    }

    public void setListadoAccesos(List<JEstadisticaAcceso> listadoAccesos) {
        this.listadoAccesos = listadoAccesos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JEstadistica that = (JEstadistica) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JEstadistica{" +
                "codigo=" + codigo +
                '}';
    }
}
