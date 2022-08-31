package dev.m18.walletmanager.client.api;

import dev.m18.walletmanager.client.WalletManagerConstants;
import dev.m18.walletmanager.client.entities.Header;
import dev.m18.walletmanager.client.utils.WalletManagerUtils;
import feign.Request.HttpMethod;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class WalletManagerApiInterceptor implements RequestInterceptor {

	private WalletManagerUtils utils;

	public WalletManagerApiInterceptor(WalletManagerUtils utils) {
		this.utils = utils;
	}

	@Override
	public void apply(RequestTemplate template) {

		String body = "";
		
		if(!template.method().equals(HttpMethod.GET.name())) {
			body = template.requestBody().asString();	
		}

		// sign content
		Header header = utils.sign(body);

		template.header(WalletManagerConstants.HEADER_ADDRESS, header.getAddress());
		template.header(WalletManagerConstants.HEADER_TIMESTAMP, String.valueOf(header.getTimestamp()));
		template.header(WalletManagerConstants.HEADER_SESSION, String.valueOf(header.getSession()));
		template.header(WalletManagerConstants.HEADER_SEQUENCE, String.valueOf(header.getSequence()));
		template.header(WalletManagerConstants.HEADER_SIGNATURE, header.getSignature());

	}

}
