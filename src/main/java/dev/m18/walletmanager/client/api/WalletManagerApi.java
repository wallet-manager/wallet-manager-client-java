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
import dev.m18.walletmanager.client.utils.JacksonExpander;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

public interface WalletManagerApi extends WalletManagerServerApi{
	

	@RequestLine("POST /get_address")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	@Override
	Response<GetAddressResult> getAddress(
			@Param(value = "request", expander = JacksonExpander.class)
			GetAddressRequest request);
	
	@RequestLine("POST /batch_withdraw")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	@Override
	Response<BatchWithdrawResult> batchWithdraw(
			@Param(value = "request", expander = JacksonExpander.class)
			BatchWithdrawRequest request);
	
	@RequestLine("POST /batch_sweep")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	@Override
	Response<BatchSweepResult> batchSweep(
			@Param(value = "request", expander = JacksonExpander.class)
			BatchSweepRequest request);
	
	@RequestLine("GET /{chain_type}/{chain_id}/transfer/addr/{address}/deposit/{asset_name}?offset={offset}&limit={limit}")
	@Override
	Response<GetDepositResult> getDepositByAddress(
			@Param(value = "chain_type")
			Integer chainType,
			@Param(value = "chain_id")
			Long chainId,
			@Param(value = "address")
			String address,
			@Param(value = "asset_name")
			String assetName,
			@QueryMap
			Map<String, String> queryParameters);
	
	@RequestLine("GET /{chain_type}/{chain_id}/transfer/hash/{tx_hash}/deposit?offset={offset}&limit={limit}")
	@Override
	Response<GetDepositResult> getDepositByHash(
			@Param(value = "chain_type")
			Integer chainType,
			@Param(value = "chain_id")
			Long chainId,
			@Param(value = "tx_hash")
			String txHash,
			@QueryMap
			Map<String, String> queryParameters);
	
	@RequestLine("GET /{chain_type}/{chain_id}/transfer/ref_no/{ref_no}/deposit")
	@Override
	Response<GetDepositResult> getDepositByRefNo(
			@Param(value = "chain_type")
			Integer chainType,
			@Param(value = "chain_id")
			Long chainId,
			@Param(value = "ref_no")
			String refNO,
			@QueryMap
			Map<String, String> queryParameters);
	

	@RequestLine("GET /withdraw/order/{merchant_order_id}")
	@Override
	Response<Operation> getWithdrawByOrderId(
			@Param(value = "merchant_order_id")
			String merchantOrderId,
			@QueryMap
			Map<String, String> queryParameters);
	
	@RequestLine("GET /withdraw/batch/{batch_id}")
	@Override
	Response<OperationBatch> getWithdrawByBatchId(
			@Param(value = "batch_id")
			Long batchId,
			@QueryMap
			Map<String, String> queryParameters);
			
	
	@RequestLine("GET /chains/get_all_latest_blocks")
	@Override
	Response<GetAllLatestBlocksResponse> getAllLatestBlocks(
			@QueryMap
			Map<String, String> queryParameters);
	
}
