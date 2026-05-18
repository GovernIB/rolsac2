package es.caib.rolsac2.api.interna.v1.model.filters;

import es.caib.rolsac2.api.interna.v1.model.EntidadJson;
import es.caib.rolsac2.api.interna.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
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
@Schema(name = "FiltroProcedimientos", description = "Filtro que permite buscar por diferentes campos")
public class FiltroProcedimientos extends EntidadJson<FiltroProcedimientos> {

    private static final Logger LOG = LoggerFactory.getLogger(FiltroProcedimientos.class);

    public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION = "fechaPublicacion";
    public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_ACTUALIZACION = "fechaActualizacion";
    public static final String CAMPO_ORD_PROCEDIMIENTO_CODIGO = "codigo";

    public static final String SAMPLE = Constantes.SALTO_LINEA + "{" + "\"codigoUA\":0," + Constantes.SALTO_LINEA + "\"codigoUADir3\":\"0\"," + Constantes.SALTO_LINEA + "\"codigoPublicoObjetivoEntidad\":0," + Constantes.SALTO_LINEA + "\"codigos\":[0]," + Constantes.SALTO_LINEA + "\"textos\":\"string\", (Compara con codigo, nombre, estado, tipo, codigoSia, estadoSia y codigoDir3Sia)" + Constantes.SALTO_LINEA + "\"codigoFormaInicio\":0," + Constantes.SALTO_LINEA + "\"titulo\":\"string\"," + Constantes.SALTO_LINEA + "\"codigoTipoProcedimiento\":0," + Constantes.SALTO_LINEA + "\"codigoSilencioAdministrativo\":0," + Constantes.SALTO_LINEA + "\"codigoFinVia\":0," + Constantes.SALTO_LINEA + "\"codigo\":0," + Constantes.SALTO_LINEA + "\"estadoWF\":\"D/M/T/A\", (D=Definitivo, M=Modificado, T=Todos (publicado o modificado, solo se muestra uno), A=Ambos (publicado y modificado))" + Constantes.SALTO_LINEA + "\"comun\":0, (1=Procedimientos comunes, 0=Todos)" + Constantes.SALTO_LINEA + "\"codigoSia\":0," + Constantes.SALTO_LINEA + "\"codigoTram\":0," + Constantes.SALTO_LINEA + "\"codigoPlantilla\":0," + Constantes.SALTO_LINEA + "\"codigoPlataforma\":0," + Constantes.SALTO_LINEA + "\"tramiteVigente\":\"S/N\", (S=Si, N=No)" + Constantes.SALTO_LINEA + "\"canalPresentacion\":\"string\"," + Constantes.SALTO_LINEA + "\"estado\":\"PV/M/P/PT/T\", (PV=Pendent validació, M=En modificació, P=Publicat, PT=Pendent tancar, T=Tancat)" + Constantes.SALTO_LINEA + "\"esPDU\":0," + Constantes.SALTO_LINEA + "\"estados\":[\"PV\",\"M\",\"P\",\"PT\",\"T\"], (PV=Pendent validació, M=En modificació, P=Publicat, PT=Pendent tancar, T=Tancat)" + Constantes.SALTO_LINEA + "\"estadoSia\":\"A/B/N\", (A=Alta, B=Baja, N=No integrado)" + Constantes.SALTO_LINEA + "\"activo\":0, (1=Visible en sede, 0=No visible en sede)," + Constantes.SALTO_LINEA + "\"buscarEnDescendientesUA\":0, (1=Si, 0=No)" + Constantes.SALTO_LINEA + "\"fechaActualizacionSia\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"codigosNormativas\":[0]," + Constantes.SALTO_LINEA + "\"codigosPublicosObjetivosEntidad\":[0]," + Constantes.SALTO_LINEA + "\"codigosMaterias\":[0]," + Constantes.SALTO_LINEA + "\"fechaPublicacionDesde\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"fechaPublicacionHasta\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA + "\"telematico\":0, (1=Telematico, 0=No telematico)" + Constantes.SALTO_LINEA + "\"disponibleFuncionarioHabilitado\":0, (0=No habilitado, 1=Sí habilitado, null no filtra por campo)" + Constantes.SALTO_LINEA + "\"disponibleApoderadoHabilitado\":0, (0=No habilitado, 1=Sí habilitado, null no filtra por campo)" + Constantes.SALTO_LINEA + "\"idEntidad\":0," + Constantes.SALTO_LINEA + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," + Constantes.SALTO_LINEA + "\"orden\":{\"campo\":\"" + CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION + "\",\"tipoOrden\":\"ASC/DESC\"}" + "}";

    public static final String SAMPLE_JSON = "{" + "\"codigoUA\":null," + "\"codigoUADir3\":null," + "\"buscarEnDescendientesUA\":null," + "\"codigoPublicoObjetivoEntidad\":null," + "\"codigos\":null," + "\"textos\":null," + "\"codigoFormaInicio\":null," + "\"activo\":null," + "\"titulo\":null," + "\"codigoTipoProcedimiento\":null," + "\"codigoSilencioAdministrativo\":null," + "\"codigoFinVia\":null," + "\"codigo\":null," + "\"estadoWF\":null," + "\"comun\":null," + "\"codigoSia\":null," + "\"codigoTram\":null," + "\"codigoPlantilla\":null," + "\"codigoPlataforma\":null," + "\"tramiteVigente\":null," + "\"canalPresentacion\":null," + "\"estado\":null," + "\"estados\":null," + "\"estadoSia\":null," + "\"fechaActualizacionSia\":null," + "\"codigosNormativas\":null," + "\"codigosPublicosObjetivosEntidad\":null," + "\"codigosMaterias\":null," + "\"fechaPublicacionDesde\":null," + "\"fechaPublicacionHasta\":null," + "\"telematico\":null," + "\"disponibleFuncionarioHabilitado\":null," + "\"disponibleApoderadoHabilitado\":null," + "\"idEntidad\":null," + "\"esPdu\":null," + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," + "\"orden\":null" + "}";

    /**
     * FiltroPaginacion.
     **/
    @Schema(name = "filtroPaginacion", description = "Filtro de paginacion", required = false)
    private FiltroPaginacion filtroPaginacion;

    /**
     * Entidad
     */
    @Schema(name = "idEntidad", description = "Codigo de la entidad. Se puede consultar en el metodo /services/v1/entidades", type = SchemaType.INTEGER, required = false)
    private Long idEntidad;

    /**
     * Lista de campos a ordenar.
     **/
    @Schema(name = "orden", description = "Filtro de orden", required = false)
    private CampoOrden orden;

    /**
     * codigosNormativas.
     **/
    @Schema(name = "codigosNormativas", description = "Lista de codigos de normativas . Se pueden consultar en el metodo /services/v1/tipos_normativa", required = false)
    private List<Long> codigosNormativas;

    /**
     * codigosPublicosObjetivosEntidad.
     **/
    @Schema(name = "codigosPublicosObjetivosEntidad", description = "Lista de codigos de publico objetivo . Se pueden consultar en el metodo /services/v1/publicos_objetivo", required = false)
    private List<Long> codigosPublicosObjetivosEntidadEntidad;

    /**
     * codigosMaterias.
     **/
    @Schema(name = "codigosMaterias", description = "Lista de codigos de materias . Se pueden consultar en el metodo /services/v1/tipos_materia", required = false)
    private List<Long> codigosMaterias;

    /**
     * codigoUA.
     **/
    @Schema(name = "codigoUA", description = "Codigo de la unidad administrativa. Este valor puede sacarse del metodo /services/v1/unidades_administrativas", type = SchemaType.INTEGER, required = false)
    private Long codigoUA;

    /**
     * codigoPlantilla.
     **/
    @Schema(name = "codigoPlantilla", description = "Codigo plantilla", type = SchemaType.INTEGER, required = false)
    private Long codigoPlantilla;

    /**
     * codigoPlataforma.
     **/
    @Schema(name = "codigoPlataforma", description = "Este valor puede sacarse del metodo /services/v1/plataformas", type = SchemaType.INTEGER, required = false)
    private Long codigoPlataforma;

    /**
     * codigo.
     **/
    @Schema(name = "codigo", description = "Codigo de procedimiento. Este valor puede consultarse en el metodo /services/v1/procedimientos", type = SchemaType.INTEGER, required = false)
    private Long codigo;

    /**
     * codigo.
     **/
    @Schema(name = "codigos", description = "Lista de codigos de procedimientos separada por comas.", required = false)
    private List<Long> codigos;


    /**
     * codigoTram.
     **/
    @Schema(name = "codigoTram", description = "Codigo del tramite. Este valor puede consultarse en el metodo /services/v1/tramites", type = SchemaType.INTEGER, required = false)
    private Long codigoTram;

    /**
     * codigoUADir3.
     **/
    @Schema(name = "codigoUADir3", description = "Codigo dir3 de la unidad administrativa. Puede obtenerlo respectivamente con los metodos /services/v1/unidades_administrativas/codigoDir3/{codigo} y /services/v1/unidades_administrativas/codigoDir3/{codigos}", type = SchemaType.STRING, required = false)
    private String codigoUADir3;

    /**
     * estadoWF.
     **/
    @Schema(name = "estadoWF", description = "Estado del workflow del procedimiento. D = Definitivo, M = Modificado, T = Publicado o modificado (Muestra un workflow teniendo preferencia el publicado si existen ambos), A = Publicado y modificado.", type = SchemaType.STRING, required = false)
    private String estadoWF;

    /**
     * canalPresentacion.
     **/
    @Schema(name = "canalPresentacion", description = "Canal de presentacion", type = SchemaType.STRING, required = false)
    private String canalPresentacion;

    @Schema(name = "telematico", description = "1 - Telematico, 0 - No telematico.", type = SchemaType.INTEGER, required = false)
    private Integer telematico;

    /**
     * tramiteVigente.
     **/
    @Schema(name = "tramiteVigente", description = "S = Vigente, N = No vigente", type = SchemaType.STRING, required = false)
    private String tramiteVigente;

    /**
     * estado.
     **/
    @Schema(name = "estado", description = "Estado del procedimiento. PV – Pendiente de validación, M – En modificación, P – Publicado, PT – Pendiente de cerrar, T – Cerrado.", type = SchemaType.STRING, required = false)
    private String estado;

    /**
     * estado.
     **/
    @Schema(name = "estados", description = "Lista de estados. (PV – Pendiente de validación, M – En modificación, P – Publicado, PT – Pendiente de cerrar, T – Cerrado)", type = SchemaType.STRING, required = false)
    private List<String> estados;

    /**
     * codigoPublicoObjetivo.
     **/
    @Schema(name = "codigoPublicoObjetivoEntidad", description = "Tipo publico objetivo entidad. Estes valor se puede sacar del metodo /services/v1/publicos_objetivo", type = SchemaType.INTEGER, required = false)
    private Long codigoPublicoObjetivoEntidad;

    /**
     * codigoTipoProcedimiento.
     **/
    @Schema(name = "codigoTipoProcedimiento", description = "Codigo tipo procedimiento. Este valor se puede sacar del metodo /services/v1/tipos_procedimiento", type = SchemaType.INTEGER, required = false)
    private Long codigoTipoProcedimiento;

    /**
     * textos.
     **/
    @Schema(name = "textos", description = "Compara con codigo procedimiento, nombre, estado, codigo SIA, estado SIA y codigo dir3 SIA.", type = SchemaType.STRING, required = false)
    private String textos;

    /**
     * codigoFormaInicio.
     **/
    @Schema(name = "codigoFormaInicio", description = "Codigo forma inicio. Este valor se puede sacar del metodo /services/v1/tipos_forma", type = SchemaType.INTEGER, required = false)
    private Long codigoFormaInicio;

    /**
     * codigoSilencioAdministrativo.
     **/
    @Schema(name = "codigoSilencioAdministrativo", description = "Codigo silencio administrativo. Este valor se puede sacar del metodo /services/v1/tipos_silencio", type = SchemaType.INTEGER, required = false)
    private Long codigoSilencioAdministrativo;

    /**
     * codigoFinVia.
     **/
    @Schema(name = "codigoFinVia", description = "Codigo tipo via. Este valor se puede sacar del metodo /services/v1/tipos_via", type = SchemaType.INTEGER, required = false)
    private Long codigoFinVia;

    /**
     * textos.
     **/
    @Schema(name = "titulo", description = "Titulo del procedimiento.", type = SchemaType.STRING, required = false)
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

    @Schema(name = "esPdu", description = "Indica si el procedimiento esta integrado con PDU. (1 - True, 0 - False)", type = SchemaType.INTEGER, required = false)
    private Integer esPdu;

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

    @Schema(name = "disponibleFuncionarioHabilitado", description = "1 - Habilitado, 0 - No habilitado.", type = SchemaType.INTEGER, required = false)
    private Integer disponibleFuncionarioHabilitado;

    @Schema(name = "disponibleApoderadoHabilitado", description = "1 - Habilitado, 0 - No habilitado.", type = SchemaType.INTEGER, required = false)
    private Integer disponibleApoderadoHabilitado;

    @Schema(name = "codigoMateria", description = "Codigo de materia. Este valor se puede sacar del metodo /services/v1/tipos_materia", type = SchemaType.STRING, required = false)
    private Long codigoMateria;

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

    public List<Long> getCodigos() {
        return codigos;
    }

    public void setCodigos(List<Long> codigos) {
        this.codigos = codigos;
    }

    public Long getCodigoTram() {
        return codigoTram;
    }

    public void setCodigoTram(Long codigoTram) {
        this.codigoTram = codigoTram;
    }

    public String getCanalPresentacion() {
        return canalPresentacion;
    }

    public void setCanalPresentacion(String canalPresentacion) {
        this.canalPresentacion = canalPresentacion;
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

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public Long getCodigoPublicoObjetivoEntidad() {
        return codigoPublicoObjetivoEntidad;
    }

    public void setCodigoPublicoObjetivoEntidad(Long codigoPublicoObjetivoEntidad) {
        this.codigoPublicoObjetivoEntidad = codigoPublicoObjetivoEntidad;
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

    public List<Long> getcodigosPublicosObjetivosEntidadEntidad() {
        return codigosPublicosObjetivosEntidadEntidad;
    }

    public void setcodigosPublicosObjetivosEntidadEntidad(List<Long> codigosPublicosObjetivosEntidadEntidad) {
        this.codigosPublicosObjetivosEntidadEntidad = codigosPublicosObjetivosEntidadEntidad;
    }

    public List<Long> getCodigosMaterias() {
        return codigosMaterias;
    }

    public void setCodigosMaterias(List<Long> codigosMaterias) {
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

    public Integer getEsPdu() {
        return esPdu;
    }

    public void setEsPdu(Integer esPdu) {
        this.esPdu = esPdu;
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

        if (this.codigoTram != null) {
            resultado.setCodigoTram(codigoTram);
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

        if (this.codigoFormaInicio != null) {
            TipoFormaInicioDTO fi = new TipoFormaInicioDTO();
            fi.setCodigo(codigoFormaInicio);
            resultado.setFormaInicio(fi);
        }

        if (this.codigoPublicoObjetivoEntidad != null) {
            TipoPublicoObjetivoDTO po = new TipoPublicoObjetivoDTO();
            po.setCodigo(codigoPublicoObjetivoEntidad);
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

        if (this.codigosNormativas != null) {
            List<NormativaGridDTO> lista = new ArrayList<NormativaGridDTO>();
            for (Long cod : codigosNormativas) {
                NormativaGridDTO norm = new NormativaGridDTO();
                norm.setCodigo(cod);
                lista.add(norm);
            }

            resultado.setNormativas(lista);
        }

        if (this.codigosPublicosObjetivosEntidadEntidad != null) {
            List<TipoPublicoObjetivoEntidadGridDTO> lista = new ArrayList<TipoPublicoObjetivoEntidadGridDTO>();
            for (Long cod : codigosPublicosObjetivosEntidadEntidad) {
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

        if (this.filtroPaginacion != null) {
            resultado.setPaginacionActiva(true);
            resultado.setPaginaFirst(filtroPaginacion.getOffset());
            resultado.setPaginaTamanyo(filtroPaginacion.getSize());
        }

        if (this.fechaPublicacionDesde != null) {
            resultado.setFechaPublicacionDesde(fechaPublicacionDesde);
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

        if (this.tramiteVigente != null) {
            resultado.setTramiteVigente(tramiteVigente);
        }

        if (this.canalPresentacion != null) {
            resultado.setTramiteTelematico(canalPresentacion);
        }

        if (this.telematico != null) {
            resultado.setTelematico(this.telematico == 1);
        }

        if (this.codigoPlantilla != null) {
            TipoTramitacionDTO plan = new TipoTramitacionDTO();
            plan.setCodigo(codigoPlantilla);
            resultado.setPlantilla(plan);
        }

        if (this.esPdu != null) {
            resultado.setIntegradoPdu(esPdu == 1);
        }
        if (this.buscarEnDescendientesUA != null) {
            resultado.setBuscarEnDescendientesUA(buscarEnDescendientesUA.compareTo(1) == 0);
        }
        if (this.activo != null) {
            resultado.setVisibleSEDE(activo == 1 ? "S" : "N");
        }

        if (this.disponibleFuncionarioHabilitado != null) {
            resultado.setDisponibleFuncionarioHabilitado(this.disponibleFuncionarioHabilitado == 1 ? "S" : "N");
        }

        if (this.disponibleApoderadoHabilitado != null) {
            resultado.setTramitacionPersonaApoderada(this.disponibleApoderadoHabilitado == 1 ? "S" : "N");
        }

        if (this.codigoMateria != null) {

            resultado.setCodigoMateria(codigoMateria);
        }

        if (this.orden != null) {
            resultado.setOrderBy(orden.getCampo());
            resultado.setAscendente(orden.getTipoOrden() != null && orden.getTipoOrden().equalsIgnoreCase("asc"));
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

    public Integer getTelematico() {
        return telematico;
    }

    public void setTelematico(Integer telematico) {
        this.telematico = telematico;
    }

    public Integer getDisponibleFuncionarioHabilitado() {
        return disponibleFuncionarioHabilitado;
    }

    public void setDisponibleFuncionarioHabilitado(Integer disponibleFuncionarioHabilitado) {
        this.disponibleFuncionarioHabilitado = disponibleFuncionarioHabilitado;
    }

    public Integer getDisponibleApoderadoHabilitado() {
        return disponibleApoderadoHabilitado;
    }

    public void setDisponibleApoderadoHabilitado(Integer disponibleApoderadoHabilitado) {
        this.disponibleApoderadoHabilitado = disponibleApoderadoHabilitado;
    }

    public Long getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(Long codigoMateria) {
        this.codigoMateria = codigoMateria;
    }
}