package es.caib.rolsac2.service.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Dades d'una Tasa de procedimiento/tramite.
 */
public class TasaProcedimientoDTO extends ModelApi implements Cloneable {

    /** Codigo **/
    private Long codigo;

    /** Código temporal para poder tratar con el dato **/
    private String codigoString;

    /** Identificador / Código (obligatorio, multiidioma) */
    private Literal identificador;

    /** Descripción (opcional, multiidioma) */
    private Literal descripcion;

    /** Forma de pago (obligatorio, multiidioma) */
    private Literal formaPago;

    /** URL (opcional, multiidioma) */
    private Literal url;

    /** Procedimiento tramite asociado */
    private ProcedimientoTramiteDTO tramiteDTO;

    /**
     * Crea instancia de TasaProcedimientoDTO.
     *
     * @param idiomas idiomas
     * @return instancia nueva
     */
    public static TasaProcedimientoDTO createInstance(List<String> idiomas) {
        TasaProcedimientoDTO tasa = new TasaProcedimientoDTO();
        if (idiomas == null || idiomas.isEmpty()) {
            tasa.setIdentificador(Literal.createInstance());
            tasa.setDescripcion(Literal.createInstance());
            tasa.setFormaPago(Literal.createInstance());
            tasa.setUrl(Literal.createInstance());
        } else {
            tasa.setIdentificador(Literal.createInstance(idiomas));
            tasa.setDescripcion(Literal.createInstance(idiomas));
            tasa.setFormaPago(Literal.createInstance(idiomas));
            tasa.setUrl(Literal.createInstance(idiomas));
        }
        return tasa;
    }

    public TasaProcedimientoDTO() {
    }

    public TasaProcedimientoDTO(Long id) {
        this.codigo = id;
    }

    @Override
    public TasaProcedimientoDTO clone() {
        try {
            TasaProcedimientoDTO clon = (TasaProcedimientoDTO) super.clone();
            if (this.identificador != null) {
                clon.identificador = (Literal) this.identificador.clone();
            }
            if (this.descripcion != null) {
                clon.descripcion = (Literal) this.descripcion.clone();
            }
            if (this.formaPago != null) {
                clon.formaPago = (Literal) this.formaPago.clone();
            }
            if (this.url != null) {
                clon.url = (Literal) this.url.clone();
            }
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCodigoString() {
        return codigoString;
    }

    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }

    public Literal getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Literal identificador) {
        this.identificador = identificador;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public Literal getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(Literal formaPago) {
        this.formaPago = formaPago;
    }

    public Literal getUrl() {
        return url;
    }

    public void setUrl(Literal url) {
        this.url = url;
    }

    public ProcedimientoTramiteDTO getTramiteDTO() {
        return tramiteDTO;
    }

    public void setTramiteDTO(ProcedimientoTramiteDTO tramiteDTO) {
        this.tramiteDTO = tramiteDTO;
    }

    /**
     * Obtiene los idiomas que están en el DTO pero no en la lista de idiomas ya existentes en BBDD.
     *
     * @param idiomas idiomas ya existentes
     * @return idiomas sobrantes a crear
     */
    public List<String> getTraduccionesSobrantes(List<String> idiomas) {
        List<String> idiomasSobrantes = new ArrayList<>();
        if (this.getIdentificador() != null) {
            for (String idioma : this.getIdentificador().getIdiomas()) {
                if (!idiomas.contains(idioma) && !idiomasSobrantes.contains(idioma)) {
                    idiomasSobrantes.add(idioma);
                }
            }
        }
        if (this.getFormaPago() != null) {
            for (String idioma : this.getFormaPago().getIdiomas()) {
                if (!idiomas.contains(idioma) && !idiomasSobrantes.contains(idioma)) {
                    idiomasSobrantes.add(idioma);
                }
            }
        }
        return idiomasSobrantes;
    }
}
