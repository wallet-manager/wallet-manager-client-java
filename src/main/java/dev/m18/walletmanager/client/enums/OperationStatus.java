package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum OperationStatus implements IntEnum{

    SignFailed(-3),
    Failed(-2),
    Rejected(-1),
    Requested(1),
    Submitted(2),
    Signed(6),
    Executing(3),
    Completed(4);
    
	
	@Getter
	private final int value; 
	
	private OperationStatus(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static OperationStatus getByValue(int value) {
		for(OperationStatus e : OperationStatus.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
