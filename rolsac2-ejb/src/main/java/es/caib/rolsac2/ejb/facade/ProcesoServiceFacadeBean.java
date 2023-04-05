package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.repository.IndexacionRepository;
import es.caib.rolsac2.persistence.repository.IndexacionSIARepository;
import es.caib.rolsac2.persistence.repository.ProcesoRepository;
import es.caib.rolsac2.service.exception.ProcesoNoExistenteException;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que da soporte a la entidad de negocio Peticionstancia.
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(ProcesoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcesoServiceFacadeBean implements ProcesoServiceFacade {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private ProcesoRepository procesoRepository;

    @Inject
    private IndexacionRepository procedimientoRepository;

    @Inject
    private IndexacionRepository indexacionRepository;

    @Inject
    private IndexacionSIARepository indexacionSIARepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ProcesoDTO obtenerProcesoPorCodigo(final Long codigo) {
        final ProcesoDTO proceso = this.procesoRepository.obtenerProcesoPorCodigo(codigo);
        if (proceso == null) {
            throw new ProcesoNoExistenteException(codigo);
        }
        return proceso;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void borrar(final Long idPeticion) {
        this.procesoRepository.borrar(idPeticion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void guardar(final ProcesoDTO proceso) {
        this.procesoRepository.guardar(proceso);
    }

    /**
     * Lista Peticion.
     **/
    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcesoGridDTO> listar(final ProcesoFiltro filtro) {
        List<ProcesoGridDTO> listaProcesos = this.procesoRepository.listar(filtro);
        return listaProcesos;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcesoGridDTO> listar(final String idioma, final String tipo) {
        return this.procesoRepository.listar(idioma, tipo);
    }

    /**
     * Total Lista Peticion.
     **/
    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Integer listarTotal(final ProcesoFiltro filtro) {
        return this.procesoRepository.listarTotal(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<ProcesoGridDTO> findByFiltro(ProcesoFiltro filtro) {
        try {
            List<ProcesoGridDTO> items = this.listar(filtro);
            long total = this.listarTotal(filtro).longValue();
            return new Pagina<>(items, total);
        } catch (Exception e) {
            List<ProcesoGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<IndexacionDTO> findSolrByFiltro(ProcesoSolrFiltro filtro) {
        try {
            List<IndexacionDTO> items = indexacionRepository.findPagedByFiltro(filtro);
            long total = indexacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            List<IndexacionDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    public Pagina<IndexacionSIADTO> findSIAByFiltro(ProcesoSIAFiltro filtro) {
        try {
            List<IndexacionSIADTO> items = indexacionSIARepository.findPagedByFiltro(filtro);
            long total = indexacionSIARepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            List<IndexacionSIADTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

}
