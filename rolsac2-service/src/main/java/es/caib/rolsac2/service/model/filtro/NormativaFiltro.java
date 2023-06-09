package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class NormativaFiltro extends AbstractFiltro {

    private String texto;

    private TipoNormativaDTO tipoNormativa;

    private TipoBoletinDTO tipoBoletin;

    private LocalDate fechaAprobacion;

	private LocalDate fechaBoletin;

    private String numero;

    private boolean hijasActivas = false;

    private List<Long> idUAsHijas;

    private List<Long> idsUAsHijasAux;

    private boolean todasUnidadesOrganicas = false;

	private Integer codigoProcedimiento;

	private Integer codigoServicio;

	private Long codigo;

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

    public TipoNormativaDTO getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(TipoNormativaDTO tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public TipoBoletinDTO getTipoBoletin() {
        return tipoBoletin;
    }

    public void setTipoBoletin(TipoBoletinDTO tipoBoletin) {
        this.tipoBoletin = tipoBoletin;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    public boolean isHijasActivas() {
        return hijasActivas;
    }

    public void setHijasActivas(boolean hijasActivas) {
        this.hijasActivas = hijasActivas;
    }

    public List<Long> getIdUAsHijas() {
        return idUAsHijas;
    }

    public void setIdUAsHijas(List<Long> idUAsHijas) {
        this.idUAsHijas = idUAsHijas;
    }

    public List<Long> getIdsUAsHijasAux() {
        return idsUAsHijasAux;
    }

    public void setIdsUAsHijasAux(List<Long> idsUAsHijasAux) {
        this.idsUAsHijasAux = idsUAsHijasAux;
    }

    public boolean isTodasUnidadesOrganicas() {
        return todasUnidadesOrganicas;
    }

    public void setTodasUnidadesOrganicas(boolean todasUnidadesOrganicas) {
        this.todasUnidadesOrganicas = todasUnidadesOrganicas;
    }

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoFechaAprobacion() { return fechaAprobacion != null; }

    public boolean isRellenoCodigo() { return codigo != null; }

    public boolean isRellenoFechaBoletin() { return fechaBoletin != null; }

    public boolean isRellenoTipoNormativa() {
        return tipoNormativa != null && tipoNormativa.getCodigo() != null;
    }

	public boolean isRellenoTipoBoletin() {
		return tipoBoletin != null && tipoBoletin.getCodigo() != null;
	}

	public void setFechaAprobacion(LocalDate fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

    public boolean isRellenoHijasActivas() {
        return hijasActivas;
    }

    public boolean isRellenoUasAux() {return idsUAsHijasAux != null; }

    public boolean isRellenoTodasUnidadesOrganicas() { return this.todasUnidadesOrganicas;
    }

    public boolean isRellenoNumero() { return numero != null && !numero.isEmpty(); }

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getCodigoProcedimiento() {
		return codigoProcedimiento;
	}

	public void setCodigoProcedimiento(Integer codigoProcedimiento) {
		this.codigoProcedimiento = codigoProcedimiento;
	}

	public Integer getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(Integer codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
