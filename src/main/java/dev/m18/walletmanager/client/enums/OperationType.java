package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum OperationType implements IntEnum{

    Withdraw(1),
    Sweep(2);
    
	
	@Getter
	private final int value; 
	
	private OperationType(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static OperationType getByValue(int value) {
		for(OperationType e : OperationType.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}