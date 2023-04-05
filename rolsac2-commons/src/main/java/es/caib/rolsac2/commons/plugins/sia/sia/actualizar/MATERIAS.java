package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para MATERIAS complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="MATERIAS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MATERIA" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CODMATERIA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "MATERIAS", propOrder = {
        "materia"
})
public class MATERIAS {

    @XmlElement(name = "MATERIA")
    protected List<MATERIA> materia;

    /**
     * Gets the value of the materia property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the materia property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMATERIA().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MATERIA }
     */
    public List<MATERIA> getMATERIA() {
        if (materia == null) {
            materia = new ArrayList<MATERIA>();
        }
        return this.materia;
    }

    public void getMATERIA(List<MATERIA> materia) {
        this.materia = materia;
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
     *         &lt;element name="CODMATERIA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "codmateria"
    })
    public static class MATERIA {

        @XmlElement(name = "CODMATERIA", required = true)
        protected String codmateria;

        /**
         * Obtiene el valor de la propiedad codmateria.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODMATERIA() {
            return codmateria;
        }

        /**
         * Define el valor de la propiedad codmateria.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODMATERIA(String value) {
            this.codmateria = value;
        }

    }

}
