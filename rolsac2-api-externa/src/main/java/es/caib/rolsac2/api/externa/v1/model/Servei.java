package es.caib.rolsac2.api.externa.v1.model;

import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Informació pública d'un servei.
 */
@XmlRootElement
@Schema(name = "Servei", description = "Informació pública d'un servei.")
public class Servei implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(Servei.class);
    private static final long serialVersionUID = 1L;

    @Schema(description = "Enllaç a la URL pública de la Seu CAIB.")
    private String url;
    @Schema(description = "Codi del servei.")
    private Long codi;
    @Schema(description = "Nom del servei.")
    private String nom;
    @Schema(description = "Data d’actualització del servei en format ISO8601.")
    private String dataActualizacio;
    @Schema(description = "Data de publicació del servei en format ISO8601.")
    private String dataPublicacio;
    @Schema(description = "Data de caducitat del servei en format ISO8601.")
    private String dataCaducitat;
    @Schema(description = "Codi SIA del servei.")
    private String codiSIA;
    @Schema(description = "Estat SIA del servei.")
    private String estatSIA;
    @Schema(description = "Data SIA del servei en format ISO8601.")
    private String dataSIA;
    @Schema(description = "Codi de la unitat administrativa responsable.")
    private Long uaResponsableCodi;
    @Schema(description = "Nom de la unitat administrativa responsable.")
    private String uaResponsableNom;
    @Schema(description = "Codi de la unitat administrativa instructora.")
    private Long uaInstructorCodi;
    @Schema(description = "Nom de la unitat administrativa instructora.")
    private String uaInstructorNom;
    @Schema(description = "Indica si el servei és comú.")
    private Boolean comu;
    @Schema(description = "Objecte del servei.")
    private String objecte;
    @Schema(description = "Destinataris del servei.")
    private String destinataris;
    @Schema(description = "Estat del servei.")
    private String estat;
    @Schema(description = "Indica si permet tramitació mitjançant apoderat.")
    private Boolean habilitatApoderat;
    @Schema(description = "Indica si permet tramitació mitjançant funcionari habilitat.")
    private Boolean habilitatFuncionari;
    @Schema(description = "Termini de resolució.")
    private String terminiResolucio;
    @Schema(description = "Indicador intern del servei definit al contracte.")
    private String intern;
    @Schema(description = "Indicador de publicació del servei.")
    private String publicat;
    @Schema(description = "Indica si el servei està actiu en LOPD.")
    private Boolean actiuLOPD;
    @Schema(description = "Codi del tipus de tramitació.")
    private Long tipusTramitacioCodi;
    @Schema(description = "Nom del tipus de tramitació.")
    private String tipusTramitacioNom;
    @Schema(description = "Indica si la tramitació és presencial.")
    private Boolean tramitPresencial;
    @Schema(description = "Indica si la tramitació és electrònica.")
    private Boolean tramitElectronica;
    @Schema(description = "Indica si la tramitació és telefònica.")
    private Boolean tramitTelefonica;
    @Schema(description = "Enllaç telemàtic a la tramitació.")
    private String urlTramitacio;
    @Schema(description = "Codi de la plataforma de tramitació.")
    private Long plataformaTramitCodi;
    @Schema(description = "Nom de la plataforma de tramitació.")
    private String plataformaTramitNom;
    @Schema(description = "Codi de la plantilla de tramitació.")
    private Long plantillaTramitCodi;
    @Schema(description = "Nom de la plantilla de tramitació.")
    private String plantillaTramitNom;
    @Schema(description = "Públics objectiu, aplanats a codi i nom.")
    private List<PublicObjectiu> publicsObjectius;

    public Servei() {
        // Constructor por defecto.
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

    public String getDataPublicacio() {
        return dataPublicacio;
    }

    public void setDataPublicacio(final String dataPublicacio) {
        this.dataPublicacio = dataPublicacio;
    }

    public String getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(final String dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
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

    public Long getUaInstructorCodi() {
        return uaInstructorCodi;
    }

    public void setUaInstructorCodi(final Long uaInstructorCodi) {
        this.uaInstructorCodi = uaInstructorCodi;
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

    public String getDestinataris() {
        return destinataris;
    }

    public void setDestinataris(final String destinataris) {
        this.destinataris = destinataris;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(final String estat) {
        this.estat = estat;
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

    public String getIntern() {
        return intern;
    }

    public void setIntern(final String intern) {
        this.intern = intern;
    }

    public String getPublicat() {
        return publicat;
    }

    public void setPublicat(final String publicat) {
        this.publicat = publicat;
    }

    public Boolean getActiuLOPD() {
        return actiuLOPD;
    }

    public void setActiuLOPD(final Boolean actiuLOPD) {
        this.actiuLOPD = actiuLOPD;
    }

    public Long getTipusTramitacioCodi() {
        return tipusTramitacioCodi;
    }

    public void setTipusTramitacioCodi(final Long tipusTramitacioCodi) {
        this.tipusTramitacioCodi = tipusTramitacioCodi;
    }

    public String getTipusTramitacioNom() {
        return tipusTramitacioNom;
    }

    public void setTipusTramitacioNom(final String tipusTramitacioNom) {
        this.tipusTramitacioNom = tipusTramitacioNom;
    }

    public Boolean getTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(final Boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public Boolean getTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(final Boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public Boolean getTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(final Boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public String getUrlTramitacio() {
        return urlTramitacio;
    }

    public void setUrlTramitacio(final String urlTramitacio) {
        this.urlTramitacio = urlTramitacio;
    }

    public Long getPlataformaTramitCodi() {
        return plataformaTramitCodi;
    }

    public void setPlataformaTramitCodi(final Long plataformaTramitCodi) {
        this.plataformaTramitCodi = plataformaTramitCodi;
    }

    public String getPlataformaTramitNom() {
        return plataformaTramitNom;
    }

    public void setPlataformaTramitNom(final String plataformaTramitNom) {
        this.plataformaTramitNom = plataformaTramitNom;
    }

    public Long getPlantillaTramitCodi() {
        return plantillaTramitCodi;
    }

    public void setPlantillaTramitCodi(final Long plantillaTramitCodi) {
        this.plantillaTramitCodi = plantillaTramitCodi;
    }

    public String getPlantillaTramitNom() {
        return plantillaTramitNom;
    }

    public void setPlantillaTramitNom(final String plantillaTramitNom) {
        this.plantillaTramitNom = plantillaTramitNom;
    }

    public List<PublicObjectiu> getPublicsObjectius() {
        return publicsObjectius;
    }

    public void setPublicsObjectius(final List<PublicObjectiu> publicsObjectius) {
        this.publicsObjectius = publicsObjectius;
    }

    /**
     * Construye la salida pública del servicio. Los getters específicos de
     * servicio han cambiado entre versiones de ROLSAC2; para esos campos se
     * usa acceso compatible por reflexión y así evitar acoplar el API externa
     * a una única versión del DTO.
     * <p>
     * Los datos de contacto (responsableEmail, responsableTelefono e
     * incidenciasEmail) NO se exponen: el apartado general del PDF indica que
     * procedimientos y servicios no incluirán datos de contacto.
     */
    public Servei(final ServicioDTO nodo, final String urlBase, final String idioma,
                  final boolean hateoasEnabled, final String idiomaPorDefecto) {
        try {
            if (nodo == null) {
                return;
            }
            this.codi = nodo.getCodigo();
            this.nom = getTraduccion(nodo.getNombreProcedimientoWorkFlow(), idioma, idiomaPorDefecto);
            this.dataActualizacio = toIso8601(nodo.getFechaActualizacion());
            this.dataPublicacio = toIso8601(nodo.getFechaPublicacion());
            this.dataCaducitat = toIso8601(nodo.getFechaCaducidad());
            this.codiSIA = nodo.getCodigoSIA() == null ? null : String.valueOf(nodo.getCodigoSIA());
            this.estatSIA = nodo.getEstadoSIA();
            this.dataSIA = toIso8601(nodo.getFechaSIA());
            this.destinataris = getTraduccion(nodo.getDestinatarios(), idioma, idiomaPorDefecto);
            this.objecte = getTraduccion(nodo.getObjeto(), idioma, idiomaPorDefecto);
            this.estat = nodo.getEstado() == null ? null : nodo.getEstado().name();
            this.comu = nodo.getComun() != 0;
            this.habilitatApoderat = nodo.isHabilitadoApoderado();
            this.habilitatFuncionari = toBooleanFlag(nodo.getHabilitadoFuncionario());
            this.terminiResolucio = getTraduccion(nodo.getTerminoResolucion(), idioma, idiomaPorDefecto);

            this.uaResponsableNom = getTraduccion(nodo.getUaResponsableLiteral(), idioma, idiomaPorDefecto);
            this.uaResponsableCodi = longFromDirectOrNested(nodo,
                    new String[]{"getUaResponsableCodigo", "getCodigoUaResponsable"},
                    new String[]{"getUaResponsable", "getUnidadResponsable"});
            if (nodo.getUaInstructor() != null) {
                this.uaInstructorCodi = nodo.getUaInstructor().getCodigo();
                this.uaInstructorNom = getDescripcionUA(nodo.getUaInstructor(), idioma, idiomaPorDefecto);
            }

            this.intern = stringValue(invokeAny(nodo, "getInterno", "getIntern"));
            this.publicat = stringValue(invokeAny(nodo, "getPublicado", "getPublicat"));
            this.actiuLOPD = booleanValue(invokeAny(nodo, "getActivoLopd", "getActiuLOPD", "isActivoLopd", "isActiuLOPD"));

            Object tipusTramitacio = invokeAny(nodo, "getTipoTramitacion", "getTipusTramitacio", "getTipoTramite");
            this.tipusTramitacioCodi = firstLong(
                    invokeAny(nodo, "getTipoTramitacionCodigo", "getTipusTramitacioCodi"),
                    invokeAny(tipusTramitacio, "getCodigo", "getId"));
            this.tipusTramitacioNom = firstText(idioma, idiomaPorDefecto,
                    invokeAny(nodo, "getTipoTramitacionNombre", "getTipusTramitacioNom"),
                    invokeAny(tipusTramitacio, "getDescripcion", "getNombre"));

            this.tramitPresencial = booleanValue(invokeAny(nodo,
                    "getTramitacionPresencial", "getTramitePresencial", "isTramitacionPresencial", "isTramitePresencial"));
            this.tramitElectronica = booleanValue(invokeAny(nodo,
                    "getTramitacionElectronica", "getTramiteElectronico", "isTramitacionElectronica", "isTramiteElectronico"));
            this.tramitTelefonica = booleanValue(invokeAny(nodo,
                    "getTramitacionTelefonica", "getTramiteTelefonico", "isTramitacionTelefonica", "isTramiteTelefonico"));
            this.urlTramitacio = stringValue(invokeAny(nodo,
                    "getUrlTramitacion", "getUrlTramitacio", "getUrlTramiteTelematico", "getEnlaceTramitacion"));

            Object plataforma = invokeAny(nodo, "getPlataformaTramitacion", "getPlataforma", "getPlatTramitElectronica");
            this.plataformaTramitCodi = firstLong(
                    invokeAny(nodo, "getPlataformaTramitacionCodigo", "getPlataformaTramitCodi"),
                    invokeAny(plataforma, "getCodigo", "getId"));
            this.plataformaTramitNom = firstText(idioma, idiomaPorDefecto,
                    invokeAny(nodo, "getPlataformaTramitacionNombre", "getPlataformaTramitNom"),
                    invokeAny(plataforma, "getDescripcion", "getNombre"));

            Object plantilla = invokeAny(nodo, "getPlantillaTramitacion", "getPlantilla", "getTipoTramitacionPlantilla");
            this.plantillaTramitCodi = firstLong(
                    invokeAny(nodo, "getPlantillaTramitacionCodigo", "getPlantillaTramitCodi"),
                    invokeAny(plantilla, "getCodigo", "getId"));
            this.plantillaTramitNom = firstText(idioma, idiomaPorDefecto,
                    invokeAny(nodo, "getPlantillaTramitacionNombre", "getPlantillaTramitNom"),
                    invokeAny(plantilla, "getDescripcion", "getNombre"));

            this.publicsObjectius = mapPublicsObjectius(
                    invokeAny(nodo, "getPublicoObjetivos", "getPublicsObjectius", "getPublicosObjetivo"),
                    idioma, idiomaPorDefecto);
            this.url = resolveSeuUrl(nodo, urlBase, this.codi);
        } catch (Exception e) {
            LOG.error("Error generando servei {}", this.codi, e);
        }
    }

    private String toIso8601(final Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private String getTraduccion(final Literal literal, final String idioma, final String idiomaPorDefecto) {
        if (literal == null) return null;
        String traduccion = literal.getTraduccionConValor(idioma, idiomaPorDefecto);
        return traduccion != null ? traduccion : literal.getTraduccion();
    }

    private String getDescripcionUA(final UnidadAdministrativaDTO ua, final String idioma, final String idiomaPorDefecto) {
        if (ua == null || ua.getNombre() == null) return null;
        String descripcion = ua.getNombre().getTraduccionConValor(idioma, idiomaPorDefecto);
        return descripcion != null ? descripcion : ua.getNombre().getTraduccion();
    }

    private Boolean toBooleanFlag(final String value) {
        if (value == null) return null;
        return "S".equalsIgnoreCase(value) || "1".equals(value) || "true".equalsIgnoreCase(value);
    }

    private Object invokeAny(final Object target, final String... getters) {
        if (target == null || getters == null) return null;
        for (String getter : getters) {
            try {
                java.lang.reflect.Method method = target.getClass().getMethod(getter);
                return method.invoke(target);
            } catch (ReflectiveOperationException ignored) {
                // siguiente getter compatible
            }
        }
        return null;
    }

    private Long longFromDirectOrNested(final Object target, final String[] directGetters, final String[] nestedGetters) {
        Long direct = longValue(invokeAny(target, directGetters));
        if (direct != null) return direct;
        Object nested = invokeAny(target, nestedGetters);
        return longValue(invokeAny(nested, "getCodigo", "getId"));
    }

    private Long firstLong(final Object... values) {
        for (Object value : values) {
            Long parsed = longValue(value);
            if (parsed != null) return parsed;
        }
        return null;
    }

    private Long longValue(final Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).longValue();
        try {
            return Long.valueOf(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean booleanValue(final Object value) {
        if (value == null) return null;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof Number) return ((Number) value).intValue() != 0;
        String text = String.valueOf(value);
        return "S".equalsIgnoreCase(text) || "1".equals(text) || "true".equalsIgnoreCase(text);
    }

    private String stringValue(final Object value) {
        if (value == null) return null;
        String text = String.valueOf(value).trim();
        return text.isEmpty() ? null : text;
    }

    private String firstText(final String idioma, final String idiomaPorDefecto, final Object... values) {
        for (Object value : values) {
            String text = textValue(value, idioma, idiomaPorDefecto);
            if (text != null && !text.trim().isEmpty()) return text;
        }
        return null;
    }

    private String textValue(final Object value, final String idioma, final String idiomaPorDefecto) {
        if (value == null) return null;
        if (value instanceof Literal) return getTraduccion((Literal) value, idioma, idiomaPorDefecto);
        Object nombre = invokeAny(value, "getDescripcion", "getNombre", "getLiteral");
        if (nombre instanceof Literal) return getTraduccion((Literal) nombre, idioma, idiomaPorDefecto);
        return stringValue(value);
    }

    private List<PublicObjectiu> mapPublicsObjectius(final Object value, final String idioma, final String idiomaPorDefecto) {
        if (!(value instanceof Iterable)) return Collections.emptyList();
        List<PublicObjectiu> result = new ArrayList<>();
        for (Object item : (Iterable<?>) value) {
            Object nested = invokeAny(item, "getPublicoObjetivo", "getTipoPublicoObjetivo", "getPublicObjectiu");
            Object source = nested != null ? nested : item;
            Long codi = firstLong(invokeAny(source, "getCodigo", "getId", "getCodigoPublicoObjetivo"));
            String nom = firstText(idioma, idiomaPorDefecto,
                    invokeAny(source, "getDescripcion", "getNombre", "getLiteral"));
            result.add(new PublicObjectiu(codi, nom));
        }
        return result;
    }

    private String resolveSeuUrl(final Object nodo, final String urlBase, final Long codigo) {
        String direct = stringValue(invokeAny(nodo,
                "getUrlSede", "getUrlSEDE", "getUrlSeu", "getUrlPublica", "getUrlServicio", "getUrl"));
        if (direct != null) return direct;
        if (urlBase == null || codigo == null) return null;
        String base = urlBase.endsWith("/") ? urlBase : urlBase + "/";
        return base + codigo;
    }

    @Schema(name = "PublicObjectiu", description = "Públic objectiu aplanat a codi i nom.")
    public static class PublicObjectiu implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long codi;
        private String nom;

        public PublicObjectiu() {
        }

        public PublicObjectiu(final Long codi, final String nom) {
            this.codi = codi;
            this.nom = nom;
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
    }
}
