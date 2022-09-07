package dev.m18.walletmanager.client.entities.callback;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.ChainType;
import dev.m18.walletmanager.client.enums.TransactionStatus;
import lombok.Data;

@Data
public class DepositStatusCallback {

	
	Long id;
	
	@JsonProperty("merchant_id")
	Long merchantId;
	
	@JsonProperty("chain_type")
	ChainType chainType;
	
	@JsonProperty("chain_id")
	Long chainId;
	
	@JsonProperty("client_id")
	String clientId;
	
	@JsonProperty("wallet_address")
	String walletAddress;
	
	@JsonProperty("from_address")
	String fromAddress;
	
	@JsonProperty("asset_name")
	String assetName;
	
	BigInteger amount;
	
	Integer decimals;
	
	TransactionStatus status;
	
	@JsonProperty("updated_time")
	Date updatedTime;
	
	@JsonProperty("block_number")
	Date blockNumber;
	
	@JsonProperty("block_hash")
	String blockHash;
	
	@JsonProperty("block_time")
	Date blockTime;
	
	@JsonProperty("tx_hash")
	String txHash;

}
