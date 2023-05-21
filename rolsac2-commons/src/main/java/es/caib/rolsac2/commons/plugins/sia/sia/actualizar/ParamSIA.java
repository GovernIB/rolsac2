package es.caib.rolsac2.commons.plugins.sia.sia.actualizar;

import javax.xml.bind.JAXBElement;
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
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="certificado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ACTUACIONES" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ACTUACION" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="INTERNO" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="ESCOMUN" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="ParamSIAACTUACIONESACTUACIONACTIVO" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="TIPOTRAMITE" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}TIPOTRAMITE {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="TITULOCIUDADANO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="DENOMINACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="DESCRIPCION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ORGANISMORESPONSABLE" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}ORGANISMORESPONSABLE" minOccurs="0"/&gt;
 *                             &lt;element name="DESTINATARIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}DESTINATARIOS" minOccurs="0"/&gt;
 *                             &lt;element name="SUJETOATASAS" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="PERIODICIDAD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="UNIDADGESTORATRAMITE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="NOTIFICACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}NOTIFICACIONES" minOccurs="0"/&gt;
 *                             &lt;element name="CODNIVELADMINISTRACIONELECTRONICA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="SISTEMASIDENTIFICACION" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}SISTEMASIDENTIFICACION" minOccurs="0"/&gt;
 *                             &lt;element name="CANALACCESO" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                             &lt;element name="FORMULARIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}FORMULARIOS" minOccurs="0"/&gt;
 *                             &lt;element name="ENLACEWEB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ESRESPONSIVE" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="PORTAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="REQUISITOSINICIACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="PRESENCIALNOADAPTABLE" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="DISPONIBLEFUNCIONARIOHABILITADO" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="DISPONIBLEAPODERADOHABILITADO" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="CODREQUISITOSIDENTPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="CODREQUISITOSIDENTPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="IDINTEGRADOCLAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="OBSERVACIONINTEGRADOCLAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="VOLUMENESTRAMITACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}VOLUMENESTRAMITACIONES" minOccurs="0"/&gt;
 *                             &lt;element name="TIEMPOMEDIORESOLUCION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="VOLUMENNOTIFICACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}VOLUMENNOTIFICACIONES" minOccurs="0"/&gt;
 *                             &lt;element name="MATERIAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}MATERIAS" minOccurs="0"/&gt;
 *                             &lt;element name="SUBMATERIAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}SUBMATERIAS" minOccurs="0"/&gt;
 *                             &lt;element name="CODCLASETRAMITE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="TRAMITESRELACIONADOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}TRAMITESRELACIONADOS" minOccurs="0"/&gt;
 *                             &lt;element name="NOREQUIEREDOCUMENTACION" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="DOCUMENTACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ALTADOCUMENTOSESPECIFICOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}ALTADOCUMENTOSESPECIFICOS" minOccurs="0"/&gt;
 *                             &lt;element name="DOCUMENTOSCATALOGO" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}DOCUMENTOSCATALOGO" minOccurs="0"/&gt;
 *                             &lt;element name="INICIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}INICIOS" minOccurs="0"/&gt;
 *                             &lt;element name="FINVIA" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
 *                                 &lt;/union&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="PLAZORESOLUCION" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}PLAZORESOLUCION" minOccurs="0"/&gt;
 *                             &lt;element name="NORMATIVAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}NORMATIVAS" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="CODIGOORIGEN" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="CODIGOACTUACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="OPERACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
@XmlType(name = "", propOrder = {"user", "password", "certificado", "actuaciones"})
@XmlRootElement(name = "paramSIA")
public class ParamSIA {

    protected String user;
    protected String password;
    protected String certificado;
    @XmlElement(name = "ACTUACIONES")
    protected ACTUACIONES actuaciones;

    /**
     * Obtiene el valor de la propiedad user.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad password.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define el valor de la propiedad password.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Obtiene el valor de la propiedad certificado.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCertificado() {
        return certificado;
    }

    /**
     * Define el valor de la propiedad certificado.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCertificado(String value) {
        this.certificado = value;
    }

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
     *         &lt;element name="ACTUACION" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="INTERNO" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="ESCOMUN" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="ParamSIAACTUACIONESACTUACIONACTIVO" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="TIPOTRAMITE" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}TIPOTRAMITE {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="TITULOCIUDADANO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="DENOMINACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="DESCRIPCION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ORGANISMORESPONSABLE" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}ORGANISMORESPONSABLE" minOccurs="0"/&gt;
     *                   &lt;element name="DESTINATARIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}DESTINATARIOS" minOccurs="0"/&gt;
     *                   &lt;element name="SUJETOATASAS" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="PERIODICIDAD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="UNIDADGESTORATRAMITE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="NOTIFICACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}NOTIFICACIONES" minOccurs="0"/&gt;
     *                   &lt;element name="CODNIVELADMINISTRACIONELECTRONICA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="SISTEMASIDENTIFICACION" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}SISTEMASIDENTIFICACION" minOccurs="0"/&gt;
     *                   &lt;element name="CANALACCESO" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                   &lt;element name="FORMULARIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}FORMULARIOS" minOccurs="0"/&gt;
     *                   &lt;element name="ENLACEWEB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ESRESPONSIVE" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="PORTAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="REQUISITOSINICIACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="PRESENCIALNOADAPTABLE" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="DISPONIBLEFUNCIONARIOHABILITADO" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="DISPONIBLEAPODERADOHABILITADO" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="CODREQUISITOSIDENTPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="CODREQUISITOSIDENTPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="IDINTEGRADOCLAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="OBSERVACIONINTEGRADOCLAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="VOLUMENESTRAMITACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}VOLUMENESTRAMITACIONES" minOccurs="0"/&gt;
     *                   &lt;element name="TIEMPOMEDIORESOLUCION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="VOLUMENNOTIFICACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}VOLUMENNOTIFICACIONES" minOccurs="0"/&gt;
     *                   &lt;element name="MATERIAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}MATERIAS" minOccurs="0"/&gt;
     *                   &lt;element name="SUBMATERIAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}SUBMATERIAS" minOccurs="0"/&gt;
     *                   &lt;element name="CODCLASETRAMITE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="TRAMITESRELACIONADOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}TRAMITESRELACIONADOS" minOccurs="0"/&gt;
     *                   &lt;element name="NOREQUIEREDOCUMENTACION" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="DOCUMENTACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ALTADOCUMENTOSESPECIFICOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}ALTADOCUMENTOSESPECIFICOS" minOccurs="0"/&gt;
     *                   &lt;element name="DOCUMENTOSCATALOGO" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}DOCUMENTOSCATALOGO" minOccurs="0"/&gt;
     *                   &lt;element name="INICIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}INICIOS" minOccurs="0"/&gt;
     *                   &lt;element name="FINVIA" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
     *                       &lt;/union&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="PLAZORESOLUCION" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}PLAZORESOLUCION" minOccurs="0"/&gt;
     *                   &lt;element name="NORMATIVAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}NORMATIVAS" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="CODIGOORIGEN" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="CODIGOACTUACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="OPERACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
    @XmlType(name = "", propOrder = {"actuacion"})
    public static class ACTUACIONES {

        @XmlElement(name = "ACTUACION")
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
         *         &lt;element name="INTERNO" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="ESCOMUN" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="ParamSIAACTUACIONESACTUACIONACTIVO" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="TIPOTRAMITE" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}TIPOTRAMITE {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="TITULOCIUDADANO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="DENOMINACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="DESCRIPCION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ORGANISMORESPONSABLE" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}ORGANISMORESPONSABLE" minOccurs="0"/&gt;
         *         &lt;element name="DESTINATARIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}DESTINATARIOS" minOccurs="0"/&gt;
         *         &lt;element name="SUJETOATASAS" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="PERIODICIDAD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="UNIDADGESTORATRAMITE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="NOTIFICACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}NOTIFICACIONES" minOccurs="0"/&gt;
         *         &lt;element name="CODNIVELADMINISTRACIONELECTRONICA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="SISTEMASIDENTIFICACION" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}SISTEMASIDENTIFICACION" minOccurs="0"/&gt;
         *         &lt;element name="CANALACCESO" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
         *         &lt;element name="FORMULARIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}FORMULARIOS" minOccurs="0"/&gt;
         *         &lt;element name="ENLACEWEB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ESRESPONSIVE" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="PORTAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="REQUISITOSINICIACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="PRESENCIALNOADAPTABLE" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="DISPONIBLEFUNCIONARIOHABILITADO" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="DISPONIBLEAPODERADOHABILITADO" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="CODREQUISITOSIDENTPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="CODREQUISITOSIDENTPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="IDINTEGRADOCLAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="OBSERVACIONINTEGRADOCLAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="VOLUMENESTRAMITACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}VOLUMENESTRAMITACIONES" minOccurs="0"/&gt;
         *         &lt;element name="TIEMPOMEDIORESOLUCION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="VOLUMENNOTIFICACIONES" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}VOLUMENNOTIFICACIONES" minOccurs="0"/&gt;
         *         &lt;element name="MATERIAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}MATERIAS" minOccurs="0"/&gt;
         *         &lt;element name="SUBMATERIAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}SUBMATERIAS" minOccurs="0"/&gt;
         *         &lt;element name="CODCLASETRAMITE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="TRAMITESRELACIONADOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}TRAMITESRELACIONADOS" minOccurs="0"/&gt;
         *         &lt;element name="NOREQUIEREDOCUMENTACION" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="DOCUMENTACION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ALTADOCUMENTOSESPECIFICOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}ALTADOCUMENTOSESPECIFICOS" minOccurs="0"/&gt;
         *         &lt;element name="DOCUMENTOSCATALOGO" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}DOCUMENTOSCATALOGO" minOccurs="0"/&gt;
         *         &lt;element name="INICIOS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}INICIOS" minOccurs="0"/&gt;
         *         &lt;element name="FINVIA" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;union memberTypes=" {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}booleano {http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}emptyString"&gt;
         *             &lt;/union&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="PLAZORESOLUCION" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}PLAZORESOLUCION" minOccurs="0"/&gt;
         *         &lt;element name="NORMATIVAS" type="{http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA}NORMATIVAS" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="CODIGOORIGEN" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="CODIGOACTUACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="OPERACION" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"interno", "escomun", "activo", "tipotramite", "titulociudadano", "denominacion", "descripcion", "organismoresponsable", "destinatarios", "sujetoatasas", "periodicidad", "unidadgestoratramite", "notificaciones", "codniveladministracionelectronica", "sistemasidentificacion", "canalacceso", "formularios", "enlaceweb", "esresponsive", "portal", "requisitosiniciacion", "presencialnoadaptable", "disponiblefuncionariohabilitado", "disponibleapoderadohabilitado", "codrequisitosidentpj", "codrequisitosidentpf", "idintegradoclave", "observacionintegradoclave", "volumenestramitaciones", "tiempomedioresolucion", "volumennotificaciones", "materias", "submaterias", "codclasetramite", "tramitesrelacionados", "norequieredocumentacion", "documentacion", "altadocumentosespecificos", "documentoscatalogo", "inicios", "finvia", "plazoresolucion", "normativas"})
        public static class ACTUACION {

            @XmlElement(name = "INTERNO")
            protected String interno;
            @XmlElement(name = "ESCOMUN")
            protected String escomun;
            @XmlElement(name = "ACTIVO")
            protected String activo;
            @XmlElement(name = "TIPOTRAMITE")
            protected String tipotramite;
            @XmlElement(name = "TITULOCIUDADANO")
            protected String titulociudadano;
            @XmlElement(name = "DENOMINACION")
            protected String denominacion;
            @XmlElement(name = "DESCRIPCION")
            protected String descripcion;
            @XmlElement(name = "ORGANISMORESPONSABLE")
            protected ORGANISMORESPONSABLE organismoresponsable;
            @XmlElement(name = "DESTINATARIOS")
            protected DESTINATARIOS destinatarios;
            @XmlElement(name = "SUJETOATASAS")
            protected String sujetoatasas;
            @XmlElement(name = "PERIODICIDAD")
            protected String periodicidad;
            @XmlElement(name = "UNIDADGESTORATRAMITE")
            protected String unidadgestoratramite;
            @XmlElement(name = "NOTIFICACIONES")
            protected NOTIFICACIONES notificaciones;
            @XmlElement(name = "CODNIVELADMINISTRACIONELECTRONICA")
            protected String codniveladministracionelectronica;
            @XmlElement(name = "SISTEMASIDENTIFICACION")
            protected SISTEMASIDENTIFICACION sistemasidentificacion;
            @XmlElement(name = "CANALACCESO")
            protected List<String> canalacceso;
            @XmlElement(name = "FORMULARIOS")
            protected FORMULARIOS formularios;
            @XmlElement(name = "ENLACEWEB")
            protected String enlaceweb;
            @XmlElement(name = "ESRESPONSIVE")
            protected String esresponsive;
            @XmlElement(name = "PORTAL")
            protected String portal;
            @XmlElement(name = "REQUISITOSINICIACION")
            protected String requisitosiniciacion;
            @XmlElementRef(name = "PRESENCIALNOADAPTABLE", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA", type = JAXBElement.class, required = false)
            protected JAXBElement<String> presencialnoadaptable;
            @XmlElementRef(name = "DISPONIBLEFUNCIONARIOHABILITADO", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA", type = JAXBElement.class, required = false)
            protected JAXBElement<String> disponiblefuncionariohabilitado;
            @XmlElementRef(name = "DISPONIBLEAPODERADOHABILITADO", namespace = "http://www.map.es/sgca/actualizar/messages/v3_1/ParamSIA", type = JAXBElement.class, required = false)
            protected JAXBElement<String> disponibleapoderadohabilitado;
            @XmlElement(name = "CODREQUISITOSIDENTPJ")
            protected String codrequisitosidentpj;
            @XmlElement(name = "CODREQUISITOSIDENTPF")
            protected String codrequisitosidentpf;
            @XmlElement(name = "IDINTEGRADOCLAVE")
            protected String idintegradoclave;
            @XmlElement(name = "OBSERVACIONINTEGRADOCLAVE")
            protected String observacionintegradoclave;
            @XmlElement(name = "VOLUMENESTRAMITACIONES")
            protected VOLUMENESTRAMITACIONES volumenestramitaciones;
            @XmlElement(name = "TIEMPOMEDIORESOLUCION")
            protected String tiempomedioresolucion;
            @XmlElement(name = "VOLUMENNOTIFICACIONES")
            protected VOLUMENNOTIFICACIONES volumennotificaciones;
            @XmlElement(name = "MATERIAS")
            protected MATERIAS materias;
            @XmlElement(name = "SUBMATERIAS")
            protected SUBMATERIAS submaterias;
            @XmlElement(name = "CODCLASETRAMITE")
            protected String codclasetramite;
            @XmlElement(name = "TRAMITESRELACIONADOS")
            protected TRAMITESRELACIONADOS tramitesrelacionados;
            @XmlElement(name = "NOREQUIEREDOCUMENTACION")
            protected String norequieredocumentacion;
            @XmlElement(name = "DOCUMENTACION")
            protected String documentacion;
            @XmlElement(name = "ALTADOCUMENTOSESPECIFICOS")
            protected ALTADOCUMENTOSESPECIFICOS altadocumentosespecificos;
            @XmlElement(name = "DOCUMENTOSCATALOGO")
            protected DOCUMENTOSCATALOGO documentoscatalogo;
            @XmlElement(name = "INICIOS")
            protected INICIOS inicios;
            @XmlElement(name = "FINVIA")
            protected String finvia;
            @XmlElement(name = "PLAZORESOLUCION")
            protected PLAZORESOLUCION plazoresolucion;
            @XmlElement(name = "NORMATIVAS")
            protected NORMATIVAS normativas;
            @XmlAttribute(name = "CODIGOORIGEN")
            protected String codigoorigen;
            @XmlAttribute(name = "CODIGOACTUACION", required = true)
            protected String codigoactuacion;
            @XmlAttribute(name = "OPERACION", required = true)
            protected String operacion;

            /**
             * Obtiene el valor de la propiedad interno.
             *
             * @return possible object is
             * {@link String }
             */
            public String getINTERNO() {
                return interno;
            }

            /**
             * Define el valor de la propiedad interno.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setINTERNO(String value) {
                this.interno = value;
            }

            /**
             * Obtiene el valor de la propiedad escomun.
             *
             * @return possible object is
             * {@link String }
             */
            public String getESCOMUN() {
                return escomun;
            }

            /**
             * Define el valor de la propiedad escomun.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setESCOMUN(String value) {
                this.escomun = value;
            }

            /**
             * Obtiene el valor de la propiedad activo.
             *
             * @return possible object is
             * {@link String }
             */
            public String getACTIVO() {
                return activo;
            }

            /**
             * Define el valor de la propiedad activo.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setACTIVO(String value) {
                this.activo = value;
            }

            /**
             * Obtiene el valor de la propiedad tipotramite.
             *
             * @return possible object is
             * {@link String }
             */
            public String getTIPOTRAMITE() {
                return tipotramite;
            }

            /**
             * Define el valor de la propiedad tipotramite.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setTIPOTRAMITE(String value) {
                this.tipotramite = value;
            }

            /**
             * Obtiene el valor de la propiedad titulociudadano.
             *
             * @return possible object is
             * {@link String }
             */
            public String getTITULOCIUDADANO() {
                return titulociudadano;
            }

            /**
             * Define el valor de la propiedad titulociudadano.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setTITULOCIUDADANO(String value) {
                this.titulociudadano = value;
            }

            /**
             * Obtiene el valor de la propiedad denominacion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getDENOMINACION() {
                return denominacion;
            }

            /**
             * Define el valor de la propiedad denominacion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setDENOMINACION(String value) {
                this.denominacion = value;
            }

            /**
             * Obtiene el valor de la propiedad descripcion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getDESCRIPCION() {
                return descripcion;
            }

            /**
             * Define el valor de la propiedad descripcion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setDESCRIPCION(String value) {
                this.descripcion = value;
            }

            /**
             * Obtiene el valor de la propiedad organismoresponsable.
             *
             * @return possible object is
             * {@link ORGANISMORESPONSABLE }
             */
            public ORGANISMORESPONSABLE getORGANISMORESPONSABLE() {
                return organismoresponsable;
            }

            /**
             * Define el valor de la propiedad organismoresponsable.
             *
             * @param value allowed object is
             *              {@link ORGANISMORESPONSABLE }
             */
            public void setORGANISMORESPONSABLE(ORGANISMORESPONSABLE value) {
                this.organismoresponsable = value;
            }

            /**
             * Obtiene el valor de la propiedad destinatarios.
             *
             * @return possible object is
             * {@link DESTINATARIOS }
             */
            public DESTINATARIOS getDESTINATARIOS() {
                return destinatarios;
            }

            /**
             * Define el valor de la propiedad destinatarios.
             *
             * @param value allowed object is
             *              {@link DESTINATARIOS }
             */
            public void setDESTINATARIOS(DESTINATARIOS value) {
                this.destinatarios = value;
            }

            /**
             * Obtiene el valor de la propiedad sujetoatasas.
             *
             * @return possible object is
             * {@link String }
             */
            public String getSUJETOATASAS() {
                return sujetoatasas;
            }

            /**
             * Define el valor de la propiedad sujetoatasas.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setSUJETOATASAS(String value) {
                this.sujetoatasas = value;
            }

            /**
             * Obtiene el valor de la propiedad periodicidad.
             *
             * @return possible object is
             * {@link String }
             */
            public String getPERIODICIDAD() {
                return periodicidad;
            }

            /**
             * Define el valor de la propiedad periodicidad.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setPERIODICIDAD(String value) {
                this.periodicidad = value;
            }

            /**
             * Obtiene el valor de la propiedad unidadgestoratramite.
             *
             * @return possible object is
             * {@link String }
             */
            public String getUNIDADGESTORATRAMITE() {
                return unidadgestoratramite;
            }

            /**
             * Define el valor de la propiedad unidadgestoratramite.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setUNIDADGESTORATRAMITE(String value) {
                this.unidadgestoratramite = value;
            }

            /**
             * Obtiene el valor de la propiedad notificaciones.
             *
             * @return possible object is
             * {@link NOTIFICACIONES }
             */
            public NOTIFICACIONES getNOTIFICACIONES() {
                return notificaciones;
            }

            /**
             * Define el valor de la propiedad notificaciones.
             *
             * @param value allowed object is
             *              {@link NOTIFICACIONES }
             */
            public void setNOTIFICACIONES(NOTIFICACIONES value) {
                this.notificaciones = value;
            }

            /**
             * Obtiene el valor de la propiedad codniveladministracionelectronica.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCODNIVELADMINISTRACIONELECTRONICA() {
                return codniveladministracionelectronica;
            }

            /**
             * Define el valor de la propiedad codniveladministracionelectronica.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCODNIVELADMINISTRACIONELECTRONICA(String value) {
                this.codniveladministracionelectronica = value;
            }

            /**
             * Obtiene el valor de la propiedad sistemasidentificacion.
             *
             * @return possible object is
             * {@link SISTEMASIDENTIFICACION }
             */
            public SISTEMASIDENTIFICACION getSISTEMASIDENTIFICACION() {
                return sistemasidentificacion;
            }

            /**
             * Define el valor de la propiedad sistemasidentificacion.
             *
             * @param value allowed object is
             *              {@link SISTEMASIDENTIFICACION }
             */
            public void setSISTEMASIDENTIFICACION(SISTEMASIDENTIFICACION value) {
                this.sistemasidentificacion = value;
            }

            /**
             * Gets the value of the canalacceso property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the canalacceso property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCANALACCESO().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             */
            public List<String> getCANALACCESO() {
                if (canalacceso == null) {
                    canalacceso = new ArrayList<String>();
                }
                return this.canalacceso;
            }

            /**
             * Obtiene el valor de la propiedad formularios.
             *
             * @return possible object is
             * {@link FORMULARIOS }
             */
            public FORMULARIOS getFORMULARIOS() {
                return formularios;
            }

            /**
             * Define el valor de la propiedad formularios.
             *
             * @param value allowed object is
             *              {@link FORMULARIOS }
             */
            public void setFORMULARIOS(FORMULARIOS value) {
                this.formularios = value;
            }

            /**
             * Obtiene el valor de la propiedad enlaceweb.
             *
             * @return possible object is
             * {@link String }
             */
            public String getENLACEWEB() {
                return enlaceweb;
            }

            /**
             * Define el valor de la propiedad enlaceweb.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setENLACEWEB(String value) {
                this.enlaceweb = value;
            }

            /**
             * Obtiene el valor de la propiedad esresponsive.
             *
             * @return possible object is
             * {@link String }
             */
            public String getESRESPONSIVE() {
                return esresponsive;
            }

            /**
             * Define el valor de la propiedad esresponsive.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setESRESPONSIVE(String value) {
                this.esresponsive = value;
            }

            /**
             * Obtiene el valor de la propiedad portal.
             *
             * @return possible object is
             * {@link String }
             */
            public String getPORTAL() {
                return portal;
            }

            /**
             * Define el valor de la propiedad portal.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setPORTAL(String value) {
                this.portal = value;
            }

            /**
             * Obtiene el valor de la propiedad requisitosiniciacion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getREQUISITOSINICIACION() {
                return requisitosiniciacion;
            }

            /**
             * Define el valor de la propiedad requisitosiniciacion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setREQUISITOSINICIACION(String value) {
                this.requisitosiniciacion = value;
            }

            /**
             * Obtiene el valor de la propiedad presencialnoadaptable.
             *
             * @return possible object is
             * {@link JAXBElement }{@code <}{@link String }{@code >}
             */
            public JAXBElement<String> getPRESENCIALNOADAPTABLE() {
                return presencialnoadaptable;
            }

            /**
             * Define el valor de la propiedad presencialnoadaptable.
             *
             * @param value allowed object is
             *              {@link JAXBElement }{@code <}{@link String }{@code >}
             */
            public void setPRESENCIALNOADAPTABLE(JAXBElement<String> value) {
                this.presencialnoadaptable = value;
            }

            /**
             * Obtiene el valor de la propiedad disponiblefuncionariohabilitado.
             *
             * @return possible object is
             * {@link JAXBElement }{@code <}{@link String }{@code >}
             */
            public JAXBElement<String> getDISPONIBLEFUNCIONARIOHABILITADO() {
                return disponiblefuncionariohabilitado;
            }

            /**
             * Define el valor de la propiedad disponiblefuncionariohabilitado.
             *
             * @param value allowed object is
             *              {@link JAXBElement }{@code <}{@link String }{@code >}
             */
            public void setDISPONIBLEFUNCIONARIOHABILITADO(JAXBElement<String> value) {
                this.disponiblefuncionariohabilitado = value;
            }

            /**
             * Obtiene el valor de la propiedad disponibleapoderadohabilitado.
             *
             * @return possible object is
             * {@link JAXBElement }{@code <}{@link String }{@code >}
             */
            public JAXBElement<String> getDISPONIBLEAPODERADOHABILITADO() {
                return disponibleapoderadohabilitado;
            }

            /**
             * Define el valor de la propiedad disponibleapoderadohabilitado.
             *
             * @param value allowed object is
             *              {@link JAXBElement }{@code <}{@link String }{@code >}
             */
            public void setDISPONIBLEAPODERADOHABILITADO(JAXBElement<String> value) {
                this.disponibleapoderadohabilitado = value;
            }

            /**
             * Obtiene el valor de la propiedad codrequisitosidentpj.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCODREQUISITOSIDENTPJ() {
                return codrequisitosidentpj;
            }

            /**
             * Define el valor de la propiedad codrequisitosidentpj.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCODREQUISITOSIDENTPJ(String value) {
                this.codrequisitosidentpj = value;
            }

            /**
             * Obtiene el valor de la propiedad codrequisitosidentpf.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCODREQUISITOSIDENTPF() {
                return codrequisitosidentpf;
            }

            /**
             * Define el valor de la propiedad codrequisitosidentpf.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCODREQUISITOSIDENTPF(String value) {
                this.codrequisitosidentpf = value;
            }

            /**
             * Obtiene el valor de la propiedad idintegradoclave.
             *
             * @return possible object is
             * {@link String }
             */
            public String getIDINTEGRADOCLAVE() {
                return idintegradoclave;
            }

            /**
             * Define el valor de la propiedad idintegradoclave.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setIDINTEGRADOCLAVE(String value) {
                this.idintegradoclave = value;
            }

            /**
             * Obtiene el valor de la propiedad observacionintegradoclave.
             *
             * @return possible object is
             * {@link String }
             */
            public String getOBSERVACIONINTEGRADOCLAVE() {
                return observacionintegradoclave;
            }

            /**
             * Define el valor de la propiedad observacionintegradoclave.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setOBSERVACIONINTEGRADOCLAVE(String value) {
                this.observacionintegradoclave = value;
            }

            /**
             * Obtiene el valor de la propiedad volumenestramitaciones.
             *
             * @return possible object is
             * {@link VOLUMENESTRAMITACIONES }
             */
            public VOLUMENESTRAMITACIONES getVOLUMENESTRAMITACIONES() {
                return volumenestramitaciones;
            }

            /**
             * Define el valor de la propiedad volumenestramitaciones.
             *
             * @param value allowed object is
             *              {@link VOLUMENESTRAMITACIONES }
             */
            public void setVOLUMENESTRAMITACIONES(VOLUMENESTRAMITACIONES value) {
                this.volumenestramitaciones = value;
            }

            /**
             * Obtiene el valor de la propiedad tiempomedioresolucion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getTIEMPOMEDIORESOLUCION() {
                return tiempomedioresolucion;
            }

            /**
             * Define el valor de la propiedad tiempomedioresolucion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setTIEMPOMEDIORESOLUCION(String value) {
                this.tiempomedioresolucion = value;
            }

            /**
             * Obtiene el valor de la propiedad volumennotificaciones.
             *
             * @return possible object is
             * {@link VOLUMENNOTIFICACIONES }
             */
            public VOLUMENNOTIFICACIONES getVOLUMENNOTIFICACIONES() {
                return volumennotificaciones;
            }

            /**
             * Define el valor de la propiedad volumennotificaciones.
             *
             * @param value allowed object is
             *              {@link VOLUMENNOTIFICACIONES }
             */
            public void setVOLUMENNOTIFICACIONES(VOLUMENNOTIFICACIONES value) {
                this.volumennotificaciones = value;
            }

            /**
             * Obtiene el valor de la propiedad materias.
             *
             * @return possible object is
             * {@link MATERIAS }
             */
            public MATERIAS getMATERIAS() {
                return materias;
            }

            /**
             * Define el valor de la propiedad materias.
             *
             * @param value allowed object is
             *              {@link MATERIAS }
             */
            public void setMATERIAS(MATERIAS value) {
                this.materias = value;
            }

            /**
             * Obtiene el valor de la propiedad submaterias.
             *
             * @return possible object is
             * {@link SUBMATERIAS }
             */
            public SUBMATERIAS getSUBMATERIAS() {
                return submaterias;
            }

            /**
             * Define el valor de la propiedad submaterias.
             *
             * @param value allowed object is
             *              {@link SUBMATERIAS }
             */
            public void setSUBMATERIAS(SUBMATERIAS value) {
                this.submaterias = value;
            }

            /**
             * Obtiene el valor de la propiedad codclasetramite.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCODCLASETRAMITE() {
                return codclasetramite;
            }

            /**
             * Define el valor de la propiedad codclasetramite.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCODCLASETRAMITE(String value) {
                this.codclasetramite = value;
            }

            /**
             * Obtiene el valor de la propiedad tramitesrelacionados.
             *
             * @return possible object is
             * {@link TRAMITESRELACIONADOS }
             */
            public TRAMITESRELACIONADOS getTRAMITESRELACIONADOS() {
                return tramitesrelacionados;
            }

            /**
             * Define el valor de la propiedad tramitesrelacionados.
             *
             * @param value allowed object is
             *              {@link TRAMITESRELACIONADOS }
             */
            public void setTRAMITESRELACIONADOS(TRAMITESRELACIONADOS value) {
                this.tramitesrelacionados = value;
            }

            /**
             * Obtiene el valor de la propiedad norequieredocumentacion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getNOREQUIEREDOCUMENTACION() {
                return norequieredocumentacion;
            }

            /**
             * Define el valor de la propiedad norequieredocumentacion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setNOREQUIEREDOCUMENTACION(String value) {
                this.norequieredocumentacion = value;
            }

            /**
             * Obtiene el valor de la propiedad documentacion.
             *
             * @return possible object is
             * {@link String }
             */
            public String getDOCUMENTACION() {
                return documentacion;
            }

            /**
             * Define el valor de la propiedad documentacion.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setDOCUMENTACION(String value) {
                this.documentacion = value;
            }

            /**
             * Obtiene el valor de la propiedad altadocumentosespecificos.
             *
             * @return possible object is
             * {@link ALTADOCUMENTOSESPECIFICOS }
             */
            public ALTADOCUMENTOSESPECIFICOS getALTADOCUMENTOSESPECIFICOS() {
                return altadocumentosespecificos;
            }

            /**
             * Define el valor de la propiedad altadocumentosespecificos.
             *
             * @param value allowed object is
             *              {@link ALTADOCUMENTOSESPECIFICOS }
             */
            public void setALTADOCUMENTOSESPECIFICOS(ALTADOCUMENTOSESPECIFICOS value) {
                this.altadocumentosespecificos = value;
            }

            /**
             * Obtiene el valor de la propiedad documentoscatalogo.
             *
             * @return possible object is
             * {@link DOCUMENTOSCATALOGO }
             */
            public DOCUMENTOSCATALOGO getDOCUMENTOSCATALOGO() {
                return documentoscatalogo;
            }

            /**
             * Define el valor de la propiedad documentoscatalogo.
             *
             * @param value allowed object is
             *              {@link DOCUMENTOSCATALOGO }
             */
            public void setDOCUMENTOSCATALOGO(DOCUMENTOSCATALOGO value) {
                this.documentoscatalogo = value;
            }

            /**
             * Obtiene el valor de la propiedad inicios.
             *
             * @return possible object is
             * {@link INICIOS }
             */
            public INICIOS getINICIOS() {
                return inicios;
            }

            /**
             * Define el valor de la propiedad inicios.
             *
             * @param value allowed object is
             *              {@link INICIOS }
             */
            public void setINICIOS(INICIOS value) {
                this.inicios = value;
            }

            /**
             * Obtiene el valor de la propiedad finvia.
             *
             * @return possible object is
             * {@link String }
             */
            public String getFINVIA() {
                return finvia;
            }

            /**
             * Define el valor de la propiedad finvia.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setFINVIA(String value) {
                this.finvia = value;
            }

            /**
             * Obtiene el valor de la propiedad plazoresolucion.
             *
             * @return possible object is
             * {@link PLAZORESOLUCION }
             */
            public PLAZORESOLUCION getPLAZORESOLUCION() {
                return plazoresolucion;
            }

            /**
             * Define el valor de la propiedad plazoresolucion.
             *
             * @param value allowed object is
             *              {@link PLAZORESOLUCION }
             */
            public void setPLAZORESOLUCION(PLAZORESOLUCION value) {
                this.plazoresolucion = value;
            }

            /**
             * Obtiene el valor de la propiedad normativas.
             *
             * @return possible object is
             * {@link NORMATIVAS }
             */
            public NORMATIVAS getNORMATIVAS() {
                return normativas;
            }

            /**
             * Define el valor de la propiedad normativas.
             *
             * @param value allowed object is
             *              {@link NORMATIVAS }
             */
            public void setNORMATIVAS(NORMATIVAS value) {
                this.normativas = value;
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

        }

    }

}
