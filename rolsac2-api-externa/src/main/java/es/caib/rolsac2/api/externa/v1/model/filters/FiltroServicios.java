package es.caib.rolsac2.api.externa.v1.model.filters;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * FiltroProcedimientos.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "FiltroServicios", description = "Filtro que permite buscar por diferentes campos")
public class FiltroServicios extends EntidadJson<FiltroServicios> {

    private static final Logger LOG = LoggerFactory.getLogger(FiltroServicios.class);

    public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION = "fechaPublicacion";
    public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_ACTUALIZACION = "fechaActualizacion";
    public static final String CAMPO_ORD_PROCEDIMIENTO_CODIGO = "codigo";

    public static final String SAMPLE = Constantes.SALTO_LINEA + "{" + "\"codigoUA\":0," + Constantes.SALTO_LINEA + "\"codigoUADir3\":\"0\"," + Constantes.SALTO_LINEA + "\"textos\":\"string\", (Compara con codigo, nombre, estado, tipo, codigoSia, estadoSia y codigoDir3Sia)" + Constantes.SALTO_LINEA + "\"titulo\":\"string\"," + Constantes.SALTO_LINEA + "\"codigo\":0," + Constantes.SALTO_LINEA + "\"volcadoSia\":\"S/N\", (S=Si, N=No)" + Constantes.SALTO_LINEA + "\"estadoWF\":\"D/M/T/A\", (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))" + Constantes.SALTO_LINEA + "\"comun\":0/1, (1= procedimientos comunes)" + Constantes.SALTO_LINEA + "\"codigoSia\":0," + Constantes.SALTO_LINEA + "\"codigoPlantilla\":0," + Constantes.SALTO_LINEA + "\"codigoPlataforma\":0," + Constantes.SALTO_LINEA + "\"idTramite\":0," + Constantes.SALTO_LINEA + "\"idPlataforma\":0," + Constantes.SALTO_LINEA + "\"version\":0," + Constantes.SALTO_LINEA + "\"estado\":\"S/M/P/T/R/U/B\", (S=Pendiente Publicar, M=En modificación, P=Publicado, T=Pendiente reservar, R=Reserva, U=Pendiente borrar, B=Borrado)" + Constantes.SALTO_LINEA + "\"estados\":\"S/M/P/T/R/U/B\", (S=Pendiente Publicar, M=En modificación, P=Publicado, T=Pendiente reservar, R=Reserva, U=Pendiente borrar, B=Borrado)" + Constantes.SALTO_LINEA + "\"fechaActualizacionSia\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"listaCodigosNormativas\":[0]," + Constantes.SALTO_LINEA + "\"listaCodigosPublicosObjetivos\":[0]," + Constantes.SALTO_LINEA + "\"listaCodigosMaterias\":[0]," + Constantes.SALTO_LINEA + "\"fechaPublicacionDesde\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"fechaPublicacionHasta\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"idEntidad\":0," + Constantes.SALTO_LINEA + "\"vigente\": (S/N)," + Constantes.SALTO_LINEA + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," + Constantes.SALTO_LINEA + "\"orden\":{\"campo\":\"" + CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION + "\",\"tipoOrden\":\"ASC/DESC\"}" + "}";

    public static final String SAMPLE_JSON = "{" + "\n	\"codigoUA\":null," + "\n	\"codigoUADir3\":null," + "\n	\"textos\":null," + "\n	\"titulo\":null," + "\n	\"codigo\":null," + "\n	\"volcadoSia\":null," + "\n	\"estadoWF\":null," + "\n	\"comun\":null," + "\n	\"codigoSia\":null," + "\n	\"codigoPlantilla\":null," + "\n	\"codigoPlataforma\":null," + "\n	\"idTramite\":null," + "\n	\"idPlataforma\":null," + "\n	\"version\":null," + "\n	\"estado\":null," + "\n	\"estados\":null," + "\n	\"fechaActualizacionSia\":null," + "\n	\"listaCodigosNormativas\":null," + "\n	\"listaCodigosPublicosObjetivos\":null," + "\n	\"listaCodigosMaterias\":null," + "\n	\"fechaPublicacionDesde\":null," + "\n	\"fechaPublicacionHasta\":null," + "\n	\"idEntidad\":null," + "\n	\"vigente\":null," + "\n	\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," + "\n	\"orden\":null" + "}";

    /**
     * FiltroPaginacion.
     **/
    @Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
    private FiltroPaginacion filtroPaginacion;

    /**
     * Lista de campos a ordenar.
     **/
    @Schema(description = "Lista de campos por los que ordenar", required = false)
    private CampoOrden orden;

    /**
     * listaCodigosNormativas.
     **/
    @Schema(description = "listaCodigosNormativas", required = false)
    private List<Long> listaCodigosNormativas;

    /**
     * listaCodigosPublicosObjetivos.
     **/
    @Schema(description = "listaCodigosPublicosObjetivos", required = false)
    private List<Long> listaCodigosPublicosObjetivos;

    /**
     * listaCodigosMaterias.
     **/
    @Schema(description = "listaCodigosMaterias", required = false)
    private List<Long> listaCodigosMaterias;

    /**
     * codigoUA.
     **/
    @Schema(description = "codigoUA", type = SchemaType.INTEGER, required = false)
    private Long codigoUA;

    /**
     * codigoPlantilla.
     **/
    @Schema(description = "codigoPlantilla", type = SchemaType.INTEGER, required = false)
    private Long codigoPlantilla;

    /**
     * codigoPlataforma.
     **/
    @Schema(description = "codigoPlataforma", type = SchemaType.INTEGER, required = false)
    private Long codigoPlataforma;

    /**
     * codigo.
     **/
    @Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
    private Long codigo;

    /**
     * codigoUADir3.
     **/
    @Schema(description = "codigoUADir3", type = SchemaType.STRING, required = false)
    private String codigoUADir3;

    /**
     * estadoWF.
     **/
    @Schema(description = "estadoWF", type = SchemaType.STRING, required = false)
    private String estadoWF;

    /**
     * volcadoSia.
     **/
    @Schema(description = "volcadoSia", type = SchemaType.STRING, required = false)
    private String volcadoSia;

    /**
     * estado.
     **/
    @Schema(description = "estado", type = SchemaType.STRING, required = false)
    private String estado;

    /**
     * estados.
     **/
    @Schema(description = "estados", type = SchemaType.STRING, required = false)
    private List<String> estados;

    /**
     * Entidad
     */
    @Schema(name = "idEntidad", description = "idEntidad", type = SchemaType.INTEGER, required = false)
    private Long idEntidad;

    /**
     * textos.
     **/
    @Schema(description = "textos", type = SchemaType.STRING, required = false)
    private String textos;

    /**
     * textos.
     **/
    @Schema(description = "titulo", type = SchemaType.STRING, required = false)
    private String titulo;

    /**
     * fechaPublicacionDesde.
     **/
    @Schema(description = "fechaPublicacionDesde", type = SchemaType.STRING, required = false)
    private String fechaPublicacionDesde;

    /**
     * fechaPublicacionHasta.
     **/
    @Schema(description = "fechaPublicacionHasta", type = SchemaType.STRING, required = false)
    private String fechaPublicacionHasta;

    /**
     * comun.
     **/
    @Schema(description = "comun", type = SchemaType.INTEGER, required = false)
    private Integer comun;

    /**
     * codigoSia.
     **/
    @Schema(description = "codigoSia", type = SchemaType.INTEGER, required = false)
    private Integer codigoSia;

    /**
     * estadoSia.
     **/
    @Schema(description = "estadoSia", type = SchemaType.STRING, required = false)
    private String estadoSia;

    /**
     * fechaActualizacionSia.
     **/
    @Schema(description = "fechaActualizacionSia", type = SchemaType.STRING, required = false)
    private String fechaActualizacionSia;

    @Schema(description = "idTramite", type = SchemaType.STRING, required = false)
    private String idTramite;

    @Schema(description = "idPlataforma", type = SchemaType.STRING, required = false)
    private String idPlataforma;

    @Schema(description = "version", type = SchemaType.STRING, required = false)
    private Integer version;

    @Schema(description = "vigente", type = SchemaType.STRING, required = false)
    private String vigente;

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

    public String getVolcadoSia() {
        return volcadoSia;
    }

    public void setVolcadoSia(String volcadoSia) {
        this.volcadoSia = volcadoSia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
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

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public String getIdPlataforma() {
        return idPlataforma;
    }

    public void setIdPlataforma(String idPlataforma) {
        this.idPlataforma = idPlataforma;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

        if (this.estado != null) {
            resultado.setEstado(estado);
        }

        if (this.estados != null) {
            resultado.setEstados(estados);
        }

        if (this.textos != null) {
            resultado.setTexto(textos);
        }

        if (this.titulo != null) {
            resultado.setTexto(titulo);
        }

        if (this.codigoPlataforma != null) {
            PlatTramitElectronicaDTO pl = new PlatTramitElectronicaDTO();
            pl.setCodigo(codigoPlataforma);
            resultado.setPlataforma(pl);
        }

        if (this.titulo != null) {
            resultado.setTexto(titulo);
        }

        if (this.listaCodigosNormativas != null) {
            List<NormativaGridDTO> lista = new ArrayList<NormativaGridDTO>();
            for (Long cod : listaCodigosNormativas) {
                NormativaGridDTO norm = new NormativaGridDTO();
                norm.setCodigo(cod);
                lista.add(norm);
            }

            resultado.setNormativas(lista);
        }

        if (this.listaCodigosPublicosObjetivos != null) {
            List<TipoPublicoObjetivoEntidadGridDTO> lista = new ArrayList<TipoPublicoObjetivoEntidadGridDTO>();
            for (Long cod : listaCodigosPublicosObjetivos) {
                TipoPublicoObjetivoEntidadGridDTO po = new TipoPublicoObjetivoEntidadGridDTO();
                po.setCodigo(cod);
                lista.add(po);
            }

            resultado.setPublicoObjetivos(lista);
        }

        if (this.listaCodigosMaterias != null) {
            List<TipoMateriaSIAGridDTO> lista = new ArrayList<TipoMateriaSIAGridDTO>();
            for (Long cod : listaCodigosMaterias) {
                TipoMateriaSIAGridDTO mat = new TipoMateriaSIAGridDTO();
                mat.setCodigo(cod);
                lista.add(mat);
            }

            resultado.setMaterias(lista);
        }

        if (this.filtroPaginacion != null) {
            resultado.setPaginacionActiva(true);
            resultado.setPaginaFirst(filtroPaginacion.getPage());
            resultado.setPaginaTamanyo(filtroPaginacion.getSize());
        }

        if (this.fechaPublicacionDesde != null) {
            resultado.setFechaPublicacionDesde(fechaPublicacionDesde);
        }

        if (this.idEntidad != null) {
            resultado.setIdEntidad(idEntidad);
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
        } else {
            resultado.setEstadoWF("T");
        }

        if (this.estadoSia != null) {
            resultado.setEstadoSIA(estadoSia);
        }

        if (this.fechaActualizacionSia != null) {
            resultado.setSiaFecha(fechaActualizacionSia);
        }

        if (this.vigente != null) {
            resultado.setTramiteVigente(vigente);
        }

        if (this.codigoPlantilla != null) {
            TipoTramitacionDTO plan = new TipoTramitacionDTO();
            plan.setCodigo(codigoPlantilla);
            resultado.setPlantilla(plan);
        }

        if (this.idPlataforma != null) {
            resultado.setIdentificadorPlataforma(this.idPlataforma);
        }

        if (this.idTramite != null) {
            resultado.setIdTramite(idTramite);
        }

        if (this.version != null) {
            resultado.setVersion(version);
        }

        if (orden != null) {
            resultado.setOrderBy(orden.getCampo());
            resultado.setOrder(orden.getTipoOrden());
        }


        resultado.setTipo("S");
        resultado.setEsProcedimiento(false);

        return resultado;
    }


    public FiltroPaginacion getFiltroPaginacion() {
        return filtroPaginacion;
    }

    public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
        this.filtroPaginacion = filtroPaginacion;
    }

    public CampoOrden getOrden() {
        return orden;
    }

    public void setOrden(CampoOrden orden) {
        this.orden = orden;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }
}
