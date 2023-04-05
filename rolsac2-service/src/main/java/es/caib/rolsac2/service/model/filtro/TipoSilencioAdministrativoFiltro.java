package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de TipoSilencioAdministrativoFiltro.
 */
public class TipoSilencioAdministrativoFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewTipoNormativa
     **/
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }

    private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public boolean isRellenoCodigo() {
		 return codigo != null ;
	}
}
