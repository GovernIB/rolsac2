package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Dades de una Unidad Administrativa.
 *
 * @author Indra
 */
@Schema(name = "UnidadAdministrativa")
public class UnidadAdministrativaDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Entidad
     **/
    private EntidadDTO entidad;
    /**
     * Padre
     **/
    private UnidadAdministrativaDTO padre;
    /**
     * Código DIR3.
     **/
    private String codigoDIR3;
    /**
     * Tipo de UA
     **/
    private TipoUnidadAdministrativaDTO tipo;
    /**
     * Hijos.
     **/
    private List<UnidadAdministrativaDTO> hijos;
    /* Identificador funcional **/
    private String identificador;
    /**
     * Abreviatura entidad
     **/
    private String abreviatura;
    /**
     * Telefono
     **/
    private String telefono;
    /**
     * Fax
     **/
    private String fax;
    /**
     * Email
     **/
    private String email;
    /**
     * Dominio
     **/
    private String dominio;
    /**
     * Responsable INFO
     **/
    private String responsableNombre;
    private TipoSexoDTO responsableSexo;
    private String responsableEmail;
    /**
     * Orden
     **/
    private Integer orden;

    /**
     * Nombre
     */
    private Literal nombre;

    /**
     * Presentacion
     */
    private Literal presentacion;

    /**
     * Url
     */
    private Literal url;

    /**
     * Responsable
     */
    private Literal responsable;

    /**
     * Usuarios de la unidad administrativa
     */
    private List<UsuarioGridDTO> usuariosUnidadAdministrativa;


    /**
     * Listado de temas asociados a la UA
     */
    private List<TemaGridDTO> temas;

    /**
     * Instancia una nueva Unidad administrativa dto.
     */
    public UnidadAdministrativaDTO() {
    }

    /**
     * Instancia una nueva Unidad administrativa dto.
     *
     * @param iId       id
     * @param nombreCa  nombre ca
     * @param nombreEs  nombre es
     */
    public UnidadAdministrativaDTO(Long iId, String nombreCa, String nombreEs) {
        // TODO Borrar
        this.codigo = iId;
        this.nombre = new Literal();
        List<Traduccion> traducciones = new ArrayList<>();
        traducciones.add(new Traduccion("es", nombreEs));
        traducciones.add(new Traduccion("ca", nombreCa));
        this.nombre.setTraducciones(traducciones);
    }

    /**
     * Instantiates a new Unidad administrativa dto.
     *
     * @param id  id
     */
    public UnidadAdministrativaDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Create instance unidad administrativa dto.
     *
     * @return  unidad administrativa dto
     */
    public static UnidadAdministrativaDTO createInstance() {
        UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        ua.setNombre(Literal.createInstance());
        return ua;
    }

    /**
     * Convertir UnidadAdministrativaDTO en UnidadAdministrativaaGridDTO
     *
     * @return  unidad administrativa grid dto
     */
    public UnidadAdministrativaGridDTO convertDTOtoGridDTO() {
        UnidadAdministrativaGridDTO unidadAdministrativa = new UnidadAdministrativaGridDTO();
        unidadAdministrativa.setCodigo(this.getCodigo());
        unidadAdministrativa.setNombre(this.getNombre());
        if(this.getTipo() != null) {
            unidadAdministrativa.setTipo(this.getTipo().getIdentificador());
        }
        if(this.getOrden() != null) {
            unidadAdministrativa.setOrden(this.getOrden());
        }
        if(this.getCodigoDIR3() != null) {
            unidadAdministrativa.setCodigoDIR3(this.getCodigoDIR3());
        }
        if(this.getPadre() != null) {
            unidadAdministrativa.setNombrePadre(this.getPadre().getNombre());
        }
        return unidadAdministrativa;
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return  codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Obtiene padre.
     *
     * @return  padre
     */
    public UnidadAdministrativaDTO getPadre() {
        return padre;
    }

    /**
     * Establece padre.
     *
     * @param padre  padre
     */
    public void setPadre(UnidadAdministrativaDTO padre) {
        this.padre = padre;
    }

    /**
     * Obtiene hijos.
     *
     * @return  hijos
     */
    public List<UnidadAdministrativaDTO> getHijos() {
        return hijos;
    }

    /**
     * Establece hijos.
     *
     * @param hijos  hijos
     */
    public void setHijos(List<UnidadAdministrativaDTO> hijos) {
        this.hijos = hijos;
    }

    /**
     * Obtiene orden.
     *
     * @return  orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * Establece orden.
     *
     * @param orden  orden
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public TipoUnidadAdministrativaDTO getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo  tipo
     */
    public void setTipo(TipoUnidadAdministrativaDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene abreviatura.
     *
     * @return  abreviatura
     */
    public String getAbreviatura() {
        return abreviatura;
    }

    /**
     * Establece abreviatura.
     *
     * @param abreviatura  abreviatura
     */
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    /**
     * Obtiene telefono.
     *
     * @return  telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece telefono.
     *
     * @param telefono  telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene fax.
     *
     * @return  fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Establece fax.
     *
     * @param fax  fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Obtiene email.
     *
     * @return  email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece email.
     *
     * @param email  email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene dominio.
     *
     * @return  dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * Establece dominio.
     *
     * @param dominio  dominio
     */
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    /**
     * Obtiene responsable nombre.
     *
     * @return  responsable nombre
     */
    public String getResponsableNombre() {
        return responsableNombre;
    }

    /**
     * Establece responsable nombre.
     *
     * @param responsableNombre  responsable nombre
     */
    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    /**
     * Obtiene responsable sexo.
     *
     * @return  responsable sexo
     */
    public TipoSexoDTO getResponsableSexo() {
        return responsableSexo;
    }

    /**
     * Establece responsable sexo.
     *
     * @param responsableSexo  responsable sexo
     */
    public void setResponsableSexo(TipoSexoDTO responsableSexo) {
        this.responsableSexo = responsableSexo;
    }

    /**
     * Obtiene responsable email.
     *
     * @return  responsable email
     */
    public String getResponsableEmail() {
        return responsableEmail;
    }

    /**
     * Establece responsable email.
     *
     * @param responsableEmail  responsable email
     */
    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    /**
     * Obtiene presentacion.
     *
     * @return  presentacion
     */
    public Literal getPresentacion() {
        return presentacion;
    }

    /**
     * Establece presentacion.
     *
     * @param presentacion  presentacion
     */
    public void setPresentacion(Literal presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * Obtiene url.
     *
     * @return  url
     */
    public Literal getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url  url
     */
    public void setUrl(Literal url) {
        this.url = url;
    }

    /**
     * Obtiene responsable.
     *
     * @return  responsable
     */
    public Literal getResponsable() {
        return responsable;
    }

    /**
     * Establece responsable.
     *
     * @param responsable  responsable
     */
    public void setResponsable(Literal responsable) {
        this.responsable = responsable;
    }

    /**
     * Obtiene codigo dir 3.
     *
     * @return  codigo dir 3
     */
    public String getCodigoDIR3() {
        return codigoDIR3;
    }

    /**
     * Establece codigo dir 3.
     *
     * @param codigoDIR3  codigo dir 3
     */
    public void setCodigoDIR3(String codigoDIR3) {
        this.codigoDIR3 = codigoDIR3;
    }

    /**
     * Obtiene usuarios unidad administrativa.
     *
     * @return  usuarios unidad administrativa
     */
    public List<UsuarioGridDTO> getUsuariosUnidadAdministrativa() { return usuariosUnidadAdministrativa; }

    /**
     * Establece usuarios unidad administrativa.
     *
     * @param usuariosUnidadAdministrativa  usuarios unidad administrativa
     */
    public void setUsuariosUnidadAdministrativa(List<UsuarioGridDTO> usuariosUnidadAdministrativa) {  this.usuariosUnidadAdministrativa = usuariosUnidadAdministrativa; }

    /**
     * Obtiene temas asociados a la UA
     * @return Listado de temas asociados
     */
    public List<TemaGridDTO> getTemas() {
        return temas;
    }

    /**
     * Establece temas asociados a la UA
     * @param temas
     */
    public void setTemas(List<TemaGridDTO> temas) {
        this.temas = temas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadAdministrativaDTO that = (UnidadAdministrativaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "UnidadAdministrativaDTO{" +
                "id=" + codigo +
                '}';
    }

    /**
     * Compare to int.
     *
     * @param ua  ua
     * @return  int
     */
    public int compareTo(final UnidadAdministrativaDTO ua) {
        if (ua == null)
            throw new NullPointerException("ua");

        return Long.compare(this.getOrden(), ua.getOrden());
    }
}
