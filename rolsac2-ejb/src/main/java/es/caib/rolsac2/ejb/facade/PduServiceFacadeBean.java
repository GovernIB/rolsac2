package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.commons.plugins.pdu.api.model.RCategory;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RLinkData;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeDelete;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeLink;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeUrl;
import es.caib.rolsac2.commons.plugins.pdu.api.model.ResultadoPdu;
import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.CategoriaPduConverter;
import es.caib.rolsac2.persistence.model.JCategoriaPdu;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.persistence.repository.CategoriaPduRepository;
import es.caib.rolsac2.persistence.repository.IndexacionPDURepository;
import es.caib.rolsac2.persistence.repository.ProcedimientoRepository;
import es.caib.rolsac2.service.facade.PduServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.CategoriaPduDto;
import es.caib.rolsac2.service.model.Constantes;
import es.caib.rolsac2.service.model.IndexacionPDUDto;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoPduFiltro;
import es.caib.rolsac2.service.model.types.TypeIdiomaFijo;
import es.caib.rolsac2.service.model.types.TypeIdiomaOpcional;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import org.apache.commons.lang3.tuple.Pair;

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
    private CategoriaPduRepository categoriaPduRepository;

    @Inject
    private CategoriaPduConverter converter;

    @Override
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
        if( procedimientoDTO.getNombreProcedimientoWorkFlow().getTraduccion(TypeIdiomaOpcional.INGLES.toString()) == null ||
                procedimientoDTO.getObjeto().getTraduccion(TypeIdiomaOpcional.INGLES.toString()) == null) {
            mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " no tiene traducción al inglés " +
                    "para el título o la descripción de la entrada PDU a crear. \n");
            return Pair.of(null, mensajeTraza.toString());
        }

        // Debe tener categoría PDU
        if(procedimientoDTO.getTemas().stream().noneMatch(t -> t.getCategoriaPdu() != null)) {
            mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " no tiene categoría PDU. \n");
            return Pair.of(null, mensajeTraza.toString());
        }


        RPeticionImportarEnlace peticionPdu = new RPeticionImportarEnlace();
        List<RLinkData> datosPdu = new ArrayList<>();

        try {
            RLinkData datoPdu = rellenarDatosPdu(indexacionDTO, procedimientoDTO,  TypeIdiomaFijo.CASTELLANO.toString());
            datosPdu.add(datoPdu);


            RLinkData datoPduEn = rellenarDatosPdu(indexacionDTO, procedimientoDTO, TypeIdiomaOpcional.INGLES.toString());
            datosPdu.add(datoPduEn);

        } catch (Exception e) {
            return Pair.of(null, e.getMessage());
        }

        peticionPdu.setLinkData(datosPdu);

        return Pair.of(peticionPdu, mensajeTraza.toString());
    }

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
            RLinkData datoPdu = rellenarDatosPdu(indexacionDTO, procedimientoDTO,  TypeIdiomaFijo.CASTELLANO.toString());
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

        procedimiento.getTemas().stream().filter(t-> t.getCategoriaPdu() != null).map(tema -> {
            RCategory category = new RCategory();
            category.setCategory(tema.getCategoriaPdu().getIdentificador());
            return category;
        }).forEach(listaCategorias::add);

        linkData.setCategories(new ArrayList<>(listaCategorias));

        linkData.setTitle(procedimiento.getNombreProcedimientoWorkFlow().getTraduccion(idioma) !=null ?
                procedimiento.getNombreProcedimientoWorkFlow().getTraduccion(idioma) : "");
        linkData.setDescription(procedimiento.getObjeto().getTraduccion(idioma) !=null? procedimiento.getObjeto().getTraduccion(idioma):"");
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
        linkData.setNationalCode("ES52");

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
    public void deleteIndexacion(Long codElemento){
        indexacionPDURepository.deleteByCodElemento(codElemento);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarPDU(IndexacionPDUDto pduDto, ResultadoPdu resultadoPDU) {
        if (pduDto.getCodigo() != null) {
            indexacionPDURepository.actualizarDato(pduDto, resultadoPDU);
        }

        if(resultadoPDU.isCorrecto()) {
            JProcedimientoWorkflow procWf = procedimientoRepository.getWF(pduDto.getCodElemento(), false);

            // Siempre se mandan los idiomas a pdu en orden castellano y luego inglés, así que se reciben en ese orden
            for(JProcedimientoWorkflowTraduccion traduccion : procWf.getTraducciones()){
                if(TypeIdiomaFijo.CASTELLANO.toString().equals(traduccion.getIdioma())){
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
    public List<CategoriaPduDto> getCategoriasPdu() {

        List<JCategoriaPdu> jCategorias =  categoriaPduRepository.findAll();

        return converter.createDTOs(jCategorias);

    }

    @Override
    public CategoriaPduDto findCategoriaById(long id) {
        JCategoriaPdu categoria = categoriaPduRepository.findById(id);

        return converter.createDTO(categoria);
    }

    @Override
    public Pagina<IndexacionPDUDto> getPendientesIntegrar(Long idEntidad) {
        try {
            ProcesoPduFiltro filtro = new ProcesoPduFiltro();
            filtro.setIdEntidad(idEntidad);
//            filtro.setIntegrarPdu(true);  Quitamos esta´opcion
            filtro.setEstadoProcedimiento(TypeProcedimientoEstado.PUBLICADO);  // Solo se indexan si está publicado el procedimiento
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
}
