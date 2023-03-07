package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Dades d'un Entidad.
 *
 * @author jsegovia
 */
@XmlRootElement
@Schema(name = "EntidadExterna", description = Constantes.TXT_DEFINICION_CLASE +  Constantes.ENTIDAD_ENTIDADES)
public class Entidad extends EntidadBase {

	private static final Logger LOG = LoggerFactory.getLogger(Entidad.class);

	@Schema(description = "codigo", required = false)
    private Long codigo;

	@Schema(description = "identificador", required = false)
    private String identificador;

	@Schema(description = "activa", required = false)
    private Boolean activa;

	@Schema(description = "rolAdmin", required = false)
    private String rolAdmin;

	@Schema(description = "rolAdminContenido", required = false)
    private String rolAdminContenido;

	@Schema(description = "rolGestor", required = false)
    private String rolGestor;

	@Schema(description = "rolInformador", required = false)
    private String rolInformador;

//    private FicheroDTO logo;

	@Schema(description = "descripcion", required = false)
    private String descripcion;

	@Schema(description = "idiomaDefectoRest", required = false)
    private String idiomaDefectoRest;

	@Schema(description = "idiomasPermitidos", required = false)
    private String idiomasPermitidos;

	@Schema(description = "idiomasObligatorios", required = false)
    private String idiomasObligatorios;

    /**
     * Instancia una nueva Entidad dto.
     */
    public Entidad() {
    }

    /**
     * Instancia una nueva Entidad dto.
     *
     * @param otro the otro
     */
    public Entidad(Entidad otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.identificador = otro.identificador;
            this.activa = otro.activa;
            this.rolAdmin = otro.rolAdmin;
            this.rolAdminContenido = otro.rolAdminContenido;
            this.rolGestor = otro.rolGestor;
            this.rolInformador = otro.rolInformador;
            this.descripcion = otro.descripcion;
            this.idiomaDefectoRest = otro.idiomaDefectoRest;
            this.idiomasPermitidos = otro.idiomasPermitidos;
            this.idiomasObligatorios = otro.idiomasObligatorios;
        }
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instancia una nueva Entidad dto.
     *
     * @param id the id
     */
    public Entidad(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return el codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo el codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene activa.
     *
     * @return activa
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * Establece activa.
     *
     * @param activa activa
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    /**
     * Obtiene rol admin.
     *
     * @return el rol admin
     */
    public String getRolAdmin() {
        return rolAdmin;
    }

    /**
     * Establece rol admin.
     *
     * @param rolAdmin el rol admin
     */
    public void setRolAdmin(String rolAdmin) {
        this.rolAdmin = rolAdmin;
    }

    /**
     * Obtiene rol admin contenido.
     *
     * @return el rol admin contenido
     */
    public String getRolAdminContenido() {
        return rolAdminContenido;
    }

    /**
     * Establece rol admin contenido.
     *
     * @param rolAdminContenido el rol admin contenido
     */
    public void setRolAdminContenido(String rolAdminContenido) {
        this.rolAdminContenido = rolAdminContenido;
    }

    /**
     * Obtiene rol gestor.
     *
     * @return el rol gestor
     */
    public String getRolGestor() {
        return rolGestor;
    }

    /**
     * Establece rol gestor.
     *
     * @param rolGestor el rol gestor
     */
    public void setRolGestor(String rolGestor) {
        this.rolGestor = rolGestor;
    }

    /**
     * Obtiene rol informador.
     *
     * @return el rol informador
     */
    public String getRolInformador() {
        return rolInformador;
    }

    /**
     * Establece rol informador.
     *
     * @param rolInformador el rol informador
     */
    public void setRolInformador(String rolInformador) {
        this.rolInformador = rolInformador;
    }

    /**
     * Obtiene identificador.
     *
     * @return el identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador el identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene idioma defecto rest.
     *
     * @return el idioma defecto rest
     */
    public String getIdiomaDefectoRest() {
        return idiomaDefectoRest;
    }

    /**
     * Establece idioma defecto rest.
     *
     * @param idiomaDefectoRest el idioma defecto rest
     */
    public void setIdiomaDefectoRest(String idiomaDefectoRest) {
        this.idiomaDefectoRest = idiomaDefectoRest;
    }

    /**
     * Obtiene idiomas permitidos.
     *
     * @return los idiomas permitidos
     */
    public String getIdiomasPermitidos() {
        return idiomasPermitidos;
    }

    /**
     * Establece idiomas permitidos.
     *
     * @param idiomasPermitidos los idiomas permitidos
     */
    public void setIdiomasPermitidos(String idiomasPermitidos) {
        this.idiomasPermitidos = idiomasPermitidos;
    }

    /**
     * Obtiene idiomas obligatorios.
     *
     * @return los idiomas obligatorios
     */
    public String getIdiomasObligatorios() {
        return idiomasObligatorios;
    }

    /**
     * Establece idiomas obligatorios.
     *
     * @param idiomasObligatorios los idiomas obligatorios
     */
    public void setIdiomasObligatorios(String idiomasObligatorios) {
        this.idiomasObligatorios = idiomasObligatorios;
    }
    public static Normativa valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<Normativa> typeRef = new TypeReference<Normativa>() {
		};
		Normativa obj;
		try {
			obj = (Normativa) objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return obj;
	}


	public String toJson() {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
			return objectMapper.writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}


	@Override
	protected void addSetersInvalidos() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void generaLinks(String urlBase) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(Long codigo) {
		this.codigo = codigo;
	}
}
