package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum ChainType implements IntEnum{

    BTC(1),
    ETH(2),
    TRON(3),
	XRP(4),
	SOLANA(5),
	BCH(6),
	LTC(7);
	
	@Getter
	private final int value; 
	
	private ChainType(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static ChainType getByValue(int value) {
		for(ChainType e : ChainType.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
