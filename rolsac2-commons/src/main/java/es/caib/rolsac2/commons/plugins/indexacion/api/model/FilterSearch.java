package es.caib.rolsac2.commons.plugins.indexacion.api.model;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumAplicacionId;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtro busquedas.
 *
 * @author Indra
 */
public class FilterSearch {

    /**
     * Lista de aplicaciones (si no se especifica, buscara en todas),.
     */
    private List<EnumAplicacionId> aplicaciones = new ArrayList<EnumAplicacionId>();

    /**
     * Filtro Unidad Organica. Buscara sobre la unidad y descendientes.
     */
    private PathUO uo;

    /**
     * Filtro Unidades Organicas. Buscara sobre la unidad (si tiene activo el check,
     * también mirará descendientes) de cada elemento del listado.
     */
    private List<PathUO> uos;

    /**
     * Id familia procedimientos.
     */
    private List<String> familiaId = new ArrayList<String>();

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
     * Id raiz (para elementos que tengan estructura arbol: microsite,
     * procedimiento).
     */
    private String elementoRaizId;

    /**
     * Categoria raiz (para elementos que tengan estructura arbol: microsite,
     * procedimiento).
     */
    private EnumCategoria elementoRaizCategoria;

    /**
     * Indica si el contenido es interno o publico. Por defecto solo publico.
     */
    private boolean interno = false;

    /**
     * Categorias.
     */
    private List<EnumCategoria> categorias = new ArrayList<EnumCategoria>();

    /**
     * Vigente.
     */
    private boolean vigente = false;

    /**
     * Fecha publicacion ini.
     **/
    private String fechaPublicacionIni;

    /**
     * Fecha publicacion fin.
     **/
    private String fechaPublicacionFin;

    /**
     * Indica si se agrupa por elemento raiz.
     */
    private boolean agruparRaiz = false;

    /**
     * Obtiene lista de aplicaciones (si no se especifica, buscara en todas),.
     *
     * @return the lista de aplicaciones (si no se especifica, buscara en todas),
     */
    public List<EnumAplicacionId> getAplicaciones() {
        return aplicaciones;
    }

    /**
     * Establece lista de aplicaciones (si no se especifica, buscara en todas),.
     *
     * @param aplicaciones the new lista de aplicaciones (si no se especifica, buscara en
     *                     todas),
     */
    public void setAplicaciones(final List<EnumAplicacionId> aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    /**
     * Obtiene filtro Unidad Organica.
     *
     * @return the filtro Unidad Organica
     */
    public PathUO getUo() {
        return uo;
    }

    /**
     * Establece filtro Unidad Organica.
     *
     * @param uo the new filtro Unidad Organica
     */
    public void setUo(final PathUO uo) {
        this.uo = uo;
    }

    /**
     * @return the uos
     */
    public List<PathUO> getUos() {
        return uos;
    }

    /**
     * @param uos the uos to set
     */
    public void setUos(final List<PathUO> uos) {
        this.uos = uos;
    }

    /**
     * Obtiene id familia procedimientos.
     *
     * @return the id familia procedimientos
     */
    public List<String> getFamiliaId() {
        return familiaId;
    }

    /**
     * Establece id familia procedimientos.
     *
     * @param familiaId the new id familia procedimientos
     */
    public void setFamiliaId(final List<String> familiaId) {
        this.familiaId = familiaId;
    }

    /**
     * Obtiene id materia procedimientos.
     *
     * @return the id materia procedimientos
     */
    public List<String> getMateriaId() {
        return materiaId;
    }

    /**
     * Establece id materia procedimientos.
     *
     * @param materiaId the new id materia procedimientos
     */
    public void setMateriaId(final List<String> materiaId) {
        this.materiaId = materiaId;
    }

    /**
     * Obtiene id publico destino procedimientos.
     *
     * @return the id publico destino procedimientos
     */
    public List<String> getPublicoId() {
        return publicoId;
    }

    /**
     * Establece id publico destino procedimientos.
     *
     * @param publicoId the new id publico destino procedimientos
     */
    public void setPublicoId(final List<String> publicoId) {
        this.publicoId = publicoId;
    }

    /**
     * Obtiene indica si es telematico.
     *
     * @return the indica si es telematico
     */
    public Boolean getTelematico() {
        return telematico;
    }

    /**
     * Establece indica si es telematico.
     *
     * @param telematico the new indica si es telematico
     */
    public void setTelematico(final Boolean telematico) {
        this.telematico = telematico;
    }

    /**
     * Obtiene id microsite al que pertenece la informacion (para elementos GUSITE).
     *
     * @return the id microsite al que pertenece la informacion (para elementos
     * GUSITE)
     */
    public String getElementoRaizId() {
        return elementoRaizId;
    }

    /**
     * Establece elemento raiz.
     *
     * @param id id
     */
    public void setElementoRaizId(final String id) {
        this.elementoRaizId = id;
    }

    /**
     * Comprueba indica si el contenido es interno o publico.
     *
     * @return the indica si el contenido es interno o publico
     */
    public boolean isInterno() {
        return interno;
    }

    /**
     * Establece indica si el contenido es interno o publico.
     *
     * @param interno the new indica si el contenido es interno o publico
     */
    public void setInterno(final boolean interno) {
        this.interno = interno;
    }

    /**
     * Obtiene categorias.
     *
     * @return the categorias
     */
    public List<EnumCategoria> getCategorias() {
        return categorias;
    }

    /**
     * Establece categorias.
     *
     * @param categorias the new categorias
     */
    public void setCategorias(final List<EnumCategoria> categorias) {
        this.categorias = categorias;
    }

    /**
     * Comprueba vigente.
     *
     * @return the vigente
     */
    public boolean isVigente() {
        return vigente;
    }

    /**
     * Establece vigente.
     *
     * @param vigente the new vigente
     */
    public void setVigente(final boolean vigente) {
        this.vigente = vigente;
    }

    /**
     * @return the fechaPublicacionIni
     */
    public String getFechaPublicacionIni() {
        return fechaPublicacionIni;
    }

    /**
     * @param fechaPublicacionIni the fechaPublicacionIni to set
     */
    public void setFechaPublicacionIni(final String fechaPublicacionIni) {
        this.fechaPublicacionIni = fechaPublicacionIni;
    }

    /**
     * @return the fechaPublicacionFin
     */
    public String getFechaPublicacionFin() {
        return fechaPublicacionFin;
    }

    /**
     * @param fechaPublicacionFin the fechaPublicacionFin to set
     */
    public void setFechaPublicacionFin(final String fechaPublicacionFin) {
        this.fechaPublicacionFin = fechaPublicacionFin;
    }

    /**
     * @return the agruparRaiz
     */
    public boolean isAgruparRaiz() {
        return agruparRaiz;
    }

    /**
     * @param agruparRaiz the agruparRaiz to set
     */
    public void setAgruparRaiz(final boolean agruparRaiz) {
        this.agruparRaiz = agruparRaiz;
    }

    /**
     * @return the elementoRaizCategoria
     */
    public EnumCategoria getElementoRaizCategoria() {
        return elementoRaizCategoria;
    }

    /**
     * @param elementoRaizCategoria the elementoRaizCategoria to set
     */
    public void setElementoRaizCategoria(final EnumCategoria elementoRaizCategoria) {
        this.elementoRaizCategoria = elementoRaizCategoria;
    }

}
