package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.NormativaDTO;

public class DocumentoNormativaFiltro extends AbstractFiltro {

	 /* Código del documento*/
    private Long codigo;

    /*Normativa asociada al documento*/
    private NormativaDTO normativa;

    /*Filtro para titulo, url y descripcion*/
    private String texto;

    /*Fichero asociado al documento*/
    private FicheroDTO documento;

    @Override
    protected String getDefaultOrder() {
        return "id";
    }

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

    public boolean isRellenoNormativa() {
        return normativa != null;
    }

    public boolean isRellenoCodigo() {
        return codigo != null;
    }

    public boolean isRellenoDocumento() {
        return documento != null;
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public NormativaDTO getNormativa() {
		return normativa;
	}

	public void setNormativa(NormativaDTO normativa) {
		this.normativa = normativa;
	}

	public FicheroDTO getDocumento() {
		return documento;
	}

	public void setDocumento(FicheroDTO documento) {
		this.documento = documento;
	}
}
