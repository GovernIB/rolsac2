package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para SISTEMASIDENTIFICACION complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="SISTEMASIDENTIFICACION"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SISTEMAIDENTIFICACION" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CODSISTEMAIDENTIFICACION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="OTROSISTEMAIDENTIFICACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "SISTEMASIDENTIFICACION", propOrder = {
        "sistemaidentificacion"
})
public class SISTEMASIDENTIFICACION {

    @XmlElement(name = "SISTEMAIDENTIFICACION")
    protected List<SISTEMAIDENTIFICACION> sistemaidentificacion;

    /**
     * Gets the value of the sistemaidentificacion property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sistemaidentificacion property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSISTEMAIDENTIFICACION().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SISTEMAIDENTIFICACION }
     */
    public List<SISTEMAIDENTIFICACION> getSISTEMAIDENTIFICACION() {
        if (sistemaidentificacion == null) {
            sistemaidentificacion = new ArrayList<SISTEMAIDENTIFICACION>();
        }
        return this.sistemaidentificacion;
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
     *         &lt;element name="CODSISTEMAIDENTIFICACION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="OTROSISTEMAIDENTIFICACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "codsistemaidentificacion",
            "otrosistemaidentificacion"
    })
    public static class SISTEMAIDENTIFICACION {

        @XmlElement(name = "CODSISTEMAIDENTIFICACION", required = true)
        protected String codsistemaidentificacion;
        @XmlElement(name = "OTROSISTEMAIDENTIFICACION")
        protected String otrosistemaidentificacion;

        /**
         * Obtiene el valor de la propiedad codsistemaidentificacion.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODSISTEMAIDENTIFICACION() {
            return codsistemaidentificacion;
        }

        /**
         * Define el valor de la propiedad codsistemaidentificacion.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODSISTEMAIDENTIFICACION(String value) {
            this.codsistemaidentificacion = value;
        }

        /**
         * Obtiene el valor de la propiedad otrosistemaidentificacion.
         *
         * @return possible object is
         * {@link String }
         */
        public String getOTROSISTEMAIDENTIFICACION() {
            return otrosistemaidentificacion;
        }

        /**
         * Define el valor de la propiedad otrosistemaidentificacion.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setOTROSISTEMAIDENTIFICACION(String value) {
            this.otrosistemaidentificacion = value;
        }

    }

}
