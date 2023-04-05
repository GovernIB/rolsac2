package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para TRAMITESRELACIONADOS complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TRAMITESRELACIONADOS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TRAMITERELACIONADO" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="TRCODACTUACION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="TRORDEN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "TRAMITESRELACIONADOS", propOrder = {
        "tramiterelacionado"
})
public class TRAMITESRELACIONADOS {

    @XmlElement(name = "TRAMITERELACIONADO")
    protected List<TRAMITERELACIONADO> tramiterelacionado;

    /**
     * Gets the value of the tramiterelacionado property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tramiterelacionado property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTRAMITERELACIONADO().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TRAMITERELACIONADO }
     */
    public List<TRAMITERELACIONADO> getTRAMITERELACIONADO() {
        if (tramiterelacionado == null) {
            tramiterelacionado = new ArrayList<TRAMITERELACIONADO>();
        }
        return this.tramiterelacionado;
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
     *         &lt;element name="TRCODACTUACION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="TRORDEN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "trcodactuacion",
            "trorden"
    })
    public static class TRAMITERELACIONADO {

        @XmlElement(name = "TRCODACTUACION", required = true)
        protected String trcodactuacion;
        @XmlElement(name = "TRORDEN", required = true)
        protected String trorden;

        /**
         * Obtiene el valor de la propiedad trcodactuacion.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTRCODACTUACION() {
            return trcodactuacion;
        }

        /**
         * Define el valor de la propiedad trcodactuacion.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTRCODACTUACION(String value) {
            this.trcodactuacion = value;
        }

        /**
         * Obtiene el valor de la propiedad trorden.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTRORDEN() {
            return trorden;
        }

        /**
         * Define el valor de la propiedad trorden.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTRORDEN(String value) {
            this.trorden = value;
        }

    }

}
