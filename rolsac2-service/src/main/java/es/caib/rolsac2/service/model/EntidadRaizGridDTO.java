package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "EntidadRaiz")
public class EntidadRaizGridDTO extends ModelApi implements Comparable<EntidadRaizGridDTO>{

    private Long codigo;

    private Literal ua;

    private String user;

    private String pwd;

    public EntidadRaizGridDTO(){}

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Literal getUa() {
        return ua;
    }

    public void setUa(Literal ua) {
        this.ua = ua;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public int compareTo(EntidadRaizGridDTO data) {
        if (data == null){
            return 1;
        }
        if (UtilComparador.compareTo(this.getCodigo(), data.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data.getCodigo());
        }

        if (UtilComparador.compareTo(this.getUa(), data.getUa()) != 0) {
            return UtilComparador.compareTo(this.getUa(), data.getUa());
        }

        if (UtilComparador.compareTo(this.getUser(), data.getUser()) != 0) {
            return UtilComparador.compareTo(this.getUser(), data.getUser());
        }

        if (UtilComparador.compareTo(this.getPwd(), data.getPwd()) != 0) {
            return UtilComparador.compareTo(this.getPwd(), data.getPwd());
        }
        return 0;
    }

    /**
     * Obtiene id string.
     *
     * @return id string
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadRaizGridDTO that = (EntidadRaizGridDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "EntidadRaizGridDTO{" +
                "codigo=" + codigo +
                ", ua='" + ua + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    @Override
    public Object clone() {
        EntidadRaizGridDTO enti = new EntidadRaizGridDTO();
        enti.setCodigo(this.getCodigo());
        enti.setUa(this.getUa());
        enti.setUser(this.user);
        enti.setPwd(this.pwd);
        return enti;
    }
}
