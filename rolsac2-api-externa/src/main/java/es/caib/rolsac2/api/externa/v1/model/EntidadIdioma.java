package es.caib.rolsac2.api.externa.v1.model;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.EntidadDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Dades d'un Entidad.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "EntidadIdioma", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_ENTIDADES)
public class EntidadIdioma extends EntidadBase<EntidadIdioma> {

    private static final Logger LOG = LoggerFactory.getLogger(EntidadIdioma.class);

    @Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
    private Long codigo;

    @Schema(description = "identificador", name = "identificador", type = SchemaType.STRING, required = false)
    private String identificador;

    @Schema(description = "idiomaDefectoRest", name = "idiomaDefectoRest", type = SchemaType.STRING, required = false)
    private String idiomaDefectoRest;

    public EntidadIdioma(EntidadDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
        super(nodo, urlBase, idioma, hateoasEnabled);
    }

    public EntidadIdioma() {
        super();
    }

    @Override
    public void generaLinks(String urlBase) {
    }

    @Override
    protected void addSetersInvalidos() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setId(Long codigo) {
        // TODO Auto-generated method stub

    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdiomaDefectoRest() {
        return idiomaDefectoRest;
    }

    public void setIdiomaDefectoRest(String idiomaDefectoRest) {
        this.idiomaDefectoRest = idiomaDefectoRest;
    }
}
