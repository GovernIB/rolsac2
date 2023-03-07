package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@XmlRootElement
@Schema(name = "Login", description = "Objeto de login")
public class Login {
    private String user;
    private String pass;

    @Schema(name = "user", required = true)
    public String getUser() {
        return user;
    }

    @Schema(name = "pass", required = true)
    public String getPass() {
        return pass;
    }
}