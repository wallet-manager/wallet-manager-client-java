package dev.m18.walletmanager.client.api;

import java.util.Map;

import dev.m18.walletmanager.client.entities.BatchSweepRequest;
import dev.m18.walletmanager.client.entities.BatchSweepResult;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest;
import dev.m18.walletmanager.client.entities.BatchWithdrawResult;
import dev.m18.walletmanager.client.entities.GetAddressRequest;
import dev.m18.walletmanager.client.entities.GetAddressResult;
import dev.m18.walletmanager.client.entities.GetAllLatestBlocksResponse;
import dev.m18.walletmanager.client.entities.GetDepositResult;
import dev.m18.walletmanager.client.entities.Operation;
import dev.m18.walletmanager.client.entities.OperationBatch;
import dev.m18.walletmanager.client.entities.Response;
import feign.QueryMap;

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
	Response<GetDepositResult> getDepositByAddress(
			Integer chainType,
			Long chainId,
			String address,
			String assetName,
			Map<String, String> queryParameters);
	
	/**
	 * Get deposit by tx hash. 
	 * Confirmed success transaction by
	 * {@link dev.m18.walletmanager.client.entities.TransferTransaction#getStatus()} is
	 * {@link dev.m18.walletmanager.client.enums.TransactionStatus#ConfirmedSuccess}
	 * 
	 * `/${chain_type}/${chain_id}/transfer/hash/${tx_hash}/deposit`
	 * @return
	 */
	Response<GetDepositResult> getDepositByHash(
			Integer chainType,
			Long chainId,
			String txHash,
			Map<String, String> queryParameters);
	
	/**
	 * Get deposit by Ref No. 
	 * Confirmed success transaction by
	 * {@link dev.m18.walletmanager.client.entities.TransferTransaction#getStatus()} is
	 * {@link dev.m18.walletmanager.client.enums.TransactionStatus#ConfirmedSuccess}
	 * 
	 * `/${chain_type}/${chain_id}/transfer/ref_no/${ref_no}/deposit`
	 * @return
	 */
	Response<GetDepositResult> getDepositByRefNo(
			Integer chainType,
			Long chainId,
			String refNo,
			Map<String, String> queryParameters);
	
	/**
	 * Get withdraw by merchant order id
	 * `/withdraw/order/${merchant_order_id}`
	 * @return
	 */
	Response<Operation> getWithdrawByOrderId(
			String merchantOrderId,
			Map<String, String> queryParameters);
	
	/**
	 * Get withdraw by operation batch ID returned in {@link #batchWithdraw(BatchWithdrawRequest)}
	 * `/withdraw/batch/${batch_id}`
	 * @return
	 */
	Response<OperationBatch> getWithdrawByBatchId(
			Long batchId,
			Map<String, String> queryParameters);
	
	/**
	 * Get latest blocks
	 * `/chain/get_all_latest_blocks`
	 * @param queryParameters
	 * @return
	 */
	Response<GetAllLatestBlocksResponse> getAllLatestBlocks(
			@QueryMap
			Map<String, String> queryParameters);
	
}
