package es.caib.rolsac2.commons.plugins.traduccion.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Opciones")
public class Opciones implements Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Campos **/
	private List<PropiedadValor> propiedades = new ArrayList<PropiedadValor>();

	public List<PropiedadValor> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(final List<PropiedadValor> propiedades) {
		this.propiedades = propiedades;
	}

	public void addPropiedadValor(final String prop, final String val) {
		if (propiedades == null) {
			propiedades = new ArrayList<PropiedadValor>();
		}
		final PropiedadValor propiedad = new PropiedadValor();
		propiedad.setPropiedad(prop);
		propiedad.setValor(val);
		propiedades.add(propiedad);

	}

	public boolean contains(final String nombrePropiedad) {
		boolean contiene = false;
		if (propiedades != null) {
			for (final PropiedadValor prop : propiedades) {
				if (prop.getPropiedad().equals(nombrePropiedad)) {
					contiene = true;
					break;
				}
			}
		}

		return contiene;
	}

	public String getValor(final String nombrePropiedad) {
		String valor = null;
		if (propiedades != null) {
			for (final PropiedadValor prop : propiedades) {
				if (prop.getPropiedad().equals(nombrePropiedad)) {
					valor = prop.getValor();
					break;
				}
			}
		}

		return valor;
	}

}
