package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para SUBMATERIAS complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="SUBMATERIAS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SUBMATERIA" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CODSUBMATERIA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "SUBMATERIAS", propOrder = {
        "submateria"
})
public class SUBMATERIAS {

    @XmlElement(name = "SUBMATERIA")
    protected List<SUBMATERIA> submateria;

    /**
     * Gets the value of the submateria property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the submateria property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSUBMATERIA().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SUBMATERIA }
     */
    public List<SUBMATERIA> getSUBMATERIA() {
        if (submateria == null) {
            submateria = new ArrayList<SUBMATERIA>();
        }
        return this.submateria;
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
     *         &lt;element name="CODSUBMATERIA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "codsubmateria"
    })
    public static class SUBMATERIA {

        @XmlElement(name = "CODSUBMATERIA", required = true)
        protected String codsubmateria;

        /**
         * Obtiene el valor de la propiedad codsubmateria.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODSUBMATERIA() {
            return codsubmateria;
        }

        /**
         * Define el valor de la propiedad codsubmateria.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODSUBMATERIA(String value) {
            this.codsubmateria = value;
        }

    }

}
