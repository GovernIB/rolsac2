package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;

@Named
@ViewScoped
public class DialogEvolucionDependenciaUnidadAdministrativa extends EvolucionController implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionDependenciaUnidadAdministrativa.class);

	private UnidadAdministrativaDTO funcionalPadre;
	private UnidadAdministrativaDTO organicoPadre;

	public void load() {
		LOG.debug("init1");

		this.setearIdioma();
		if (id != null && id.split(",").length > 1) {
			ids = id.split(",");
		}

		if (ids != null && ids.length > 0) {
			data = unidadAdministrativaServiceFacade.findById(Long.valueOf(ids[0]));
		} else if (id != null) {
			data = unidadAdministrativaServiceFacade.findById(Long.valueOf(id));
		}
	}

	public void evolucionar() {
		FacesMessage msg = new FacesMessage("Successful", "Aceptar pendiente de implementación");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<UnidadAdministrativaDTO> completeUnidadAdministrativa(final String query) {
		List<UnidadAdministrativaDTO> suggestions = new ArrayList<UnidadAdministrativaDTO>();
		UnidadAdministrativaFiltro filtro = new UnidadAdministrativaFiltro();
		filtro.setIdioma(getIdioma());

		if (query == null || query.isBlank()) {
			filtro.setPaginaFirst(0);
			filtro.setPaginaTamanyo(10);
		}

		filtro.setNombre(StringUtils.stripAccents(query.toLowerCase()));

		Pagina<UnidadAdministrativaDTO> resultado = unidadAdministrativaServiceFacade.findByFiltroRest(filtro);

		if (resultado != null) {
			suggestions = resultado.getItems();
		}

		return suggestions;
	}

	public Literal getUaDestino() {
		return uaDestino;
	}

	public void setUaDestino(Literal uaDestino) {
		this.uaDestino = uaDestino;
	}

	public UnidadAdministrativaDTO getFuncionalPadre() {
		return funcionalPadre;
	}

	public void setFuncionalPadre(UnidadAdministrativaDTO funcionalPadre) {
		this.funcionalPadre = funcionalPadre;
	}

	public UnidadAdministrativaDTO getOrganicoPadre() {
		return organicoPadre;
	}

	public void setOrganicoPadre(UnidadAdministrativaDTO organicoPadre) {
		this.organicoPadre = organicoPadre;
	}
}
