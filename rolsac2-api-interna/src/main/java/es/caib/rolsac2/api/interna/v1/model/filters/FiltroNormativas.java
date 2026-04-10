package es.caib.rolsac2.api.interna.v1.model.filters;

import es.caib.rolsac2.api.interna.v1.model.EntidadJson;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;

/**
 * FiltroNormatives.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "FiltroNormativas", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroNormativas extends EntidadJson<FiltroNormativas> {

    private static final Logger LOG = LoggerFactory.getLogger(FiltroNormativas.class);

    public static final String SAMPLE = Constantes.SALTO_LINEA + "{" + "\"idUA\":0," + Constantes.SALTO_LINEA + "\"idUAsHijas\":[0, ...]," + Constantes.SALTO_LINEA + "\"fechaBoletin\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"codigoTipoNormativa\":0," + Constantes.SALTO_LINEA + "\"codigoTipoBoletin\":0," + Constantes.SALTO_LINEA + "\"numero\":\"string\"," + Constantes.SALTO_LINEA + "\"fechaAprobacion\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"idEntidad\":0," + Constantes.SALTO_LINEA + "\"texto\":\"string\"," + Constantes.SALTO_LINEA + "\"vigente\":\"0\", (1=Si, 0=No)" + Constantes.SALTO_LINEA + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" + "}";

    public static final String SAMPLE_JSON = "{" + "\"idUA\":null," + "\"idUAsHijas\":null," + "\"fechaBoletin\":null," + "\"codigoTipoNormativa\":null," + "\"codigoTipoBoletin\":null," + "\"numero\":null," + "\"fechaAprobacion\":null," + "\"idEntidad\":null," + "\"texto\":null," + "\"vigente\":null," + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" + "}";

    /**
     * idUA.
     **/
    @Schema(name = "idUA", description = "Identificador de la unidad administrativa. Se puede consultar en el método /services/v1/unidades_administrativas", type = SchemaType.INTEGER, required = false)
    private Long idUA;

    /**
     * idUAsHijas.
     **/
    @Schema(name = "idUAsHijas", description = "Identificadores de unidades administrativas hijas", type = SchemaType.ARRAY, required = false)
    private List<Long> idUAsHijas;

    /**
     * fechaBoletin.
     **/
    @Schema(name = "fechaBoletin", description = "Fecha del boletín (DD/MM/YYYY). Se puede consultar en /services/v1/boletines", type = SchemaType.STRING, required = false)
    private LocalDate fechaBoletin;

    /**
     * codigoTipoNormativa.
     **/
    @Schema(name = "codigoTipoNormativa", description = "Código de tipo normativa. Se puede consultar en el método /services/v1/tipos_normativa", type = SchemaType.INTEGER, required = false)
    private Long codigoTipoNormativa;

    /**
     * numero.
     **/
    @Schema(name = "numero", description = "Número de la normativa", type = SchemaType.STRING, required = false)
    private String numero;

    /**
     * codigoTipoBoletin.
     **/
    @Schema(name = "codigoTipoBoletin", description = "Código de tipo boletín. Se puede consultar en /services/v1/boletines", type = SchemaType.INTEGER, required = false)
    private Long codigoTipoBoletin;

    /**
     * fechaAprobacion.
     **/
    @Schema(name = "fechaAprobacion", description = "Fecha de aprobación (DD/MM/YYYY)", type = SchemaType.STRING, required = false)
    private LocalDate fechaAprobacion;

    /**
     * FiltroPaginacion.
     **/
    @Schema(name = "filtroPaginacion", description = "Filtro de paginación", required = false)
    private FiltroPaginacion filtroPaginacion;

    /**
     * texto.
     **/
    @Schema(name = "texto", description = "Compara con título, identificador de tipo normativa, número, nombre de boletín oficial, fecha de aprobación", type = SchemaType.STRING, required = false)
    private String texto;

    /**
     * vigente.
     **/
    @Schema(name = "vigente", description = "Valores posibles: 1 – Vigente, 0 – No vigente", type = SchemaType.INTEGER, required = false)
    private Integer vigente;

    /**
     * Entidad
     */
    @Schema(name = "idEntidad", description = "Identificador de la entidad. Se puede consultar en el método /services/v1/entidades", type = SchemaType.INTEGER, required = false)
    private Long idEntidad;

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public NormativaFiltro toNormativaFiltro() {
        NormativaFiltro resultado = new NormativaFiltro();

        if (this.texto != null && !this.texto.isEmpty()) {
            resultado.setTexto(texto);
        }

        if (this.fechaAprobacion != null) {
            resultado.setFechaAprobacion(fechaAprobacion);
        }

        if (this.codigoTipoBoletin != null) {
            TipoBoletinDTO tipoBoletin = new TipoBoletinDTO();
            tipoBoletin.setCodigo(codigoTipoBoletin);
            resultado.setTipoBoletin(tipoBoletin);
        }

        if (this.numero != null) {
            resultado.setNumero(numero);
        }

        if (this.codigoTipoNormativa != null) {
            TipoNormativaDTO tipoNormativa = new TipoNormativaDTO();
            tipoNormativa.setCodigo(codigoTipoNormativa);
            resultado.setTipoNormativa(tipoNormativa);
        }

        if (this.fechaBoletin != null) {
            resultado.setFechaBoletin(fechaBoletin);
        }

        if (this.idUAsHijas != null) {
            resultado.setIdUAsHijas(idUAsHijas);
            resultado.setHijasActivas(true);
        }

        if (this.idEntidad != null) {
            resultado.setIdEntidad(idEntidad);
        }

        if (this.idUA != null) {
            resultado.setIdUA(idUA);
        }

        if (this.vigente != null) {
            resultado.setVigente(this.vigente == 1);
        }

        return resultado;

    }

    public FiltroPaginacion getFiltroPaginacion() {
        return filtroPaginacion;
    }

    public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
        this.filtroPaginacion = filtroPaginacion;
    }

    public Long getIdUA() {
        return idUA;
    }

    public void setIdUA(Long idUA) {
        this.idUA = idUA;
    }

    public List<Long> getIdUAsHijas() {
        return idUAsHijas;
    }

    public void setIdUAsHijas(List<Long> idUAsHijas) {
        this.idUAsHijas = idUAsHijas;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    public Long getCodigoTipoNormativa() {
        return codigoTipoNormativa;
    }

    public void setCodigoTipoNormativa(Long codigoTipoNormativa) {
        this.codigoTipoNormativa = codigoTipoNormativa;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getCodigoTipoBoletin() {
        return codigoTipoBoletin;
    }

    public void setCodigoTipoBoletin(Long codigoTipoBoletin) {
        this.codigoTipoBoletin = codigoTipoBoletin;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Integer getVigente() {
        return vigente;
    }

    public void setVigente(Integer vigente) {
        this.vigente = vigente;
    }
}