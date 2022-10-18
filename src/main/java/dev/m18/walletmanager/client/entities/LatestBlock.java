package dev.m18.walletmanager.client.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.ChainType;
import lombok.Data;

@Data
public class LatestBlock {
	
	@JsonProperty("chain_id")
	Long chainId;
	
	@JsonProperty("chain_type")
	ChainType chainType;
	
	@JsonProperty("chain_code")
	String chainCode;
	
	@JsonProperty("native_asset_name")
	String nativeAssetName;
	
	@JsonProperty("latest_block_number")
	Integer latestBlockNumber;
	
	@JsonProperty("latest_pending_block_number")
	Integer latesPendingBlockNumber;
	
}
