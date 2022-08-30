package dev.m18.walletmanager.client.entities;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BatchSweepResult {
	
	@JsonProperty("batch_id")
    Long batchId;
	
	@JsonProperty("sweep_records")
    List<SweepRecord> sweepRecords;
    
    @JsonProperty("request_time")
    Long requestTime;
    
    
    @Data
    public class SweepRecord {
    	@JsonProperty("wallet_version")
        Long walletVersion;
    	
        String path;
        
        @JsonProperty("from_address")
        String fromAddress;
        
        @JsonProperty("to_address")
        String toAddress;
        
        BigInteger amount;
        
        Integer decimals;
    }
	
}
