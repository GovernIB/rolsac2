
package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para INICIOS complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="INICIOS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FORMAINICIACIONINTERESADO"&gt;
 *           &lt;simpleType&gt;
 *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *             &lt;/union&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="CODEFECTOSILENCIOINTERESADO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FORMAINICIACIONOFICIO"&gt;
 *           &lt;simpleType&gt;
 *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *             &lt;/union&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="CODEFECTOSILENCIOOFICIO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INICIOS", propOrder = {
    "formainiciacioninteresado",
    "codefectosilenciointeresado",
    "formainiciacionoficio",
    "codefectosilenciooficio"
})
public class INICIOS {

    @XmlElement(name = "FORMAINICIACIONINTERESADO", required = true)
    protected String formainiciacioninteresado;
    @XmlElement(name = "CODEFECTOSILENCIOINTERESADO")
    protected String codefectosilenciointeresado;
    @XmlElement(name = "FORMAINICIACIONOFICIO", required = true)
    protected String formainiciacionoficio;
    @XmlElement(name = "CODEFECTOSILENCIOOFICIO")
    protected String codefectosilenciooficio;

    /**
     * Obtiene el valor de la propiedad formainiciacioninteresado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFORMAINICIACIONINTERESADO() {
        return formainiciacioninteresado;
    }

    /**
     * Define el valor de la propiedad formainiciacioninteresado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFORMAINICIACIONINTERESADO(String value) {
        this.formainiciacioninteresado = value;
    }

    /**
     * Obtiene el valor de la propiedad codefectosilenciointeresado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODEFECTOSILENCIOINTERESADO() {
        return codefectosilenciointeresado;
    }

    /**
     * Define el valor de la propiedad codefectosilenciointeresado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODEFECTOSILENCIOINTERESADO(String value) {
        this.codefectosilenciointeresado = value;
    }

    /**
     * Obtiene el valor de la propiedad formainiciacionoficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFORMAINICIACIONOFICIO() {
        return formainiciacionoficio;
    }

    /**
     * Define el valor de la propiedad formainiciacionoficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFORMAINICIACIONOFICIO(String value) {
        this.formainiciacionoficio = value;
    }

    /**
     * Obtiene el valor de la propiedad codefectosilenciooficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODEFECTOSILENCIOOFICIO() {
        return codefectosilenciooficio;
    }

    /**
     * Define el valor de la propiedad codefectosilenciooficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODEFECTOSILENCIOOFICIO(String value) {
        this.codefectosilenciooficio = value;
    }

}
