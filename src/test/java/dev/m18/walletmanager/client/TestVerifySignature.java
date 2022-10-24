package dev.m18.walletmanager.client;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dev.m18.walletmanager.client.entities.Header;
import dev.m18.walletmanager.client.enums.VerifyResult;
import dev.m18.walletmanager.client.utils.WalletManagerUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestVerifySignature {
	
	String whiteListedAddress;
	Header header;
	String body;


    @Before
    public void setUp() throws Exception {
    	whiteListedAddress = "0xbc99982c7CF7baa65230a6bEC78cb48d9A86CBe4";
        
    	header = new Header();
    	header.setAddress("0xbc99982c7CF7baa65230a6bEC78cb48d9A86CBe4");
    	header.setSequence(1);
    	header.setSession(1034060317857943552L);
    	header.setSignature("0x162ab077f0ddc9569bd42068c8ccc82d86041f0b4410c75a49cb2bf0efa858a831c22b0c8958c6c19ced4fed5a1bf727dc3401ba9710eb25010bb5fe87e4a3c41b");
    	header.setTimestamp(1666609592398L);
    	
    	body = "{\"merchant_id\":\"1\",\"chain_type\":2,\"chain_id\":\"1\",\"client_id\":\"C1666609591475\"}";
    }
    
	
	
	@Test
    public void testVerified() {
    	VerifyResult result = WalletManagerUtils.verify(
    			Set.of(whiteListedAddress), 
    			header, 
    			body, 
    			3600 * 1000);
    	
    	Assert.assertEquals(VerifyResult.Verified, result);
    }
	
	@Test
    public void testChangeWhiteList() {
    	// change white list
		VerifyResult result = WalletManagerUtils.verify(
    			Set.of("0xbc99982c7CF7baa65230a6bEC78cb48d9A86CBe1"), 
    			header, 
    			body, 
    			3600 * 1000);
    	Assert.assertEquals(VerifyResult.InvalidAddress, result);
	}
	
	@Test
	public void testChangeAddress() {
    	// change address
    	header.setAddress("0xbc99982c7CF7baa65230a6bEC78cb48d9A86CBe1");
    	VerifyResult result = WalletManagerUtils.verify(
    			Set.of(whiteListedAddress), 
    			header, 
    			body, 
    			3600 * 1000);
    	Assert.assertEquals(VerifyResult.InvalidAddress, result);
    	
	}
	
	@Test
	public void testChangeBody() {
    	// update body
    	body = "_{\"merchant_id\":\"1\",\"chain_type\":2,\"chain_id\":\"1\",\"client_id\":\"C1666609591475\"}";
    	
    	VerifyResult result = WalletManagerUtils.verify(
    			Set.of(whiteListedAddress), 
    			header, 
    			body, 
    			3600 * 1000);
    	Assert.assertEquals(result, VerifyResult.SignatureNotMatch);
	}
	
	@Test
	public void testChangeTimestamp() {
    	// update body
		header.setTimestamp(header.getTimestamp() - 36000 * 1000);
    	
    	VerifyResult result = WalletManagerUtils.verify(
    			Set.of(whiteListedAddress), 
    			header, 
    			body, 
    			3600 * 1000);
    	Assert.assertEquals(result, VerifyResult.Expired);
	}
	
	@Test
	public void testChangeSignature() {
    	// change signature
		header.setSignature("0x262ab077f0ddc9569bd42068c8ccc82d86041f0b4410c75a49cb2bf0efa858a831c22b0c8958c6c19ced4fed5a1bf727dc3401ba9710eb25010bb5fe87e4a3c41b");
    	
    	VerifyResult result = WalletManagerUtils.verify(
    			Set.of(whiteListedAddress), 
    			header, 
    			body, 
    			3600 * 1000);
    	Assert.assertEquals(result, VerifyResult.SignatureNotMatch);
	}
	
	@Test
	public void testChangeSequence() {
    	// change sequence
		header.setSequence(2L);
		
    	
    	VerifyResult result = WalletManagerUtils.verify(
    			Set.of(whiteListedAddress), 
    			header, 
    			body, 
    			3600 * 1000);
    	Assert.assertEquals(result, VerifyResult.SignatureNotMatch);
	}
	
	
	@Test
	public void testChangeSession() {
    	// change signature
		header.setSession(623547326843L);
    	
    	VerifyResult result = WalletManagerUtils.verify(
    			Set.of(whiteListedAddress), 
    			header, 
    			body, 
    			3600 * 1000);
    	Assert.assertEquals(result, VerifyResult.SignatureNotMatch);
	}
	
	
	
}
