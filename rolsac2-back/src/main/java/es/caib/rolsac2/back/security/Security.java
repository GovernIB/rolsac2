package es.caib.rolsac2.back.security;

import es.caib.rolsac2.commons.utils.Constants;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Proveeix una forma centralitzada de comprovar els permisos de l'usuari dins l'aplicació web.
 *
 * @author Indra
 */
@Named
@ApplicationScoped
public class Security {

    @Inject
    private HttpServletRequest request;

    @EJB
    private AdministracionSupServiceFacade supServiceFacade;


    public List<TypePerfiles> getPerfiles() {
        List<TypePerfiles> perfiles = new ArrayList<TypePerfiles>();
        for (TypePerfiles perfil : TypePerfiles.values()) {
            if (request.isUserInRole(perfil.toString())) {
                perfiles.add(perfil);
            }
        }
        return perfiles;
    }

    public List<String> getRoles(List<Long> idEntidades) {
        List<String> roles = new ArrayList<>();
        List<String> rolesEntidades = supServiceFacade.findRolesDefinidos(idEntidades);
        if (!rolesEntidades.isEmpty()) {
            for (String rol : rolesEntidades) {
                if (request.isUserInRole(rol) && !roles.contains(rol)) {
                    roles.add(rol);
                }
            }
        }
        return roles;
    }

    public String getIdentificadorUsuario() {
        return request.getRemoteUser();
    }

    public boolean isAdmin() {
        return request.isUserInRole(Constants.RSC_ADMIN);
    }

    public boolean isUser() {
        return request.isUserInRole(Constants.RSC_USER);
    }

    public boolean isUserOrAdmin() {
        return isUser() || isAdmin();
    }

}
