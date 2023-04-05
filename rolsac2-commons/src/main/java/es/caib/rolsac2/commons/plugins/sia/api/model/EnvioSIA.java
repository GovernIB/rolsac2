package es.caib.rolsac2.commons.plugins.sia.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Elemento para envío a SIA.
 *
 * @author Indra
 */
public class EnvioSIA implements Serializable {

    /**
     * Serial version UID.
     **/
    private static final long serialVersionUID = 1L;

    /**
     * Los ids de los centros directivos.
     **/
    private String idDepartamento;

    /**
     * Campo con el ID SIA (será nulo si es un procedimiento creado) *.
     */
    private String idSia;

    /**
     * Operación a realizar *.
     */
    private String operacion;

    /**
     * Id expediente (trámite) *.
     */
    private String cdExpediente;

    /**
     * TIPO ACTUACION  IT_SERVICIO = 0 -> [1] Trámite  IT_SERVICIO = 1 -> [2] Servicio.
     */
    private String tipoTramite;

    /**
     * Tipología Tramitación  1= INTERNO COMUN 2= INTERNO ESPECIFICO 3= EXTERNO COMUN 4= EXTERNO ESPECIFICO.
     */
    private Integer tipologia;

    /**
     * DS_PROCEDIMIENTO - DENOMINACIÓN *.
     */
    private String dsProcedimiento;

    /**
     * DS_OBJETO (DS_PROCEDIMIENTO SI DS_OBJETO NULO) - DESCRIPCION *.
     */
    private String dsObjeto;

    /**
     * ID CENTRO DIRECTIVO (DIR3).
     **/
    private String idCentroDirectivo;

    /**
     * DS_UNIDADTRAM - UNIDAD GESTORA *.
     */
    private String unidadGestora;

    /**
     * Los id del destinatario siendo el valor 1 ciudadano, 2 la empresa y 3 la
     * administración.
     **/
    private List<String> idDestinatario;

    /**
     * Nivel de administración electrónica.
     * <p>
     * Valores posibles: 1-Información 2-Descarga de formulario 3-Descarga y
     * envío 4-Tramitación electrónica 5-Proactivo 6-Sin tramitación electrónica
     **/
    private String nivelAdminElectronica;

    /**
     * Valores posibles: N o S *.
     */
    private String finVia;

    /**
     * Enlace web *.
     */
    private String enlaceWeb;

    /**
     * Normativas *.
     */
    private List<NormativaSIA> normativas;

    /**
     * Identificador de las materias *.
     */
    private List<String> materias;

    private Boolean disponibleFuncionarioHabilitado;

    private Boolean disponibleApoderadoHabilitado;

    /**
     * Usuario - password
     **/
    private String usuario;
    private String password;

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * Gets the cd expediente.
     *
     * @return the cdExpediente
     */
    public String getCdExpediente() {
        return cdExpediente;
    }

    /**
     * Sets the cd expediente.
     *
     * @param cdExpediente the cdExpediente to set
     */
    public void setCdExpediente(String cdExpediente) {
        this.cdExpediente = cdExpediente;
    }

    /**
     * Gets the ds procedimiento.
     *
     * @return the dsProcedimiento
     */
    public String getDsProcedimiento() {
        return dsProcedimiento;
    }

    /**
     * Sets the ds procedimiento.
     *
     * @param dsProcedimiento the dsProcedimiento to set
     */
    public void setDsProcedimiento(String dsProcedimiento) {
        this.dsProcedimiento = dsProcedimiento;
    }

    /**
     * Gets the id centro directivo.
     *
     * @return the idCentroDirectivo
     */
    public String getIdCentroDirectivo() {
        return idCentroDirectivo;
    }

    /**
     * Sets the id centro directivo.
     *
     * @param idCentroDirectivo the idCentroDirectivo to set
     */
    public void setIdCentroDirectivo(String idCentroDirectivo) {
        this.idCentroDirectivo = idCentroDirectivo;
    }

    /**
     * Gets the unidad gestora.
     *
     * @return the unidadGestora
     */
    public String getUnidadGestora() {
        return unidadGestora;
    }

    /**
     * Sets the unidad gestora.
     *
     * @param unidadGestora the unidadGestora to set
     */
    public void setUnidadGestora(String unidadGestora) {
        this.unidadGestora = unidadGestora;
    }

    /**
     * Gets the nivel admin electronica.
     *
     * @return the nivelAdminElectronica
     */
    public String getNivelAdminElectronica() {
        return nivelAdminElectronica;
    }

    /**
     * Sets the nivel admin electronica.
     *
     * @param nivelAdminElectronica the nivelAdminElectronica to set
     */
    public void setNivelAdminElectronica(String nivelAdminElectronica) {
        this.nivelAdminElectronica = nivelAdminElectronica;
    }

    /**
     * Gets the fin via.
     *
     * @return the finVia
     */
    public String getFinVia() {
        return finVia;
    }

    /**
     * Sets the fin via.
     *
     * @param finVia the finVia to set
     */
    public void setFinVia(String finVia) {
        this.finVia = finVia;
    }

    /**
     * Gets the id sia.
     *
     * @return the idSia
     */
    public String getIdSia() {
        return idSia;
    }

    /**
     * Sets the id sia.
     *
     * @param idSia the idSia to set
     */
    public void setIdSia(String idSia) {
        this.idSia = idSia;
    }

    /**
     * Gets the ds objeto.
     *
     * @return the dsObjeto
     */
    public String getDsObjeto() {
        return dsObjeto;
    }

    /**
     * Sets the ds objeto.
     *
     * @param dsObjeto the dsObjeto to set
     */
    public void setDsObjeto(String dsObjeto) {
        this.dsObjeto = dsObjeto;
    }

    /**
     * Gets the id destinatario.
     *
     * @return the idDestinatario
     */
    public List<String> getIdDestinatario() {
        if (this.idDestinatario == null) {
            this.idDestinatario = new ArrayList<String>();
        }
        return this.idDestinatario;
    }

    public void setIdDestinatario(List<String> idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public void setNormativas(List<NormativaSIA> normativas) {
        this.normativas = normativas;
    }

    public void setMaterias(List<String> materias) {
        this.materias = materias;
    }

    /**
     * Gets the normativas.
     *
     * @return the normativas
     */
    public List<NormativaSIA> getNormativas() {
        if (this.normativas == null) {
            this.normativas = new ArrayList<NormativaSIA>();
        }
        return this.normativas;
    }

    /**
     * Gets the materias.
     *
     * @return the materias
     */
    public List<String> getMaterias() {
        if (this.materias == null) {
            this.materias = new ArrayList<String>();
        }
        return this.materias;
    }

    /**
     * Gets the tipologia.
     *
     * @return the tipologia
     */
    public Integer getTipologia() {
        return tipologia;
    }

    /**
     * Sets the tipologia.
     *
     * @param tipologia the tipologia to set
     */
    public void setTipologia(Integer tipologia) {
        this.tipologia = tipologia;
    }

    /**
     * Gets the tipo tramite.
     *
     * @return the tipoTramite
     */
    public String getTipoTramite() {
        return tipoTramite;
    }

    /**
     * Sets the tipo tramite.
     *
     * @param tipoTramite the tipoTramite to set
     */
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * Gets the enlace web.
     *
     * @return the enlaceWeb
     */
    public String getEnlaceWeb() {
        return enlaceWeb;
    }

    /**
     * Sets the enlace web.
     *
     * @param enlaceWeb the enlaceWeb to set
     */
    public void setEnlaceWeb(String enlaceWeb) {
        this.enlaceWeb = enlaceWeb;
    }

    /**
     * Gets the operacion.
     *
     * @return the operacion
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * Sets the operacion.
     *
     * @param operacion the operacion to set
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Boolean getDisponibleFuncionarioHabilitado() {
        return disponibleFuncionarioHabilitado;
    }

    public void setDisponibleFuncionarioHabilitado(Boolean disponibleFuncionarioHabilitado) {
        this.disponibleFuncionarioHabilitado = disponibleFuncionarioHabilitado;
    }

    public Boolean getDisponibleApoderadoHabilitado() {
        return disponibleApoderadoHabilitado;
    }

    public void setDisponibleApoderadoHabilitado(Boolean disponibleApoderadoHabilitado) {
        this.disponibleApoderadoHabilitado = disponibleApoderadoHabilitado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
