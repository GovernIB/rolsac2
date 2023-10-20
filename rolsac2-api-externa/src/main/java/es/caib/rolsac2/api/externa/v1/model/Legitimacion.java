package es.caib.rolsac2.api.externa.v1.model;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Serveis.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "Inicio", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_SERVICIOS)
public class Legitimacion extends EntidadBase<Legitimacion> {

    private static final Logger LOG = LoggerFactory.getLogger(Legitimacion.class);

    /**
     * codigo
     **/
    @Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
    private long codigo;

    @Schema(description = "identificador", type = SchemaType.STRING, required = false)
    private String identificador;

    @Schema(description = "descripcion", type = SchemaType.STRING, required = false)
    private String descripcion;

    public Legitimacion() {
        super();
    }

    public Legitimacion(TipoLegitimacionDTO elem, String urlBase, String idioma, boolean hateoasEnabled) {
        super(elem, urlBase, idioma, hateoasEnabled);
    }

    @Override
    public void generaLinks(final String urlBase) {
        //		linkServicioResponsable = this.generaLink(this.servicioResponsable, Constantes.ENTIDAD_UA, Constantes.URL_UA,
        //				urlBase, null);
        //		linkOrganoInstructor = this.generaLink(this.organoInstructor, Constantes.ENTIDAD_UA, Constantes.URL_UA,
        //				urlBase, null);
        //		linkLopdInfoAdicional = this.generaLinkArchivo(this.lopdInfoAdicional, urlBase, null);
    }

    @Override
    protected void addSetersInvalidos() {
        /*
        if (!SETTERS_INVALIDS.contains("setCodigo")) {
            SETTERS_INVALIDS.add("setCodigo");
        }*/

        if (!SETTERS_INVALIDS.contains("setPlataforma")) {
            SETTERS_INVALIDS.add("setPlataforma");
        }

        if (!SETTERS_INVALIDS.contains("setPorDefecto")) {
            SETTERS_INVALIDS.add("setPorDefecto");
        }

    }

    @Override
    public void setId(final Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
