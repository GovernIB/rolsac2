package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para VOLUMENNOTIFICACIONES complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="VOLUMENNOTIFICACIONES"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VOLUMENNOTIFICACION" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ANIO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="PERIODO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="NUMPAPEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="NUMCOMPARECENCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="NUMDEH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "VOLUMENNOTIFICACIONES", propOrder = {
        "volumennotificacion"
})
public class VOLUMENNOTIFICACIONES {

    @XmlElement(name = "VOLUMENNOTIFICACION")
    protected List<VOLUMENNOTIFICACION> volumennotificacion;

    /**
     * Gets the value of the volumennotificacion property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the volumennotificacion property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVOLUMENNOTIFICACION().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VOLUMENNOTIFICACION }
     */
    public List<VOLUMENNOTIFICACION> getVOLUMENNOTIFICACION() {
        if (volumennotificacion == null) {
            volumennotificacion = new ArrayList<VOLUMENNOTIFICACION>();
        }
        return this.volumennotificacion;
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
     *         &lt;element name="ANIO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="PERIODO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="NUMPAPEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="NUMCOMPARECENCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="NUMDEH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "anio",
            "periodo",
            "numpapel",
            "numcomparecencia",
            "numdeh"
    })
    public static class VOLUMENNOTIFICACION {

        @XmlElement(name = "ANIO")
        protected String anio;
        @XmlElement(name = "PERIODO")
        protected String periodo;
        @XmlElement(name = "NUMPAPEL")
        protected String numpapel;
        @XmlElement(name = "NUMCOMPARECENCIA")
        protected String numcomparecencia;
        @XmlElement(name = "NUMDEH")
        protected String numdeh;

        /**
         * Obtiene el valor de la propiedad anio.
         *
         * @return possible object is
         * {@link String }
         */
        public String getANIO() {
            return anio;
        }

        /**
         * Define el valor de la propiedad anio.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setANIO(String value) {
            this.anio = value;
        }

        /**
         * Obtiene el valor de la propiedad periodo.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPERIODO() {
            return periodo;
        }

        /**
         * Define el valor de la propiedad periodo.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPERIODO(String value) {
            this.periodo = value;
        }

        /**
         * Obtiene el valor de la propiedad numpapel.
         *
         * @return possible object is
         * {@link String }
         */
        public String getNUMPAPEL() {
            return numpapel;
        }

        /**
         * Define el valor de la propiedad numpapel.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setNUMPAPEL(String value) {
            this.numpapel = value;
        }

        /**
         * Obtiene el valor de la propiedad numcomparecencia.
         *
         * @return possible object is
         * {@link String }
         */
        public String getNUMCOMPARECENCIA() {
            return numcomparecencia;
        }

        /**
         * Define el valor de la propiedad numcomparecencia.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setNUMCOMPARECENCIA(String value) {
            this.numcomparecencia = value;
        }

        /**
         * Obtiene el valor de la propiedad numdeh.
         *
         * @return possible object is
         * {@link String }
         */
        public String getNUMDEH() {
            return numdeh;
        }

        /**
         * Define el valor de la propiedad numdeh.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setNUMDEH(String value) {
            this.numdeh = value;
        }

    }

}
