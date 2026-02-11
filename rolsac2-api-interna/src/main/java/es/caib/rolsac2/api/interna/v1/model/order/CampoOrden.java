package es.caib.rolsac2.api.interna.v1.model.order;

import es.caib.rolsac2.api.interna.v1.model.EntidadJson;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * FiltroUA.
 *
 * @author indra
 */
@XmlRootElement
@Schema(name = "CampoOrden", type = SchemaType.STRING, description = "Campo a ordenar")
public class CampoOrden extends EntidadJson<CampoOrden> {

    /**
     * campo.
     **/
    @Schema(required = false, type = SchemaType.STRING, name = "campo", description = "Campo por el que se ordena")
    private String campo;

    /**
     * tipoOrden.
     **/
    @Schema(required = false, type = SchemaType.STRING, name = "tipoOrden", description = "Tipo de ordenacion", enumeration = "ASC,DESC")
    private String tipoOrden;

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }


    /**
     * @param campo the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }


    /**
     * @return the tipoOrden
     */
    public String getTipoOrden() {
        return tipoOrden;
    }


    /**
     * @param tipoOrden the tipoOrden to set
     */
    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }


}
