package es.caib.rolsac2.service.model;

/**
 *
 * Clase propiedad básica de codigo por valor.
 *
 * @author Indra
 *
 */

public final class Propiedad extends ModelApi {

	/**
	 * Código.
	 */
	private String codigo;

	/**
	 * Valor
	 */
	private String valor;

	/**
	 * Orden.
	 */
	private Integer orden;

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final Integer orden) {
		this.orden = orden;
	}

}
