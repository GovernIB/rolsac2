package es.caib.rolsac2.api.interna.v1.model;

import es.caib.rolsac2.service.model.TasaProcedimientoDTO;
import es.caib.rolsac2.service.model.TasaServicioDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Tasa.
 */
@XmlRootElement
@Schema(name = "Tasa", description = "Datos de una Tasa")
public class Tasa extends EntidadBase<Tasa> {

    @Schema(description = "codigo", type = SchemaType.STRING)
    private String codigo;

    @Schema(description = "descripcion", type = SchemaType.STRING)
    private String descripcion;

    @Schema(description = "formaPago", type = SchemaType.STRING)
    private String formaPago;

    @Schema(description = "url", type = SchemaType.STRING)
    private String url;

    public Tasa(TasaProcedimientoDTO elem, String urlBase, String idioma, boolean hateoasEnabled, String idiomaPorDefecto) {
        super(elem, urlBase, idioma, hateoasEnabled);
        if (elem.getIdentificador() != null) {
            this.codigo = elem.getIdentificador().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (elem.getDescripcion() != null) {
            this.descripcion = elem.getDescripcion().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (elem.getFormaPago() != null) {
            this.formaPago = elem.getFormaPago().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (elem.getUrl() != null) {
            this.url = elem.getUrl().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
    }

    public Tasa(TasaServicioDTO elem, String urlBase, String idioma, boolean hateoasEnabled, String idiomaPorDefecto) {
        super(elem, urlBase, idioma, hateoasEnabled);
        if (elem.getIdentificador() != null) {
            this.codigo = elem.getIdentificador().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (elem.getDescripcion() != null) {
            this.descripcion = elem.getDescripcion().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (elem.getFormaPago() != null) {
            this.formaPago = elem.getFormaPago().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (elem.getUrl() != null) {
            this.url = elem.getUrl().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
    }

    public Tasa() {
        super();
    }

    @Override
    public void generaLinks(String urlBase) {
    }

    @Override
    public void setId(Long codigo) {
        this.codigo = codigo != null ? String.valueOf(codigo) : null;
    }

    @Override
    protected void addSetersInvalidos() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}