package dev.m18.walletmanager.client.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetAddressRequest {

	@JsonProperty("merchant_id")
    Long merchantId;
	
	@JsonProperty("chain_type")
    Integer chainType;
    
    @JsonProperty("chain_id")
    Long chainId;
    
    @JsonProperty("client_id")
    String clientId;
	
}
