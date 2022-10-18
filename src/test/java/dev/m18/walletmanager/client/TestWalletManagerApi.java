package dev.m18.walletmanager.client;

import java.math.BigInteger;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dev.m18.walletmanager.client.WalletManagerConstants.ChainId;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest;
import dev.m18.walletmanager.client.entities.BatchWithdrawRequest.WithdrawOrder;
import dev.m18.walletmanager.client.entities.BatchWithdrawResult;
import dev.m18.walletmanager.client.entities.GetAddressRequest;
import dev.m18.walletmanager.client.entities.GetAddressResult;
import dev.m18.walletmanager.client.entities.GetAllLatestBlocksResponse;
import dev.m18.walletmanager.client.entities.GetDepositRequestOptions;
import dev.m18.walletmanager.client.entities.GetDepositResult;
import dev.m18.walletmanager.client.entities.GetWithdrawRequestOptions;
import dev.m18.walletmanager.client.entities.LatestBlock;
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
		
		request.setMerchantId(config.getMerchantId());
		request.setChainType(CHAIN_TYPE.getIntVal());
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
        request.setMerchantId(config.getMerchantId());
        request.setChainType(CHAIN_TYPE.getIntVal());
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

    	String addres = "0xaa2a674256017f7B71f8f7dF36041C5187D7B68E";
    	String assetName = "UNI";
    	
    	GetDepositRequestOptions options = 
    			GetDepositRequestOptions.builder().offset(0).limit(10).build();

    	Response<GetDepositResult> response = client.getDepositByAddress(
				ChainType.ETH.getIntVal(), 
				ChainId.Rinkeby, 
				addres, assetName, 
				options);
		
    	log.info("Get Deposit by Address {}", response);
    	
    }
    
    @Test
    public void testGetDepositByHash() {    
    	
    	String txHash = "0x5890c794096a98008f1e0d60feb93076169a1def754e4015971db8215e450ff1";
    	
    	GetDepositRequestOptions options = GetDepositRequestOptions.builder().offset(0).limit(10).build();
    	
    	Response<GetDepositResult> response = 
    			client.getDepositByHash(
    					CHAIN_TYPE.getIntVal(), 
    					CHAIN_ID, 
    					txHash,
    					options);

    	GetDepositResult result = response.getResult();
		if (result != null) {

			// filter confirmed success transaction
			List<TransferTransaction> successDepositList = result.getTransactions().stream().filter(tx -> {
				return Boolean.TRUE.equals(tx.getTxStatus()) && "80000001".equals(tx.getWalletName())
						&& tx.getStatus().equals(TransactionStatus.ConfirmedSuccess);
			}).toList();

			// process success deposit records
			for (TransferTransaction deposit : successDepositList) {
				// the unique id of transaction
				String refNo = deposit.getRefNo();
				log.info("Process deposit refNo: {}", refNo);
				if (true/* this refNo not process before */) {
					// process here
					log.info("Deposit: {}", deposit);
				}
			}
		}
    	
    	log.info("Get Deposit by Hash {}", response);
    }
    
    
    @Test
    public void testGetUniqueDepositByRefNo() {
    	
    	String refNo = "ROX6TM563AL4ZAB7HUKU7VT7IA2EZODWMBLI46CZJ7QDM2XOIPWA";
    	String blockHash = "0x3dd2555ccf211656e21705a584d53d0b7e63b50c5e6d61b3eec7618bd1467eae";
    	Response<GetDepositResult> response = client.getUniqueDepositByRefNo(CHAIN_TYPE.getIntVal(), CHAIN_ID, refNo, blockHash);
    	GetDepositResult result = response.getResult();
    	if(result != null && result.getTransactions().size() > 0) {    		
			
    		// only one record
			Assert.assertEquals(result.getTransactions().size(), 1);
			
			TransferTransaction deposit = result.getTransactions().get(0);
			
			log.info("Tx Status {}", deposit.getTxStatus());
			log.info("Tranaction status {}", deposit.getStatus());
			log.info("Deposit record {}", deposit);
    	}
    }
    
    
    @Test
    public void testSuccessGetDepositByRefNo() {
    	
    	String refNo = "ROX6TM563AL4ZAB7HUKU7VT7IA2EZODWMBLI46CZJ7QDM2XOIPWA";
    	Response<GetDepositResult> response = client.getSuccessDepositByRefNo(CHAIN_TYPE.getIntVal(), CHAIN_ID, refNo);
    	GetDepositResult result = response.getResult();
		if(result != null && result.getTransactions().size() > 0) {
			
			// only one success record
			Assert.assertEquals(result.getTransactions().size(), 1);

			TransferTransaction deposit = result.getTransactions().get(0);
			
			Assert.assertTrue(deposit.getTxStatus());
			log.info("Tx Status {}", deposit.getTxStatus());
			
			// either FastConfirmedSuccess or ConfirmSuccess
			Assert.assertTrue(TransactionStatus.FastConfirmedSuccess.equals(deposit.getStatus()) || 
					TransactionStatus.ConfirmedSuccess.equals(deposit.getStatus()));
			log.info("Tranaction status {}", deposit.getStatus());
			
			log.info("Deposit record {}", deposit);
		}
    }
    
    
    @Test
    public void testGetWithdrawByOrderId() {
    	String orderId = "W1665124395593";
    	Response<Operation> response = client.getWithdrawByOrderId(orderId);
    	
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
    	Response<OperationBatch> response = client.getWithdrawByBatchId(batchId, 
    			GetWithdrawRequestOptions.builder().offset(0).limit(10).build());
    	
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
    
    @Test
    public void testGetAllLatestBlocks() {
    	Response<GetAllLatestBlocksResponse> result = client.getAllLatestBlocks();
    	
    	GetAllLatestBlocksResponse response = result.getResult();
    	
    	Assert.assertTrue(response.size() > 1);
    	
    	for(LatestBlock block : response) {
    		Assert.assertTrue(block.getLatesPendingBlockNumber() >= block.getLatestBlockNumber());	
    	}
    	
    	log.info("Get withdraw by batch Id {}", response);
    }

}
