package dev.m18.walletmanager.client.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.ChainType;
import lombok.Data;

@Data
public class GetDepositByAddressRequest {

	@JsonProperty("chain_type")
	ChainType chainType;
	
	@JsonProperty("chain_id")
	Long chainId;
	
    String address;
    
    @JsonProperty("asset_name")
    String assetName;
    
    Integer offset;
    
    Integer limit;
	
}
