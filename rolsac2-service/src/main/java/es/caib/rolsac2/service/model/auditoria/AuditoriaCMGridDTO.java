package es.caib.rolsac2.service.model.auditoria;

public class AuditoriaCMGridDTO extends AuditoriaGridDTO{

    private Long idAuditado;

    private String nombreAuditado;

    private String accion;

    public Long getIdAuditado() {
        return idAuditado;
    }

    public void setIdAuditado(Long idAuditado) {
        this.idAuditado = idAuditado;
    }

    public String getNombreAuditado() {
        return nombreAuditado;
    }

    public void setNombreAuditado(String nombreAuditado) {
        this.nombreAuditado = nombreAuditado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
