package dev.m18.walletmanager.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

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
