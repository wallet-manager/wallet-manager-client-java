package dev.m18.walletmanager.client;

import dev.m18.walletmanager.client.entities.Identity;
import dev.m18.walletmanager.client.utils.WalletManagerUtils;

public class WalletGenerator {
	
	public static void main(String[] args) {
		
		Identity identity = WalletManagerUtils.generateBip32ECKey();
		
		System.out.println("Seed:\t" + identity.getSeed());
		System.out.println("Private Key:\t" + identity.getPrivateKey());
		System.out.println("Public Key:\t" + identity.getPublicKey());
		System.out.println("Address: \t" + identity.getAddress());
	}
}
