package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum TransactionType implements IntEnum{

    ConfirmedFail(-1),
    Unconfirmed(1),
    ConfirmedSuccess(2),
    Invoid(3);
	
	@Getter
	private final int value; 
	
	private TransactionType(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static TransactionType getByValue(int value) {
		for(TransactionType e : TransactionType.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
