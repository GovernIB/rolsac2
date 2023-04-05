package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para NOTIFICACIONES complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="NOTIFICACIONES"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NOTIFICACION" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CODNOTIFICACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "NOTIFICACIONES", propOrder = {
        "notificacion"
})
public class NOTIFICACIONES {

    @XmlElement(name = "NOTIFICACION")
    protected List<NOTIFICACION> notificacion;

    /**
     * Gets the value of the notificacion property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notificacion property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNOTIFICACION().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NOTIFICACION }
     */
    public List<NOTIFICACION> getNOTIFICACION() {
        if (notificacion == null) {
            notificacion = new ArrayList<NOTIFICACION>();
        }
        return this.notificacion;
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
     *         &lt;element name="CODNOTIFICACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "codnotificacion"
    })
    public static class NOTIFICACION {

        @XmlElement(name = "CODNOTIFICACION")
        protected String codnotificacion;

        /**
         * Obtiene el valor de la propiedad codnotificacion.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODNOTIFICACION() {
            return codnotificacion;
        }

        /**
         * Define el valor de la propiedad codnotificacion.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODNOTIFICACION(String value) {
            this.codnotificacion = value;
        }

    }

}
