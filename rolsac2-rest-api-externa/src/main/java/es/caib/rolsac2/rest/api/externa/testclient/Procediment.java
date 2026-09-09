package es.caib.rolsac2.rest.api.externa.testclient;

import java.io.Serializable;

/**
 * Modelo de cliente para la respuesta de /procediments/.
 *
 * Es una copia ligera del contrato público de Procediment:
 * no contiene dependencias de JBoss, OpenAPI, SLF4J ni de los DTO internos.
 */
public class Procediment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;
    private Long codi;
    private String nom;
    private String dataActualizacio;
    private String dataCaducitat;
    private String dataPublicacio;
    private String destinataris;
    private String codiSIA;
    private String estatSIA;
    private String dataSIA;
    private Long uaResponsableCodi;
    private String uaResponsableNom;
    private Long uaCompetenteCodi;
    private String uaCompetenteNom;
    private Long uaInstructor;
    private String uaInstructorNom;
    private Boolean comu;
    private String objecte;
    private Long tipusCodi;
    private String tipusNom;
    private String estat;
    private Long iniciacionCodi;
    private String iniciacionNom;
    private Long silenciCodi;
    private String silenciNom;
    private Long tipusViaCodi;
    private String tipusViaNom;
    private Boolean habilitatApoderat;
    private Boolean habilitatFuncionari;
    private String terminiResolucio;

    public Procediment() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCodi() {
        return codi;
    }

    public void setCodi(Long codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDataActualizacio() {
        return dataActualizacio;
    }

    public void setDataActualizacio(String dataActualizacio) {
        this.dataActualizacio = dataActualizacio;
    }

    public String getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(String dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
    }

    public String getDataPublicacio() {
        return dataPublicacio;
    }

    public void setDataPublicacio(String dataPublicacio) {
        this.dataPublicacio = dataPublicacio;
    }

    public String getDestinataris() {
        return destinataris;
    }

    public void setDestinataris(String destinataris) {
        this.destinataris = destinataris;
    }

    public String getCodiSIA() {
        return codiSIA;
    }

    public void setCodiSIA(String codiSIA) {
        this.codiSIA = codiSIA;
    }

    public String getEstatSIA() {
        return estatSIA;
    }

    public void setEstatSIA(String estatSIA) {
        this.estatSIA = estatSIA;
    }

    public String getDataSIA() {
        return dataSIA;
    }

    public void setDataSIA(String dataSIA) {
        this.dataSIA = dataSIA;
    }

    public Long getUaResponsableCodi() {
        return uaResponsableCodi;
    }

    public void setUaResponsableCodi(Long uaResponsableCodi) {
        this.uaResponsableCodi = uaResponsableCodi;
    }

    public String getUaResponsableNom() {
        return uaResponsableNom;
    }

    public void setUaResponsableNom(String uaResponsableNom) {
        this.uaResponsableNom = uaResponsableNom;
    }

    public Long getUaCompetenteCodi() {
        return uaCompetenteCodi;
    }

    public void setUaCompetenteCodi(Long uaCompetenteCodi) {
        this.uaCompetenteCodi = uaCompetenteCodi;
    }

    public String getUaCompetenteNom() {
        return uaCompetenteNom;
    }

    public void setUaCompetenteNom(String uaCompetenteNom) {
        this.uaCompetenteNom = uaCompetenteNom;
    }

    public Long getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(Long uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    public String getUaInstructorNom() {
        return uaInstructorNom;
    }

    public void setUaInstructorNom(String uaInstructorNom) {
        this.uaInstructorNom = uaInstructorNom;
    }

    public Boolean getComu() {
        return comu;
    }

    public void setComu(Boolean comu) {
        this.comu = comu;
    }

    public String getObjecte() {
        return objecte;
    }

    public void setObjecte(String objecte) {
        this.objecte = objecte;
    }

    public Long getTipusCodi() {
        return tipusCodi;
    }

    public void setTipusCodi(Long tipusCodi) {
        this.tipusCodi = tipusCodi;
    }

    public String getTipusNom() {
        return tipusNom;
    }

    public void setTipusNom(String tipusNom) {
        this.tipusNom = tipusNom;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public Long getIniciacionCodi() {
        return iniciacionCodi;
    }

    public void setIniciacionCodi(Long iniciacionCodi) {
        this.iniciacionCodi = iniciacionCodi;
    }

    public String getIniciacionNom() {
        return iniciacionNom;
    }

    public void setIniciacionNom(String iniciacionNom) {
        this.iniciacionNom = iniciacionNom;
    }

    public Long getSilenciCodi() {
        return silenciCodi;
    }

    public void setSilenciCodi(Long silenciCodi) {
        this.silenciCodi = silenciCodi;
    }

    public String getSilenciNom() {
        return silenciNom;
    }

    public void setSilenciNom(String silenciNom) {
        this.silenciNom = silenciNom;
    }

    public Long getTipusViaCodi() {
        return tipusViaCodi;
    }

    public void setTipusViaCodi(Long tipusViaCodi) {
        this.tipusViaCodi = tipusViaCodi;
    }

    public String getTipusViaNom() {
        return tipusViaNom;
    }

    public void setTipusViaNom(String tipusViaNom) {
        this.tipusViaNom = tipusViaNom;
    }

    public Boolean getHabilitatApoderat() {
        return habilitatApoderat;
    }

    public void setHabilitatApoderat(Boolean habilitatApoderat) {
        this.habilitatApoderat = habilitatApoderat;
    }

    public Boolean getHabilitatFuncionari() {
        return habilitatFuncionari;
    }

    public void setHabilitatFuncionari(Boolean habilitatFuncionari) {
        this.habilitatFuncionari = habilitatFuncionari;
    }

    public String getTerminiResolucio() {
        return terminiResolucio;
    }

    public void setTerminiResolucio(String terminiResolucio) {
        this.terminiResolucio = terminiResolucio;
    }

    @Override
    public String toString() {
        return "Procediment{" +
                "codi=" + codi +
                ", nom='" + nom + '\'' +
                ", estat='" + estat + '\'' +
                ", codiSIA='" + codiSIA + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
