package dev.m18.walletmanager.client.utils;

import java.io.IOException;
import java.math.BigInteger;

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

	

	@Override
	public String expand(Object value) {
		try {
			return ObjectMapperUtils.getObjectMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.error("", e);
			return "";
		}
	}
	
}
