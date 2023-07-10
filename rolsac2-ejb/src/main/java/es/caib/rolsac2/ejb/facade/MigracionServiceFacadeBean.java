package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.persistence.model.JUsuarioUnidadAdministrativa;
import es.caib.rolsac2.persistence.repository.MigracionRepository;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
import es.caib.rolsac2.persistence.repository.UsuarioRepository;
import es.caib.rolsac2.service.facade.MigracionServiceFacade;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio que da soporte a la entidad de negocio Peticionstancia.
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(MigracionServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MigracionServiceFacadeBean implements MigracionServiceFacade {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private MigracionRepository migracionRepository;

    @Inject
    private UnidadAdministrativaRepository uaRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String ejecutarMetodo(String metodo, String param1, String param2) {
        return migracionRepository.ejecutarMetodo(metodo, param1, param2);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<BigDecimal> getUAs(Long idEntidad, Long idUARaiz) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("INICI MIGRACIO UAs \n");
        return migracionRepository.getUAs(idEntidad, idUARaiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarUAs(List<BigDecimal> idUAs, Long idEntidad, Long idUARaiz, String usuarios) {
        StringBuilder resultado = new StringBuilder();
        if (idUAs != null) {
            for (BigDecimal idUA : idUAs) {
                String resultadoNormativa = migracionRepository.importarUA(idUA.longValue(), idEntidad, idUARaiz);
                resultado.append(resultadoNormativa);

                /** Creamos las relaciones de los usuarios con la UA raiz **/
                if (idUARaiz.compareTo(idUA.longValue()) == 0) {
                    if (usuarios != null && !usuarios.isEmpty()) {
                        JUnidadAdministrativa jua = uaRepository.findById(idUARaiz);
                        for (String usuario : usuarios.split(",")) {
                            if (!usuarioRepository.existeUsuarioUA(Long.valueOf(usuario), idUARaiz)) {
                                JUsuario jusuario = usuarioRepository.findById(Long.valueOf(usuario));
                                JUsuarioUnidadAdministrativa dato = new JUsuarioUnidadAdministrativa();
                                dato.setUsuario(jusuario);
                                dato.setUnidadAdministrativa(jua);
                                if (jusuario != null && jua != null) {
                                    usuarioRepository.anyadirNuevoUsuarioUA(jusuario, jua);
                                }
                            }
                        }
                    }
                }
            }
        }

        return resultado.toString();
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarNormativas(Long idEntidad) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("INICI MIGRACIO NORMATIVES \n");
        List<BigDecimal> idNormativas = migracionRepository.getNormativas(idEntidad);
        if (idNormativas != null) {
            for (BigDecimal idNormativa : idNormativas) {
                String resultadoNormativa = migracionRepository.importarNormativa(idNormativa.longValue(), idEntidad);
                resultado.append(resultadoNormativa);
            }
        }
        resultado.append("FI MIGRACIO NORMATIVES \n");
        return resultado.toString();
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarProcedimientos(Long idEntidad, Long idUARaiz) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("INICI MIGRACIO PROCEDIMENTS \n");
        List<BigDecimal> idProcedimientos = migracionRepository.getProcedimientos(idEntidad, idUARaiz);
        if (idProcedimientos != null) {
            for (BigDecimal idProcedimiento : idProcedimientos) {
                String resultadoProc = migracionRepository.importarProcedimiento(idProcedimiento.longValue(), idEntidad);
                resultado.append(resultadoProc);
            }
        }
        resultado.append("FI MIGRACIO PROCEDIMENTS \n");
        return resultado.toString();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarServicios(Long idEntidad, Long idUARaiz) {
        StringBuilder resultado = new StringBuilder();

        resultado.append("INICI MIGRACIO SERVEIS \n");
        List<BigDecimal> idServicios = migracionRepository.getServicios(idEntidad, idUARaiz);
        if (idServicios != null) {
            for (BigDecimal idServicio : idServicios) {
                String resultadoProc = migracionRepository.importarServicio(idServicio.longValue(), idEntidad);
                resultado.append(resultadoProc);
            }
        }
        resultado.append("FI MIGRACIO SERVEIS \n");
        return resultado.toString();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void ejecutarDesactivarRestricciones() {
        migracionRepository.ejecutarMetodo("DESACTIVAR_RESTRICCIONES");
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void ejecutarActivarRestriccionesSecuencias() {
        migracionRepository.ejecutarMetodo("ACTIVAR_RESTRIC_SEQ");
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void ejecutarRevisarSecuencias() {
        migracionRepository.ejecutarMetodo("REVISAR_SEQ");
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getUnidadAdministrativasRaiz() {
        return migracionRepository.getUnidadAdministrativasRaiz();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarUsuarios() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("INICI MIGRACIO USUARIOS \n");
        List<String> idUsuarios = migracionRepository.getUsuarios();
        if (idUsuarios != null) {
            for (String idUsuario : idUsuarios) {
                String resultadoUser = migracionRepository.importarUsuario(idUsuario);
                resultado.append(resultadoUser);
            }
        }
        resultado.append("FI MIGRACIO USUARIOS \n");
        return resultado.toString();
    }
}

