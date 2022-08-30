package dev.m18.walletmanager.client.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetWithdrawByBatchIdRequest {

	@JsonProperty("batch_id")
	String batchId;
	
}
