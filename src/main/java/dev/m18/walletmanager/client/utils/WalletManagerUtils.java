package dev.m18.walletmanager.client.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import dev.m18.walletmanager.client.entities.Header;
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
	private String contentToBeSigned(Header header, String body) {
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

	public VerifyResult verify(Header header, String body, long expiredInMs) {

		String content = contentToBeSigned(header, body);

		byte[] messageHash = sha256(content);

		try {
			SignatureData signature = signature(header.getSignature());

			log.debug("Sign message content {}", content);
			log.debug("Verfiy message hash {}", Hex.encodeHexString(messageHash));
			log.debug("Signagure V {}", Hex.encodeHexString(signature.getV()));
			log.debug("Signature R {}", Hex.encodeHexString(signature.getR()));
			log.debug("Signature S {}", Hex.encodeHexString(signature.getS()));

			BigInteger publicKey = Sign.signedMessageHashToKey(messageHash, signature);

			String address = getAddressFromPublicKey(publicKey);

			long now = System.currentTimeMillis();
			if (header.getTimestamp() < now - expiredInMs) {
				return VerifyResult.Expired;
			}

			if (address.equals(header.getAddress())) {
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

		return add0x(Hex.encodeHexString(signatureBytes, true));
	}

	public static SignatureData signature(String signature) throws DecoderException {
		byte[] signatureBytes = Hex.decodeHex(signature);

		byte[] r = new byte[32];
		byte[] s = new byte[32];
		byte[] v = new byte[1];
		System.arraycopy(signatureBytes, 0, r, 0, 32);
		System.arraycopy(signatureBytes, 32, s, 0, 32);
		System.arraycopy(signatureBytes, 64, v, 0, 1);
		return new SignatureData(v, r, s);
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

	public static void main(String[] args) {

		WalletManagerUtils utils = new WalletManagerUtils(
				"0xaaaacfa6385cd439f23c0be005f3bb9e9303d6b1525e0168ca4b7044e5379085", 1);
		log.debug(utils.getAddress());
		log.debug(utils.getPublicKey());
		log.debug(utils.getPrivateKey());

		String body = "abc";
		Header header = utils.sign(body);
		log.debug("Header {}", header);

		VerifyResult result = utils.verify(header, body, 60000);

		log.debug("Result {}", result);

	}

}
