package es.caib.rolsac2.commons.plugins.sia.api.model;

import java.io.Serializable;

/**
 * Clase que representa una normativa en SIA.
 * 
 * @author Indra
 */
public class NormativaSIA implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** ID Rango normativo. */
	private Integer idRangoNormativo;

	/** Titulo Normativa. */
	private String titulo;

	/**
	 * Constructor.
	 *
	 * @param idRangoNormativo the id rango normativo
	 * @param titulo the titulo
	 */
	public NormativaSIA(Integer idRangoNormativo, String titulo) {
		super();
		this.idRangoNormativo = idRangoNormativo;
		this.titulo = titulo;
	}

	/**
	 * Gets the id rango normativo.
	 *
	 * @return the idRangoNormativo
	 */
	public Integer getIdRangoNormativo() {
		return idRangoNormativo;
	}

	/**
	 * Sets the id rango normativo.
	 *
	 * @param idRangoNormativo the idRangoNormativo to set
	 */
	public void setIdRangoNormativo(Integer idRangoNormativo) {
		this.idRangoNormativo = idRangoNormativo;
	}

	/**
	 * Gets the titulo.
	 *
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Sets the titulo.
	 *
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
