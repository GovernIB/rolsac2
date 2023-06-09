package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.plugins.email.api.AnexoEmail;
import es.caib.rolsac2.commons.plugins.email.api.EmailPluginException;
import es.caib.rolsac2.service.facade.integracion.EmailServiceFacade;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DialogAlertas extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogAlertas.class);

    @EJB
    private EmailServiceFacade emailServiceFacade;

    private String asunto;

    private String mensaje;

    private String destinatario;

    private List<String> destinatarios;

    private List<AnexoEmail> anexos;

    private AnexoEmail anexo;

    private boolean resultado;

    public void load() throws EmailPluginException {
        destinatarios = new ArrayList<>();
//        destinatarios.add(destinatario);
        anexos = new ArrayList<>();
        /*anexo = new AnexoEmail();
        anexo.setFileName("fichero");
        anexo.setContentType("");
        anexos.add(anexo);*/

        asunto = "asunto";
        mensaje = "mensaje";

//        envioEmail();
    }

    public void envioEmail() throws EmailPluginException {
        destinatarios.add(destinatario);
        resultado = emailServiceFacade.envioEmail(destinatarios, asunto, mensaje, anexos, sessionBean.getEntidad().getCodigo());
        System.out.println("Resultado envio email: " + resultado);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Mensaje envidado correctamente");
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<String> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public List<AnexoEmail> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<AnexoEmail> anexos) {
        this.anexos = anexos;
    }

    public AnexoEmail getAnexo() {
        return anexo;
    }

    public void setAnexo(AnexoEmail anexo) {
        this.anexo = anexo;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}
