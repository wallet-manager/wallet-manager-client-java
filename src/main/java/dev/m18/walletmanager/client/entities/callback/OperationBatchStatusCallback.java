package dev.m18.walletmanager.client.entities.callback;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.OperationBatchStatus;
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
public class OperationBatchStatusCallback implements Callback {

	public final static String OPERATION_BATCH_STATUS = "operation_batch_status";

	OperationBatchStatusCallbackData data;

	OperationBatchStatusCallback() {

	}

	@Override
	public String getType() {
		return OPERATION_BATCH_STATUS;
	}

	@Data
	public static class OperationBatchStatusCallbackData {

		@JsonProperty("batch_id")
		Long batchId;

		@JsonProperty("operation_type")
		OperationType operationType;

		@JsonProperty("merchant_id")
		Long merchantId;

		@JsonProperty("chain_type")
		Integer chainType;

		@JsonProperty("chain_id")
		Long chainId;

		@JsonProperty("asset_name")
		String assetName;

		@JsonProperty("client_data")
		String clientData;

		OperationBatchStatus status;

		@JsonProperty("updated_time")
		Date updatedTime;

	}

}
