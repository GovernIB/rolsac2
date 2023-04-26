package es.caib.rolsac2.api.externa.v1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class EntidadJson<V> {

	private static final Logger LOG = LoggerFactory.getLogger(EntidadBase.class);

	public EntidadJson() {
	}

	public V valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<V> typeRef = new TypeReference<V>() {
		};
		V obj;
		try {
			obj = (V) objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return obj;
	}

	public String toJson() {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return objectMapper.writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
