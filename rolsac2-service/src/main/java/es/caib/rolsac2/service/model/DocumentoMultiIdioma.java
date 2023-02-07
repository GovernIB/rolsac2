package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeIdiomaFijo;
import es.caib.rolsac2.service.model.types.TypeIdiomaOpcional;

import java.util.ArrayList;
import java.util.List;

/**
 * Documento Multi Idioma.
 *
 * @author indra
 */

public class DocumentoMultiIdioma extends ModelApi implements Cloneable {

    /**
     * Serial version UID.
     **/
    private static final long serialVersionUID = 1L;

    /**
     * Idioma.
     **/
    private List<DocumentoTraduccion> traducciones;

    /**
     * Codigo.
     **/
    private Long codigo;


    /**
     * Crea una nueva instancia de Traducciones.
     */
    public DocumentoMultiIdioma() {
        super();
    }

    public Object clone() {
        DocumentoMultiIdioma obj = new DocumentoMultiIdioma();
        obj.setCodigo(this.codigo);
        List<DocumentoTraduccion> trads = new ArrayList<>();
        if (traducciones != null) {
            for (DocumentoTraduccion trad : traducciones) {
                trads.add((DocumentoTraduccion) trad.clone());
            }
        }
        obj.setTraducciones(trads);
        return obj;

    }

    public static DocumentoMultiIdioma createInstance() {
        DocumentoMultiIdioma docMulti = new DocumentoMultiIdioma();
        List<DocumentoTraduccion> trads = new ArrayList<>();
        List<String> idiomas = new ArrayList<>();
        for (TypeIdiomaFijo i : TypeIdiomaFijo.values()) {
            idiomas.add(i.toString());
        }
        for (TypeIdiomaOpcional i : TypeIdiomaOpcional.values()) {
            idiomas.add(i.toString());
        }
        for (String idioma : idiomas) {
            trads.add(new DocumentoTraduccion(idioma, null));
        }
        docMulti.setTraducciones(trads);
        return docMulti;
    }

    public static DocumentoMultiIdioma createInstance(List<String> idiomas) {
        DocumentoMultiIdioma literal = new DocumentoMultiIdioma();
        List<DocumentoTraduccion> trads = new ArrayList<>();

        for (String idioma : idiomas) {
            trads.add(new DocumentoTraduccion(idioma, null));
        }
        literal.setTraducciones(trads);
        return literal;
    }


    /**
     * Para obtener un documento relacionado a un idioma
     *
     * @return
     */
    /*public FicheroDTO getTraduccion() {

        if (traducciones == null) {
            return null;
        }

        for (final DocumentoTraduccion traduccion : traducciones) {
            if ("ca".equals(traduccion.getIdioma())) {
                return traduccion.getFicheroDTO();
            }
        }

        return traducciones.get(0).getFicheroDTO();
    }*/

    /**
     * Obtiene el valor de un fichero DTO.
     *
     * @param idioma idioma
     * @return el valor del ficheroDTO
     */
    public FicheroDTO getTraduccion(String idioma) {

        if (traducciones == null || idioma == null) {
            return null;
        }

        for (final DocumentoTraduccion traduccion : traducciones) {
            if (idioma.equals(traduccion.getIdioma())) {
                return traduccion.getFicheroDTO();
            }
        }

        return null;
    }

    /**
     * Obtiene el valor de un fichero DTO.
     *
     * @param idioma idioma
     * @return el valor del ficheroDTO
     */
    public DocumentoTraduccion getDocumentoTraduccion(String idioma) {

        if (traducciones == null || idioma == null) {
            return null;
        }

        for (final DocumentoTraduccion traduccion : traducciones) {
            if (idioma.equals(traduccion.getIdioma())) {
                return traduccion;
            }
        }

        return null;
    }

    /**
     * Obtiene el valor de ficheroDTO (con algún valor por defecto).
     *
     * @param idioma idioma
     * @return el valor de ficheroDTO
     */
    public FicheroDTO getTraduccion(final String idioma, final List<String> idiomas) {

        // Si no hay traducciones devolver vacío
        if (traducciones == null || idioma == null || traducciones.isEmpty()) {
            return null;
        }

        // Solo se buscará si el idioma que se pida está en la lista de idiomas
        if (idiomas.contains(idioma)) {
            // Recorrer las traducciones buscando el idioma que se pide.
            for (final DocumentoTraduccion traduccion : traducciones) {
                if (idioma.equals(traduccion.getIdioma())) {
                    return traduccion.getFicheroDTO();
                }
            }
        }

        // Recorrer las traducciones buscando el idioma según lo definido en idiomas
        // (que viene por configuracion global)
        for (final String idi : idiomas) {
            final FicheroDTO trad = this.getTraduccion(idi);
            if (trad != null) {
                return trad;
            }
        }

        // Sino devolver cualquier traduccion.
        return traducciones.get(0).getFicheroDTO();

    }

    /**
     * Incluye una traducción, si la traducción:
     * <ul>
     * <li>No existe, añade la traducción.</li>
     * <li>Sí existe, actualiza el texto.</li>
     * </ul>
     *
     * @param traduccion traduccion
     */
    public void add(final DocumentoTraduccion traduccion) {
        boolean encontrado = false;
        if (traducciones == null) {
            traducciones = new ArrayList<>();
        }
        for (final DocumentoTraduccion trad : traducciones) {
            if (trad.getIdioma().equals(traduccion.getIdioma())) {
                encontrado = true;
                trad.setFicheroDTO(traduccion.getFicheroDTO());
            }
        }

        if (!encontrado) {
            traducciones.add(traduccion);
        }
    }

    /**
     * Comprueba si esta un idioma en concreto.
     *
     * @param idioma idioma
     * @return true si existe
     */
    public boolean contains(final String idioma) {
        boolean existe = false;
        for (final DocumentoTraduccion traduccion : traducciones) {
            if (idioma.equals(traduccion.getIdioma())) {
                existe = true;
            }
        }

        return existe;
    }

    /**
     * Obtiene el valor de idiomas.
     *
     * @return el valor de idiomas
     */
    public List<String> getIdiomas() {
        final List<String> idiomas = new ArrayList<>();
        if (traducciones != null) {
            for (final DocumentoTraduccion traduccion : traducciones) {
                idiomas.add(traduccion.getIdioma());
            }
        }
        return idiomas;
    }

    /**
     * Obtiene el valor de traducciones.
     *
     * @return el valor de traducciones
     */
    public List<DocumentoTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece el valor de traducciones.
     *
     * @param traducciones el nuevo valor de traducciones
     */
    public void setTraducciones(final List<DocumentoTraduccion> traducciones) {
        this.traducciones = traducciones;
    }

    /**
     * Obtiene el valor de codigo.
     *
     * @return el valor de codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece el valor de codigo.
     *
     * @param codigo el nuevo valor de codigo
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

}