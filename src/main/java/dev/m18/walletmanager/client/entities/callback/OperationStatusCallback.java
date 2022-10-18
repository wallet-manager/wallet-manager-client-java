package dev.m18.walletmanager.client.entities.callback;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.OperationStatus;
import dev.m18.walletmanager.client.enums.OperationTransactionStatus;
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
public class OperationStatusCallback implements Callback {

	public final static String OPERATION_STATUS = "operation_status";

	OperationStatusCallbackData data;

	OperationStatusCallback() {

	}

	@Override
	public String getType() {
		return OPERATION_STATUS;
	}

	
	@Data
	public static class OperationStatusCallbackData {

		@JsonProperty("batch_id")
		Long batchId;

		@JsonProperty("operation_type")
		OperationType operationType;

		@JsonProperty("operation_seq")
		Integer operationSeq;

		@JsonProperty("merchant_order_id")
		String merchantOrderId;

		@JsonProperty("merchant_id")
		Long merchantId;

		@JsonProperty("chain_type")
		Integer chainType;

		@JsonProperty("chain_id")
		Long chainId;

		@JsonProperty("asset_name")
		String assetName;

		BigInteger amount;

		Integer decimals;

		@JsonProperty("wallet_version")
		Integer walletVersion;

		String path;

		@JsonProperty("from_address")
		String fromAddress;

		@JsonProperty("to_address")
		String toAddress;

		@JsonProperty("invoker_address")
		String invokerAddress;

		@JsonProperty("client_data")
		String clientData;

		OperationStatus status;

		@JsonProperty("updated_time")
		Date updatedTime;

		List<Transaction> transactions;

		@Data
		public static class Transaction {

			@JsonProperty("block_number")
			Long blockNumber;

			@JsonProperty("block_hash")
			String blockHash;

			@JsonProperty("block_time")
			Long blockTime;
			
			@JsonProperty("tx_status")
			Boolean txStatus;

			@JsonProperty("tx_hash")
			String txHash;

			OperationTransactionStatus status;

			String remarks;
		}

	}
	
}


