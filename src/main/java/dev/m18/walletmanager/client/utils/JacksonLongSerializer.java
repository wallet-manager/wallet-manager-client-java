package dev.m18.walletmanager.client.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonLongSerializer extends StdSerializer<Long> {

	private static final long serialVersionUID = -8440924257455428741L;

	public JacksonLongSerializer() {
	        this(null);
	    }

	public JacksonLongSerializer(Class<Long> t) {
	        super(t);
	    }

	@Override
	public void serialize(Long value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeString(value.toString());
	}
}
