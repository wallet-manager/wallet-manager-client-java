package dev.m18.walletmanager.client.entities.callback;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.TransactionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DepositStatusCallback implements Callback{
	
	public final static String DEPOSIT_STATUS = "deposit_status";
	
	
	DepositStatusCallbackData data;
	
	DepositStatusCallback() {
		
	}
	

	@Override
	public String getType() {
		return DEPOSIT_STATUS;
	}
	
	@Data
	public static class DepositStatusCallbackData{
		
		Long id;

		@JsonProperty("merchant_id")
		Long merchantId;

		@JsonProperty("chain_type")
		Integer chainType;

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
		Long blockNumber;

		@JsonProperty("block_hash")
		String blockHash;

		@JsonProperty("block_time")
		Date blockTime;
		
		@JsonProperty("tx_status")
		Boolean txStatus;

		@JsonProperty("tx_hash")
		String txHash;

		@JsonProperty("ref_no")
		String refNo;
		
		@JsonProperty("client_tag")
		String clientTag;
		
		@JsonProperty("wallet_tag")
		String walletTag;
	}
	
}
