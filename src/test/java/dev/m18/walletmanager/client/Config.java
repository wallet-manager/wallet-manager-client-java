package dev.m18.walletmanager.client;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class Config {
	
	Identity identity;
	ServerConfig serverConfig;
	ClientConfig clientConfig;
	

	@Data
	public class Identity {
		String privateKey;
		String publicKey;
		String address;
	}

	@Data
	public class ServerConfig {
		int serverPort;
		int messageExpiredInMs;

	}

	@Data
	public class ClientConfig {
		int instanceId;
		String baseURL;
		boolean contentTypeJson;
	}

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
