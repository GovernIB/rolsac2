package es.caib.rolsac2.api.externa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
	private final ObjectMapper MAPPER;

	public ObjectMapperContextResolver() {
		MAPPER = new ObjectMapper();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateSerializer localDateSerializer = new LocalDateSerializer(dateTimeFormatter);
		LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(dateTimeFormatter);

		MAPPER.registerModule(new SimpleModule().addSerializer(localDateSerializer));
		MAPPER.registerModule(new SimpleModule().addDeserializer(LocalDate.class, localDateDeserializer));
		MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return MAPPER;
	}
}