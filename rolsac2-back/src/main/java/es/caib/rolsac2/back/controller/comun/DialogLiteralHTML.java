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

	/** Visible Catalan. **/
	private boolean visibleCa = false;
	/** Visible Espanyol. **/
	private boolean visibleEs = false;
	/** Indica si se añade el plugin del código fuente. **/
	private String sourceCode =" | code";
	/** Obligatorio catalan. **/
	private boolean requiredCa = false;
	/** Obligatorio espanyol. **/
	private boolean requiredEs = false;

	/** Idioma que tiene que mostrar. **/
	private String idiomaInicial;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;

    @Inject
    private SessionBean sessionBean;

    private String textoCA;
    private String textoES;
    private Literal literal;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.

        literal = (Literal) UtilJSF.getValorMochilaByKey("literal"); //(Literal) sessionBean.getValorMochilaByKey("literal");
        if (literal == null) {
            literal = Literal.createInstance();
        }
		inicializarTextosPermisos();
        UtilJSF.vaciarMochila();//sessionBean.vaciarMochila();
        LOG.debug("Modo acceso " + this.getModoAcceso());


    }

    public void onload() {
    	PrimeFaces.current().executeScript("mostrar('"+idiomaInicial+"')");
    }
    /**
	 * Inicializa los textos, la visiblidad y obligatoriedad.
	 */
	private void inicializarTextosPermisos() {
		String[] idiomasPosibles = {"ca", "es"};
		for (final String idioma : idiomasPosibles) {

			if ( idioma != null) {
				switch (idioma) {
				case "ca":
					textoCA = literal.getTraduccion(idioma);
					visibleCa = true;
					//idiomasObligatorios.contains(idioma)
					if (true) {
						requiredCa = true;
					}
					break;
				case "es":
					textoES = literal.getTraduccion(idioma);
					visibleEs = true;
					if (true) {
						requiredEs = true;
					}
					break;
				default:
					break;
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

        literal.add(new Traduccion("es", ""));
        literal.add(new Traduccion("ca", ""));

        // Retornamos resultado
        LOG.debug("Acceso:" + this.getModoAcceso());
        if (this.getModoAcceso() == null) {
            //TODO Pendiente
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
             //TODO Pendiente
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

        literal.add(new Traduccion("es", textoES));
        literal.add(new Traduccion("ca", textoCA));

        // Retornamos resultado
        LOG.debug("Acceso:" + this.getModoAcceso());
        if (this.getModoAcceso() == null) {
            //TODO Pendiente
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
	/*public void aceptar() {

		if (visibleCa) {
			if (textoCA == null || textoCA.isEmpty()) {
				//error falta literal
				return;
			}
			if (excedeLongitud(textoCA)) {
				//error longitud
				return;
			}
			textoCA = replaceComillas(textoCA);
			literal.add(new Traduccion("ca", textoCA));
		}
		if (visibleEs) {
			if (textoES == null || textoES.isEmpty()) {
				//error falta literal
				return;
			}
			if (excedeLongitud(textoES)) {
				//error longitud
				return;
			}
			textoES = replaceComillas(textoES);
			literal.add(new Traduccion("es", textoES));
		}


		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(literal);
		UtilJSF.closeDialog(result);

	}*/


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

    public String gettextoCA() {
        return textoCA;
    }

    public void settextoCA(String textoCA) {
        this.textoCA = textoCA;
    }

    public String gettextoES() {
        return textoES;
    }

    public void settextoES(String textoES) {
        this.textoES = textoES;
    }

    public Literal getLiteral() {
        return literal;
    }

    public void setLiteral(Literal literal) {
        this.literal = literal;
    }

	public boolean isVisibleCa() {
		return visibleCa;
	}

	public void setVisibleCa(boolean visibleCa) {
		this.visibleCa = visibleCa;
	}

	public boolean isVisibleEs() {
		return visibleEs;
	}

	public void setVisibleEs(boolean visibleEs) {
		this.visibleEs = visibleEs;
	}

    public String getTextoCA() {
        return textoCA;
    }

    public void setTextoCA(String textoCA) {
        this.textoCA = textoCA;
    }

	public String getTextoES() {
		return textoES;
	}

	public void setTextoES(String textoES) {
		this.textoES = textoES;
	}

	public boolean isRequiredCa() {
		return requiredCa;
	}

	public void setRequiredCa(boolean requiredCa) {
		this.requiredCa = requiredCa;
	}

	public boolean isRequiredEs() {
		return requiredEs;
	}

	public void setRequiredEs(boolean requiredEs) {
		this.requiredEs = requiredEs;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(final String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getIdiomaInicial() {
		return idiomaInicial;
	}

	public void setIdiomaInicial(String idiomaInicial) {
		this.idiomaInicial = idiomaInicial;
	}

}
