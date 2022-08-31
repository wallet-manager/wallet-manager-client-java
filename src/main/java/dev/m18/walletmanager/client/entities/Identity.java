package dev.m18.walletmanager.client.entities;

import lombok.Data;

@Data
public class Identity {
	
	String seed;
	String privateKey;
	String publicKey;
	String address;
}
