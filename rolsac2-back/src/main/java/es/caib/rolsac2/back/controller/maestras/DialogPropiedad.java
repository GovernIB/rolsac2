package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.utils.UtilJSON;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@ViewScoped
public class DialogPropiedad extends AbstractController implements Serializable {

    @Inject
    private SessionBean sessionBean;
    /**
     * Dato elemento en formato JSON.
     */
    private String iData;

    /**
     * Datos elemento.
     */
    private Propiedad data;

    /**
     * Datos elemento.
     */
    private Propiedad dataOriginal;

    /**
     * Parametro de entrada para ocultar 'valor'.
     */
    private String ocultarValor;

    /**
     * Muestra el valor.
     */
    private boolean mostrarValor = true;

    /**
     * Inicialización.
     */
    public void load() {
        if (this.isModoAlta()) {
            data = new Propiedad();
        } else {
            data = (Propiedad) UtilJSON.fromJSON(iData, Propiedad.class);
            dataOriginal = data.clone();
        }
        if (ocultarValor != null && "S".equals(ocultarValor)) {
            mostrarValor = false;
        }
    }

    /**
     * Aceptar.
     */
    public void aceptar() {
        // Retornamos resultado
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    /**
     * Cancelar.
     */
    //	public void cancelar() {
    //		if (comprobarModificacion()) {
    //			PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
    //		} else {
    //			cancelarDefinitivo();
    //		}
    //	}
    public void cancelar() {
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    //	private boolean comprobarModificacion() {
    //		return !data.getCodigo().equals(dataOriginal.getCodigo()) || !data.getValor().equals(dataOriginal.getValor())
    //				|| !data.getOrden().equals(dataOriginal.getOrden());
    //	}

    //	public void cancelarDefinitivo() {
    //		final DialogResult result = new DialogResult();
    //		result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
    //		result.setCanceled(true);
    //		UtilJSF.closeDialog(result);
    //	}

    /**
     * @return the iData
     */
    public String getiData() {
        return iData;
    }

    /**
     * @param iData the iData to set
     */
    public void setiData(final String iData) {
        this.iData = iData;
    }

    /**
     * @return the data
     */
    public Propiedad getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(final Propiedad data) {
        this.data = data;
    }

    /**
     * @return the mostrarValor
     */
    public boolean isMostrarValor() {
        return mostrarValor;
    }

    /**
     * @param mostrarValor the mostrarValor to set
     */
    public void setMostrarValor(final boolean mostrarValor) {
        this.mostrarValor = mostrarValor;
    }

    /**
     * @return the ocultarValor
     */
    public String getOcultarValor() {
        return ocultarValor;
    }

    /**
     * @param ocultarValor the ocultarValor to set
     */
    public void setOcultarValor(final String ocultarValor) {
        this.ocultarValor = ocultarValor;
    }

    public Propiedad getDataOriginal() {
        return dataOriginal;
    }

    public void setDataOriginal(Propiedad dataOriginal) {
        this.dataOriginal = dataOriginal;
    }

}
