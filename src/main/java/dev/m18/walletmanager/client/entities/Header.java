package dev.m18.walletmanager.client.entities;

import lombok.Data;

@Data
public class Header {
	
    String address;
    
    long timestamp;
    
    long session;
    
    long sequence;
    
    String signature;

}
