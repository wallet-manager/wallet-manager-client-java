package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum TransactionStatus implements IntEnum{

	FastConfirmedFail(-2),
    ConfirmedFail(-1),
    Unconfirmed(1),
    ConfirmedSuccess(2),
    Invoid(3),
	FastConfirmedSuccess(4);
	
	@Getter
	private final int value; 
	
	private TransactionStatus(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static TransactionStatus getByValue(int value) {
		for(TransactionStatus e : TransactionStatus.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
