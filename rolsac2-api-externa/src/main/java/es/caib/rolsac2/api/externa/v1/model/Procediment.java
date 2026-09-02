package es.caib.rolsac2.api.externa.v1.model;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Procediments.
 *
 * @author Indra
 * Informacio d'un procediment.
 */
@XmlRootElement
@Schema(
        name = "Procediment",
        description = "Informacio d'un procediment."
)
public class Procediment implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(Procediment.class);

    private static final long serialVersionUID = 1L;
    @Schema(
            description = "Enllac a la URL de la Seu CAIB.",
            example = "https://www.caib.es/seucaib/ca/200/persones/tramites/tramite/1234567"
    )
    private String url;
    @Schema(
            description = "Codi del procediment.",
            example = "123"
    )
    private Long codi;
    @Schema(
            description = "Nom del procediment.",
            example = "Sol.licitud d'ajuda"
    )
    private String nom;
    @Schema(
            description = "Data d'actualitzacio del procediment en format ISO8601.",
            example = "2026-08-25T10:30:00+02:00"
    )
    private String dataActualizacio;
    @Schema(
            description = "Data de caducitat del procediment en format ISO8601.",
            example = "2026-12-31T23:59:59+01:00"
    )
    private String dataCaducitat;
    @Schema(
            description = "Data de publicacio del procediment en format ISO8601.",
            example = "2026-01-15T09:00:00+01:00"
    )
    private String dataPublicacio;
    @Schema(
            description = "Destinataris del procediment."
    )
    private String destinataris;
    @Schema(
            description = "Codi SIA del procediment.",
            example = "1234567"
    )
    private String codiSIA;
    @Schema(
            description = "Estat SIA del procediment."
    )
    private String estatSIA;
    @Schema(
            description = "Data SIA del procediment en format ISO8601.",
            example = "2026-05-20T12:00:00+02:00"
    )
    private String dataSIA;
    @Schema(
            description = "Codi de la unitat administrativa responsable."
    )
    private Long uaResponsableCodi;
    @Schema(
            description = "Nom de la unitat administrativa responsable."
    )
    private String uaResponsableNom;
    @Schema(
            description = "Codi de la unitat administrativa competent."
    )
    private Long uaCompetenteCodi;
    @Schema(
            description = "Nom de la unitat administrativa competent."
    )
    private String uaCompetenteNom;
    @Schema(
            description = "Codi de la unitat administrativa instructora."
    )
    private Long uaInstructor;
    @Schema(
            description = "Nom de la unitat administrativa instructora."
    )
    private String uaInstructorNom;
    @Schema(
            description = "Indica si el procediment es comu."
    )
    private Boolean comu;
    @Schema(
            description = "Objecte del procediment."
    )
    private String objecte;
    @Schema(
            description = "Codi del tipus del procediment."
    )
    private Long tipusCodi;
    @Schema(
            description = "Nom del tipus del procediment."
    )
    private String tipusNom;
    @Schema(
            description = "Estat del procediment."
    )
    private String estat;
    @Schema(
            description = "Codi d'iniciacio del procediment."
    )
    private Long iniciacionCodi;
    @Schema(
            description = "Nom d'iniciacio del procediment."
    )
    private String iniciacionNom;
    @Schema(
            description = "Codi del silenci del procediment."
    )
    private Long silenciCodi;
    @Schema(
            description = "Nom del silenci del procediment."
    )
    private String silenciNom;
    @Schema(
            description = "Codi del tipus de procediment."
    )
    private Long tipusProcedimientoCodi;
    @Schema(
            description = "Nom del tipus de procediment."
    )
    private String tipusProcedimientoNom;
    @Schema(
            description = "Codi del tipus de via del procediment."
    )
    private Long tipusViaCodi;
    @Schema(
            description = "Nom del tipus de via del procediment."
    )
    private String tipusViaNom;
    @Schema(
            description = "Indica si esta habilitada la tramitacio mitjancant apoderat."
    )
    private Boolean habilitatApoderat;
    @Schema(
            description = "Indica si esta habilitada la tramitacio mitjancant funcionari."
    )
    private Boolean habilitatFuncionari;
    @Schema(
            description = "Termini de resolucio del procediment."
    )
    private String terminiResolucio;

    public Procediment() {
        // Constructor por defecto
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Long getCodi() {
        return codi;
    }

    public void setCodi(final Long codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getDataActualizacio() {
        return dataActualizacio;
    }

    public void setDataActualizacio(final String dataActualizacio) {
        this.dataActualizacio = dataActualizacio;
    }

    public String getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(final String dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
    }

    public String getDataPublicacio() {
        return dataPublicacio;
    }

    public void setDataPublicacio(final String dataPublicacio) {
        this.dataPublicacio = dataPublicacio;
    }

    public String getDestinataris() {
        return destinataris;
    }

    public void setDestinataris(final String destinataris) {
        this.destinataris = destinataris;
    }

    public String getCodiSIA() {
        return codiSIA;
    }

    public void setCodiSIA(final String codiSIA) {
        this.codiSIA = codiSIA;
    }

    public String getEstatSIA() {
        return estatSIA;
    }

    public void setEstatSIA(final String estatSIA) {
        this.estatSIA = estatSIA;
    }

    public String getDataSIA() {
        return dataSIA;
    }

    public void setDataSIA(final String dataSIA) {
        this.dataSIA = dataSIA;
    }

    public Long getUaResponsableCodi() {
        return uaResponsableCodi;
    }

    public void setUaResponsableCodi(final Long uaResponsableCodi) {
        this.uaResponsableCodi = uaResponsableCodi;
    }

    public String getUaResponsableNom() {
        return uaResponsableNom;
    }

    public void setUaResponsableNom(final String uaResponsableNom) {
        this.uaResponsableNom = uaResponsableNom;
    }

    public Long getUaCompetenteCodi() {
        return uaCompetenteCodi;
    }

    public void setUaCompetenteCodi(final Long uaCompetenteCodi) {
        this.uaCompetenteCodi = uaCompetenteCodi;
    }

    public String getUaCompetenteNom() {
        return uaCompetenteNom;
    }

    public void setUaCompetenteNom(final String uaCompetenteNom) {
        this.uaCompetenteNom = uaCompetenteNom;
    }

    public Long getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(final Long uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    public String getUaInstructorNom() {
        return uaInstructorNom;
    }

    public void setUaInstructorNom(final String uaInstructorNom) {
        this.uaInstructorNom = uaInstructorNom;
    }

    public Boolean getComu() {
        return comu;
    }

    public void setComu(final Boolean comu) {
        this.comu = comu;
    }

    public String getObjecte() {
        return objecte;
    }

    public void setObjecte(final String objecte) {
        this.objecte = objecte;
    }

    public Long getTipusCodi() {
        return tipusCodi;
    }

    public void setTipusCodi(final Long tipusCodi) {
        this.tipusCodi = tipusCodi;
    }

    public String getTipusNom() {
        return tipusNom;
    }

    public void setTipusNom(final String tipusNom) {
        this.tipusNom = tipusNom;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(final String estat) {
        this.estat = estat;
    }

    public Long getIniciacionCodi() {
        return iniciacionCodi;
    }

    public void setIniciacionCodi(final Long iniciacionCodi) {
        this.iniciacionCodi = iniciacionCodi;
    }

    public String getIniciacionNom() {
        return iniciacionNom;
    }

    public void setIniciacionNom(final String iniciacionNom) {
        this.iniciacionNom = iniciacionNom;
    }

    public Long getSilenciCodi() {
        return silenciCodi;
    }

    public void setSilenciCodi(final Long silenciCodi) {
        this.silenciCodi = silenciCodi;
    }

    public String getSilenciNom() {
        return silenciNom;
    }

    public void setSilenciNom(final String silenciNom) {
        this.silenciNom = silenciNom;
    }

    public Long getTipusProcedimientoCodi() {
        return tipusProcedimientoCodi;
    }

    public void setTipusProcedimientoCodi(final Long tipusProcedimientoCodi) {
        this.tipusProcedimientoCodi = tipusProcedimientoCodi;
    }

    public String getTipusProcedimientoNom() {
        return tipusProcedimientoNom;
    }

    public void setTipusProcedimientoNom(final String tipusProcedimientoNom) {
        this.tipusProcedimientoNom = tipusProcedimientoNom;
    }

    public Long getTipusViaCodi() {
        return tipusViaCodi;
    }

    public void setTipusViaCodi(final Long tipusViaCodi) {
        this.tipusViaCodi = tipusViaCodi;
    }

    public String getTipusViaNom() {
        return tipusViaNom;
    }

    public void setTipusViaNom(final String tipusViaNom) {
        this.tipusViaNom = tipusViaNom;
    }

    public Boolean getHabilitatApoderat() {
        return habilitatApoderat;
    }

    public void setHabilitatApoderat(final Boolean habilitatApoderat) {
        this.habilitatApoderat = habilitatApoderat;
    }

    public Boolean getHabilitatFuncionari() {
        return habilitatFuncionari;
    }

    public void setHabilitatFuncionari(final Boolean habilitatFuncionari) {
        this.habilitatFuncionari = habilitatFuncionari;
    }

    public String getTerminiResolucio() {
        return terminiResolucio;
    }

    public void setTerminiResolucio(final String terminiResolucio) {
        this.terminiResolucio = terminiResolucio;
    }

    public Procediment(ProcedimientoDTO nodo, String urlBase, String idioma, boolean hateoasEnabled, final String idiomaPorDefecto) {
        try {
            if (nodo == null) {
                return;
            }
            this.codi = nodo.getCodigo();
            this.nom = getTraduccion(nodo.getNombreProcedimientoWorkFlow(), idioma, idiomaPorDefecto);
            this.dataActualizacio = toIso8601(nodo.getFechaActualizacion());
            this.dataCaducitat = toIso8601(nodo.getFechaCaducidad());
            this.dataPublicacio = toIso8601(nodo.getFechaPublicacion());
            this.destinataris = getTraduccion(nodo.getDestinatarios(), idioma, idiomaPorDefecto);
            this.codiSIA = nodo.getCodigoSIA() == null ? null : String.valueOf(nodo.getCodigoSIA());
            this.estatSIA = nodo.getEstadoSIA();
            this.dataSIA = toIso8601(nodo.getFechaSIA());
            this.uaResponsableNom = getTraduccion(nodo.getUaResponsableLiteral(), idioma, idiomaPorDefecto);
            this.uaResponsableCodi = getLongProperty(nodo, "getUaResponsableCodigo", "getCodigoUaResponsable");
            if (this.uaResponsableCodi == null) {
                Object uaResponsable = invokeAny(nodo, "getUaResponsable", "getUnidadResponsable");
                this.uaResponsableCodi = getLongProperty(uaResponsable, "getCodigo", "getId");
            }
            if (nodo.getUaCompetente() != null) {
                this.uaCompetenteCodi = nodo.getUaCompetente().getCodigo();
                this.uaCompetenteNom = getDescripcionUA(nodo.getUaCompetente(), idioma, idiomaPorDefecto);
            }
            if (nodo.getUaInstructor() != null) {
                this.uaInstructor = nodo.getUaInstructor().getCodigo();
                this.uaInstructorNom = getDescripcionUA(nodo.getUaInstructor(), idioma, idiomaPorDefecto);
            }
            this.comu = nodo.getComun() != 0;
            this.objecte = getTraduccion(nodo.getObjeto(), idioma, idiomaPorDefecto);
            if (nodo.getTipoProcedimiento() != null) {
                this.tipusCodi = nodo.getTipoProcedimiento().getCodigo();
                this.tipusNom = getTraduccion(nodo.getTipoProcedimiento().getDescripcion(), idioma, idiomaPorDefecto);
                this.tipusProcedimientoCodi = this.tipusCodi;
                this.tipusProcedimientoNom = this.tipusNom;
            }
            this.estat = nodo.getEstado() == null ? null : nodo.getEstado().name();
            if (nodo.getIniciacion() != null) {
                this.iniciacionCodi = nodo.getIniciacion().getCodigo();
                this.iniciacionNom = getTraduccion(nodo.getIniciacion().getDescripcion(), idioma, idiomaPorDefecto);
            }
            if (nodo.getSilencio() != null) {
                this.silenciCodi = nodo.getSilencio().getCodigo();
                this.silenciNom = getTraduccion(nodo.getSilencio().getDescripcion(), idioma, idiomaPorDefecto);
            }
            if (nodo.getTipoVia() != null) {
                this.tipusViaCodi = nodo.getTipoVia().getCodigo();
                this.tipusViaNom = getTraduccion(nodo.getTipoVia().getDescripcion(), idioma, idiomaPorDefecto);
            }
            this.habilitatApoderat = nodo.isHabilitadoApoderado();
            this.habilitatFuncionari = toBooleanFlag(nodo.getHabilitadoFuncionario());
            this.terminiResolucio = getTraduccion(nodo.getTerminoResolucion(), idioma, idiomaPorDefecto);
            this.url = resolveSeuUrl(nodo, urlBase, this.codi);
        } catch (Exception e) {
            LOG.error("Error generando procediment {}", this.codi, e);
        }
    }

    private String toIso8601(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private String getTraduccion(Literal literal, String idioma, String idiomaPorDefecto) {
        if (literal == null) {
            return null;
        }
        String traduccion = literal.getTraduccionConValor(idioma, idiomaPorDefecto);
        if (traduccion == null) {
            traduccion = literal.getTraduccion();
        }
        return traduccion;
    }

    private String getDescripcionUA(UnidadAdministrativaDTO ua, String idioma, String idiomaPorDefecto) {
        if (ua == null || ua.getNombre() == null) {
            return null;
        }
        String descripcion = ua.getNombre().getTraduccionConValor(idioma, idiomaPorDefecto);
        if (descripcion == null) {
            descripcion = ua.getNombre().getTraduccion();
        }
        return descripcion;
    }

    private Boolean toBooleanFlag(String value) {
        if (value == null) {
            return null;
        }
        return "S".equalsIgnoreCase(value) || "1".equals(value) || "true".equalsIgnoreCase(value);
    }

    /**
     * Intenta obtener la URL pública de la Seu desde el DTO. El PDF exige que
     * el campo url apunte a seucaib; no debe confundirse con el enlace HATEOAS
     * del propio REST. Se prueban varios getters para mantener compatibilidad
     * entre versiones del DTO.
     */
    private String resolveSeuUrl(Object nodo, String urlBase, Long codigoProcedimiento) {
        Object valor = invokeAny(nodo,
                "getUrlSede", "getUrlSEDE", "getUrlSeu", "getUrlPublica",
                "getUrlProcedimiento", "getUrl");
        if (valor != null) {
            String urlSeu = String.valueOf(valor).trim();
            if (!urlSeu.isEmpty()) {
                return urlSeu;
            }
        }
        // Fallback únicamente si el llamador proporciona explícitamente una base.
        return buildUrl(urlBase, codigoProcedimiento);
    }

    private Object invokeAny(Object target, String... getters) {
        if (target == null || getters == null) {
            return null;
        }
        for (String getter : getters) {
            try {
                java.lang.reflect.Method method = target.getClass().getMethod(getter);
                return method.invoke(target);
            } catch (ReflectiveOperationException ignored) {
                // Se intenta el siguiente nombre compatible.
            }
        }
        return null;
    }

    private Long getLongProperty(Object target, String... getters) {
        Object value = invokeAny(target, getters);
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        try {
            return Long.valueOf(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String buildUrl(String urlBase, Long codigoProcedimiento) {
        if (urlBase == null || codigoProcedimiento == null) {
            return null;
        }
        String base = urlBase.endsWith("/") ? urlBase : urlBase + "/";
        return base + Constantes.ENTIDAD_PROCEDIMIENTO + "/" + codigoProcedimiento;
    }

    @Override
    public String toString() {
        return "Procediment{" +
                "tipusNom='" + tipusNom + '\'' +
                ", estat='" + estat + '\'' +
                ", iniciacionCodi=" + iniciacionCodi +
                ", iniciacionNom='" + iniciacionNom + '\'' +
                ", silenciCodi=" + silenciCodi +
                ", silenciNom='" + silenciNom + '\'' +
                ", tipusProcedimientoCodi=" + tipusProcedimientoCodi +
                ", tipusProcedimientoNom='" + tipusProcedimientoNom + '\'' +
                ", tipusViaCodi=" + tipusViaCodi +
                ", tipusViaNom='" + tipusViaNom + '\'' +
                ", habilitatApoderat=" + habilitatApoderat +
                ", habilitatFuncionari=" + habilitatFuncionari +
                ", terminiResolucio='" + terminiResolucio + '\'' +
                '}';
    }
}
