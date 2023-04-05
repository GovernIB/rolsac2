
package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para PLAZORESOLUCION complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PLAZORESOLUCION"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NUMEROPLAZORESOLUCION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CODTIPOPLAZORESOLUCION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PLAZORESOLUCION", propOrder = {
    "numeroplazoresolucion",
    "codtipoplazoresolucion"
})
public class PLAZORESOLUCION {

    @XmlElement(name = "NUMEROPLAZORESOLUCION", required = true)
    protected String numeroplazoresolucion;
    @XmlElement(name = "CODTIPOPLAZORESOLUCION", required = true)
    protected String codtipoplazoresolucion;

    /**
     * Obtiene el valor de la propiedad numeroplazoresolucion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROPLAZORESOLUCION() {
        return numeroplazoresolucion;
    }

    /**
     * Define el valor de la propiedad numeroplazoresolucion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROPLAZORESOLUCION(String value) {
        this.numeroplazoresolucion = value;
    }

    /**
     * Obtiene el valor de la propiedad codtipoplazoresolucion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODTIPOPLAZORESOLUCION() {
        return codtipoplazoresolucion;
    }

    /**
     * Define el valor de la propiedad codtipoplazoresolucion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODTIPOPLAZORESOLUCION(String value) {
        this.codtipoplazoresolucion = value;
    }

}
