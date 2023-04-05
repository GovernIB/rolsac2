package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para FORMULARIOS complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="FORMULARIOS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FORMULARIO" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="TITULOFORMUL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="URLFORMUL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "FORMULARIOS", propOrder = {
        "formulario"
})
public class FORMULARIOS {

    @XmlElement(name = "FORMULARIO")
    protected List<FORMULARIO> formulario;

    /**
     * Gets the value of the formulario property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formulario property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFORMULARIO().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FORMULARIO }
     */
    public List<FORMULARIO> getFORMULARIO() {
        if (formulario == null) {
            formulario = new ArrayList<FORMULARIO>();
        }
        return this.formulario;
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
     *         &lt;element name="TITULOFORMUL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="URLFORMUL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "tituloformul",
            "urlformul"
    })
    public static class FORMULARIO {

        @XmlElement(name = "TITULOFORMUL", required = true)
        protected String tituloformul;
        @XmlElement(name = "URLFORMUL", required = true)
        protected String urlformul;

        /**
         * Obtiene el valor de la propiedad tituloformul.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTITULOFORMUL() {
            return tituloformul;
        }

        /**
         * Define el valor de la propiedad tituloformul.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTITULOFORMUL(String value) {
            this.tituloformul = value;
        }

        /**
         * Obtiene el valor de la propiedad urlformul.
         *
         * @return possible object is
         * {@link String }
         */
        public String getURLFORMUL() {
            return urlformul;
        }

        /**
         * Define el valor de la propiedad urlformul.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setURLFORMUL(String value) {
            this.urlformul = value;
        }

    }

}
