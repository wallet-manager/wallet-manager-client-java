package dev.m18.walletmanager.client.api;

import dev.m18.walletmanager.client.WalletManagerConstants;
import dev.m18.walletmanager.client.utils.WalletManagerUtils;
import feign.Feign;
import feign.Feign.Builder;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

public class ApiFactory {

	public static WalletManagerApi makeWalletManagerApi(String baseUrl, WalletManagerUtils utils) {
		Builder builder = Feign.builder()
				.options(new Request.Options(WalletManagerConstants.HTTP_CONNECTION_TIMEOUT_MS,
						WalletManagerConstants.HTTP_READ_TIMEOUT_MS))
				.encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
				.logger(new Slf4jLogger(WalletManagerApi.class))
				.logLevel(Logger.Level.FULL)
				.retryer(Retryer.NEVER_RETRY)
				.requestInterceptor(new WalletManagerApiInterceptor(utils));

		return builder.target(WalletManagerApi.class, baseUrl);
	}

}