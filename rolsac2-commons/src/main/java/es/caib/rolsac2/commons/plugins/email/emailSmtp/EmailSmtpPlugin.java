package es.caib.rolsac2.commons.plugins.email.emailSmtp;

import es.caib.rolsac2.commons.plugins.email.api.AnexoEmail;
import es.caib.rolsac2.commons.plugins.email.api.EmailPlugin;
import es.caib.rolsac2.commons.plugins.email.api.EmailPluginException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.InitialContext;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailSmtpPlugin extends AbstractPluginProperties implements EmailPlugin {

    /**
     * Encoding utilizado en la generaciÃ³n de XML
     */
    public static final String ENCODING = "UTF-8";
    private static final Logger LOG = LoggerFactory.getLogger(EmailSmtpPlugin.class);

    /**
     * Prefix.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "mail.";

    public static final String EMAIL_JNDI = "jndi";
    public static final String USUARIO_EMAIL = "user";
    public static final String PWD_EMAIL = "pwd";
    public static final String PORT_EMAIL = "port";
    public static final String HOST_EMAIL = "host";

    /**
     * Constructor.
     **/
    public EmailSmtpPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    public boolean envioEmail(final List<String> destinatarios, final String asunto, final String mensaje,
                              final List<AnexoEmail> anexos, String idioma) throws EmailPluginException {

        try {
            final InitialContext jndiContext = new InitialContext();
            final Session mailSession;
            if (getProperty(EMAIL_JNDI) == null) {

                /** para configurarlo a travÃ©s de parÃ¡metros de entrada */
                // ConfiguraciÃ³n de propiedades
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", getPropiedad(HOST_EMAIL));
                props.put("mail.smtp.port", getPropiedad(PORT_EMAIL));
                String user = getPropiedad(USUARIO_EMAIL);
                String pwd = getPropiedad(PWD_EMAIL);
                // CreaciÃ³n de la sesiÃ³n
                mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(user, pwd);
                    }
                });
            } else {
                mailSession = (Session) jndiContext.lookup(getProperty(EMAIL_JNDI));
            }
            //final Session mailSession = (Session) jndiContext.lookup("java:/es.caib.rolsac2.mail");
            final MimeMessage msg = new MimeMessage(mailSession);

            final InternetAddress[] direcciones = new InternetAddress[destinatarios.size()];
            for (int i = 0; i < destinatarios.size(); i++) {
                final InternetAddress direccion = new InternetAddress();
                direccion.setAddress(destinatarios.get(i));
                direcciones[i] = direccion;
            }
            msg.setRecipients(javax.mail.Message.RecipientType.TO, direcciones);

            msg.setSubject(asunto);

            String contenido;
            if (isHtml(mensaje)) {
                contenido = new String(mensaje.getBytes(), ENCODING);
            } else {
                contenido = StringEscapeUtils.escapeHtml4(new String(mensaje.getBytes(), ENCODING));
            }

            msg.setHeader("X-Mailer", "JavaMailer");
            String mailFrom = null;
            if (mailSession.getProperty("mail.from") != null) {
                mailFrom = mailSession.getProperty("mail.from");
            } else if (mailSession.getProperty("mail.smtp.user") != null) {
                mailFrom = mailSession.getProperty("mail.smtp.user");
            } else {
                throw new EmailPluginException("Error, mail from no especificado");
            }
            msg.setFrom(new InternetAddress(mailFrom));

            if (anexos != null && !anexos.isEmpty()) {
                // Envio con anexos
                final Multipart multipart = new MimeMultipart("mixed");
                // Mensaje
                final MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(contenido, "text/html; charset=utf-8");
                multipart.addBodyPart(textPart);
                // Anexos
                for (final AnexoEmail a : anexos) {
                    final DataSource source = new ByteArrayDataSource(a.getContent(), a.getContentType());
                    final MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(a.getFileName());
                    multipart.addBodyPart(messageBodyPart);
                }
                // Mensaje + Anexos
                msg.setContent(multipart);
            } else {
                // Envio sin anexos
                msg.setContent(contenido, "text/html; charset=utf-8");
            }

            Transport.send(msg);

        } catch (final MessagingException e) {
            LOG.error("Error enviando. MSG:" + ExceptionUtils.getMessage(e), e);
            String error = interpretarError(idioma, e);
            throw new EmailPluginException(error, e);
        } catch (final Exception e) {
            LOG.error("Error enviando. MSG:" + ExceptionUtils.getMessage(e), e);
            throw new EmailPluginException("Error enviando. MSG:" + ExceptionUtils.getMessage(e), e);
        }
        return true;

    }

    public String interpretarError(String idioma, MessagingException e) {
        // Primero, obtenemos el mensaje principal
        String mensaje = e.getMessage();
        // Si no contiene información, buscamos en la excepción encadenada
        if (mensaje == null || mensaje.isEmpty()) {
            Exception next = e.getNextException();
            if (next != null) {
                mensaje = next.getMessage();
            }
        }

        if (mensaje == null || mensaje.isEmpty()) {
            return idioma.equals("ca") ? "Error desconegut a l’enviar el correu."
                    : "Error desconocido al enviar el correo.";
        }

        // Convertir a minúsculas para simplificar las comparaciones
        mensaje = mensaje.toLowerCase();

        // Interpretamos el mensaje para obtener una descripción más clara
        if (mensaje.contains("connection timed out")) {
            return idioma.equals("ca") ? "No es pot connectar amb el servidor SMTP: s'ha esgotat el temps de connexió."
                    : "No se puede conectar con el servidor SMTP: se agotó el tiempo de conexión.";
        } else if (mensaje.contains("authentication failed")) {
            return idioma.equals("ca") ? "Error d'autenticació: verifiqueu que l'usuari i la contrasenya siguin correctes."
                    : "Error de autenticación: verifique el usuario y la contraseña.";
        } else if (mensaje.contains("unknown smtp host")) {
            return idioma.equals("ca") ? "El servidor SMTP no es coneix o no es pot resoldre."
                    : "El servidor SMTP no es conocido o no se puede resolver.";
        } else if (mensaje.contains("could not convert socket")) {
            return idioma.equals("ca") ? "Error en establir la connexió: problema amb el protocol de xarxa."
                    : "Error al establecer la conexión: problema con el protocolo de red.";
        } else if (mensaje.contains("could not connect to smtp host")) {
            return idioma.equals("ca") ? "No es pot connectar amb el servidor SMTP: verifiqueu l'adreça i el port."
                    : "No se puede conectar con el servidor SMTP: verifique la dirección y el puerto.";
        } else if (mensaje.contains("invalid addresses")) {
            return idioma.equals("ca") ? "Error en les adreces de correu: verifiqueu els destinataris."
                    : "Error en las direcciones de correo: verifique los destinatarios.";
        } else if (mensaje.contains("connect to host")) {
            return idioma.equals("ca") ? "Error en la configuració del servidor SMTP."
                    : "Error en la configuración del servidor SMTP.";
        } else if (mensaje.contains("connection refused")) {
            return idioma.equals("ca") ? "La connexió ha estat rebutjada pel servidor."
                    : "La conexión fue rechazada por el servidor.";
        } else if (mensaje.contains("starttls")) {
            return idioma.equals("ca") ? "Error amb el protocol STARTTLS: verifiqueu la configuració de seguretat."
                    : "Error con el protocolo STARTTLS: verifique la configuración de seguridad.";
        } else if (mensaje.contains("could not open connection")) {
            return idioma.equals("ca") ? "No s'ha pogut obrir la connexió amb el servidor."
                    : "No se pudo abrir la conexión con el servidor.";
        }

        // Si no se reconoce el mensaje, se devuelve el mensaje original
        return idioma.equals("ca") ? "Error en enviar el correu: " + mensaje
                : "Error al enviar el correo: " + mensaje;
    }


    private static final String HTML_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    private final Pattern pattern = Pattern.compile(HTML_PATTERN);

    public boolean isHtml(final String text) {
        final Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    /**
     * Obtiene propiedad.
     *
     * @param propiedad propiedad
     * @return valor
     * @throws EmailPluginException
     */
    private String getPropiedad(final String propiedad) throws EmailPluginException {
        final String res = getProperty(EMAIL_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
        if (res == null) {
            throw new EmailPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
        }
        return res;
    }

}

