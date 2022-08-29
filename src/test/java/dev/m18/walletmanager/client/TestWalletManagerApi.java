package dev.m18.walletmanager.client;

import org.junit.Before;
import org.junit.Test;

import dev.m18.walletmanager.client.WalletManagerClient;
import dev.m18.walletmanager.client.WalletManagerConstants.ChainId;
import dev.m18.walletmanager.client.entities.GetAddressRequest;
import dev.m18.walletmanager.client.entities.GetAddressResult;
import dev.m18.walletmanager.client.entities.Response;
import dev.m18.walletmanager.client.enums.ChainType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestWalletManagerApi extends ConfigUnitTest{
	
	WalletManagerClient client;

    @Before
    @Override
    public void setUp() throws Exception {
    	super.setUp();
		client = new WalletManagerClient(
				config.getIdentity().getPrivateKey(),
				config.getClientConfig().getBaseURL(),
				config.getClientConfig().getInstanceId());	
    }

    @Test
    public void getAddress() {
    	
		GetAddressRequest request = new GetAddressRequest();
		
		request.setMerchantId(3L);
		request.setChainType(ChainType.ETH);
		request.setChainId(ChainId.Rinkeby);
//		request.setClientId("2");
		request.setClientId("" + System.currentTimeMillis());
		
		
		Response<GetAddressResult> response = client.getApi().getAddress(request);
		
		log.info("Response {}", response);
    }

}
