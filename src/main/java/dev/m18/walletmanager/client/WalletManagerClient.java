package dev.m18.walletmanager.client;


import dev.m18.walletmanager.client.api.ApiFactory;
import dev.m18.walletmanager.client.api.WalletManagerApi;
import dev.m18.walletmanager.client.utils.WalletManagerUtils;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WalletManagerClient {
	
	
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
	
}
