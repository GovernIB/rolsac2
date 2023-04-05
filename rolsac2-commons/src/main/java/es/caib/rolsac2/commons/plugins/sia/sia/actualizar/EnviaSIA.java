package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="ACTUACIONES" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ACTUACION" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="CORRECTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ERRORES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA}ERRORES" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="CODIGOORIGEN" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="OPERACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="CODIGOACTUACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
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
@XmlType(name = "", propOrder = {
        "actuaciones"
})
@XmlRootElement(name = "enviaSIA", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA")
public class EnviaSIA {

    @XmlElement(name = "ACTUACIONES", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA")
    protected ACTUACIONES actuaciones;

    /**
     * Obtiene el valor de la propiedad actuaciones.
     *
     * @return possible object is
     * {@link ACTUACIONES }
     */
    public ACTUACIONES getACTUACIONES() {
        return actuaciones;
    }

    /**
     * Define el valor de la propiedad actuaciones.
     *
     * @param value allowed object is
     *              {@link ACTUACIONES }
     */
    public void setACTUACIONES(ACTUACIONES value) {
        this.actuaciones = value;
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
     *         &lt;element name="ACTUACION" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="CORRECTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ERRORES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA}ERRORES" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="CODIGOORIGEN" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="OPERACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="CODIGOACTUACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
    @XmlType(name = "", propOrder = {
            "actuacion"
    })
    public static class ACTUACIONES {

        @XmlElement(name = "ACTUACION", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA", required = true)
        protected List<ACTUACION> actuacion;

        /**
         * Gets the value of the actuacion property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the actuacion property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getACTUACION().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ACTUACION }
         */
        public List<ACTUACION> getACTUACION() {
            if (actuacion == null) {
                actuacion = new ArrayList<ACTUACION>();
            }
            return this.actuacion;
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
         *         &lt;element name="CORRECTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ERRORES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA}ERRORES" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="CODIGOORIGEN" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="OPERACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="CODIGOACTUACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "correcto",
                "errores"
        })
        public static class ACTUACION {

            @XmlElement(name = "CORRECTO", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA", required = true)
            protected String correcto;
            @XmlElement(name = "ERRORES", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/EnviaSIA")
            protected ERRORES errores;
            @XmlAttribute(name = "CODIGOORIGEN", required = true)
            protected String codigoorigen;
            @XmlAttribute(name = "OPERACION", required = true)
            protected String operacion;
            @XmlAttribute(name = "CODIGOACTUACION", required = true)
            protected String codigoactuacion;

            /**
             * Obtiene el valor de la propiedad correcto.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCORRECTO() {
                return correcto;
            }

            /**
             * Define el valor de la propiedad correcto.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCORRECTO(String value) {
                this.correcto = value;
            }

            /**
             * Obtiene el valor de la propiedad errores.
             *
             * @return possible object is
             * {@link ERRORES }
             */
            public ERRORES getERRORES() {
                return errores;
            }

            /**
             * Define el valor de la propiedad errores.
             *
             * @param value allowed object is
             *              {@link ERRORES }
             */
            public void setERRORES(ERRORES value) {
                this.errores = value;
            }

            /**
             * Obtiene el valor de la propiedad codigoorigen.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCODIGOORIGEN() {
                return codigoorigen;
            }

            /**
             * Define el valor de la propiedad codigoorigen.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCODIGOORIGEN(String value) {
                this.codigoorigen = value;
            }

            /**
             * Obtiene el valor de la propiedad operacion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getOPERACION() {
                return operacion;
            }

            /**
             * Define el valor de la propiedad operacion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setOPERACION(String value) {
                this.operacion = value;
            }

            /**
             * Obtiene el valor de la propiedad codigoactuacion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCODIGOACTUACION() {
                return codigoactuacion;
            }

            /**
             * Define el valor de la propiedad codigoactuacion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCODIGOACTUACION(String value) {
                this.codigoactuacion = value;
            }

        }

    }

}
