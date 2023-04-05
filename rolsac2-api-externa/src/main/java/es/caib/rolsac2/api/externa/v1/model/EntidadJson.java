package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
			return objectMapper.writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
