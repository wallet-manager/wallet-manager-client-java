package dev.m18.walletmanager.client.entities;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BatchSweepRequest {

	@JsonProperty("merchant_id")
    Long merchantId;
	
	@JsonProperty("merchant_order_id")
    String merchantOrderId;
    
	@JsonProperty("chain_type")
    Integer chainType;
    
	@JsonProperty("chain_id")
    Long chainId;
    
	@JsonProperty("asset_name")
    String assetName;
    
    BigInteger threshold;
    
    Integer decimals;
    
    @JsonProperty("gether_address")
    String getherAddress;
    
    @JsonProperty("invoker_address")
    String invokerAddress;
    
    @JsonProperty("client_data")
    String clientData;
    
    Boolean preview;
	
}
