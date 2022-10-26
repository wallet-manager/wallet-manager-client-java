package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum WalletType implements IntEnum{

    ClientWallet(1),
    HotWallet(2),
    InvokerWallet(3);
	
	@Getter
	private final int value; 
	
	private WalletType(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static WalletType getByValue(int value) {
		for(WalletType e : WalletType.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
