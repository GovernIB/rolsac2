package es.caib.rolsac2.back.controller.ayuda;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AyudaServiceFacade;
import es.caib.rolsac2.service.model.AyudaDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class DialogAyuda extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogAyuda.class);

    private static final String TIPO_BOLETIN_PROPIEDAD = "tipoBoletin";

    private String id;

    private AyudaDTO data;


    @EJB
    AyudaServiceFacade ayudaServiceFacade;


    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = ayudaServiceFacade.findByIdentificador(id, "TODOS");

        if (data == null) {
            //No ha sido creado aun la ayuda
            data = new AyudaDTO();
            data.setIdentificador(id);
            data.setDescripcion(getLiteralVacio());
            data.setPerfil("TODOS");
            data.setFechaCreacion(new Date());
        }
    }

    private Literal getLiteralVacio() {
        Literal html = new Literal();
        List<Traduccion> traducciones = new ArrayList<>();
        for (String idioma : sessionBean.getIdiomasPermitidosList()) {
            traducciones.add(new Traduccion(idioma, "<html><body><div></div></body></html>"));
        }
        html.setTraducciones(traducciones);

        return html;
    }

    public void editar() {

        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.EDICION.toString());
        params.put("idioma", sessionBean.getLang());
        params.put("deshabilitarBorrar", "true");
        String direccion = "/comun/dialogLiteralHTML";
        UtilJSF.anyadirMochila("literal", data.getDescripcion());
        UtilJSF.openDialog(direccion, TypeModoAcceso.EDICION, params, true, 1050, 750);

    }

    /**
     * Retorno dialogo de los botones de propiedades.
     *
     * @param event respuesta dialogo
     */
    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        if (!respuesta.isCanceled()) {
            data.setDescripcion((Literal) respuesta.getResult());
            if (data.getCodigo() == null) {
                ayudaServiceFacade.create(data);
            } else {
                ayudaServiceFacade.update(data);
            }
        }
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AyudaDTO getData() {
        return data;
    }

    public void setData(AyudaDTO data) {
        this.data = data;
    }

}
