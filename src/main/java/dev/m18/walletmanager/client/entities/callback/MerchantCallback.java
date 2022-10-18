package dev.m18.walletmanager.client.entities.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import dev.m18.walletmanager.client.entities.callback.BlockNumberCallback.BlockNumberCallbackData;
import dev.m18.walletmanager.client.entities.callback.DepositStatusCallback.DepositStatusCallbackData;
import dev.m18.walletmanager.client.entities.callback.OperationBatchStatusCallback.OperationBatchStatusCallbackData;
import dev.m18.walletmanager.client.entities.callback.OperationStatusCallback.OperationStatusCallbackData;
import dev.m18.walletmanager.client.entities.callback.VerifyWithdrawCallback.VerifyWithdrawCallbackData;
import dev.m18.walletmanager.client.utils.ObjectMapperUtils;
import lombok.Data;

@Data
public class MerchantCallback implements Callback {

	String type;
	JsonNode data;


	public boolean isDepositStatusCallback() {
		return this.getType().equals(DepositStatusCallback.DEPOSIT_STATUS);
	}
	
	public DepositStatusCallback toDepositStatusCallback() throws JsonProcessingException {
		if (this.isDepositStatusCallback()) {
			DepositStatusCallback callback = new DepositStatusCallback();
			DepositStatusCallbackData data = ObjectMapperUtils.getObjectMapper().treeToValue(this.getData(),
					DepositStatusCallbackData.class);
			callback.setData(data);
			return callback;
		} else {
			throw new RuntimeException("Callback type not match");
		}
	}

	public boolean isOperationBatchStatusCallback() {
		return this.getType().equals(OperationBatchStatusCallback.OPERATION_BATCH_STATUS);
	}
	
	public OperationBatchStatusCallback toOperationBatchStatusCallback() throws JsonProcessingException {
		
		if (this.isOperationBatchStatusCallback()) {
			OperationBatchStatusCallback callback = new OperationBatchStatusCallback();
			OperationBatchStatusCallbackData data = ObjectMapperUtils.getObjectMapper().treeToValue(this.getData(),
					OperationBatchStatusCallbackData.class);
			callback.setData(data);
			return callback;
		} else {
			throw new RuntimeException("Callback type not match");
		}
	}

	public boolean isOperationStatusCallback() {
		return this.getType().equals(OperationStatusCallback.OPERATION_STATUS);
	}
	
	public OperationStatusCallback toOperationStatusCallback() throws JsonProcessingException {
		
		if (this.isOperationStatusCallback()) {
			OperationStatusCallback callback = new OperationStatusCallback();
			OperationStatusCallbackData data = ObjectMapperUtils.getObjectMapper().treeToValue(this.getData(),
					OperationStatusCallbackData.class);
			callback.setData(data);
			return callback;
		} else {
			throw new RuntimeException("Callback type not match");
		}
	}
	
	
	public boolean isVerifyWithdrawCallback () {
		return this.getType().equals(VerifyWithdrawCallback.VERIFY_WITHDRAW_TRANSACTION);
	}
	
	public VerifyWithdrawCallback toVerifyWithdrawCallback() throws JsonProcessingException {
		
		if (this.isVerifyWithdrawCallback()) {
			VerifyWithdrawCallback callback = new VerifyWithdrawCallback();
			VerifyWithdrawCallbackData data = ObjectMapperUtils.getObjectMapper().treeToValue(this.getData(),
					VerifyWithdrawCallbackData.class);
			callback.setData(data);
			return callback;
		} else {
			throw new RuntimeException("Callback type not match");
		}
	}
	
	public boolean isBlockNumberCallback() {
		return this.getType().equals(BlockNumberCallback.BLOCK_NUMBER);
	}
	
	public BlockNumberCallback toBlockNumberCallback() throws JsonProcessingException {
		if (this.isBlockNumberCallback()) {
			BlockNumberCallback callback = new BlockNumberCallback();
			BlockNumberCallbackData data = ObjectMapperUtils.getObjectMapper().treeToValue(this.getData(),
					BlockNumberCallbackData.class);
			callback.setData(data);
			return callback;
		} else {
			throw new RuntimeException("Callback type not match");
		}
	}

	
	
}
