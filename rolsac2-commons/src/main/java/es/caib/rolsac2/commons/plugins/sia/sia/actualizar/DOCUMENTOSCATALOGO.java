package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para DOCUMENTOSCATALOGO complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="DOCUMENTOSCATALOGO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTOCATALOGO" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CODDOCUMENTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="OBLIGADOAPORTARLOINTERESADO"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                       &lt;/union&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
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
@XmlType(name = "DOCUMENTOSCATALOGO", propOrder = {
        "documentocatalogo"
})
public class DOCUMENTOSCATALOGO {

    @XmlElement(name = "DOCUMENTOCATALOGO")
    protected List<DOCUMENTOCATALOGO> documentocatalogo;

    /**
     * Gets the value of the documentocatalogo property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentocatalogo property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDOCUMENTOCATALOGO().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DOCUMENTOCATALOGO }
     */
    public List<DOCUMENTOCATALOGO> getDOCUMENTOCATALOGO() {
        if (documentocatalogo == null) {
            documentocatalogo = new ArrayList<DOCUMENTOCATALOGO>();
        }
        return this.documentocatalogo;
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
     *         &lt;element name="CODDOCUMENTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="OBLIGADOAPORTARLOINTERESADO"&gt;
     *           &lt;simpleType&gt;
     *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *             &lt;/union&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "coddocumento",
            "obligadoaportarlointeresado"
    })
    public static class DOCUMENTOCATALOGO {

        @XmlElement(name = "CODDOCUMENTO", required = true)
        protected String coddocumento;
        @XmlElement(name = "OBLIGADOAPORTARLOINTERESADO", required = true, nillable = true)
        protected String obligadoaportarlointeresado;

        /**
         * Obtiene el valor de la propiedad coddocumento.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODDOCUMENTO() {
            return coddocumento;
        }

        /**
         * Define el valor de la propiedad coddocumento.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODDOCUMENTO(String value) {
            this.coddocumento = value;
        }

        /**
         * Obtiene el valor de la propiedad obligadoaportarlointeresado.
         *
         * @return possible object is
         * {@link String }
         */
        public String getOBLIGADOAPORTARLOINTERESADO() {
            return obligadoaportarlointeresado;
        }

        /**
         * Define el valor de la propiedad obligadoaportarlointeresado.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setOBLIGADOAPORTARLOINTERESADO(String value) {
            this.obligadoaportarlointeresado = value;
        }

    }

}
