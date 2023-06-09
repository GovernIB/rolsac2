package es.caib.rolsac2.api.externa.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.NormativaDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;

/**
 * Normatives.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "Normativa", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_NORMATIVAS)
public class Normativa extends EntidadBase<Normativa> {

    private static final Logger LOG = LoggerFactory.getLogger(Normativa.class);

    /**
     * codigo
     **/
    @Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
    private Long codigo;

    /**
     * fecha
     **/
    @Schema(description = "fechaAprobacion", name = "fechaAprobacion", type = SchemaType.STRING, required = false)
    //	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaAprobacion;

    /**
     * numero
     **/
    @Schema(description = "numero", type = SchemaType.STRING, required = false)
    private String numero;

    /**
     * fechaBoletin
     **/
    @Schema(description = "fechaBoletin", name = "fechaBoletin", type = SchemaType.STRING, required = false)
    //	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaBoletin;//

    /**
     * numeroBoletin
     **/
    @Schema(description = "numeroBoletin", type = SchemaType.STRING, required = false)
    private String numeroBoletin;

    /**
     * urlBoletin
     **/
    @Schema(description = "urlBoletin", type = SchemaType.STRING, required = false)
    private String urlBoletin;//

    /**
     * nombre
     **/
    @Schema(description = "nombre", type = SchemaType.STRING, required = false)
    private String nombre;//

    /**
     * responsable
     **/
    @Schema(description = "nombreResponsable", type = SchemaType.STRING, required = false)
    private String nombreResponsable;//

    // -- LINKS--//
    // -- se duplican las entidades para poder generar la clase link en funcion de
    // la propiedad principal (sin "link_")
    /**
     * boletin
     **/
    @Schema(description = "link_boletinOficial", required = false)
    private Link link_boletinOficial;
    @JsonIgnore
    @Schema(hidden = true)
    @XmlTransient
    private Long boletinOficial;

    @Schema(description = "link_tipoNormativa", required = false)
    private Link link_tipoNormativa;
    @JsonIgnore
    @Schema(hidden = true)
    @XmlTransient
    private Long tipoNormativa;

    @Schema(description = "link_entidad", required = false)
    private Link link_entidad;
    @JsonIgnore
    @Schema(hidden = true)
    @XmlTransient
    private Long entidad;

    public Normativa(NormativaDTO elem, String urlBase, String idioma, boolean hateoasEnabled) {
        super(elem, urlBase, idioma, hateoasEnabled);
    }

    public Normativa() {
        super();
    }

    @Override
    public void generaLinks(String urlBase) {
        link_boletinOficial = this.generaLink(this.boletinOficial, Constantes.ENTIDAD_BOLETINES, Constantes.URL_BOLETINES, urlBase, null);
        link_entidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase, null);
        link_tipoNormativa = this.generaLink(this.tipoNormativa, Constantes.ENTIDAD_TIPO_NORMATIVA, Constantes.URL_TIPO_NORMATIVA, urlBase, null);
    }

    public String getUrlBoletin() {
        return urlBoletin;
    }

    public void setUrlBoletin(String urlBoletin) {
        this.urlBoletin = urlBoletin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    protected void addSetersInvalidos() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setId(Long codigo) {
        this.codigo = codigo;
    }

    public Long getBoletinOficial() {
        return boletinOficial;
    }

    public void setBoletinOficial(Long boletinOficial) {
        this.boletinOficial = boletinOficial;
    }

    @XmlTransient
    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    public String getNumeroBoletin() {
        return numeroBoletin;
    }

    public void setNumeroBoletin(String numeroBoletin) {
        this.numeroBoletin = numeroBoletin;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public Link getLink_boletinOficial() {
        return link_boletinOficial;
    }

    public void setLink_boletinOficial(Link link_boletinOficial) {
        this.link_boletinOficial = link_boletinOficial;
    }

    public Link getLink_tipoNormativa() {
        return link_tipoNormativa;
    }

    public void setLink_tipoNormativa(Link link_tipoNormativa) {
        this.link_tipoNormativa = link_tipoNormativa;
    }

    public Link getLink_entidad() {
        return link_entidad;
    }

    public void setLink_entidad(Link link_entidad) {
        this.link_entidad = link_entidad;
    }

    public Long getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(Long tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

	public Long getCodigo() {
		return codigo;
	}

}
