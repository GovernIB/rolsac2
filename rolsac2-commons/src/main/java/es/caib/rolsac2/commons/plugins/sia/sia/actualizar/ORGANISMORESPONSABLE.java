
package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ORGANISMORESPONSABLE complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ORGANISMORESPONSABLE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CODORGANISMORESPONSABLEN1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CODORGANISMORESPONSABLEN2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ORGANISMORESPONSABLE", propOrder = {
    "codorganismoresponsablen1",
    "codorganismoresponsablen2"
})
public class ORGANISMORESPONSABLE {

    @XmlElement(name = "CODORGANISMORESPONSABLEN1", required = true)
    protected String codorganismoresponsablen1;
    @XmlElement(name = "CODORGANISMORESPONSABLEN2")
    protected String codorganismoresponsablen2;

    /**
     * Obtiene el valor de la propiedad codorganismoresponsablen1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODORGANISMORESPONSABLEN1() {
        return codorganismoresponsablen1;
    }

    /**
     * Define el valor de la propiedad codorganismoresponsablen1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODORGANISMORESPONSABLEN1(String value) {
        this.codorganismoresponsablen1 = value;
    }

    /**
     * Obtiene el valor de la propiedad codorganismoresponsablen2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODORGANISMORESPONSABLEN2() {
        return codorganismoresponsablen2;
    }

    /**
     * Define el valor de la propiedad codorganismoresponsablen2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODORGANISMORESPONSABLEN2(String value) {
        this.codorganismoresponsablen2 = value;
    }

}
