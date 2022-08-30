package dev.m18.walletmanager.client.entities;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.ChainType;
import dev.m18.walletmanager.client.enums.Direction;
import dev.m18.walletmanager.client.enums.TransactionStatus;
import dev.m18.walletmanager.client.enums.TransactionType;
import lombok.Data;

@Data
public class TransferTransaction {

	@JsonProperty("merchant_id")
	Long merchantId;
	
	@JsonProperty("chain_type")
	ChainType chainType;
	
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
	Long transDate;
	
	@JsonProperty("wallet_address")
	String walletAddress;
	
	Direction direction;
	
	@JsonProperty("from_address")
	String fromAddress;
	
	@JsonProperty("to_address")
	String toAddress;
	
	Integer confirmations;
	
	TransactionStatus status;
	
	@JsonProperty("wallet_settlement_date")
	Long walletSettlementDate;
	
	@JsonProperty("creatd_date")
	Long creatdDate;
	
	@JsonProperty("post_balance")
	Long postBalance;

}