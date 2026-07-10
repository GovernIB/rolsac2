package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.commons.plugins.pdu.api.model.*;
import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.CategoriaPduConverter;
import es.caib.rolsac2.persistence.model.JCategoriaPDU;
import es.caib.rolsac2.persistence.model.JIndexacionPdu;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.persistence.repository.CategoriaPDURepository;
import es.caib.rolsac2.persistence.repository.IndexacionPDURepository;
import es.caib.rolsac2.persistence.repository.ProcedimientoRepository;
import es.caib.rolsac2.service.facade.PduServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoPduFiltro;
import es.caib.rolsac2.service.model.types.TypeIdiomaFijo;
import es.caib.rolsac2.service.model.types.TypeIdiomaOpcional;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Logged
@ExceptionTranslate
@Stateless
@Local(PduServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PduServiceFacadeBean implements PduServiceFacade {

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private IndexacionPDURepository indexacionPDURepository;

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    private CategoriaPDURepository categoriaPduRepository;

    @Inject
    private CategoriaPduConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pair<RPeticionImportarEnlace, String> crearPeticionPdu(IndexacionPDUDto indexacionDTO) {
        Long codigoWF = null;
        StringBuilder mensajeTraza = new StringBuilder();

        codigoWF = procedimientoService.getCodigoByWF(indexacionDTO.getCodElemento(), Constantes.PROCEDIMIENTO_DEFINITIVO);

        if (codigoWF == null) {

            mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " no está publicat. \n");
//            return new ResultadoAccion(true, "El procediment " + indexacionDTO.getCodElemento() + " NO esta publicat.");
            return Pair.of(null, mensajeTraza.toString());
        }

        ProcedimientoBaseDTO procedimientoDTO = "SER".equals(indexacionDTO.getTipo()) ?
                procedimientoService.findServicioById(codigoWF) : procedimientoService.findProcedimientoById(codigoWF);

        // Validación pdu
        // Debe tener el idioma ingles para título y descripción
        if (procedimientoDTO.getNombreProcedimientoWorkFlow().getTraduccion(TypeIdiomaOpcional.INGLES.toString()) == null ||
                procedimientoDTO.getObjeto().getTraduccion(TypeIdiomaOpcional.INGLES.toString()) == null) {
            mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " no tiene traducción al inglés " +
                    "para el título o la descripción de la entrada PDU a crear. \n");
            return Pair.of(null, mensajeTraza.toString());
        }

        // Debe tener categoría PDU
        if (procedimientoDTO.getCategoriasPDU() == null || procedimientoDTO.getCategoriasPDU().isEmpty()) {
            mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " no tiene categoría PDU. \n");
            return Pair.of(null, mensajeTraza.toString());
        }


        RPeticionImportarEnlace peticionPdu = new RPeticionImportarEnlace();
        List<RLinkData> datosPdu = new ArrayList<>();

        try {
            RLinkData datoPdu = rellenarDatosPdu(indexacionDTO, procedimientoDTO, TypeIdiomaFijo.CASTELLANO.toString());
            datosPdu.add(datoPdu);


            RLinkData datoPduEn = rellenarDatosPdu(indexacionDTO, procedimientoDTO, TypeIdiomaOpcional.INGLES.toString());
            datosPdu.add(datoPduEn);

        } catch (Exception e) {
            return Pair.of(null, e.getMessage());
        }

        peticionPdu.setLinkData(datosPdu);

        return Pair.of(peticionPdu, mensajeTraza.toString());
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pair<RPeticionImportarEnlace, String> peticionEliminarElementoPdu(IndexacionPDUDto indexacionDTO) {
        Long codigoWF = null;
        StringBuilder mensajeTraza = new StringBuilder();

        codigoWF = procedimientoService.getCodigoByWF(indexacionDTO.getCodElemento(), Constantes.PROCEDIMIENTO_DEFINITIVO);

        if (codigoWF == null) {

            mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " no está publicat. \n");
//            return new ResultadoAccion(true, "El procediment " + indexacionDTO.getCodElemento() + " NO esta publicat.");
            return Pair.of(null, mensajeTraza.toString());
        }

        ProcedimientoBaseDTO procedimientoDTO = "SER".equals(indexacionDTO.getTipo()) ?
                procedimientoService.findServicioById(codigoWF) : procedimientoService.findProcedimientoById(codigoWF);


        RPeticionImportarEnlace peticionPdu = new RPeticionImportarEnlace();
        List<RLinkData> datosPdu = new ArrayList<>();

        try {
            RLinkData datoPdu = rellenarDatosPdu(indexacionDTO, procedimientoDTO, TypeIdiomaFijo.CASTELLANO.toString());
            datoPdu.setDelete(RTypeDelete.YES);
            datosPdu.add(datoPdu);


            RLinkData datoPduEn = rellenarDatosPdu(indexacionDTO, procedimientoDTO, TypeIdiomaOpcional.INGLES.toString());
            datoPdu.setDelete(RTypeDelete.YES);
            datosPdu.add(datoPduEn);

        } catch (Exception e) {
            return Pair.of(null, e.getMessage());
        }

        peticionPdu.setLinkData(datosPdu);

        return Pair.of(peticionPdu, mensajeTraza.toString());
    }

    private RLinkData rellenarDatosPdu(IndexacionPDUDto indexacionDTO, ProcedimientoBaseDTO procedimiento, String idioma) throws Exception {
        RLinkData linkData = new RLinkData();

        Set<RCategory> listaCategorias = new HashSet<>();

        // Se añaden las categorías PDU
        if (procedimiento.getCategoriasPDU() != null) {
            procedimiento.getCategoriasPDU().forEach(categoria -> {
                RCategory category = new RCategory();
                category.setCategory(categoria.getIdentificador());
                listaCategorias.add(category);
            });
        }

        linkData.setCategories(new ArrayList<>(listaCategorias));

        linkData.setTitle(procedimiento.getNombreProcedimientoWorkFlow().getTraduccion(idioma) != null ?
                procedimiento.getNombreProcedimientoWorkFlow().getTraduccion(idioma) : "");
        linkData.setDescription(procedimiento.getObjeto().getTraduccion(idioma) != null ? procedimiento.getObjeto().getTraduccion(idioma) : "");
        String urlPdu = systemServiceFacade.obtenerPropiedadConfiguracion("pdu.urlProc");// + indexacionDTO.getCodElemento();

        String.format(urlPdu, idioma, indexacionDTO.getCodElemento());

        linkData.setUrl(String.format(urlPdu, idioma, indexacionDTO.getCodElemento()));
        String parentUrlPdu = systemServiceFacade.obtenerPropiedadConfiguracion("pdu.parentUrl");
        linkData.setParentUrl(parentUrlPdu);
        RTypeLink tipoLink = "P".equals(procedimiento.getTipo()) ? RTypeLink.Procedure : RTypeLink.Information;

        linkData.setType(tipoLink.toString());
        linkData.setUrlType(RTypeUrl.Webpage);
        linkData.setLanguage(idioma);
        linkData.setIgnoreParams(new ArrayList<>());
        linkData.setSdgDashboardInfoSearchResults("");
        linkData.setCrawlUrl("");
        linkData.setSitemaps(new ArrayList<>());
        linkData.setExcludedPaths(new ArrayList<>());
        linkData.setNationalCode("ES53");

        RTypeDelete tipoDelete = indexacionDTO.getAccion() == 2 ? RTypeDelete.YES : RTypeDelete.NO;
        linkData.setDelete(tipoDelete);
        //Cambios añadidos el 2 de julio de 2024 por cambios en el servicio PDU que requieren estos campos aunque sean vacíos
        //De momento sólo están en PRE pero se puede subir a PRO pues se ignoraran estos campos
        linkData.setProcedureName("");
        linkData.setProcedureType("");
        linkData.setProcedureAvailability("");
        //Cambios añadidos el 11 de diciembre de 2024 por cambios en el servicio PDU que requieren este campos aunque sean vacíos
        //De momento sólo están en PRE pero se puede subir a PRO pues se ignoraran estos campos

        linkData.setAssociatedProcedures(new ArrayList<>());

        return linkData;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteIndexacion(Long codElemento) {
        indexacionPDURepository.deleteByCodElemento(codElemento);
    }

    @Override
    @PermitAll
    public void actualizarPDUfuturo(IndexacionPDUDto indexacionPDU, String mensaje) {
        // Se actualiza la indexacionPDU para que se reindexe otro día, cuando se ejecute el flujo poniendo el mensaje en el mensaje error
        indexacionPDU.setMensajeError(mensaje);
        if (indexacionPDU.getCodigo() != null) {
            JIndexacionPdu jindexacionPDU = indexacionPDURepository.findById(indexacionPDU.getCodigo());
            jindexacionPDU.setMensajeError(mensaje);
            indexacionPDURepository.update(jindexacionPDU);
        }
    }


    @Override
    @PermitAll
    public void quitarPDUnoDef(IndexacionPDUDto pduDto) {
        indexacionPDURepository.deleteByCodElemento(pduDto.getCodElemento());
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarPDU(IndexacionPDUDto pduDto, ResultadoPdu resultadoPDU) {
        if (pduDto.getCodigo() != null) {
            indexacionPDURepository.actualizarDato(pduDto, resultadoPDU);
        }

        if (resultadoPDU.isCorrecto()) {
            JProcedimientoWorkflow procWf = procedimientoRepository.getWF(pduDto.getCodElemento(), false);

            // Siempre se mandan los idiomas a pdu en orden castellano y luego inglés, así que se reciben en ese orden
            for (JProcedimientoWorkflowTraduccion traduccion : procWf.getTraducciones()) {
                if (TypeIdiomaFijo.CASTELLANO.toString().equals(traduccion.getIdioma())) {
                    traduccion.setUrlPdu(resultadoPDU.getRespuestaPdu().getEnlaces().get(0));
                } else if (TypeIdiomaOpcional.INGLES.toString().equals(traduccion.getIdioma())) {
                    traduccion.setUrlPdu(resultadoPDU.getRespuestaPdu().getEnlaces().get(1));
                }
            }

            procedimientoRepository.updateWF(procWf);

            JProcedimiento procedimiento = procedimientoRepository.findById(pduDto.getCodElemento());
            procedimiento.setEstadoPdu(1);

            procedimientoRepository.update(procedimiento);

        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Pagina<IndexacionPDUDto> getProcedimientosIntegrado(Long idEntidad) {
        return procedimientoRepository.getIndexacionProcedimientosIntegradosPdu(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public CategoriaPDUDTO findCategoriaById(long id) {
        JCategoriaPDU categoria = categoriaPduRepository.findById(id);

        return converter.createDTO(categoria);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<IndexacionPDUDto> getPendientesIntegrar(Long idEntidad) {
        try {
            ProcesoPduFiltro filtro = new ProcesoPduFiltro();
            filtro.setIdEntidad(idEntidad);
//            filtro.setIntegrarPdu(true);  Quitamos esta´opcion
//            filtro.setEstadoProcedimiento(TypeProcedimientoEstado.PUBLICADO);  // Solo se indexan si está publicado el procedimiento
            filtro.setPaginaTamanyo(10000);
            filtro.setPaginaFirst(0);

            List<IndexacionPDUDto> items = indexacionPDURepository.findPagedByFiltro(filtro);
            long total = indexacionPDURepository.countByFiltro(filtro);

            return new Pagina<>(items, total);
        } catch (Exception e) {
            List<IndexacionPDUDto> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<String> obtenerEnlaces(IndexacionPDUDto indexacionDTO) {
        JProcedimientoWorkflow procWf = procedimientoRepository.getWF(indexacionDTO.getCodElemento(), false);
        List<String> enlaces = new ArrayList<>();
        for (JProcedimientoWorkflowTraduccion traduccion : procWf.getTraducciones()) {
            if (TypeIdiomaFijo.CASTELLANO.toString().equals(traduccion.getIdioma())) {
                enlaces.add(traduccion.getUrlPdu());
            } else if (TypeIdiomaOpcional.INGLES.toString().equals(traduccion.getIdioma())) {
                enlaces.add(traduccion.getUrlPdu());
            }
        }
        return enlaces;
    }

}
