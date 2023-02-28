package es.caib.rolsac2.service.utils;

import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaIdioma;
import es.caib.rolsac2.service.model.auditoria.AuditoriaValorCampo;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Indra
 */

public final class AuditoriaUtil {


    private AuditoriaUtil() {
        super();
    }

    public static String getValor(Object objeto) {
        if (objeto == null) {
            return "nul";
        }
        if (objeto instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            String fecha = dateFormat.format((Date) objeto);
            return fecha;
        }
        if (objeto instanceof Boolean) {
            Boolean booleano = (Boolean) objeto;
            if (booleano) {
                return "true";
            } else {
                return "false";
            }
        }
        if (objeto instanceof UnidadAdministrativaDTO) {
            UnidadAdministrativaDTO ua = (UnidadAdministrativaDTO) objeto;
            if (ua.getNombre() != null) {
                if (ua.getNombre().getTraduccion("ca") != null) {
                    return ua.getNombre().getTraduccion("ca");
                } else if (ua.getNombre().getTraduccion("es") != null) {
                    return ua.getNombre().getTraduccion("es");
                } else {
                    return ua.getNombre().getTraduccion();
                }
            } else {
                return ua.getIdentificador();
            }
        }
        if (objeto instanceof TipoPublicoObjetivoEntidadGridDTO) {
            TipoPublicoObjetivoEntidadGridDTO tipo = (TipoPublicoObjetivoEntidadGridDTO) objeto;
            if (tipo.getDescripcion() != null) {
                if (tipo.getDescripcion().getTraduccion("ca") != null) {
                    return tipo.getDescripcion().getTraduccion("ca");
                } else if (tipo.getDescripcion().getTraduccion("es") != null) {
                    return tipo.getDescripcion().getTraduccion("es");
                } else {
                    return tipo.getDescripcion().getTraduccion();
                }
            } else {
                return tipo.getIdentificador();
            }
        }
        if (objeto instanceof TipoMateriaSIAGridDTO) {
            TipoMateriaSIAGridDTO tipo = (TipoMateriaSIAGridDTO) objeto;
            if (tipo.getDescripcion() != null) {
                if (tipo.getDescripcion().getTraduccion("ca") != null) {
                    return tipo.getDescripcion().getTraduccion("ca");
                } else if (tipo.getDescripcion().getTraduccion("es") != null) {
                    return tipo.getDescripcion().getTraduccion("es");
                } else {
                    return tipo.getDescripcion().getTraduccion();
                }
            } else {
                return tipo.getIdentificador();
            }
        }
        if (objeto instanceof TipoNormativaDTO) {
            TipoNormativaDTO tipo = (TipoNormativaDTO) objeto;
            if (tipo.getDescripcion() != null) {
                if (tipo.getDescripcion().getTraduccion("ca") != null) {
                    return tipo.getDescripcion().getTraduccion("ca");
                } else if (tipo.getDescripcion().getTraduccion("es") != null) {
                    return tipo.getDescripcion().getTraduccion("es");
                } else {
                    return tipo.getDescripcion().getTraduccion();
                }
            } else {
                return tipo.getIdentificador();
            }
        }
        if (objeto instanceof NormativaGridDTO) {
            NormativaGridDTO tipo = (NormativaGridDTO) objeto;
            if (tipo.getTitulo() != null) {
                if (tipo.getTitulo().getTraduccion("ca") != null) {
                    return tipo.getTitulo().getTraduccion("ca");
                } else if (tipo.getTitulo().getTraduccion("es") != null) {
                    return tipo.getTitulo().getTraduccion("es");
                } else {
                    return tipo.getTitulo().getTraduccion();
                }
            } else {
                return tipo.getIdString();
            }
        }
        if (objeto instanceof ProcedimientoDocumentoDTO) {
            ProcedimientoDocumentoDTO tipo = (ProcedimientoDocumentoDTO) objeto;
            if (tipo.getTitulo() != null) {
                if (tipo.getTitulo().getTraduccion("ca") != null) {
                    return tipo.getTitulo().getTraduccion("ca");
                } else if (tipo.getTitulo().getTraduccion("es") != null) {
                    return tipo.getTitulo().getTraduccion("es");
                } else {
                    return tipo.getTitulo().getTraduccion();
                }
            } else {
                return tipo.getCodigoString();
            }
        }
        if (objeto instanceof ProcedimientoTramiteDTO) {
            ProcedimientoTramiteDTO tipo = (ProcedimientoTramiteDTO) objeto;
            if (tipo.getNombre() != null) {
                if (tipo.getNombre().getTraduccion("ca") != null) {
                    return tipo.getNombre().getTraduccion("ca");
                } else if (tipo.getNombre().getTraduccion("es") != null) {
                    return tipo.getNombre().getTraduccion("es");
                } else {
                    return tipo.getNombre().getTraduccion();
                }
            } else {
                return tipo.getCodigoString();
            }
        }


        return objeto.toString();
    }


    public static void auditar(TypeProcedimientoEstado valorModificado, TypeProcedimientoEstado valorPublicado, List<AuditoriaCambio> cambios) {

        if (valorPublicado != null && valorModificado != null) {
            if (valorPublicado != valorModificado) {

                AuditoriaCambio cambio = new AuditoriaCambio();
                String idCampo = "auditoria.procedimiento.estado";
                cambio.setIdCampo(idCampo);

                final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();

                valorCampo.setValorAnterior(getTexto(valorPublicado));
                valorCampo.setValorNuevo(getTexto(valorModificado));

                cambio.getValoresModificados().add(valorCampo);

                cambios.add(cambio);
            }
        }
    }

    private static String getTexto(TypeProcedimientoEstado valor) {
        String retorno = null;
        if (valor != null) {
            switch (valor) {
                case PUBLICADO:
                    retorno = "Publicat";
                    break;
                case BORRADO:
                    retorno = "Esborrat";
                    break;
                case MODIFICACION:
                    retorno = "En modificació";
                    break;
                case PENDIENTE_BORRAR:
                    retorno = "Pendent esborrar";
                    break;
                case PENDIENTE_PUBLICAR:
                    retorno = "Pendent publicar";
                    break;
                case PENDIENTE_RESERVAR:
                    retorno = "Pendent reservar";
                    break;
                case RESERVA:
                    retorno = "Reservat";
                    break;
            }
        }
        return retorno;
    }

    /**
     * Compara dos valores String y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @param idioma
     * @return
     */

    public static final AuditoriaCambio auditarCampoCadena(final String valorPublicado, final String valorModificado, List<AuditoriaCambio> cambios, final String idCampo,
                                                           final AuditoriaIdioma idioma) {
        AuditoriaCambio cambio = null;

        if (valorPublicado != null && valorModificado != null) {
            if (!valorPublicado.equals(valorModificado)) {
                cambio = agregarAuditoriaValorCampo(idioma, valorPublicado, valorModificado, idCampo);
            }
        } else if (valorPublicado != null || valorModificado != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, getValor(valorPublicado), getValor(valorModificado), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
        return cambio;
    }

    /**
     * Compara dos valores String y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @return
     */

    public static final void auditar(final Literal valorPublicado, final Literal valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.estaVacio()) && (valorModificado == null || valorModificado.estaVacio())) {
            return;
        } else if ((valorPublicado != null && !valorPublicado.estaVacio() && valorModificado == null) || (valorPublicado == null && valorModificado != null && !valorModificado.estaVacio())) {

            if (valorPublicado != null) {
                for (Traduccion traduccion : valorPublicado.getTraducciones()) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(traduccion.getIdioma()), traduccion.getLiteral(), null, idCampo);
                    cambios.add(cambio);
                }
                return;
            }
            if (valorModificado != null) {
                for (Traduccion traduccion : valorModificado.getTraducciones()) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(traduccion.getIdioma()), "nul", traduccion.getLiteral(), idCampo);
                    cambios.add(cambio);
                }
                return;
            }
        } else {
            if (valorModificado.estaVacio() && valorPublicado.estaVacio()) {
                //Si los dos vacios, no hacer nada
                return;
            } else if (valorModificado.estaVacio()) {
                //Si el modificado esta vacío, añadir los cambios del publicado
                for (Traduccion traduccion : valorPublicado.getTraducciones()) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(traduccion.getIdioma()), traduccion.getLiteral(), null, idCampo);
                    cambios.add(cambio);
                }
            } else if (valorPublicado.estaVacio()) {
                //Si el modificado esta vacío, añadir los cambios del modificado
                for (Traduccion traduccion : valorModificado.getTraducciones()) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(traduccion.getIdioma()), "nul", traduccion.getLiteral(), idCampo);
                    cambios.add(cambio);
                }
            } else {
                //Sino ir comparando idiomas
                for (String idi : valorModificado.getIdiomas()) {
                    if (valorPublicado != null && !esIgual(valorPublicado.getTraduccion(idi), valorModificado.getTraduccion(idi))) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(idi), valorPublicado.getTraduccion(idi), valorModificado.getTraduccion(idi), idCampo);
                        cambios.add(cambio);
                    }
                }
            }
        }
    }

    /**
     * Una pequeña comparativa por si dos textos son iguales (que daba un poco por saco si era nulo o si estaba vacío)
     *
     * @param traduccion
     * @param traduccion2
     * @return
     */
    private static boolean esIgual(String traduccion, String traduccion2) {
        if ((traduccion == null || traduccion.isEmpty()) && (traduccion2 == null || traduccion2.isEmpty())) {
            return true;
        }
        return traduccion.equals(traduccion2);
    }


    /**
     * Compara dos valores TipoTramitacionDTO y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @return
     */

    public static final void auditar(final TipoTramitacionDTO valorPublicado, final TipoTramitacionDTO valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if (valorPublicado == null && valorModificado == null) {
            return;
        } else if ((valorPublicado != null && valorModificado == null) || (valorPublicado == null && valorModificado != null)) {

            if (valorPublicado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, valorPublicado.getTramiteId(), null, idCampo + ".remove");
                cambios.add(cambio);
            }
            if (valorModificado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, null, valorModificado.getTramiteId(), idCampo + ".add");
                cambios.add(cambio);
            }
        } else {

            if (valorPublicado.getCodigo() != null && valorModificado.getCodigo() != null && valorPublicado.getCodigo().compareTo(valorModificado.getCodigo()) != 0) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, valorPublicado.getCodigo().toString(), valorModificado.getCodigo().toString(), idCampo);
                cambios.add(cambio);
            } else {

                AuditoriaUtil.auditar(valorPublicado.getTramiteId(), valorModificado.getTramiteId(), cambios, idCampo + ".tramiteId");
                AuditoriaUtil.auditar(valorPublicado.getTramiteVersion(), valorModificado.getTramiteVersion(), cambios, idCampo + ".tramiteVersion");
                AuditoriaUtil.auditar(valorPublicado.getCodPlatTramitacion(), valorModificado.getCodPlatTramitacion(), cambios, idCampo + ".tramiteCodPlataforma");
                AuditoriaUtil.auditar(valorPublicado.getUrlTramitacion(), valorModificado.getUrlTramitacion(), cambios, idCampo + ".tramiteUrl");
                AuditoriaUtil.auditar(valorPublicado.getTramiteParametros(), valorModificado.getTramiteParametros(), cambios, idCampo + ".tramiteParametros");
            }
        }
    }


    /**
     * Compara dos valores TipoTramitacionDTO y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @return
     */

    public static final void auditar(final PlatTramitElectronicaDTO valorPublicado, final PlatTramitElectronicaDTO valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if (valorPublicado == null && valorModificado == null) {
            return;
        } else if ((valorPublicado != null && valorModificado == null) || (valorPublicado != null && valorModificado == null)) {

            if (valorPublicado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, valorPublicado.getIdentificador(), null, idCampo + ".remove");
                cambios.add(cambio);
            }
            if (valorModificado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, null, valorModificado.getIdentificador(), idCampo + ".add");
                cambios.add(cambio);
            }
        } else {

            AuditoriaUtil.auditar(valorPublicado.getIdentificador(), valorModificado.getIdentificador(), cambios, idCampo);

        }
    }

    /**
     * Compara dos valores Tramite y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @return
     */

    public static final void auditar(final ProcedimientoTramiteDTO valorPublicado, final ProcedimientoTramiteDTO valorModificado, List<AuditoriaCambio> cambiosPadre, final String idCampo) {


        if (valorPublicado == null && valorModificado == null) {
            return;
        } else if ((valorPublicado != null && valorModificado == null) || (valorPublicado != null && valorModificado == null)) {

            if (valorPublicado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, null, valorModificado.getNombre().getTraduccion("ca"), idCampo + ".remove");
                cambiosPadre.add(cambio);
            }
            if (valorModificado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, valorPublicado.getNombre().getTraduccion("ca"), null, idCampo + ".add");
                cambiosPadre.add(cambio);
            }
        } else {

            //Seccion datos
            ArrayList<AuditoriaCambio> cambios = new ArrayList();
            AuditoriaUtil.auditar(valorPublicado.getFechaPublicacion(), valorModificado.getFechaPublicacion(), cambios, "auditoria.tramite.fechaPublicacion");
            AuditoriaUtil.auditar(valorPublicado.getFechaInicio(), valorModificado.getFechaInicio(), cambios, "auditoria.tramite.fechaInicio");
            AuditoriaUtil.auditar(valorPublicado.getFechaCierre(), valorModificado.getFechaCierre(), cambios, "auditoria.tramite.fechaCierre");
            AuditoriaUtil.auditar(valorPublicado.getFase(), valorModificado.getFase(), cambios, "auditoria.tramite.fase");
            if (valorPublicado.getOrden() != null && valorModificado.getOrden() != null && valorPublicado.getOrden().compareTo(valorModificado.getOrden()) != 0) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(valorPublicado), "de " + valorPublicado.getOrden() + " a " + valorModificado.getOrden(), "auditoria.tramite.orden");
                cambios.add(cambio);
            }
            AuditoriaUtil.auditar(valorPublicado.getNombre(), valorModificado.getNombre(), cambios, "auditoria.tramite.nombre");
            AuditoriaUtil.auditar(valorPublicado.getRequisitos(), valorModificado.getRequisitos(), cambios, "auditoria.tramite.requisitos");
            AuditoriaUtil.auditar(valorPublicado.getDocumentacion(), valorModificado.getDocumentacion(), cambios, "auditoria.tramite.documentacion");
            AuditoriaUtil.auditar(valorPublicado.getTerminoMaximo(), valorModificado.getTerminoMaximo(), cambios, "auditoria.tramite.terminoMaximo");
            AuditoriaUtil.auditar(valorPublicado.getUnidadAdministrativa(), valorModificado.getUnidadAdministrativa(), cambios, "auditoria.tramite.unidadAdministrativa");
            AuditoriaUtil.auditar(valorPublicado.getObservacion(), valorModificado.getObservacion(), cambios, "auditoria.tramite.observacion");

            //Canales presentacion
            AuditoriaUtil.auditar(valorPublicado.getPlantillaSel(), valorModificado.getPlantillaSel(), cambios, "auditoria.tramite.plantillaSel");
            //AuditoriaUtil.auditar(valorPublicado.getTasaAsociada(), valorModificado.getTasaAsociada(), cambios, "auditoria.tramite.tasaAsociada");
            AuditoriaUtil.auditar(valorPublicado.getTipoTramitacion(), valorModificado.getTipoTramitacion(), cambios, "auditoria.tramite.tipoTramitacion");

            //Literal
            //AuditoriaUtil.auditar(valorPublicado.getPlantillaSel(), valorModificado.getPlantillaSel(), cambios, "auditoria.tramite.plantillaSel");


            //Relaciones
            AuditoriaUtil.auditarDocumentos(valorPublicado.getListaDocumentos(), valorModificado.getListaDocumentos(), cambios, "auditoria.tramite.listaDocumentos");
            AuditoriaUtil.auditarDocumentos(valorPublicado.getListaModelos(), valorModificado.getListaModelos(), cambios, "auditoria.tramite.listaModelos");

            if (!cambios.isEmpty()) {
                String elemento = getValor(valorModificado);
                for (AuditoriaCambio cambio : cambios) {
                    for (AuditoriaValorCampo valorModif : cambio.getValoresModificados()) {
                        valorModif.setElemento(elemento);
                    }
                    cambiosPadre.add(cambio);
                }
            }
        }
    }


    /**
     * Compara dos valores String y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @return
     */

    public static final void auditar(final DocumentoMultiIdioma valorPublicado, final DocumentoMultiIdioma valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if (valorPublicado == null && valorModificado == null) {
            return;
        } else if ((valorPublicado != null && valorModificado == null) || (valorPublicado != null && valorModificado == null)) {

            if (valorPublicado != null) {
                for (DocumentoTraduccion traduccion : valorPublicado.getTraducciones()) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(traduccion.getIdioma()), getTexto(traduccion), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null) {
                for (DocumentoTraduccion traduccion : valorModificado.getTraducciones()) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(traduccion.getIdioma()), null, getTexto(traduccion), idCampo + ".add");
                    cambios.add(cambio);
                }
            }
        } else {
            for (String idi : valorModificado.getIdiomas()) {
                if (valorPublicado != null && valorPublicado.getTraduccion(idi) != null && valorPublicado.getTraduccion(idi).compareTo(valorModificado.getTraduccion(idi)) != 0) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampo(AuditoriaIdioma.fromString(idi), getTexto(valorPublicado.getDocumentoTraduccion(idi)), getTexto(valorModificado.getDocumentoTraduccion(idi)), idCampo);
                    cambios.add(cambio);
                }
            }
        }
    }

    private static String getTexto(DocumentoTraduccion traduccion) {
        if (traduccion == null) {
            return "";
        }
        if (traduccion.getFicheroDTO() != null) {
            return traduccion.getFicheroDTO().getFilename();
        } else if (traduccion.getCodigo() != null) {
            return traduccion.getCodigo().toString();
        } else {
            return "";
        }

    }

    public static void auditarTipoPublico(final List<TipoPublicoObjetivoEntidadGridDTO> valorPublicado, final List<TipoPublicoObjetivoEntidadGridDTO> valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) {
            return;
        } else if (((valorPublicado != null && !valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) || ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado != null && !valorModificado.isEmpty()))) {
            if (valorPublicado != null && !valorPublicado.isEmpty()) {
                for (TipoPublicoObjetivoEntidadGridDTO tipo : valorPublicado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getValor(tipo), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null && !valorModificado.isEmpty()) {
                for (TipoPublicoObjetivoEntidadGridDTO tipo : valorModificado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getValor(tipo), idCampo + ".add");
                    cambios.add(cambio);
                }
            }
        } else {
            for (TipoPublicoObjetivoEntidadGridDTO tipo : valorModificado) {
                boolean existe = false;
                for (TipoPublicoObjetivoEntidadGridDTO tipoPublicado : valorPublicado) {
                    if (tipo.getCodigo().compareTo(tipoPublicado.getCodigo()) == 0) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getValor(tipo), idCampo + ".add");
                    cambios.add(cambio);
                }
            }

            for (TipoPublicoObjetivoEntidadGridDTO tipoNuevo : valorPublicado) {
                boolean existe = false;
                for (TipoPublicoObjetivoEntidadGridDTO tipo : valorModificado) {
                    if (tipo.getCodigo().compareTo(tipoNuevo.getCodigo()) == 0) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getValor(tipoNuevo), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
        }
    }

    public static void auditarMateriaSIA(final List<TipoMateriaSIAGridDTO> valorPublicado, final List<TipoMateriaSIAGridDTO> valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) {
            return;
        } else if (((valorPublicado != null && !valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) || ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado != null && !valorModificado.isEmpty()))) {
            if (valorPublicado != null && !valorPublicado.isEmpty()) {
                for (TipoMateriaSIAGridDTO tipo : valorPublicado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getValor(tipo), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null && !valorModificado.isEmpty()) {
                for (TipoMateriaSIAGridDTO tipo : valorModificado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getValor(tipo), idCampo + ".add");
                    cambios.add(cambio);
                }
            }
        } else {
            for (TipoMateriaSIAGridDTO tipo : valorModificado) {
                boolean existe = false;
                for (TipoMateriaSIAGridDTO tipoPublicado : valorPublicado) {
                    if (tipo.getCodigo().compareTo(tipoPublicado.getCodigo()) == 0) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getValor(tipo), idCampo + ".add");
                    cambios.add(cambio);
                }
            }

            for (TipoMateriaSIAGridDTO tipoNuevo : valorPublicado) {
                boolean existe = false;
                for (TipoMateriaSIAGridDTO tipo : valorModificado) {
                    if (tipo.getCodigo().compareTo(tipoNuevo.getCodigo()) == 0) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getValor(tipoNuevo), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
        }
    }

    public static void auditarNormativas(final List<NormativaGridDTO> valorPublicado, final List<NormativaGridDTO> valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) {
            return;
        } else if (((valorPublicado != null && !valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) || ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado != null && !valorModificado.isEmpty()))) {
            if (valorPublicado != null && !valorPublicado.isEmpty()) {
                for (NormativaGridDTO tipo : valorPublicado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getValor(tipo), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null && !valorModificado.isEmpty()) {
                for (NormativaGridDTO tipo : valorModificado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getValor(tipo), idCampo + ".add");
                    cambios.add(cambio);
                }
            }
        } else {
            if (valorModificado.size() >= valorPublicado.size()) {
                List<NormativaGridDTO> normativasAnteriores = new ArrayList<>();
                for (NormativaGridDTO tipo : valorModificado) {
                    boolean existe = false;
                    for (NormativaGridDTO tipoPublicado : valorPublicado) {
                        if (tipo.getCodigo().compareTo(tipoPublicado.getCodigo()) == 0) {
                            normativasAnteriores.add(tipoPublicado);
                            if (tipo.getOrden() != null && tipoPublicado.getOrden() != null && tipo.getOrden().compareTo(tipoPublicado.getOrden()) != 0) {
                                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(tipo), "de " + tipoPublicado.getOrden() + " a " + tipo.getOrden(), idCampo + ".orden");
                                cambios.add(cambio);
                            }
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getValor(tipo), idCampo + ".add");
                        cambios.add(cambio);
                    }
                }
                if (!normativasAnteriores.containsAll(valorPublicado)) {
                    for (NormativaGridDTO normativa : valorPublicado) {
                        if (!normativasAnteriores.contains(normativa)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getValor(normativa), null, idCampo + ".remove");
                            cambios.add(cambio);
                        }
                    }
                }
            } else {
                List<NormativaGridDTO> normativasNuevas = new ArrayList<>();
                for (NormativaGridDTO tipoAntiguo : valorPublicado) {
                    boolean existe = false;
                    for (NormativaGridDTO tipoNuevo : valorModificado) {
                        if (tipoNuevo.getCodigo().compareTo(tipoAntiguo.getCodigo()) == 0) {
                            normativasNuevas.add(tipoNuevo);
                            if (tipoNuevo.getOrden().compareTo(tipoAntiguo.getOrden()) != 0) {
                                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(tipoNuevo), "de " + tipoAntiguo.getOrden() + " a " + tipoNuevo.getOrden(), idCampo + ".orden");
                                cambios.add(cambio);
                            }
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getValor(tipoAntiguo), null, idCampo + ".remove");
                        cambios.add(cambio);
                    }
                }
                if (!normativasNuevas.containsAll(valorModificado)) {
                    for (NormativaGridDTO normativa : valorModificado) {
                        if (!normativasNuevas.contains(normativa)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getValor(normativa), idCampo + ".add");
                            cambios.add(cambio);
                        }
                    }
                }
            }
        }
    }

    public static void auditarUsuarios(final List<UsuarioGridDTO> valorPublicado, final List<UsuarioGridDTO> valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) {
            return;
        } else if (((valorPublicado != null && !valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) || ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado != null && !valorModificado.isEmpty()))) {
            if (valorPublicado != null && !valorPublicado.isEmpty()) {
                for (UsuarioGridDTO tipo : valorPublicado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tipo.getIdentificador(), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null && !valorModificado.isEmpty()) {
                for (UsuarioGridDTO tipo : valorModificado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tipo.getIdentificador(), idCampo + ".add");
                    cambios.add(cambio);
                }
            }
        } else {
            if (valorModificado.size() >= valorPublicado.size()) {
                List<UsuarioGridDTO> usuariosAnteriores = new ArrayList<>();
                for (UsuarioGridDTO tipo : valorModificado) {
                    boolean existe = false;
                    for (UsuarioGridDTO tipoPublicado : valorPublicado) {
                        if (tipo.getCodigo().compareTo(tipoPublicado.getCodigo()) == 0) {
                            usuariosAnteriores.add(tipoPublicado);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tipo.getIdentificador(), idCampo + ".add");
                        cambios.add(cambio);
                    }
                }
                if (!usuariosAnteriores.containsAll(valorPublicado)) {
                    for (UsuarioGridDTO usuario : valorPublicado) {
                        if (!usuariosAnteriores.contains(usuario)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, usuario.getIdentificador(), null, idCampo + ".remove");
                            cambios.add(cambio);
                        }
                    }
                }
            } else {
                List<UsuarioGridDTO> usuariosNuevos = new ArrayList<>();
                for (UsuarioGridDTO tipoAntiguo : valorPublicado) {
                    boolean existe = false;
                    for (UsuarioGridDTO tipoNuevo : valorModificado) {
                        if (tipoNuevo.getCodigo().compareTo(tipoAntiguo.getCodigo()) == 0) {
                            usuariosNuevos.add(tipoNuevo);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tipoAntiguo.getIdentificador(), null, idCampo + ".remove");
                        cambios.add(cambio);
                    }
                }
                if (!usuariosNuevos.containsAll(valorModificado)) {
                    for (UsuarioGridDTO usuario : valorModificado) {
                        if (!usuariosNuevos.contains(usuario)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, usuario.getIdentificador(), idCampo + ".add");
                            cambios.add(cambio);
                        }
                    }
                }
            }


        }
    }

    public static void auditarTemas(final List<TemaGridDTO> valorPublicado, final List<TemaGridDTO> valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) {
            return;
        } else if (((valorPublicado != null && !valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) || ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado != null && !valorModificado.isEmpty()))) {
            if (valorPublicado != null && !valorPublicado.isEmpty()) {
                for (TemaGridDTO tipo : valorPublicado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tipo.getIdentificador(), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null && !valorModificado.isEmpty()) {
                for (TemaGridDTO tipo : valorModificado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tipo.getIdentificador(), idCampo + ".add");
                    cambios.add(cambio);
                }
            }
        } else {
            if (valorModificado.size() >= valorPublicado.size()) {
                List<TemaGridDTO> temasAnteriores = new ArrayList<>();
                for (TemaGridDTO tipo : valorModificado) {
                    boolean existe = false;
                    for (TemaGridDTO tipoPublicado : valorPublicado) {
                        if (tipo.getCodigo().compareTo(tipoPublicado.getCodigo()) == 0) {
                            temasAnteriores.add(tipoPublicado);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tipo.getIdentificador(), idCampo + ".add");
                        cambios.add(cambio);
                    }
                }
                if (!temasAnteriores.containsAll(valorPublicado)) {
                    for (TemaGridDTO tema : valorPublicado) {
                        if (!temasAnteriores.contains(tema)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tema.getIdentificador(), null, idCampo + ".remove");
                            cambios.add(cambio);
                        }
                    }
                }
            } else {
                List<TemaGridDTO> temasNuevos = new ArrayList<>();
                for (TemaGridDTO tipoAntiguo : valorPublicado) {
                    boolean existe = false;
                    for (TemaGridDTO tipoNuevo : valorModificado) {
                        if (tipoNuevo.getCodigo().compareTo(tipoAntiguo.getCodigo()) == 0) {
                            temasNuevos.add(tipoNuevo);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tipoAntiguo.getIdentificador(), null, idCampo + ".remove");
                        cambios.add(cambio);
                    }
                }
                if (!temasNuevos.containsAll(valorModificado)) {
                    for (TemaGridDTO tema : valorModificado) {
                        if (!temasNuevos.contains(tema)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tema.getIdentificador(), idCampo + ".add");
                            cambios.add(cambio);
                        }
                    }
                }
            }

        }
    }

    /**
     * Compara dos valores String y determina si se crea una entrada en la auditoria.
     *
     * @param valorPublicado
     * @param valorModificado
     * @param idCampo
     * @return
     */

    public static final void auditar(final ProcedimientoDocumentoDTO valorPublicado, final ProcedimientoDocumentoDTO valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if (valorPublicado == null && valorModificado == null) {
            return;
        } else if ((valorPublicado != null && valorModificado == null) || (valorPublicado != null && valorModificado == null)) {

            if (valorPublicado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, valorPublicado.getTitulo().getTraduccion(), null, idCampo + ".remove");
                cambios.add(cambio);
            }
            if (valorModificado != null) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, null, valorModificado.getTitulo().getTraduccion(), idCampo + ".add");
                cambios.add(cambio);
            }
        } else {

            if (valorPublicado.getOrden() != null && valorPublicado.getOrden().compareTo(valorModificado.getOrden()) != 0) {
                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(valorPublicado), "de " + valorPublicado.getOrden() + " a " + valorModificado.getOrden(), idCampo + ".orden");
                cambios.add(cambio);
            }
            auditar(valorPublicado.getTitulo(), valorModificado.getTitulo(), cambios, idCampo + ".titulo");
            auditar(valorPublicado.getDescripcion(), valorModificado.getDescripcion(), cambios, idCampo + ".descripcion");
            auditar(valorPublicado.getUrl(), valorModificado.getUrl(), cambios, idCampo + ".url");
            auditar(valorPublicado.getDocumentos(), valorModificado.getDocumentos(), cambios, idCampo + ".documento");
        }
    }

    public static void auditarDocumentos(final List<ProcedimientoDocumentoDTO> valorPublicado, final List<ProcedimientoDocumentoDTO> valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) {
            return;
        } else if (((valorPublicado != null && !valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) || ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado != null && !valorModificado.isEmpty()))) {
            if (valorPublicado != null && !valorPublicado.isEmpty()) {
                for (ProcedimientoDocumentoDTO tipo : valorPublicado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getTexto(tipo), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null && !valorModificado.isEmpty()) {
                for (ProcedimientoDocumentoDTO tipo : valorModificado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getTexto(tipo), idCampo + ".add");
                    cambios.add(cambio);
                }
            }
        } else {
            if (valorModificado.size() >= valorPublicado.size()) {
                List<ProcedimientoDocumentoDTO> documentosAnteriores = new ArrayList<>();
                for (ProcedimientoDocumentoDTO tipo : valorModificado) {
                    boolean existe = false;
                    for (ProcedimientoDocumentoDTO tipoPublicado : valorPublicado) {
                        if (tipo.getCodigo() != null && tipo.getCodigo().compareTo(tipoPublicado.getCodigo()) == 0) {
                            documentosAnteriores.add(tipoPublicado);
                            if (tipo.getOrden() != null && tipoPublicado.getOrden() != null && tipo.getOrden().compareTo(tipoPublicado.getOrden()) != 0) {
                                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(tipo), "de " + tipoPublicado.getOrden() + " a " + tipo.getOrden(), idCampo + ".orden");
                                cambios.add(cambio);
                            }
                            auditar(tipoPublicado, tipo, cambios, idCampo);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getTexto(tipo), idCampo + ".add");
                        cambios.add(cambio);
                    }
                }
                if (!documentosAnteriores.containsAll(valorPublicado)) {
                    for (ProcedimientoDocumentoDTO documento : valorPublicado) {
                        if (!documentosAnteriores.contains(documento)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getTexto(documento), null, idCampo + ".remove");
                            cambios.add(cambio);
                        }
                    }
                }
            } else {
                List<ProcedimientoDocumentoDTO> documentosNuevos = new ArrayList<>();
                for (ProcedimientoDocumentoDTO tipoAntiguo : valorPublicado) {
                    boolean existe = false;
                    for (ProcedimientoDocumentoDTO tipoNuevo : valorModificado) {
                        if (tipoNuevo.getCodigo() != null && tipoNuevo.getCodigo().compareTo(tipoAntiguo.getCodigo()) == 0) {
                            documentosNuevos.add(tipoNuevo);
                            if (tipoNuevo.getOrden().compareTo(tipoAntiguo.getOrden()) != 0) {
                                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(tipoNuevo), "de " + tipoAntiguo.getOrden() + " a " + tipoNuevo.getOrden(), idCampo + ".orden");
                                cambios.add(cambio);
                            }
                            auditar(tipoAntiguo, tipoNuevo, cambios, idCampo);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, getTexto(tipoAntiguo), null, idCampo + ".remove");
                        cambios.add(cambio);
                    }
                }
                if (!documentosNuevos.containsAll(valorModificado)) {
                    for (ProcedimientoDocumentoDTO documento : valorModificado) {
                        if (!documentosNuevos.contains(documento)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, getTexto(documento), idCampo + ".add");
                            cambios.add(cambio);
                        }
                    }
                }
            }
        }
    }

    public static String getTexto(ProcedimientoDocumentoDTO doc) {
        if (doc.getTitulo() != null) {
            return doc.getTitulo().getTraduccion("ca");
        } else if (doc.getDocumentos() != null && doc.getDocumentos().getTraduccion("ca") != null) {
            return doc.getDocumentos().getTraduccion("ca").getFilename();
        } else if (doc.getCodigoString() != null) {
            return doc.getCodigoString();
        } else {
            return "";
        }
    }

    public static void auditarTramites(final List<ProcedimientoTramiteDTO> valorPublicado, final List<ProcedimientoTramiteDTO> valorModificado, List<AuditoriaCambio> cambios, final String idCampo) {


        if ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) {
            return;
        } else if (((valorPublicado != null && !valorPublicado.isEmpty()) && (valorModificado == null || valorModificado.isEmpty())) || ((valorPublicado == null || valorPublicado.isEmpty()) && (valorModificado != null && !valorModificado.isEmpty()))) {
            if (valorPublicado != null && !valorPublicado.isEmpty()) {
                for (ProcedimientoTramiteDTO tipo : valorPublicado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tipo.getCodigo().toString(), null, idCampo);
                    cambios.add(cambio);
                }
            }
            if (valorModificado != null && !valorModificado.isEmpty()) {
                for (ProcedimientoTramiteDTO tipo : valorModificado) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tipo.getNombre().getTraduccion("ca"), idCampo);
                    cambios.add(cambio);
                }
            }
        } else {
            if (valorModificado.size() >= valorPublicado.size()) {
                List<ProcedimientoTramiteDTO> tramitesAnteriores = new ArrayList<>();
                for (ProcedimientoTramiteDTO tipo : valorModificado) {
                    boolean existe = false;
                    for (ProcedimientoTramiteDTO tipoPublicado : valorPublicado) {
                        if (tipo.getCodigo() != null && tipo.getCodigo().compareTo(tipoPublicado.getCodigo()) == 0) {
                            tramitesAnteriores.add(tipoPublicado);
                            if (tipo.getOrden() != null && tipoPublicado.getOrden() != null && tipo.getOrden().compareTo(tipoPublicado.getOrden()) != 0) {
                                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(tipo), "de " + tipoPublicado.getOrden() + " a " + tipo.getOrden(), idCampo + ".orden");
                                cambios.add(cambio);
                            }
                            auditar(tipoPublicado, tipo, cambios, idCampo);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tipo.getNombre().getTraduccion("ca"), idCampo + ".add");
                        cambios.add(cambio);
                    }
                }
                if (!tramitesAnteriores.containsAll(valorPublicado)) {
                    for (ProcedimientoTramiteDTO tramite : valorPublicado) {
                        if (!tramitesAnteriores.contains(tramite)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tramite.getNombre().getTraduccion("ca"), null, idCampo + ".remove");
                            cambios.add(cambio);
                        }
                    }
                }
            } else {
                List<ProcedimientoTramiteDTO> tramitesNuevos = new ArrayList<>();
                for (ProcedimientoTramiteDTO tipoAntiguo : valorPublicado) {
                    boolean existe = false;
                    for (ProcedimientoTramiteDTO tipoNuevo : valorModificado) {
                        if (tipoNuevo.getCodigo() != null && tipoNuevo.getCodigo().compareTo(tipoAntiguo.getCodigo()) == 0) {
                            tramitesNuevos.add(tipoNuevo);
                            if (tipoNuevo.getOrden().compareTo(tipoAntiguo.getOrden()) != 0) {
                                AuditoriaCambio cambio = agregarAuditoriaValorCampo(null, getValor(tipoNuevo), "de " + tipoAntiguo.getOrden() + " a " + tipoNuevo.getOrden(), idCampo + ".orden");
                                cambios.add(cambio);
                            }
                            auditar(tipoAntiguo, tipoNuevo, cambios, idCampo);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tipoAntiguo.getNombre().getTraduccion("ca"), null, idCampo + ".remove");
                        cambios.add(cambio);
                    }
                }
                if (!tramitesNuevos.containsAll(valorModificado)) {
                    for (ProcedimientoTramiteDTO tramite : valorModificado) {
                        if (!tramitesNuevos.contains(tramite)) {
                            AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, null, tramite.getNombre().getTraduccion("ca"), idCampo + ".add");
                            cambios.add(cambio);
                        }
                    }
                }
            }

            /*for (ProcedimientoTramiteDTO tipoNuevo : valorModificado) {
                boolean existe = false;
                for (ProcedimientoTramiteDTO tipo : valorPublicado) {
                    if (tipo.getCodigo().compareTo(tipoNuevo.getCodigo()) == 0) {
                        existe = true;
                        auditar(tipo, tipoNuevo, cambios, idCampo);
                        break;
                    }
                }

                if (!existe) {
                    AuditoriaCambio cambio = agregarAuditoriaValorCampoSinNulo(null, tipoNuevo.getNombre().getTraduccion("ca"), null, idCampo + ".remove");
                    cambios.add(cambio);
                }
            }*/
        }
    }

    public static void auditar(Date fechaAnterior, Date fechaNueva, List<AuditoriaCambio> cambios, String idCampo) {
        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (fechaAnterior != null && fechaNueva != null) {
            if (fechaAnterior.compareTo(fechaNueva) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, fechaAnterior, fechaNueva, idCampo);
            }
        } else if (fechaAnterior != null || fechaNueva != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, fechaAnterior, fechaNueva, idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(String textoAnterior, String textoNuevo, List<AuditoriaCambio> cambios, String idCampo) {
        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (textoAnterior != null && textoNuevo != null) {
            if (!textoAnterior.equals(textoNuevo)) {
                cambio = agregarAuditoriaValorCampo(idioma, textoAnterior, textoNuevo, idCampo);
            }
        } else if (textoAnterior != null || textoNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, textoAnterior, textoNuevo, idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(Integer valorAnterior, Integer valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {
        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.toString(), valorNuevo.toString(), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.toString(), valorNuevo == null ? "nul" : valorNuevo.toString(), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(Boolean valorAnterior, Boolean valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {
        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.toString(), valorNuevo.toString(), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.toString(), valorNuevo == null ? "nul" : valorNuevo.toString(), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }


    public static void auditar(TipoSilencioAdministrativoDTO valorAnterior, TipoSilencioAdministrativoDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {
        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo == null ? "nul" : valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(TipoProcedimientoDTO valorAnterior, TipoProcedimientoDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {

        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo == null ? "nul" : valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(UnidadAdministrativaDTO valorAnterior, UnidadAdministrativaDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {

        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, getValor(valorAnterior), getValor(valorNuevo), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, getValor(valorAnterior), getValor(valorNuevo), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(TipoFormaInicioDTO valorAnterior, TipoFormaInicioDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {

        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo == null ? "nul" : valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(TipoViaDTO valorAnterior, TipoViaDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {

        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo == null ? "nul" : valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(TipoLegitimacionDTO valorAnterior, TipoLegitimacionDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {

        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo == null ? "nul" : valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(TipoSexoDTO valorAnterior, TipoSexoDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {
        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo == null ? "nul" : valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    public static void auditar(TipoUnidadAdministrativaDTO valorAnterior, TipoUnidadAdministrativaDTO valorNuevo, List<AuditoriaCambio> cambios, String idCampo) {
        AuditoriaCambio cambio = null;
        AuditoriaIdioma idioma = null;

        if (valorAnterior != null && valorNuevo != null) {
            if (valorAnterior.compareTo(valorNuevo) != 0) {
                cambio = agregarAuditoriaValorCampo(idioma, valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);
            }
        } else if (valorAnterior != null || valorNuevo != null) {
            cambio =
                    agregarAuditoriaValorCampo(idioma, valorAnterior == null ? "nul" : valorAnterior.getDescripcion().getTraduccion("ca"), valorNuevo == null ? "nul" : valorNuevo.getDescripcion().getTraduccion("ca"), idCampo);

        }

        if (cambio != null) {
            cambios.add(cambio);
        }
    }

    private static AuditoriaCambio agregarAuditoriaValorCampo(final AuditoriaIdioma idioma, final Date valorAnterior, final Date valorNuevo, final String idCampo) {
        AuditoriaCambio cambio = new AuditoriaCambio();
        cambio.setIdCampo(idCampo);

        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        valorCampo.setIdioma(idioma);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        if (valorAnterior == null) {
            valorCampo.setValorAnterior("nul");
        } else {
            valorCampo.setValorAnterior(dateFormat.format(valorAnterior));
        }
        if (valorNuevo == null) {
            valorCampo.setValorNuevo("nul");
        } else {
            valorCampo.setValorNuevo(dateFormat.format(valorNuevo));
        }

        cambio.getValoresModificados().add(valorCampo);

        return cambio;
    }

    private static AuditoriaCambio agregarAuditoriaValorCampo(final AuditoriaIdioma idioma, final FicheroDTO valorAnterior, final FicheroDTO valorNuevo, final String idCampo) {
        AuditoriaCambio cambio = new AuditoriaCambio();
        cambio.setIdCampo(idCampo);

        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        valorCampo.setIdioma(idioma);
        if (valorAnterior == null) {
            valorCampo.setValorAnterior("nul");
        } else {
            valorCampo.setValorAnterior(getValor(valorAnterior));
        }
        if (valorNuevo == null) {
            valorCampo.setValorNuevo("nul");
        } else {
            valorCampo.setValorNuevo(getValor(valorNuevo));
        }

        cambio.getValoresModificados().add(valorCampo);

        return cambio;
    }

    private static AuditoriaCambio agregarAuditoriaValorCampo(final AuditoriaIdioma idioma, final String valorAnterior, final String valorNuevo, final String idCampo) {
        AuditoriaCambio cambio = new AuditoriaCambio();
        cambio.setIdCampo(idCampo);

        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        valorCampo.setIdioma(idioma);
        valorCampo.setValorAnterior(getValor(valorAnterior));
        valorCampo.setValorNuevo(getValor(valorNuevo));

        cambio.getValoresModificados().add(valorCampo);

        return cambio;
    }

    private static AuditoriaCambio agregarAuditoriaValorCampoSinNulo(final AuditoriaIdioma idioma, final String valorAnterior, final String valorNuevo, final String idCampo) {
        AuditoriaCambio cambio = new AuditoriaCambio();
        cambio.setIdCampo(idCampo);

        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        valorCampo.setIdioma(idioma);
        if (valorAnterior != null) {
            valorCampo.setValorAnterior(getValor(valorAnterior));
        }
        if (valorNuevo != null) {
            valorCampo.setValorNuevo(getValor(valorNuevo));
        }
        cambio.getValoresModificados().add(valorCampo);

        return cambio;
    }
}

