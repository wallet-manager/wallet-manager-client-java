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
import dev.m18.walletmanager.client.utils.JacksonExpander;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface WalletManagerApi {
	
	/**
	 * Get client deposit address by clientId
	 * @param request
	 * @return
	 */
	@RequestLine("POST /get_address")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	Response<GetAddressResult> getAddress(
			@Param(value = "request", expander = JacksonExpander.class)
			GetAddressRequest request);
	
	/**
	 * Send a batch withdraw request.
	 * @param request
	 * @return
	 */
	@RequestLine("POST /batch_withdraw")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	Response<BatchWithdrawResult> batchWithdraw(
			@Param(value = "request", expander = JacksonExpander.class)
			BatchWithdrawRequest request);
	
	/**
	 * Send a batch sweep request.
	 * @param request
	 * @return
	 */
	@RequestLine("POST /batch_sweep")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	Response<BatchSweepResult> batchSweep(
			@Param(value = "request", expander = JacksonExpander.class)
			BatchSweepRequest request);
	
	/**
	 * Get deposit by address.
	 * `/${chain_type}/${chain_id}/transfer/addr/${address}/deposit/${asset_name}`
	 * @return
	 */
	@RequestLine("GET /{chain_type}/{chain_id}/transfer/addr/{address}/deposit/{asset_name}?offset={offset}&limit={limit}")
	Response<GetDepositByAddressResult> getDepositByAddress(
			@Param(value = "chain_type")
			Integer chainType,
			@Param(value = "chain_id")
			Long chainId,
			@Param(value = "address")
			String address,
			@Param(value = "asset_name")
			String assetName,
			@Param(value = "offset")
			Integer offset,
			@Param(value = "limit")
			Integer limit);
	
	/**
	 * Get deposit by address.
	 * `/${chain_type}/${chain_id}/transfer/hash/${tx_hash}/deposit`
	 * @return
	 */
	@RequestLine("GET /{chain_type}/{chain_id}/transfer/hash/{tx_hash}/deposit")
	Response<GetDepositByHashResult> getDepositByHash(
			@Param(value = "chain_type")
			Integer chainType,
			@Param(value = "chain_id")
			Long chainId,
			@Param(value = "tx_hash")
			String txHash);
	
	/**
	 * Get withdraw by merchant order id
	 * `/withdraw/order/${merchant_order_id}`
	 * @return
	 */
	@RequestLine("GET /withdraw/order/{merchant_order_id}")
	Response<Operation> getWithdrawByOrderId(
			@Param(value = "merchant_order_id")
			String merchantOrderId);
	
	/**
	 * Get withdraw by operation batch ID returned in {@link #batchWithdraw(BatchWithdrawRequest)}
	 * `/withdraw/batch/${batch_id}`
	 * @return
	 */
	@RequestLine("GET /withdraw/batch/{batch_id}")
	Response<OperationBatch> getWithdrawByBatchId(
			@Param(value = "batch_id")
			Long batchId);
	
}
