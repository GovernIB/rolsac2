package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.utils.AuditoriaUtil;
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
public class UnidadAdministrativaDTO extends ModelApi implements Cloneable {

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
     * Versión de la UA
     */
    private Integer version;

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

    private List<UnidadAdministrativaDTO> hijos;


    /**
     * Listado de temas asociados a la UA
     */
    private List<TemaGridDTO> temas;

    /**
     * Usuario que realiza lso cambios
     */
    private String usuarioAuditoria;

    /**
     * Instancia una nueva Unidad administrativa dto.
     */
    public UnidadAdministrativaDTO() {
    }

    public UnidadAdministrativaDTO(UnidadAdministrativaDTO otro) {
        this.codigo = otro.codigo;
        this.codigoDIR3 = otro.codigoDIR3;
        this.identificador = otro.identificador;
        this.abreviatura = otro.abreviatura;
        this.telefono = otro.telefono;
        this.fax = otro.fax;
        this.email = otro.email;
        this.dominio = otro.dominio;
        this.responsableEmail = otro.responsableEmail;
        this.responsableNombre = otro.responsableNombre;
        this.presentacion = otro.presentacion == null ? null : (Literal) otro.presentacion.clone();
        this.url = otro.url == null ? null : (Literal) otro.url.clone();
        this.responsable = otro.responsable == null ? null : (Literal) otro.responsable.clone();
        this.nombre = otro.nombre == null ? null : (Literal) otro.nombre.clone();
        this.tipo = otro.tipo == null ? null : (TipoUnidadAdministrativaDTO) otro.tipo.clone();
        this.responsableSexo = otro.responsableSexo == null ? null : (TipoSexoDTO) otro.responsableSexo.clone();
        this.temas = otro.temas;
        this.usuarioAuditoria = otro.usuarioAuditoria;
        this.hijos = otro.hijos;
        this.usuariosUnidadAdministrativa = otro.usuariosUnidadAdministrativa;
        this.orden = otro.orden;
        this.padre = otro.padre == null ? null : (UnidadAdministrativaDTO) otro.padre.clone();
    }

    /**
     * Instancia una nueva Unidad administrativa dto.
     *
     * @param iId      id
     * @param nombreCa nombre ca
     * @param nombreEs nombre es
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
     * @param id id
     */
    public UnidadAdministrativaDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Create instance unidad administrativa dto.
     *
     * @return unidad administrativa dto
     */
    public static UnidadAdministrativaDTO createInstance() {
        UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        ua.setNombre(Literal.createInstance());
        return ua;
    }

    /**
     * Convertir UnidadAdministrativaDTO en UnidadAdministrativaaGridDTO
     *
     * @return unidad administrativa grid dto
     */
    public UnidadAdministrativaGridDTO convertDTOtoGridDTO() {
        UnidadAdministrativaGridDTO unidadAdministrativa = new UnidadAdministrativaGridDTO();
        unidadAdministrativa.setCodigo(this.getCodigo());
        unidadAdministrativa.setNombre(this.getNombre());
        if (this.getTipo() != null) {
            unidadAdministrativa.setTipo(this.getTipo().getIdentificador());
        }
        if (this.getOrden() != null) {
            unidadAdministrativa.setOrden(this.getOrden());
        }
        if (this.getCodigoDIR3() != null) {
            unidadAdministrativa.setCodigoDIR3(this.getCodigoDIR3());
        }
        if (this.getPadre() != null) {
            unidadAdministrativa.setNombrePadre(this.getPadre().getNombre());
        }
        return unidadAdministrativa;
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return codigo
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
     * @param idString codigo to set
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
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene nombre.
     *
     * @return nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Obtiene padre.
     *
     * @return padre
     */
    public UnidadAdministrativaDTO getPadre() {
        return padre;
    }

    /**
     * Establece padre.
     *
     * @param padre padre
     */
    public void setPadre(UnidadAdministrativaDTO padre) {
        this.padre = padre;
    }

    /**
     * Obtiene orden.
     *
     * @return orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * Establece orden.
     *
     * @param orden orden
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Establece nombre.
     *
     * @param nombre nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene entidad.
     *
     * @return entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad entidad
     */
    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene tipo.
     *
     * @return tipo
     */
    public TipoUnidadAdministrativaDTO getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(TipoUnidadAdministrativaDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene abreviatura.
     *
     * @return abreviatura
     */
    public String getAbreviatura() {
        return abreviatura;
    }

    /**
     * Establece abreviatura.
     *
     * @param abreviatura abreviatura
     */
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    /**
     * Obtiene telefono.
     *
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece telefono.
     *
     * @param telefono telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene fax.
     *
     * @return fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Establece fax.
     *
     * @param fax fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Obtiene email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene dominio.
     *
     * @return dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * Establece dominio.
     *
     * @param dominio dominio
     */
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    /**
     * Obtiene responsable nombre.
     *
     * @return responsable nombre
     */
    public String getResponsableNombre() {
        return responsableNombre;
    }

    /**
     * Establece responsable nombre.
     *
     * @param responsableNombre responsable nombre
     */
    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    /**
     * Obtiene responsable sexo.
     *
     * @return responsable sexo
     */
    public TipoSexoDTO getResponsableSexo() {
        return responsableSexo;
    }

    /**
     * Establece responsable sexo.
     *
     * @param responsableSexo responsable sexo
     */
    public void setResponsableSexo(TipoSexoDTO responsableSexo) {
        this.responsableSexo = responsableSexo;
    }

    /**
     * Obtiene responsable email.
     *
     * @return responsable email
     */
    public String getResponsableEmail() {
        return responsableEmail;
    }

    /**
     * Establece responsable email.
     *
     * @param responsableEmail responsable email
     */
    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    /**
     * Obtiene presentacion.
     *
     * @return presentacion
     */
    public Literal getPresentacion() {
        return presentacion;
    }

    /**
     * Establece presentacion.
     *
     * @param presentacion presentacion
     */
    public void setPresentacion(Literal presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * Obtiene url.
     *
     * @return url
     */
    public Literal getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url url
     */
    public void setUrl(Literal url) {
        this.url = url;
    }

    /**
     * Obtiene responsable.
     *
     * @return responsable
     */
    public Literal getResponsable() {
        return responsable;
    }

    /**
     * Establece responsable.
     *
     * @param responsable responsable
     */
    public void setResponsable(Literal responsable) {
        this.responsable = responsable;
    }

    /**
     * Obtiene codigo dir 3.
     *
     * @return codigo dir 3
     */
    public String getCodigoDIR3() {
        return codigoDIR3;
    }

    /**
     * Establece codigo dir 3.
     *
     * @param codigoDIR3 codigo dir 3
     */
    public void setCodigoDIR3(String codigoDIR3) {
        this.codigoDIR3 = codigoDIR3;
    }

    /**
     * Obtiene usuarios unidad administrativa.
     *
     * @return usuarios unidad administrativa
     */
    public List<UsuarioGridDTO> getUsuariosUnidadAdministrativa() {
        return usuariosUnidadAdministrativa;
    }

    /**
     * Establece usuarios unidad administrativa.
     *
     * @param usuariosUnidadAdministrativa usuarios unidad administrativa
     */
    public void setUsuariosUnidadAdministrativa(List<UsuarioGridDTO> usuariosUnidadAdministrativa) {
        this.usuariosUnidadAdministrativa = usuariosUnidadAdministrativa;
    }

    public List<UnidadAdministrativaDTO> getHijos() {
        return hijos;
    }

    public void setHijos(List<UnidadAdministrativaDTO> hijos) {
        this.hijos = hijos;
    }

    /**
     * Obtiene temas asociados a la UA
     *
     * @return Listado de temas asociados
     */
    public List<TemaGridDTO> getTemas() {
        return temas;
    }

    /**
     * Establece temas asociados a la UA
     *
     * @param temas
     */
    public void setTemas(List<TemaGridDTO> temas) {
        this.temas = temas;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        UnidadAdministrativaDTO tipo = new UnidadAdministrativaDTO();
        tipo.setCodigo(this.codigo);
        tipo.setCodigoDIR3(this.codigoDIR3);
        tipo.setIdentificador(this.identificador);
        tipo.setAbreviatura(this.abreviatura);
        tipo.setTelefono(this.telefono);
        tipo.setFax(this.fax);
        tipo.setEmail(this.email);
        tipo.setDominio(this.dominio);
        tipo.setResponsableEmail(this.responsableEmail);
        tipo.setResponsableNombre(this.responsableNombre);
        tipo.setOrden(this.orden);
        tipo.setUsuarioAuditoria(this.usuarioAuditoria);
        tipo.setHijos(this.hijos);

        if (this.getPresentacion() != null) {
            tipo.setPresentacion((Literal) this.getPresentacion().clone());
        }
        if (this.getUrl() != null) {
            tipo.setUrl((Literal) this.getUrl().clone());
        }
        if (this.getResponsable() != null) {
            tipo.setResponsable((Literal) this.getResponsable().clone());
        }
        if (this.getNombre() != null) {
            tipo.setNombre((Literal) this.getNombre().clone());
        }

        if (this.getTipo() != null) {
            tipo.setTipo((TipoUnidadAdministrativaDTO) this.getTipo().clone());
        }
        if (this.getResponsableSexo() != null) {
            tipo.setResponsableSexo((TipoSexoDTO) this.getResponsableSexo().clone());
        }

        if (this.getTemas() != null) {
            List<TemaGridDTO> temas = new ArrayList<>();
            for (TemaGridDTO tema : this.getTemas()) {
                temas.add((TemaGridDTO) tema.clone());
            }
            tipo.setTemas(temas);

        }
        if (this.getUsuariosUnidadAdministrativa() != null) {
            List<UsuarioGridDTO> usuarios = new ArrayList<>();
            for (UsuarioGridDTO usuario : this.getUsuariosUnidadAdministrativa()) {
                usuarios.add((UsuarioGridDTO) usuario.clone());
            }
            tipo.setUsuariosUnidadAdministrativa(usuarios);

        }
        return tipo;
    }

    public boolean esRaiz() {
        return this.padre == null;
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
                "codigo=" + codigo +
                '}';
    }

    /**
     * Compare to int.
     *
     * @param ua ua
     * @return int
     */
    public int compareTo(final UnidadAdministrativaDTO ua) {
        if (ua == null)
            throw new NullPointerException("ua");

        return Long.compare(this.getCodigo(), ua.getCodigo());
    }

    public static List<AuditoriaCambio> auditar(UnidadAdministrativaDTO data, UnidadAdministrativaDTO dataOriginal) {

        List<AuditoriaCambio> cambios = new ArrayList<>();
        if (dataOriginal == null) {
            return cambios;
        }

        AuditoriaUtil.auditar(data.getCodigoDIR3(), dataOriginal.getCodigoDIR3(), cambios, "auditoria.uas.codigoDIR3");
        AuditoriaUtil.auditar(data.getAbreviatura(), dataOriginal.getAbreviatura(), cambios, "auditoria.uas.abreviatura");
        // AuditoriaUtil.auditar(data.getFechaActualizacion(), dataOriginal.getFechaActualizacion(), cambios, "auditoria.procedimiento.fechaActualizacion");
        //AuditoriaUtil.auditar(data.getResponsable(), dataOriginal.getResponsable(), cambios, "auditoria.uas.responsable");
        AuditoriaUtil.auditar(data.getIdentificador(), dataOriginal.getIdentificador(), cambios, "auditoria.uas.identificador");
        AuditoriaUtil.auditar(data.getTelefono(), dataOriginal.getTelefono(), cambios, "auditoria.uas.telefono");
        AuditoriaUtil.auditar(data.getFax(), dataOriginal.getFax(), cambios, "auditoria.uas.fax");
        AuditoriaUtil.auditar(data.getEmail(), dataOriginal.getEmail(), cambios, "auditoria.uas.email");
        AuditoriaUtil.auditar(data.getDominio(), dataOriginal.getDominio(), cambios, "auditoria.uas.dominio");
        AuditoriaUtil.auditar(data.getResponsableNombre(), dataOriginal.getResponsableNombre(), cambios, "auditoria.uas.responsableNombre");
        AuditoriaUtil.auditar(data.getResponsableEmail(), dataOriginal.getResponsableEmail(), cambios, "auditoria.uas.responsableEmail");

        //Literal
        AuditoriaUtil.auditar(data.getResponsable(), dataOriginal.getResponsable(), cambios, "auditoria.uas.responsable");
        AuditoriaUtil.auditar(data.getUrl(), dataOriginal.getUrl(), cambios, "auditoria.uas.url");
        AuditoriaUtil.auditar(data.getNombre(), dataOriginal.getNombre(), cambios, "auditoria.uas.nombre");
        AuditoriaUtil.auditar(data.getPresentacion(), dataOriginal.getPresentacion(), cambios, "auditoria.uas.presentacion");


        //Relaciones
        AuditoriaUtil.auditarUsuarios(data.getUsuariosUnidadAdministrativa(), dataOriginal.getUsuariosUnidadAdministrativa(), cambios, "auditoria.uas.usuarios");
        AuditoriaUtil.auditarTemas(data.getTemas(), dataOriginal.getTemas(), cambios, "auditoria.uas.temas");
        AuditoriaUtil.auditar(data.getResponsableSexo(), dataOriginal.getResponsableSexo(), cambios, "auditoria.uas.responsableSexo");
        AuditoriaUtil.auditar(data.getTipo(), dataOriginal.getTipo(), cambios, "auditoria.uas.tipo");

        return cambios;
    }

    /**
     * Cast super basico
     *
     * @return
     */
    public UnidadAdministrativaGridDTO getUAGrid() {
        UnidadAdministrativaGridDTO uaGrid = new UnidadAdministrativaGridDTO();
        uaGrid.setCodigo(this.getCodigo());
        if (uaGrid.getPadre() != null) {
            UnidadAdministrativaGridDTO uaGridPadre = new UnidadAdministrativaGridDTO();
            uaGridPadre.setCodigo(uaGrid.getPadre().getCodigo());
            uaGridPadre.setNombre(uaGrid.getPadre().getNombre());
            uaGrid.setPadre(uaGridPadre);
        }
        uaGrid.setNombre(this.getNombre());
        return uaGrid;
    }
}
