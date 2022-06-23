package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TipoNormativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Controlador para editar un  DialogTipoNormativa.
 *
 * @author jsegovia
 */
@Named
@ViewScoped
public class DialogTipoNormativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoNormativa.class);


    private String id;

    private TipoNormativaDTO data;

    @EJB
    TipoNormativaServiceFacade tipoNormativaServiceFacade;

    private String identificadorOld;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.
        this.setearIdioma();
        data = new TipoNormativaDTO();
        if (this.isModoAlta()) {
            data = new TipoNormativaDTO();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoNormativaServiceFacade.findById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
        }
        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance());
        }

    }

    public void initMockup() {
        data = new TipoNormativaDTO();
        data.setId(1l);
        data.setIdentificador("p1");
        Literal lit = new Literal();
        lit.setCodigo(1L);
        Traduccion tra = new Traduccion();
        lit.setTraducciones(Arrays.asList(tra));
        data.setDescripcion(lit);
    }

    public void abrirDlg() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("dialogPersonal", TypeModoAcceso.ALTA, params, true, 1050, 550);
    }

    public void testMsg() {
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Test MSG desde dialog", "INFO");// UtilJSF.getLiteral("info.borrado.ok"));
    }

    public void guardar() {

        if((Objects.nonNull(this.data.getId()) && !this.identificadorOld.equals(this.data.getIdentificador())) || Objects.isNull(this.data.getId())
                && Boolean.TRUE.equals(tipoNormativaServiceFacade.checkIdentificador(this.data.getIdentificador()))) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Ya existe el identificador", true);
            return;
        }

        if (this.data.getId() == null) {
            tipoNormativaServiceFacade.create(this.data, sessionBean.getUnidadActiva().getId());
        } else {
            tipoNormativaServiceFacade.update(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoNormativaDTO getData() {
        return data;
    }

    public void setData(TipoNormativaDTO data) {
        this.data = data;
    }
}
