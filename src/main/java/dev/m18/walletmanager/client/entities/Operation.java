package dev.m18.walletmanager.client.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.OperationStatus;
import dev.m18.walletmanager.client.enums.OperationType;
import lombok.Data;

@Data
public class Operation {

	@JsonProperty("merchant_id")
	Long merchantId;
	
	@JsonProperty("batch_id")
	Long batchId;
	
	@JsonProperty("operation_seq")
	Long operationSeq;
	
	@JsonProperty("merchant_order_id")
	String merchantOrderId;
	
	@JsonProperty("wallet_version")
	Integer walletVersion;
	
	String path;
	
	@JsonProperty("from_address")
	String fromAddress;
	
	@JsonProperty("to_addresss")
	String toAddresss;
	
	@JsonProperty("invoker_address")
	String invokerAddress;
	
	@JsonProperty("operation_type")
	OperationType operationType;
	
	BigDecimal amount;
	
	@JsonProperty("asset_name")
	String assetName;
	
	OperationStatus status;
	
	@JsonProperty("create_date")
	Date createDate;
	
	@JsonProperty("last_modified_date")
	Date lastModifiedDate;

}
