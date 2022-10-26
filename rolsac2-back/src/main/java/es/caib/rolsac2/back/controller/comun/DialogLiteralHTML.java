package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para seleccionar una UA/entidad.
 *
 * @author areus
 */
@Named
@ViewScoped
public class DialogLiteralHTML extends AbstractController implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(DialogLiteralHTML.class);

	private String id;

	/** Visible lista. **/
	private Map<String, Boolean> visible = new HashMap<String, Boolean>();
	private String sourceCode = " | code";
	/** Obligatorio lista. **/
	private Map<String, Boolean> required = new HashMap<String, Boolean>();

	/** Idioma que tiene que mostrar. **/
	private String idiomaInicial;

	/** Idiomas permitidos. **/
	private List<String> idiomasPermitidos;

	@Inject
	private UnidadAdministrativaServiceFacade uaService;

	@Inject
	private SessionBean sessionBean;

	Map<String, String> texto = new HashMap<String, String>();
	private Literal literal;

	public void load() {
		LOG.debug("init");
		// Inicializamos combos/desplegables/inputs
		// De momento, no tenemos desplegables.

		idiomasPermitidos = sessionBean.getIdiomasPermitidosList();

		literal = (Literal) UtilJSF.getValorMochilaByKey("literal"); // (Literal)
																		// sessionBean.getValorMochilaByKey("literal");
		if (literal == null) {
			literal = Literal.createInstance();
		}
		inicializarTextosPermisos();
		UtilJSF.vaciarMochila();// sessionBean.vaciarMochila();
		LOG.debug("Modo acceso " + this.getModoAcceso());

	}

	public void onload() {
		PrimeFaces.current().executeScript("mostrar('" + idiomaInicial + "')");
	}

	/**
	 * Inicializa los textos, la visiblidad y obligatoriedad.
	 */
	private void inicializarTextosPermisos() {
		List<String> idiomasPosibles = idiomasPermitidos;
		String idiomaCamel;
		for (final String idioma : idiomasPosibles) {
			if (idioma != null) {
				idiomaCamel = idioma.substring(0, 1).toUpperCase() + idioma.substring(1);
				texto.put(idiomaCamel, literal.getTraduccion(idioma));
				visible.put(idiomaCamel, true);
				// idiomasObligatorios.contains(idioma)
				if (true) {
					required.put(idiomaCamel, true);
				}
			}
		}
	}

	/** Lanza los errores por idioma. **/
	public void errorCa() {
	}

	public void errorEs() {
	}

	/**
	 * Borrar.
	 */
	public void borrar() {
		for (String idioma : sessionBean.getIdiomasObligatoriosList()) {
			literal.add(new Traduccion(idioma, ""));
		}

		// Retornamos resultado
		LOG.debug("Acceso:" + this.getModoAcceso());
		if (this.getModoAcceso() == null) {
			// TODO Pendiente
			this.setModoAcceso("ALTA");
		}
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
		result.setResult(literal);
		UtilJSF.closeDialog(result);

	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public void cerrar() {

		literal.add(new Traduccion("es", ""));
		literal.add(new Traduccion("ca", ""));

		// Retornamos resultado
		LOG.debug("Acceso:" + this.getModoAcceso());
		if (this.getModoAcceso() == null) {
			// TODO Pendiente
			this.setModoAcceso("ALTA");
		}
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
		result.setResult(literal);
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	private String replaceComillas(String texto) {
		texto = texto.replace("`", "'");
		texto = texto.replace("´", "'");
		texto = texto.replace("‘", "'");
		texto = texto.replace("’", "'");
		return texto;
	}

	/**
	 * Indica si el dialogo se abre en modo alta.
	 *
	 * @return boolean
	 */
	public boolean isAlta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(this.getModoAcceso());
		return (modo == TypeModoAcceso.ALTA);
	}

	/**
	 * Indica si el dialogo se abre en modo edicion.
	 *
	 * @return boolean
	 */
	public boolean isEdicion() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(this.getModoAcceso());
		return (modo == TypeModoAcceso.EDICION);
	}

	/**
	 * Indica si el dialogo se abre en modo consulta.
	 *
	 * @return boolean
	 */
	public boolean isConsulta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(this.getModoAcceso());
		return (modo == TypeModoAcceso.CONSULTA);
	}

	/**
	 * Comprueba si se excede la longitud.
	 *
	 * @param texto
	 * @return
	 */
	private boolean excedeLongitud(final String texto) {

		// 4000 es el tamaño máximo en tradidi
		return texto != null && texto.length() > 4000;
	}

	public void guardar() {
		List<Traduccion> trads = new ArrayList<Traduccion>();
		for (String idioma : idiomasPermitidos) {
			trads.add(new Traduccion(idioma,
					texto.get(idioma.substring(0, 1).toUpperCase().concat(idioma.substring(1))) == null ? ""
							: texto.get(idioma.substring(0, 1).toUpperCase().concat(idioma.substring(1))).toString()));
		}
		literal.setTraducciones(trads);
		// Retornamos resultado
		LOG.debug("Acceso:" + this.getModoAcceso());
		if (this.getModoAcceso() == null) {
			// TODO Pendiente
			this.setModoAcceso("ALTA");
		}
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
		result.setResult(literal);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Aceptar.
	 */
	/*
	 * public void aceptar() {
	 *
	 * if (visibleCa) { if (textoCA == null || textoCA.isEmpty()) { //error falta
	 * literal return; } if (excedeLongitud(textoCA)) { //error longitud return; }
	 * textoCA = replaceComillas(textoCA); literal.add(new Traduccion("ca",
	 * textoCA)); } if (visibleEs) { if (textoES == null || textoES.isEmpty()) {
	 * //error falta literal return; } if (excedeLongitud(textoES)) { //error
	 * longitud return; } textoES = replaceComillas(textoES); literal.add(new
	 * Traduccion("es", textoES)); }
	 *
	 *
	 * // Retornamos resultado final DialogResult result = new DialogResult();
	 * result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
	 * result.setResult(literal); UtilJSF.closeDialog(result);
	 *
	 * }
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UnidadAdministrativaServiceFacade getUaService() {
		return uaService;
	}

	public void setUaService(UnidadAdministrativaServiceFacade uaService) {
		this.uaService = uaService;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public Literal getLiteral() {
		return literal;
	}

	public void setLiteral(Literal literal) {
		this.literal = literal;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(final String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public final List<String> getIdiomasPermitidos() {
		return idiomasPermitidos;
	}

	/**
	 * @return the texto
	 */
	public final Map<String, String> getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public final void setTexto(Map<String, String> texto) {
		this.texto = texto;
	}

	public final void setIdiomasPermitidos(List<String> idiomasPermitidos) {
		this.idiomasPermitidos = idiomasPermitidos;
	}

	public String getIdiomaInicial() {
		return idiomaInicial;
	}

	public void setIdiomaInicial(String idiomaInicial) {
		this.idiomaInicial = idiomaInicial;
	}

	public boolean visible(String idioma) {
		return !idioma.isEmpty() ? this.visible.get(idioma.substring(0, 1).toUpperCase() + idioma.substring(1)) : false;
	}

}
