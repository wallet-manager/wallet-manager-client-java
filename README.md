
# API documents

## API Format
Request and response formats of API.

[WM-API Format.pdf](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/docs/WM-API%20Format.pdf)

## Merchant APIs
The APIs implemented at merchant side used to be called back by Wallet Manager.

[WM-Merchant APIs.pdf](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/docs/WM-Merchant%20APIs.pdf)

## Wallet manager APIs
The APIs private by Wallet Manager.

[WM-Wallet Manager APIs.pdf](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/docs/WM-Wallet%20Manager%20APIs.pdf)

## Appendix (Enum and constants)

[WM-Appendix.pdf](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/docs/WM-Appendix.pdf)

## Support chain list

[WM-Chain List.pdf](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/docs/WM-Chain%20List.pdf)


# Utilities and Client

## Initialise WalletManagerUtils

```
// Your private key for sign message
String privateKey = "PRIVATE_KEY_HERE";
// Instance unique id
int instanceId = 1;
WalletManagerUtils utils = new WalletManagerUtils(privateKey, instanceId);

// Get public key
String publicKey = utils.getPublicKey();

// Get address
String address = utils.getAddress();
```

## Static utility methods

```
public static byte[] sha256(String content);

public static String getAddressFromPublicKey(BigInteger publicKey);

public static BigInteger publicKey(String publicKey);

public static String publicKey(BigInteger publicKey);

public static BigInteger privateKey(String privateKey);

public static String privateKey(BigInteger privateKey);

public static String signature(SignatureData signature);

public static SignatureData signature(String signature) throws DecoderException;
```


# Sign and Verify

Signature and related information are store in a Header object.


| Name        | Description                | HTTP Header          | 
| ----------- | -------------------------- |----------------------|
| address     | Address assigned to server | X-Message-Address    |
| timestamp   | Time stamp of the message  | X-Message-Timestamp  |
| session     | Session ID (Snowflake ID)  | X-Message-Sessio     |
| sequence    | Session ID (Snowflake ID)  | X-Message-Sequence   |
| signature   | ECDSA_Sign(HSA265(Timestamp#Session#Sequence#BODY)) <br/> Using secp256k1 curve|X-Message-Signature |
Ï

```
public class Header {
	
	/**
	 * 
	 * HTTP header X-Message-Address
	 */
    String address;
    
    /**
     * Time stamp of the message
     * HTTP header X-Message-Timestamp
     */
    long timestamp;
    
    /**
     * Session ID (Snowflake ID)
     * HTTP header X-Message-Session
     */
    long session;
    
    /**
     * Sequence of message in this session
     * HTTP header X-Message-Sequence
     */
    long sequence;
    
    /**
     * ECDSA_Sign(HSA265(Timestamp#Session#Sequence#BODY)) 
     * Using secp256k1 curve
     * HTTP header X-Message-Signature
     */
    String signature;

}
```


## Sign


```
Header header = utils.sign("message to be signed");

```


## Verify and Extract Callback

Verify message from a HTTP callback.

```
// Initialise Header by get values from HTTP header.
Header header = ...;
// message from HTTP body 
String body = ...; 
// define expired time of this message.
Long expiredInMs = 60000; 
// white listed addresses
Set<String> whiteListedAddresses = Set.of("0xeD0fe4A3F67938faB2E37EfcB93402EF7b8bc57E"); 

// verify message
// return a enum VerifyResult with values
// Expired(-1), SignatureNotMatch(0) or Verified(1);
VerifyResult result = WalletManagerUtils.verify(whiteListedAddresses, header, body, expiredInMs);


// Parse callback
MerchantCallback callback = WalletManagerUtils.parseMerchantCallback(body);

// if type == "deposit_status"
if(callback.isDepositStatusCallback()){
	// Convert to DepositStatusCallback
	DepositStatusCallback depositStatusCallback = callback.toDepositStatusCallback();
	// process callback here
	...
}

// if type == "operation_status"
if(callback.isOperationStatusCallback()){
	// Convert to OperationStatusCallback
	OperationStatusCallback operationStatusCallback = callback.toOperationStatusCallback();
	// process callback here
	...
}

// if type == "operation_batch_status"
if(callback.isOperationBatchStatusCallback()){
	// Convert to OperationBatchStatusCallback
	OperationBatchStatusCallback operationBatchStatusCallback = callback.toOperationBatchStatusCallback();
	// process callback here
	...
}

// if type == "verify_withdraw_transaction"
if(callback.isVerifyWithdrawCallback()){
	// Convert to VerifyWithdrawCallback
	VerifyWithdrawCallback verifyWithdrawCallback = callback.toVerifyWithdrawCallback();
	// process callback here
	...
}

// If process succeeded
Response<Boolean> response = new Response<>();
response.setResult(true);
    	
// If process failed
Response<Boolean> response = new Response<>();
response.setResult(false);

// Finally, send response in JSON format.
```


# APIs

Methods of call APIs of Wallet Manager are implements in WalletManagerClient.

```
// init
WalletManagerClient client = new WalletManagerClient(
				config.getIdentity().getPrivateKey(),
				config.getClientConfig().getBaseURL(),
				config.getClientConfig().getInstanceId()
			);	
			
WalletManagerUtils utils = client.getUtils();

WalletManagerApi api = client.getApi();
```

Methods of api are declared in [WalletManagerServerApi](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/src/main/java/dev/m18/walletmanager/client/api/WalletManagerServerApi.java)


```
/**
 * Get client deposit address by clientId
 * @param request
 * @return
 */
Response<GetAddressResult> getAddress(GetAddressRequest request);

/**
 * Send a batch withdraw request.
 * @param request
 * @return
 */
Response<BatchWithdrawResult> batchWithdraw(BatchWithdrawRequest request);

/**
 * Send a batch sweep request.
 * @param request
 * @return
 */
Response<BatchSweepResult> batchSweep(BatchSweepRequest request);

/**
 * Get deposit by address.
 * `/${chain_type}/${chain_id}/transfer/addr/${address}/deposit/${asset_name}`
 * @return
 */
Response<GetDepositByAddressResult> getDepositByAddress(
		Integer chainType,
		Long chainId,
		String address,
		String assetName,
		Integer offset,
		Integer limit);

/**
 * Get deposit by tx hash.
 * `/${chain_type}/${chain_id}/transfer/hash/${tx_hash}/deposit`
 * @return
 */
Response<GetDepositByHashResult> getDepositByHash(
		Integer chainType,
		Long chainId,
		String txHash);

/**
 * Get withdraw by merchant order id
 * `/withdraw/order/${merchant_order_id}`
 * @return
 */
Response<Operation> getWithdrawByOrderId(String merchantOrderId);

/**
 * Get withdraw by operation batch ID returned in {@link #batchWithdraw(BatchWithdrawRequest)}
 * `/withdraw/batch/${batch_id}`
 * @return
 */
Response<OperationBatch> getWithdrawByBatchId(Long batchId);
```

# Examples

## Get address

```
GetAddressRequest request = new GetAddressRequest();

request.setMerchantId(3L);
request.setChainType(ChainType.ETH);
request.setChainId(ChainId.Rinkeby);
request.setClientId("C" + clientSeq++);


Response<GetAddressResult> response = client.getApi().getAddress(request);
GetAddressResult result = response.getResult();
if(result != null) {
	log.info("Address of client id {} is {}", request.getClientId(), result.getAddress());
}
```


## Withdraw request

```
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

BatchWithdrawResult result = response.getResult();
if(result != null) {
    log.info("Successfully sent batch withdraw request. batch id {}", result.getBatchId());
}

```


## Get transaction by hash.

```
    	Response<GetDepositByHashResult> response = 
    			client.getApi().getDepositByHash(ChainType.ETH.getIntVal(), ChainId.Rinkeby, "0x11111", 0, 100);

    	GetDepositByHashResult result = response.getResult();
    	if(result != null) {
    		
    		// filter confirmed success transaction
    		List<TransferTransaction> successDepositList = result.getTransactions().stream().filter(
    				tx -> {
    					return Boolean.TRUE.equals(tx.getTxStatus()) && // only success transactions
    							"80000001".equals(tx.getWalletName()) &&  // specify wallet name(client id)
    							tx.getStatus().equals(TransactionStatus.ConfirmedSuccess); // in ConfirmedSucceess status.
    				}).toList();
    		
    		// process success deposit records
    		for(TransferTransaction deposit : successDepositList) {
    			// the unique id of transaction
    			String refNo = deposit.getRefNo();
    			if(true/* this refNo not process before*/) {
    				// process here
    			}
    		}
    	}
```


## Get withdraw by merchant order id

```
Response<Operation> response = client.getApi().getWithdrawByOrderId("100002123");

Operation operation = response.getResult();
if(operation != null) {
	if(OperationStatus.Completed.equals(operation.getStatus())) {
		log.info("Withdraw complete. merchant order id{}", operation.getMerchantOrderId());
	}
}

```

## Get withdraw by batch id

```
Long batchId = 56L;
Response<OperationBatch> response = client.getApi().getWithdrawByBatchId(batchId);

OperationBatch batch = response.getResult();
if(batch != null) {
	if(OperationBatchStatus.Completed.equals(batch.getStatus())) {
		for(Operation operation : batch.getOperations()) {
			log.info("Withdraw {}. merchant order id{}", operation.getStatus(),operation.getMerchantOrderId());
		}
	}
}

```


# Testcases

Testcases of calling API methods are implemented in [TestWalletManagerApi](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/src/test/java/dev/m18/walletmanager/client/TestWalletManagerApi.java)


Testcases of extracting callback request are implemented in [TestWalletManagerCallback](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/src/test/java/dev/m18/walletmanager/client/TestWalletManagerCallback.java)

# Generate Bip32 ECDSA Key

```
public class WalletGenerator {
	
	public static void main(String[] args) {
		
		Identity identity = WalletManagerUtils.generateBip32ECKey();
		
		System.out.println("Seed:\t" + identity.getSeed());
		System.out.println("Private Key:\t" + identity.getPrivateKey());
		System.out.println("Public Key:\t" + identity.getPublicKey());
		System.out.println("Address: \t" + identity.getAddress());
	}
}
```
