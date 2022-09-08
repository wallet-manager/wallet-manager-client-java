package dev.m18.walletmanager.client;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

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
import dev.m18.walletmanager.client.entities.TransferTransaction;
import dev.m18.walletmanager.client.enums.ChainType;
import dev.m18.walletmanager.client.enums.OperationBatchStatus;
import dev.m18.walletmanager.client.enums.OperationStatus;
import dev.m18.walletmanager.client.enums.TransactionStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestWalletManagerApi extends ConfigUnitTest{
	
	WalletManagerClient client;
	long orderSeq = System.currentTimeMillis();
	long clientSeq = System.currentTimeMillis();

	private static Long MERCHANT_ID = 3L;
	private static ChainType CHAIN_TYPE = ChainType.ETH;
	private static Long CHAIN_ID = ChainId.Rinkeby;
	
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
		
		request.setMerchantId(MERCHANT_ID);
		request.setChainType(CHAIN_TYPE);
		request.setChainId(CHAIN_ID);
		request.setClientId("C" + clientSeq++);
		
		
		Response<GetAddressResult> response = client.getApi().getAddress(request);
		GetAddressResult result = response.getResult();
		if(result != null) {
			log.info("Address of client id {} is {}", request.getClientId(), result.getAddress());
		}
		
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
        request.setMerchantId(MERCHANT_ID);
        request.setChainType(CHAIN_TYPE);
        request.setChainId(CHAIN_ID);
        request.setAssetName("USDT");     
        request.setOrders(List.of(order1, order2));
        request.setClientData("abc");
        
        
        Response<BatchWithdrawResult> response = client.getApi().batchWithdraw(request);
        
        BatchWithdrawResult result = response.getResult();
        if(result != null) {
        	log.info("Successfully sent batch withdraw request. batch id {}", result.getBatchId());
        }
        
    	log.info("Batch Withdraw Response {}", response);
    }
    
    @Test
    public void testGetDepositByAddress() {

    	Response<GetDepositByAddressResult> response = client.getApi().getDepositByAddress(
				ChainType.ETH.getIntVal(), 
				ChainId.Rinkeby, 
				"0xaa2a674256017f7B71f8f7dF36041C5187D7B68E", 
				"UNI", 
				0, 
				10);
		
    	log.info("Get Deposit by Address {}", response);
    	
    }
    
    @Test
    public void testGetDepositByHash() {    
    	
    	Response<GetDepositByHashResult> response = 
    			client.getApi().getDepositByHash(
    					CHAIN_TYPE.getIntVal(), 
    					CHAIN_ID, 
    					"0x1976e40062b6024d52667a6c88508a2ec0716ab50107ebfc26095beb4e8e4851",
    					0, 
    					10);
    	
    	GetDepositByHashResult result = response.getResult();
    	if(result != null) {
    		
    		// filter confirmed success transaction
    		Optional<TransferTransaction> deposit = result.getTransactions().stream().filter(
    				tx -> {
    					// Success and not a fee transaction
        				return TransactionStatus.ConfirmedSuccess.equals(tx.getStatus()) && 
        						Boolean.FALSE.equals(tx.getIsFee());
    				}).findFirst();
    		
    		if(deposit.isPresent()) {
    			log.info("Depsit success txid {}", deposit.get().getTxHash());
    		}
    	}
    	
    	log.info("Get Deposit by Hash {}", response);
    }
    
    @Test
    public void testGetWithdrawByOrderId() {
    	Response<Operation> response = client.getApi().getWithdrawByOrderId("W1662104213630", 0, 10);
    	
    	Operation operation = response.getResult();
    	if(operation != null) {
    		if(OperationStatus.Completed.equals(operation.getStatus())) {
    			log.info("Withdraw complete. merchant order id{}", operation.getMerchantOrderId());
    		}
    	}
    	
    	log.info("Get withdraw by order Id {}", response);
    }
    
    @Test
    public void testGetWithdrawByBatchId() {
    	Long batchId = 168L;
    	Response<OperationBatch> response = client.getApi().getWithdrawByBatchId(batchId, 0, 10);
    	
    	OperationBatch batch = response.getResult();
    	if(batch != null) {
    		if(OperationBatchStatus.Completed.equals(batch.getStatus())) {
    			for(Operation operation : batch.getOperations()) {
    				log.info("Withdraw {}. merchant order id{}", operation.getStatus(),operation.getMerchantOrderId());
    			}
    		}
    	}
    	
    	log.info("Get withdraw by batch Id {}", response);
    }
    
    

}
