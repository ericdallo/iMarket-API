package br.com.imarket.configuration.json;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate>{
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		String dateAsString = value.format(formatter);
		gen.writeString(dateAsString);
	}
}
