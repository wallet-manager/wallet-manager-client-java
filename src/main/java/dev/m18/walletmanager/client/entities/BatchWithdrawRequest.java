package dev.m18.walletmanager.client.entities;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BatchWithdrawRequest {

	@JsonProperty("merchant_id")
    Long merchantId;
	
	@JsonProperty("chain_type")
    Integer chainType;
	
	@JsonProperty("chain_id")
    Long chainId;
	
	@JsonProperty("asset_name")
    String assetName;
	
    List<WithdrawOrder> orders;
	
	@JsonProperty("client_data")
    String clientData;
	
	
	@Data
	static public class WithdrawOrder{
		
		@JsonProperty("merchant_order_id")
	    String merchantOrderId;
		
	    BigInteger amount;
	    
	    Integer decimals;
	    
	    @JsonProperty("to_address")
	    String toAddress;
	    
	    @JsonProperty("to_wallet_tag")
	    String toWalletTag;
	}
}

