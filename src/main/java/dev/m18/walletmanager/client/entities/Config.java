package dev.m18.walletmanager.client.entities;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class Config {
	
	Long merchantId;
	Identity identity;
	ServerConfig serverConfig;
	ClientConfig clientConfig;


	public static Config read() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Config config = objectMapper.readValue(Config.class.getResourceAsStream("/config.json"), Config.class);
		return config;
	}
	
	public static void main(String... args) throws Exception{
		System.out.println(read());
	}

}
