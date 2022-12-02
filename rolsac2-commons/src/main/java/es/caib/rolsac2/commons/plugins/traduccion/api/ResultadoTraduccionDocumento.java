package es.caib.rolsac2.commons.plugins.traduccion.api;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "ResultadoTraduccionDocumento")
public class ResultadoTraduccionDocumento {

	/** Campos **/
	private boolean error = true;
	private String descripcionError;
	private String textoTraducido;
	private String direccion;
	private String checksum;
	private String tipo;
	
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
