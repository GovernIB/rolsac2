package es.caib.rolsac2.rest.api.externa.testclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo standalone del cliente de ejemplo.
 * <p>
 * IMPORTANTE:
 * Debe mantenerse sincronizado con
 * es.caib.rolsac2.api.externa.v1.model.Servei.
 */
public class Servei implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;
    private Long codi;
    private String nom;
    private String dataActualizacio;
    private String dataPublicacio;
    private String dataCaducitat;
    private String codiSIA;
    private String estatSIA;
    private String dataSIA;
    private Long uaResponsableCodi;
    private String uaResponsableNom;
    private Long uaInstructorCodi;
    private String uaInstructorNom;
    private Boolean comu;
    private String objecte;
    private String destinataris;
    private String estat;
    private Boolean habilitatApoderat;
    private Boolean habilitatFuncionari;
    private String terminiResolucio;
    private String intern;
    private String publicat;
    private Boolean actiuLOPD;

    // Se incluyen como opcionales para que el cliente siga funcionando
    // cuando el servidor los exponga.
    private Long tipusTramitacioCodi;
    private String tipusTramitacioNom;

    private Boolean tramitPresencial;
    private Boolean tramitElectronica;
    private Boolean tramitTelefonica;
    private String urlTramitacio;
    private Long plataformaTramitCodi;
    private String plataformaTramitNom;

    // Igual que los anteriores: opcionales/future-proof.
    private Long plantillaTramitCodi;
    private String plantillaTramitNom;

    private List<PublicObjectiu> publicsObjectius = new ArrayList<>();

    public Servei() {
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

    public String getDataPublicacio() {
        return dataPublicacio;
    }

    public void setDataPublicacio(String dataPublicacio) {
        this.dataPublicacio = dataPublicacio;
    }

    public String getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(String dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
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

    public Long getUaInstructorCodi() {
        return uaInstructorCodi;
    }

    public void setUaInstructorCodi(Long uaInstructorCodi) {
        this.uaInstructorCodi = uaInstructorCodi;
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

    public String getDestinataris() {
        return destinataris;
    }

    public void setDestinataris(String destinataris) {
        this.destinataris = destinataris;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
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

    public String getIntern() {
        return intern;
    }

    public void setIntern(String intern) {
        this.intern = intern;
    }

    public String getPublicat() {
        return publicat;
    }

    public void setPublicat(String publicat) {
        this.publicat = publicat;
    }

    public Boolean getActiuLOPD() {
        return actiuLOPD;
    }

    public void setActiuLOPD(Boolean actiuLOPD) {
        this.actiuLOPD = actiuLOPD;
    }

    public Long getTipusTramitacioCodi() {
        return tipusTramitacioCodi;
    }

    public void setTipusTramitacioCodi(Long tipusTramitacioCodi) {
        this.tipusTramitacioCodi = tipusTramitacioCodi;
    }

    public String getTipusTramitacioNom() {
        return tipusTramitacioNom;
    }

    public void setTipusTramitacioNom(String tipusTramitacioNom) {
        this.tipusTramitacioNom = tipusTramitacioNom;
    }

    public Boolean getTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(Boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public Boolean getTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(Boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public Boolean getTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(Boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public String getUrlTramitacio() {
        return urlTramitacio;
    }

    public void setUrlTramitacio(String urlTramitacio) {
        this.urlTramitacio = urlTramitacio;
    }

    public Long getPlataformaTramitCodi() {
        return plataformaTramitCodi;
    }

    public void setPlataformaTramitCodi(Long plataformaTramitCodi) {
        this.plataformaTramitCodi = plataformaTramitCodi;
    }

    public String getPlataformaTramitNom() {
        return plataformaTramitNom;
    }

    public void setPlataformaTramitNom(String plataformaTramitNom) {
        this.plataformaTramitNom = plataformaTramitNom;
    }

    public Long getPlantillaTramitCodi() {
        return plantillaTramitCodi;
    }

    public void setPlantillaTramitCodi(Long plantillaTramitCodi) {
        this.plantillaTramitCodi = plantillaTramitCodi;
    }

    public String getPlantillaTramitNom() {
        return plantillaTramitNom;
    }

    public void setPlantillaTramitNom(String plantillaTramitNom) {
        this.plantillaTramitNom = plantillaTramitNom;
    }

    public List<PublicObjectiu> getPublicsObjectius() {
        return publicsObjectius;
    }

    public void setPublicsObjectius(List<PublicObjectiu> publicsObjectius) {
        this.publicsObjectius = publicsObjectius == null ? new ArrayList<>() : publicsObjectius;
    }

    @Override
    public String toString() {
        return "Servei{" +
                "codi=" + codi +
                ", nom='" + nom + '\'' +
                ", estat='" + estat + '\'' +
                ", codiSIA='" + codiSIA + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public static class PublicObjectiu implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long codi;
        private String nom;

        public PublicObjectiu() {
        }

        public PublicObjectiu(Long codi, String nom) {
            this.codi = codi;
            this.nom = nom;
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

        @Override
        public String toString() {
            return codi + " | " + nom;
        }
    }
}
