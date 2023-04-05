package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ERRORES complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="ERRORES"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ERROR" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DESCERROR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ERROR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ERRORES", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA", propOrder = {
        "error"
})
public class ERRORES {

    @XmlElement(name = "ERROR")
    protected List<ERROR> error;

    /**
     * Gets the value of the error property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the error property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getERROR().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ERROR }
     */
    public List<ERROR> getERROR() {
        if (error == null) {
            error = new ArrayList<ERROR>();
        }
        return this.error;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     *
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="DESCERROR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ERROR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "descerror",
            "error"
    })
    public static class ERROR {

        @XmlElement(name = "DESCERROR", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA")
        protected String descerror;
        @XmlElement(name = "ERROR", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA")
        protected String error;

        /**
         * Obtiene el valor de la propiedad descerror.
         *
         * @return possible object is
         * {@link String }
         */
        public String getDESCERROR() {
            return descerror;
        }

        /**
         * Define el valor de la propiedad descerror.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setDESCERROR(String value) {
            this.descerror = value;
        }

        /**
         * Obtiene el valor de la propiedad error.
         *
         * @return possible object is
         * {@link String }
         */
        public String getERROR() {
            return error;
        }

        /**
         * Define el valor de la propiedad error.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setERROR(String value) {
            this.error = value;
        }

    }

}
