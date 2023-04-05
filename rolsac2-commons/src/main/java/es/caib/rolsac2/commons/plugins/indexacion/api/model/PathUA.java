package es.caib.rolsac2.commons.plugins.indexacion.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Path Unidad Organica.
 *
 * @author Indra
 */
public class PathUA {

    /**
     * Path UO: jerarquia de codigos de UOs ascendientes incluyendo la UO.
     */
    private List<String> path = new ArrayList<>();

    /**
     * Busqueda tambien en descendientes.
     **/
    private boolean activaBusquedaDescendientes = true;

    /**
     * Obtiene path UO: jerarquia de codigos de UOs ascendientes incluyendo la UO.
     *
     * @return the path UO: jerarquia de codigos de UOs ascendientes incluyendo la
     * UO
     */
    public List<String> getPath() {
        return path;
    }

    /**
     * Establece path UO: jerarquia de codigos de UOs ascendientes incluyendo la UO.
     *
     * @param path the new path UO: jerarquia de codigos de UOs ascendientes
     *             incluyendo la UO
     */
    public void setPath(final List<String> path) {
        this.path = path;
    }

    /**
     * Devuelve el identificador del último UO del path.
     *
     * @return identificado uo
     */
    public String getUO() {
        return path.get(path.size() - 1);
    }

    /**
     * @return the activaBusquedaDescendientes
     */
    public boolean isActivaBusquedaDescendientes() {
        return activaBusquedaDescendientes;
    }

    /**
     * @param activaBusquedaDescendientes the activaBusquedaDescendientes to set
     */
    public void setActivaBusquedaDescendientes(final boolean activaBusquedaDescendientes) {
        this.activaBusquedaDescendientes = activaBusquedaDescendientes;
    }

    public PathUO convertirUOS() {
        PathUO pathUO = new PathUO();
        pathUO.setPath(this.path);
        return pathUO;
    }
}
