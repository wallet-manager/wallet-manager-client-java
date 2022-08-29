package dev.m18.walletmanager.client.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BatchWithdrawResult {

	@JsonProperty("batch_id")
    Long batchId;
	
	@JsonProperty("request_time")
    Long requestTime;
	
}
