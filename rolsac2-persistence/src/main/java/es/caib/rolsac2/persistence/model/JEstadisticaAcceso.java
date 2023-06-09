package es.caib.rolsac2.persistence.model;


import jdk.jfr.Name;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "estadistica-accesos-sequence", sequenceName = "RS2_ESTACC_SEQ", allocationSize = 1)
@Table(name = "RS2_ESTACC", indexes = {@Index(name = "RS2_ESTACC_PK_I", columnList = "EACC_CODI")})
public class JEstadisticaAcceso extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estadistica-accesos-sequence")
    @Column(name = "EACC_CODI", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EACC_CODEST")
    private JEstadistica codEstadistica;

    @Column(name = "EACC_CONT")
    private Long contador;

    @Column(name = "EACC_FECHA")
    private Date fechaAcceso;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public JEstadistica getCodEstadistica() {
        return codEstadistica;
    }

    public void setCodEstadistica(JEstadistica codEstadistica) {
        this.codEstadistica = codEstadistica;
    }

    public Long getContador() {
        return contador;
    }

    public void setContador(Long contador) {
        this.contador = contador;
    }

    public Date getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(Date fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JEstadisticaAcceso that = (JEstadisticaAcceso) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public String toString() {
        return "JEstadistica{" +
                "codigo=" + codigo +
                '}';
    }
}
