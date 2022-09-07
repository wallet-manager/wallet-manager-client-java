package dev.m18.walletmanager.client.entities.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.ChainType;
import dev.m18.walletmanager.client.enums.OperationBatchStatus;
import dev.m18.walletmanager.client.enums.OperationType;
import lombok.Data;

@Data
public class OperationBatchStatusCallback {

	@JsonProperty("batch_id")
	Long batchId;
	
	@JsonProperty("operation_type")
	OperationType operationType;
	
	@JsonProperty("merchant_id")
	Long merchantId;
	
	@JsonProperty("chain_type")
	ChainType chainType;
	
	@JsonProperty("chain_id")
	Long chainId;
	
	@JsonProperty("asset_name")
	String assetName;
	
	@JsonProperty("client_data")
	String clientData;
	
	OperationBatchStatus status;
	
	@JsonProperty("updated_time")
	Long updatedTime;
	
}
