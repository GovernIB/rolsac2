package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TasaServicioDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.primefaces.event.SelectEvent;

/**
 * Dialogo de tasa de servicio.
 */
@Named
@ViewScoped
public class DialogTasaServicio extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTasaServicio.class);

    private TasaServicioDTO data;

    public void load() {
        this.setearIdioma();

        if (this.isModoAlta()) {
            data = TasaServicioDTO.createInstance(sessionBean.getIdiomasPermitidosList());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            TasaServicioDTO tasaMod = (TasaServicioDTO) UtilJSF.getValorMochilaByKey("tasaServicio");
            data = tasaMod.clone();
            UtilJSF.vaciarMochila();
        }
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (data.getIdentificador() == null || data.getIdentificador().estaVacio()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.obligatorio.codigo"), true);
            return false;
        }
        if (!data.getIdentificador().estaCompleto(sessionBean.getIdiomasObligatoriosList())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.obligatorio.generico.literal", new Object[]{getLiteral("dialogTasaServicio.codigo")}), true);
            return false;
        }
        // Comprobar que si hay algún campo en un idioma, el código esté también en ese idioma y que no haya HTML en los campos
        List<String> idiomasPermitidos = sessionBean.getIdiomasPermitidosList();
        for (String idioma : idiomasPermitidos) {
            boolean tieneOtroCampo = tieneValor(data.getDescripcion(), idioma) || tieneValor(data.getFormaPago(), idioma) || tieneValor(data.getUrl(), idioma);
            if (tieneOtroCampo && !tieneValor(data.getIdentificador(), idioma)) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.obligatorio.codigo.idioma", new Object[]{idioma}), true);
                return false;
            }
            List<Literal> camposAValidar = java.util.Arrays.asList(data.getDescripcion(),data.getFormaPago(),data.getUrl());
            for (Literal campo : camposAValidar) {
                if (tieneValor(campo, idioma)) {
                    String texto = campo.getTraduccion(idioma);
                    if (!Jsoup.parse(texto).body().children().isEmpty() ) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.error.html.no.permitido", new Object[]{idioma}), true);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public TasaServicioDTO getData() {
        return data;
    }

    public void setData(TasaServicioDTO data) {
        this.data = data;
    }

    private boolean tieneValor(Literal literal, String idioma) {
        if (literal == null) {
            return false;
        }
        String valor = literal.getTraduccion(idioma);
        return valor != null && !valor.isEmpty();
    }

    public void traducir() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        if (event != null && event.getObject() instanceof TasaServicioDTO) {
            this.data = ((TasaServicioDTO) event.getObject());
        }
    }
}
