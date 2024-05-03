package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.repository.ProcesoLogRepository;
import es.caib.rolsac2.persistence.repository.ProcesoRepository;
import es.caib.rolsac2.service.facade.ProcesoLogServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio que da soporte a la entidad de negocio Peticionstancia.
 *
 * @author Indra
 */

@Logged
@ExceptionTranslate
@Stateless
@Local(ProcesoLogServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcesoLogServiceFacadeBean implements ProcesoLogServiceFacade {


  @Inject
  ProcesoLogRepository procesoLogRepository;

  @Inject
  ProcesoRepository procesoRepository;

  @Override
  @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
  public ProcesoLogDTO obtenerProcesoLogPorCodigo(final Long codigo) {
    return this.procesoLogRepository.obtenerProcesoLogPorCodigo(codigo);
  }

  /**
   * Lista Peticion.
   **/
  @Override
  @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
  public List<ProcesoLogGridDTO> listar(final ProcesoLogFiltro filtro) {
    return this.procesoLogRepository.listar(filtro);
  }

  @Override
  @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
  public List<ProcesoLogGridDTO> listar(final String idioma, final String tipo) {
    return this.procesoLogRepository.listar(idioma, tipo);
  }

  /**
   * Total Lista ProcesoLog.
   **/
  @Override
  @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
  public Integer listarTotal(final ProcesoLogFiltro filtro) {
    return this.procesoLogRepository.listarTotal(filtro);
  }

  @Override
  @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
  public List<ProcesoGridDTO> listarProceso(final ProcesoFiltro filtro) {
    return procesoRepository.listar(filtro);
  }

  @Override
  @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
  public Pagina<ProcesoLogGridDTO> findByFiltro(ProcesoLogFiltro filtro) {
    try {
      List<ProcesoLogGridDTO> items = this.listar(filtro);
      long total = this.listarTotal(filtro).longValue();
      return new Pagina<>(items, total);
    } catch (Exception e) {
      List<ProcesoLogGridDTO> items = new ArrayList<>();
      return new Pagina<>(items, 0L);
    }
  }

  @Override
  @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
  public Date obtenerFechaUltimaEjecucionCorrecta(final String idProceso, final Long idEntidad) {
    ProcesoDTO proceso = procesoRepository.obtenerProcesoPorIdentificador(idProceso, idEntidad);
    return procesoLogRepository.obtenerUltimaEjecucionCorrecta(proceso.getCodigo());
  }

}
