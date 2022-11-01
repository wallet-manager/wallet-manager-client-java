package dev.m18.walletmanager.client.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dev.m18.walletmanager.client.entities.Header;
import dev.m18.walletmanager.client.entities.Identity;
import dev.m18.walletmanager.client.entities.callback.MerchantCallback;
import dev.m18.walletmanager.client.enums.VerifyResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WalletManagerUtils {

	static final int PRIVATE_KEY_SIZE = 32;
	static final int PUBLIC_KEY_SIZE = 64;

	public static final int ADDRESS_SIZE = 160;
	public static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_SIZE >> 2;
	public static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
	public static final int PRIVATE_KEY_LENGTH_IN_HEX = PRIVATE_KEY_SIZE << 1;

	public static final int SIGNATURE_LENGTH_IN_HEX = 130;

	private AtomicLong seq = new AtomicLong(1);
	private long sessionId;
	private ECKeyPair keyPair;
	private String address;
	
	public WalletManagerUtils(String privateKey, int instanceId) {
		this.sessionId = genSnowflakeId(instanceId);
		this.keyPair = ECKeyPair.create(privateKey(privateKey));
		this.address = getAddressFromPublicKey(keyPair.getPublicKey());
	}

	/**
	 * Timestamp#Session#Sequence#BODY
	 * 
	 * @param header
	 * @param body
	 * @return
	 */
	private static String contentToBeSigned(Header header, String body) {
		return new StringBuilder().append(header.getTimestamp()).append("#").append(header.getSession()).append("#")
				.append(header.getSequence()).append("#").append(body).toString();
	}

	public Header sign(String body) {

		Header header = new Header();
		header.setAddress(this.address);
		header.setTimestamp(System.currentTimeMillis());
		header.setSession(this.sessionId);
		header.setSequence(this.seq.getAndIncrement());

		String content = contentToBeSigned(header, body);

		byte[] messageHash = sha256(content);

		SignatureData signature = Sign.signMessage(messageHash, this.keyPair, false);

		log.debug("Sign message content {}", content);
		log.debug("Sign message hash {}", Hex.encodeHexString(messageHash));
		log.debug("Signagure V {}", Hex.encodeHexString(signature.getV()));
		log.debug("Signature R {}", Hex.encodeHexString(signature.getR()));
		log.debug("Signature S {}", Hex.encodeHexString(signature.getS()));

		header.setSignature(signature((signature)));

		return header;

	}

	public static VerifyResult verify(Set<String> whiteListedAddresses, Header header, String body, long expiredInMs) {
		
		if(!whiteListedAddresses.contains(header.getAddress())) {
			return VerifyResult.InvalidAddress;
		}
		
		long now = System.currentTimeMillis();
		if (header.getTimestamp() < now - expiredInMs) {
			return VerifyResult.Expired;
		}

		String content = contentToBeSigned(header, body);

		byte[] messageHash = sha256(content);
		
		String signature = header.getSignature();
        if(!signature.startsWith("0x")){
            signature = "0x" + signature;
        }
		
		

		try {
			log.debug("Sign message content {}", content);
			log.debug("Verfiy message hash {}", Hex.encodeHexString(messageHash));

			List<SignatureData> signatures = new ArrayList<>();
			if (signature.length() == 130) {
				SignatureData signatureData27 = signature(signature + "1b");  // v = 27
				SignatureData signatureData28 = signature(signature + "1c");  // v = 28
				signatures.add(signatureData27);
				signatures.add(signatureData28);
				log.debug("Signagure V {}", Hex.encodeHexString(signatureData27.getV()));
				log.debug("Signature R {}", Hex.encodeHexString(signatureData27.getR()));
			} else {
				SignatureData signatureData = signature(signature);
				signatures.add(signatureData);
				log.debug("Signagure V {}", Hex.encodeHexString(signatureData.getV()));
				log.debug("Signature R {}", Hex.encodeHexString(signatureData.getR()));
				log.debug("Signature S {}", Hex.encodeHexString(signatureData.getS()));
			}

			boolean match = false;
			for(SignatureData s : signatures) {
				BigInteger publicKey = Sign.signedMessageHashToKey(messageHash, s);
				String address = getAddressFromPublicKey(publicKey);
				if (address.equals(header.getAddress())) {
					match = true;
				}
			}

			if (match) {
				return VerifyResult.Verified;
			} else {
				return VerifyResult.SignatureNotMatch;
			}
		} catch (Exception e) {
			log.error("Verify signature failed.", e);
			return VerifyResult.SignatureNotMatch;
		}
	}

	public String getPrivateKey() {
		return privateKey(this.keyPair.getPrivateKey());
	}

	public String getPublicKey() {
		return publicKey(this.keyPair.getPublicKey());
	}

	public String getAddress() {
		return this.address;
	}
	
	public static Long genSnowflakeId(int instanceId) {
		Snowflake snowflake = new Snowflake(instanceId);
		return snowflake.nextId();
	}

	public static byte[] sha256(String content) {
		MessageDigest digest;

		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			log.error("Should never thrown", e);
			throw new RuntimeException(e);
		}

		byte[] encodedhash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
		return encodedhash;
	}

	public static String getAddressFromPublicKey(BigInteger publicKey) {
		return Keys.toChecksumAddress(Keys.getAddress(publicKey));
	}

	public static BigInteger publicKey(String publicKey) {
		return new BigInteger(remove0x(publicKey), 16);
	}

	public static String publicKey(BigInteger publicKey) {
		return Numeric.toHexStringWithPrefixZeroPadded(publicKey, PUBLIC_KEY_LENGTH_IN_HEX);
	}

	public static BigInteger privateKey(String privateKey) {
		return new BigInteger(remove0x(privateKey), 16);
	}

	public static String privateKey(BigInteger privateKey) {
		return Numeric.toHexStringWithPrefixZeroPadded(privateKey, PRIVATE_KEY_LENGTH_IN_HEX);
	}

	public static String signature(SignatureData signature) {
		byte[] signatureBytes = new byte[65];

		System.arraycopy(signature.getR(), 0, signatureBytes, 0, 32);
		System.arraycopy(signature.getS(), 0, signatureBytes, 32, 32);
		System.arraycopy(signature.getV(), 0, signatureBytes, 64, 1);

		return add0x(Hex.encodeHexString(signatureBytes).toLowerCase());
	}

	public static SignatureData signature(String signature) throws DecoderException {
		byte[] signatureBytes = Hex.decodeHex(remove0x(signature).toCharArray());

		byte[] r = new byte[32];
		byte[] s = new byte[32];
		byte[] v = new byte[1];
		System.arraycopy(signatureBytes, 0, r, 0, 32);
		System.arraycopy(signatureBytes, 32, s, 0, 32);
		if(signatureBytes.length > 64) {
			System.arraycopy(signatureBytes, 64, v, 0, 1);
		}
		return new SignatureData(v, r, s);
	}
	
	public static Identity generateBip32ECKey() {
		return generateBip32ECKey(null);
	}
	
	public static Identity generateBip32ECKey(String randomInput) {
		
		SecureRandom random = randomInput != null ? new SecureRandom(randomInput.getBytes()) : new SecureRandom();
		byte seed[] = new byte[2048];
		random.nextBytes(seed);
		Bip32ECKeyPair keyPair = Bip32ECKeyPair.generateKeyPair(seed);
		
		Identity identity = new Identity();
		String privateKey = WalletManagerUtils.privateKey(keyPair.getPrivateKey());
		String publicKey = WalletManagerUtils.publicKey(keyPair.getPublicKey());
		String address = WalletManagerUtils.getAddressFromPublicKey(keyPair.getPublicKey());
		
		identity.setSeed(Hex.encodeHexString(seed));
		identity.setPrivateKey(privateKey);
		identity.setPublicKey(publicKey);
		identity.setAddress(address);
		
		return identity;
	}

	private static String remove0x(String hex) {
		if (hex.startsWith("0x")) {
			return hex.substring(2);
		} else {
			return hex;
		}
	}
	
	private static String add0x(String hex) {
		if (hex.startsWith("0x")) {
			return hex;
		} else {
			return "0x" + hex;
		}
	}
	
	public static MerchantCallback parseMerchantCallback(String json) 
			throws JsonMappingException, JsonProcessingException {
		
		return ObjectMapperUtils.getObjectMapper().readValue(json, MerchantCallback.class);
	}
	

	public static void main(String[] args) {

//		WalletManagerUtils utils = new WalletManagerUtils(
//				"0xaaaacfa6385cd439f23c0be005f3bb9e9303d6b1525e0168ca4b7044e5379085", 1);
//		log.debug(utils.getAddress());
//		log.debug(utils.getPublicKey());
//		log.debug(utils.getPrivateKey());
		
		
//		Header header = utils.sign(body);
//		log.debug("Header {}", header);

		String body = "{\"type\":\"deposit_status\",\"data\":{\"id\":104,\"merchant_id\":1,\"chain_type\":1,\"chain_id\":1,\"client_id\":\"525a1134205946a3348b0507cc3af213\",\"trans_type\":1,\"wallet_address\":\"1LjAYqbJQRL1U1kPmo7MPHmMWPbaj1M3Qc\",\"from_address\":\"1Cxu9uvTkBFVmFN7vvT4dTCsBbrqod4QjP\",\"asset_name\":\"BTC\",\"amount\":\"10000\",\"decimals\":8,\"status\":2,\"updated_time\":1664351487,\"block_number\":\"756030\",\"block_hash\":\"0000000000000000000534b74798cec3ed91ff62fa4049ae38ce3723d01cf262\",\"block_time\":1664349920,\"tx_hash\":\"30e929ac8ae51a9f32805a774e9cc73b5f6bb3a662ef7d1c716d69327548ec51\"}}";
		Header header = new Header();
		header.setAddress("0x55eD3b16A77BbA3f1D64faEC075F7BF0CFFCfb7C");
		header.setSequence(1);
		header.setSession(4360192);
		header.setSignature("0xc9210b26d15b4f3127e88f60e5e559c97f5389f62271a0c77bd0c33263484b3122f0e4475e0225cedfa2a3e2a2d134227de9530735b8320fab5e69f29e230d191c");
		header.setTimestamp(1664351487642L);


		Set<String> whiteListedAddresses = Set.of("0x55eD3b16A77BbA3f1D64faEC075F7BF0CFFCfb7C");
		
		VerifyResult result = WalletManagerUtils.verify(whiteListedAddresses, header, body, 60000000);

		log.debug("Result {}", result);

	}

}
