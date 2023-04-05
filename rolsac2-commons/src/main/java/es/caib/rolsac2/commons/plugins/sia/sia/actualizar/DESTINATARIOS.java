package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para DESTINATARIOS complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="DESTINATARIOS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DESTINATARIO" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CODDESTINATARIO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "DESTINATARIOS", propOrder = {
        "destinatario"
})
public class DESTINATARIOS {

    @XmlElement(name = "DESTINATARIO")
    protected List<DESTINATARIO> destinatario;

    /**
     * Gets the value of the destinatario property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatario property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDESTINATARIO().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DESTINATARIO }
     */
    public List<DESTINATARIO> getDESTINATARIO() {
        if (destinatario == null) {
            destinatario = new ArrayList<DESTINATARIO>();
        }
        return this.destinatario;
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
     *         &lt;element name="CODDESTINATARIO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "coddestinatario"
    })
    public static class DESTINATARIO {

        @XmlElement(name = "CODDESTINATARIO", required = true)
        protected String coddestinatario;

        /**
         * Obtiene el valor de la propiedad coddestinatario.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODDESTINATARIO() {
            return coddestinatario;
        }

        /**
         * Define el valor de la propiedad coddestinatario.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODDESTINATARIO(String value) {
            this.coddestinatario = value;
        }

    }

}
