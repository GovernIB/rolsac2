package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Dades de un documento ficha.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoDocumento")
public class ProcedimientoDocumentoDTO extends ModelApi implements Cloneable, Comparable<ProcedimientoDocumentoDTO> {

    /**
     * LOGGERR
     **/
    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoDocumentoDTO.class);

    /**
     * Codigo
     **/
    private Long codigo;

    /**
     * Orden
     */
    private Integer orden;

    /**
     * Código temporal para poder tratar con el dato
     **/
    private String codigoString;

    /*Título del documento*/
    private Literal titulo;

    /*URL del documento*/
    private Literal url;

    /*Descripción del documento*/
    private Literal descripcion;

    /*Fichero asociado al documento*/
    private DocumentoMultiIdioma documentos;

    /**
     * Procedimiento
     **/
    private ProcedimientoDTO procedimientoDTO;

    /**
     * Obtiene codigo.
     *
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo the codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene procedimiento dto.
     *
     * @return the procedimiento dto
     */
    public ProcedimientoDTO getProcedimientoDTO() {
        return procedimientoDTO;
    }

    /**
     * Establece procedimiento dto.
     *
     * @param procedimientoDTO the procedimiento dto
     */
    public void setProcedimientoDTO(ProcedimientoDTO procedimientoDTO) {
        this.procedimientoDTO = procedimientoDTO;
    }

    /**
     * Obtiene titulo.
     *
     * @return the titulo
     */
    public Literal getTitulo() {
        return titulo;
    }

    /**
     * Establece titulo.
     *
     * @param titulo the titulo
     */
    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene url.
     *
     * @return the url
     */
    public Literal getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url the url
     */
    public void setUrl(Literal url) {
        this.url = url;
    }

    /**
     * Obtiene descripcion.
     *
     * @return the descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene documentos.
     *
     * @return the documentos
     */
    public DocumentoMultiIdioma getDocumentos() {
        return documentos;
    }

    /**
     * Establece documentos.
     *
     * @param documentos the documentos
     */
    public void setDocumentos(DocumentoMultiIdioma documentos) {
        this.documentos = documentos;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Obtiene codigo string.
     *
     * @return the codigo string
     */
    public String getCodigoString() {
        if (codigo != null) {
            return codigo.toString();
        } else {
            return codigoString;
        }
    }

    /**
     * Establece codigo string.
     *
     * @param codigoString the codigo string
     */
    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }

    public Object clone() {
        ProcedimientoDocumentoDTO obj = null;
        try {
            obj = (ProcedimientoDocumentoDTO) super.clone();
            obj.setOrden(this.getOrden());
            if (this.titulo != null) {
                obj.titulo = (Literal) this.titulo.clone();
            }

//            if (this.url != null) {
//                obj.url = (Literal) this.url.clone();
//            }

            if (this.descripcion != null) {
                obj.descripcion = (Literal) this.descripcion.clone();
            }

            if (documentos != null) {
                obj.documentos = (DocumentoMultiIdioma) documentos.clone();
            }
        } catch (CloneNotSupportedException ex) {
            LOG.error(" no se puede duplicar", ex);
        }
        return obj;
    }

    @Override
    public String toString() {
        return "ProcedimientoDocumentoDTO{" +
                "id=" + codigo +
                '}';
    }

    /**
     * Obtiene traducciones sobrantes.
     *
     * @param idiomas the idiomas
     * @return the traducciones sobrantes
     */
    public List<String> getTraduccionesSobrantes(List<String> idiomas) {
        List<String> idiomasSobrantes = new ArrayList<>();
        //Se va a mirar titulo y documentos, porque depende el caso, o es obligatorio el titulo o el documento
        if (this.getTitulo() != null) {
            for (String idiomaTitulo : this.getTitulo().getIdiomas()) {
                if (!idiomas.contains(idiomaTitulo) && !idiomasSobrantes.contains(idiomaTitulo)) {
                    idiomasSobrantes.add(idiomaTitulo);
                }
            }
        }

        if (this.getDocumentos() != null) {
            for (String idiomaDocumento : this.getDocumentos().getIdiomas()) {
                if (!idiomas.contains(idiomaDocumento) && !idiomasSobrantes.contains(idiomaDocumento)) {
                    idiomasSobrantes.add(idiomaDocumento);
                }
            }
        }
        return idiomasSobrantes;
    }


    @Override
    public int compareTo(ProcedimientoDocumentoDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getOrden(), data2.getOrden()) != 0) {
            return UtilComparador.compareTo(this.getOrden(), data2.getOrden());
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }

        if (UtilComparador.compareTo(this.getTitulo(), data2.getTitulo()) != 0) {
            return UtilComparador.compareTo(this.getTitulo(), data2.getTitulo());
        }

        if (UtilComparador.compareTo(this.getDocumentos(), data2.getDocumentos()) != 0) {
            return UtilComparador.compareTo(this.getDocumentos(), data2.getDocumentos());
        }
        return 0;
    }

    public static int compareTo(List<ProcedimientoDocumentoDTO> dato, List<ProcedimientoDocumentoDTO> dato2) {
        if ((dato == null || dato.size() == 0) && (dato2 == null || dato2.size() == 0)) {
            return 0;
        }
        if ((dato == null || dato.size() == 0) && (dato2 != null && dato2.size() > 0)) {
            return -1;
        }
        if ((dato != null && dato.size() > 0) && (dato2 == null || dato2.size() == 0)) {
            return 1;
        }

        if (dato.size() > dato2.size()) {
            return 1;
        } else if (dato2.size() > dato.size()) {
            return -1;
        } else {
            for (ProcedimientoDocumentoDTO tipo : dato) {
                boolean existe = false;
                for (ProcedimientoDocumentoDTO tipo2 : dato2) {
                    if (tipo.getCodigo().compareTo(tipo2.getCodigo()) == 0) {
                        if (tipo.getOrden().compareTo(tipo2.getOrden()) != 0) {
                            return tipo.getOrden().compareTo(tipo2.getOrden());
                        }
                        existe = true;
                    }
                }
                if (!existe) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
