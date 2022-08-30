package dev.m18.walletmanager.client;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dev.m18.walletmanager.client.WalletManagerConstants.ChainId;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest.WithdrawOrder;
import dev.m18.walletmanager.client.entities.BatchWithdrawResult;
import dev.m18.walletmanager.client.entities.GetAddressRequest;
import dev.m18.walletmanager.client.entities.GetAddressResult;
import dev.m18.walletmanager.client.entities.GetDepositByAddressResult;
import dev.m18.walletmanager.client.entities.GetDepositByHashResult;
import dev.m18.walletmanager.client.entities.Operation;
import dev.m18.walletmanager.client.entities.OperationBatch;
import dev.m18.walletmanager.client.entities.Response;
import dev.m18.walletmanager.client.enums.ChainType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestWalletManagerApi extends ConfigUnitTest{
	
	WalletManagerClient client;
	long orderSeq = System.currentTimeMillis();

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
    public void testGetAddress() {
    	
		GetAddressRequest request = new GetAddressRequest();
		
		request.setMerchantId(3L);
		request.setChainType(ChainType.ETH);
		request.setChainId(ChainId.Rinkeby);
//		request.setClientId("2");
		request.setClientId("" + System.currentTimeMillis());
		
		
		Response<GetAddressResult> response = client.getApi().getAddress(request);
		
		log.info("Get Address Response {}", response);
    }
    
    @Test
    public void testBatchWithdraw() {
    	
    	// prepare orders
    	WithdrawOrder order1 = new WithdrawOrder();
    	order1.setMerchantOrderId("W" + orderSeq++);
    	order1.setAmount(new BigInteger("1000000"));
        order1.setDecimals(6);
        order1.setToAddress("0x8F9092CE573e41d72378Cf8c9d3335584e6843F2");
        
    	WithdrawOrder order2 = new WithdrawOrder();
    	order2.setMerchantOrderId("W" + orderSeq++);
    	order2.setAmount(new BigInteger("1000000"));
        order2.setDecimals(6);
        order2.setToAddress("0x8F9092CE573e41d72378Cf8c9d3335584e6843F2");

        BatchWithdrawRequest request = new BatchWithdrawRequest();
        request.setMerchantId(3L);
        request.setChainType(ChainType.ETH);
        request.setChainId(ChainId.Rinkeby);
        request.setAssetName("USDT");     
        request.setOrders(List.of(order1, order2));
        request.setClientData("abc");
        
        
        Response<BatchWithdrawResult> response = client.getApi().batchWithdraw(request);
        
    	log.info("Batch Withdraw Response {}", response);
    }
    
    @Test
    public void testGetDepositByAddress() {

    	Response<GetDepositByAddressResult> response = client.getApi().getDepositByAddress(
				ChainType.ETH.getIntVal(), 
				ChainId.Rinkeby, 
				"0x8F9092CE573e41d72378Cf8c9d3335584e6843F1", 
				"USDT", 
				0, 
				10);
		
    	log.info("Get Deposit by Address {}", response);
    	
    }
    
    @Test
    public void testGetDepositByHash() {    	
    	Response<GetDepositByHashResult> response = 
    			client.getApi().getDepositByHash(ChainType.ETH.getIntVal(), ChainId.Rinkeby, "0x11111");
    	
    	log.info("Get Deposit by Hash {}", response);
    }
    
    @Test
    public void testGetWithdrawByOrderId() {
    	Response<Operation> response = client.getApi().getWithdrawByOrderId("100002123");
    	
    	log.info("Get withdraw by order Id {}", response);
    }
    
    @Test
    public void testGetWithdrawByBatchId() {
    	Response<OperationBatch> response = client.getApi().getWithdrawByBatchId(56L);
    	
    	log.info("Get withdraw by batch Id {}", response);
    }
    
    

}
