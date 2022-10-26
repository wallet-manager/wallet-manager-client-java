package dev.m18.walletmanager.client.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetAddressResult {

	String address;
	
	@JsonProperty("wallet_tag")
	String walletTag;
	
	@JsonProperty("x_address")
	String xAddress;
	
}
