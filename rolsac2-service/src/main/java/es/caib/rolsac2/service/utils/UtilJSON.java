package es.caib.rolsac2.service.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.caib.rolsac2.service.exception.FrontException;
import es.caib.rolsac2.service.model.Mensaje;

import javax.json.JsonException;
import java.io.IOException;
import java.util.List;

// TODO V0 Se ha creado JSONUtil en commons, cambiar para usarla

/**
 * Utilidades conversion JSON.
 *
 * @author Indra
 */
public class UtilJSON {

    /**
     * Constructor privado para evitar problema.
     */
    private UtilJSON() {
        // not called
    }

    /**
     * Clase para convertir un objeto a JSON.
     *
     * @param objeto
     * @return
     * @throws JsonProcessingException
     */
    public static String toJSON(final Object objeto) {
        try {
            String res = null;
            if (objeto != null) {
                final ObjectMapper mapper = new ObjectMapper();
                res = mapper.writeValueAsString(objeto);
            }
            return res;
        } catch (final JsonProcessingException ex) {
            throw new FrontException("Excepcion convirtiendo a JSON", ex);
        }
    }

    /**
     * Clase para convertir de json a objeto.
     *
     * @param json
     * @param clase
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object fromJSON(final String json, final Class<?> clase) {
        try {
            Object res = null;
            if (json != null) {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                res = mapper.readValue(json, clase);
            }
            return res;
        } catch (final IOException ex) {
            throw new FrontException("Excepcion convirtiendo desde JSON", ex);
        }
    }

    /**
     * Clase para convertir de json a objeto.
     *
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static List<Mensaje> getMensaje(final String json) {
        try {
            List<Mensaje> res = null;
            if (json != null) {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                res = mapper.readValue(json, new TypeReference<List<Mensaje>>() {
                });
            }
            return res;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clase para convertir de json a objeto.
     *
     * @param json
     * @param claseLista
     * @return
     */
    public static List<?> fromListJSON(final String json, final Class<?> claseLista) {
        try {
            List<?> res = null;
            if (json != null) {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                res = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, claseLista));
            }
            return res;
        } catch (final IOException e) {
            throw new JsonException(e.toString());
        }
    }

}
