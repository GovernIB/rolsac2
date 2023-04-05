package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para VOLUMENESTRAMITACIONES complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="VOLUMENESTRAMITACIONES"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VOLUMENTRAMITACIONES" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ANIO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="PERIODO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="VOLTOTAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="VOLELEC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="VOLCERTIFICADOELEC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="VOLDNIELEC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "VOLUMENESTRAMITACIONES", propOrder = {
        "volumentramitaciones"
})
public class VOLUMENESTRAMITACIONES {

    @XmlElement(name = "VOLUMENTRAMITACIONES")
    protected List<VOLUMENTRAMITACIONES> volumentramitaciones;

    /**
     * Gets the value of the volumentramitaciones property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the volumentramitaciones property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVOLUMENTRAMITACIONES().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VOLUMENTRAMITACIONES }
     */
    public List<VOLUMENTRAMITACIONES> getVOLUMENTRAMITACIONES() {
        if (volumentramitaciones == null) {
            volumentramitaciones = new ArrayList<VOLUMENTRAMITACIONES>();
        }
        return this.volumentramitaciones;
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
     *         &lt;element name="ANIO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="PERIODO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="VOLTOTAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="VOLELEC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="VOLCERTIFICADOELEC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="VOLDNIELEC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "voltotal",
            "volelec",
            "volcertificadoelec",
            "voldnielec"
    })
    public static class VOLUMENTRAMITACIONES {

        @XmlElement(name = "ANIO", required = true)
        protected String anio;
        @XmlElement(name = "PERIODO", required = true)
        protected String periodo;
        @XmlElement(name = "VOLTOTAL", required = true)
        protected String voltotal;
        @XmlElement(name = "VOLELEC", required = true)
        protected String volelec;
        @XmlElement(name = "VOLCERTIFICADOELEC", required = true)
        protected String volcertificadoelec;
        @XmlElement(name = "VOLDNIELEC", required = true)
        protected String voldnielec;

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
         * Obtiene el valor de la propiedad voltotal.
         *
         * @return possible object is
         * {@link String }
         */
        public String getVOLTOTAL() {
            return voltotal;
        }

        /**
         * Define el valor de la propiedad voltotal.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setVOLTOTAL(String value) {
            this.voltotal = value;
        }

        /**
         * Obtiene el valor de la propiedad volelec.
         *
         * @return possible object is
         * {@link String }
         */
        public String getVOLELEC() {
            return volelec;
        }

        /**
         * Define el valor de la propiedad volelec.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setVOLELEC(String value) {
            this.volelec = value;
        }

        /**
         * Obtiene el valor de la propiedad volcertificadoelec.
         *
         * @return possible object is
         * {@link String }
         */
        public String getVOLCERTIFICADOELEC() {
            return volcertificadoelec;
        }

        /**
         * Define el valor de la propiedad volcertificadoelec.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setVOLCERTIFICADOELEC(String value) {
            this.volcertificadoelec = value;
        }

        /**
         * Obtiene el valor de la propiedad voldnielec.
         *
         * @return possible object is
         * {@link String }
         */
        public String getVOLDNIELEC() {
            return voldnielec;
        }

        /**
         * Define el valor de la propiedad voldnielec.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setVOLDNIELEC(String value) {
            this.voldnielec = value;
        }

    }

}
