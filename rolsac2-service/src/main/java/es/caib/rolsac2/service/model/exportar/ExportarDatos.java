package es.caib.rolsac2.service.model.exportar;

import es.caib.rolsac2.service.model.types.TypeExportarFormato;

import java.util.ArrayList;
import java.util.List;

/**
 * Para cuando se exporta datos.
 */
public class ExportarDatos {

    /**
     * Campos
     **/
    private List<ExportarCampos> campos;

    /**
     * Tipos de exportación.
     * <ul>
     *   <li>NORMATIVA</li>
     *   <li>UA</li>
     *   <li>PROCEDIMIENTO</li>
     *   <li>SERVICIO</li>
     * </ul>
     */
    private String tipo;

    /**
     * Formato
     **/
    private TypeExportarFormato formato = TypeExportarFormato.CSV;

    /**
     * Todos los datos
     **/
    private boolean todosLosDatos = true;

    /**
     * Mostrar todos los estados o sólo uno.
     */
    private boolean estados = true;

    /**
     * Constructor
     **/
    public ExportarDatos(List<ExportarCampos> campos) {
        this.campos = campos;
    }

    /**
     * Constructor
     **/
    public ExportarDatos(ExportarDatos otro) {
        this.campos = otro.campos;
        this.campos = new ArrayList<>();
        for (ExportarCampos campo : otro.getCampos()) {
            campos.add(campo.clone());
        }
        this.formato = otro.formato;
        this.todosLosDatos = otro.todosLosDatos;
        this.estados = otro.estados;
    }


    /**
     * Constructor
     **/
    public ExportarDatos(List<ExportarCampos> campos, TypeExportarFormato formato, boolean todosLosDatos) {
        this.campos = campos;
        this.formato = formato;
        this.todosLosDatos = todosLosDatos;
    }

    /**
     * Obtiene campos.
     *
     * @return campos
     **/
    public List<ExportarCampos> getCampos() {
        return campos;
    }

    /**
     * Establece el campo campos.
     *
     * @param campos nuevo valor para el campo campos
     **/
    public void setCampos(List<ExportarCampos> campos) {
        this.campos = campos;
    }

    /**
     * Obtiene formato.
     *
     * @return formato
     **/
    public TypeExportarFormato getFormato() {
        return formato;
    }

    /**
     * Establece el campo formato.
     *
     * @param formato nuevo valor para el campo formato
     **/
    public void setFormato(TypeExportarFormato formato) {
        this.formato = formato;
    }

    /**
     * Obtiene todosLosDatos.
     *
     * @return todosLosDatos
     **/
    public boolean getTodosLosDatos() {
        return todosLosDatos;
    }

    /**
     * Establece el campo todosLosDatos.
     *
     * @param todosLosDatos nuevo valor para el campo todosLosDatos
     **/
    public void setTodosLosDatos(boolean todosLosDatos) {
        this.todosLosDatos = todosLosDatos;
    }

    /**
     * Obtiene tipo.
     *
     * @return tipo
     **/
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el campo tipo.
     *
     * @param tipo nuevo valor para el campo tipo
     **/
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene los pesos de los campos.
     *
     * @return
     */
    public float[] getPesos() {
        float[] pesos = new float[campos.size()];
        for (int i = 0; i < campos.size(); i++) {
            pesos[i] = campos.get(i).getPeso();
        }
        return pesos;
    }

    public float getFontSize() {
        float fontSize = 10;
        int peso = 0;
        for (ExportarCampos campo : campos) {
            peso += campo.getPeso();
        }
        if (peso >= 30) {
            fontSize = 2.5f;
        } else if (peso >= 20) {
            fontSize = 3.5f;
        } else if (peso >= 10) {
            fontSize = 4.5f;
        } else if (peso >= 5) {
            fontSize = 5.5f;
        } else if (peso >= 3) {
            fontSize = 6.5f;
        } else if (peso >= 2) {
            fontSize = 7.5f;
        } else if (peso >= 1) {
            fontSize = 8.5f;
        }
        return fontSize;
    }


    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public ExportarDatos clone() {
        return new ExportarDatos(this);
    }

    public boolean isEstados() {
        return estados;
    }

    public void setEstados(boolean estados) {
        this.estados = estados;
    }
}
