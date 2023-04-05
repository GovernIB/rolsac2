package es.caib.rolsac2.commons.plugins.indexacion.api.model;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumAplicacionId;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Informacion a indexar.
 */
public class DataIndexacion {

    /**
     * Id elemento.
     * Clave única para el elemento dentro de su categoría.
     */
    private String elementoId;

    /**
     * Id elemento padre.
     */
    private String elementoIdPadre;

    /**
     * Tipo elemento.
     */
    private EnumCategoria categoria;

    /**
     * Id aplicacion.
     */
    private EnumAplicacionId aplicacionId;

    /**
     * Titulo.
     */
    private LiteralMultilang titulo;

    /**
     * Descripcion.
     */
    private LiteralMultilang descripcion;

    /**
     * Url acceso.
     */
    private LiteralMultilang url;

    /**
     * Tipo elemento padre.
     */
    private EnumCategoria categoriaPadre;

    /**
     * Descripcion padre.
     */
    private LiteralMultilang descripcionPadre;

    /**
     * Url acceso padre.
     */
    private LiteralMultilang urlPadre;

    /**
     * Fecha ultima actualizacion.
     */
    private Date fechaActualizacion;

    /**
     * Fecha publicacion.
     */
    private Date fechaPublicacion;

    /**
     * Fecha caducidad.
     */
    private Date fechaCaducidad;

    /**
     * Plazo inicio.
     */
    private Date fechaPlazoIni;

    /**
     * Plazo fin.
     */
    private Date FechaPlazoFin;

    /**
     * Url foto asociada.
     */
    private String urlFoto;

    /**
     * Extension (para archivos).
     */
    private LiteralMultilang extension;

    /**
     * Unidades orgánicas asociadas al elemento.
     */
    private List<PathUA> uos = new ArrayList<PathUA>();

    /**
     * Id familia procedimientos.
     */
    private String familiaId;

    /**
     * Id materia procedimientos.
     */
    private List<String> materiaId = new ArrayList<String>();

    /**
     * Id publico destino procedimientos.
     */
    private List<String> publicoId = new ArrayList<String>();

    /**
     * Indica si es telematico.
     */
    private Boolean telematico;

    /**
     * Tipo elemento raiz.
     */
    private EnumCategoria categoriaRaiz;

    /**
     * Elemento raiz para elementos con estructura en arbol.
     */
    private String elementoIdRaiz;

    /**
     * Indica si el contenido es interno o publico.
     */
    private Boolean interno;

    /**
     * Score, valor entre 0.00 y 1.00.
     **/
    private Float score;
    /**
     * Idiomas del contenido a indexar. Se verificará que existen los campos
     * obligatorios para los idiomas especificados.
     */
    private List<EnumIdiomas> idiomas;

    /**
     * Texto que será comparado en las búsquedas.
     */
    private LiteralMultilang searchText;

    /**
     * Texto que será comparado en las búsquedas pero con un peso menor.
     */
    private LiteralMultilang searchTextOptional;

    /**
     * Obtiene texto que será comparado en las búsquedas.
     *
     * @return the texto que será comparado en las búsquedas
     */
    public LiteralMultilang getSearchText() {
        return searchText;
    }

    /**
     * Establece texto que será comparado en las búsquedas.
     *
     * @param searchText the new texto que será comparado en las búsquedas
     */
    public void setSearchText(LiteralMultilang searchText) {
        this.searchText = searchText;
    }

    /**
     * Obtiene texto que será comparado en las búsquedas pero con un peso menor.
     *
     * @return the texto que será comparado en las búsquedas pero con un peso
     * menor
     */
    public LiteralMultilang getSearchTextOptional() {
        return searchTextOptional;
    }

    /**
     * Establece texto que será comparado en las búsquedas pero con un peso
     * menor.
     *
     * @param searchTextOptional the new texto que será comparado en las búsquedas pero con un
     *                           peso menor
     */
    public void setSearchTextOptional(LiteralMultilang searchTextOptional) {
        this.searchTextOptional = searchTextOptional;
    }

    /**
     * Obtiene idiomas del contenido a indexar.
     *
     * @return the idiomas del contenido a indexar
     */
    public List<EnumIdiomas> getIdiomas() {
        return idiomas;
    }

    /**
     * Establece idiomas del contenido a indexar.
     *
     * @param idiomas the new idiomas del contenido a indexar
     */
    public void setIdiomas(List<EnumIdiomas> idiomas) {
        this.idiomas = idiomas;
    }

    /**
     * Obtiene id elemento.
     *
     * @return id elemento
     */
    public String getElementoId() {
        return elementoId;
    }


    /**
     * Establece id elemento.
     *
     * @param elementoId id elemento
     */
    public void setElementoId(String elementoId) {
        this.elementoId = elementoId;
    }


    /**
     * Obtiene id aplicacion.
     *
     * @return id aplicacion
     */
    public EnumAplicacionId getAplicacionId() {
        return aplicacionId;
    }


    /**
     * Establece id aplicacion.
     *
     * @param aplicacionId id aplicacion
     */
    public void setAplicacionId(EnumAplicacionId aplicacionId) {
        this.aplicacionId = aplicacionId;
    }


    /**
     * Obtiene tipo elemento.
     *
     * @return tipo elemento
     */
    public EnumCategoria getCategoria() {
        return categoria;
    }


    /**
     * Establece tipo elemento.
     *
     * @param categoria tipo elemento
     */
    public void setCategoria(EnumCategoria categoria) {
        this.categoria = categoria;
    }


    /**
     * Obtiene titulo.
     *
     * @return titulo
     */
    public LiteralMultilang getTitulo() {
        return titulo;
    }


    /**
     * Establece titulo.
     *
     * @param titulo titulo
     */
    public void setTitulo(LiteralMultilang titulo) {
        this.titulo = titulo;
    }


    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public LiteralMultilang getDescripcion() {
        return descripcion;
    }


    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(LiteralMultilang descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Obtiene url acceso.
     *
     * @return url acceso
     */
    public LiteralMultilang getUrl() {
        return url;
    }


    /**
     * Establece url acceso.
     *
     * @param url url acceso
     */
    public void setUrl(LiteralMultilang url) {
        this.url = url;
    }


    /**
     * Obtiene tipo elemento padre.
     *
     * @return tipo elemento padre
     */
    public EnumCategoria getCategoriaPadre() {
        return categoriaPadre;
    }


    /**
     * Establece tipo elemento padre.
     *
     * @param categoriaPadre tipo elemento padre
     */
    public void setCategoriaPadre(EnumCategoria categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
    }


    /**
     * Obtiene descripcion padre.
     *
     * @return descripcion padre
     */
    public LiteralMultilang getDescripcionPadre() {
        return descripcionPadre;
    }


    /**
     * Establece descripcion padre.
     *
     * @param descripcionPadre descripcion padre
     */
    public void setDescripcionPadre(LiteralMultilang descripcionPadre) {
        this.descripcionPadre = descripcionPadre;
    }


    /**
     * Obtiene url acceso padre.
     *
     * @return url acceso padre
     */
    public LiteralMultilang getUrlPadre() {
        return urlPadre;
    }


    /**
     * Establece url acceso padre.
     *
     * @param urlPadre url acceso padre
     */
    public void setUrlPadre(LiteralMultilang urlPadre) {
        this.urlPadre = urlPadre;
    }


    /**
     * Obtiene fecha ultima actualizacion.
     *
     * @return fecha ultima actualizacion
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }


    /**
     * Establece fecha ultima actualizacion.
     *
     * @param fechaActualizacion fecha ultima actualizacion
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }


    /**
     * Obtiene fecha publicacion.
     *
     * @return fecha publicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }


    /**
     * Establece fecha publicacion.
     *
     * @param fechaPublicacion fecha publicacion
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }


    /**
     * Obtiene fecha caducidad.
     *
     * @return fecha caducidad
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }


    /**
     * Establece fecha caducidad.
     *
     * @param fechaCaducidad fecha caducidad
     */
    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }


    /**
     * Obtiene plazo inicio.
     *
     * @return plazo inicio
     */
    public Date getFechaPlazoIni() {
        return fechaPlazoIni;
    }

    /**
     * Establece plazo inicio.
     *
     * @param fechaPlazoIni plazo inicio
     */
    public void setFechaPlazoIni(Date fechaPlazoIni) {
        this.fechaPlazoIni = fechaPlazoIni;
    }


    /**
     * Gets the fecha plazo fin.
     *
     * @return the fecha plazo fin
     */
    public Date getFechaPlazoFin() {
        return FechaPlazoFin;
    }


    /**
     * Sets the fecha plazo fin.
     *
     * @param fechaPlazoFin the new fecha plazo fin
     */
    public void setFechaPlazoFin(Date fechaPlazoFin) {
        FechaPlazoFin = fechaPlazoFin;
    }


    /**
     * Obtiene url foto asociada.
     *
     * @return url foto asociada
     */
    public String getUrlFoto() {
        return urlFoto;
    }


    /**
     * Establece url foto asociada.
     *
     * @param urlFoto url foto asociada
     */
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }


    /**
     * Obtiene extension (para archivos).
     *
     * @return extension (para archivos)
     */
    public LiteralMultilang getExtension() {
        return extension;
    }


    /**
     * Establece extension (para archivos).
     *
     * @param extension extension (para archivos)
     */
    public void setExtension(LiteralMultilang extension) {
        this.extension = extension;
    }


    /**
     * Obtiene unidades orgánicas asociadas al elemento.
     *
     * @return unidades orgánicas asociadas al elemento
     */
    public List<PathUA> getUos() {
        return uos;
    }


    /**
     * Establece unidades orgánicas asociadas al elemento.
     *
     * @param uos unidades orgánicas asociadas al elemento
     */
    public void setUos(List<PathUA> uos) {
        this.uos = uos;
    }


    /**
     * Obtiene id familia procedimientos.
     *
     * @return id familia procedimientos
     */
    public String getFamiliaId() {
        return familiaId;
    }


    /**
     * Establece id familia procedimientos.
     *
     * @param familiaId id familia procedimientos
     */
    public void setFamiliaId(String familiaId) {
        this.familiaId = familiaId;
    }


    /**
     * Obtiene id materia procedimientos.
     *
     * @return id materia procedimientos
     */
    public List<String> getMateriaId() {
        return materiaId;
    }


    /**
     * Establece id materia procedimientos.
     *
     * @param materiaId id materia procedimientos
     */
    public void setMateriaId(List<String> materiaId) {
        this.materiaId = materiaId;
    }


    /**
     * Obtiene id publico destino procedimientos.
     *
     * @return id publico destino procedimientos
     */
    public List<String> getPublicoId() {
        return publicoId;
    }


    /**
     * Establece id publico destino procedimientos.
     *
     * @param publicoId id publico destino procedimientos
     */
    public void setPublicoId(List<String> publicoId) {
        this.publicoId = publicoId;
    }


    /**
     * Obtiene indica si es telematico.
     *
     * @return indica si es telematico
     */
    public Boolean getTelematico() {
        return telematico;
    }


    /**
     * Establece indica si es telematico.
     *
     * @param telematico indica si es telematico
     */
    public void setTelematico(Boolean telematico) {
        this.telematico = telematico;
    }


    /**
     * Elemento raiz.
     *
     * @return id Elemento raiz.
     */
    public String getElementoIdRaiz() {
        return elementoIdRaiz;
    }


    /**
     * Establece identificador elemento raiz para elementos que estan en una estructura arbol.
     *
     * @param elementoRaizId Identificador elemento raiz
     */
    public void setElementoIdRaiz(String elementoRaizId) {
        this.elementoIdRaiz = elementoRaizId;
    }


    /**
     * Comprueba interno.
     *
     * @return the boolean
     */
    public Boolean isInterno() {
        return interno;
    }


    /**
     * Establece indica si el contenido es interno o publico.
     *
     * @param interno indica si el contenido es interno o publico
     */
    public void setInterno(Boolean interno) {
        this.interno = interno;
    }


    /**
     * Obtiene el id del padre. Se introduce principalmente en documentos.
     *
     * @return the elementoIdPadre
     */
    public String getElementoIdPadre() {
        return elementoIdPadre;
    }


    /**
     * Setea el id del padre. Se introduce principalmente en documentos.
     *
     * @param elementoIdPadre the elementoIdPadre to set
     */
    public void setElementoIdPadre(String elementoIdPadre) {
        this.elementoIdPadre = elementoIdPadre;
    }


    /**
     * @return the categoriaRaiz
     */
    public EnumCategoria getCategoriaRaiz() {
        return categoriaRaiz;
    }


    /**
     * @param categoriaRaiz the categoriaIdRaiz to set
     */
    public void setCategoriaRaiz(EnumCategoria categoriaRaiz) {
        this.categoriaRaiz = categoriaRaiz;
    }


    /**
     * @return the score
     */
    public Float getScore() {
        return score;
    }


    /**
     * @param score the score to set
     */
    public void setScore(Float score) {
        this.score = score;
    }

}