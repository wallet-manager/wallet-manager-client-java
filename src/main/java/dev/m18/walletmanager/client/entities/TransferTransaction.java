package dev.m18.walletmanager.client.entities;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.Direction;
import dev.m18.walletmanager.client.enums.TransactionStatus;
import dev.m18.walletmanager.client.enums.TransactionType;
import lombok.Data;

@Data
public class TransferTransaction {

	@JsonProperty("merchant_id")
	Long merchantId;
	
	@JsonProperty("chain_type")
	Integer chainType;
	
	@JsonProperty("chain_id")
	Long chainId;
	
	@JsonProperty("tx_hash")
	String txHash;
	
	@JsonProperty("transfer_seq")
	Integer transferSeq;
	
	@JsonProperty("block_hash")
	String blockHash;
	
	@JsonProperty("block_number")
	Long blockNumber;
	
	@JsonProperty("wallet_name")
	String walletName;
	
	@JsonProperty("wallet_tag")
	String walletTag;
	
	@JsonProperty("client_id")
	String clientId;
	
	@JsonProperty("ref_no")
	String refNo;
	
	@JsonProperty("asset_name")
	String assetName;
	
	@JsonProperty("trans_type")
	TransactionType transType;
	
	BigInteger amount;
	
	@JsonProperty("is_fee")
	Boolean isFee;
	
	@JsonProperty("tx_status")
	Boolean txStatus;
	
	@JsonProperty("trans_fee")
	BigInteger transFee;
	
	@JsonProperty("trans_date")
	Date transDate;
	
	@JsonProperty("wallet_address")
	String walletAddress;
	
	Direction direction;
	
	@JsonProperty("from_address")
	String fromAddress;
	
	@JsonProperty("to_address")
	String toAddress;
	
	@JsonProperty("to_wallet_tag")
	String toWalletTag;
	
	Integer confirmations;
	
	TransactionStatus status;
	
	@JsonProperty("wallet_settlement_date")
	Date walletSettlementDate;
	
	@JsonProperty("creatd_date")
	Date creatdDate;
	
	@JsonProperty("post_balance")
	BigInteger postBalance;
	
	Integer decimals; 

}
