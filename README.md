

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


#Sign and Verify

Signature and related information are store in a Header object.


| Name        | Description                | HTTP Header          | 
| ----------- | -------------------------- |----------------------|
| address     | Address assigned to server | X-Message-Address    |
| timestamp   |  Time stamp of the message | X-Message-Timestamp  |
| session     | Session ID (Snowflake ID)  |  X-Message-Sessio    |
| sequence    | Session ID (Snowflake ID)  | X-Message-Sequence   |
| signature   | ECDSA_Sign(HSA265(Timestamp#Session#Sequence#BODY)) <br/> Using secp256k1 curve|X-Message-Signature |


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


##Sign


```
Header header = utils.sign("message to be signed");

```


##Verify

Verify message from a HTTP request.

```
Header header = ...; // Initialise Header by get values from HTTP header.
String body = ...; // message from HTTP body
Long expiredInMs = 60000; // define expired time of this message.

// verify message
// return a enum VerifyResult with values
// Expired(-1), SignatureNotMatch(0) or Verified(1);
VerifyResult result = utils.verify(header, body, expiredInMs);

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
 * Get deposit by address.
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


# Testcases

Testcases of calling API methods are implemented in [TestWalletManagerApi](https://github.com/wallet-manager/wallet-manager-client-java/blob/main/src/test/java/dev/m18/walletmanager/client/TestWalletManagerApi.java)


