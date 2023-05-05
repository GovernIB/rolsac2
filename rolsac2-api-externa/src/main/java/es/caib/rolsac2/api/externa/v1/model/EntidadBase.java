package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.api.externa.v1.utils.Utiles;

/**
 * RespuestaBase. Estructura de respuesta que contiene la información comun a
 * todas las respuestas.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "EntidadBase", description = "Entidad Base")
public abstract class EntidadBase<V> extends EntidadJson<V> {

	@Schema(hidden = true)
	@JsonIgnore
	protected List<String> SETTERS_INVALIDS = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

		{
			add("setSerializer");
			add("setDeserializer");
			add("setTypeDesc");
		}
	};

	private static final Logger LOG = LoggerFactory.getLogger(EntidadBase.class);

	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	protected boolean hateoasEnabled;

	public EntidadBase() {
		super();
		addSetersInvalidos();
	}

	/**
	 * Permite generar una clase a partir de los resultados del EJB (tr)
	 *
	 * @param tr             Origen de datos, clase de la que copiar las propiedades
	 *                       (deben llamarse igual que en la clase actual)
	 * @param urlBase
	 * @param idioma
	 * @param hateoasEnabled
	 */
	public <T> EntidadBase(T tr, String urlBase, String idioma, boolean hateoasEnabled) {
		super();
		addSetersInvalidos();
		this.fill(tr, urlBase, idioma, hateoasEnabled);
	}

	/**
	 * Función que permite copiar los valores de las propiedades de tr en la clase
	 * actual asigna el hateoas,copia las propiedades y genera los links
	 *
	 * @param tr
	 * @param urlBase
	 * @param idioma
	 * @param hateoasEnabled
	 */
	public <T> void fill(T tr, String urlBase, String idioma, boolean hateoasEnabled) {
		setHateoasEnabled(hateoasEnabled);
		copiaPropiedadesDeEntity(tr, idioma);
		generaLinks(urlBase);
	}

	public Link generaLink(String codigo, String entidad, String url, String urlBase, String descripcion) {
		if (codigo != null) {
			return new Link(entidad, codigo,
					(StringUtils.isEmpty(urlBase) ? Constantes.URL_BASE : urlBase) + url.replace("{0}", codigo),
					descripcion, hateoasEnabled);
		}
		return null;
	}

	public Link generaLink(Long codigo, String entidad, String url, String urlBase, String descripcion) {
		if (codigo != null) {
			return new Link(entidad, codigo.toString(),
					(StringUtils.isEmpty(urlBase) ? Constantes.URL_BASE : urlBase) + url.replace("{0}", codigo + ""),
					descripcion, hateoasEnabled);
		}
		return null;
	}
	/**
	 * @return the hateoasEnabled
	 */
	@XmlTransient
	public boolean isHateoasEnabled() {
		return hateoasEnabled;
	}

	/**
	 * @param hateoasEnabled the hateoasEnabled to set
	 */
	public void setHateoasEnabled(boolean hateoasEnabled) {
		this.hateoasEnabled = hateoasEnabled;
	}

	public void copiaPropiedadesDeEntity(Object entity, String lang) {
		if (entity == null) {
			return;
		}
		try {
			Method[] dtoMethods = this.getClass().getMethods();
			Method method;
			StringBuilder errores = new StringBuilder();

			for (int i = 0; i < dtoMethods.length; i++) {
				method = dtoMethods[i];
				if (method.getName().startsWith("set") && !SETTERS_INVALIDS.contains(method.getName())) {
					try {
						Utiles.copyProperty(entity, this, method, lang);
					} catch (Exception e) {
						errores.append("Error al copiar la propiedad:");
						errores.append(method.getName());
						errores.append(";");
					}

				}
			}
			if (errores.length() > 0) {
				throw new Exception(errores.toString());
			}
		} catch (Exception e) {
			LOG.error("Error al copiar las propiedades en " + this.getClass() + " a partir de " + entity.getClass()
					+ ".-->" + e.getMessage(), e);
		}
	}

	/**
	 * Función que permite añadir nuevos setters a la lista de setters a omitir al
	 * volcar las propiedades de la clase referenciada en el constructor como origen
	 * de datos
	 */
	protected abstract void addSetersInvalidos(); // { }

	/**
	 * Función que permite especificar los links a las diferentes entidades. debe
	 * sobreescribirse para incluirlos.
	 *
	 * @param urlBase
	 */
	protected abstract void generaLinks(String urlBase); // {}

	/**
	 * Función necesaria para convertir la propiedad Id (proviene de los EJB) a la
	 * propiedad código
	 *
	 * @param codigo (id)s
	 */
	public abstract void setId(Long codigo);
}
