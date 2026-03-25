package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TemaConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.TemaRepository;
import es.caib.rolsac2.persistence.repository.TipoMateriaSIARepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.exception.TemaNivelMaximoException;
import es.caib.rolsac2.service.facade.TemaServiceFacade;
import es.caib.rolsac2.service.model.Constantes;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.filtro.TemaFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Logged
@ExceptionTranslate
@Stateless
@Local(TemaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TemaServiceFacadeBean implements TemaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TemaServiceFacadeBean.class);
    private static final String ERROR_LITERAL = "Error";
    private static final int DEFAULT_NIVEL_TEMAS_MAXIMO = 3;

    @Inject
    TemaRepository temaRepository;

    @Inject
    TipoMateriaSIARepository tipoMateriaSIARepository;

    @Inject
    TemaConverter converter;

    @Inject
    EntidadRepository entidadRepository;


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
        validarNivelTemasMaximo(dto, null, null);
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

        JTipoMateriaSIA jTipoMateriaSIA = null;
        if (dto.getTipoMateriaSIA() != null) {
            jTipoMateriaSIA = tipoMateriaSIARepository.findById(dto.getTipoMateriaSIA().getCodigo());
        }
        validarNivelTemasMaximo(dto, jTema, idioma);
        this.verificarModificacionTemaPadre(dto, jTema, idioma);
        jTema.setEntidad(jEntidad);

        String mathPath = "";
        if (dto.getTemaPadre() != null && dto.getTemaPadre().getCodigo() != null) {
            JTema jTemaPadre = temaRepository.getReference(dto.getTemaPadre().getCodigo());
            if (jTemaPadre.getMathPath() != null && !jTemaPadre.getMathPath().isEmpty()) {
                mathPath += jTemaPadre.getMathPath();
                mathPath += ";" + jTemaPadre.getCodigo();
                ;
            }
        } else {
            dto.setMathPath(null);
        }
        converter.mergeEntity(jTema, dto);
        //temaRepository.update(jTema);
        temaRepository.actualizar(jTema, jTipoMateriaSIA);
        temaRepository.actualizarMathPath(jTema.getCodigo(), dto.getMathPath());
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int delete(Long id) throws RecursoNoEncontradoException {

        boolean usadoEnProcedimiento = temaRepository.isReferencedByProcedimiento(id);

        if(usadoEnProcedimiento) {
            return Constantes.NO_ELIMINABLE_POR_TENER_REFERENCIAS;
        }else {
            JTema jTema = temaRepository.getReference(id);
            temaRepository.delete(jTema);
            return 0;
        }
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
            return new Pagina<>(items, 0L);
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

    private void validarNivelTemasMaximo(TemaDTO dto, JTema temaActual, String idioma) {
        if (dto == null || dto.getEntidad() == null || dto.getEntidad().getCodigo() == null) {
            return;
        }

        JEntidad entidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        Integer nivelMaximo = entidad.getNivelTemasMaximo();
        int nivelMaximoFinal = nivelMaximo != null ? nivelMaximo : DEFAULT_NIVEL_TEMAS_MAXIMO;

        Long padreNuevoId = dto.getTemaPadre() != null ? dto.getTemaPadre().getCodigo() : null;
        if (padreNuevoId == null) {
            return;
        }

        if (temaActual != null) {
            Long padreActualId = temaActual.getTemaPadre() != null ? temaActual.getTemaPadre().getCodigo() : null;
            if (Objects.equals(padreActualId, padreNuevoId)) {
                return;
            }
        }

        JTema temaPadre = temaRepository.getReference(padreNuevoId);
        int nivelPadre = calcularNivelDesdeMathPath(temaPadre.getMathPath());
        int nivelNuevoTema = nivelPadre + 1;

        int profundidadSubarbol = 1;
        if (temaActual != null) {
            int nivelActual = calcularNivelDesdeMathPath(temaActual.getMathPath());
            String prefix;
            if (temaActual.getMathPath() == null || temaActual.getMathPath().isEmpty()) {
                prefix = temaActual.getCodigo() + ";";
            } else {
                prefix = temaActual.getMathPath() + ";" + temaActual.getCodigo() + ";";
            }
            List<JTema> hijosAll = temaRepository.getHijosTodosNiveles(prefix, idioma);
            for (JTema hijo : hijosAll) {
                int nivelHijo = calcularNivelDesdeMathPath(hijo.getMathPath());
                int profundidadRelativa = (nivelHijo - nivelActual) + 1;
                if (profundidadRelativa > profundidadSubarbol) {
                    profundidadSubarbol = profundidadRelativa;
                }
            }
        }

        int nivelMaximoResultado = nivelNuevoTema + profundidadSubarbol - 1;
        if (nivelMaximoResultado > nivelMaximoFinal) {
            throw new TemaNivelMaximoException(nivelMaximoFinal);
        }
    }

    private int calcularNivelDesdeMathPath(String mathPath) {
        if (mathPath == null || mathPath.isEmpty()) {
            return 1;
        }
        String[] segmentos = mathPath.split(";");
        return segmentos.length + 1;
    }


    private void verificarModificacionTemaPadre(TemaDTO temaActualizado, JTema jTema, String idioma) {
        if (temaActualizado.getTemaPadre() != null && jTema.getTemaPadre() != null) {
            if (jTema.getTemaPadre().getCodigo().compareTo(temaActualizado.getTemaPadre().getCodigo()) != 0) {
                String mathPathAntiguo = jTema.getMathPath();
                String mathPathNuevo = temaActualizado.getMathPath();
                String prefixBusqueda;
                if (mathPathAntiguo == null || mathPathAntiguo.isEmpty()) {
                    prefixBusqueda = jTema.getCodigo().toString() + ";";
                } else {
                    prefixBusqueda = mathPathAntiguo + ";" + jTema.getCodigo().toString() + ";";
                }
                List<JTema> hijosAll = temaRepository.getHijosTodosNiveles(prefixBusqueda, idioma);
                for (JTema tema : hijosAll) {
                    String mathPath = tema.getMathPath();
                    String mathPathActualizado = mathPath.replace(mathPathAntiguo, mathPathNuevo);
                    tema.setMathPath(mathPathActualizado);
                    temaRepository.update(tema);
                }
                JTema temaPadre = temaRepository.getReference(temaActualizado.getTemaPadre().getCodigo());
                jTema.setTemaPadre(temaPadre);
                String mathPathPadre = temaPadre.getMathPath();
                if (mathPathPadre != null && !mathPathPadre.isEmpty()) {
                    jTema.setMathPath(mathPathPadre + ";" + temaPadre.getCodigo().toString());
                } else {
                    jTema.setMathPath(temaPadre.getCodigo().toString());
                }
            }
        } else if (temaActualizado.getTemaPadre() == null && jTema.getTemaPadre() != null) {
            String mathPathAntiguo = jTema.getMathPath();
            String prefixBusqueda;
            if (mathPathAntiguo == null || mathPathAntiguo.isEmpty()) {
                prefixBusqueda = jTema.getCodigo().toString() + ";";
            } else {
                prefixBusqueda = mathPathAntiguo + ";" + jTema.getCodigo().toString() + ";";
            }
            List<JTema> hijosAll = temaRepository.getHijosTodosNiveles(prefixBusqueda, idioma);
            for (JTema tema : hijosAll) {
                String mathPath = tema.getMathPath();
                String mathPathActualizado = mathPath.replace(mathPathAntiguo, "");
                tema.setMathPath(mathPathActualizado);
                temaRepository.update(tema);
            }
            jTema.setTemaPadre(null);
            jTema.setMathPath(null);
        } else if (temaActualizado.getTemaPadre() != null && jTema.getTemaPadre() == null) {

            String prefixBusqueda = temaActualizado.getCodigo().toString() + ";";
            List<JTema> hijosAll = temaRepository.getHijosTodosNiveles(prefixBusqueda, idioma);
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
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoMateriaSIADTO> getTipoMateriasSIA(String idioma) {
        TipoMateriaSIAFiltro filtro = new TipoMateriaSIAFiltro();
        filtro.setIdioma(idioma);
        filtro.setPaginaTamanyo(1000);
        return tipoMateriaSIARepository.getListTipoMateriaSIADTO(filtro);
    }

    @Override
    @PermitAll
    public boolean tieneHijos(Long temaCodigo) {
        return temaRepository.tieneHijos(temaCodigo);
    }

    @Override
    @PermitAll
    public boolean esHijo(Long codigo, Long codigo1) {
        return temaRepository.esHijo(codigo, codigo1);
    }
}
