package dev.m18.walletmanager.client;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dev.m18.walletmanager.client.entities.Response;
import dev.m18.walletmanager.client.entities.callback.DepositStatusCallback;
import dev.m18.walletmanager.client.entities.callback.OperationBatchStatusCallback;
import dev.m18.walletmanager.client.entities.callback.OperationStatusCallback;
import dev.m18.walletmanager.client.utils.WalletManagerUtils;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TestWalletManagerCallback {

	
    @Test
    public void depositStatusCallback() throws JsonMappingException, JsonProcessingException {
    	//String json = "{\"id\":1807,\"merchant_id\":3,\"client_id\":\"C1662022476213\",\"from_address\":\"0x2d1366C71e86F20De3eeCc3f00F270D78A8CEFe5\",\"asset_name\":\"UNI\",\"amount\":\"1000000000000000000\",\"decimals\":18,\"status\":1,\"updated_time\":1662023467,\"block_number\":\"11303719\",\"block_hash\":\"0xfa9740bd808799ee31aa22a507b0c0c371866cddeb4f44bef41a112467d454f0\",\"block_time\":1662023467,\"tx_hash\":\"0x1976e40062b6024d52667a6c88508a2ec0716ab50107ebfc26095beb4e8e4851\"}";
    	
    	String json = "{\n"
    			+ "		\"id\": 1654,\n"
    			+ "		\"merchant_id\": 3,\n"
    			+ "		\"chain_type\": 2,\n"
    			+ "		\"chain_id\": 4,\n"
    			+ "		\"client_id\": \"1661931442815\",\n"
    			+ "		\"trans_type\": 1,\n"
    			+ "		\"wallet_address\": \"0x0ADEAcA39D7cBEFBf9097c937Bcc26399Ba8eF80\",\n"
    			+ "		\"from_address\": \"0xD912d5e14dEb355700e0362a7f898b0454b63684\",\n"
    			+ "		\"asset_name\": \"UNI\",\n"
    			+ "		\"amount\": \"10000000000000000000\",\n"
    			+ "		\"decimals\": 18,\n"
    			+ "		\"status\": 2,\n"
    			+ "		\"updated_time\": 1661935021,\n"
    			+ "		\"block_number\": \"11297681\",\n"
    			+ "		\"block_hash\": \"0x4b14c1d0f8009010e6e1e2866bec278defba8f3e82dc94a359814be7775ff476\",\n"
    			+ "		\"block_time\": 1661932721,\n"
    			+ "		\"tx_hash\": \"0xe8539c054bca34de343b8f15b7fe2736a76b7e66a4c476be96204c0a3a645138\"\n"
    			+ "	}";
    	DepositStatusCallback callback = WalletManagerUtils.parseDepositStatusCallback(json);
    	log.info("Deposit status {}", callback);
    	
    }
    
    @Test
    public void operationStatusCallback() throws JsonMappingException, JsonProcessingException {
//    	String json = "{\"batch_id\":133,\"operation_type\":2,\"operation_seq\":1,\"merchant_order_id\":\"128554693\",\"merchant_id\":3,\"chain_type\":2,\"chain_id\":4,\"asset_name\":\"ETH\",\"amount\":\"1000000000000000\",\"decimals\":18,\"wallet_version\":1,\"path\":\"m/44'/60'/0'/0/1\",\"from_address\":\"0x98a72fe8F26A8E7a4c43489779E65Dec21E6A533\",\"to_address\":\"0x3bB86AF74140649Cb6F97e98D800aC581EA97E31\",\"invoker_address\":\"0xfeeBAa66d152D610DA189fA60e4f11895C11f0b3\",\"client_data\":\"cf_test BatchSweep 001 api\",\"status\":2}";
    	String json = "{\n"
    			+ "		\"batch_id\": 120,\n"
    			+ "		\"operation_type\": 1,\n"
    			+ "		\"operation_seq\": 1,\n"
    			+ "		\"merchant_order_id\": \"hello2\",\n"
    			+ "		\"merchant_id\": 3,\n"
    			+ "		\"chain_type\": 2,\n"
    			+ "		\"chain_id\": 4,\n"
    			+ "		\"asset_name\": \"USDC\",\n"
    			+ "		\"amount\": \"2000000\",\n"
    			+ "		\"decimals\": 6,\n"
    			+ "		\"wallet_version\": 1,\n"
    			+ "		\"path\": \"\",\n"
    			+ "		\"from_address\": \"0xf8f4374652Df888B30A1D4D44547ba2A7AA754D3\",\n"
    			+ "		\"to_address\": \"0x3bB86AF74140649Cb6F97e98D800aC581EA97E31\",\n"
    			+ "		\"invoker_address\": \"\",\n"
    			+ "		\"client_data\": \"nothing2\",\n"
    			+ "		\"status\": 2,\n"
    			+ "		\"updated_time\": 1662021437\n"
    			+ "	}";
    	OperationStatusCallback callback = WalletManagerUtils.parseOperationStatusCallback(json);
    	log.info("Deposit status {}", callback);
    	
    	// set success response
    	Response<Boolean> response = new Response<>();
    	response.setResult(true);
    	// sent out response
    }
    
    @Test
    public void operationBatchStatusCallback() throws JsonMappingException, JsonProcessingException {
    	//String json = "{\"batch_id\":133,\"operation_type\":2,\"merchant_id\":3,\"chain_type\":2,\"chain_id\":4,\"asset_name\":\"ETH\",\"client_data\":\"cf_test BatchSweep 001 api\",\"status\":2,\"updated_time\":1662088378}";
    	String json = "{\n"
    			+ "		\"batch_id\": 120,\n"
    			+ "		\"operation_type\": 1,\n"
    			+ "		\"merchant_id\": 3,\n"
    			+ "		\"chain_type\": 2,\n"
    			+ "		\"chain_id\": 4,\n"
    			+ "		\"asset_name\": \"USDC\",\n"
    			+ "		\"client_data\": \"nothing2\",\n"
    			+ "		\"status\": 2,\n"
    			+ "		\"updated_time\": 1662021438\n"
    			+ "	}";
    	OperationBatchStatusCallback callback = WalletManagerUtils.parseOperationBatchStatusCallback(json);
    	log.info("Deposit status {}", callback);
    }
	
}
