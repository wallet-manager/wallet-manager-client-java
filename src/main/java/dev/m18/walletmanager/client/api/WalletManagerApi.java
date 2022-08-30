package dev.m18.walletmanager.client.api;

import dev.m18.walletmanager.client.entities.BatchSweepRequest;
import dev.m18.walletmanager.client.entities.BatchSweepResult;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest;
import dev.m18.walletmanager.client.entities.BatchWithdrawResult;
import dev.m18.walletmanager.client.entities.GetAddressRequest;
import dev.m18.walletmanager.client.entities.GetAddressResult;
import dev.m18.walletmanager.client.entities.GetDepositByAddressResult;
import dev.m18.walletmanager.client.entities.Response;
import dev.m18.walletmanager.client.utils.JacksonExpander;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface WalletManagerApi {
	
	@RequestLine("POST /get_address")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	Response<GetAddressResult> getAddress(
			@Param(value = "request", expander = JacksonExpander.class)
			GetAddressRequest request);
	
	
	
	@RequestLine("POST /batch_withdraw")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	Response<BatchWithdrawResult> batchWithdraw(
			@Param(value = "request", expander = JacksonExpander.class)
			BatchWithdrawRequest request);
	
	
	@RequestLine("POST /batch_sweep")
	@Headers("Content-Type: application/json")
	@Body("{request}")
	Response<BatchSweepResult> batchSweep(
			@Param(value = "request", expander = JacksonExpander.class)
			BatchSweepRequest request);
	
	
	/**
	 * `/${chain_type}/${chain_id}/transfer/hash/${tx_hash}/deposit`
	 * @return
	 */
	@RequestLine("GET /{chain_type}/{chain_id}/transfer/hash/{tx_hash}/deposit")
	Response<GetDepositByAddressResult> getDepositByAddress(
			@Param(value = "chain_type")
			Integer chainType,
			@Param(value = "chain_id")
			Long chainId,
			@Param(value = "tx_hash")
			String txHash);
	
}
