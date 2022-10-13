package dev.m18.walletmanager.client.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.OperationBatchStatus;
import dev.m18.walletmanager.client.enums.OperationType;
import lombok.Data;

@Data
public class OperationBatch {

	@JsonProperty("merchant_id")
	Long merchantId;
	
	@JsonProperty("chain_type")
    Integer chainType;
	
	@JsonProperty("chain_id")
    Long chainId;
	
	@JsonProperty("operation_type")
    OperationType operationType;
	
	@JsonProperty("total_operation")
    Integer totalOperation;
	
	@JsonProperty("total_step")
    Integer totalStep;
	
	@JsonProperty("current_step")
    Integer currentStep;
	
    OperationBatchStatus status;
    
    @JsonProperty("create_date")
    Date createDate;
    
    @JsonProperty("last_modified_date")
    Date lastModifiedDate;
    
    List<Operation> operations;
	
}
