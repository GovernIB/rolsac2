package es.caib.rolsac2.commons.plugins.traduccion.api;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "ResultadoTraduccionTexto")
public class ResultadoTraduccionTexto {

	/** Campos **/
	private boolean error = true;
	private String descripcionError;
	private String textoTraducido;

	public boolean isError() {
		return error;
	}

	public void setError(final boolean error) {
		this.error = error;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(final String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public String getTextoTraducido() {
		return textoTraducido;
	}

	public void setTextoTraducido(final String textoTraducido) {
		this.textoTraducido = textoTraducido;
	}

}
