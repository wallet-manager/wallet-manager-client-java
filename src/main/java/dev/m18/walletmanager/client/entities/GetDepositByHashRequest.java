package dev.m18.walletmanager.client.entities;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.ChainType;
import lombok.Data;

@Data
public class GetDepositByHashRequest {

	@JsonProperty("chain_type")
	Integer chainType;
	
	@JsonProperty("chain_id")
    BigInteger chainId;
	
	@JsonProperty("tx_hash")
    String txHash;
	
}
