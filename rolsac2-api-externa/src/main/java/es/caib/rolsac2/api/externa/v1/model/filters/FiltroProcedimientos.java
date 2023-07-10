package es.caib.rolsac2.api.externa.v1.model.filters;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.TipoProcedimientoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.TipoViaDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

/**
 * FiltroProcedimientos.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroProcedimientos", description = "Filtro que permite buscar por diferentes campos")
public class FiltroProcedimientos extends EntidadJson<FiltroProcedimientos> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroProcedimientos.class);

	public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION = "fechaPublicacion";
	public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_ACTUALIZACION = "fechaActualizacion";
	public static final String CAMPO_ORD_PROCEDIMIENTO_CODIGO = "codigo";

	public static final String SAMPLE = Constantes.SALTO_LINEA + "{"
			+ "\"codigoUA\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoUADir3\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoPublicoObjetivo\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"textos\":\"string\", (Compara con codigo, nombre, estado, tipo, codigoSia, estadoSia y codigoDir3Sia)" + Constantes.SALTO_LINEA
			+ "\"codigoFormaInicio\":\"codigo\"," + Constantes.SALTO_LINEA
			+ "\"titulo\":\"string\"," + Constantes.SALTO_LINEA
			+ "\"codigoTipoProcedimiento\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoSilencioAdministrativo\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoFinVia\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigo\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"volcadoSia\":\"S/N\", (S=Si, N=No)" + Constantes.SALTO_LINEA
			+ "\"estadoWF\":\"D/M/T/A\", (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))" + Constantes.SALTO_LINEA
			+ "\"comun\":\"0/1\", (1= procedimientos comunes)" + Constantes.SALTO_LINEA
			+ "\"codigoSia\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoTram\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoPlantilla\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoPlataforma\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"tramiteVigente\":\"S/N\", (S=Si, N=No)" + Constantes.SALTO_LINEA
			+ "\"tramiteTelematico\":\"string\"," + Constantes.SALTO_LINEA
			+ "\"estado\":\"S/M/P/T/R/U/B\", (S=Pendiente Publicar, M=En modificación, P=Publicado, T=Pendiente reservar, R=Reservado, U=Pendiente borrar, B=Borrado)" + Constantes.SALTO_LINEA
			+ "\"estadoSia\":\"A/B\", (A=Alta, B=Baja)" + Constantes.SALTO_LINEA
			+ "\"fechaActualizacionSia\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA
			+ "\"listaCodigosNormativas\":[0]," + Constantes.SALTO_LINEA
			+ "\"listaCodigosPublicosObjetivos\":[0]," + Constantes.SALTO_LINEA
			+ "\"listaCodigosMaterias\":[0]," + Constantes.SALTO_LINEA
			+ "\"fechaPublicacionDesde\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA
			+ "\"fechaPublicacionHasta\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA
			+ "\"idEntidad\":0," + Constantes.SALTO_LINEA
			+ "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," + Constantes.SALTO_LINEA
			+ "\"orden\":{\"campo\":\""
			+ CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION + "\",\"tipoOrden\":\"ASC/DESC\"}"
			+ "}";

	public static final String SAMPLE_JSON = "{"
			+ "\"codigoUA\":null,"
			+ "\"codigoUADir3\":null,"
			+ "\"codigoPublicoObjetivo\":null,"
			+ "\"textos\":null,"
			+ "\"codigoFormaInicio\":null,"
			+ "\"titulo\":null,"
			+ "\"codigoTipoProcedimiento\":null,"
			+ "\"codigoSilencioAdministrativo\":null,"
			+ "\"codigoFinVia\":null,"
			+ "\"codigo\":null,"
			+ "\"volcadoSia\":null,"
			+ "\"estadoWF\":null,"
			+ "\"comun\":null,"
			+ "\"codigoSia\":null,"
			+ "\"codigoTram\":null,"
			+ "\"codigoPlantilla\":null,"
			+ "\"codigoPlataforma\":null,"
			+ "\"tramiteVigente\":null,"
			+ "\"tramiteTelematico\":null,"
			+ "\"estado\":null,"
			+ "\"estadoSia\":null,"
			+ "\"fechaActualizacionSia\":null,"
			+ "\"listaCodigosNormativas\":null,"
			+ "\"listaCodigosPublicosObjetivos\":null,"
			+ "\"listaCodigosMaterias\":null,"
			+ "\"fechaPublicacionDesde\":null,"
			+ "\"fechaPublicacionHasta\":null,"
			+ "\"idEntidad\":null,"
			+ "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"},"
			+ "\"orden\":null"
			+ "}";

	/** FiltroPaginacion. **/
	@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
	private FiltroPaginacion filtroPaginacion;

    /**
     * Entidad
     */
	@Schema(name = "idEntidad", description = "idEntidad", type = SchemaType.INTEGER, required = false)
    private Long idEntidad;

	/** Lista de campos a ordenar. **/
	@Schema(description = "Campo por el que se tiene que ordenar", required = false)
	private CampoOrden orden;

	/** listaCodigosNormativas. **/
	@Schema(description = "listaCodigosNormativas", required = false)
	private List<Long> listaCodigosNormativas;

	/** listaCodigosPublicosObjetivos. **/
	@Schema(description = "listaCodigosPublicosObjetivos", required = false)
	private List<Long> listaCodigosPublicosObjetivos;

	/** listaCodigosMaterias. **/
	@Schema(description = "listaCodigosMaterias", required = false)
	private List<Long> listaCodigosMaterias;

	/** codigoUA. **/
	@Schema(description = "codigoUA", type = SchemaType.INTEGER, required = false)
	private Long codigoUA;

	/** codigoPlantilla. **/
	@Schema(description = "codigoPlantilla", type = SchemaType.INTEGER, required = false)
	private Long codigoPlantilla;

	/** codigoPlataforma. **/
	@Schema(description = "codigoPlataforma", type = SchemaType.INTEGER, required = false)
	private Long codigoPlataforma;

	/** codigo. **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	/** codigoTram. **/
	@Schema(description = "codigoTram", type = SchemaType.INTEGER, required = false)
	private Long codigoTram;

	/** codigoUADir3. **/
	@Schema(description = "codigoUADir3", type = SchemaType.STRING, required = false)
	private String codigoUADir3;

	/** estadoWF. **/
	@Schema(description = "estadoWF", type = SchemaType.STRING, required = false)
	private String estadoWF;

	/** tramiteTelematico. **/
	@Schema(description = "tramiteTelematico", type = SchemaType.STRING, required = false)
	private String tramiteTelematico;

	/** volcadoSia. **/
	@Schema(description = "volcadoSia", type = SchemaType.STRING, required = false)
	private String volcadoSia;

	/** tramiteVigente. **/
	@Schema(description = "tramiteVigente", type = SchemaType.STRING, required = false)
	private String tramiteVigente;

	/** estado. **/
	@Schema(description = "estado", type = SchemaType.STRING, required = false)
	private String estado;

	/** codigoPublicoObjetivo. **/
	@Schema(description = "codigoPublicoObjetivo", type = SchemaType.INTEGER, required = false)
	private Long codigoPublicoObjetivo;

	/** codigoTipoProcedimiento. **/
	@Schema(description = "codigoTipoProcedimiento", type = SchemaType.INTEGER, required = false)
	private Long codigoTipoProcedimiento;

	/** textos. **/
	@Schema(description = "textos", type = SchemaType.STRING, required = false)
	private String textos;

	/** codigoFormaInicio. **/
	@Schema(description = "codigoFormaInicio", type = SchemaType.INTEGER, required = false)
	private Long codigoFormaInicio;

	/** codigoSilencioAdministrativo. **/
	@Schema(description = "codigoSilencioAdministrativo", type = SchemaType.INTEGER, required = false)
	private Long codigoSilencioAdministrativo;

	/** codigoFinVia. **/
	@Schema(description = "codigoFinVia", type = SchemaType.INTEGER, required = false)
	private Long codigoFinVia;

	/** textos. **/
	@Schema(description = "titulo", type = SchemaType.STRING, required = false)
	private String titulo;

	/** fechaPublicacionDesde. **/
	@Schema(description = "fechaPublicacionDesde", type = SchemaType.STRING, required = false)
	private String fechaPublicacionDesde;

	/** fechaPublicacionHasta. **/
	@Schema(description = "fechaPublicacionHasta", type = SchemaType.STRING, required = false)
	private String fechaPublicacionHasta;

	/** comun. **/
	@Schema(description = "comun", type = SchemaType.INTEGER, required = false)
	private Integer comun;

	/** codigoSia. **/
	@Schema(description = "codigoSia", type = SchemaType.INTEGER, required = false)
	private Integer codigoSia;

	/** estadoSia. **/
	@Schema(description = "estadoSia", type = SchemaType.STRING, required = false)
	private String estadoSia;

	/** fechaActualizacionSia. **/
	@Schema(description = "fechaActualizacionSia", type = SchemaType.STRING, required = false)
	private String fechaActualizacionSia;

	/**
	 * @return the textos
	 */
	public String getTextos() {
		return textos;
	}

	public List<Long> getListaCodigosNormativas() {
		return listaCodigosNormativas;
	}

	public void setListaCodigosNormativas(List<Long> listaCodigosNormativas) {
		this.listaCodigosNormativas = listaCodigosNormativas;
	}

	public Long getCodigoUA() {
		return codigoUA;
	}

	public void setCodigoUA(Long codigoUA) {
		this.codigoUA = codigoUA;
	}

	public Long getCodigoPlantilla() {
		return codigoPlantilla;
	}

	public void setCodigoPlantilla(Long codigoPlantilla) {
		this.codigoPlantilla = codigoPlantilla;
	}

	public Long getCodigoPlataforma() {
		return codigoPlataforma;
	}

	public void setCodigoPlataforma(Long codigoPlataforma) {
		this.codigoPlataforma = codigoPlataforma;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoTram() {
		return codigoTram;
	}

	public void setCodigoTram(Long codigoTram) {
		this.codigoTram = codigoTram;
	}

	public String getTramiteTelematico() {
		return tramiteTelematico;
	}

	public void setTramiteTelematico(String tramiteTelematico) {
		this.tramiteTelematico = tramiteTelematico;
	}

	public String getVolcadoSia() {
		return volcadoSia;
	}

	public void setVolcadoSia(String volcadoSia) {
		this.volcadoSia = volcadoSia;
	}

	public String getTramiteVigente() {
		return tramiteVigente;
	}

	public void setTramiteVigente(String tramiteVigente) {
		this.tramiteVigente = tramiteVigente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getCodigoPublicoObjetivo() {
		return codigoPublicoObjetivo;
	}

	public void setCodigoPublicoObjetivo(Long codigoPublicoObjetivo) {
		this.codigoPublicoObjetivo = codigoPublicoObjetivo;
	}

	public Long getCodigoTipoProcedimiento() {
		return codigoTipoProcedimiento;
	}

	public void setCodigoTipoProcedimiento(Long codigoTipoProcedimiento) {
		this.codigoTipoProcedimiento = codigoTipoProcedimiento;
	}

	public Long getCodigoFormaInicio() {
		return codigoFormaInicio;
	}

	public void setCodigoFormaInicio(Long codigoFormaInicio) {
		this.codigoFormaInicio = codigoFormaInicio;
	}

	public Long getCodigoSilencioAdministrativo() {
		return codigoSilencioAdministrativo;
	}

	public void setCodigoSilencioAdministrativo(Long codigoSilencioAdministrativo) {
		this.codigoSilencioAdministrativo = codigoSilencioAdministrativo;
	}

	public Long getCodigoFinVia() {
		return codigoFinVia;
	}

	public void setCodigoFinVia(Long codigoFinVia) {
		this.codigoFinVia = codigoFinVia;
	}

	/**
	 * @param textos the textos to set
	 */
	public void setTextos(final String textos) {
		this.textos = textos;
	}

	/**
	 * @return the codigoSia
	 */
	public Integer getCodigoSia() {
		return codigoSia;
	}

	/**
	 * @param codigoSia the codigoSia to set
	 */
	public void setCodigoSia(final Integer codigoSia) {
		this.codigoSia = codigoSia;
	}

	/**
	 * @return the fechaActualizacionSia
	 */
	public String getFechaActualizacionSia() {
		return fechaActualizacionSia;
	}

	/**
	 * @param fechaActualizacionSia the fechaActualizacionSia to set
	 */
	public void setFechaActualizacionSia(final String fechaActualizacionSia) {
		this.fechaActualizacionSia = fechaActualizacionSia;
	}

	/**
	 * @return the estadoSia
	 */
	public String getEstadoSia() {
		return estadoSia;
	}

	/**
	 * @param estadoSia the estadoSia to set
	 */
	public void setEstadoSia(final String estadoSia) {
		this.estadoSia = estadoSia;
	}

	public String getFechaPublicacionDesde() {
		return fechaPublicacionDesde;
	}

	public void setFechaPublicacionDesde(String fechaPublicacionDesde) {
		this.fechaPublicacionDesde = fechaPublicacionDesde;
	}

	public String getFechaPublicacionHasta() {
		return fechaPublicacionHasta;
	}

	public void setFechaPublicacionHasta(String fechaPublicacionHasta) {
		this.fechaPublicacionHasta = fechaPublicacionHasta;
	}

	public List<Long> getListaCodigosPublicosObjetivos() {
		return listaCodigosPublicosObjetivos;
	}

	public void setListaCodigosPublicosObjetivos(List<Long> listaCodigosPublicosObjetivos) {
		this.listaCodigosPublicosObjetivos = listaCodigosPublicosObjetivos;
	}

	public List<Long> getListaCodigosMaterias() {
		return listaCodigosMaterias;
	}

	public void setListaCodigosMaterias(List<Long> listaCodigosMaterias) {
		this.listaCodigosMaterias = listaCodigosMaterias;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the codigoUADir3
	 */
	public String getCodigoUADir3() {
		return codigoUADir3;
	}

	/**
	 * @param codigoUADir3 the codigoUADir3 to set
	 */
	public void setCodigoUADir3(final String codigoUADir3) {
		this.codigoUADir3 = codigoUADir3;
	}

	/**
	 * @return the comun
	 */
	public Integer getComun() {
		return comun;
	}

	/**
	 * @param comun the comun to set
	 */
	public void setComun(final Integer comun) {
		this.comun = comun;
	}

	public String getEstadoWF() {
		return estadoWF;
	}

	public void setEstadoWF(String estadoWF) {
		this.estadoWF = estadoWF;
	}

	public ProcedimientoFiltro toProcedimientoFiltro() {
		ProcedimientoFiltro resultado = new ProcedimientoFiltro();

		if (this.codigoUA != null) {
			resultado.setIdUA(codigoUA);
		}

		if (this.codigoUADir3 != null) {
			resultado.setCodigoUaDir3(codigoUADir3);
		}

		if (this.codigo != null) {
			resultado.setCodigoProc(codigo);
		}

		if (this.codigoTram != null) {
			resultado.setCodigoTram(codigoTram);
		}

		if (this.estado != null) {
			resultado.setEstado(estado);
		}

		if (this.textos != null) {
			resultado.setTexto(textos);
		}

		if (this.titulo != null) {
			resultado.setTexto(titulo);
		}

		if (this.codigoFormaInicio != null) {
			TipoFormaInicioDTO fi = new TipoFormaInicioDTO();
			fi.setCodigo(codigoFormaInicio);
			resultado.setFormaInicio(fi);
		}

		if (this.codigoPublicoObjetivo != null) {
			TipoPublicoObjetivoDTO po = new TipoPublicoObjetivoDTO();
			po.setCodigo(codigoFormaInicio);
			resultado.setPublicoObjetivo(po);
		}

		if (this.codigoTipoProcedimiento != null) {
			TipoProcedimientoDTO tp = new TipoProcedimientoDTO();
			tp.setCodigo(codigoTipoProcedimiento);
			resultado.setTipoProcedimiento(tp);
		}

		if (this.codigoSilencioAdministrativo != null) {
			TipoSilencioAdministrativoDTO sa = new TipoSilencioAdministrativoDTO();
			sa.setCodigo(codigoSilencioAdministrativo);
			resultado.setSilencioAdministrativo(sa);
		}

		if (this.codigoFinVia != null) {
			TipoViaDTO vi = new TipoViaDTO();
			vi.setCodigo(codigoFinVia);
			resultado.setFinVia(vi);
		}

		if (this.codigoPlataforma != null) {
			PlatTramitElectronicaDTO pl = new PlatTramitElectronicaDTO();
			pl.setCodigo(codigoPlataforma);
			resultado.setPlataforma(pl);
		}

		if (this.titulo != null) {
			resultado.setTexto(titulo);
		}

		if (this.idEntidad != null) {
			resultado.setIdEntidad(idEntidad);
		}

		if (this.listaCodigosNormativas != null) {
			List<NormativaGridDTO> lista= new ArrayList<NormativaGridDTO>();
			for(Long cod : listaCodigosNormativas) {
				NormativaGridDTO norm = new NormativaGridDTO();
				norm.setCodigo(cod);
				lista.add(norm);
			}

			resultado.setNormativas(lista);
		}

		if (this.listaCodigosPublicosObjetivos != null) {
			List<TipoPublicoObjetivoEntidadGridDTO> lista= new ArrayList<TipoPublicoObjetivoEntidadGridDTO>();
			for(Long cod : listaCodigosPublicosObjetivos) {
				TipoPublicoObjetivoEntidadGridDTO po = new TipoPublicoObjetivoEntidadGridDTO();
				po.setCodigo(cod);
				lista.add(po);
			}

			resultado.setPublicoObjetivos(lista);
		}

		if (this.listaCodigosMaterias != null) {
			List<TipoMateriaSIAGridDTO> lista= new ArrayList<TipoMateriaSIAGridDTO>();
			for(Long cod : listaCodigosMaterias) {
				TipoMateriaSIAGridDTO mat = new TipoMateriaSIAGridDTO();
				mat.setCodigo(cod);
				lista.add(mat);
			}

			resultado.setMaterias(lista);
		}

		if(this.filtroPaginacion!=null) {
			resultado.setPaginacionActiva(true);
			resultado.setPaginaFirst(filtroPaginacion.getPage());
			resultado.setPaginaTamanyo(filtroPaginacion.getSize());
		}

		if (this.fechaPublicacionDesde != null) {
			resultado.setFechaPublicacionDesde(fechaPublicacionDesde);
		}

		if (this.fechaPublicacionHasta != null) {
			resultado.setFechaPublicacionHasta(fechaPublicacionHasta);
		}

		if (this.comun != null) {
			resultado.setComun(comun.toString());
		}

		if (this.codigoSia != null) {
			resultado.setCodigoSIA(codigoSia);
		}

		if (this.volcadoSia != null) {
			resultado.setVolcadoSIA(volcadoSia);
		}

		if (this.estadoWF != null) {
			resultado.setEstadoWF(estadoWF);
		}else {
			resultado.setEstadoWF("T");
		}

		if (this.estadoSia != null) {
			resultado.setEstadoSIA(estadoSia);
		}

		if (this.fechaActualizacionSia != null) {
			resultado.setSiaFecha(fechaActualizacionSia);
		}

		if (this.tramiteVigente != null) {
			resultado.setTramiteVigente(tramiteVigente);
		}

		if (this.tramiteTelematico != null) {
			resultado.setTramiteTelematico(tramiteTelematico);
		}

		if(this.codigoPlantilla!=null) {
			TipoTramitacionDTO plan = new TipoTramitacionDTO();
			plan.setCodigo(codigoPlantilla);
			resultado.setPlantilla(plan);
		}

		resultado.setTipo("P");
		resultado.setEsProcedimiento(true);

		return resultado;
	}


	public FiltroPaginacion getFiltroPaginacion() {
		return filtroPaginacion;
	}

	public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
		this.filtroPaginacion = filtroPaginacion;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	public CampoOrden getOrden() {
		return orden;
	}

	public void setOrden(CampoOrden orden) {
		this.orden = orden;
	}
}
