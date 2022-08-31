package dev.m18.walletmanager.client.api;

import dev.m18.walletmanager.client.entities.BatchSweepRequest;
import dev.m18.walletmanager.client.entities.BatchSweepResult;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest;
import dev.m18.walletmanager.client.entities.BatchWithdrawResult;
import dev.m18.walletmanager.client.entities.GetAddressRequest;
import dev.m18.walletmanager.client.entities.GetAddressResult;
import dev.m18.walletmanager.client.entities.GetDepositByAddressResult;
import dev.m18.walletmanager.client.entities.GetDepositByHashResult;
import dev.m18.walletmanager.client.entities.Operation;
import dev.m18.walletmanager.client.entities.OperationBatch;
import dev.m18.walletmanager.client.entities.Response;

public interface WalletManagerServerApi {


	/**
	 * Get client deposit address by clientId
	 * @param request
	 * @return
	 */
	Response<GetAddressResult> getAddress(GetAddressRequest request);
	
	/**
	 * Send a batch withdraw request.
	 * @param request
	 * @return
	 */
	Response<BatchWithdrawResult> batchWithdraw(BatchWithdrawRequest request);
	
	/**
	 * Send a batch sweep request.
	 * @param request
	 * @return
	 */
	Response<BatchSweepResult> batchSweep(BatchSweepRequest request);
	
	/**
	 * Get deposit by address.
	 * Confirmed success transaction by
	 * {@link dev.m18.walletmanager.client.entities.TransferTransaction#getStatus() status} is
	 * {@link dev.m18.walletmanager.client.enums.TransactionStatus#ConfirmedSuccess ConfirmedSuccess}
	 * `/${chain_type}/${chain_id}/transfer/addr/${address}/deposit/${asset_name}`
	 * @return
	 */
	Response<GetDepositByAddressResult> getDepositByAddress(
			Integer chainType,
			Long chainId,
			String address,
			String assetName,
			Integer offset,
			Integer limit);
	
	/**
	 * Get deposit by tx hash. 
	 * Confirmed success transaction by
	 * {@link dev.m18.walletmanager.client.entities.TransferTransaction#getStatus()} is
	 * {@link dev.m18.walletmanager.client.enums.TransactionStatus#ConfirmedSuccess}
	 * 
	 * `/${chain_type}/${chain_id}/transfer/hash/${tx_hash}/deposit`
	 * @return
	 */
	Response<GetDepositByHashResult> getDepositByHash(
			Integer chainType,
			Long chainId,
			String txHash);
	
	/**
	 * Get withdraw by merchant order id
	 * `/withdraw/order/${merchant_order_id}`
	 * @return
	 */
	Response<Operation> getWithdrawByOrderId(String merchantOrderId);
	
	/**
	 * Get withdraw by operation batch ID returned in {@link #batchWithdraw(BatchWithdrawRequest)}
	 * `/withdraw/batch/${batch_id}`
	 * @return
	 */
	Response<OperationBatch> getWithdrawByBatchId(Long batchId);
	
}
