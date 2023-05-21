package es.caib.rolsac2.service.model.types;

/**
 * Plugins entidad.
 *
 * @author Indra
 *
 */
public enum TypePluginEntidad {

	/**
	 * Boletín (Entidad).
	 */
	BOLETIN("BOL"),

	/**
	 * Traducción (Entidad)
	 */
	TRADUCCION("TRA"),

	/**
	 * DIR3
	 */
	DIR3("DI3"),

	/**
	 * Indexación
	 */
	INDEXACION("IDX"),

	/**
	 * SIA
	 */
	SIA("SIA"),

	/**
	 * EMAIL
	 */
	EMAIL("EMA");

	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param valueStr
	 *                     Valor como string.
	 */
	private TypePluginEntidad(final String valueStr) {
		stringValue = valueStr;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *                 string
	 * @return TypeSiNo
	 */
	public static TypePluginEntidad fromString(final String text) {
		TypePluginEntidad respuesta = null;
		if (text != null) {
			for (final TypePluginEntidad b : TypePluginEntidad.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
