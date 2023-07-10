package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.ServicioGridDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class DialogEvolucionDivisionUnidadAdministrativa extends EvolucionController implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionDivisionUnidadAdministrativa.class);

	private List<UnidadAdministrativaGridDTO> selectedUnidades;
	private UnidadAdministrativaGridDTO uaSeleccionada;
	private UnidadAdministrativaGridDTO uaSeleccionadaReasignar;

	private Map<Long, List<UsuarioGridDTO>> listasUsuarios;
	private Map<Long, List<UnidadAdministrativaGridDTO>> listasUasHijas;
	private Map<Long, List<NormativaGridDTO>> listasNormativas;
	private Map<Long, List<ProcedimientoGridDTO>> listasProcedimientos;
	private Map<Long, List<ServicioGridDTO>> listasServicios;
	private Map<Long, List<TemaGridDTO>> listasTemas;

	private List<Integer> selectores;

	private List<UsuarioGridDTO> usuarios;
	private List<UnidadAdministrativaGridDTO> uasHijas;
	private List<NormativaGridDTO> normativas;
	private List<ProcedimientoGridDTO> procedimientos;
	private List<ServicioGridDTO> servicios;
	private List<TemaGridDTO> temas;

	@EJB
	ProcedimientoServiceFacade procedimientoServiceFacade;

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

		uaDestino = new Literal();
		setSelectedUnidades(new ArrayList<UnidadAdministrativaGridDTO>());
	}

	public void cargarUAs() {
		UnidadAdministrativaDTO unidad = null;
		listasUsuarios = new HashMap<>();
		listasUasHijas = new HashMap<>();
		listasNormativas = new HashMap<>();
		listasProcedimientos = new HashMap<>();
		listasServicios = new HashMap<>();
		listasTemas = new HashMap<>();

		for (UnidadAdministrativaGridDTO ua : selectedUnidades) {
			unidad = unidadAdministrativaServiceFacade.findById(ua.getCodigo());

			listasUasHijas.put(unidad.getCodigo(),
					unidadAdministrativaServiceFacade.getHijosGrid(unidad.getCodigo(), getIdioma()));
			listasUsuarios.put(unidad.getCodigo(), unidad.getUsuariosUnidadAdministrativa());
			listasTemas.put(unidad.getCodigo(), unidad.getTemas());

			NormativaFiltro filtroNormativa = new NormativaFiltro();
			filtroNormativa.setIdUA(unidad.getCodigo());
			filtroNormativa.setIdioma(getIdioma());
			Pagina<NormativaGridDTO> paginaNormativa = normativaServiceFacade.findByFiltro(filtroNormativa);
			if (paginaNormativa != null) {
				listasNormativas.put(unidad.getCodigo(), paginaNormativa.getItems());
			}

			ProcedimientoFiltro filtroProcedimiento = new ProcedimientoFiltro();
			filtroProcedimiento.setIdUA(unidad.getCodigo());
			filtroProcedimiento.setIdioma(getIdioma());
			Pagina<ProcedimientoGridDTO> paginaProcedimiento = procedimientoServiceFacade
					.findProcedimientosByFiltro(filtroProcedimiento);
			if (paginaProcedimiento != null) {
				listasProcedimientos.put(unidad.getCodigo(), paginaProcedimiento.getItems());
			}

			Pagina<ServicioGridDTO> paginaServicio = procedimientoServiceFacade
					.findServiciosByFiltro(filtroProcedimiento);
			if (paginaServicio != null) {
				listasServicios.put(unidad.getCodigo(), paginaServicio.getItems());
			}
		}
	}

	public void seleccionarUA() {
		if (uaSeleccionadaReasignar != null) {
			if (listasUsuarios == null || listasUasHijas == null || listasNormativas == null
					|| listasProcedimientos == null || listasServicios == null || listasTemas == null) {
				cargarUAs();
			}
//			else {
//				listasUasHijas.put(uaSeleccionadaReasignar.getCodigo(), uasHijas);
//				listasUsuarios.put(uaSeleccionadaReasignar.getCodigo(), usuarios);
//				listasTemas.put(uaSeleccionadaReasignar.getCodigo(), temas);
//				listasNormativas.put(uaSeleccionadaReasignar.getCodigo(), normativas);
//				listasProcedimientos.put(uaSeleccionadaReasignar.getCodigo(), procedimientos);
//				listasServicios.put(uaSeleccionadaReasignar.getCodigo(), servicios);
//			}

			uasHijas = listasUasHijas.get(uaSeleccionadaReasignar.getCodigo());
			usuarios = listasUsuarios.get(uaSeleccionadaReasignar.getCodigo());
			temas = listasTemas.get(uaSeleccionadaReasignar.getCodigo());
			normativas = listasNormativas.get(uaSeleccionadaReasignar.getCodigo());
			procedimientos = listasProcedimientos.get(uaSeleccionadaReasignar.getCodigo());
			servicios = listasServicios.get(uaSeleccionadaReasignar.getCodigo());
		} else {
			listasUsuarios = null;
			listasUasHijas = null;
			listasNormativas = null;
			listasProcedimientos = null;
			listasServicios = null;
			listasTemas = null;
		}

	}

	public void deseleccionarUA() {
		if(uaSeleccionadaReasignar != null) {
			listasUasHijas.put(uaSeleccionadaReasignar.getCodigo(), uasHijas);
			listasUsuarios.put(uaSeleccionadaReasignar.getCodigo(), usuarios);
			listasTemas.put(uaSeleccionadaReasignar.getCodigo(), temas);
			listasNormativas.put(uaSeleccionadaReasignar.getCodigo(), normativas);
			listasProcedimientos.put(uaSeleccionadaReasignar.getCodigo(), procedimientos);
			listasServicios.put(uaSeleccionadaReasignar.getCodigo(), servicios);
		}

		uasHijas = null;
		usuarios = null;
		temas = null;
		normativas = null;
		procedimientos = null;
		servicios = null;
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

			uaSeleccionadaReasignar = null;
			uasHijas = null;
			usuarios = null;
			temas = null;
			normativas = null;
			procedimientos = null;
			servicios = null;

			listasUsuarios = null;
			listasUasHijas = null;
			listasNormativas = null;
			listasProcedimientos = null;
			listasServicios = null;
			listasTemas = null;

			addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
		}
	}

	public UnidadAdministrativaGridDTO getUaSeleccionada() {
		return uaSeleccionada;
	}

	public void setUaSeleccionada(UnidadAdministrativaGridDTO uaSeleccionada) {
		this.uaSeleccionada = uaSeleccionada;
	}

	public List<UnidadAdministrativaGridDTO> getSelectedUnidades() {
		if (selectedUnidades != null) {
			int i = 1;
			for (UnidadAdministrativaGridDTO unidad : selectedUnidades) {
				unidad.setNumero(i++);
			}
		}
		return selectedUnidades;
	}

	public void setSelectedUnidades(List<UnidadAdministrativaGridDTO> selectedUnidades) {
		this.selectedUnidades = selectedUnidades;
	}

	public UnidadAdministrativaGridDTO getUaSeleccionadaReasignar() {
		return uaSeleccionadaReasignar;
	}

	public void setUaSeleccionadaReasignar(UnidadAdministrativaGridDTO uaSeleccionadaReasignar) {
		this.uaSeleccionadaReasignar = uaSeleccionadaReasignar;
	}

	public List<UsuarioGridDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioGridDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UnidadAdministrativaGridDTO> getUasHijas() {
		return uasHijas;
	}

	public void setUasHijas(List<UnidadAdministrativaGridDTO> uasHijas) {
		this.uasHijas = uasHijas;
	}

	public List<NormativaGridDTO> getNormativas() {
		return normativas;
	}

	public void setNormativas(List<NormativaGridDTO> normativas) {
		this.normativas = normativas;
	}

	public List<Integer> getSelectores() {
		if(selectores == null) {
			selectores = new ArrayList<>();

			selectores.add(0);

			int i = 1;

			for(UnidadAdministrativaGridDTO unidad : selectedUnidades) {
				selectores.add(i);
				i++;
			}

		}
		return selectores;
	}

	public void setSelectores(List<Integer> selectores) {
		this.selectores = selectores;
	}

	public List<ProcedimientoGridDTO> getProcedimientos() {
		return procedimientos;
	}

	public void setProcedimientos(List<ProcedimientoGridDTO> procedimientos) {
		this.procedimientos = procedimientos;
	}

	public List<ServicioGridDTO> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServicioGridDTO> servicios) {
		this.servicios = servicios;
	}

	public List<TemaGridDTO> getTemas() {
		return temas;
	}

	public void setTemas(List<TemaGridDTO> temas) {
		this.temas = temas;
	}

	public Map<Long, List<UsuarioGridDTO>> getListasUsuarios() {
		return listasUsuarios;
	}

	public void setListasUsuarios(Map<Long, List<UsuarioGridDTO>> listasUsuarios) {
		this.listasUsuarios = listasUsuarios;
	}

	public Map<Long, List<UnidadAdministrativaGridDTO>> getListasUasHijas() {
		return listasUasHijas;
	}

	public void setListasUasHijas(Map<Long, List<UnidadAdministrativaGridDTO>> listasUasHijas) {
		this.listasUasHijas = listasUasHijas;
	}

	public Map<Long, List<NormativaGridDTO>> getListasNormativas() {
		return listasNormativas;
	}

	public void setListasNormativas(Map<Long, List<NormativaGridDTO>> listasNormativas) {
		this.listasNormativas = listasNormativas;
	}

	public Map<Long, List<ProcedimientoGridDTO>> getListasProcedimientos() {
		return listasProcedimientos;
	}

	public void setListasProcedimientos(Map<Long, List<ProcedimientoGridDTO>> listasProcedimientos) {
		this.listasProcedimientos = listasProcedimientos;
	}

	public Map<Long, List<ServicioGridDTO>> getListasServicios() {
		return listasServicios;
	}

	public void setListasServicios(Map<Long, List<ServicioGridDTO>> listasServicios) {
		this.listasServicios = listasServicios;
	}

	public Map<Long, List<TemaGridDTO>> getListasTemas() {
		return listasTemas;
	}

	public void setListasTemas(Map<Long, List<TemaGridDTO>> listasTemas) {
		this.listasTemas = listasTemas;
	}

}
