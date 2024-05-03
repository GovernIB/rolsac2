package es.caib.rolsac2.api.externa.v1.model.filters;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
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

    public static final String SAMPLE = Constantes.SALTO_LINEA + "{" + "\"idUA\":0," + Constantes.SALTO_LINEA + "\"idUAsHijas\":[0, ...]," + Constantes.SALTO_LINEA + "\"fechaBoletin\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"codigoTipoNormativa\":0," + Constantes.SALTO_LINEA + "\"codigoTipoBoletin\":0," + Constantes.SALTO_LINEA + "\"numero\":\"string\"," + Constantes.SALTO_LINEA + "\"fechaAprobacion\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"idEntidad\":0," + Constantes.SALTO_LINEA + "\"texto\":\"string\"," + Constantes.SALTO_LINEA + "\"vigente\":\"boolean\"," + Constantes.SALTO_LINEA + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" + "}";

    public static final String SAMPLE_JSON = "{" + "\"idUA\":null," + "\"idUAsHijas\":null," + "\"fechaBoletin\":null," + "\"codigoTipoNormativa\":null," + "\"codigoTipoBoletin\":null," + "\"numero\":null," + "\"fechaAprobacion\":null," + "\"idEntidad\":null," + "\"texto\":null," + "\"vigente\":null," + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" + "}";

    /**
     * idUA.
     **/
    @Schema(name = "idUA", description = "idUA", type = SchemaType.INTEGER, required = false)
    private Long idUA;

    @Schema(name = "idUAsHijas", description = "idUAsHijas", type = SchemaType.ARRAY, required = false)
    private List<Long> idUAsHijas;

    /**
     * fechaBoletin.
     **/
    @Schema(name = "fechaBoletin", description = "fechaBoletin", type = SchemaType.STRING, required = false)
    private LocalDate fechaBoletin;

    /**
     * codigoTipoNormativa.
     **/
    @Schema(name = "codigoTipoNormativa", description = "codigoTipoNormativa", type = SchemaType.INTEGER, required = false)
    private Long codigoTipoNormativa;

    /**
     * numero.
     **/
    @Schema(name = "numero", description = "numero", type = SchemaType.STRING, required = false)
    private String numero;

    /**
     * codigoTipoBoletin.
     **/
    @Schema(name = "codigoTipoBoletin", description = "codigoTipoBoletin", type = SchemaType.INTEGER, required = false)
    private Long codigoTipoBoletin;

    /**
     * fechaAprobacion.
     **/
    @Schema(name = "fechaAprobacion", description = "fechaAprobacion", type = SchemaType.STRING, required = false)
    private LocalDate fechaAprobacion;

    /**
     * FiltroPaginacion.
     **/
    @Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
    private FiltroPaginacion filtroPaginacion;

    /**
     * texto.
     **/
    @Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
    private String texto;

    /**
     * texto.
     **/
    @Schema(name = "vigente", description = "vigente", type = SchemaType.BOOLEAN, required = false)
    private Boolean vigente;

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

    /**
     * Entidad
     */
    @Schema(name = "idEntidad", description = "idEntidad", type = SchemaType.INTEGER, required = false)
    private Long idEntidad;

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
            resultado.setVigente(this.vigente);
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

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }
}
