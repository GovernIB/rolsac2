package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class DialogEvolucionFusionUnidadAdministrativa extends EvolucionController implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionFusionUnidadAdministrativa.class);

	private List<UnidadAdministrativaGridDTO> selectedUnidades;
	private UnidadAdministrativaGridDTO uaSeleccionada;

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

		selectedUnidades = new ArrayList<UnidadAdministrativaGridDTO>();
		selectedUnidades.add(data.convertDTOtoGridDTO());

		uaDestino = new Literal();

		Traduccion traduccionModificada;
		String textoTraduccion = "";

		for (Traduccion traduccion : data.getNombre().getTraducciones()) {
			textoTraduccion = traduccion.getLiteral() + " (FUSIONADO)";

			traduccionModificada = new Traduccion(traduccion.getIdioma(), textoTraduccion);

			uaDestino.add(traduccionModificada);
		}
	}

	public void evolucionar() {
		FacesMessage msg = new FacesMessage("Successful", "Aceptar pendiente de implementación");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Método para consultar el detalle de una UA
	 */
	public void consultarUA() {
		if (uaSeleccionada == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
		} else {
			abrirDialogUAs(TypeModoAcceso.CONSULTA);
		}
	}

	/**
	 * Abrir dialogo de Selección de Unidades Administrativas
	 */
	public void abrirDialogUAs(TypeModoAcceso modoAcceso) {

		if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
			final Map<String, String> params = new HashMap<>();
			params.put("ID", uaSeleccionada.getCodigo().toString());
			UtilJSF.openDialog("dialogUnidadAdministrativa", modoAcceso, params, true, 1530, 733);
		} else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
			UtilJSF.anyadirMochila("unidadesAdministrativas", selectedUnidades);
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.ALTA.toString());
			// params.put("esCabecera", "true");
			String direccion = "/comun/dialogSeleccionarUA";
			UtilJSF.openDialog(direccion, modoAcceso, params, true, 850, 575);
		}
	}

	/**
	 * Método para dar de alta UAs en un usuario
	 */
	public void anyadirUAs() {
		abrirDialogUAs(TypeModoAcceso.ALTA);
	}

	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (respuesta != null && !respuesta.isCanceled()
				&& !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
			UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
			uaSeleccionada = unidadAdministrativaServiceFacade.findById(uaSeleccionada.getCodigo());
			if (uaSeleccionada != null) {
				UnidadAdministrativaGridDTO uaSeleccionadaGrid = uaSeleccionada.convertDTOtoGridDTO();
				if (uaSeleccionadaGrid.getIdEntidad() == null) {
					uaSeleccionadaGrid.setIdEntidad(sessionBean.getEntidad().getCodigo());
				}
				// verificamos qeu la UA no esté seleccionada ya, en caso de estarlo mostramos
				// mensaje
				if (selectedUnidades == null) {
					selectedUnidades = new ArrayList<>();
				}
				if (selectedUnidades.contains(uaSeleccionadaGrid)) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.elementoRepetido"));
				} else {
					selectedUnidades.add(uaSeleccionadaGrid);
//                    actualizarUasEntidad();
				}
			}
		}
	}

	/**
	 * Método para borrar un usuario en una UA
	 */
	public void borrarUA() {
		if (uaSeleccionada == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
		} else {
			selectedUnidades.remove(uaSeleccionada);
			uaSeleccionada = null;
			addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
		}
	}

	public void editarUnidadAdministrativa() {
		FacesMessage msg = new FacesMessage("Successful", "Edición pendiente de implementación");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

//	private List<UnidadAdministrativaDTO> completeUnidadesAdministrativas() {
//		List<UnidadAdministrativaDTO> unidades = new ArrayList<>();
//		UnidadAdministrativaFiltro filtro = new UnidadAdministrativaFiltro();
//		filtro.setIdioma(getIdioma());
//		Pagina<UnidadAdministrativaDTO> resultado = unidadAdministrativaServiceFacade.findByFiltroRest(filtro);
//		if (resultado != null) {
//			unidades = resultado.getItems();
//		}
//
//		return unidades;
//	}

	public UnidadAdministrativaGridDTO getUaSeleccionada() {
		return uaSeleccionada;
	}

	public void setUaSeleccionada(UnidadAdministrativaGridDTO uaSeleccionada) {
		this.uaSeleccionada = uaSeleccionada;
	}

	public void setSelectedUnidades(List<UnidadAdministrativaGridDTO> selectedUnidades) {
		this.selectedUnidades = selectedUnidades;
	}

	public List<UnidadAdministrativaGridDTO> getSelectedUnidades() {
		return selectedUnidades;
	}
}
