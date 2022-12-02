package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Named
@ViewScoped
public class DialogTraduccion extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTraduccion.class);

    private Object data;

    private List<String> idiomas;

    private String idiomaSeleccionado;

    private List<Traduccion> traducciones;

    private List<Literal> literales;

    private List<String> trad1;

    private List<String> trad2;

    private Literal litTrad;

    public void load() throws IllegalAccessException {
        LOG.debug("load");
        this.setearIdioma();
        literales = new ArrayList<>();
        trad1 = new ArrayList<>();
        trad2 = new ArrayList<>();
        traducciones = new ArrayList<>();
        idiomas  = new ArrayList<>();
        litTrad = Literal.createInstance();
        idiomaSeleccionado = "es";

        data = UtilJSF.getValorMochilaByKey("dataTraduccion");


        imprimirLiterales();
    }

    public void guardar() {
        traducirLiterales();
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.ALTA);
        result.setResult(data);

        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.ALTA);
        result.setCanceled(true);

        UtilJSF.closeDialog(result);
    }

    public boolean isEmptyLiterales() {
        return literales.isEmpty();
    }

    public void imprimirLiterales() {

        Method[] metodos=data.getClass().getMethods();
        Literal lit = new Literal();
        Field[] fields = data.getClass().getDeclaredFields();

        for (Field field : fields) {
            for (Method m: metodos) {
                if ((m.getName().startsWith("get")) &&
                        m.getGenericReturnType().getTypeName().equals(lit.getClass().getTypeName())) {
                    if ((field.getName().toLowerCase(Locale.ROOT).equals(m.getName().toLowerCase(Locale.ROOT).replace("get", "")))) {
                        try{
                            Literal k = (Literal) m.invoke(data, null);
                            literales.add(k);
                        } catch (IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void traducirLiterales() {

        for (String li : trad1) {
            traducciones.add(new Traduccion(sessionBean.getLang(), li));
            traducciones.add(new Traduccion(idiomaSeleccionado, trad2.get(trad1.indexOf(li))));
            for (Traduccion trad : traducciones) {
                litTrad.add(trad);
            }
            literales.add(litTrad);
            litTrad = new Literal();
            traducciones = new ArrayList<>();
        }

        Method[] metodos=data.getClass().getMethods();
        Field[] fields = data.getClass().getDeclaredFields();
        int cont = 0;

        for (Field field : fields) {
            for (Method m: metodos) {
                if ((m.getName().startsWith("set"))
                        && (m.getParameterTypes()[0].getTypeName().equals(Literal.class.getTypeName()))) {
                    try {

                        if ((field.getName().toLowerCase(Locale.ROOT).equals(m.getName().toLowerCase(Locale.ROOT).replace("set", "")))) {
                            m.invoke(data, literales.get(cont));
                            cont++;
                        }

                    } catch (IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException e) {
                        e.printStackTrace();
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

    public List<String> getTrad1() {
        return trad1;
    }

    public void setTrad1(List<String> trad1) {
        this.trad1 = trad1;
    }

    public List<String> getTrad2() {
        return trad2;
    }

    public void setTrad2(List<String> trad2) {
        this.trad2 = trad2;
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

    public String getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(String idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }


}
