package es.caib.rolsac2.back.security;

import es.caib.rolsac2.commons.utils.Version;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

/**
 * Bean CDI per obtenir instàncies de UserInfo
 * @author areus
 */
@ApplicationScoped
public class UserInfoProducer {

    UserInfoFactory factory;

    /**
     * Si keycloak està present al sistema emprar factory de keycloak, sinó un factory per defecte.
     */
    @PostConstruct
    protected void init() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("rolsac2.version.Version");
            String authMethod = bundle.getString("authMethod");
            if(authMethod.equals("KEYCLOAK")) {
                Class.forName("org.keycloak.KeycloakPrincipal");
                factory = new KeycloakUserInfoFactory();
            } else {
                factory = new DefaultUserInfoFactory();
            }
        } catch (ClassNotFoundException e) {
            factory = new DefaultUserInfoFactory();
        }
    }

    /**
     * Produim el bean amb SessionScoped perquè només ho faci un pic per sessió.
     * Si la informació d'usuari pogués variar durant la sessió bastaria posar-li RequestScoped.
     */
    @Produces
    @SessionScoped
    @Named("userInfo")
    public UserInfo produceUserInfo(HttpServletRequest request) {
        return factory.createUserInfo(request);
    }
}
