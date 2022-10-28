package dev.m18.walletmanager.client.entities.callback;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.OperationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VerifyWithdrawCallback implements Callback{

	public final static String VERIFY_WITHDRAW_TRANSACTION = "verify_withdraw_transaction";

	VerifyWithdrawCallbackData data;

	VerifyWithdrawCallback() {

	}

	@Override
	public String getType() {
		return VERIFY_WITHDRAW_TRANSACTION;
	}
	
	@Data
	public static class VerifyWithdrawCallbackData{
		
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
	    
	    @JsonProperty("client_data")
	    String clientData;
	    
	    @JsonProperty("request_time")
	    Long requestTime;

	    List<VerifyWithdrawCallbackOperation> operations;
	    
	    @Data
	    public static class VerifyWithdrawCallbackOperation {

	    	@JsonProperty("merchant_order_id")
	    	String merchantOrderId;
	    	
	    	@JsonProperty("merchant_id")
	    	Long merchantId;

	    	@JsonProperty("operation_seq")
	    	Long operationSeq;
	    	
	    	
	    	@JsonProperty("operation_type")
	    	OperationType operationType;
	    	
	    	@JsonProperty("batch_id")
	    	Long batchId;
	    	
	    	@JsonProperty("wallet_version")
	    	String walletVersion;
	    	
	    	@JsonProperty("wallet_path")
	    	String walletPath;
	    	
			@JsonProperty("asset_name")
			String assetName;
	    	
	    	BigInteger amount;
	    	
	    	Integer decimals;
	    	
	    	@JsonProperty("from_address")
	    	String fromAddress;
	    	
	    	@JsonProperty("to_address")
	    	String toAddresss;
	    	
	    	@JsonProperty("to_wallet_tag")
	    	String toWalletTag;

	    	@JsonProperty("request_time")
	    	Long requestTime;
	    	
	    }
	}
	
}
