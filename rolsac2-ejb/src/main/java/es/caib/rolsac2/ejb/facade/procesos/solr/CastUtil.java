package es.caib.rolsac2.ejb.facade.procesos.solr;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.LiteralMultilang;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumAplicacionId;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;
import es.caib.rolsac2.service.model.*;

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
                    descripcionPadre.addIdioma(enumIdioma,
                            procedimiento.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma));
                }
                searchText.addIdioma(enumIdioma, tramite.getNombre().getTraduccion(keyIdioma) + " " + tramite.getObservacion().getTraduccion(keyIdioma));
                searchTextOptional.addIdioma(enumIdioma, tramite.getDocumentacion().getTraduccion(keyIdioma));

                if (procedimiento != null) {

                    String nombrePubObjetivox = "persones";
                    String idPublicoObjetivo = "200";

                    if (procedimiento.getPublicosObjetivo() != null && !procedimiento.getPublicosObjetivo().isEmpty()) {
                        final TipoPublicoObjetivoEntidadGridDTO publicoObjectivo = procedimiento.getPublicosObjetivo().get(0);
                        nombrePubObjetivox = publicoObjectivo.getIdentificador().toLowerCase();
                        idPublicoObjetivo = publicoObjectivo.getCodigo().toString();
                    }

                    urlsPadre.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPublicoObjetivo + "/"
                            + nombrePubObjetivox + "/tramites/tramite/" + procedimiento.getCodigo());
                    urls.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPublicoObjetivo + "/"
                            + nombrePubObjetivox + "/tramites/tramite/" + procedimiento.getCodigo());
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
        for (final TipoMateriaSIAGridDTO materia : procedimiento.getMateriasSIA()) {
            materiasId.add(materia.getCodigo().toString());
        }
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
                searchText.addIdioma(enumIdioma, servicio.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma) + " " + servicio.getObjeto().getTraduccion(keyIdioma));

                final StringBuffer textoOptional = new StringBuffer();

                // materia
                for (final TipoMateriaSIAGridDTO materia : servicio.getMateriasSIA()) {
                    textoOptional.append(" ");
                    textoOptional.append(materia.getCodigo());
                    textoOptional.append(" ");
                    textoOptional.append(materia.getIdentificador());
                    textoOptional.append(" ");
                    textoOptional.append(materia.getDescripcion().getTraduccion(keyIdioma));

                }

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
                if (servicio.getUaInstructor() != null
                        && servicio.getUaInstructor().getNombre().getTraduccion(keyIdioma) != null) {
                    textoOptional.append(" ");
                    textoOptional.append(servicio.getUaInstructor().getNombre().getTraduccion(keyIdioma));

                }

                // Normativa asociadas
                for (final NormativaGridDTO normativa : servicio.getNormativas()) {
                    textoOptional.append(normativa.getTitulo().getTraduccion(keyIdioma));
                    textoOptional.append(" ");

                }

                searchTextOptional.addIdioma(enumIdioma, servicio.getObjeto().getTraduccion(keyIdioma) + " "
                        + servicio.getObservaciones().getTraduccion(keyIdioma) + " " + textoOptional.toString());

                if (esProcSerInterno) {
                    // Si es interno usamos la url especifica para los Servicios internos

                    final String url = getPropiedadPOInternoUrlProc()
                            .replace("{idioma}", keyIdioma).replace("{idPublicoObjetivo}", idPubObjetivo)
                            .replace("{nombrePubObjetivo}", nombrePubObjetivo)
                            .replace("{idServicio}", servicio.getCodigo().toString());
                    urls.addIdioma(enumIdioma, url);

                } else {
                    // Si no es interno
                    urls.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPubObjetivo + "/"
                            + nombrePubObjetivo + "/tramites/servicio/" + servicio.getCodigo());
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
        for (final TipoMateriaSIAGridDTO materia : servicio.getMateriasSIA()) {
            materiasId.add(materia.getCodigo().toString());
        }
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
                searchText.addIdioma(enumIdioma, proc.getNombreProcedimientoWorkFlow().getTraduccion(keyIdioma) + " " + proc.getObjeto().getTraduccion(keyIdioma));

                final StringBuffer textoOptional = new StringBuffer();

                // materia
                for (final TipoMateriaSIAGridDTO materia : proc.getMateriasSIA()) {
                    textoOptional.append(" ");
                    textoOptional.append(materia.getCodigo());
                    textoOptional.append(" ");
                    textoOptional.append(materia.getIdentificador());
                    textoOptional.append(" ");
                    textoOptional.append(materia.getDescripcion().getTraduccion(keyIdioma));

                }

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
                if (proc.getUaInstructor() != null
                        && proc.getUaInstructor().getNombre().getTraduccion(keyIdioma) != null) {
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

                    final String url = getPropiedadPOInternoUrlProc()
                            .replace("{idioma}", keyIdioma).replace("{idPublicoObjetivo}", idPublicoObjetivo)
                            .replace("{nombrePubObjetivo}", nombrePubObjetivo)
                            .replace("{idProcedimiento}", proc.getCodigo().toString());
                    urls.addIdioma(enumIdioma, url);

                } else {
                    // Si no es interno
                    urls.addIdioma(enumIdioma, "/seucaib/" + keyIdioma + "/" + idPublicoObjetivo + "/"
                            + nombrePubObjetivo + "/tramites/tramite/" + proc.getCodigo());
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
        for (final TipoMateriaSIAGridDTO materia : proc.getMateriasSIA()) {
            materiasId.add(materia.getCodigo().toString());
        }
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

    private static boolean contienePOInterno(List<TipoPublicoObjetivoEntidadGridDTO> publicosObjetivo) {
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
