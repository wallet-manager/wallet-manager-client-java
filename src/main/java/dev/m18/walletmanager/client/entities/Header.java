package dev.m18.walletmanager.client.entities;

import lombok.Data;

@Data
public class Header {
	
	/**
	 * Address assigned to server
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
