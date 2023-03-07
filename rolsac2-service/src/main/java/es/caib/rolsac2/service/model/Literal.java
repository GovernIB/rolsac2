package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeIdiomaFijo;
import es.caib.rolsac2.service.model.types.TypeIdiomaOpcional;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Literal.
 *
 * @author indra
 */
@Schema(name = "Literal")
public class Literal implements Cloneable {

    /**
     * LOGGER
     **/
    private static final Logger LOG = LoggerFactory.getLogger(Literal.class);

    /**
     * Serial version UID.
     **/
    private static final long serialVersionUID = 1L;

    /**
     * Idioma.
     **/
    private List<Traduccion> trads;

    /**
     * Codigo.
     **/
    private Long codigo;

    /**
     * Crea una nueva instancia de Traducciones.
     */
    public Literal() {
        super();
    }

    public static Literal createInstance() {
        Literal literal = new Literal();
        List<Traduccion> trads = new ArrayList<>();
        List<String> idiomas = new ArrayList<>();
        for (TypeIdiomaFijo i : TypeIdiomaFijo.values()) {
            idiomas.add(i.toString());
        }
        for (TypeIdiomaOpcional i : TypeIdiomaOpcional.values()) {
            idiomas.add(i.toString());
        }
        for (String idioma : idiomas) {
            trads.add(new Traduccion(idioma, ""));
        }
        literal.setTraducciones(trads);
        return literal;
    }

    public static Literal createInstance(List<String> idiomas) {
        Literal literal = new Literal();
        List<Traduccion> trads = new ArrayList<>();

        for (String idioma : idiomas) {
            trads.add(new Traduccion(idioma, ""));
        }
        literal.setTraducciones(trads);
        return literal;
    }


    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        Literal literal = new Literal();
        literal.setCodigo(this.codigo);
        List<Traduccion> traducciones = new ArrayList<>();
        if (trads != null) {
            for (Traduccion trad : trads) {
                traducciones.add((Traduccion) trad.clone());
            }
        }
        literal.setTraducciones(traducciones);
        return literal;
    }


    /**
     * Para obtener una traducción
     *
     * @return
     */
    public String getTraduccion() {

        if (trads == null) {
            return "";
        }

        for (final Traduccion traduccion : trads) {
            if ("ca".equals(traduccion.getIdioma())) {
                return traduccion.getLiteral();
            }
        }

        return trads.get(0).getLiteral();
    }

    /**
     * Obtiene el valor de traduccion.
     *
     * @param idioma idioma
     * @return el valor de traduccion
     */
    public String getTraduccion(final String idioma) {

        if (trads == null || idioma == null) {
            return null;
        }

        for (final Traduccion traduccion : trads) {
            if (idioma.equals(traduccion.getIdioma())) {
                return traduccion.getLiteral();
            }
        }

        return null;
    }

    /**
     * Obtiene el valor de traduccion (con algún valor por defecto).
     *
     * @param idioma idioma
     * @return el valor de traduccion
     */
    public String getTraduccion(final String idioma, final List<String> idiomas) {

        // Si no hay traducciones devolver vacío
        if (trads == null || idioma == null || trads.isEmpty()) {
            return null;
        }

        // Solo se buscará si el idioma que se pida está en la lista de idiomas
        if (idiomas.contains(idioma)) {
            // Recorrer las traducciones buscando el idioma que se pide.
            for (final Traduccion traduccion : trads) {
                if (idioma.equals(traduccion.getIdioma())) {
                    return traduccion.getLiteral();
                }
            }
        }

        // Recorrer las traducciones buscando el idioma según lo definido en idiomas
        // (que viene por configuracion global)
        for (final String idi : idiomas) {
            final String trad = this.getTraduccion(idi);
            if (trad != null) {
                return trad;
            }
        }

        // Sino devolver cualquier traduccion.
        return trads.get(0).getLiteral();

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
    public void add(final Traduccion traduccion) {
        boolean encontrado = false;
        if (trads == null) {
            trads = new ArrayList<>();
        }
        for (final Traduccion trad : trads) {
            if (trad.getIdioma().equals(traduccion.getIdioma())) {
                encontrado = true;
                trad.setLiteral(traduccion.getLiteral());
            }
        }

        if (!encontrado) {
            trads.add(traduccion);
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
        for (final Traduccion traduccion : trads) {
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
        if (trads != null) {
            for (final Traduccion traduccion : trads) {
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
    public List<Traduccion> getTraducciones() {
        return trads;
    }

    /**
     * Establece el valor de traducciones.
     *
     * @param traducciones el nuevo valor de traducciones
     */
    public void setTraducciones(final List<Traduccion> traducciones) {
        this.trads = traducciones;
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

    /**
     * Devuelve true si están todos los idiomas rellenos.
     * False si está vacío o algún idioma no está relleno (todos los idiomas cuentan como obligatorios).
     *
     * @return
     */
    public boolean checkObligatorio() {
        if (this.getTraducciones().isEmpty()) {
            return false;
        }

        for (String idioma : Constantes.IDIOMAS) {
            String trad = this.getTraduccion(idioma);
            if (trad == null || trad.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Literal literal = (Literal) o;
        return Objects.equals(trads, literal.trads) && Objects.equals(codigo, literal.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trads, codigo);
    }

    /**
     * Comprueba si al menos tiene una traducción con contenido.
     *
     * @return
     */
    public boolean estaVacio() {

        if (this.getTraducciones() == null || this.getTraducciones().isEmpty()) {
            return true;
        }
        boolean vacio = true;
        for (Traduccion trad : this.getTraducciones()) {
            if (trad.getLiteral() != null && !trad.getLiteral().isEmpty()) {
                vacio = false;
                break;
            }
        }
        return vacio;
    }
}