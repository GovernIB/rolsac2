package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.persistence.model.JUsuarioUnidadAdministrativa;
import es.caib.rolsac2.persistence.repository.FicheroExternoRepository;
import es.caib.rolsac2.persistence.repository.MigracionRepository;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
import es.caib.rolsac2.persistence.repository.UsuarioRepository;
import es.caib.rolsac2.service.facade.MigracionServiceFacade;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.migracion.FicheroInfo;
import es.caib.rolsac2.service.model.migracion.FicheroRolsac1;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * log.
     */
    private static final Logger log = LoggerFactory.getLogger(MigracionServiceFacadeBean.class);

    @Inject
    MigracionRepository migracionRepository;

    @Inject
    FicheroExternoRepository ficheroRepository;

    @Inject
    UnidadAdministrativaRepository uaRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String ejecutarMetodo(String metodo, String param1, String param2) {
        return migracionRepository.ejecutarMetodo(metodo, param1, param2);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<BigDecimal> getUAs(Long idEntidad, Long idUARaiz) {
        return migracionRepository.getUAs(idEntidad, idUARaiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarUAs(List<BigDecimal> idUAs, Long idEntidad, Long idUARaiz, String usuarios) {
        StringBuilder resultado = new StringBuilder();
        if (idUAs != null) {
            for (BigDecimal idUA : idUAs) {
                String resultadoUA = migracionRepository.importarUA(idUA.longValue(), idEntidad, idUARaiz);
                resultado.append(resultadoUA);

                // Creamos las relaciones de los usuarios con la UA raiz
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
    public List<BigDecimal> getNormativas(Long idEntidad) {
        return migracionRepository.getNormativas(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarNormativas(List<BigDecimal> idNormativas, Long idEntidad) {
        StringBuilder resultado = new StringBuilder();
        if (idNormativas != null) {
            for (BigDecimal idNormativa : idNormativas) {
                String resultadoNormativa = migracionRepository.importarNormativa(idNormativa.longValue(), idEntidad);
                resultado.append(resultadoNormativa);
            }
        }
        return resultado.toString();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarNormativasAfe() {
        return migracionRepository.importarNormativasAfectaciones();
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<BigDecimal> getProcedimientos(Long idEntidad, Long idUARaiz) {
        return migracionRepository.getProcedimientos(idEntidad, idUARaiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarProcedimientos(List<BigDecimal> idProcedimientos, Long idEntidad, Long idUARaiz) {
        StringBuilder resultado = new StringBuilder();
        if (idProcedimientos != null) {
            for (BigDecimal idProcedimiento : idProcedimientos) {
                String resultadoProc = migracionRepository.importarProcedimiento(idProcedimiento.longValue(), idEntidad);
                resultado.append(resultadoProc);
            }
        }
        return resultado.toString();
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<BigDecimal> getServicios(Long idEntidad, Long idUARaiz) {
        return migracionRepository.getServicios(idEntidad, idUARaiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarServicios(List<BigDecimal> idServicios, Long idEntidad, Long idUARaiz) {
        StringBuilder resultado = new StringBuilder();
        if (idServicios != null) {
            for (BigDecimal idServicio : idServicios) {
                String resultadoProc = migracionRepository.importarServicio(idServicio.longValue(), idEntidad);
                resultado.append(resultadoProc);
            }
        }
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

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String desactivarRestriccionDocumento() {
        return migracionRepository.desactivarRestriccionDocumento();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String activarRestriccionDocumento() {
        return migracionRepository.activarRestriccionDocumento();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<FicheroInfo> getDocumentosProcedimientos(Long idEntidad, Long uaRaiz) {
        return migracionRepository.getDocumentos(idEntidad, uaRaiz, true, false);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<FicheroInfo> getDocumentosNormativas(Long idEntidad, Long uaRaiz) {
        return migracionRepository.getDocumentos(idEntidad, uaRaiz, false, true);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String migrarDocumentos(FicheroInfo infoDoc, Long entidad, Long uaRaiz, String pathAlmacenamientoRolsac1, String pathAlmacenamiento, TypeFicheroExterno tipoficheroExterno) {
        StringBuilder resultado = new StringBuilder();
        try {
            FicheroRolsac1 ficheroRolsac1 = ficheroRepository.getFicheroRolsac(infoDoc.getCodigoFicheroRolsac1(), pathAlmacenamientoRolsac1);
            if (ficheroRolsac1.getFilename() == null || ficheroRolsac1.getFilename().isEmpty()) {
                return "\tFichero de rolsac1 " + infoDoc.getCodigoFicheroRolsac1() + " NO se migrado porque no tiene filename correcto.\n";
            }
            if (ficheroRolsac1.getContenido() == null) {
                return "\tFichero de rolsac1 " + infoDoc.getCodigoFicheroRolsac1() + " NO se migrado porque no tiene contenido.\n";
            }
            Long idPadre;
            if (tipoficheroExterno == TypeFicheroExterno.PROCEDIMIENTO_DOCUMENTOS) {
                idPadre = migracionRepository.getProcedimiento(infoDoc.getCodigoDocumentoTraduccion());
            } else {
                idPadre = migracionRepository.getNormativa(infoDoc.getCodigoDocumentoTraduccion());
            }
            if (idPadre == null) {
                return "\tFichero de rolsac1 " + infoDoc.getCodigoFicheroRolsac1() + " NO se ha encontrado el contenido padre.\n";
            }
            infoDoc.setCodigoPadre(idPadre);
            Long idFichero = ficheroRepository.createFicheroExternoMigracion(ficheroRolsac1.getContenido(), ficheroRolsac1.getFilename(), tipoficheroExterno, idPadre, pathAlmacenamiento, ficheroRolsac1.getCodigo());
            migracionRepository.migrarArchivo(idFichero, infoDoc.getCodigoDocumentoTraduccion(), tipoficheroExterno);
            resultado.append("\tFichero de rolsac1 ");
            resultado.append(infoDoc.getCodigoFicheroRolsac1());
            resultado.append(" migrado correctamente \n");
        } catch (Exception e) {
            log.error("Error migrando fichero " + infoDoc, e);
            resultado.append("\tFichero de rolsac1 ");
            resultado.append(infoDoc.getCodigoFicheroRolsac1());
            resultado.append(" ha dado un error. Error:");
            resultado.append(e.getLocalizedMessage());
            resultado.append(" \n");
        }
        return resultado.toString();
    }
}

