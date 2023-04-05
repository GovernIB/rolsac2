package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para NORMATIVAS complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="NORMATIVAS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NORMATIVA" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CODRANGO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="NUMERODISPOSICION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="TITULO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "NORMATIVAS", propOrder = {
        "normativa"
})
public class NORMATIVAS {

    @XmlElement(name = "NORMATIVA")
    protected List<NORMATIVA> normativa;

    /**
     * Gets the value of the normativa property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the normativa property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNORMATIVA().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NORMATIVA }
     */
    public List<NORMATIVA> getNORMATIVA() {
        if (normativa == null) {
            normativa = new ArrayList<NORMATIVA>();
        }
        return this.normativa;
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
     *         &lt;element name="CODRANGO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="NUMERODISPOSICION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="TITULO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "codrango",
            "numerodisposicion",
            "titulo"
    })
    public static class NORMATIVA {

        @XmlElement(name = "CODRANGO", required = true)
        protected String codrango;
        @XmlElement(name = "NUMERODISPOSICION")
        protected String numerodisposicion;
        @XmlElement(name = "TITULO", required = true)
        protected String titulo;

        /**
         * Obtiene el valor de la propiedad codrango.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODRANGO() {
            return codrango;
        }

        /**
         * Define el valor de la propiedad codrango.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODRANGO(String value) {
            this.codrango = value;
        }

        /**
         * Obtiene el valor de la propiedad numerodisposicion.
         *
         * @return possible object is
         * {@link String }
         */
        public String getNUMERODISPOSICION() {
            return numerodisposicion;
        }

        /**
         * Define el valor de la propiedad numerodisposicion.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setNUMERODISPOSICION(String value) {
            this.numerodisposicion = value;
        }

        /**
         * Obtiene el valor de la propiedad titulo.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTITULO() {
            return titulo;
        }

        /**
         * Define el valor de la propiedad titulo.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTITULO(String value) {
            this.titulo = value;
        }

    }

}
