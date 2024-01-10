package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TemaConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.TemaRepository;
import es.caib.rolsac2.persistence.repository.TipoMateriaSIARepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TemaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.filtro.TemaFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Logged
@ExceptionTranslate
@Stateless
@Local(TemaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TemaServiceFacadeBean implements TemaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TemaServiceFacadeBean.class);
    private static final String ERROR_LITERAL = "Error";

    @Resource
    private SessionContext context;

    @Inject
    private TemaRepository temaRepository;

    @Inject
    private TipoMateriaSIARepository tipoMateriaSIARepository;

    @Inject
    private TemaConverter converter;

    @Inject
    private EntidadRepository entidadRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TemaDTO> getHijos(Long id, String idioma) {
        return converter.createTreeDTOs(temaRepository.getHijos(id, idioma));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TemaGridDTO> getGridHijos(Long id, String idioma) {
        return converter.createGridDTOs(temaRepository.getHijos(id, idioma));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TemaDTO> getRoot(String idioma, Long entidadId) {
        return converter.createDTOs(temaRepository.getRoot(idioma, entidadId));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TemaGridDTO> getGridRoot(String idioma, Long entidadId) {
        return converter.createGridDTOs(temaRepository.getRoot(idioma, entidadId));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TemaDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JTema jTema = converter.createEntity(dto);
        String path = "";
        //Calculamos path con el que se guardará el objeto
        if (dto.getTemaPadre() != null && dto.getTemaPadre().getCodigo() != null) {
            if (dto.getTemaPadre().getMathPath() != null && !dto.getTemaPadre().getMathPath().isEmpty()) {
                path += dto.getTemaPadre().getMathPath();
                path += ";" + dto.getTemaPadre().getCodigo();
            } else {
                path = dto.getTemaPadre().getCodigo().toString();
            }

        }
        jTema.setMathPath(path);
        temaRepository.create(jTema);
        return jTema.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TemaDTO dto, String idioma) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JTema jTema = temaRepository.getReference(dto.getCodigo());

        this.verificarModificacionTemaPadre(dto, jTema, idioma);
        jTema.setEntidad(jEntidad);
        converter.mergeEntity(jTema, dto);
        temaRepository.update(jTema);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTema jTema = temaRepository.getReference(id);
        temaRepository.delete(jTema);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TemaDTO findById(Long id) {
        return converter.createDTO(temaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TemaGridDTO findGridById(Long id) {
        return converter.createGridDTO(temaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TemaGridDTO> findByFiltro(TemaFiltro filtro) {
        try {
            List<TemaGridDTO> items = temaRepository.findPageByFiltro(filtro);
            long total = temaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<TemaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(TemaFiltro filtro) {
        return (int) temaRepository.countByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long getCountHijos(Long parentId) {
        return temaRepository.getCountHijos(parentId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador, Long idEntidad) {
        return temaRepository.checkIdentificador(identificador, idEntidad);
    }


    private void verificarModificacionTemaPadre(TemaDTO temaActualizado, JTema jTema, String idioma) {
        if (temaActualizado.getTemaPadre() != null && jTema.getTemaPadre() != null) {
            if (jTema.getTemaPadre().getCodigo() != temaActualizado.getTemaPadre().getCodigo()) {
                String mathPathAntiguo = jTema.getMathPath();
                String mathPathNuevo = temaActualizado.getMathPath();
                List<JTema> hijosAll = temaRepository.getHijosTodosNiveles(mathPathAntiguo + jTema.getCodigo().toString(), idioma);
                for (JTema tema : hijosAll) {
                    String mathPath = tema.getMathPath();
                    String mathPathActualizado = mathPath.replace(mathPathAntiguo, mathPathNuevo);
                    tema.setMathPath(mathPathActualizado);
                    temaRepository.update(tema);
                }
                JTema temaPadre = temaRepository.getReference(temaActualizado.getTemaPadre().getCodigo());
                jTema.setTemaPadre(temaPadre);
                String mathPathPadre = temaPadre.getMathPath();
                if (mathPathPadre != null) {
                    String mathPath = mathPathPadre += temaPadre.getCodigo().toString();
                    jTema.setMathPath(mathPath);
                } else {
                    jTema.setMathPath(temaPadre.getCodigo().toString());
                }
            }
        } else if (temaActualizado.getTemaPadre() == null && jTema.getTemaPadre() != null) {
            String mathPathAntiguo = jTema.getMathPath();
            List<JTema> hijosAll = temaRepository.getHijosTodosNiveles(mathPathAntiguo, idioma);
            for (JTema tema : hijosAll) {
                String mathPath = tema.getMathPath();
                String mathPathActualizado = mathPath.replace(mathPathAntiguo, "");
                tema.setMathPath(mathPathActualizado);
                temaRepository.update(tema);
            }
            jTema.setTemaPadre(null);
            jTema.setMathPath(null);
        } else if (temaActualizado.getTemaPadre() != null && jTema.getTemaPadre() == null) {
            String mathPathActualizado = temaActualizado.getMathPath();
            List<JTema> hijosAll = temaRepository.getHijosTodosNiveles(temaActualizado.getCodigo().toString(), idioma);
            for (JTema tema : hijosAll) {
                String mathPath = temaActualizado.getMathPath() + tema.getMathPath();
                tema.setMathPath(mathPath);
                temaRepository.update(tema);
            }
            JTema temaPadre = temaRepository.getReference(temaActualizado.getTemaPadre().getCodigo());
            jTema.setTemaPadre(temaPadre);
            jTema.setMathPath(temaActualizado.getMathPath());
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TemaDTO> findByFiltroRest(TemaFiltro filtro) {
        try {
            List<TemaDTO> items = temaRepository.findPagedByFiltroRest(filtro);
            long total = temaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TemaDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoMateriaSIADTO> getTipoMateriasSIA(String idioma) {
        TipoMateriaSIAFiltro filtro = new TipoMateriaSIAFiltro();
        filtro.setIdioma(idioma);
        return tipoMateriaSIARepository.getListTipoMateriaSIADTO(filtro);
    }
}
