package es.caib.rolsac2.ejb.facade.procesos.solr;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.*;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumAplicacionId;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;
import es.caib.rolsac2.service.model.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CastUtil {

    public static DataIndexacion getDataIndexacion(ProcedimientoTramiteDTO tramite, ProcedimientoDTO procedimiento, PathUA pathUO) {

        DataIndexacion indexData = new DataIndexacion();

        // Preparamos la información básica: id elemento, aplicacionID = ROLSAC y la
        // categoria de tipo ficha.
        indexData.setCategoria(EnumCategoria.ROLSAC_TRAMITE);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setCategoriaPadre(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        indexData.setElementoId(tramite.getCodigo().toString());
        indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        indexData.setElementoIdRaiz(procedimiento.getCodigo().toString());

        indexData.getUos().add(pathUO);
        indexData.setElementoIdPadre(procedimiento.getCodigo().toString());

        // Iteramos las traducciones
        final LiteralMultilang titulo = new LiteralMultilang();
        final LiteralMultilang descripcion = new LiteralMultilang();
        final LiteralMultilang descripcionPadre = new LiteralMultilang();
        final LiteralMultilang urls = new LiteralMultilang();
        final LiteralMultilang urlsPadre = new LiteralMultilang();
        final LiteralMultilang searchText = new LiteralMultilang();
        final LiteralMultilang searchTextOptional = new LiteralMultilang();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();

        // Recorremos las traducciones
        for (final String keyIdioma : tramite.getNombre().getIdiomas()) {
            final EnumIdiomas enumIdioma = EnumIdiomas.fromString(keyIdioma);

            if (enumIdioma != null) {

                if (tramite.getNombre().getTraduccion(keyIdioma) == null || tramite.getNombre().getTraduccion(keyIdioma).isEmpty()) {
                    continue;
                }

                // Anyadimos idioma al enumerado.
                idiomas.add(enumIdioma);

                // Seteamos los primeros campos multiidiomas: Titulo, Descripción (y padre) y el
                // search text y el search text optional.
                titulo.addIdioma(enumIdioma, tramite.getNombre().getTraduccion(keyIdioma));
                descripcion.addIdioma(enumIdioma, tramite.getObservacion().getTraduccion(keyIdioma)); // solrIndexer.htmlToText(tramite.getObservaciones()));
                if (procedimiento.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma) != null) {
                    descripcionPadre.addIdioma(enumIdioma, procedimiento.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma));
                }
                searchText.addIdioma(enumIdioma, tramite.getNombre().getTraduccion(keyIdioma) + " " + tramite.getObservacion().getTraduccion(keyIdioma) + " " + procedimiento.getKeywords());
                searchTextOptional.addIdioma(enumIdioma, tramite.getDocumentacion().getTraduccion(keyIdioma));

                if (procedimiento != null) {

                    String nombrePubObjetivox = "persones";
                    String idPublicoObjetivo = "200";

                    if (procedimiento.getPublicosObjetivo() != null && !procedimiento.getPublicosObjetivo().isEmpty()) {
                        final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo = procedimiento.getPublicosObjetivo().get(0);
                        nombrePubObjetivox = publicoObjectivo.getIdentificador().toLowerCase();
                        idPublicoObjetivo = publicoObjectivo.getCodigo().toString();
                    }

                    urlsPadre.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPublicoObjetivo + "/" + nombrePubObjetivox + "/tramites/tramite/" + procedimiento.getCodigo());
                    urls.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPublicoObjetivo + "/" + nombrePubObjetivox + "/tramites/tramite/" + procedimiento.getCodigo());
                }
            }

        }

        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcion(descripcion);
        indexData.setDescripcionPadre(descripcionPadre);
        indexData.setUrl(urls);
        indexData.setUrlPadre(urlsPadre);
        indexData.setSearchText(searchText);
        indexData.setSearchTextOptional(searchTextOptional);
        indexData.setIdiomas(idiomas);

        // Datos IDs materias.
        final List<String> materiasId = new ArrayList<String>();
        //for (final TipoMateriaSIAGridDTO materia : procedimiento.getMateriasSIA()) {
        //    materiasId.add(materia.getCodigo().toString());
        //}
        indexData.setMateriaId(materiasId);

        // Datos IDs publico Objetivos.
        final List<String> publicoObjetivoId = new ArrayList<String>();
        for (final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo : procedimiento.getPublicosObjetivo()) {
            publicoObjetivoId.add(publicoObjectivo.getCodigo().toString());
        }
        indexData.setPublicoId(publicoObjetivoId);

        // Fechas
        indexData.setFechaActualizacion(procedimiento.getFechaActualizacion());
        indexData.setFechaPublicacion(tramite.getFechaPublicacion());
        indexData.setFechaCaducidad(tramite.getFechaCierre());


        final ProcedimientoTramiteDTO tramiteInicio = IndexacionUtil.getTramiteInicio(procedimiento.getTramites());
        if (tramiteInicio != null) {
            indexData.setFechaPlazoIni(tramite.getFechaInicio());
            indexData.setFechaPlazoFin(tramite.getFechaCierre());
        }

        indexData.setInterno(false);

        // FamiliaID
        //if (procedimiento.getFamilia() != null) {
        //    indexData.setFamiliaId(procedimiento.getFamilia().getId().toString());
        // }

        // Telematico

        indexData.setTelematico(tramite.isTramitElectronica());


        return indexData;
    }

    public static DataIndexacion getDataIndexacion(ServicioDTO servicio, PathUA pathUA) {

        DataIndexacion indexData = new DataIndexacion();

        // Preparamos la información básica: id elemento, aplicacionID = ROLSAC y la
        // categoria de tipo ficha.

        indexData.setCategoria(EnumCategoria.ROLSAC_SERVICIO);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setElementoId(servicio.getCodigo().toString());
        indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_SERVICIO);
        indexData.setElementoIdRaiz(servicio.getCodigo().toString());

        // Iteramos las traducciones
        final LiteralMultilang titulo = new LiteralMultilang();
        final LiteralMultilang descripcion = new LiteralMultilang();
        final LiteralMultilang urls = new LiteralMultilang();
        final LiteralMultilang searchText = new LiteralMultilang();
        final LiteralMultilang searchTextOptional = new LiteralMultilang();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();

        final String nomUnidadAministrativa;
        if (servicio.getUaInstructor() == null) {
            nomUnidadAministrativa = "";
        } else {
            nomUnidadAministrativa = servicio.getUaInstructor().getNombre().getTraduccion("ca");
        }

        final boolean esProcSerInterno = contienePOInterno(servicio.getPublicosObjetivo());

        // Recorremos las traducciones
        for (final String keyIdioma : servicio.getNombreProcedimientoWorkFlow().getIdiomas()) {


            final EnumIdiomas enumIdioma = EnumIdiomas.fromString(keyIdioma);

            if (enumIdioma != null) {
                if (servicio.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma) == null || servicio.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma).isEmpty()) {
                    continue;
                }

                // Anyadimos idioma al enumerado.
                idiomas.add(enumIdioma);

                // Seteamos los primeros campos multiidiomas: Titulo, Descripción y el search
                // text.
                titulo.addIdioma(enumIdioma, servicio.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma));
                descripcion.addIdioma(enumIdioma, servicio.getObjeto().getTraduccion(keyIdioma));
                searchText.addIdioma(enumIdioma, servicio.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma) + " " + servicio.getObjeto().getTraduccion(keyIdioma) + " " + servicio.getKeywords());

                final StringBuffer textoOptional = new StringBuffer();

                // materia
                //for (final TipoMateriaSIAGridDTO materia : servicio.getMateriasSIA()) {
                //    textoOptional.append(" ");
                //    textoOptional.append(materia.getCodigo());
                //    textoOptional.append(" ");
                //    textoOptional.append(materia.getIdentificador());
                //    textoOptional.append(" ");
                //    textoOptional.append(materia.getDescripcion().getTraduccion(keyIdioma));

                //}

                // Servicio Responsable
                if (servicio.getUaResponsable() != null) {
                    textoOptional.append(" ");
                    textoOptional.append(servicio.getUaResponsable().getNombre().getTraduccion(keyIdioma));

                }

                // Publico objetivo, para extraer el nombre del publico objetivo
                String nombrePubObjetivo = "persones";
                String idPubObjetivo = "200";
                for (final TipoPublicoObjetivoEntidadGridDTO publicoObjetivo : servicio.getPublicosObjetivo()) {
                    nombrePubObjetivo = publicoObjetivo.getDescripcion().getTraduccion(keyIdioma).toLowerCase();
                    idPubObjetivo = publicoObjetivo.getCodigo().toString();
                }

                // UO
                if (servicio.getUaInstructor() != null && servicio.getUaInstructor().getNombre().getTraduccion(keyIdioma) != null) {
                    textoOptional.append(" ");
                    textoOptional.append(servicio.getUaInstructor().getNombre().getTraduccion(keyIdioma));

                }

                // Normativa asociadas
                for (final NormativaGridDTO normativa : servicio.getNormativas()) {
                    textoOptional.append(normativa.getTitulo().getTraduccion(keyIdioma));
                    textoOptional.append(" ");

                }

                searchTextOptional.addIdioma(enumIdioma, servicio.getObjeto().getTraduccion(keyIdioma) + " " + servicio.getObservaciones().getTraduccion(keyIdioma) + " " + textoOptional.toString());

                if (esProcSerInterno) {
                    // Si es interno usamos la url especifica para los Servicios internos

                    final String url = getPropiedadPOInternoUrlProc().replace("{idioma}", keyIdioma).replace("{idPublicoObjetivo}", idPubObjetivo).replace("{nombrePubObjetivo}", nombrePubObjetivo).replace("{idServicio}", servicio.getCodigo().toString());
                    urls.addIdioma(enumIdioma, url);

                } else {
                    // Si no es interno
                    urls.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPubObjetivo + "/" + nombrePubObjetivo + "/tramites/servicio/" + servicio.getCodigo());
                }

            }
        }

        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcion(descripcion);
        indexData.setUrl(urls);
        indexData.setSearchText(searchText);
        indexData.setSearchTextOptional(searchTextOptional);
        indexData.setIdiomas(idiomas);

        // Datos IDs materias.
        final List<String> materiasId = new ArrayList<String>();
        //for (final TipoMateriaSIAGridDTO materia : servicio.getMateriasSIA()) {
        //    materiasId.add(materia.getCodigo().toString());
        // }
        indexData.setMateriaId(materiasId);

        // Datos IDs publico Objetivos.
        final List<String> publicoObjetivoId = new ArrayList<String>();
        for (final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo : servicio.getPublicosObjetivo()) {
            publicoObjetivoId.add(publicoObjectivo.getCodigo().toString());
        }
        indexData.setPublicoId(publicoObjetivoId);

        // Fechas
        indexData.setFechaActualizacion(servicio.getFechaActualizacion());
        indexData.setFechaPublicacion(servicio.getFechaPublicacion());
        indexData.setFechaCaducidad(servicio.getFechaCaducidad());
        if (esProcSerInterno) {
            indexData.setInterno(true);
        } else {
            indexData.setInterno(false);
        }

        // Telematico
        indexData.setTelematico(servicio.isTramitElectronica());

        // UA
        if (pathUA == null) {
            //return new SolrPendienteResultado(true, "No se puede indexar: no cuelga de UA visible");
        }
        indexData.getUos().add(pathUA);

        indexData.setFamiliaId("none");
        return indexData;
    }

    public static DataIndexacion getDataIndexacion(UnidadAdministrativaDTO uaDTO, PathUA pathUA) {
        DataIndexacion indexData = new DataIndexacion();
        indexData.setCategoria(EnumCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setElementoId(uaDTO.getCodigo().toString());
        indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA);
        indexData.setElementoIdRaiz(uaDTO.getCodigo().toString());
        indexData.getUos().add(pathUA);
        //indexData.setFechaPublicacion(java.util.Date.from(uaDTO.getFechaActualizacion().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Iteramos las traducciones
        final LiteralMultilang titulo = new LiteralMultilang();
        final LiteralMultilang descripcion = new LiteralMultilang();
        final LiteralMultilang urls = new LiteralMultilang();
        final LiteralMultilang searchText = new LiteralMultilang();
        final LiteralMultilang searchTextOptional = new LiteralMultilang();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();

        // Recorremos las traducciones
        for (final String keyIdioma : uaDTO.getNombre().getIdiomas()) {
            final EnumIdiomas enumIdioma = EnumIdiomas.fromString(keyIdioma);
            if (enumIdioma != null) {
                //Para saltarse los idiomas sin titulo
                if (uaDTO.getNombre().getTraduccion(keyIdioma) == null || uaDTO.getNombre().getTraduccion(keyIdioma).isEmpty()) {
                    continue;
                }

                //Anyadimos idioma al enumerado.
                idiomas.add(enumIdioma);

                // Seteamos los primeros campos multiidiomas: Titulo, Descripción y el search
                // text.
                titulo.addIdioma(enumIdioma, uaDTO.getNombre().getTraduccion(keyIdioma));
                descripcion.addIdioma(enumIdioma, uaDTO.getPresentacion().getTraduccion(keyIdioma));


                searchText.addIdioma(enumIdioma, uaDTO.getNombre().getTraduccion(keyIdioma) + " " + uaDTO.getResponsable().getTraduccion(keyIdioma) + " " + uaDTO.getResponsableNombre());


                final StringBuffer textoOptional = new StringBuffer();
                //TODO Revisar
                //textoOptional.append(IndexacionUtil.calcularPathTextUO(UA, keyIdioma));

                searchTextOptional.addIdioma(enumIdioma, textoOptional.toString());
                urls.addIdioma(enumIdioma, "/govern/organigrama/area.do?lang=" + keyIdioma + "&coduo=" + uaDTO.getCodigo());
            }
        }

        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcionHTML(descripcion);
        indexData.setUrl(urls);
        indexData.setSearchText(searchText);
        indexData.setSearchTextOptional(searchTextOptional);
        indexData.setIdiomas(idiomas);

        return indexData;
    }

    public static DataIndexacion getDataIndexacion(NormativaDTO norm, List<PathUA> pathUAs) {
        DataIndexacion indexData = new DataIndexacion();
        indexData.setCategoria(EnumCategoria.ROLSAC_NORMATIVA);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setElementoId(norm.getCodigo().toString());
        indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_NORMATIVA);
        indexData.setElementoIdRaiz(norm.getCodigo().toString());
        indexData.getUos().addAll(pathUAs);
        if (norm.getFechaBoletin() != null) {
            indexData.setFechaPublicacion(java.util.Date.from(norm.getFechaBoletin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        // Iteramos las traducciones
        final LiteralMultilang titulo = new LiteralMultilang();
        final LiteralMultilang descripcion = new LiteralMultilang();
        final LiteralMultilang urls = new LiteralMultilang();
        final LiteralMultilang searchText = new LiteralMultilang();
        final LiteralMultilang searchTextOptional = new LiteralMultilang();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();
        String urlCAT = "";
        if (norm.getUrlBoletin() != null && !norm.getUrlBoletin().getTraduccion("ca").isEmpty()) {
            urlCAT = norm.getUrlBoletin().getTraduccion("ca");
        }
        // Recorremos las traducciones
        for (final String keyIdioma : norm.getTitulo().getIdiomas()) {
            final EnumIdiomas enumIdioma = EnumIdiomas.fromString(keyIdioma);
            if (enumIdioma != null) {
                //Para saltarse los idiomas sin titulo
                if (norm.getTitulo().getTraduccion(keyIdioma) == null || norm.getTitulo().getTraduccion(keyIdioma).isEmpty()) {
                    continue;
                }

                //Anyadimos idioma al enumerado.
                idiomas.add(enumIdioma);

                // Seteamos los primeros campos multiidiomas: Titulo, Descripción y el search
                // text.
                titulo.addIdioma(enumIdioma, norm.getTitulo().getTraduccion(keyIdioma));
                descripcion.addIdioma(enumIdioma, norm.getTitulo().getTraduccion(keyIdioma));


                String tipoNormativaNombre = "";
                if (norm.getTipoNormativa() != null && norm.getTipoNormativa().getDescripcion() != null && norm.getTipoNormativa().getDescripcion().getTraduccion(keyIdioma) != null) {
                    tipoNormativaNombre = norm.getTipoNormativa().getDescripcion().getTraduccion(keyIdioma);
                }
                searchText.addIdioma(enumIdioma, norm.getTitulo().getTraduccion(keyIdioma) + " " + norm.getNumero() + " " + tipoNormativaNombre);


                final StringBuffer textoOptional = new StringBuffer();
                //TODO Revisar
                //textoOptional.append(" " + norm.getObservaciones());
                //textoOptional.append(IndexacionUtil.calcularPathTextUO(UA, keyIdioma));

                searchTextOptional.addIdioma(enumIdioma, textoOptional.toString());
                if (norm.getUrlBoletin() != null && !norm.getUrlBoletin().getTraduccion(keyIdioma).isEmpty()) {
                    urls.addIdioma(enumIdioma, norm.getUrlBoletin().getTraduccion(keyIdioma));
                } else {
                    //En caso de url vacio, cogemos la de catalan.
                    urls.addIdioma(enumIdioma, urlCAT);
                }
            }
        }

        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcion(descripcion);
        indexData.setUrl(urls);
        indexData.setSearchText(searchText);
        indexData.setSearchTextOptional(searchTextOptional);
        indexData.setIdiomas(idiomas);
        return indexData;
    }

    public static IndexFile getDataIndexacion(ProcedimientoDTO proc, ProcedimientoDocumentoDTO doc, DocumentoTraduccion docIdioma, FicheroDTO ficheroDTO, String idioma, PathUA pathUA) {
        IndexFile indexData = new IndexFile();
        indexData.setCategoria(EnumCategoria.ROLSAC_PROCEDIMIENTO_DOCUMENTO);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setElementoId(doc.getCodigo().toString());

        if (proc.getTipo().equals(Constantes.PROCEDIMIENTO)) {
            indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_PROCEDIMIENTO);
            indexData.setCategoriaPadre(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        } else {
            indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_SERVICIO);
            indexData.setCategoriaPadre(EnumCategoria.ROLSAC_SERVICIO);
        }
        indexData.setElementoIdRaiz(proc.getCodigo().toString());
        indexData.setElementoIdPadre(proc.getCodigo().toString());
        indexData.getUos().add(pathUA.convertirUOS());
        final boolean esProcSerInterno = contienePOInterno(proc.getPublicosObjetivo());

        // Iteramos las traducciones
        final MultilangLiteral titulo = new MultilangLiteral();
        final MultilangLiteral descripcion = new MultilangLiteral();
        final MultilangLiteral descripcionPadre = new MultilangLiteral();
        final MultilangLiteral urls = new MultilangLiteral();
        final MultilangLiteral urlsPadre = new MultilangLiteral();
        final StringBuffer textoOptional = new StringBuffer();

        final MultilangLiteral extension = new MultilangLiteral();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();

        // Recorremos las traducciones

        final EnumIdiomas enumIdioma = EnumIdiomas.fromString(idioma);
        if (enumIdioma != null) {
            //Para saltarse los idiomas sin titulo
            if (doc.getTitulo().getTraduccion(idioma) == null || doc.getTitulo().getTraduccion(idioma).isEmpty()) {
                return null;
            }

            //Anyadimos idioma al enumerado.
            idiomas.add(enumIdioma);


            // materia
            //for (final TipoMateriaSIAGridDTO materia : proc.getMateriasSIA()) {
            //    textoOptional.append(" ");
            //    textoOptional.append(materia.getCodigo());
            //    textoOptional.append(" ");
            //    textoOptional.append(materia.getIdentificador());
            //    textoOptional.append(" ");
            //   textoOptional.append(materia.getDescripcion().getTraduccion(idioma));
            //}

            // Servicio Responsable
            if (proc.getUaResponsable() != null && proc.getUaResponsable().getNombre() != null && proc.getUaResponsable().getNombre().getTraduccion(idioma) != null) {
                textoOptional.append(" ");
                textoOptional.append(proc.getUaResponsable().getNombre().getTraduccion(idioma));
            }

            titulo.addIdioma(enumIdioma, doc.getTitulo().getTraduccion(idioma));
            descripcion.addIdioma(enumIdioma, ficheroDTO.getFilename());
            descripcionPadre.addIdioma(enumIdioma, proc.getObjeto().getTraduccion(idioma));
            extension.addIdioma(enumIdioma, IndexacionUtil.calcularExtensionArchivo(ficheroDTO.getFilename()));
            indexData.setFileContent(ficheroDTO.getContenido());

            urls.addIdioma(enumIdioma, "/govern/rest/arxiu/" + ficheroDTO.getCodigo());


            // Publico objetivo, para extraer el nombre del publico objetivo
            String nombrePubObjetivo = "persones";
            String idPublicoObjetivo = "200";

            if (proc.getPublicosObjetivo() != null && !proc.getPublicosObjetivo().isEmpty()) {
                final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo = proc.getPublicosObjetivo().get(0);
                nombrePubObjetivo = publicoObjectivo.getIdentificador().toLowerCase();
                idPublicoObjetivo = publicoObjectivo.getCodigo().toString();
            }
            if (esProcSerInterno) {
                // Si es interno usamos la url especifica para los procedimientos internos

                final String url = getPropiedadPOInternoUrlProc().replace("{idioma}", idioma).replace("{idPublicoObjetivo}", idPublicoObjetivo).replace("{nombrePubObjetivo}", nombrePubObjetivo).replace("{idProcedimiento}", proc.getCodigo().toString());
                urls.addIdioma(enumIdioma, url);

            } else {
                // Si no es interno
                urls.addIdioma(enumIdioma, "/seucaib/" + idioma + "/" + idPublicoObjetivo + "/" + nombrePubObjetivo + "/tramites/tramite/" + proc.getCodigo());
            }
        }


        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcion(descripcion);
        indexData.setUrl(urls);
        indexData.setUrlPadre(urlsPadre);
        indexData.setFechaActualizacion(proc.getFechaActualizacion());
        indexData.setFechaPublicacion(proc.getFechaPublicacion());
        indexData.setFechaCaducidad(proc.getFechaCaducidad());
        indexData.setExtension(extension);
        MultilangLiteral searchTextOpcional = new MultilangLiteral();
        searchTextOpcional.addIdioma(EnumIdiomas.fromString(idioma), textoOptional.toString());
        indexData.setSearchTextOptional(searchTextOpcional);

        indexData.setIdioma(enumIdioma);
        return indexData;
    }


    public static IndexFile getDataIndexacion(ProcedimientoDTO proc, ProcedimientoTramiteDTO tramite, ProcedimientoDocumentoDTO doc, DocumentoTraduccion docIdioma, FicheroDTO ficheroDTO, String idioma, PathUA pathUA) {

        IndexFile indexData = new IndexFile();
        indexData.setCategoria(EnumCategoria.ROLSAC_PROCEDIMIENTO_DOCUMENTO);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setElementoId(doc.getCodigo().toString());

        indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        indexData.setCategoriaPadre(EnumCategoria.ROLSAC_TRAMITE);

        indexData.setElementoIdRaiz(proc.getCodigo().toString());
        indexData.setElementoIdPadre(tramite.getCodigo().toString());
        indexData.getUos().add(pathUA.convertirUOS());
        final boolean esProcSerInterno = contienePOInterno(proc.getPublicosObjetivo());

        // Iteramos las traducciones
        final MultilangLiteral titulo = new MultilangLiteral();
        final MultilangLiteral descripcion = new MultilangLiteral();
        final MultilangLiteral descripcionPadre = new MultilangLiteral();
        final MultilangLiteral urls = new MultilangLiteral();
        final MultilangLiteral urlsPadre = new MultilangLiteral();
        final StringBuffer textoOptional = new StringBuffer();

        final MultilangLiteral extension = new MultilangLiteral();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();

        // Recorremos las traducciones

        final EnumIdiomas enumIdioma = EnumIdiomas.fromString(idioma);
        if (enumIdioma != null) {
            //Para saltarse los idiomas sin titulo
            if (doc.getTitulo().getTraduccion(idioma) == null || doc.getTitulo().getTraduccion(idioma).isEmpty()) {
                return null;
            }

            //Anyadimos idioma al enumerado.
            idiomas.add(enumIdioma);


            // materia
            //for (final TipoMateriaSIAGridDTO materia : proc.getMateriasSIA()) {
            //    textoOptional.append(" ");
            //    textoOptional.append(materia.getCodigo());
            //    textoOptional.append(" ");
            //    textoOptional.append(materia.getIdentificador());
            //    textoOptional.append(" ");
            //    textoOptional.append(materia.getDescripcion().getTraduccion(idioma));
            //}

            // Servicio Responsable
            if (proc.getUaResponsable() != null && proc.getUaResponsable().getNombre() != null && proc.getUaResponsable().getNombre().getTraduccion(idioma) != null) {
                textoOptional.append(" ");
                textoOptional.append(proc.getUaResponsable().getNombre().getTraduccion(idioma));
            }

            titulo.addIdioma(enumIdioma, doc.getTitulo().getTraduccion(idioma));
            descripcion.addIdioma(enumIdioma, ficheroDTO.getFilename());
            descripcionPadre.addIdioma(enumIdioma, proc.getObjeto().getTraduccion(idioma));
            extension.addIdioma(enumIdioma, IndexacionUtil.calcularExtensionArchivo(ficheroDTO.getFilename()));
            indexData.setFileContent(ficheroDTO.getContenido());

            urls.addIdioma(enumIdioma, "/govern/rest/arxiu/" + ficheroDTO.getCodigo());


            // Publico objetivo, para extraer el nombre del publico objetivo
            String nombrePubObjetivo = "persones";
            String idPublicoObjetivo = "200";

            if (proc.getPublicosObjetivo() != null && !proc.getPublicosObjetivo().isEmpty()) {
                final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo = proc.getPublicosObjetivo().get(0);
                nombrePubObjetivo = publicoObjectivo.getIdentificador().toLowerCase();
                idPublicoObjetivo = publicoObjectivo.getCodigo().toString();
            }
            if (esProcSerInterno) {
                // Si es interno usamos la url especifica para los procedimientos internos

                final String url = getPropiedadPOInternoUrlProc().replace("{idioma}", idioma).replace("{idPublicoObjetivo}", idPublicoObjetivo).replace("{nombrePubObjetivo}", nombrePubObjetivo).replace("{idProcedimiento}", proc.getCodigo().toString());
                urls.addIdioma(enumIdioma, url);

            } else {
                // Si no es interno
                urls.addIdioma(enumIdioma, "/seucaib/" + idioma + "/" + idPublicoObjetivo + "/" + nombrePubObjetivo + "/tramites/tramite/" + proc.getCodigo());
            }
        }


        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcion(descripcion);
        indexData.setUrl(urls);
        indexData.setUrlPadre(urlsPadre);
        indexData.setFechaActualizacion(proc.getFechaActualizacion());
        indexData.setFechaPublicacion(proc.getFechaPublicacion());
        indexData.setFechaCaducidad(proc.getFechaCaducidad());
        indexData.setExtension(extension);
        MultilangLiteral searchTextOpcional = new MultilangLiteral();
        searchTextOpcional.addIdioma(EnumIdiomas.fromString(idioma), textoOptional.toString());
        indexData.setSearchTextOptional(searchTextOpcional);

        indexData.setIdioma(enumIdioma);
        return indexData;
    }

    public static IndexFile getDataIndexacion(NormativaDTO norm, DocumentoNormativaDTO doc, DocumentoTraduccion docIdioma, FicheroDTO ficheroDTO, String idioma, List<PathUA> pathUAs) {
        IndexFile indexData = new IndexFile();
        indexData.setCategoria(EnumCategoria.ROLSAC_NORMATIVA_DOCUMENTO);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setElementoId(doc.getCodigo().toString());
        indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_NORMATIVA);
        indexData.setElementoIdRaiz(norm.getCodigo().toString());
        indexData.setCategoriaPadre(EnumCategoria.ROLSAC_NORMATIVA);
        indexData.setElementoIdPadre(norm.getCodigo().toString());
        indexData.setUos(getUos(pathUAs));
        if (norm.getFechaBoletin() != null) {
            indexData.setFechaPublicacion(java.util.Date.from(norm.getFechaBoletin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        // Iteramos las traducciones
        final MultilangLiteral titulo = new MultilangLiteral();
        final MultilangLiteral descripcion = new MultilangLiteral();
        final MultilangLiteral descripcionPadre = new MultilangLiteral();
        final MultilangLiteral urls = new MultilangLiteral();
        final MultilangLiteral urlsPadre = new MultilangLiteral();

        final MultilangLiteral extension = new MultilangLiteral();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();
        String urlCAT = "";
        if (norm.getUrlBoletin() != null && !norm.getUrlBoletin().getTraduccion("ca").isEmpty()) {
            urlCAT = norm.getUrlBoletin().getTraduccion("ca");
        }
        // Recorremos las traducciones

        final EnumIdiomas enumIdioma = EnumIdiomas.fromString(idioma);
        if (enumIdioma != null) {
            //Para saltarse los idiomas sin titulo
            if (doc.getTitulo().getTraduccion(idioma) == null || doc.getTitulo().getTraduccion(idioma).isEmpty()) {
                return null;
            }

            //Anyadimos idioma al enumerado.
            idiomas.add(enumIdioma);

            // Seteamos los primeros campos multiidiomas: Titulo, Descripción y el search
            // text.
            titulo.addIdioma(enumIdioma, doc.getTitulo().getTraduccion(idioma));
            descripcion.addIdioma(enumIdioma, ficheroDTO.getFilename());
            descripcionPadre.addIdioma(enumIdioma, norm.getTitulo().getTraduccion(idioma));
            extension.addIdioma(enumIdioma, IndexacionUtil.calcularExtensionArchivo(ficheroDTO.getFilename()));
            indexData.setFileContent(ficheroDTO.getContenido());

            urls.addIdioma(enumIdioma, "/normativa/archivo.do?id=" + ficheroDTO.getCodigo() + "&lang=" + idioma);
            if (norm.getUrlBoletin() != null && !norm.getUrlBoletin().getTraduccion(idioma).isEmpty()) {
                urlsPadre.addIdioma(enumIdioma, norm.getUrlBoletin().getTraduccion(idioma));
            } else {
                //En caso de url vacio, cogemos la de catalan.
                urlsPadre.addIdioma(enumIdioma, urlCAT);
            }
        }


        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcion(descripcion);
        indexData.setUrl(urls);
        indexData.setUrlPadre(urlsPadre);
        indexData.setExtension(extension);
        indexData.setIdioma(enumIdioma);
        return indexData;
    }

    private static List<PathUO> getUos(List<PathUA> uas) {
        List<PathUO> pathUOs = new ArrayList<>();
        for (PathUA ua : uas) {
            PathUO uo = new PathUO();
            uo.setPath(ua.getPath());
            pathUOs.add(uo);
        }

        return pathUOs;
    }

    public static DataIndexacion getDataIndexacion(ProcedimientoDTO proc, PathUA pathUA) {
        DataIndexacion indexData = new DataIndexacion();
        indexData.setCategoria(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        indexData.setAplicacionId(EnumAplicacionId.ROLSAC);
        indexData.setElementoId(proc.getCodigo().toString());
        indexData.setCategoriaRaiz(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        indexData.setElementoIdRaiz(proc.getCodigo().toString());

        // Iteramos las traducciones
        final LiteralMultilang titulo = new LiteralMultilang();
        final LiteralMultilang descripcion = new LiteralMultilang();
        final LiteralMultilang urls = new LiteralMultilang();
        final LiteralMultilang searchText = new LiteralMultilang();
        final LiteralMultilang searchTextOptional = new LiteralMultilang();
        final List<EnumIdiomas> idiomas = new ArrayList<EnumIdiomas>();

        final String nomUnidadAministrativa;
        if (proc.getUaResponsable() == null) {
            nomUnidadAministrativa = "";
        } else {
            nomUnidadAministrativa = proc.getUaResponsable().getNombre().getTraduccion("ca");
        }

        final boolean esProcSerInterno = contienePOInterno(proc.getPublicosObjetivo());

        // Recorremos las traducciones
        for (final String keyIdioma : proc.getNombreProcedimientoWorkFlow().getIdiomas()) {
            final EnumIdiomas enumIdioma = EnumIdiomas.fromString(keyIdioma);
            if (enumIdioma != null) {

                if (proc.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma) == null || proc.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma).isEmpty()) {
                    continue;
                }

                // Anyadimos idioma al enumerado.
                idiomas.add(enumIdioma);

                // Seteamos los primeros campos multiidiomas: Titulo, Descripción y el search
                // text.
                titulo.addIdioma(enumIdioma, proc.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma));
                descripcion.addIdioma(enumIdioma, proc.getObjeto().getTraduccion(keyIdioma));
                searchText.addIdioma(enumIdioma, proc.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma) + " " + proc.getObjeto().getTraduccion(keyIdioma) + " " + proc.getKeywords());

                final StringBuffer textoOptional = new StringBuffer();

                // materia
                //for (final TipoMateriaSIAGridDTO materia : proc.getMateriasSIA()) {
                //    textoOptional.append(" ");
                //    textoOptional.append(materia.getCodigo());
                //    textoOptional.append(" ");
                //    textoOptional.append(materia.getIdentificador());
                //    textoOptional.append(" ");
                //    textoOptional.append(materia.getDescripcion().getTraduccion(keyIdioma));
                //}

                // Servicio Responsable
                if (proc.getUaResponsable() != null && proc.getUaResponsable().getNombre() != null && proc.getUaResponsable().getNombre().getTraduccion(keyIdioma) != null) {
                    textoOptional.append(" ");
                    textoOptional.append(proc.getUaResponsable().getNombre().getTraduccion(keyIdioma));
                }

                // Publico objetivo, para extraer el nombre del publico objetivo
                String nombrePubObjetivo = "persones";
                String idPublicoObjetivo = "200";

                if (proc.getPublicosObjetivo() != null && !proc.getPublicosObjetivo().isEmpty()) {
                    final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo = proc.getPublicosObjetivo().get(0);
                    nombrePubObjetivo = publicoObjectivo.getIdentificador().toLowerCase();
                    idPublicoObjetivo = publicoObjectivo.getCodigo().toString();
                }

                // UO
                if (proc.getUaInstructor() != null && proc.getUaInstructor().getNombre().getTraduccion(keyIdioma) != null) {
                    textoOptional.append(" ");
                    textoOptional.append(proc.getUaInstructor().getNombre().getTraduccion(keyIdioma));
                }

                // Nombre familia (No se necesita ya)
                //textoOptional.append(" ");
                //textoOptional.append(proc.getNombreFamilia());

                // Normativa asociadas
                for (final NormativaGridDTO normativa : proc.getNormativas()) {
                    textoOptional.append(normativa.getTitulo().getTraduccion(keyIdioma));
                    textoOptional.append(" ");
                }

                searchTextOptional.addIdioma(enumIdioma, /*traduccion.getResultat() + " " */
                        proc.getObservaciones().getTraduccion(keyIdioma) + " " + textoOptional.toString());

                if (esProcSerInterno) {
                    // Si es interno usamos la url especifica para los procedimientos internos

                    final String url = getPropiedadPOInternoUrlProc().replace("{idioma}", keyIdioma).replace("{idPublicoObjetivo}", idPublicoObjetivo).replace("{nombrePubObjetivo}", nombrePubObjetivo).replace("{idProcedimiento}", proc.getCodigo().toString());
                    urls.addIdioma(enumIdioma, url);

                } else {
                    // Si no es interno
                    urls.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPublicoObjetivo + "/" + nombrePubObjetivo + "/tramites/tramite/" + proc.getCodigo());
                }
            }
        }

        // Seteamos datos multidioma.
        indexData.setTitulo(titulo);
        indexData.setDescripcion(descripcion);
        indexData.setUrl(urls);
        indexData.setSearchText(searchText);
        indexData.setSearchTextOptional(searchTextOptional);
        indexData.setIdiomas(idiomas);

        // Datos IDs materias.
        final List<String> materiasId = new ArrayList<String>();
        //for (final TipoMateriaSIAGridDTO materia : proc.getMateriasSIA()) {
        //    materiasId.add(materia.getCodigo().toString());
        //}
        indexData.setMateriaId(materiasId);

        // Datos IDs publico Objetivos.
        final List<String> publicoObjetivoId = new ArrayList<String>();
        for (final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo : proc.getPublicosObjetivo()) {
            publicoObjetivoId.add(publicoObjectivo.getCodigo().toString());
        }
        indexData.setPublicoId(publicoObjetivoId);

        // Datos IDs de familia.
        //if (proc.getFamilia() != null) {
        //    indexData.setFamiliaId(proc.getFamilia().getId().toString());
        //}

        // Fechas
        indexData.setFechaActualizacion(proc.getFechaActualizacion());
        indexData.setFechaPublicacion(proc.getFechaPublicacion());
        indexData.setFechaCaducidad(proc.getFechaCaducidad());

        if (esProcSerInterno) {
            indexData.setInterno(true);
        } else {
            indexData.setInterno(false);
        }

        // UA
        if (pathUA == null) {
            //TODO, tiene que venir calculado
            //return new SolrPendienteResultado(true, "No se puede indexar: no cuelga de UA visible");
        }
        indexData.getUos().add(pathUA);

        final boolean telematico = IndexacionUtil.isTelematicoProcedimiento(proc.getTramites());
        indexData.setTelematico(telematico);

        final ProcedimientoTramiteDTO tramite = IndexacionUtil.getTramiteInicio(proc.getTramites());
        if (tramite != null) {
            indexData.setFechaPlazoIni(tramite.getFechaInicio());
            indexData.setFechaPlazoFin(tramite.getFechaCierre());
        }

        return indexData;
    }

    public static boolean contienePOInterno(List<TipoPublicoObjetivoEntidadGridDTO> publicosObjetivo) {
        boolean contiene = false;
        if (publicosObjetivo != null) {
            for (TipoPublicoObjetivoEntidadGridDTO tipo : publicosObjetivo) {
                if (tipo.isEmpleadoPublico()) {
                    contiene = true;
                }
            }
        }
        return contiene;
    }

    private static String getPropiedadPOInternoUrlProc() {
        return "";
    }

    private static LiteralMultilang getLiteral(Literal lit) {
        LiteralMultilang literal = new LiteralMultilang();
        if (lit == null) {
            return null;
        }
        for (Traduccion trad : lit.getTraducciones()) {
            literal.addIdioma(EnumIdiomas.fromString(trad.getIdioma()), trad.getLiteral());
        }
        return literal;
    }


}
