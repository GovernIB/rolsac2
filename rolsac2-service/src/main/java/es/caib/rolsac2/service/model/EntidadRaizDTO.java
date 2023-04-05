package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "EntidadRaiz")
public class EntidadRaizDTO extends ModelApi {

    private Long codigo;
    private UnidadAdministrativaDTO ua;
    private String user;
    private String pwd;


    public EntidadRaizDTO() {
    }

    public EntidadRaizDTO(EntidadRaizDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.ua = otro.ua == null ? null : (UnidadAdministrativaDTO) otro.ua.clone();
            this.user = otro.user;
            this.pwd = otro.pwd;
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public UnidadAdministrativaDTO getUa() {
        return ua;
    }

    public void setUa(UnidadAdministrativaDTO ua) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadRaizDTO that = (EntidadRaizDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public EntidadRaizDTO clone() {
        return new EntidadRaizDTO(this);
    }
}
