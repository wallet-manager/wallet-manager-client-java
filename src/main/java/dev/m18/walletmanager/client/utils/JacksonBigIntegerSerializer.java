package dev.m18.walletmanager.client.utils;

import java.io.IOException;
import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonBigIntegerSerializer extends StdSerializer<BigInteger> {

	private static final long serialVersionUID = -8440924257455428741L;

	public JacksonBigIntegerSerializer() {
		this(null);
	}

	public JacksonBigIntegerSerializer(Class<BigInteger> t) {
		super(t);
	}

	@Override
	public void serialize(BigInteger value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeString(value.toString());
	}
}
