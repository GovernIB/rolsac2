package es.caib.rolsac2.api.interna.v1.model;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Dades contacte.
 *
 * @author indra
 */
@XmlRootElement
@Schema(name = "DatosContacto", description = "Datos de contacto para la gestión de incidencias")
public class DatosContacto {

    @Schema(description = "Servicio o sección responsable", type = SchemaType.STRING, required = false)
    private String servicioResponsable;

    @Schema(description = "Nombre de la persona a cargo", type = SchemaType.STRING, required = false)
    private String personaACargo;

    @Schema(description = "Dirección de correo electrónico para gestionar incidencias", type = SchemaType.STRING, required = false)
    private String emailIncidencias;

    public DatosContacto() {
    }

    public DatosContacto(String servicioResponsable, String personaACargo, String emailIncidencias) {
        this.servicioResponsable = servicioResponsable;
        this.personaACargo = personaACargo;
        this.emailIncidencias = emailIncidencias;
    }

    public String getServicioResponsable() {
        return servicioResponsable;
    }

    public void setServicioResponsable(String servicioResponsable) {
        this.servicioResponsable = servicioResponsable;
    }

    public String getPersonaACargo() {
        return personaACargo;
    }

    public void setPersonaACargo(String personaACargo) {
        this.personaACargo = personaACargo;
    }

    public String getEmailIncidencias() {
        return emailIncidencias;
    }

    public void setEmailIncidencias(String emailIncidencias) {
        this.emailIncidencias = emailIncidencias;
    }
}