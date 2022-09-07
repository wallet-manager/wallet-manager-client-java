package dev.m18.walletmanager.client.api;

import dev.m18.walletmanager.client.WalletManagerConstants;
import dev.m18.walletmanager.client.utils.ObjectMapperUtils;
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

	public static WalletManagerApi makeWalletManagerApi(
			String baseUrl, WalletManagerUtils utils, Logger.Level loggerLevel) {
		Builder builder = Feign.builder()
				.options(new Request.Options(WalletManagerConstants.HTTP_CONNECTION_TIMEOUT_MS,
						WalletManagerConstants.HTTP_READ_TIMEOUT_MS))
				.encoder(new JacksonEncoder(ObjectMapperUtils.getObjectMapper()))
				.decoder(new JacksonDecoder(ObjectMapperUtils.getObjectMapper()))
				.logger(new Slf4jLogger(WalletManagerApi.class))
				.logLevel(loggerLevel)
				.retryer(Retryer.NEVER_RETRY)
				.requestInterceptor(new WalletManagerApiInterceptor(utils));

		return builder.target(WalletManagerApi.class, baseUrl);
	}

}
