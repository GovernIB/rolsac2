package es.caib.rolsac2.commons.plugins.traduccion.api;

public abstract class ParametrosTraduccion {

	public static final String PLUGIN = "plugin";
	public static final String PLUGIN_MOCKUP = "mock";
	public static final String PLUGIN_OPENTRAD = "opentrad";
	public static final String PLUGIN_PLATA = "plata";

	/** No es obligatorio el plugin, sino se selecciona la opción por defecto. **/
	private String plugin;

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(final String plugin) {
		this.plugin = plugin;
	}

}
