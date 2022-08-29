package dev.m18.walletmanager.client.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import dev.m18.walletmanager.client.enums.ChainType;
import dev.m18.walletmanager.client.enums.IntEnum;
import feign.Param.Expander;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonExpander implements Expander{

	protected static ObjectMapper objectMapper = null;
	
	private static ObjectMapper getObjectMapper() {
		
		if(objectMapper == null) {
			objectMapper = new ObjectMapper()
					.setSerializationInclusion(Include.NON_NULL)
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			SimpleModule module = new SimpleModule();

			// Long
			module.addSerializer(Long.class, new JsonSerializer<Long>() {

				@Override
				public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeString(value.toString());
				}

			});

			module.addDeserializer(Long.class, new JsonDeserializer<Long>() {

				@Override
				public Long deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return Long.parseLong(p.getValueAsString());
				}
			});			
			
			// BigInteger
			
			module.addSerializer(BigInteger.class, new JsonSerializer<BigInteger>() {

				@Override
				public void serialize(BigInteger value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeString(value.toString());
				}

			});

			module.addDeserializer(BigInteger.class, new JsonDeserializer<BigInteger>() {

				@Override
				public BigInteger deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return new BigInteger(p.getValueAsString());
				}
			});	
			
			// Enum
			
			module.addSerializer(IntEnum.class, new JsonSerializer<IntEnum>() {

				@Override
				public void serialize(IntEnum value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeNumber(value.getIntVal());
				}

			});

			module.addDeserializer(ChainType.class, new JsonDeserializer<ChainType>() {

				@Override
				public ChainType deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return ChainType.getByValue(p.getValueAsInt());
				}
			});
			
			objectMapper.registerModule(module);			
		}

		
		return objectMapper;
	}

	@Override
	public String expand(Object value) {
		try {
			return getObjectMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.error("", e);
			return "";
		}
	}
	
}
