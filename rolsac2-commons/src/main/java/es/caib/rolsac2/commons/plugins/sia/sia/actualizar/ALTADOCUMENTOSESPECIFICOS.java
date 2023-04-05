package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ALTADOCUMENTOSESPECIFICOS complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="ALTADOCUMENTOSESPECIFICOS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ALTADOCUMENTOESPECIFICO" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="NOMBREDOCUMENTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CODENTIDADEMISORA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CODORGARESPN1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CODORGARESPN2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="CODORGAEMISORN1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="CODORGAEMISORN2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "ALTADOCUMENTOSESPECIFICOS", propOrder = {
        "altadocumentoespecifico"
})
public class ALTADOCUMENTOSESPECIFICOS {

    @XmlElement(name = "ALTADOCUMENTOESPECIFICO")
    protected List<ALTADOCUMENTOESPECIFICO> altadocumentoespecifico;

    /**
     * Gets the value of the altadocumentoespecifico property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altadocumentoespecifico property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getALTADOCUMENTOESPECIFICO().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ALTADOCUMENTOESPECIFICO }
     */
    public List<ALTADOCUMENTOESPECIFICO> getALTADOCUMENTOESPECIFICO() {
        if (altadocumentoespecifico == null) {
            altadocumentoespecifico = new ArrayList<ALTADOCUMENTOESPECIFICO>();
        }
        return this.altadocumentoespecifico;
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
     *         &lt;element name="NOMBREDOCUMENTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CODENTIDADEMISORA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CODORGARESPN1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CODORGARESPN2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="CODORGAEMISORN1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="CODORGAEMISORN2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "nombredocumento",
            "codentidademisora",
            "codorgarespn1",
            "codorgarespn2",
            "codorgaemisorn1",
            "codorgaemisorn2",
            "obligadoaportarlointeresado"
    })
    public static class ALTADOCUMENTOESPECIFICO {

        @XmlElement(name = "NOMBREDOCUMENTO", required = true)
        protected String nombredocumento;
        @XmlElement(name = "CODENTIDADEMISORA", required = true)
        protected String codentidademisora;
        @XmlElement(name = "CODORGARESPN1", required = true)
        protected String codorgarespn1;
        @XmlElement(name = "CODORGARESPN2")
        protected String codorgarespn2;
        @XmlElement(name = "CODORGAEMISORN1")
        protected String codorgaemisorn1;
        @XmlElement(name = "CODORGAEMISORN2")
        protected String codorgaemisorn2;
        @XmlElement(name = "OBLIGADOAPORTARLOINTERESADO", required = true, nillable = true)
        protected String obligadoaportarlointeresado;

        /**
         * Obtiene el valor de la propiedad nombredocumento.
         *
         * @return possible object is
         * {@link String }
         */
        public String getNOMBREDOCUMENTO() {
            return nombredocumento;
        }

        /**
         * Define el valor de la propiedad nombredocumento.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setNOMBREDOCUMENTO(String value) {
            this.nombredocumento = value;
        }

        /**
         * Obtiene el valor de la propiedad codentidademisora.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODENTIDADEMISORA() {
            return codentidademisora;
        }

        /**
         * Define el valor de la propiedad codentidademisora.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODENTIDADEMISORA(String value) {
            this.codentidademisora = value;
        }

        /**
         * Obtiene el valor de la propiedad codorgarespn1.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODORGARESPN1() {
            return codorgarespn1;
        }

        /**
         * Define el valor de la propiedad codorgarespn1.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODORGARESPN1(String value) {
            this.codorgarespn1 = value;
        }

        /**
         * Obtiene el valor de la propiedad codorgarespn2.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODORGARESPN2() {
            return codorgarespn2;
        }

        /**
         * Define el valor de la propiedad codorgarespn2.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODORGARESPN2(String value) {
            this.codorgarespn2 = value;
        }

        /**
         * Obtiene el valor de la propiedad codorgaemisorn1.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODORGAEMISORN1() {
            return codorgaemisorn1;
        }

        /**
         * Define el valor de la propiedad codorgaemisorn1.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODORGAEMISORN1(String value) {
            this.codorgaemisorn1 = value;
        }

        /**
         * Obtiene el valor de la propiedad codorgaemisorn2.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCODORGAEMISORN2() {
            return codorgaemisorn2;
        }

        /**
         * Define el valor de la propiedad codorgaemisorn2.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCODORGAEMISORN2(String value) {
            this.codorgaemisorn2 = value;
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
