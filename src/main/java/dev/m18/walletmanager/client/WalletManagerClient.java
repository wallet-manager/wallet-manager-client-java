package dev.m18.walletmanager.client;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dev.m18.walletmanager.client.api.ApiFactory;
import dev.m18.walletmanager.client.api.WalletManagerApi;
import dev.m18.walletmanager.client.api.WalletManagerServerApi;
import dev.m18.walletmanager.client.entities.BatchSweepRequest;
import dev.m18.walletmanager.client.entities.BatchSweepResult;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest;
import dev.m18.walletmanager.client.entities.BatchWithdrawResult;
import dev.m18.walletmanager.client.entities.GetAddressRequest;
import dev.m18.walletmanager.client.entities.GetAddressResult;
import dev.m18.walletmanager.client.entities.GetAllLatestBlocksOptions;
import dev.m18.walletmanager.client.entities.GetAllLatestBlocksResponse;
import dev.m18.walletmanager.client.entities.GetDepositRequestOptions;
import dev.m18.walletmanager.client.entities.GetDepositResult;
import dev.m18.walletmanager.client.entities.GetWithdrawRequestOptions;
import dev.m18.walletmanager.client.entities.Operation;
import dev.m18.walletmanager.client.entities.OperationBatch;
import dev.m18.walletmanager.client.entities.Response;
import dev.m18.walletmanager.client.utils.WalletManagerUtils;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WalletManagerClient implements WalletManagerServerApi{
	
	
	private WalletManagerUtils utils;
	private WalletManagerApi api;
	
	
	public WalletManagerClient(String privateKey, String baseUrl, int instanceId) {
		this(privateKey, baseUrl, instanceId, Logger.Level.FULL);
	}

	public WalletManagerClient(String privateKey, String baseUrl, int instanceId, Logger.Level loggerLevel) {
		this.utils = new WalletManagerUtils(privateKey, instanceId);
		this.api = ApiFactory.makeWalletManagerApi(baseUrl, utils, loggerLevel);
		log.info("Init WalletManagerClient instance {}", instanceId);
		log.info("Wallet manager api base url {}", baseUrl);
	}
	
	
	public WalletManagerUtils getUtils() {
		return this.utils;
	}
	
	public WalletManagerApi getApi() {
		return this.api;
	}

	@Override
	public Response<GetAddressResult> getAddress(GetAddressRequest request) {
		return this.getApi().getAddress(request);
	}

	@Override
	public Response<BatchWithdrawResult> batchWithdraw(BatchWithdrawRequest request) {
		return this.getApi().batchWithdraw(request);
	}

	@Override
	public Response<BatchSweepResult> batchSweep(BatchSweepRequest request) {
		return this.getApi().batchSweep(request);
	}

	@Override
	public Response<GetDepositResult> getDepositByAddress(Integer chainType, Long chainId, String address, String assetName, 
			Map<String, String> queryParameters) {
		return this.getApi().getDepositByAddress(chainType, chainId, address, assetName, queryParameters);
	}
	
	
	private Map<String, String>  convertGetDepsoitRequestOptions(GetDepositRequestOptions options){
		Map<String, String> queryParameters = new HashMap<>();
		if(options != null) {
			// block hash
			Optional.ofNullable(options.getBlockHash()).ifPresent(e -> queryParameters.put("block_hash", e.toString()));
			// tx status
			Optional.ofNullable(options.getTxStatus()).ifPresent(e -> queryParameters.put("tx_status", e.toString()));
			// only valid
			Optional.ofNullable(options.getValid()).ifPresent(e -> queryParameters.put("valid", e.toString()));
			// add offset
			Optional.ofNullable(options.getOffset()).ifPresent(e -> queryParameters.put("offset", e.toString()));
			// add limit
			Optional.ofNullable(options.getLimit()).ifPresent(e -> queryParameters.put("limit", e.toString()));			
		}
		return queryParameters;
	}
	
	private Map<String, String>  convertGetWithdrawRequestOptions(GetWithdrawRequestOptions options){
		Map<String, String> queryParameters = new HashMap<>();
		if(options != null) {
			// add offset
			Optional.ofNullable(options.getOffset()).ifPresent(e -> queryParameters.put("offset", e.toString()));
			// add limit
			Optional.ofNullable(options.getLimit()).ifPresent(e -> queryParameters.put("limit", e.toString()));			
		}
		return queryParameters;
	}
	
	
	/**
	 * 
	 * @param chainType
	 * @param chainId
	 * @param address
	 * @param assetName
	 * @param options
	 * @return
	 */
	public Response<GetDepositResult> getDepositByAddress(Integer chainType, Long chainId, String address, String assetName, 
			GetDepositRequestOptions options) {
		// convert options
		Map<String, String> queryParameters = convertGetDepsoitRequestOptions(options);
		// query
		return this.getApi().getDepositByAddress(chainType, chainId, address, assetName, queryParameters);
	}

	@Override
	public Response<GetDepositResult> getDepositByHash(Integer chainType, Long chainId, String txHash,
			Map<String, String> queryParameters) {
		return this.getApi().getDepositByHash(chainType, chainId, txHash, queryParameters);
	}
	
	/**
	 * Get deposit by tx hash
	 * @param chainType
	 * @param chainId
	 * @param txHash
	 * @param options
	 * @return
	 */
	public Response<GetDepositResult> getDepositByHash(Integer chainType, Long chainId, String txHash,
			GetDepositRequestOptions options) {
		
		// convert options
		Map<String, String> queryParameters = convertGetDepsoitRequestOptions(options);
		// query
		return this.getApi().getDepositByHash(chainType, chainId, txHash, queryParameters);
	}

	@Override
	public Response<GetDepositResult> getDepositByRefNo(Integer chainType, Long chainId, String refNo,
			Map<String, String> queryParameters) {
		return this.getApi().getDepositByRefNo(chainType, chainId, refNo, queryParameters);
	}

	/**
	 * Get deposit by refNo
	 * @param chainType
	 * @param chainId
	 * @param refNo
	 * @param options
	 * @return
	 */
	public Response<GetDepositResult> getDepositByRefNo(Integer chainType, Long chainId, String refNo,
			GetDepositRequestOptions options) {
		// convert options
		Map<String, String> queryParameters = convertGetDepsoitRequestOptions(options);
		// query
		return this.getApi().getDepositByRefNo(chainType, chainId, refNo, queryParameters);
	}
	
	/**
	 * Get unique record by refNo and blockHash
	 * @param chainType
	 * @param chainId
	 * @param refNo
	 * @param blockHash
	 * @return
	 */
	public Response<GetDepositResult> getUniqueDepositByRefNo(Integer chainType, Long chainId, String refNo,
			String blockHash) {
		GetDepositRequestOptions options = GetDepositRequestOptions.builder().blockHash(blockHash).build();
		return this.getDepositByRefNo(chainType, chainId, refNo, options);
	}
	
	/**
	 * Return success and valid deposit
	 * @param chainType
	 * @param chainId
	 * @param refNo
	 * @return
	 */
	public Response<GetDepositResult> getSuccessDepositByRefNo(Integer chainType, Long chainId, String refNo) {
		GetDepositRequestOptions options = GetDepositRequestOptions.builder().txStatus(true).valid(true).build();
		return this.getDepositByRefNo(chainType, chainId, refNo, options);
	}
	
	@Override
	public Response<Operation> getWithdrawByOrderId(String merchantOrderId, Map<String, String> queryParameters) {
		return this.getApi().getWithdrawByOrderId(merchantOrderId, queryParameters);
	}
	
	/**
	 * Get withdraw by order ID
	 * @param merchantOrderId
	 * @param options
	 * @return
	 */
	private Response<Operation> getWithdrawByOrderId(String merchantOrderId, GetWithdrawRequestOptions options) {
		// convert options
		Map<String, String> queryParameters = convertGetWithdrawRequestOptions(options);
		// query
		return this.getApi().getWithdrawByOrderId(merchantOrderId, queryParameters);
	}
	
	/**
	 * Get withdraw by order ID
	 * @param merchantOrderId
	 * @return
	 */
	public Response<Operation> getWithdrawByOrderId(String merchantOrderId) {
		GetWithdrawRequestOptions pagination = GetWithdrawRequestOptions.builder().offset(0).limit(20).build();
		// should only one record returned.
		return this.getWithdrawByOrderId(merchantOrderId, pagination);
	}

	@Override
	public Response<OperationBatch> getWithdrawByBatchId(Long batchId, Map<String, String> queryParameters) {
		return this.getApi().getWithdrawByBatchId(batchId, queryParameters);
	}
	
	/**
	 * Get withdraw by batch ID.
	 * @param batchId
	 * @param options
	 * @return
	 */
	public Response<OperationBatch> getWithdrawByBatchId(Long batchId, GetWithdrawRequestOptions options) {
		// convert options
		Map<String, String> queryParameters = convertGetWithdrawRequestOptions(options);
		// query
		return this.getApi().getWithdrawByBatchId(batchId, queryParameters);
	}

	
	private Map<String, String>  convertGetAllLatestBlocksOptions(GetAllLatestBlocksOptions options){
		Map<String, String> queryParameters = new HashMap<>();
		if(options != null) {
			// chain type
			Optional.ofNullable(options.getChainId()).ifPresent(e -> queryParameters.put("chain_id", e.toString()));
			// chain id
			Optional.ofNullable(options.getChainType()).ifPresent(e -> queryParameters.put("chain_type", e.getIntVal().toString()));		
		}
		return queryParameters;
	}
	
	@Override
	public Response<GetAllLatestBlocksResponse> getAllLatestBlocks(Map<String, String> queryParameters) {
		return this.getApi().getAllLatestBlocks(queryParameters);
	}
	
	public Response<GetAllLatestBlocksResponse> getAllLatestBlocks() {
		return this.getAllLatestBlocks((GetAllLatestBlocksOptions)null);
	}
	
	public Response<GetAllLatestBlocksResponse> getAllLatestBlocks(GetAllLatestBlocksOptions options) {
		// convert options
		Map<String, String> queryParameters = convertGetAllLatestBlocksOptions(options);
		// query
		return this.getApi().getAllLatestBlocks(queryParameters);
	}
	
	
	
}
