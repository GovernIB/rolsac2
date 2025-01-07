package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.plugins.traduccion.api.IPluginTraduccionException;
import es.caib.rolsac2.commons.plugins.traduccion.api.Idioma;
import es.caib.rolsac2.commons.plugins.traduccion.api.TipoEntrada;
import es.caib.rolsac2.service.facade.integracion.TraduccionServiceFacade;
import es.caib.rolsac2.service.model.*;
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

    private String idiomaAuxOrigen;
    private String idiomaAuxDestino;

    private List<Traduccion> traducciones;

    private List<Literal> literales;

    private Literal litTrad;

    private List<Literal> literalAux;
    private List<String> literalAuxOrigen;
    private List<String> literalAuxDestino;
    private List<String> literalAuxDestinoSust;
    private List<String> listaFields;

    private List<Literal> literalesHTML;
    private List<String> literalHTMLAuxOrigen;
    private List<String> literalHTMLAuxDestino;
    private List<String> listaFieldsHTML;
    private List<String> literalHTMLAuxDestinoSust;


    private boolean sustitucion = true;

    public void load() throws IllegalAccessException {
        LOG.debug("load");
        this.setearIdioma();
        literales = new ArrayList<>();
        traducciones = new ArrayList<>();
        idiomas = new ArrayList<>();
        litTrad = Literal.createInstance();
        idiomaOrigen = sessionBean.getLang();
        idiomaAuxOrigen = idiomaOrigen;
        if (idiomaOrigen.equals("es")) {
            idiomaDestino = "ca";
            idiomaAuxDestino = idiomaDestino;
        } else {
            idiomaDestino = "es";
            idiomaAuxDestino = idiomaDestino;
        }
        literalAux = new ArrayList<>();
        listaFields = new ArrayList<>();
        literalesHTML = new ArrayList<>();
        listaFieldsHTML = new ArrayList<>();

        data = UtilJSF.getValorMochilaByKey("dataTraduccion");
        UtilJSF.vaciarMochila();
        dataCopy = data;

        if (data instanceof ProcedimientoDTO) {

            imprimirLiteralesProcedimiento();

        } else if (data instanceof ServicioDTO) {

            imprimirLiteralesServicio();

        } else if (data instanceof UnidadAdministrativaDTO) {

            imprimirLiteralesUA();

        } else if (data instanceof ProcedimientoTramiteDTO) {

            imprimirProcedimientoTramite();

        } else if (data instanceof NormativaDTO) {

            imprimirNormativa();

        } else {

            imprimirLiterales();

        }


        literalAuxOrigen = new ArrayList<>();
        literalAuxDestino = new ArrayList<>();
        literalHTMLAuxOrigen = new ArrayList<>();
        literalHTMLAuxDestino = new ArrayList<>();

        if (literales != null) {
            for (Literal literal : literales) {
                literalAuxOrigen.add(literal.getTraduccion(idiomaOrigen));
                literalAuxDestino.add(literal.getTraduccion(idiomaDestino));
            }
            literalAuxDestinoSust = new ArrayList<>(literalAuxDestino);
        }

        if (literalesHTML != null) {
            for (Literal literal : literalesHTML) {
                literalHTMLAuxOrigen.add(literal.getTraduccion(idiomaOrigen));
                literalHTMLAuxDestino.add(literal.getTraduccion(idiomaDestino));
            }
            literalHTMLAuxDestinoSust = new ArrayList<>(literalHTMLAuxDestino);
        }
    }

    public void guardar() {

        if (data instanceof UnidadAdministrativaDTO) {

            if (literales != null & literalesHTML != null) {
                ((UnidadAdministrativaDTO) data).setNombre(literales.get(0));
                //                ((UnidadAdministrativaDTO) data).setUrl(literales.get(1));
                ((UnidadAdministrativaDTO) data).setPresentacion(literalesHTML.get(0));
                ((UnidadAdministrativaDTO) data).setResponsable(literalesHTML.get(1));
            }

        } else if (data instanceof ProcedimientoDTO) {
            if (literales != null) {
                ((ProcedimientoDTO) data).setNombreProcedimientoWorkFlow(literales.get(0));
                ((ProcedimientoDTO) data).setObjeto(literales.get(1));
                ((ProcedimientoDTO) data).setDestinatarios(literales.get(2));
                ((ProcedimientoDTO) data).setTerminoResolucion(literales.get(3));
                ((ProcedimientoDTO) data).setObservaciones(literales.get(4));
            }
        } else if (data instanceof ServicioDTO) {
            if (literales != null) {
                ((ServicioDTO) data).setNombreProcedimientoWorkFlow(literales.get(0));
                ((ServicioDTO) data).setObjeto(literales.get(1));
                ((ServicioDTO) data).setDestinatarios(literales.get(2));
                ((ServicioDTO) data).setRequisitos(literales.get(3));
                ((ServicioDTO) data).setObservaciones(literales.get(4));
            }
        } else if (data instanceof ProcedimientoTramiteDTO) {
            if (literales != null) {
                ((ProcedimientoTramiteDTO) data).setNombre(literales.get(0));
                ((ProcedimientoTramiteDTO) data).setRequisitos(literales.get(1));
                ((ProcedimientoTramiteDTO) data).setDocumentacion(literales.get(2));
                ((ProcedimientoTramiteDTO) data).setTerminoMaximo(literales.get(3));
                ((ProcedimientoTramiteDTO) data).setObservacion(literales.get(4));
            }
        } else if (data instanceof NormativaDTO) {
            if (literales != null) {
                ((NormativaDTO) data).setTitulo(literales.get(0));
                ((NormativaDTO) data).setUrlBoletin(literales.get(1));
                ((NormativaDTO) data).setNombreResponsable(literales.get(2));
            }
        } else {

            if (literales != null) {
                Method[] metodos = data.getClass().getMethods();
                Field[] fields = data.getClass().getDeclaredFields();
                int cont = 0;

                for (Field field : fields) {
                    for (Method m : metodos) {
                        if ((m.getName().startsWith("set")) && (m.getParameterTypes()[0].getTypeName().equals(Literal.class.getTypeName()))) {
                            try {

                                if (field.getName().toLowerCase(Locale.ROOT).equals(m.getName().toLowerCase(Locale.ROOT).replace("set", "")) && !field.getName().toLowerCase(Locale.ROOT).contains("url")) {
                                    if (literales.size() > cont) {
                                        m.invoke(data, literales.get(cont));
                                        cont++;
                                    }
                                }
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
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

    public boolean containHTML() {
        return data instanceof UnidadAdministrativaDTO;
    }

    private void imprimirLiteralesUA() {
        literales.add((Literal) ((UnidadAdministrativaDTO) data).getNombre().clone());
        //        literales.add((Literal) ((UnidadAdministrativaDTO) data).getUrl().clone());
        listaFields.add("nombre");
        //        listaFields.add("url");

        literalesHTML.add((Literal) ((UnidadAdministrativaDTO) data).getPresentacion().clone());
        literalesHTML.add((Literal) ((UnidadAdministrativaDTO) data).getResponsable().clone());
        listaFieldsHTML.add("presentacion");
        listaFieldsHTML.add("responsable");
    }

    private void imprimirNormativa() {
        literales.add((Literal) ((NormativaDTO) data).getTitulo().clone());
        literales.add((Literal) ((NormativaDTO) data).getUrlBoletin().clone());
        literales.add((Literal) ((NormativaDTO) data).getNombreResponsable().clone());

        listaFields.add("tituloNormativa");
        listaFields.add("urlBoletin");
        listaFields.add("responsable");
    }

    private void imprimirProcedimientoTramite() {
        literales.add((Literal) ((ProcedimientoTramiteDTO) data).getNombre().clone());
        literales.add((Literal) ((ProcedimientoTramiteDTO) data).getRequisitos().clone());
        literales.add((Literal) ((ProcedimientoTramiteDTO) data).getDocumentacion().clone());
        literales.add((Literal) ((ProcedimientoTramiteDTO) data).getTerminoMaximo().clone());
        literales.add((Literal) ((ProcedimientoTramiteDTO) data).getObservacion().clone());

        listaFields.add("nombre");
        listaFields.add("requisitos");
        listaFields.add("documentacion");
        listaFields.add("terminoMaximo");
        listaFields.add("observacion");
    }

    public void imprimirLiteralesServicio() {
        literales.add((Literal) ((ServicioDTO) data).getNombreProcedimientoWorkFlow().clone());
        literales.add((Literal) ((ServicioDTO) data).getObjeto().clone());
        literales.add((Literal) ((ServicioDTO) data).getDestinatarios().clone());
        literales.add((Literal) ((ServicioDTO) data).getRequisitos().clone());
        literales.add((Literal) ((ServicioDTO) data).getObservaciones().clone());
        literales.add((Literal) ((ServicioDTO) data).getKeywords().clone());

        /*literales.add((Literal) ((ProcedimientoDTO) data).getDatosPersonalesFinalidad().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDatosPersonalesDestinatario().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getRequisitos().clone());*/

        listaFields.add("nombreProcedimientoWorkFlow");
        listaFields.add("objeto");
        listaFields.add("destinatarios");
        listaFields.add("requisitos");
        listaFields.add("observaciones");
        listaFields.add("keywords");
        //      listaFields.add("url");
        /*listaFields.add("datosPersonalesFinalidad");
        listaFields.add("datosPersonalesDestinatario");
        listaFields.add("requisitos");*/


    }

    public void imprimirLiteralesProcedimiento() {
        literales.add((Literal) ((ProcedimientoDTO) data).getNombreProcedimientoWorkFlow().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getObjeto().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getDestinatarios().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getTerminoResolucion().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getObservaciones().clone());
        literales.add((Literal) ((ProcedimientoDTO) data).getKeywords().clone());
        //literales.add((Literal) ((ProcedimientoDTO) data).getDatosPersonalesFinalidad().clone());
        //literales.add((Literal) ((ProcedimientoDTO) data).getDestinatarios().clone());
        //literales.add((Literal) ((ProcedimientoDTO) data).getRequisitos().clone());

        listaFields.add("nombreProcedimientoWorkFlow");
        listaFields.add("objeto");
        listaFields.add("destinatarios");
        listaFields.add("terminoResolucion");
        listaFields.add("observaciones");
        listaFields.add("keywords");
        //listaFields.add("datosPersonalesFinalidad");
        //listaFields.add("destinatarios");
        //listaFields.add("requisitos");


    }

    public void imprimirLiterales() {
        literales = new ArrayList<>();
        Method[] metodos = data.getClass().getMethods();
        Literal lit = new Literal();
        Field[] fields = data.getClass().getDeclaredFields();

        for (Field field : fields) {
            for (Method m : metodos) {
                if ((m.getName().startsWith("get")) && m.getGenericReturnType().getTypeName().equals(lit.getClass().getTypeName())) {
                    if ((field.getName().toLowerCase(Locale.ROOT).equals(m.getName().toLowerCase(Locale.ROOT).replace("get", "")))) {
                        try {
                            if (!field.getName().toLowerCase(Locale.ROOT).contains("url")) {
                                Literal k = (Literal) m.invoke(data, null);
                                literales.add((Literal) k.clone());
                                listaFields.add(field.getName());
                            }

                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void cambiarIdiomaOrigenAux() {
        if (!Objects.equals(idiomaOrigen, idiomaDestino)) {
            literalAuxOrigen.clear();
            for (Literal literal : literales) {
                literalAuxOrigen.add(literal.getTraduccion(idiomaOrigen));
            }
            if (data instanceof UnidadAdministrativaDTO) {
                literalHTMLAuxOrigen.clear();
                for (Literal literal : literalesHTML) {
                    literalHTMLAuxOrigen.add(literal.getTraduccion(idiomaOrigen));
                }
            }
            idiomaAuxOrigen = idiomaOrigen;
        } else {
            idiomaOrigen = idiomaAuxOrigen;
            cambioValores();
        }
    }

    public void cambiarIdiomaDestinoAux() {
        if (!Objects.equals(idiomaDestino, idiomaOrigen)) {
            literalAuxDestino.clear();
            for (Literal literal : literales) {
                literalAuxDestino.add(literal.getTraduccion(idiomaDestino));
            }
            if (data instanceof UnidadAdministrativaDTO) {
                literalHTMLAuxDestino.clear();
                for (Literal literal : literalesHTML) {
                    literalHTMLAuxDestino.add(literal.getTraduccion(idiomaDestino));
                }
            }
            idiomaAuxDestino = idiomaDestino;
        } else {
            idiomaDestino = idiomaAuxDestino;
            cambioValores();
        }

    }

    public void cambioValores() {
        String aux = idiomaOrigen;
        idiomaOrigen = idiomaDestino;
        idiomaDestino = aux;
        idiomaAuxOrigen = idiomaOrigen;
        idiomaAuxDestino = idiomaDestino;
        cambiarIdiomaOrigenAux();
        cambiarIdiomaDestinoAux();

    }

    public void cambioIdiomaOrigen(Literal literal, String idioma, Integer posicion) {
        literal.add(new Traduccion(idioma, this.literalAuxOrigen.get(posicion)));
    }

    public void cambioIdiomaOrigenHTML(Literal literal, String idioma, Integer posicion) {
        if (data instanceof UnidadAdministrativaDTO) {
            //            literal.add(new Traduccion(idioma, this.literalHTMLAuxOrigen.get(posicion)));
            literalesHTML.get(posicion).add(new Traduccion(idioma, this.literalHTMLAuxOrigen.get(posicion)));
        }
    }

    public void cambioIdiomaDestino(Literal literal, String idioma, Integer posicion) {
        literal.add(new Traduccion(idioma, this.literalAuxDestino.get(posicion)));
    }

    public void cambioIdiomaDestinoHTML(Literal literal, String idioma, Integer posicion) {
        if (data instanceof UnidadAdministrativaDTO) {
            //            literal.add(new Traduccion(idioma, this.literalHTMLAuxDestino.get(posicion)));
            literalesHTML.get(posicion).add(new Traduccion(idioma, this.literalHTMLAuxDestino.get(posicion)));
        }
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
            Map<String, String> opciones = new HashMap<>();
            if (data instanceof UnidadAdministrativaDTO) {
                for (int i = 0; i < literales.size(); i++) {
                    try {
                        String tradDestino = traduccionServiceFacade.traducir(TipoEntrada.TEXTO_PLANO.toString(), literales.get(i).getTraduccion(idiomaOrigen), comprobarIdioma(idiomaOrigen), comprobarIdioma(idiomaDestino), opciones, sessionBean.getEntidad().getCodigo());
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
                for (int i = 0; i < literalesHTML.size(); i++) {
                    try {
                        String tradDestino = traduccionServiceFacade.traducir(TipoEntrada.HTML.toString(), literalesHTML.get(i).getTraduccion(idiomaOrigen), comprobarIdioma(idiomaOrigen), comprobarIdioma(idiomaDestino), opciones, sessionBean.getEntidad().getCodigo());
                        if (isSustitucion()) {
                            literalHTMLAuxDestino.add(i, tradDestino);
                            this.cambioIdiomaDestinoHTML(literalesHTML.get(i), idiomaDestino, i);
                        } else {
                            literalHTMLAuxDestinoSust.add(i, tradDestino);
                        }
                    } catch (IPluginTraduccionException e) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraduccion.errorComunicacion"));
                    }


                }
            } else {
                for (int i = 0; i < literales.size(); i++) {
                    try {
                        String tradDestino = traduccionServiceFacade.traducir(TipoEntrada.TEXTO_PLANO.toString(), literales.get(i).getTraduccion(idiomaOrigen), comprobarIdioma(idiomaOrigen), comprobarIdioma(idiomaDestino), opciones, sessionBean.getEntidad().getCodigo());
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

    public List<Literal> getLiteralesHTML() {
        return literalesHTML;
    }

    public void setLiteralesHTML(List<Literal> literalesHTML) {
        this.literalesHTML = literalesHTML;
    }

    public List<String> getLiteralHTMLAuxOrigen() {
        return literalHTMLAuxOrigen;
    }

    public void setLiteralHTMLAuxOrigen(List<String> literalHTMLAuxOrigen) {
        this.literalHTMLAuxOrigen = literalHTMLAuxOrigen;
    }

    public List<String> getLiteralHTMLAuxDestino() {
        return literalHTMLAuxDestino;
    }

    public void setLiteralHTMLAuxDestino(List<String> literalHTMLAuxDestino) {
        this.literalHTMLAuxDestino = literalHTMLAuxDestino;
    }

    public List<String> getListaFieldsHTML() {
        return listaFieldsHTML;
    }

    public void setListaFieldsHTML(List<String> listaFieldsHTML) {
        this.listaFieldsHTML = listaFieldsHTML;
    }

    public List<String> getLiteralHTMLAuxDestinoSust() {
        return literalHTMLAuxDestinoSust;
    }

    public void setLiteralHTMLAuxDestinoSust(List<String> literalHTMLAuxDestinoSust) {
        this.literalHTMLAuxDestinoSust = literalHTMLAuxDestinoSust;
    }

    public String getIdiomaAuxOrigen() {
        return idiomaAuxOrigen;
    }

    public void setIdiomaAuxOrigen(String idiomaAuxOrigen) {
        this.idiomaAuxOrigen = idiomaAuxOrigen;
    }

    public String getIdiomaAuxDestino() {
        return idiomaAuxDestino;
    }

    public void setIdiomaAuxDestino(String idiomaAuxDestino) {
        this.idiomaAuxDestino = idiomaAuxDestino;
    }
}
