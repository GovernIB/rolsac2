package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.plugins.traduccion.api.IPluginTraduccionException;
import es.caib.rolsac2.commons.plugins.traduccion.api.Idioma;
import es.caib.rolsac2.commons.plugins.traduccion.api.TipoEntrada;
import es.caib.rolsac2.service.facade.integracion.TraduccionServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Named
@ViewScoped
public class DialogTraduccion extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTraduccion.class);

    @EJB
    private TraduccionServiceFacade traduccionServiceFacade;

    private Object data;

    private Object dataCopy;

    private List<String> idiomas;

    private String idiomaDestino;

    private String idiomaOrigen;

    private List<Traduccion> traducciones;

    private List<Literal> literales;

    private Literal litTrad;

    private List<Literal> literalAux;
    private List<String> literalAuxOrigen;
    private List<String> literalAuxDestino;
    private List<String> literalAuxDestinoSust;
    private List<String> listaFields;

    private boolean sustitucion = true;

    public void load() throws IllegalAccessException {
        LOG.debug("load");
        this.setearIdioma();
        literales = new ArrayList<>();
        traducciones = new ArrayList<>();
        idiomas = new ArrayList<>();
        litTrad = Literal.createInstance();
        idiomaDestino = "es";
        idiomaOrigen = sessionBean.getLang();
        literalAux = new ArrayList<>();
        listaFields = new ArrayList<>();

        data = UtilJSF.getValorMochilaByKey("dataTraduccion");
        UtilJSF.vaciarMochila();
        dataCopy = data;

        if (data instanceof ProcedimientoDTO) {

            imprimirLiteralesProcedimiento();

        } else if (data instanceof ServicioDTO) {

            imprimirLiteralesServicio();

        } else {
            imprimirLiterales();
        }

        literalAuxOrigen = new ArrayList<>();
        literalAuxDestino = new ArrayList<>();
        if (literales != null) {
            for (Literal literal : literales) {
                literalAuxOrigen.add(literal.getTraduccion(idiomaOrigen));
                literalAuxDestino.add(literal.getTraduccion(idiomaDestino));
            }
            literalAuxDestinoSust = new ArrayList<>(literalAuxDestino);
        }
    }

    public void guardar() {

        if (literales != null) {
            Method[] metodos = data.getClass().getMethods();
            Field[] fields = data.getClass().getDeclaredFields();
            int cont = 0;

            for (Field field : fields) {
                for (Method m : metodos) {
                    if ((m.getName().startsWith("set"))
                            && (m.getParameterTypes()[0].getTypeName().equals(Literal.class.getTypeName()))) {
                        try {

                            if ((field.getName().toLowerCase(Locale.ROOT).equals(m.getName().toLowerCase(Locale.ROOT).replace("set", "")))) {
                                if (literales.size() > cont) {
                                    m.invoke(data, literales.get(cont));
                                    cont++;
                                }
                            }
                        } catch (IllegalAccessException | IllegalArgumentException
                                 | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.ALTA);
        result.setResult(data);

        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        data = dataCopy;
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.ALTA);
        result.setCanceled(true);

        UtilJSF.closeDialog(result);
    }

    public boolean isEmptyLiterales() {
        return literales.isEmpty();
    }

    public void imprimirLiteralesServicio() {
        literales.add((Literal) ((ProcedimientoDTO) data).getNombreProcedimientoWorkFlow().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDatosPersonalesFinalidad().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDatosPersonalesDestinatario().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getRequisitos().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getObjeto().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDestinatarios().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getTerminoResolucion().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getObservaciones().clone());
        listaFields.add("nombreProcedimientoWorkFlow");
        listaFields.add("datosPersonalesFinalidad");
        listaFields.add("datosPersonalesDestinatario");
        listaFields.add("requisitos");
        listaFields.add("objeto");
        listaFields.add("destinatarios");
        listaFields.add("terminoResolucion");
        listaFields.add("observaciones");

        literales.add((Literal) ((ProcedimientoDTO) data).getLopdFinalidad().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getLopdDestinatario().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getLopdDerechos().clone());
        //literales.add((Literal) ((ProcedimientoDTO) data).getLopdInfoAdicional().clone());
        listaFields.add("lopdFinalidad");
        listaFields.add("lopdDestinatario");
        listaFields.add("lopdDerechos");
        //listaFields.add("lopdInfoAdicional");
    }

    public void imprimirLiteralesProcedimiento() {
        literales.add((Literal) ((ProcedimientoDTO) data).getNombreProcedimientoWorkFlow().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDatosPersonalesFinalidad().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDatosPersonalesDestinatario().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getRequisitos().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getObjeto().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDestinatarios().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getTerminoResolucion().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getObservaciones().clone());
        listaFields.add("nombreProcedimientoWorkFlow");
        listaFields.add("datosPersonalesFinalidad");
        listaFields.add("datosPersonalesDestinatario");
        listaFields.add("requisitos");
        listaFields.add("objeto");
        listaFields.add("destinatarios");
        listaFields.add("terminoResolucion");
        listaFields.add("observaciones");

        literales.add((Literal) ((ProcedimientoDTO) data).getLopdFinalidad().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getLopdDestinatario().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getLopdDerechos().clone());
        //literales.add((Literal) ((ProcedimientoDTO) data).getLopdInfoAdicional().clone());
        listaFields.add("lopdFinalidad");
        listaFields.add("lopdDestinatario");
        listaFields.add("lopdDerechos");
        //listaFields.add("lopdInfoAdicional");
    }

    public void imprimirLiterales() {
        literales = new ArrayList<>();
        Method[] metodos = data.getClass().getMethods();
        Literal lit = new Literal();
        Field[] fields = data.getClass().getDeclaredFields();

        for (Field field : fields) {
            for (Method m : metodos) {
                if ((m.getName().startsWith("get")) &&
                        m.getGenericReturnType().getTypeName().equals(lit.getClass().getTypeName())) {
                    if ((field.getName().toLowerCase(Locale.ROOT).equals(m.getName().toLowerCase(Locale.ROOT).replace("get", "")))) {
                        try {
                            Literal k = (Literal) m.invoke(data, null);
                            literales.add((Literal) k.clone());
                            listaFields.add(field.getName());
                        } catch (IllegalAccessException | IllegalArgumentException
                                 | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void cambiarIdiomaOrigenAux() {
        literalAuxOrigen.clear();
        for (Literal literal : literales) {
            literalAuxOrigen.add(literal.getTraduccion(idiomaOrigen));
        }
    }

    public void cambiarIdiomaDestinoAux() {
        literalAuxDestino.clear();
        for (Literal literal : literales) {
            literalAuxDestino.add(literal.getTraduccion(idiomaDestino));
        }
    }

    public void cambioIdiomaOrigen(Literal literal, String idioma, Integer posicion) {
        literal.add(new Traduccion(idioma, this.literalAuxOrigen.get(posicion)));
    }

    public void cambioIdiomaDestino(Literal literal, String idioma, Integer posicion) {
        literal.add(new Traduccion(idioma, this.literalAuxDestino.get(posicion)));
    }

    public void cambioValores() {
        String aux = idiomaOrigen;
        idiomaOrigen = idiomaDestino;
        idiomaDestino = aux;
    }

    public Idioma comprobarIdioma(String idioma) {
        switch (idioma) {
            case "ca":
                return Idioma.CATALAN;
            case "es":
                return Idioma.CASTELLANO;
            case "fr":
                return Idioma.FRANCES;
            case "en":
                return Idioma.INGLES;
            case "de":
                return Idioma.ALEMAN;
            case "it":
                return Idioma.ITALIANO;
            default:
                return null;
        }
    }

    public void traducirLiterales() {
        if (idiomaDestino.equals(idiomaOrigen)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraduccion.mismoIdioma"), true);
        } else {
            literalAux = literales;
            Map<String, String> opciones = new HashMap<>();
            for (int i = 0; i < literales.size(); i++) {
                try {
                    String tradDestino = traduccionServiceFacade.traducir(TipoEntrada.TEXTO_PLANO.toString(),
                            literales.get(i).getTraduccion(idiomaOrigen), comprobarIdioma(idiomaOrigen), comprobarIdioma(idiomaDestino),
                            opciones, sessionBean.getEntidad().getCodigo());
                    if (isSustitucion()) {
                        literalAuxDestino.add(i, tradDestino);
                        this.cambioIdiomaDestino(literales.get(i), idiomaDestino, i);
                    } else {
                        literalAuxDestinoSust.add(i, tradDestino);
                    }
                } catch (IPluginTraduccionException e) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraduccion.errorComunicacion"));
                }


            }
        }
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Traduccion> getTraducciones() {
        return traducciones;
    }

    public void setTraducciones(List<Traduccion> traducciones) {
        this.traducciones = traducciones;
    }

    public List<Literal> getLiterales() {
        return literales;
    }

    public void setLiterales(List<Literal> literales) {
        this.literales = literales;
    }

    public Literal getLitTrad() {
        return litTrad;
    }

    public void setLitTrad(Literal litTrad) {
        this.litTrad = litTrad;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getIdiomaDestino() {
        return idiomaDestino;
    }

    public void setIdiomaDestino(String idiomaDestino) {
        this.idiomaDestino = idiomaDestino;
    }

    public String getIdiomaOrigen() {
        return idiomaOrigen;
    }

    public void setIdiomaOrigen(String idiomaOrigen) {
        this.idiomaOrigen = idiomaOrigen;
    }

    public List<Literal> getLiteralAux() {
        return literalAux;
    }

    public void setLiteralAux(List<Literal> literalAux) {
        this.literalAux = literalAux;
    }

    public List<String> getListaFields() {
        return listaFields;
    }

    public void setListaFields(List<String> listaFields) {
        this.listaFields = listaFields;
    }

    public List<String> getLiteralAuxOrigen() {
        return literalAuxOrigen;
    }

    public void setLiteralAuxOrigen(List<String> literalAuxOrigen) {
        this.literalAuxOrigen = literalAuxOrigen;
    }

    public List<String> getLiteralAuxDestino() {
        return literalAuxDestino;
    }

    public void setLiteralAuxDestino(List<String> literalAuxDestino) {
        this.literalAuxDestino = literalAuxDestino;
    }

    public boolean isSustitucion() {
        return sustitucion;
    }

    public void setSustitucion(boolean sustitucion) {
        this.sustitucion = sustitucion;
    }

    public List<String> getLiteralAuxDestinoSust() {
        return literalAuxDestinoSust;
    }

    public void setLiteralAuxDestinoSust(List<String> literalAuxDestinoSust) {
        this.literalAuxDestinoSust = literalAuxDestinoSust;
    }
}
