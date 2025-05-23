package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.AyudaConverter;
import es.caib.rolsac2.persistence.model.JAyuda;
import es.caib.rolsac2.persistence.repository.AyudaRepository;
import es.caib.rolsac2.persistence.repository.FicheroExternoRepositoryBean;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.AyudaServiceFacade;
import es.caib.rolsac2.service.model.AyudaDTO;
import es.caib.rolsac2.service.model.AyudaGridDTO;
import es.caib.rolsac2.service.model.AyudaImagenGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.AyudaFiltro;
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
import java.util.ArrayList;
import java.util.List;

@Logged
@ExceptionTranslate
@Stateless
@Local(AyudaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AyudaServiceFacadeBean implements AyudaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(AyudaServiceFacadeBean.class);
    private static final String ERROR_LITERAL = "Error";

    @Inject
    private AyudaRepository ayudaRepository;

    @Inject
    private AyudaConverter converter;

    @Inject
    private FicheroExternoRepositoryBean ficheroExternoRepositoryBean;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(AyudaDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JAyuda jAyuda = converter.createEntity(dto);


        ayudaRepository.create(jAyuda);
        return jAyuda.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(AyudaDTO dto) throws RecursoNoEncontradoException {
        JAyuda jAyuda = ayudaRepository.getReference(dto.getCodigo());
        converter.mergeEntity(jAyuda, dto);
        ayudaRepository.update(jAyuda);
    }


    @Override
    public void borrarAyudaById(Long idAyuda) {
        ayudaRepository.borrarAyudaById(idAyuda);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public AyudaDTO getAyuda(String identificador, String perfil) {
        return ayudaRepository.getAyuda(identificador, perfil);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public AyudaGridDTO getAyudaGrid(String identificador, String perfil) {
        return ayudaRepository.getAyudaGrid(identificador, perfil);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public AyudaGridDTO findGridById(Long id) {
        return converter.createGridDTO(ayudaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public AyudaGridDTO findGridByIdentificador(String identificador, String perfil) {
        return ayudaRepository.getAyudaGrid(identificador, perfil);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public AyudaDTO findByIdentificador(String identificador, String perfil) {
        return ayudaRepository.getAyuda(identificador, perfil);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AyudaGridDTO> findPageByFiltro(AyudaFiltro filtro) {
        return ayudaRepository.findAyudaPageByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<AyudaGridDTO> findAyudaPageByFiltro(AyudaFiltro filtro) {
        try {
            List<AyudaGridDTO> items = ayudaRepository.findAyudaPageByFiltro(filtro);
            long total = ayudaRepository.countAyudaByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<AyudaGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public long countAyudaByFiltro(AyudaFiltro filtro) {
        return ayudaRepository.countAyudaByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AyudaImagenGridDTO> listImagenes(String path) {
        List<AyudaImagenGridDTO> imgs = ayudaRepository.getImagenes();
        if (imgs != null) {
            for (AyudaImagenGridDTO img : imgs) {
                boolean existe = ficheroExternoRepositoryBean.existeFichero(path, img);
                img.setExisteFicheroFisico(existe);
            }
        }
        return imgs;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AyudaImagenGridDTO> listImagenesPerdidas(List<AyudaImagenGridDTO> imgs, String path) {
        List<AyudaImagenGridDTO> imgsRetorno = new ArrayList<>();

        List<String> ids = ficheroExternoRepositoryBean.getListadoFicheros(path, TypeFicheroExterno.AYUDAS_IMAGEN);
        if (ids != null) {
            for (String id : ids) {
                boolean encontrado = false;
                if (imgs != null) {
                    for (AyudaImagenGridDTO img : imgs) {
                        if (id.equals(img.getRuta())) {
                            encontrado = true;
                            break;
                        }
                    }
                }
                if (!encontrado) {
                    AyudaImagenGridDTO img = new AyudaImagenGridDTO();
                    img.setRuta(id);
                    img.setTotal(0L);
                    img.setFilename(id.replace("ayudas/", ""));
                    img.setExisteJFichero(false);
                    img.setExisteFicheroFisico(true);
                    imgsRetorno.add(img);
                }
            }
        }
        return imgsRetorno;
    }

}
