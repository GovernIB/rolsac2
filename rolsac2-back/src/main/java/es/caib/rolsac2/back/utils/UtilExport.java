package es.caib.rolsac2.back.utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.exportar.ExportarCampos;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.types.TypeExportarFormato;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UtilExport {

    public static final String PARAMETRO_EXPORTAR = "exportar";
    public static final String PARAMETRO_EXPORTAR_TIPO = "tipo";

    public static final String getValor(Object valor, String idioma) {
        if (valor == null) {
            return "";
        }

        if (valor instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(valor);
        }

        if (valor instanceof UnidadAdministrativaDTO) {
            Literal nombre = ((UnidadAdministrativaDTO) valor).getNombre();
            if (nombre == null) {
                return "";
            }
            if (nombre.getTraduccion(idioma) == null) {
                return nombre.getTraduccion();
            } else {
                return nombre.getTraduccion(idioma);
            }
        }

        if (valor instanceof Boolean) {
            return (boolean) valor ? "Sí" : "No";
        }

        if (valor instanceof Literal) {
            String valorLiteral;
            if (idioma == null) {
                valorLiteral = ((Literal) valor).getTraduccion();
            } else {
                valorLiteral = ((Literal) valor).getTraduccion(idioma);
            }
            return (valorLiteral == null) ? "" : valorLiteral;
        }

        return valor.toString();
    }

    public static final String getValor(Object valor, Object valor2, String idioma, boolean tieneDosWF, boolean prioridadPublicado) {
        //El caracter separador de wf solo se mostrará si de verdad tiene relleno bien los 2 wf.
        String caracterSeparador = mostrarSeparador(tieneDosWF);
        if (valor == null && valor2 == null) {
            return "";
        }

        if ((valor != null && valor instanceof Date) || (valor2 != null && valor2 instanceof Date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            if (valor == null) {
                if (valor2 == null) {
                    return "";
                } else {
                    return caracterSeparador + sdf.format(valor2);
                }

            } else {
                if (valor2 == null) {
                    return sdf.format(valor) + caracterSeparador;
                } else {
                    if (sdf.format(valor).equals(sdf.format(valor2))) {
                        return sdf.format(valor);
                    } else {
                        //Solo mostramos la barra si son fechas diferentes
                        return sdf.format(valor) + caracterSeparador + sdf.format(valor2);
                    }
                }
            }
        }

        if ((valor != null && valor instanceof UnidadAdministrativaDTO) || (valor2 != null && valor2 instanceof UnidadAdministrativaDTO)) {


            Literal literal = null;
            Literal literal2 = null;
            String nombre = null;
            String nombre2 = null;
            if (valor != null) {
                literal = ((UnidadAdministrativaDTO) valor).getNombre();
            }

            if (valor2 != null) {
                literal2 = ((UnidadAdministrativaDTO) valor2).getNombre();
            }

            if (literal == null && literal2 == null) {
                return "";
            }

            if (literal != null) {
                if (literal.getTraduccion(idioma) == null) {
                    nombre = literal.getTraduccion();
                } else {
                    nombre = literal.getTraduccion(idioma);
                }
            }

            if (literal2 != null) {
                if (literal2.getTraduccion(idioma) == null) {
                    nombre2 = literal2.getTraduccion();
                } else {
                    nombre2 = literal2.getTraduccion(idioma);
                }
            }

            if (nombre == null) {
                if (nombre2 == null) {
                    return "";
                } else {
                    return caracterSeparador + nombre2;
                }
            } else {
                if (valor2 == null) {
                    return nombre + caracterSeparador;
                } else {
                    if (nombre.equals(nombre2)) {
                        return nombre;
                    } else {
                        //Solo mostramos la barra si son fechas diferentes
                        return nombre + caracterSeparador + nombre2;
                    }
                }
            }
        }

        if ((valor != null && valor instanceof Boolean) || (valor2 != null && valor2 instanceof Boolean)) {

            if (!tieneDosWF) {
                //Si no tiene dos wf, solo hay que mostrar un dato
                Boolean valorBoolean;
                if (prioridadPublicado) {
                    valorBoolean = (boolean) valor;
                } else {
                    valorBoolean = (boolean) valor2;
                }

                if (valorBoolean == null) {
                    return "";
                } else {
                    return valorBoolean ? "Sí" : "No";
                }
            }

            if (valor == null) {
                if (valor2 == null) {
                    return "";
                } else {
                    return caracterSeparador + ((Boolean) valor2 ? "Sí" : "No");
                }
            } else {
                if (valor2 == null) {
                    return ((Boolean) valor ? "Sí" : "No") + caracterSeparador;
                } else {
                    if (((Boolean) valor) == ((Boolean) valor2)) {
                        return ((Boolean) valor ? "Sí" : "No");
                    } else {
                        //Solo mostramos la barra si son fechas diferentes
                        return ((Boolean) valor ? "Sí" : "No") + caracterSeparador + ((Boolean) valor2 ? "Sí" : "No");
                    }
                }
            }
        }

        if ((valor != null && valor instanceof Literal) || (valor2 != null && valor2 instanceof Literal)) {
            Literal literal = null;
            Literal literal2 = null;
            String nombre = null;
            String nombre2 = null;
            if (valor != null) {
                literal = ((Literal) valor);
            }
            if (valor2 != null) {
                literal2 = ((Literal) valor2);
            }
            if (literal != null) {
                if (literal.getTraduccion(idioma) == null) {
                    nombre = literal.getTraduccion();
                } else {
                    nombre = literal.getTraduccion(idioma);
                }
            }

            if (literal2 != null) {
                if (literal2.getTraduccion(idioma) == null) {
                    nombre2 = literal2.getTraduccion();
                } else {
                    nombre2 = literal2.getTraduccion(idioma);
                }
            }

            if (nombre == null) {
                if (nombre2 == null) {
                    return "";
                } else {
                    return caracterSeparador + nombre2;
                }
            } else {
                if (valor2 == null) {
                    return nombre + caracterSeparador;
                } else {
                    if (nombre.equals(nombre2)) {
                        return nombre;
                    } else {
                        //Solo mostramos la barra si son fechas diferentes
                        return nombre + caracterSeparador + nombre2;
                    }
                }
            }
        }

        if (valor == null) {
            if (valor2 == null) {
                return "";
            } else {
                return caracterSeparador + valor2;
            }
        } else {
            if (valor2 == null) {
                return valor + caracterSeparador;
            } else {
                if (valor.equals(valor2)) {
                    return valor.toString();
                } else {
                    //Solo mostramos la barra si son fechas diferentes
                    return valor + caracterSeparador + valor2;
                }
            }
        }
    }

    /**
     * Método que dependiendo de si tieneDosWF o no, devuelve el separador \ o sólo caracter vacío.
     *
     * @param tieneDosWF
     * @return
     */
    private static String mostrarSeparador(boolean tieneDosWF) {
        //Método que dependiendo de si tieneDosWF o no, devuelve el separador \ o sólo caracter vacío.
        return tieneDosWF ? " \\ " : "";
    }

    /**
     * Convierte los valores de una lista de uas en un array de String
     *
     * @param uas
     * @param exportarDatos
     * @param idioma
     * @return
     */
    public static String[][] getValoresUAs(List<UnidadAdministrativaDTO> uas, ExportarDatos exportarDatos, String idioma) {

        String[][] retorno = new String[uas.size()][exportarDatos.getCampos().size()];
        int fila = 0;
        for (UnidadAdministrativaDTO ua : uas) {
            int columna = 0;

            for (ExportarCampos exp : exportarDatos.getCampos()) {


                switch (exp.getCampo()) {
                    case "codigo":
                        retorno[fila][columna] = UtilExport.getValor(ua.getCodigo(), idioma);
                        break;
                    case "identificador":
                        retorno[fila][columna] = UtilExport.getValor(ua.getIdentificador(), idioma);
                        break;
                    case "codigoDIR3":
                        retorno[fila][columna] = UtilExport.getValor(ua.getCodigoDIR3(), idioma);
                        break;
                    case "nombreCat":
                        retorno[fila][columna] = UtilExport.getValor(ua.getNombre(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "nombreEsp":
                        retorno[fila][columna] = UtilExport.getValor(ua.getNombre(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "tipo":
                        if (ua.getTipo() == null) {
                            retorno[fila][columna] = "";
                        } else {
                            retorno[fila][columna] = UtilExport.getValor(ua.getTipo().getDescripcion(), Constantes.IDIOMA_ESPANYOL);
                        }
                        break;
                    case "nombrePadre":
                        if (ua.getPadre() == null) {
                            retorno[fila][columna] = "";
                        } else {
                            retorno[fila][columna] = UtilExport.getValor(ua.getPadre().getNombre(), Constantes.IDIOMA_ESPANYOL);
                        }
                        break;
                    case "orden":
                        retorno[fila][columna] = UtilExport.getValor(ua.getOrden(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "version":
                        retorno[fila][columna] = UtilExport.getValor(ua.getVersion(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "abreviatura":
                        retorno[fila][columna] = UtilExport.getValor(ua.getAbreviatura(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "url":
                        retorno[fila][columna] = UtilExport.getValor(ua.getUrl(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "presentacion":
                        retorno[fila][columna] = UtilExport.getValor(ua.getPresentacion(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "responsableNombre":
                        retorno[fila][columna] = UtilExport.getValor(ua.getResponsableNombre(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "responsableEmail":
                        retorno[fila][columna] = UtilExport.getValor(ua.getResponsableEmail(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "responsableSexo":
                        if (ua.getResponsableSexo() == null) {
                            retorno[fila][columna] = "";
                        } else {
                            retorno[fila][columna] = UtilExport.getValor(ua.getResponsableSexo().getDescripcion(), Constantes.IDIOMA_ESPANYOL);
                        }
                        break;
                    case "contactoTelf":
                        retorno[fila][columna] = UtilExport.getValor(ua.getTelefono(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "contactoFax":
                        retorno[fila][columna] = UtilExport.getValor(ua.getFax(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "contactoEmail":
                        retorno[fila][columna] = UtilExport.getValor(ua.getEmail(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "contactoDominio":
                        retorno[fila][columna] = UtilExport.getValor(ua.getDominio(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    default:
                        retorno[fila][columna] = "";
                        break;
                }

                columna++;
            }
            fila++;
        }
        return retorno;
    }

    /**
     * Convierte los valores de una lista de procs en un array de String
     *
     * @param procs
     * @param exportarDatos
     * @param idioma
     * @return
     */
    public static String[][] getValoresServs(List<ProcedimientoBaseDTO> procs, ExportarDatos exportarDatos, String idioma) {

        String[][] retorno = new String[procs.size()][exportarDatos.getCampos().size()];
        int fila = 0;
        for (ProcedimientoBaseDTO dato : procs) {
            ServicioDTO servicioDTO = (ServicioDTO) dato;
            int columna = 0;
            for (ExportarCampos exp : exportarDatos.getCampos()) {
                switch (exp.getCampo()) {
                    case "codigo":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getCodigo(), idioma);
                        break;
                    case "codigoWF":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getCodigoWF(), idioma);
                        break;
                    case "codigoSIA":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getCodigoSIA(), idioma);
                        break;
                    case "estadoSIA":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getEstadoSIA(), idioma);
                        break;
                    case "fechaSIA":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getFechaSIA(), idioma);
                        break;
                    case "fechaPub":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getFechaPublicacion(), idioma);
                        break;
                    case "fechaDespub":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getFechaCaducidad(), idioma);
                        break;
                    case "estado":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getEstado().toString(), idioma);
                        break;
                    case "visibilidad":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.esVisible(), idioma);
                        break;
                    /*case "pendienteValidar":
                        sb.append(UtilExport.getValor(servicioDTO.isPendienteIndexar(),idioma));
                        break;*/
                    case "nombreCat":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getNombreProcedimientoWorkFlow(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "nombreEsp":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getNombreProcedimientoWorkFlow(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "objetoCat":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getObjeto(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "objetoEsp":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getObjeto(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "publicoObjetivo":
                        if (servicioDTO.getPublicosObjetivo() == null || servicioDTO.getPublicosObjetivo().isEmpty()) {
                            retorno[fila][columna] = "";
                        } else {
                            String publicoObjetivo = "";
                            for (TipoPublicoObjetivoEntidadGridDTO tipoPublicoObjetivoDTO : servicioDTO.getPublicosObjetivo()) {
                                if (tipoPublicoObjetivoDTO.getDescripcion().getTraduccion(idioma) == null) {
                                    publicoObjetivo += tipoPublicoObjetivoDTO.getDescripcion().getTraduccion() + ", ";
                                } else {
                                    publicoObjetivo += tipoPublicoObjetivoDTO.getDescripcion().getTraduccion(idioma) + ", ";
                                }
                            }
                            retorno[fila][columna] = UtilExport.getValor(publicoObjetivo, idioma);
                        }
                        break;
                    case "unidadAdministrativaInstructora":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getUaInstructor(), idioma);
                        break;
                    case "unidadAdministrativaResponsable":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getUaResponsable(), idioma);
                        break;
                    case "unidadAdministrativaCompetente":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getUaCompetente(), idioma);
                        break;

                    case "responsable":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getResponsable(), idioma);
                        break;
                    case "unidadAdministrativaResolutoria":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getUaCompetente(), idioma);
                        break;

                    case "numeroNormas":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getNormativas() == null ? 0 : servicioDTO.getNormativas().size(), idioma);
                        break;
                    case "fechaActualizacion":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getFechaActualizacion(), idioma);
                        break;
                    case "usuarioUltimaActualizacion":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.getUsuarioAuditoria(), idioma);
                        break;
                    case "comun":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.esComun(), idioma);
                        break;
                    case "presencial":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.isTramitPresencial(), idioma);
                        break;
                    case "telematico":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.isTramitElectronica(), idioma);
                        break;
                    case "telefonico":
                        retorno[fila][columna] = UtilExport.getValor(servicioDTO.isTramitTelefonica(), idioma);
                        break;
                    default:
                        retorno[fila][columna] = "";
                        break;
                }
                columna++;
            }
            fila++;
        }
        return retorno;
    }


    /**
     * Convierte los valores de una lista de procs en un array de String
     *
     * @param procs
     * @param exportarDatos
     * @param idioma
     * @return
     */
    public static String[][] getValoresProcs(List<ProcedimientoBaseDTO> procs, ExportarDatos exportarDatos, String idioma) {

        String[][] retorno = new String[procs.size()][exportarDatos.getCampos().size()];
        int fila = 0;
        for (ProcedimientoBaseDTO dato : procs) {
            ProcedimientoDTO procedimientoDTO = (ProcedimientoDTO) dato;
            int columna = 0;
            for (ExportarCampos exp : exportarDatos.getCampos()) {
                switch (exp.getCampo()) {
                    case "codigo":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getCodigo(), idioma);
                        break;
                    case "codigoWF":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getCodigoWF(), idioma);
                        break;
                    case "codigoSIA":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getCodigoSIA(), idioma);
                        break;
                    case "estadoSIA":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getEstadoSIA(), idioma);
                        break;
                    case "fechaSIA":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getFechaSIA(), idioma);
                        break;
                    case "fechaPub":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getFechaPublicacion(), idioma);
                        break;
                    case "fechaCad":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getFechaCaducidad(), idioma);
                        break;
                    case "estado":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getEstado(), idioma);
                        break;
                    case "visibilidad":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.esVisible(), idioma);
                        break;
                    /*case "pendienteValidar":
                        sb.append(UtilExport.getValor(procedimientoDTO.isPendienteIndexar(),idioma));
                        break;*/
                    case "nombreCat":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getNombreProcedimientoWorkFlow(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "nombreEsp":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getNombreProcedimientoWorkFlow(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "objetoCat":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getObjeto(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "objetoEsp":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getObjeto(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "publicoObjetivo":
                        if (procedimientoDTO.getPublicosObjetivo() == null || procedimientoDTO.getPublicosObjetivo().isEmpty()) {
                            retorno[fila][columna] = "";
                        } else {
                            String publicoObjetivo = "";
                            for (TipoPublicoObjetivoEntidadGridDTO tipoPublicoObjetivoDTO : procedimientoDTO.getPublicosObjetivo()) {
                                if (tipoPublicoObjetivoDTO.getDescripcion().getTraduccion(idioma) == null) {
                                    publicoObjetivo += tipoPublicoObjetivoDTO.getDescripcion().getTraduccion() + ", ";
                                } else {
                                    publicoObjetivo += tipoPublicoObjetivoDTO.getDescripcion().getTraduccion(idioma) + ", ";
                                }
                            }
                            retorno[fila][columna] = UtilExport.getValor(publicoObjetivo, idioma);
                        }
                        break;
                    case "unidadAdministrativaInstructora":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getUaInstructor(), idioma);
                        break;
                    case "unidadAdministrativaResponsable":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getUaResponsable(), idioma);
                        break;
                    case "responsable":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getResponsable(), idioma);
                        break;
                    case "unidadAdministrativaResolutoria":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getUaCompetente(), idioma);
                        break;
                    case "numeroTramites":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getTramites() == null ? 0 : procedimientoDTO.getTramites().size(), idioma);
                        break;
                    case "numeroTramitesTelematicos":
                        if (procedimientoDTO.getTramites() == null) {
                            retorno[fila][columna] = UtilExport.getValor(0, idioma);
                        } else {
                            int total = 0;
                            for (ProcedimientoTramiteDTO tramiteDTO : procedimientoDTO.getTramites()) {
                                if (tramiteDTO.isTramitElectronica()) {
                                    total++;
                                }
                            }
                            retorno[fila][columna] = UtilExport.getValor(total, idioma);
                        }

                        break;
                    case "numeroNormas":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getNormativas() == null ? 0 : procedimientoDTO.getNormativas().size(), idioma);
                        break;
                    case "fechaActualizacion":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getFechaActualizacion(), idioma);
                        break;
                    case "usuarioUltimaActualizacion":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.getUsuarioAuditoria(), idioma);
                        break;
                    case "comun":
                        retorno[fila][columna] = UtilExport.getValor(procedimientoDTO.esComun(), idioma);
                        break;
                    default:
                        break;
                }
                columna++;
            }
            fila++;
        }
        return retorno;
    }


    /**
     * Convierte los valores de una lista de normativas en un array de String
     *
     * @param normativas
     * @param exportarDatos
     * @param idioma
     * @return
     */
    public static String[][] getValoresNormativas(List<NormativaDTO> normativas, ExportarDatos exportarDatos, String idioma) {

        String[][] retorno = new String[normativas.size()][exportarDatos.getCampos().size()];
        int fila = 0;
        for (NormativaDTO normativa : normativas) {
            int columna = 0;
            for (ExportarCampos exp : exportarDatos.getCampos()) {
                switch (exp.getCampo()) {
                    case "codigo":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getCodigo(), idioma);
                        break;
                    case "normaCat":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getTitulo(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "normaEsp":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getTitulo(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "enlaceCat":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getUrlBoletin(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "enlaceEsp":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getUrlBoletin(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "responsableCat":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getNombreResponsable(), Constantes.IDIOMA_CATALAN);
                        break;
                    case "responsableEsp":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getNombreResponsable(), Constantes.IDIOMA_ESPANYOL);
                        break;
                    case "estado":
                        if (idioma.equals(Constantes.IDIOMA_CATALAN)) {
                            retorno[fila][columna] = normativa.getVigente() ? "VIGENT" : "NO VIGENT";
                        } else {
                            retorno[fila][columna] = normativa.getVigente() ? "VIGENTE" : "NO VIGENTE";
                        }
                        break;
                    case "rangoLegal":
                        retorno[fila][columna] = ""; //normativa.getRangoLegal());
                        break;
                    case "tipoBoletin":
                        if (normativa.getBoletinOficial() == null) {
                            retorno[fila][columna] = "";
                        } else {
                            retorno[fila][columna] = UtilExport.getValor(normativa.getBoletinOficial().getNombre(), idioma);
                        }
                        break;
                    case "numeroBoletin":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getNumeroBoletin(), idioma);
                        break;
                    case "numero":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getNumero(), idioma);
                        break;
                    case "enlace":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getUrlBoletin(), idioma);
                        break;
                    case "fechaAprobacion":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getFechaAprobacion(), idioma);
                        break;
                    case "fechaBoletin":
                        retorno[fila][columna] = UtilExport.getValor(normativa.getFechaBoletin(), idioma);
                        break;
                    case "tipoNormativa":
                        if (normativa.getTipoNormativa() == null) {
                            retorno[fila][columna] = "";
                        } else {
                            retorno[fila][columna] = UtilExport.getValor(normativa.getTipoNormativa().getDescripcion(), idioma);
                        }
                        break;
                    default:
                        retorno[fila][columna] = "";
                        break;
                }
                columna++;
            }
            fila++;
        }
        return retorno;
    }

    public static String[] getCabecera(ExportarDatos exportarDatos) {
        String[] retorno = new String[exportarDatos.getCampos().size()];
        int posicion = 0;
        for (ExportarCampos exp : exportarDatos.getCampos()) {
            retorno[posicion] = exp.getNombreCampo();
            posicion++;
        }
        return retorno;
    }

    public static StreamedContent generarStreamedContent(String tipo, String[] cabecera, String[][] datos, ExportarDatos exportarDatos) {
        if (exportarDatos.getFormato().equals(TypeExportarFormato.CSV)) {
            return generarStreamedContentCSV(tipo, cabecera, datos, exportarDatos);
        } else if (exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
            return generarStreamedContentTXT(tipo, cabecera, datos, exportarDatos);
        } else if (exportarDatos.getFormato().equals(TypeExportarFormato.XLS)) {
            return generarStreamedContentXLS(tipo, cabecera, datos, exportarDatos);
        } else {
            return generarStreamedContentPDF(tipo, cabecera, datos, exportarDatos);
        }
    }

    public static StreamedContent generarStreamedContentPDF(String tipo, String[] cabecera, String[][] datos, ExportarDatos exportarDatos) {
        //Hecho con itext
        try {

            Document pdf = new Document(PageSize.A4.rotate());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(pdf, baos);

            pdf.open();
            float fntSize = exportarDatos.getFontSize();
            float lineSpacing = 1f;

            //Instanciamos una tabla de 3 columnas
            PdfPTable tabla = new PdfPTable(cabecera.length);
            tabla.setWidthPercentage(100);

            tabla.setWidths(exportarDatos.getPesos());
            //Declaramos un objeto para manejar las celdas
            for (String cabeza : cabecera) {
                PdfPCell celda = new PdfPCell(new Phrase(cabeza, FontFactory.getFont(FontFactory.TIMES_ROMAN, fntSize * 1.3f, Font.BOLD)));
                celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setVerticalAlignment(Element.ALIGN_TOP);
                celda.setBorderWidth(1);
                tabla.addCell(celda);
            }


            for (String[] dato : datos) {
                for (String valor : dato) {
                    PdfPCell celda = new PdfPCell(new Phrase("" + valor, FontFactory.getFont(FontFactory.TIMES_ROMAN, fntSize)));
                    celda.setBorder(Rectangle.BOTTOM);
                    celda.setBorderWidthBottom(0.2f);
                    celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    celda.setPaddingBottom(2.5f);
                    tabla.addCell(celda);
                }
            }

            pdf.add(tabla);
            pdf.close();

            InputStream stream = new ByteArrayInputStream(baos.toByteArray());
            StreamedContent fileDownload = new DefaultStreamedContent(stream, "application/pdf", tipo + ".pdf");
            return fileDownload;

        } catch (Exception e) {
            LOG.error("Error al generar el PDF", e);
            return null;
        }
    }

    public static StreamedContent generarStreamedContentPDFBox(String tipo, String[] cabecera, String[][] datos, ExportarDatos exportarDatos) {
        try {

            // Create a document and add a page to it
            PDDocument document = new PDDocument();

            PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
            // PDRectangle.LETTER and others are also possible
            PDRectangle rect = page.getMediaBox();
            // rect can be used to get the page width and height
            document.addPage(page);

            int pageWidth = (int) page.getTrimBox().getWidth(); //get width of the page
            int pageHeight = (int) page.getTrimBox().getHeight(); //get height of the page


            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setStrokingColor(Color.DARK_GRAY);
            contentStream.setLineWidth(1);

            int initX = 50;
            int initY = pageHeight - 50;
            int cellHeight = 20;
            int cellWidth = 100;

            int colCount = 3;
            int rowCount = 3;

            int i = 1;
            int j = 1;
            for (String cabeza : cabecera) {
                contentStream.addRect(initX, initY, cellWidth + 30, -cellHeight);

                contentStream.beginText();
                contentStream.newLineAtOffset(initX + 30, initY - cellHeight + 10);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                contentStream.showText(cabeza);
                contentStream.endText();
                initX += cellWidth + 30;
                j++;
            }

            //Anyadimos los datos
            int fila = 2;
            initY = pageHeight - 70;
            for (String[] dato : datos) {
                int cellid = 1;
                initX = 50;
                if (cellid > 110) {
                    String parar = "";
                }
                for (String valor : dato) {

                    contentStream.addRect(initX, initY, cellWidth, -cellHeight);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(initX + 10, initY - cellHeight + 10);
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                    contentStream.showText(quitarCaracteresEspeciales(valor));
                    contentStream.endText();

                    initX += 60;
                    //initY += cellHeight;
                }
                initY -= 30;

            }

            contentStream.stroke();
            contentStream.close();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);

            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            StreamedContent streamedContent = new DefaultStreamedContent(inputStream, "application/pdf", tipo + ".pdf");
            return streamedContent;

        } catch (Exception e) {
            LOG.error("Error al generar el PDF", e);
            return null;
        }
    }

    /**
     * Quita los saltos de linea
     *
     * @param valor
     * @return
     */
    private static String quitarCaracteresEspeciales(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t", " ").replaceAll(";", ",");
    }

    private static final Logger LOG = LoggerFactory.getLogger(UtilExport.class);

    public static StreamedContent generarStreamedContentXLS(String tipo, String[] cabecera, String[][] datos, ExportarDatos exportarDatos) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(tipo); //Se pone el nombre de la hoja , el tipo de exportacion

        //Anyadimos la cabecera
        //Si exportamos en formato XLS, añadimos la cabecera
        XSSFRow row = spreadsheet.createRow((short) 1);
        int i = 1;
        for (ExportarCampos exp : exportarDatos.getCampos()) {
            row.createCell(i).setCellValue(exp.getNombreCampo());
            i++;
        }

        //Anyadimos los datos
        int fila = 2;
        for (String[] dato : datos) {
            int cellid = 1;
            XSSFRow filaXLS = spreadsheet.createRow(fila);
            for (String valor : dato) {
                filaXLS.createCell(cellid).setCellValue(valor);
                cellid++;
            }
            fila++;
        }

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            StreamedContent streamedContent = new DefaultStreamedContent(inputStream, "application/xls", tipo + ".xlsx");
            return streamedContent;


        } catch (Exception e) {
            return null;
        }
    }

    public static StreamedContent generarStreamedContentTXT(String tipo, String[] cabecera, String[][] datos, ExportarDatos exportarDatos) {
        String filename = tipo + ".txt";
        StringBuilder sb = new StringBuilder();

        for (String[] fila : datos) {
            int posicion = 0;
            sb.append(tipo);
            sb.append("\n");
            for (String valor : fila) {
                sb.append("\t" + cabecera[posicion] + ": " + valor + "\n");
                posicion++;
            }
            sb.append("\n");
        }

        String mimeType = "text/plain";
        String encoding = "ISO-8859-1"; // encoding =  "UTF-8"; // encoding = "CP1252";
        InputStream fis = new ByteArrayInputStream(getBytesEncoding(sb.toString().getBytes(), encoding));
        StreamedContent file = DefaultStreamedContent.builder().name(filename).contentType(mimeType).stream(() -> fis).build();
        return file;
    }

    /**
     * Devuelve un array de bytes con el encoding indicado
     *
     * @param bytes
     * @param encoding
     * @return
     */
    private static byte[] getBytesEncoding(byte[] bytes, String encoding) {
        try {
            return new String(bytes, encoding).getBytes(encoding);
            //R1: StringCoding.encode(charsetName, value, 0, value.length)
        } catch (Exception e) {
            return bytes;
        }
    }

    public static StreamedContent generarStreamedContentCSV(String tipo, String[] cabecera, String[][] datos, ExportarDatos exportarDatos) {
        String filename = tipo + ".csv";
        String separator = ";";
        StringBuilder sb = new StringBuilder();
        for (String cab : cabecera) {
            sb.append(cab + separator);
        }
        sb.append("\n");
        for (String[] fila : datos) {
            for (String valor : fila) {
                sb.append(quitarCaracteresEspeciales(valor) + separator);
            }
            sb.append("\n");
        }

        String mimeType = "text/csv";
        String encoding = "ISO-8859-1"; // encoding =  "UTF-8"; // encoding = "CP1252";
        InputStream fis = new ByteArrayInputStream(getBytesEncoding(sb.toString().getBytes(), encoding));
        StreamedContent file = DefaultStreamedContent.builder().name(filename).contentType(mimeType).stream(() -> fis).build();
        return file;

    }
}
