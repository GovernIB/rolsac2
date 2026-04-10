package es.caib.rolsac2.api.interna.v1.model.filters;

import es.caib.rolsac2.api.interna.v1.model.EntidadJson;
import es.caib.rolsac2.api.interna.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

    public static final String SAMPLE = Constantes.SALTO_LINEA + "{" + "\"codigoUA\":0," + Constantes.SALTO_LINEA + "\"codigoUADir3\":\"0\"," + Constantes.SALTO_LINEA + "\"codigos\":[0]," + Constantes.SALTO_LINEA + "\"textos\":\"string\", (Compara con codigo, nombre, estado, tipo, codigoSia, estadoSia y codigoDir3Sia)" + Constantes.SALTO_LINEA + "\"titulo\":\"string\"," + Constantes.SALTO_LINEA + "\"codigo\":0," + Constantes.SALTO_LINEA + "\"estadoSia\":\"A/B/N\", (A=Alta, B=Baja, N=No integrado)" + Constantes.SALTO_LINEA + "\"estadoWF\":\"D/M/T/A\", (D=Definitivo, M=Modificado, T=Todos (publicado o modificado, solo se muestra uno), A=Ambos (publicado y modificado))" + Constantes.SALTO_LINEA + "\"comun\":0, (1=Procedimientos comunes, 0=Todos)" + Constantes.SALTO_LINEA + "\"codigoSia\":0," + Constantes.SALTO_LINEA + "\"codigoPlataforma\":0," + Constantes.SALTO_LINEA + "\"codigoMateria\":0," + Constantes.SALTO_LINEA + "\"plataforma\":\"string\"," + Constantes.SALTO_LINEA + "\"codigoTramiteTelematico\":\"string\"," + Constantes.SALTO_LINEA + "\"versionTramiteTelematico\":\"0\"," + Constantes.SALTO_LINEA + "\"parametros\":\"string\"," + Constantes.SALTO_LINEA + "\"version\":0," + Constantes.SALTO_LINEA + "\"estado\":\"PV/M/P/PT/T\", (PV=Pendent validació, M=En modificació, P=Publicat, PT=Pendent tancar, T=Tancat)" + Constantes.SALTO_LINEA + "\"estados\":[\"PV\",\"M\"], (PV=Pendent validació, M=En modificació, P=Publicat, PT=Pendent tancar, T=Tancat)" + Constantes.SALTO_LINEA + "\"fechaActualizacionSia\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"codigosNormativas\":[0]," + Constantes.SALTO_LINEA + "\"codigosPublicosObjetivos\":[0]," + Constantes.SALTO_LINEA + "\"codigosMaterias\":[0]," + Constantes.SALTO_LINEA + "\"fechaPublicacionDesde\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"activo\":0, (1=Visible en sede, 0=No visible en sede)," + Constantes.SALTO_LINEA + "\"buscarEnDescendientesUA\":0, (1=Si, 0=No)," + Constantes.SALTO_LINEA + "\"fechaPublicacionHasta\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"idEntidad\":0," + Constantes.SALTO_LINEA + "\"vigente\":\"S/N\", (S=Vigente, N=No vigente)" + Constantes.SALTO_LINEA + "\"estadoUA\":1/2, (1=Pública,2=Interna)" + Constantes.SALTO_LINEA + "\"filtroPaginacion\":{\"page\":0,\"size\":10}," + Constantes.SALTO_LINEA + "\"orden\":{\"campo\":\"" + CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION + "\",\"tipoOrden\":\"ASC/DESC\"}" + "}";

    public static final String SAMPLE_JSON = "{" + "\n	\"codigoUA\":null," + "\n	\"codigoUADir3\":null," + "\"codigos\":null," + "\"buscarEnDescendientesUA\":null," + "\"activo\":null," + "\n	\"textos\":null," + "\n	\"titulo\":null," + "\n	\"codigo\":null," + "\n	\"estadoSia\":null," + "\n	\"estadoWF\":null," + "\n	\"comun\":null," + "\n	\"codigoSia\":null," + "\n	\"codigoPlataforma\":null," + "\n	\"plataforma\":null," + "\n	\"version\":null," + "\n	\"estado\":null," + "\n	\"estados\":null," + "\n	\"fechaActualizacionSia\":null," + "\n	\"codigosNormativas\":null," + "\n	\"codigosPublicosObjetivos\":null," + "\n	\"codigosMaterias\":null," + "\n	\"fechaPublicacionDesde\":null," + "\n	\"fechaPublicacionHasta\":null," + "\n	\"idEntidad\":null," + "\n	\"vigente\":null," + "\n \"codigoMateria\":null," + "\n \"codigoTramiteTelematico\":null," +  "\n \"parametros\":null," + "\n \"versionTramiteTelematico\":null," +  "\n \"estadoUA\":null," + "\n	\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," + "\n	\"orden\":null" + "}";

    /**
     * FiltroPaginacion.
     **/
    @Schema(name = "filtroPaginacion", description = "Filtro de paginacion", required = false)
    private FiltroPaginacion filtroPaginacion;

    /**
     * Lista de campos a ordenar.
     **/
    @Schema(name = "orden", description = "Filtro de orden", required = false)
    private CampoOrden orden;

    /**
     * codigosNormativas.
     **/
    @Schema(name = "codigosNormativas", description = "Lista de codigos de normativas separados por comas. Se pueden consultar en el metodo /services/v1/tipos_normativa", required = false)
    private List<Long> codigosNormativas;

    /**
     * codigosPublicosObjetivos.
     **/
    @Schema(name = "codigosPublicosObjetivos", description = "Lista de codigos de publico objetivo separados por comas. Se pueden consultar en el metodo /services/v1/publicos_objetivo", required = false)
    private List<Long> codigosPublicosObjetivos;

    /**
     * codigosMaterias.
     **/
    @Schema(name = "codigosMaterias", description = "Lista de codigos de materias separados por comas. Se pueden consultar en el metodo /services/v1/tipos_materia", required = false)
    private List<Long> codigosMaterias;

    /**
     * codigoUA.
     **/
    @Schema(name = "codigoUA", description = "Codigo de la unidad administrativa. Este valor puede sacarse del metodo /services/v1/unidades_administrativas", type = SchemaType.INTEGER, required = false)
    private Long codigoUA;

    /**
     * codigoPlataforma.
     **/
    @Schema(name = "codigoPlataforma", description = "Este valor puede sacarse del metodo /services/v1/plataformas", type = SchemaType.INTEGER, required = false)
    private Long codigoPlataforma;

    /**
     * codigo.
     **/
    @Schema(name = "codigo", description = "Codigo del servicio. Este valor puede consultarse en el metodo /services/v1/servicios", type = SchemaType.INTEGER, required = false)
    private Long codigo;

    /**
     * codigo.
     **/
    @Schema(name = "codigos", description = "Lista de codigos de servicios separada por comas.", required = false)
    private List<Long> codigos;


    /**
     * codigoUADir3.
     **/
    @Schema(name = "codigoUADir3", description = "Codigo dir3 de la unidad administrativa. Puede obtenerlo respectivamente con los metodos /services/v1/unidades_administrativas/codigoDir3/{codigo} y /services/v1/unidades_administrativas/codigoDir3/{codigos}", type = SchemaType.STRING, required = false)
    private String codigoUADir3;

    /**
     * estadoWF.
     **/
    @Schema(name = "estadoWF", description = "Estado del workflow del servicio. D = Definitivo, M = Modificado, T = Publicado o modificado (Muestra un workflow teniendo preferencia el publicado si existen ambos), A = Publicado y modificado.", type = SchemaType.STRING, required = false)
    private String estadoWF;


    /**
     * estado.
     **/
    @Schema(name = "estado", description = "Estado del servicio. PV – Pendiente de validación, M – En modificación, P – Publicado, PT – Pendiente de cerrar, T – Cerrado.", type = SchemaType.STRING, required = false)
    private String estado;

    /**
     * estados.
     **/
    @Schema(name = "estados", description = "Lista de estados. (PV – Pendiente de validación, M – En modificación, P – Publicado, PT – Pendiente de cerrar, T – Cerrado)", type = SchemaType.STRING, required = false)
    private List<String> estados;

    /**
     * Entidad
     */
    @Schema(name = "idEntidad", description = "Codigo de la entidad. Se puede consultar en el metodo /services/v1/entidades", type = SchemaType.INTEGER, required = false)
    private Long idEntidad;

    /**
     * textos.
     **/
    @Schema(name = "textos", description = "Compara con codigo procedimiento, nombre, estado, codigo SIA, estado SIA y codigo dir3 SIA.", type = SchemaType.STRING, required = false)
    private String textos;

    /**
     * textos.
     **/
    @Schema(name = "titulo", description = "Titulo del servicio.", type = SchemaType.STRING, required = false)
    private String titulo;

    /**
     * fechaPublicacionDesde.
     **/
    @Schema(name = "fechaPublicacionDesde", description = "Fecha de publicacion igual o superior", type = SchemaType.STRING, required = false)
    private String fechaPublicacionDesde;

    /**
     * fechaPublicacionHasta.
     **/
    @Schema(name = "fechaPublicacionHasta", description = "Fecha de publicacion igual o anterior", type = SchemaType.STRING, required = false)
    private String fechaPublicacionHasta;

    /**
     * comun.
     **/
    @Schema(name = "comun", description = "1 – Comunes, 0 – No comunes", type = SchemaType.INTEGER, required = false)
    private Integer comun;

    /**
     * codigoSia.
     **/
    @Schema(name = "codigoSia", description = "Codigo sia", type = SchemaType.INTEGER, required = false)
    private Integer codigoSia;

    /**
     * estadoSia.
     **/
    @Schema(name = "estadoSia", description = "Valores posibles: A (Alta), B (Baja), N (No integrado).", type = SchemaType.STRING, required = false)
    private String estadoSia;

    /**
     * fechaActualizacionSia.
     **/
    @Schema(name = "fechaActualizacionSia", description = "Fecha de actualización de SIA (DD/MM/YYYY)", type = SchemaType.STRING, required = false)
    private String fechaActualizacionSia;

    @Schema(name = "plataforma", description = "Identificador de plataforma. Se puede consultar en el método /services/v1/plataformas", type = SchemaType.STRING, required = false)
    private String plataforma;

    @Schema(name = "version", description = "Versión del tipo de tramitación. Se obtiene del método /services/v1/tipos_tramitacion", type = SchemaType.INTEGER, required = false)
    private Integer version;

    @Schema(name = "vigente", description = "S = Vigente, N = No vigente", type = SchemaType.STRING, required = false)
    private String vigente;

    /**
     * buscarEnDescendientesUA.
     **/
    @Schema(name = "buscarEnDescendientesUA", description = "Buscar en descendientes UA. 1 - Si, 0 - No.", type = SchemaType.INTEGER, required = false)
    private Integer buscarEnDescendientesUA;

    /**
     * activo corresponde a visible en SEDE.
     **/
    @Schema(name = "activo", description = "Corresponde a visible en SEDE. 1- Visible, 0 - No visible.", type = SchemaType.INTEGER, required = false)
    private Integer activo;

    @Schema(name = "codigoMateria", description = "Codigo de materia. Este valor se puede sacar del metodo /services/v1/tipos_materia", type = SchemaType.STRING, required = false)
    private Long codigoMateria;

    @Schema(name = "codigoTramiteTelematico", description = "Identificador del trámite telemático.", type = SchemaType.STRING, required = false)
    private String codigoTramiteTelematico;

    @Schema(name = "versionTramiteTelematico", description = "Versión del trámite telemático.", type = SchemaType.STRING, required = false)
    private String versionTramiteTelematico;

    @Schema(name = "parametros", description = "Parámetros del trámite telemático.", type = SchemaType.STRING, required = false)
    private String parametros;

    @Schema(name = "estadoUA", description = "Estado de la Unidad Administrativa. (1=Pública, 2=Interna)", type = SchemaType.INTEGER, required = false)
    private Integer estadoUA;

    /**
     * @return the textos
     */
    public String getTextos() {
        return textos;
    }

    public List<Long> getCodigosNormativas() {
        return codigosNormativas;
    }

    public void setCodigosNormativas(List<Long> codigosNormativas) {
        this.codigosNormativas = codigosNormativas;
    }

    public Long getCodigoUA() {
        return codigoUA;
    }

    public void setCodigoUA(Long codigoUA) {
        this.codigoUA = codigoUA;
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

    public List<Long> getCodigos() {
        return codigos;
    }

    public void setCodigos(List<Long> codigos) {
        this.codigos = codigos;
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

    public List<Long> getCodigosPublicosObjetivos() {
        return codigosPublicosObjetivos;
    }

    public void setCodigosPublicosObjetivos(List<Long> codigosPublicosObjetivos) {
        this.codigosPublicosObjetivos = codigosPublicosObjetivos;
    }

    public List<Long> getcodigosMaterias() {
        return codigosMaterias;
    }

    public void setcodigosMaterias(List<Long> codigosMaterias) {
        this.codigosMaterias = codigosMaterias;
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

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(Long codigoMateria) {
        this.codigoMateria = codigoMateria;
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

        if (this.codigos != null) {
            resultado.setCodigosProc(codigos);
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

        if (this.codigosNormativas != null) {
            List<NormativaGridDTO> lista = new ArrayList<NormativaGridDTO>();
            for (Long cod : codigosNormativas) {
                NormativaGridDTO norm = new NormativaGridDTO();
                norm.setCodigo(cod);
                lista.add(norm);
            }

            resultado.setNormativas(lista);
        }

        if (this.codigosPublicosObjetivos != null) {
            List<TipoPublicoObjetivoEntidadGridDTO> lista = new ArrayList<TipoPublicoObjetivoEntidadGridDTO>();
            for (Long cod : codigosPublicosObjetivos) {
                TipoPublicoObjetivoEntidadGridDTO po = new TipoPublicoObjetivoEntidadGridDTO();
                po.setCodigo(cod);
                lista.add(po);
            }

            resultado.setPublicoObjetivos(lista);
        }

        if (this.codigosMaterias != null) {
            List<TipoMateriaSIAGridDTO> lista = new ArrayList<TipoMateriaSIAGridDTO>();
            for (Long cod : codigosMaterias) {
                TipoMateriaSIAGridDTO mat = new TipoMateriaSIAGridDTO();
                mat.setCodigo(cod);
                lista.add(mat);
            }

            resultado.setMaterias(lista);
        }

        if(this.codigoMateria != null) {
            resultado.setCodigoMateria(this.codigoMateria);
        }

        if (this.filtroPaginacion != null) {
            resultado.setPaginacionActiva(true);
            resultado.setPaginaFirst(filtroPaginacion.getOffset());
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
            resultado.setComun(comun == 1 ? "S" : "N");
        }

        if (this.codigoSia != null) {
            resultado.setCodigoSIA(codigoSia);
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

        if (this.plataforma != null) {
            resultado.setIdentificadorPlataforma(this.plataforma);
        }

        if (this.version != null) {
            resultado.setVersion(version);
        }

        if (orden != null) {
            resultado.setOrderBy(orden.getCampo());
            resultado.setAscendente(orden.getTipoOrden() != null && orden.getTipoOrden().equalsIgnoreCase("asc"));
        }
        if (this.buscarEnDescendientesUA != null) {
            resultado.setBuscarEnDescendientesUA(buscarEnDescendientesUA.compareTo(1) == 0);
        }
        if (this.activo != null) {
            resultado.setVisibleSEDE(activo == 1 ? "S" : "N");
        }

        if( StringUtils.isNotBlank(this.codigoTramiteTelematico)){
            resultado.setIdTramiteTelematico(this.codigoTramiteTelematico);
        }

        if( NumberUtils.isCreatable(this.versionTramiteTelematico)){
            resultado.setVersionTramiteTelematico(NumberUtils.createInteger(this.versionTramiteTelematico));
        }

        if( this.parametros != null){
            resultado.setParametrosTramiteElectronico(this.parametros);
        }

        if(this.estadoUA != null){
            resultado.setuAInterna(this.estadoUA == 2 ? true : false);
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

    public Integer getBuscarEnDescendientesUA() {
        return buscarEnDescendientesUA;
    }

    public void setBuscarEnDescendientesUA(Integer buscarEnDescendientesUA) {
        this.buscarEnDescendientesUA = buscarEnDescendientesUA;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getCodigoTramiteTelematico() {
        return codigoTramiteTelematico;
    }

    public void setCodigoTramiteTelematico(String codigoTramiteTelematico) {
        this.codigoTramiteTelematico = codigoTramiteTelematico;
    }

    public String getVersionTramiteTelematico() {
        return versionTramiteTelematico;
    }

    public void setVersionTramiteTelematico(String versionTramiteTelematico) {
        this.versionTramiteTelematico = versionTramiteTelematico;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public Integer getEstadoUA() {
        return estadoUA;
    }

    public void setEstadoUA(Integer estadoUA) {
        this.estadoUA = estadoUA;
    }
}
